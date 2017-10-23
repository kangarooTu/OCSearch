'use strict';

angular.module('basic').config(["$translateProvider", function($translateProvider) {
  $translateProvider.translations("zh", {
    ADD_NEW_TABLE: "增加新表",
    ADD_NEW_SCHEMA: "新建表结构",
    EDIT_SCHEMA: "编辑表结构",
    NAME: "名称",
    INDEX_TYPE: "类型",
    ROWKEY_EXPRESSION: "Rowkey表达式",
    TABLE_EXPRESSION: "Table表达式",
    SCHEMA: "表结构",
    SCHEMA_NAME: "表结构名称",
    WEIGHT: "权重",
    FIELDS: "字段",
    TABLE: "表",
    SEARCH: "搜索",
    REGION_NUM: "Region Num",
    REGION_SPLIT: "Region Split",
    SOLR_SHARDS: "Solr Shards",
    SOLR_REPLICAS: "Solr Replicas",
    OK: "确定",
    CANCEL: "取消",
    NEXT: "下一步",
    ERROR: "错误",
    PREV: "上一步",
    EDIT_TABLE_FIELDS: "编辑表结构",
    FIELD_NAME: "字段名",
    STORE_TYPE: "存储类型",
    CONTENT_FIELD: "内容索引字段",
    INNER_FIELD: "压缩字段",
    FIELD_INDEX: "字段索引",
    INDEXED: "索引",
    QUERY_FIELD: "查询字段",
    ID_FORMATTER: "ID Formatter",
    INTABLE_NAME: "字段名",
    INTABLE_TYPE: "字段类型",
    INTABLE_SEPARATOR: "分隔符",
    INTABLE_STORE_TYPE: "字段类型",
    INTABLE_INDEX_TYPE: "字段索引类型",
    INTABLE_INNER_FIELD: "压缩字段",
    INTABLE_CONTENT_FIELD: "内容索引字段",
    INTABLE_QUERY_WEIGHT: "查询权重",
    INTABLE_OPERATION: "操作",
    INTABLE_WEIGHT: "权重",
    INTABLE_DISPLAY: "搜索结果显示",
    DISPLAY: "显示",
    SELECT_TYPE: "选择类型",
    SELECT_SCHEMA: "选择表结构",
    SELECT_INDEX_TYPE: "选择类型",
    SELECT_FIELD_STORE_TYPE: "选择字段存储类型",
    SELECT_FIELD_INDEX_TYPE: "选择索引类型",
    SELECT_FIELD_CONTENT_FIELD: "选择已经存在的内容索引字段",
    SELECT_FIELD_INNER_FIELD: "选择已经存在的压缩字段",
    SELECT_QUERY_FIELD: "选择已经存在的字段建立查询字段",
    BASIC_INFO: "基础信息",
    ADD: "新建",
    EDIT: "编辑",
    DELETE: "删除",
    UNAVAILABLE: "不可用",
    YES: "是",
    NO: "否",
    CONFIRMATION: "确定",
    WARNING: "警告",
    MODALMSG_FILL_IN_ALL: "请填写所有标记为*的内容",
    MODALMSG_NO_FIELDS: "未定义任何字段！",
    CONFIRM_ADD_TABLE: "新建表？",
    CONFIRM_EDIT_TABLE: "改变表结构？",
    CONFIRM_DELETE_TABLE: "删除选中表？表中所有数据将会丢失！",
    CONFIRM_ADD_SCHEMA: "确定新建表结构？",
    CONFIRM_EDIT_SCHEMA: "确定修改表结构？",
    CONFIRM_DELETE_SCHEMA: "确定删除选中的表结构？",
    CONFIRM_DELETE_SCHEMA_ERR: "无法删除表结构，请先删除所有相关的表！",
    CONFIRM_EDIT_SCHEMA_ERR: '无法编辑表结构，请在"表"面板里编辑表结构字段',
    CONFIRM_TITLE_CREATE_TABLE_ERROR: "创建表失败",
    CONFIRM_TITLE_EDIT_TABLE_ERROR: "编辑表错误",
    CONFIRM_TITLE_CREATE_SCHEMA_ERROR: "创建表结构失败",
    EMPTY: "(空)",
    UNDEFINED: "(未定义)",
    NULL: "(null)",
    HELP_EXPRESSION_TITLE: "如何建立一个表达式",
    HELP_EXPRESSION_CONTENT: "想要了解",
    HELP_EXPRESSION_LINK: "更多",
    ROWS: "每页行数",
    CONDITIONS: "搜索条件",
    ADVANCE_SEARCH: "高级搜索",
    WITH_HBASE: "Hbase已存在",
    HBASE: "Hbase",
    HBASE_FAMILY: "family",
    HBASE_COLUMN: "column",
  });
}]);