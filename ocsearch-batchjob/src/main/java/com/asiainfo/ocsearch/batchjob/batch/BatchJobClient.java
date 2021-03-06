package com.asiainfo.ocsearch.batchjob.batch;

import com.asiainfo.ocsearch.batchjob.batch.map.BulkloadMapper;
import com.asiainfo.ocsearch.batchjob.status.BulkLoadJobListener;
import com.asiainfo.ocsearch.batchjob.status.JobStatusResult;
import com.asiainfo.ocsearch.batchjob.util.ColumnField;
import com.asiainfo.ocsearch.batchjob.util.SchemaProvider;
import com.asiainfo.ocsearch.meta.Schema;
import com.asiainfo.ocsearch.utils.PropertiesLoadUtil;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;
import org.apache.hadoop.hbase.mapreduce.PutCombiner;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by Aaron on 17/5/26.
 */
public class BatchJobClient extends Configured implements Tool {

    private static final Logger logger = LoggerFactory.getLogger(BatchJobClient.class);

//    private static String DATA_SEPERATOR = "";
    private static String TABLE_NAME = "";

    private static String JOBID;

    private static String ERROR_CODE = "-1";

    private static JobStatusResult jobStatusResult;
    private Configuration configuration;


    public static String submitJob(String inputPath,String outputPath,String tableName,String dataSeparator,Map<String,Integer> fieldSequence,Schema schema) {

        TABLE_NAME = tableName;

//        SchemaProvider schemaProvider = new SchemaProvider(tableName,fieldSequence);

        SchemaProvider schemaProvider = new SchemaProvider(schema);

        schemaProvider.setFieldSequenceMap(fieldSequence);

        String rowKeyExpression = schemaProvider.getRowkeyExpression();

        Map<String,ColumnField> columnFamilyMap = schemaProvider.getColumnFamilyMap();

        String fieldSequenceStr = new Gson().toJson(fieldSequence);

        String columnFamilyStr = new Gson().toJson(columnFamilyMap);

        String[] args = new String[]{inputPath,outputPath,tableName,dataSeparator,rowKeyExpression,fieldSequenceStr,columnFamilyStr};

        return submitJob(args);

    }

