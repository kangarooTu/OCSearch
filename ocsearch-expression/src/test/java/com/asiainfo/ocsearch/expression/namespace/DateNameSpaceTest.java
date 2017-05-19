package com.asiainfo.ocsearch.expression.namespace;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 2017/5/11.
 */
public class DateNameSpaceTest {
    @Test
    public void testFormat() throws Exception {

    }

    @Test
    public void testToDate() throws Exception {

    }

    @Test
    public void testNow() throws Exception {
        Map<String, Object> jc = new HashMap<>();

        jc.put("count",10.1);
        jc.put("name","hello");
        JexlEngine jexl = new JexlBuilder().create();
        JexlExpression jexlExpression = jexl.createExpression("(count>=10) and (count<=10) or name>'hoello'");
//        Executor ee = Engine.getInstance().createExecutor("count>10");

        System.out.println(jexlExpression.evaluate(new MapContext(jc)));
    }

}