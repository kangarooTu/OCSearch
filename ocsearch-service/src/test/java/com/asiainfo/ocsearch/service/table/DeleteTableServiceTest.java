package com.asiainfo.ocsearch.service.table;

import com.asiainfo.ocsearch.datasource.hbase.HbaseServiceManager;
import com.asiainfo.ocsearch.datasource.hbase.PutService;
import com.asiainfo.ocsearch.listener.SystemListener;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.hadoop.hbase.client.Put;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2017/6/1.
 */
public class DeleteTableServiceTest {
    @Test
    public void testDoService() throws Exception {
        new SystemListener().initAll();
        new TestSendData().start();
        Thread.sleep(1000*60*60);

//        String tableString = "{\n" +
//                "    \"name\": \"GPRS__20170510\",\n" +
//                "    \"schema\": \"phoenixSchema\",\n" +
//
//                "    \"hbase_exist\":false\n" +
//                "}";
//        System.out.println(tableString);
//        new DeleteTableService().doService(new ObjectMapper().readTree(tableString));

//        PutService putService = HbaseServiceManager.getInstance().createPutService("SITEPOSITION");
//        for (int j = 0; j < 10; j++) {
//            List<Put> puts = new ArrayList<>();
//            for (int i = 0; i < 1000; i++) {
//                puts.add(generateData());
//            }
//            putService.execute("SITEPOSITION", htable -> {
//                htable.put(puts);
//                return true;
//            });
////            Thread.sleep(1);
//            System.out.println(1000);
//        }
    }

    RandomDataGenerator random = new RandomDataGenerator();

    String[] areas = new String[]{"A0A211024", "A0A211017", "A0A211011", "A0A211020", "A0A211027", "A0A211004", "A0A211013", "A0A211007",
            "A0A211015", "A0A211018", "A0A211010", "A0A211002", "A0A211025", "A0A211016", "A0A211023", "A0A211022", "A0A211009", "A0A211003", "A0A211008",
            "A0A211005", "A0A211012", "A0A211001", "A0A211006", "A0A211026", "A0A211019", "A0A211014", "A0A211021"};

    public Put generateData() {
        Put put = new Put(random.nextHexString(8).getBytes());
        put.addColumn("INFO".getBytes(), "SECURITY_AREA".getBytes(), areas[random.nextInt(0, areas.length - 1)].getBytes());
        put.addColumn("INFO".getBytes(), "LAC".getBytes(), "37194".getBytes());
        put.addColumn("INFO".getBytes(), "CELL".getBytes(), "61402".getBytes());
        put.addColumn("0".getBytes(), "PHONENUM".getBytes(), ("135" + random.nextLong(10000000l, 99999999l)).getBytes());
        return put;
    }