    public static String submitJob(String[] args) {

        try {
            int response = ToolRunner.run(HBaseConfiguration.create(), new BatchJobClient(), args);
            if(response == 0) {
                System.out.println("Job is successfully completed...");
                return JOBID;
            } else {
                System.out.println("Job failed...");
                return ERROR_CODE;
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        return ERROR_CODE;
    }

    private Configuration confInit(String[] args) {

        Configuration configuration = getConf();
        configuration.addResource("core-site.xml");
        configuration.addResource("hdfs-site.xml");
        configuration.addResource("hbase-site.xml");
        configuration.set("data.separator", args[3]);
        configuration.set("hbase.table.name", args[2]);
        configuration.set("hbase.table.rowkey.expression",args[4]);
        configuration.set("hbase.column.family.map",args[6]);
        configuration.set("hbase.field.sequence",args[5]);
//        configuration.set("mapreduce.job.user.classpath.first", "true", "mapred-site.xml");
        Properties properties = PropertiesLoadUtil.loadProFile("ocsearch.properties");
        String libPath = properties.getProperty("bulkload.lib.path");
        List<String> jars = new ArrayList<>();
        try {
            FileSystem fileSystem = FileSystem.get(configuration);
            for (FileStatus fs : fileSystem.listStatus(new Path(libPath))) {
                System.out.println(fs.getPath());
                jars.add(fs.getPath().toString());
            }
            fileSystem.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
        String tmpJars = StringUtils.join(jars, ",");
        configuration.set("tmpjars", tmpJars);

        return configuration;
    }

    public int run(String[] args) throws Exception {
        String outputPath = args[1];
        configuration = confInit(args);
        Job job = Job.getInstance(configuration, "Bulk Loading HBase Table::" + TABLE_NAME);
        job.setJarByClass(BatchJobClient.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setMapOutputKeyClass(ImmutableBytesWritable.class);//指定输出键类
        job.setMapOutputValueClass(Put.class);//指定输出值类
        job.setMapperClass(BulkloadMapper.class);//指定Map函数
//        job.setCombinerClass(PutCombiner.class);
        String inputPath = args[0];
        String commaSeparatedInputPaths = "";
        try {
            FileSystem fileSystem = FileSystem.get(configuration);
            for (FileStatus fs : fileSystem.listStatus(new Path(inputPath))) {
                logger.info("BulkLoad Input File : "+fs.getPath().toString());
                commaSeparatedInputPaths = commaSeparatedInputPaths.concat(fs.getPath().toString()).concat(",");
            }
            commaSeparatedInputPaths = commaSeparatedInputPaths.substring(0,commaSeparatedInputPaths.length()-1);
            logger.info("BulkLoad Input Files is : "+commaSeparatedInputPaths);
            fileSystem.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
        FileInputFormat.addInputPaths(job, commaSeparatedInputPaths);//输入路径
        FileSystem fs = FileSystem.get(configuration);
        Path output = new Path(outputPath);
        if (fs.exists(output)) {
            fs.delete(output, true);//如果输出路径存在，就将其删除
        }
        FileOutputFormat.setOutputPath(job, output);//输出路径
        Connection connection = ConnectionFactory.createConnection(configuration);
        TableName tableName = TableName.valueOf(TABLE_NAME);
        HFileOutputFormat2.configureIncrementalLoad(job, connection.getTable(tableName), connection.getRegionLocator(tableName));

        TableMapReduceUtil.addDependencyJars(job);
        job.submit();
        JobID jobId = job.getJobID();
        JOBID = jobId.toString();
        jobStatusResult = new JobStatusResult(JOBID);
        jobStatusResult.setJobStatusListener(new BulkLoadJobListener());
        jobStatusResult.startJob(new Date().getTime());

        new Thread(new BulkLoadThread(job,outputPath)).start();

//        job.waitForCompletion(true);
        return 0;
    }
    private class BulkLoadThread implements Runnable {

        Job job;
        String hfilePath;

        public BulkLoadThread(Job job,String hfilePath) {
            this.job = job;
            this.hfilePath = hfilePath;
        }

        @Override
        public void run() {
            try {
                job.monitorAndPrintJob();
                if (job.isSuccessful()){
                    Long everyJobInputLine = 0L;
                    Long everyJobOutputLine = 0L;
                    Long everyJobBadLine = 0L;
                    everyJobBadLine = job.getCounters().findCounter(BulkloadMapper.COUNTERS.BAD).getValue();
                    everyJobInputLine = job.getCounters().findCounter(BulkloadMapper.COUNTERS.TOTAL).getValue();
                    everyJobOutputLine = everyJobInputLine - everyJobBadLine;
                    logger.info("BulkLoad Job Bad Lines Number is "+everyJobBadLine);
                    logger.info("BulkLoad Job Input Lines Number is "+everyJobInputLine);
                    logger.info("BulkLoad Job Output Lines Number is "+everyJobOutputLine);
//                    HFileLoader.doBulkLoad(hfilePath, TABLE_NAME);//导入数据
                    String[] toolRunnerArgs = new String[]{hfilePath,TABLE_NAME};
                    int ret = ToolRunner.run(new LoadIncrementalHFiles(configuration), toolRunnerArgs);
                    if(ret==0) {
                        jobStatusResult.setBadlines(everyJobBadLine);
                        jobStatusResult.setTotallines(everyJobInputLine);
                        jobStatusResult.finishJob(new Date().getTime());
                    }else {
                        jobStatusResult.failJob(new Date().getTime());
                    }
                }else {
                    jobStatusResult.failJob(new Date().getTime());
                }
            } catch (Exception e) {
                jobStatusResult.failJob(new Date().getTime());
                e.printStackTrace();
            }

        }
    }

}
