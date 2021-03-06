package com.asiainfo.ocsearch.meta;

import java.io.Serializable;

/**
 * Created by mac on 2017/5/4.
 */
public enum IndexType  implements Serializable {

    HBASE_ONLY(-1),HBASE_SOLR(0), HBASE_PHOENIX(1),HBASE_SOLR_PHOENIX(2);

    int value;
    IndexType(int value) {
        this.value=value;
    }
    public int getValue(){
        return value;
    }
    public static IndexType valueOf(int indexType){
        switch (indexType) {
            case -1:
               return HBASE_ONLY;
            case 0:
                return HBASE_SOLR;
            case 1:
                return HBASE_PHOENIX;
            case 2:
                return HBASE_SOLR_PHOENIX;
            default:
                throw new RuntimeException("unknown index type : " + indexType);
        }
    }


}