    @Test
    public void testData() throws IOException {
        String data[] = new String[]{"{\n" +
                "    \"query\": \"\",\n" +
                "    \"start\": 0,\n" +
                "    \"rows\": 100,\n" +
                "    \"sort\": \"id asc\",\n" +
                "    \"condition\": \"SECURITY_AREA:A0A211001\",\n" +
                "    \"tables\": [\"SITEPOSITION\"],\n" +
                "    \"return_fields\": [\"PHONENUM\",\"id\"]\n" +
                "}",
                "{\"result\":{\"error_code\":0,\"error_desc\":\"success\"},\"data\":{\"total\":756,\"docs\":[{\"id\":\"85f8e1b4\",\"PHONENUM\":\"13515854675\"},{\"id\":\"c5310246\",\"PHONENUM\":\"13550157222\"},{\"id\":\"efbb96a0\",\"PHONENUM\":\"13540527558\"},{\"id\":\"3de613ff\",\"PHONENUM\":\"13523019228\"},{\"id\":\"5003171f\",\"PHONENUM\":\"13514749060\"},{\"id\":\"69ed4571\",\"PHONENUM\":\"13594903500\"},{\"id\":\"d5638b54\",\"PHONENUM\":\"13524328557\"},{\"id\":\"d6b2537c\",\"PHONENUM\":\"13554026510\"},{\"id\":\"e2a9a9b0\",\"PHONENUM\":\"13517445079\"},{\"id\":\"a2ada476\",\"PHONENUM\":\"13546943128\"},{\"id\":\"cd785942\",\"PHONENUM\":\"13563959861\"},{\"id\":\"8c406a2c\",\"PHONENUM\":\"13598830470\"},{\"id\":\"7fbdc7c2\",\"PHONENUM\":\"13560011732\"},{\"id\":\"6995858d\",\"PHONENUM\":\"13551192113\"},{\"id\":\"243a4672\",\"PHONENUM\":\"13596288466\"},{\"id\":\"8699fa88\",\"PHONENUM\":\"13563278026\"},{\"id\":\"55573045\",\"PHONENUM\":\"13593940457\"},{\"id\":\"37dcc8d6\",\"PHONENUM\":\"13530002468\"},{\"id\":\"f2164d9b\",\"PHONENUM\":\"13560243666\"},{\"id\":\"34673290\",\"PHONENUM\":\"13549830680\"},{\"id\":\"882c94fb\",\"PHONENUM\":\"13566058196\"},{\"id\":\"4b2cf293\",\"PHONENUM\":\"13555434302\"},{\"id\":\"4249af2f\",\"PHONENUM\":\"13515258520\"},{\"id\":\"07d7156a\",\"PHONENUM\":\"13572508440\"},{\"id\":\"7c14f785\",\"PHONENUM\":\"13512139264\"},{\"id\":\"efd89521\",\"PHONENUM\":\"13561275994\"},{\"id\":\"03f8d721\",\"PHONENUM\":\"13568614197\"},{\"id\":\"89a5a586\",\"PHONENUM\":\"13580948993\"},{\"id\":\"6f756f9e\",\"PHONENUM\":\"13541340424\"},{\"id\":\"9debcaf9\",\"PHONENUM\":\"13594036587\"},{\"id\":\"ba7e4fd4\",\"PHONENUM\":\"13517971760\"},{\"id\":\"666e5808\",\"PHONENUM\":\"13572139874\"},{\"id\":\"994ec0a4\",\"PHONENUM\":\"13563536207\"},{\"id\":\"73c76c3e\",\"PHONENUM\":\"13532530320\"},{\"id\":\"418b06f0\",\"PHONENUM\":\"13561737163\"},{\"id\":\"549933fe\",\"PHONENUM\":\"13553532948\"},{\"id\":\"850e1d7f\",\"PHONENUM\":\"13543900707\"},{\"id\":\"25851452\",\"PHONENUM\":\"13573269090\"},{\"id\":\"1e9302f1\",\"PHONENUM\":\"13541961003\"},{\"id\":\"1e314819\",\"PHONENUM\":\"13510469083\"},{\"id\":\"aba7e969\",\"PHONENUM\":\"13511375486\"},{\"id\":\"e49e79ad\",\"PHONENUM\":\"13533120313\"},{\"id\":\"11c12b90\",\"PHONENUM\":\"13546675377\"},{\"id\":\"f6c3ad28\",\"PHONENUM\":\"13534518578\"},{\"id\":\"8145c319\",\"PHONENUM\":\"13579520740\"},{\"id\":\"626a8379\",\"PHONENUM\":\"13536662458\"},{\"id\":\"f32fdfd9\",\"PHONENUM\":\"13544654365\"},{\"id\":\"ced05ad0\",\"PHONENUM\":\"13567502346\"},{\"id\":\"30cb3b23\",\"PHONENUM\":\"13524443834\"},{\"id\":\"ed816431\",\"PHONENUM\":\"13516474450\"},{\"id\":\"59932307\",\"PHONENUM\":\"13570176551\"},{\"id\":\"b5dbfe45\",\"PHONENUM\":\"13521544196\"},{\"id\":\"0adb790d\",\"PHONENUM\":\"13551527972\"},{\"id\":\"2dcf8b8a\",\"PHONENUM\":\"13572294061\"},{\"id\":\"361946da\",\"PHONENUM\":\"13596208986\"},{\"id\":\"8703ece1\",\"PHONENUM\":\"13531071800\"},{\"id\":\"b05d43ea\",\"PHONENUM\":\"13582033994\"},{\"id\":\"8ab6e00a\",\"PHONENUM\":\"13522897695\"},{\"id\":\"d32ba9cf\",\"PHONENUM\":\"13539741125\"},{\"id\":\"c7992fa7\",\"PHONENUM\":\"13571717575\"},{\"id\":\"d5062f7a\",\"PHONENUM\":\"13576440271\"},{\"id\":\"2735a9ef\",\"PHONENUM\":\"13580062747\"},{\"id\":\"094520ad\",\"PHONENUM\":\"13582117917\"},{\"id\":\"9d52a4af\",\"PHONENUM\":\"13561171176\"},{\"id\":\"35bef4a0\",\"PHONENUM\":\"13566496778\"},{\"id\":\"f1451009\",\"PHONENUM\":\"13594976753\"},{\"id\":\"54565972\",\"PHONENUM\":\"13550319902\"},{\"id\":\"16ae743c\",\"PHONENUM\":\"13523804909\"},{\"id\":\"ad36eab5\",\"PHONENUM\":\"13558945268\"},{\"id\":\"7e8e0a53\",\"PHONENUM\":\"13512439364\"},{\"id\":\"05c18d1c\",\"PHONENUM\":\"13584672903\"},{\"id\":\"0e85039d\",\"PHONENUM\":\"13577178813\"},{\"id\":\"857ea3d8\",\"PHONENUM\":\"13562961547\"},{\"id\":\"c8e6d912\",\"PHONENUM\":\"13535085195\"},{\"id\":\"0cdb5915\",\"PHONENUM\":\"13533191437\"},{\"id\":\"043e19f8\",\"PHONENUM\":\"13589602330\"},{\"id\":\"e39d4ebd\",\"PHONENUM\":\"13588851477\"},{\"id\":\"b4649d24\",\"PHONENUM\":\"13533361892\"},{\"id\":\"ed469f2d\",\"PHONENUM\":\"13584547435\"},{\"id\":\"3db9f73b\",\"PHONENUM\":\"13560194813\"},{\"id\":\"166dc867\",\"PHONENUM\":\"13550536694\"},{\"id\":\"d1448eca\",\"PHONENUM\":\"13520756328\"},{\"id\":\"da2a2797\",\"PHONENUM\":\"13526642367\"},{\"id\":\"cf277072\",\"PHONENUM\":\"13592059787\"},{\"id\":\"a0a9bce1\",\"PHONENUM\":\"13585206596\"},{\"id\":\"6ab42feb\",\"PHONENUM\":\"13520451530\"},{\"id\":\"899b9c0f\",\"PHONENUM\":\"13550947554\"},{\"id\":\"b26002fb\",\"PHONENUM\":\"13543188842\"},{\"id\":\"5132f103\",\"PHONENUM\":\"13521240485\"},{\"id\":\"87a7f7b9\",\"PHONENUM\":\"13518309411\"},{\"id\":\"b2de6f3c\",\"PHONENUM\":\"13532012979\"},{\"id\":\"38904826\",\"PHONENUM\":\"13559321575\"},{\"id\":\"5fd44096\",\"PHONENUM\":\"13540297748\"},{\"id\":\"155b7026\",\"PHONENUM\":\"13562146635\"},{\"id\":\"95eafb0e\",\"PHONENUM\":\"13511987595\"},{\"id\":\"f9bdfaca\",\"PHONENUM\":\"13566289724\"},{\"id\":\"0d71ed23\",\"PHONENUM\":\"13587491749\"},{\"id\":\"b1ed3ee1\",\"PHONENUM\":\"13546573389\"},{\"id\":\"93a5b313\",\"PHONENUM\":\"13559383154\"},{\"id\":\"f081db04\",\"PHONENUM\":\"13592868574\"}]}}\n",
                "{\"result\":{\"error_code\":0,\"error_desc\":\"success\"},\"data\":{\"total\":756,\"docs\":[{\"id\":\"cadece44\",\"PHONENUM\":\"13531789653\"},{\"id\":\"8f12018a\",\"PHONENUM\":\"13531283293\"},{\"id\":\"a4486a77\",\"PHONENUM\":\"13524720099\"},{\"id\":\"22df135e\",\"PHONENUM\":\"13530633266\"},{\"id\":\"f5725ddb\",\"PHONENUM\":\"13560082035\"},{\"id\":\"f48252de\",\"PHONENUM\":\"13575336742\"},{\"id\":\"43d6ace8\",\"PHONENUM\":\"13537694570\"},{\"id\":\"05832ac8\",\"PHONENUM\":\"13590130465\"},{\"id\":\"c3d32465\",\"PHONENUM\":\"13587391280\"},{\"id\":\"01071222\",\"PHONENUM\":\"13536901376\"},{\"id\":\"4cda6962\",\"PHONENUM\":\"13579725024\"},{\"id\":\"115a430f\",\"PHONENUM\":\"13574820255\"},{\"id\":\"07fbaedc\",\"PHONENUM\":\"13510817532\"},{\"id\":\"9f59e58c\",\"PHONENUM\":\"13545520784\"},{\"id\":\"ffc39861\",\"PHONENUM\":\"13531152251\"},{\"id\":\"216e0e66\",\"PHONENUM\":\"13520483273\"},{\"id\":\"f4a91c90\",\"PHONENUM\":\"13573408791\"},{\"id\":\"95d1c3dd\",\"PHONENUM\":\"13527269745\"},{\"id\":\"34ccd557\",\"PHONENUM\":\"13569287644\"},{\"id\":\"8717dc18\",\"PHONENUM\":\"13518153533\"},{\"id\":\"13a2dc47\",\"PHONENUM\":\"13542193246\"},{\"id\":\"1ba957fa\",\"PHONENUM\":\"13546469178\"},{\"id\":\"da7e895e\",\"PHONENUM\":\"13589086118\"},{\"id\":\"4a8d5ab2\",\"PHONENUM\":\"13578827424\"},{\"id\":\"efd963fc\",\"PHONENUM\":\"13545676283\"},{\"id\":\"5f18b3c8\",\"PHONENUM\":\"13521410999\"},{\"id\":\"62cfb6e7\",\"PHONENUM\":\"13569488280\"},{\"id\":\"6a1822ec\",\"PHONENUM\":\"13585766089\"},{\"id\":\"8c7d45dc\",\"PHONENUM\":\"13575572789\"},{\"id\":\"e600abfb\",\"PHONENUM\":\"13535977563\"},{\"id\":\"533b3704\",\"PHONENUM\":\"13554385414\"},{\"id\":\"9237e33f\",\"PHONENUM\":\"13556394043\"},{\"id\":\"c36e3abe\",\"PHONENUM\":\"13519662451\"},{\"id\":\"a4e19e76\",\"PHONENUM\":\"13546321619\"},{\"id\":\"8feea1f0\",\"PHONENUM\":\"13544152332\"},{\"id\":\"064f0c1f\",\"PHONENUM\":\"13519998878\"},{\"id\":\"2309db3d\",\"PHONENUM\":\"13578550615\"},{\"id\":\"4d9d5394\",\"PHONENUM\":\"13586942846\"},{\"id\":\"83f1da55\",\"PHONENUM\":\"13536368856\"},{\"id\":\"743144d7\",\"PHONENUM\":\"13582723389\"},{\"id\":\"60aeca2a\",\"PHONENUM\":\"13533309130\"},{\"id\":\"5178ac07\",\"PHONENUM\":\"13569630204\"},{\"id\":\"4bd9b85f\",\"PHONENUM\":\"13512424753\"},{\"id\":\"a1938ec5\",\"PHONENUM\":\"13552900121\"},{\"id\":\"69e0e470\",\"PHONENUM\":\"13577432887\"},{\"id\":\"1c909d07\",\"PHONENUM\":\"13536033007\"},{\"id\":\"84824eb0\",\"PHONENUM\":\"13559500190\"},{\"id\":\"ce4fc146\",\"PHONENUM\":\"13563133010\"},{\"id\":\"99b45b97\",\"PHONENUM\":\"13542251982\"},{\"id\":\"add996d7\",\"PHONENUM\":\"13556653807\"},{\"id\":\"4a5e01e2\",\"PHONENUM\":\"13596237841\"},{\"id\":\"83421340\",\"PHONENUM\":\"13574653527\"},{\"id\":\"bce1a202\",\"PHONENUM\":\"13514757601\"},{\"id\":\"8339c0ab\",\"PHONENUM\":\"13534825101\"},{\"id\":\"af865134\",\"PHONENUM\":\"13559569933\"},{\"id\":\"8ca922da\",\"PHONENUM\":\"13564812577\"},{\"id\":\"34862ff0\",\"PHONENUM\":\"13546826493\"},{\"id\":\"f1cf4452\",\"PHONENUM\":\"13517961803\"},{\"id\":\"f986cebe\",\"PHONENUM\":\"13588086109\"},{\"id\":\"8a4a495e\",\"PHONENUM\":\"13524203851\"},{\"id\":\"cc291b59\",\"PHONENUM\":\"13549494763\"},{\"id\":\"022b2f47\",\"PHONENUM\":\"13527291691\"},{\"id\":\"4c9c4961\",\"PHONENUM\":\"13587131937\"},{\"id\":\"6f924681\",\"PHONENUM\":\"13562654428\"},{\"id\":\"e05d074e\",\"PHONENUM\":\"13552085960\"},{\"id\":\"5da9dfb0\",\"PHONENUM\":\"13592153401\"},{\"id\":\"fc31bff8\",\"PHONENUM\":\"13531340452\"},{\"id\":\"74f448a3\",\"PHONENUM\":\"13590690388\"},{\"id\":\"1126692f\",\"PHONENUM\":\"13594704060\"},{\"id\":\"28360480\",\"PHONENUM\":\"13561038919\"},{\"id\":\"3f169caf\",\"PHONENUM\":\"13528238227\"},{\"id\":\"6f1483b3\",\"PHONENUM\":\"13524666896\"},{\"id\":\"7bd28298\",\"PHONENUM\":\"13516414801\"},{\"id\":\"016de199\",\"PHONENUM\":\"13597245266\"},{\"id\":\"48d1b6ef\",\"PHONENUM\":\"13543541557\"},{\"id\":\"e09111c8\",\"PHONENUM\":\"13555713946\"},{\"id\":\"e156e2e1\",\"PHONENUM\":\"13555344110\"},{\"id\":\"d51ad0bb\",\"PHONENUM\":\"13585078771\"},{\"id\":\"6374135c\",\"PHONENUM\":\"13526793001\"},{\"id\":\"b7301587\",\"PHONENUM\":\"13558152014\"},{\"id\":\"01c5dca1\",\"PHONENUM\":\"13577447854\"},{\"id\":\"5bfa1789\",\"PHONENUM\":\"13518648900\"},{\"id\":\"c6a695db\",\"PHONENUM\":\"13574723374\"},{\"id\":\"cd950cb0\",\"PHONENUM\":\"13595855025\"},{\"id\":\"541f2b2e\",\"PHONENUM\":\"13523528767\"},{\"id\":\"b8de6a1a\",\"PHONENUM\":\"13599617326\"},{\"id\":\"fce6f332\",\"PHONENUM\":\"13527772493\"},{\"id\":\"55b76575\",\"PHONENUM\":\"13533283562\"},{\"id\":\"d5386bf3\",\"PHONENUM\":\"13510005575\"},{\"id\":\"a1d2bd09\",\"PHONENUM\":\"13531687714\"},{\"id\":\"30112533\",\"PHONENUM\":\"13568362424\"},{\"id\":\"e7e7f69d\",\"PHONENUM\":\"13592922350\"},{\"id\":\"91b6fb69\",\"PHONENUM\":\"13543785451\"},{\"id\":\"0a53f4f2\",\"PHONENUM\":\"13580856385\"},{\"id\":\"ebad0d1f\",\"PHONENUM\":\"13580621800\"},{\"id\":\"15b836db\",\"PHONENUM\":\"13559368882\"},{\"id\":\"1450fe47\",\"PHONENUM\":\"13513218532\"},{\"id\":\"a4a309da\",\"PHONENUM\":\"13515941853\"},{\"id\":\"e6231791\",\"PHONENUM\":\"13548870012\"},{\"id\":\"36384ba3\",\"PHONENUM\":\"13584075703\"}]}}\n",
                "{\"result\":{\"error_code\":0,\"error_desc\":\"success\"},\"data\":{\"total\":756,\"docs\":[{\"id\":\"047743f4\",\"PHONENUM\":\"13598525725\"},{\"id\":\"5f631488\",\"PHONENUM\":\"13558839343\"},{\"id\":\"e5b25edc\",\"PHONENUM\":\"13583867764\"},{\"id\":\"6844948a\",\"PHONENUM\":\"13530276384\"},{\"id\":\"672f2d0c\",\"PHONENUM\":\"13574503130\"},{\"id\":\"378b6324\",\"PHONENUM\":\"13589994725\"},{\"id\":\"d311f526\",\"PHONENUM\":\"13541823548\"},{\"id\":\"0a86d35e\",\"PHONENUM\":\"13542622117\"},{\"id\":\"402af753\",\"PHONENUM\":\"13567931591\"},{\"id\":\"19cabe31\",\"PHONENUM\":\"13589363894\"},{\"id\":\"4e87e4af\",\"PHONENUM\":\"13577691313\"},{\"id\":\"89339bc6\",\"PHONENUM\":\"13540315387\"},{\"id\":\"7ad928f2\",\"PHONENUM\":\"13525252072\"},{\"id\":\"2d7a67c3\",\"PHONENUM\":\"13586242798\"},{\"id\":\"087b0468\",\"PHONENUM\":\"13545676417\"},{\"id\":\"c5179535\",\"PHONENUM\":\"13573050907\"},{\"id\":\"8f03889f\",\"PHONENUM\":\"13543428010\"},{\"id\":\"b0dd20a3\",\"PHONENUM\":\"13575410834\"},{\"id\":\"194a7101\",\"PHONENUM\":\"13556365627\"},{\"id\":\"33eb7c41\",\"PHONENUM\":\"13570154350\"},{\"id\":\"858624a2\",\"PHONENUM\":\"13583819986\"},{\"id\":\"3171ccf4\",\"PHONENUM\":\"13566780394\"},{\"id\":\"9a3343c2\",\"PHONENUM\":\"13553992244\"},{\"id\":\"770503bc\",\"PHONENUM\":\"13568986135\"},{\"id\":\"1a8b59b7\",\"PHONENUM\":\"13597543358\"},{\"id\":\"ae95d9a8\",\"PHONENUM\":\"13590911249\"},{\"id\":\"b2dc4352\",\"PHONENUM\":\"13573831363\"},{\"id\":\"3b3b5c57\",\"PHONENUM\":\"13541790141\"},{\"id\":\"34fcc061\",\"PHONENUM\":\"13540685906\"},{\"id\":\"c282b0ea\",\"PHONENUM\":\"13586845064\"},{\"id\":\"51f36a91\",\"PHONENUM\":\"13550819825\"},{\"id\":\"77048d66\",\"PHONENUM\":\"13595655510\"},{\"id\":\"6abe80a9\",\"PHONENUM\":\"13581177407\"},{\"id\":\"ae9d3550\",\"PHONENUM\":\"13583416310\"},{\"id\":\"40a43f63\",\"PHONENUM\":\"13571235306\"},{\"id\":\"37522569\",\"PHONENUM\":\"13550952980\"},{\"id\":\"79964788\",\"PHONENUM\":\"13574171679\"},{\"id\":\"e1c84f61\",\"PHONENUM\":\"13553458359\"},{\"id\":\"be63c1aa\",\"PHONENUM\":\"13552931049\"},{\"id\":\"8b7be6b8\",\"PHONENUM\":\"13591237838\"},{\"id\":\"fa335e00\",\"PHONENUM\":\"13586052825\"},{\"id\":\"32465fa8\",\"PHONENUM\":\"13587596298\"},{\"id\":\"65a56aa4\",\"PHONENUM\":\"13518278280\"},{\"id\":\"6166574c\",\"PHONENUM\":\"13568012068\"},{\"id\":\"b67383cd\",\"PHONENUM\":\"13523045613\"},{\"id\":\"5dc678ac\",\"PHONENUM\":\"13515251890\"},{\"id\":\"0290db11\",\"PHONENUM\":\"13529667370\"},{\"id\":\"1cc53e19\",\"PHONENUM\":\"13538317507\"},{\"id\":\"9d1849c0\",\"PHONENUM\":\"13565859860\"},{\"id\":\"b251d2b6\",\"PHONENUM\":\"13520997640\"},{\"id\":\"aaafc434\",\"PHONENUM\":\"13544061148\"},{\"id\":\"46e531a6\",\"PHONENUM\":\"13556222876\"},{\"id\":\"258efe1d\",\"PHONENUM\":\"13564715798\"},{\"id\":\"e24feb10\",\"PHONENUM\":\"13513324464\"},{\"id\":\"215071ca\",\"PHONENUM\":\"13561632880\"},{\"id\":\"1dd4c9e9\",\"PHONENUM\":\"13536215770\"},{\"id\":\"f3296a66\",\"PHONENUM\":\"13594943178\"},{\"id\":\"d2914f4a\",\"PHONENUM\":\"13590922923\"},{\"id\":\"ab25a465\",\"PHONENUM\":\"13544386375\"},{\"id\":\"681b4f47\",\"PHONENUM\":\"13556504577\"},{\"id\":\"5bab4f86\",\"PHONENUM\":\"13592752565\"},{\"id\":\"c8b4771a\",\"PHONENUM\":\"13514465991\"},{\"id\":\"b21a32c1\",\"PHONENUM\":\"13575476213\"},{\"id\":\"d75cae57\",\"PHONENUM\":\"13516705241\"},{\"id\":\"4b362469\",\"PHONENUM\":\"13544283736\"},{\"id\":\"ac4a7bc4\",\"PHONENUM\":\"13538970449\"},{\"id\":\"bf394502\",\"PHONENUM\":\"13573786055\"},{\"id\":\"77dd1023\",\"PHONENUM\":\"13520441191\"},{\"id\":\"ab1ff6d1\",\"PHONENUM\":\"13582192672\"},{\"id\":\"8600cd08\",\"PHONENUM\":\"13527713871\"},{\"id\":\"bc4cf7cc\",\"PHONENUM\":\"13583923734\"},{\"id\":\"4c3c6283\",\"PHONENUM\":\"13564471493\"},{\"id\":\"2cb7dfab\",\"PHONENUM\":\"13540305789\"},{\"id\":\"3151a656\",\"PHONENUM\":\"13546658889\"},{\"id\":\"e385e80b\",\"PHONENUM\":\"13577245303\"},{\"id\":\"f0058042\",\"PHONENUM\":\"13577547908\"},{\"id\":\"962f275b\",\"PHONENUM\":\"13579116538\"},{\"id\":\"b1e21a98\",\"PHONENUM\":\"13594103774\"},{\"id\":\"379769ff\",\"PHONENUM\":\"13598974657\"},{\"id\":\"fab086e0\",\"PHONENUM\":\"13532856202\"},{\"id\":\"82b8c4db\",\"PHONENUM\":\"13598532026\"},{\"id\":\"63a7a8c8\",\"PHONENUM\":\"13515124164\"},{\"id\":\"0cec8ca0\",\"PHONENUM\":\"13516060943\"},{\"id\":\"509742f1\",\"PHONENUM\":\"13550115343\"},{\"id\":\"e68441d8\",\"PHONENUM\":\"13597557626\"},{\"id\":\"a5ff4556\",\"PHONENUM\":\"13579760760\"},{\"id\":\"c5f1a270\",\"PHONENUM\":\"13589576056\"},{\"id\":\"6754446c\",\"PHONENUM\":\"13566863188\"},{\"id\":\"553c269d\",\"PHONENUM\":\"13580796499\"},{\"id\":\"f91a0843\",\"PHONENUM\":\"13590493789\"},{\"id\":\"ddf1c14b\",\"PHONENUM\":\"13533560535\"},{\"id\":\"b16b9faf\",\"PHONENUM\":\"13587644155\"},{\"id\":\"ee9058a1\",\"PHONENUM\":\"13583459591\"},{\"id\":\"40d2d584\",\"PHONENUM\":\"13531552887\"},{\"id\":\"0d9dce0c\",\"PHONENUM\":\"13514494709\"},{\"id\":\"7642472e\",\"PHONENUM\":\"13525233531\"},{\"id\":\"4b11df02\",\"PHONENUM\":\"13590512047\"},{\"id\":\"8ec72512\",\"PHONENUM\":\"13562219924\"},{\"id\":\"b92e50d8\",\"PHONENUM\":\"13536857674\"},{\"id\":\"c8bcd989\",\"PHONENUM\":\"13553524135\"}]}}\n",
                "{\"result\":{\"error_code\":0,\"error_desc\":\"success\"},\"data\":{\"total\":756,\"docs\":[{\"id\":\"c7888787\",\"PHONENUM\":\"13534519806\"},{\"id\":\"fee2a900\",\"PHONENUM\":\"13523151049\"},{\"id\":\"53b2794b\",\"PHONENUM\":\"13578085988\"},{\"id\":\"9b64ddd3\",\"PHONENUM\":\"13528550194\"},{\"id\":\"411f959a\",\"PHONENUM\":\"13571140361\"},{\"id\":\"781fde9a\",\"PHONENUM\":\"13556781636\"},{\"id\":\"c2d54a38\",\"PHONENUM\":\"13564966392\"},{\"id\":\"d9a120da\",\"PHONENUM\":\"13572152229\"},{\"id\":\"6be6c53b\",\"PHONENUM\":\"13585039569\"},{\"id\":\"8cc718cb\",\"PHONENUM\":\"13542671499\"},{\"id\":\"802ae99d\",\"PHONENUM\":\"13563794839\"},{\"id\":\"c74c3bde\",\"PHONENUM\":\"13588600577\"},{\"id\":\"0755d53b\",\"PHONENUM\":\"13597374816\"},{\"id\":\"7d565d0c\",\"PHONENUM\":\"13597609072\"},{\"id\":\"feb076bd\",\"PHONENUM\":\"13575160631\"},{\"id\":\"6f042db1\",\"PHONENUM\":\"13526189412\"},{\"id\":\"1c9543ef\",\"PHONENUM\":\"13556917104\"},{\"id\":\"1118d7f1\",\"PHONENUM\":\"13546440117\"},{\"id\":\"630367dc\",\"PHONENUM\":\"13550299449\"},{\"id\":\"56797c69\",\"PHONENUM\":\"13583552059\"},{\"id\":\"68874ec4\",\"PHONENUM\":\"13564864329\"},{\"id\":\"349e1ee8\",\"PHONENUM\":\"13586793924\"},{\"id\":\"4563ec20\",\"PHONENUM\":\"13576189755\"},{\"id\":\"35a6ecda\",\"PHONENUM\":\"13514213229\"},{\"id\":\"94b67eaf\",\"PHONENUM\":\"13519411489\"},{\"id\":\"16b2da2b\",\"PHONENUM\":\"13577883757\"},{\"id\":\"4437580d\",\"PHONENUM\":\"13566471382\"},{\"id\":\"6fb56cc3\",\"PHONENUM\":\"13543215958\"},{\"id\":\"cf854057\",\"PHONENUM\":\"13599431424\"},{\"id\":\"6d8039fe\",\"PHONENUM\":\"13579599472\"},{\"id\":\"56edbf49\",\"PHONENUM\":\"13544518664\"},{\"id\":\"19adb555\",\"PHONENUM\":\"13535211861\"},{\"id\":\"9be6bda9\",\"PHONENUM\":\"13513479958\"},{\"id\":\"2f214feb\",\"PHONENUM\":\"13527528685\"},{\"id\":\"1722f99b\",\"PHONENUM\":\"13521727515\"},{\"id\":\"13a17882\",\"PHONENUM\":\"13561720039\"},{\"id\":\"bc408415\",\"PHONENUM\":\"13584339570\"},{\"id\":\"0f70a68b\",\"PHONENUM\":\"13530771842\"},{\"id\":\"dafbd551\",\"PHONENUM\":\"13526296109\"},{\"id\":\"b2564d2c\",\"PHONENUM\":\"13582214808\"},{\"id\":\"176c872a\",\"PHONENUM\":\"13541621189\"},{\"id\":\"9f582c50\",\"PHONENUM\":\"13581908224\"},{\"id\":\"8d99ab45\",\"PHONENUM\":\"13551652291\"},{\"id\":\"a6e22c5f\",\"PHONENUM\":\"13588179344\"},{\"id\":\"029e5a41\",\"PHONENUM\":\"13552067283\"},{\"id\":\"83fe21db\",\"PHONENUM\":\"13586388762\"},{\"id\":\"8014a8bf\",\"PHONENUM\":\"13548154093\"},{\"id\":\"d3fb2829\",\"PHONENUM\":\"13530236313\"},{\"id\":\"8f3be99e\",\"PHONENUM\":\"13558328887\"},{\"id\":\"79b17b5f\",\"PHONENUM\":\"13542257032\"},{\"id\":\"5a2a4cf3\",\"PHONENUM\":\"13587485478\"},{\"id\":\"919a7062\",\"PHONENUM\":\"13533191276\"},{\"id\":\"701857f6\",\"PHONENUM\":\"13585520630\"},{\"id\":\"eb5ec4c6\",\"PHONENUM\":\"13578466230\"},{\"id\":\"bdd76df6\",\"PHONENUM\":\"13597145204\"},{\"id\":\"3db094dd\",\"PHONENUM\":\"13569982174\"},{\"id\":\"cdfbf83b\",\"PHONENUM\":\"13528900635\"},{\"id\":\"a9eb5a60\",\"PHONENUM\":\"13568777508\"},{\"id\":\"61f5948c\",\"PHONENUM\":\"13584891044\"},{\"id\":\"0142324e\",\"PHONENUM\":\"13573656317\"},{\"id\":\"0cc25481\",\"PHONENUM\":\"13536075581\"},{\"id\":\"eef4e736\",\"PHONENUM\":\"13597417296\"},{\"id\":\"4ab84dd1\",\"PHONENUM\":\"13544451292\"},{\"id\":\"3c0c167a\",\"PHONENUM\":\"13512716076\"},{\"id\":\"bdaf16c8\",\"PHONENUM\":\"13515524162\"},{\"id\":\"85fac986\",\"PHONENUM\":\"13585300621\"},{\"id\":\"31c81351\",\"PHONENUM\":\"13522668584\"},{\"id\":\"bbf1d9d7\",\"PHONENUM\":\"13531064063\"},{\"id\":\"4d5c7a3a\",\"PHONENUM\":\"13563363805\"},{\"id\":\"5f3d07b6\",\"PHONENUM\":\"13568941456\"},{\"id\":\"af598843\",\"PHONENUM\":\"13539613528\"},{\"id\":\"03b55b44\",\"PHONENUM\":\"13537654714\"},{\"id\":\"6af2f0e6\",\"PHONENUM\":\"13541572581\"},{\"id\":\"5874e675\",\"PHONENUM\":\"13591061581\"},{\"id\":\"62e2c964\",\"PHONENUM\":\"13591426970\"},{\"id\":\"5b99aa5d\",\"PHONENUM\":\"13554403376\"},{\"id\":\"b7489a58\",\"PHONENUM\":\"13577362550\"},{\"id\":\"6bb72dc4\",\"PHONENUM\":\"13571764146\"},{\"id\":\"92d90277\",\"PHONENUM\":\"13558543247\"},{\"id\":\"aee046cf\",\"PHONENUM\":\"13511735977\"},{\"id\":\"7dacc690\",\"PHONENUM\":\"13521665592\"},{\"id\":\"20e992d5\",\"PHONENUM\":\"13576521100\"},{\"id\":\"22175883\",\"PHONENUM\":\"13542424450\"},{\"id\":\"6fdb39b1\",\"PHONENUM\":\"13552804199\"},{\"id\":\"0e9ba6ce\",\"PHONENUM\":\"13538842500\"},{\"id\":\"5d326ff6\",\"PHONENUM\":\"13569925854\"},{\"id\":\"d473602e\",\"PHONENUM\":\"13551820473\"},{\"id\":\"897d87fe\",\"PHONENUM\":\"13565856793\"},{\"id\":\"a4cc336e\",\"PHONENUM\":\"13566082641\"},{\"id\":\"5f994a0e\",\"PHONENUM\":\"13514067084\"},{\"id\":\"d3d9cdff\",\"PHONENUM\":\"13587676781\"},{\"id\":\"411b3677\",\"PHONENUM\":\"13512115139\"},{\"id\":\"9cdf42c5\",\"PHONENUM\":\"13581312672\"},{\"id\":\"c73095bb\",\"PHONENUM\":\"13575147664\"},{\"id\":\"42464d16\",\"PHONENUM\":\"13589401985\"},{\"id\":\"fba11c4b\",\"PHONENUM\":\"13564215262\"},{\"id\":\"679763e4\",\"PHONENUM\":\"13557009113\"},{\"id\":\"46b81df2\",\"PHONENUM\":\"13570098634\"},{\"id\":\"66592968\",\"PHONENUM\":\"13582423552\"},{\"id\":\"e20b099b\",\"PHONENUM\":\"13572564698\"}]}}\n",
                "{\"result\":{\"error_code\":0,\"error_desc\":\"success\"},\"data\":{\"total\":756,\"docs\":[{\"id\":\"7ae93fe0\",\"PHONENUM\":\"13574017984\"},{\"id\":\"144fc897\",\"PHONENUM\":\"13555963555\"},{\"id\":\"b317e8d0\",\"PHONENUM\":\"13584587893\"},{\"id\":\"dc9b6433\",\"PHONENUM\":\"13547000335\"},{\"id\":\"fa6fc8bd\",\"PHONENUM\":\"13535361245\"},{\"id\":\"827eb676\",\"PHONENUM\":\"13549503319\"},{\"id\":\"5a5be0f6\",\"PHONENUM\":\"13550847702\"},{\"id\":\"707cd1e9\",\"PHONENUM\":\"13533316821\"},{\"id\":\"37e0321e\",\"PHONENUM\":\"13562620845\"},{\"id\":\"f4eb43e3\",\"PHONENUM\":\"13576795666\"},{\"id\":\"69f13cdc\",\"PHONENUM\":\"13530298772\"},{\"id\":\"423a30bc\",\"PHONENUM\":\"13581866874\"},{\"id\":\"d2a04c4f\",\"PHONENUM\":\"13564296972\"},{\"id\":\"2c33ac34\",\"PHONENUM\":\"13518312503\"},{\"id\":\"9e7e332b\",\"PHONENUM\":\"13546894085\"},{\"id\":\"ed87704f\",\"PHONENUM\":\"13528376005\"},{\"id\":\"2264ee58\",\"PHONENUM\":\"13563399968\"},{\"id\":\"3baabcfc\",\"PHONENUM\":\"13577259795\"},{\"id\":\"9852a0fd\",\"PHONENUM\":\"13543793422\"},{\"id\":\"31e05792\",\"PHONENUM\":\"13556720547\"},{\"id\":\"768ad99d\",\"PHONENUM\":\"13593493743\"},{\"id\":\"11d1a2b4\",\"PHONENUM\":\"13529811120\"},{\"id\":\"e9b3a4c5\",\"PHONENUM\":\"13510455041\"},{\"id\":\"68b8d54d\",\"PHONENUM\":\"13591598026\"},{\"id\":\"d0cc5380\",\"PHONENUM\":\"13513077271\"},{\"id\":\"0e5770b7\",\"PHONENUM\":\"13529071751\"},{\"id\":\"cc92b2d5\",\"PHONENUM\":\"13576030920\"},{\"id\":\"fa7f2adb\",\"PHONENUM\":\"13550134565\"},{\"id\":\"ab998779\",\"PHONENUM\":\"13597250880\"},{\"id\":\"6c4d11f1\",\"PHONENUM\":\"13531484705\"},{\"id\":\"c3e136a5\",\"PHONENUM\":\"13528545486\"},{\"id\":\"703752c1\",\"PHONENUM\":\"13562895170\"},{\"id\":\"e8177448\",\"PHONENUM\":\"13517156471\"},{\"id\":\"5cbfc56f\",\"PHONENUM\":\"13548860101\"},{\"id\":\"d09d2c57\",\"PHONENUM\":\"13585792206\"},{\"id\":\"965cffae\",\"PHONENUM\":\"13556614843\"},{\"id\":\"c1ff7dd6\",\"PHONENUM\":\"13514923426\"},{\"id\":\"72a5f230\",\"PHONENUM\":\"13533544892\"},{\"id\":\"e76a0682\",\"PHONENUM\":\"13597947260\"},{\"id\":\"88dc42dc\",\"PHONENUM\":\"13550019097\"},{\"id\":\"5f0b346d\",\"PHONENUM\":\"13561027423\"},{\"id\":\"d39e32b3\",\"PHONENUM\":\"13591698004\"},{\"id\":\"5fea76e1\",\"PHONENUM\":\"13516718329\"},{\"id\":\"306b4c22\",\"PHONENUM\":\"13513723176\"},{\"id\":\"f9b56b36\",\"PHONENUM\":\"13574264549\"},{\"id\":\"878fa243\",\"PHONENUM\":\"13541422270\"},{\"id\":\"7eb2d105\",\"PHONENUM\":\"13582012824\"},{\"id\":\"ccd314cd\",\"PHONENUM\":\"13539221411\"},{\"id\":\"8f827f11\",\"PHONENUM\":\"13592831258\"},{\"id\":\"9ca1f498\",\"PHONENUM\":\"13596681729\"},{\"id\":\"8c53b54d\",\"PHONENUM\":\"13594656122\"},{\"id\":\"23b65949\",\"PHONENUM\":\"13593031022\"},{\"id\":\"496e2114\",\"PHONENUM\":\"13583435362\"},{\"id\":\"274c2367\",\"PHONENUM\":\"13582923478\"},{\"id\":\"4c0f6ac8\",\"PHONENUM\":\"13514461201\"},{\"id\":\"94d85a2d\",\"PHONENUM\":\"13532052077\"},{\"id\":\"bd4e54a9\",\"PHONENUM\":\"13588331852\"},{\"id\":\"bf07f667\",\"PHONENUM\":\"13590190206\"},{\"id\":\"f121fdf6\",\"PHONENUM\":\"13568251253\"},{\"id\":\"31599a43\",\"PHONENUM\":\"13584020373\"},{\"id\":\"ef72f783\",\"PHONENUM\":\"13520685979\"},{\"id\":\"5e07c0ad\",\"PHONENUM\":\"13579012226\"},{\"id\":\"a9bcea18\",\"PHONENUM\":\"13556181081\"},{\"id\":\"f925d100\",\"PHONENUM\":\"13550103886\"},{\"id\":\"2e6e4a66\",\"PHONENUM\":\"13522651493\"},{\"id\":\"e0afb315\",\"PHONENUM\":\"13537333526\"},{\"id\":\"2618ec34\",\"PHONENUM\":\"13539173738\"},{\"id\":\"6dce8795\",\"PHONENUM\":\"13585793747\"},{\"id\":\"3609c9e4\",\"PHONENUM\":\"13570288649\"},{\"id\":\"51a0a464\",\"PHONENUM\":\"13535093095\"},{\"id\":\"abee1eca\",\"PHONENUM\":\"13594930446\"},{\"id\":\"f583acfa\",\"PHONENUM\":\"13589350429\"},{\"id\":\"758c7a0a\",\"PHONENUM\":\"13558636007\"},{\"id\":\"d5498c1e\",\"PHONENUM\":\"13585280319\"},{\"id\":\"06d4b963\",\"PHONENUM\":\"13596583142\"},{\"id\":\"a5154f80\",\"PHONENUM\":\"13529763711\"},{\"id\":\"02111af1\",\"PHONENUM\":\"13539073222\"},{\"id\":\"63b70bf3\",\"PHONENUM\":\"13552373882\"},{\"id\":\"6474cd75\",\"PHONENUM\":\"13585518531\"},{\"id\":\"32b37445\",\"PHONENUM\":\"13597798853\"},{\"id\":\"dd12d84d\",\"PHONENUM\":\"13517953185\"},{\"id\":\"85f4ac2f\",\"PHONENUM\":\"13578972809\"},{\"id\":\"6d6b9042\",\"PHONENUM\":\"13593636024\"},{\"id\":\"8a78f1bc\",\"PHONENUM\":\"13550127604\"},{\"id\":\"c7c79da7\",\"PHONENUM\":\"13578740979\"},{\"id\":\"2f25788a\",\"PHONENUM\":\"13592494975\"},{\"id\":\"4a463759\",\"PHONENUM\":\"13563133588\"},{\"id\":\"27588dcc\",\"PHONENUM\":\"13587528536\"},{\"id\":\"8269d950\",\"PHONENUM\":\"13539247358\"},{\"id\":\"5b23a6fd\",\"PHONENUM\":\"13553437636\"},{\"id\":\"3a20389d\",\"PHONENUM\":\"13542072419\"},{\"id\":\"33d2e368\",\"PHONENUM\":\"13580863321\"},{\"id\":\"b28e348c\",\"PHONENUM\":\"13565529914\"},{\"id\":\"06a90836\",\"PHONENUM\":\"13583358499\"},{\"id\":\"092bbbcf\",\"PHONENUM\":\"13524025575\"},{\"id\":\"290ea5c7\",\"PHONENUM\":\"13526981990\"},{\"id\":\"6178620d\",\"PHONENUM\":\"13594303994\"},{\"id\":\"e6245aba\",\"PHONENUM\":\"13576293022\"},{\"id\":\"ef449607\",\"PHONENUM\":\"13524405183\"},{\"id\":\"745469f4\",\"PHONENUM\":\"13510769526\"}]}}\n"};
//        Set<String> phones = new HashSet<>();
//        for (String i : data) {
//            ArrayNode arrayNode = (ArrayNode) new ObjectMapper().readTree(i).get("data").get("docs");
//
//            arrayNode.forEach(node -> {
//                if (phones.contains(node.get("PHONENUM").asText()))
//                    System.out.println(node.get("PHONENUM").asText());
//                else
//                    phones.add(node.get("PHONENUM").asText());
//            });
//        }
        System.out.println(new ObjectMapper().readTree(data[0]).toString());
    }

