package com.asiainfo.ocsearch.service.query;

import com.asiainfo.ocsearch.exception.ErrorCode;
import com.asiainfo.ocsearch.exception.ServiceException;
import com.asiainfo.ocsearch.listener.ThreadPoolManager;
import com.asiainfo.ocsearch.meta.Schema;
import com.asiainfo.ocsearch.metahelper.MetaDataHelperManager;
import com.asiainfo.ocsearch.query.*;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by mac on 2017/5/18.
 */
public class GetService extends QueryService {

    @Override
    public JsonNode query(JsonNode request) throws ServiceException {

        try {
            if (false == (request.has("tables") && request.has("ids"))) {
                throw new ServiceException("the get service request must have 'table' and 'ids' param keys!", ErrorCode.PARSE_ERROR);
            }
            ArrayNode tables = (ArrayNode) request.get("tables");
            String table = tables.get(0).asText();

            Schema schema = MetaDataHelperManager.getInstance().getSchemaByTable(table);

            ArrayNode idsNode = (ArrayNode) request.get("ids");

            List<String> ids = new ArrayList<>();

            idsNode.forEach(jsonNode -> ids.add(jsonNode.asText()));

            if (schema == null)
                throw new ServiceException("the table does not exist!", ErrorCode.TABLE_NOT_EXIST);


            ArrayNode returnNode = (ArrayNode) request.get("return_fields");

            HbaseQuery hbaseQuery = new HbaseQuery(schema, table, generateReturnFields(schema, returnNode), ids);

            CountDownLatch runningThreadNum = new CountDownLatch(1);

            QueryActor queryActor = new GetQueryActor(hbaseQuery, runningThreadNum);

            ThreadPoolManager.getExecutor("getQuery").submit(queryActor);

            runningThreadNum.await();

            QueryResult result = queryActor.getQueryResult();
            if (null != result.getLastError()) {
                throw result.getLastError();
            } else {

                ObjectNode data = JsonNodeFactory.instance.objectNode();

                data.put("total", result.getTotal());
                boolean withTable = hasTable(returnNode);
                boolean withId = hasId(returnNode);
                ArrayNode docs = JsonNodeFactory.instance.arrayNode();
                ids.forEach(id -> {
                    ObjectNode node = result.getData().remove(0);

                    docs.add(updateNode(node,id,table,withTable,withId));
                });
                data.put("docs", docs);

                return data;
            }
        } catch (ServiceException e) {
            log.warn(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException(e, ErrorCode.RUNTIME_ERROR);
        }
    }



}
