package com.asiainfo.ocsearch.service.table;

import com.asiainfo.ocsearch.exception.ErrorCode;
import com.asiainfo.ocsearch.exception.ServiceException;
import com.asiainfo.ocsearch.meta.IndexType;
import com.asiainfo.ocsearch.meta.Schema;
import com.asiainfo.ocsearch.metahelper.MetaDataHelper;
import com.asiainfo.ocsearch.metahelper.MetaDataHelperManager;
import com.asiainfo.ocsearch.service.OCSearchService;
import com.asiainfo.ocsearch.transaction.Transaction;
import com.asiainfo.ocsearch.transaction.atomic.table.*;
import com.asiainfo.ocsearch.transaction.internal.TransactionImpl;
import com.asiainfo.ocsearch.transaction.internal.TransactionUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;


/**
 * Created by mac on 2017/5/4.
 */
public class DeleteTableService extends OCSearchService {

    Logger stateLog = Logger.getLogger("state");


    @Override
    public byte[] doService(JsonNode request) throws ServiceException {
        String uuid = getRequestId();
        try {
            stateLog.info("start request " + uuid + " at " + System.currentTimeMillis());
            String name = request.get("name").asText();

            if (!MetaDataHelperManager.getInstance().hasTable(name)) {
                throw new ServiceException("table " + name + " does not exist!", ErrorCode.TABLE_NOT_EXIST);
            }
            MetaDataHelper metaDataHelper = MetaDataHelperManager.getInstance();
            Schema schema = metaDataHelper.getSchemaByTable(name);

            Transaction transaction = new TransactionImpl();

//            transaction.add(new RemoveTableFromDb(name));
            transaction.add(new RemoveTableFromZk(name));  //instead db with zookeeper

            IndexType indexType = schema.getIndexType();

            if (indexType == IndexType.HBASE_SOLR || indexType == IndexType.HBASE_SOLR_PHOENIX) {
                transaction.add(new DeleteIndexerTable(name));
                transaction.add(new DeleteSolrCollection(name));
            }
            if (indexType == IndexType.HBASE_PHOENIX || indexType == IndexType.HBASE_SOLR_PHOENIX) {
                transaction.add(new DeletePhoenixView(name));
            }

            if (!schema.withHbase())
                transaction.add(new DeleteHbaseTable(name));

            String lock = metaDataHelper.lock("TABLE_" + name);

            if (lock == null)
                throw new ServiceException("get lock failure,please check lock file on zookeeper", ErrorCode.TABLE_EXIST);

            if (!transaction.canExecute()) {
                throw new ServiceException(String.format("delete table :%s  failure because of transaction can't be executed. please check hbase indexer servers", name), ErrorCode.RUNTIME_ERROR);
            }
            try {
                transaction.execute();
            } catch (Exception e) {
                TransactionUtil.serialize(uuid + "_table_delete_" + name, transaction, false);
                log.error("delete table " + name + " failure", e);
                throw e;
            } finally {
                metaDataHelper.unlock(lock);
            }
            return success;
        } catch (ServiceException se) {
            log.warn(se);
            throw se;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException(e, ErrorCode.RUNTIME_ERROR);
        } finally {
            stateLog.info("end request " + uuid + " at " + System.currentTimeMillis());
        }
    }
}