    class TestSendData extends Thread {

        public void run() {
            try {
                PutService putService = HbaseServiceManager.getInstance().createPutService("SITEPOSITION");
                for (int j = 0; j < 1; j++) {
                    List<Put> puts = new ArrayList<>();
                    for (int i = 0; i < 1000; i++) {
                        puts.add(generateData());
                    }
                    putService.execute("SITEPOSITION", htable -> {
                        htable.put(puts);
                        return true;
                    });

                }
            } catch (Exception e) {

            }
        }

        RandomDataGenerator random = new RandomDataGenerator();

        String[] areas = new String[]{"A0A211024", "A0A211017", "A0A211011", "A0A211020", "A0A211027", "A0A211004", "A0A211013", "A0A211007",
                "A0A211015", "A0A211018", "A0A211010", "A0A211002", "A0A211025", "A0A211016", "A0A211023", "A0A211022", "A0A211009", "A0A211003", "A0A211008",
                "A0A211005", "A0A211012", "A0A211001", "A0A211006", "A0A211026", "A0A211019", "A0A211014", "A0A211021"};

        public Put generateData() {
            Put put = new Put(random.nextHexString(8).getBytes());
            put.addColumn("INFO".getBytes(), "SECURITY_AREA".getBytes(), areas[random.nextInt(0, areas.length - 1)].getBytes());
            put.addColumn("INFO".getBytes(), "LAC".getBytes(), "37194".getBytes());
            put.addColumn("0".getBytes(), "EPARCHY_ID".getBytes(), "61402".getBytes());
            put.addColumn("INFO".getBytes(), "CELL".getBytes(), "61402".getBytes());

            put.addColumn("0".getBytes(), "PHONENUM".getBytes(), ("135" + random.nextLong(10000000l, 99999999l)).getBytes());
            return put;
        }
    }
}