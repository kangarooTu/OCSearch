package com.asiainfo.ocsearch.datasource.solr;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.request.schema.SchemaRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.schema.SchemaResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mac on 2017/3/22.
 */
public class SolrServer {

    private CloudSolrClient solrServer;

    private SolrConfig solrConfig;


    public SolrServer(Properties prop) {

        this.solrConfig = new SolrConfig(prop);

        solrServer = createSolrClient();
    }


    private CloudSolrClient createSolrClient() {

        CloudSolrClient solrClient = new CloudSolrClient.Builder().withZkHost(solrConfig.getZookeeper()).build();

        solrClient.setSoTimeout(solrConfig.getSoTimeout());
        solrClient.setZkConnectTimeout(solrConfig.getZkConnectTimeout());
        solrClient.setZkClientTimeout(solrConfig.getZkClientTimeout());
        solrClient.connect();
        return solrClient;
    }

    public void createCollection(String collection, String config, int numShards, int numReplicas) throws Exception {

        CollectionAdminRequest.Create create = CollectionAdminRequest.createCollection(collection, config, numShards, numReplicas);

        create.setAutoAddReplicas(solrConfig.isAutoAddReplicas());
        create.setMaxShardsPerNode(solrConfig.getMaxShardsPerNode());
        CollectionAdminResponse response = create.process(solrServer);

        if (!response.isSuccess()) {
            throw new Exception(response.getErrorMessages().toString());
        }

    }

    public void createCollection(String collection, String config, int shards) throws Exception {

        this.createCollection(collection, config, shards, solrConfig.getReplicas());
    }


    public SolrDocumentList query(String collection, SolrParams solrParams) throws Exception {
        try {
            QueryResponse queryResponse = solrServer.query(collection, solrParams);
            return queryResponse.getResults();
        } catch (SolrServerException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public QueryResponse queryWithCursorMark(String collection, SolrParams solrParams) throws Exception {

        try {
            return solrServer.query(collection, solrParams);
        } catch (SolrServerException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void uploadConfig(Path p, String name) throws IOException {
        this.solrServer.uploadConfig(p, name);
    }

    public void deleteConfig(String name) throws IOException {
        this.solrServer.getZkStateReader().getConfigManager().deleteConfigDir(name);
    }

    /**
     * close the solr client
     */
    public void close() {
        if (this.solrServer != null) {
            try {
                this.solrServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.solrServer = null;
    }

    public void deleteCollection(String collection) throws Exception {

        CollectionAdminRequest.Delete delete = CollectionAdminRequest.deleteCollection(collection);

        CollectionAdminResponse response = delete.process(solrServer);

        if (!response.isSuccess()) {
            throw new Exception(response.getErrorMessages().toString());
        }
    }

    public boolean existCollection(String collection) throws Exception {

        return solrServer.getZkStateReader().getClusterState().getCollectionsMap().containsKey(collection);

    }

    public boolean existConfig(String config) throws IOException {
        return this.solrServer.getZkStateReader().getConfigManager().configExists(config);
    }

    public boolean existsFields(String collection, Set<String> solrFieldNames) throws IOException, SolrServerException {

        SchemaRequest.Fields fields = new SchemaRequest.Fields();

        SchemaResponse.FieldsResponse response = fields.process(solrServer, collection);

        Set<String> allFields = response.getFields().stream().map(map -> map.get("name").toString()).collect(Collectors.toSet());

        for (String name : solrFieldNames) {
            if (!allFields.contains(name)) {
                return false;
            }
        }
        return true;
    }

    public boolean addField(Map<String, Object> params, String collection) {

        try {
            SchemaRequest.AddField addField = new SchemaRequest.AddField(params);

            SchemaResponse.UpdateResponse response = addField.process(solrServer, collection);
            if (response.getStatus() == 0)
                return true;
            else
                throw new RuntimeException("add field error:" + params.get("name"));
        } catch (SolrServerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    public boolean addCopyField(String source,String dest,String collection){
        try {
            SchemaRequest.AddCopyField addField = new SchemaRequest.AddCopyField(source, Arrays.asList(dest));

            SchemaResponse.UpdateResponse response = addField.process(solrServer, collection);
            if (response.getStatus() == 0)
                return true;
            else
                throw new RuntimeException("add source field error:" + source);

        } catch (SolrServerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean updateFields(List<SchemaRequest.Update> updates,String collection){
        try {
            SchemaRequest.MultiUpdate mu = new SchemaRequest.MultiUpdate(updates);

            SchemaResponse.UpdateResponse response = mu.process(solrServer, collection);
            if (response.getStatus() == 0)
                return true;
            else
                throw new RuntimeException("update field error" );

        } catch (SolrServerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void optimize() {
        try {
            solrServer.optimize("SITE",false,false);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public SolrConfig getSolrConfig() {
        return solrConfig;
    }
}
