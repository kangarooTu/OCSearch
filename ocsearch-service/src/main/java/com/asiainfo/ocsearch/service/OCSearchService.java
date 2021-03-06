package com.asiainfo.ocsearch.service;


import com.asiainfo.ocsearch.constants.Constants;
import com.asiainfo.ocsearch.constants.OCSearchEnv;
import com.asiainfo.ocsearch.exception.ErrorCode;
import com.asiainfo.ocsearch.exception.ServiceException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mac on 2017/3/21.
 */
public abstract class OCSearchService extends HttpServlet {

    protected Logger log = Logger.getLogger(getClass());

    protected byte[] success;

    public OCSearchService() {

        try {
            success = "{\"result\":{\"error_code\":0,\"error_desc\":\"success\"}}".getBytes(Constants.DEFUAT_CHARSET);
        } catch (IOException e) {
        }
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = getRequestId();
        long start = System.currentTimeMillis();
        log.info("start request:" + id);
        try {
            byte[] re;

            if (request.getContentLength() > Integer.parseInt(OCSearchEnv.getEnvValue("request.length.max", "10240")))
                re = new ServiceException("request is too long", ErrorCode.PARSE_ERROR).getErrorResponse();
            else {

                try {
                    JsonNode jsonNode = new ObjectMapper().readTree(request.getInputStream());
                    if (log.isDebugEnabled())
                        log.debug(id + ":" + jsonNode.toString());
                    re = doService(jsonNode);

                } catch (IOException e) {
                    re = new ServiceException(e, ErrorCode.PARSE_ERROR).getErrorResponse();
                } catch (ServiceException serviceException) {
                    re = serviceException.getErrorResponse();
                }
            }
            log.info("return request:" + id + "," + (System.currentTimeMillis() - start) + "ms used.");
            response.setCharacterEncoding("utf-8");
            ServletOutputStream so = response.getOutputStream();
            so.write(re);
            so.flush();
            so.close();
        } catch (Exception e) {
            log.error("transport data failure:", e);
        } finally {
            log.info("end request:" + id + "," + (System.currentTimeMillis() - start) + "ms used.");
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = getRequestId();
        long start = System.currentTimeMillis();
        log.info("start request:" + id);
        try {
            Map<String, String[]> params = request.getParameterMap();
            ObjectNode jsonNode = new ObjectMapper().createObjectNode();

            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                jsonNode.put(entry.getKey(), entry.getValue()[0]);
            }

            byte[] re;

            try {
                if (log.isDebugEnabled())
                    log.debug(id + ":" + jsonNode.toString());
                re = doService(jsonNode);
            } catch (ServiceException serviceException) {
                re = serviceException.getErrorResponse();
            }

            response.setCharacterEncoding("utf-8");
            ServletOutputStream so = response.getOutputStream();
            so.write(re);
            so.flush();
            so.close();
        } catch (Exception e) {
            log.error(e);
        } finally {
            log.info("end request:" + id + "," + (System.currentTimeMillis() - start) + "ms used.");
        }
    }

    public abstract byte[] doService(JsonNode request) throws ServiceException;

    protected String getRequestId() {
        return UUID.randomUUID().toString();
    }

    protected ObjectNode getSuccessResult() {
        try {
            return (ObjectNode) new ObjectMapper().readTree("{\"result\":{\"error_code\":0,\"error_desc\":\"success\"}}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
