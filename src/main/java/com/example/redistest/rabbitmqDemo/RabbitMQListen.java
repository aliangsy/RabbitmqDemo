//package com.example.redistest.rabbitmqDemo;
//
//import cn.hutool.json.JSON;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Configuration
//@PropertySource("classpath:config.properties")
//public class RabbitMQListen implements ApplicationListener<ContextRefreshedEvent> {
//
//    /**
//     * 日志
//     */
//    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQListen.class);
//
//    /**
//     * Redis 模板
//     */
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//    /**
//     * 同步数据接口，将数据从Redis服务中持久化到MySQL数据库中
//     */
//
//    /**
//     * MQ的host地址
//     */
//    @Value("${mq.host}")
//    private String host;
//
//    /**
//     * MQ的端口
//     */
//    @Value("${mq.port}")
//    private int port;
//
//    /**
//     * MQ的登录名
//     */
//    @Value("${mq.name}")
//    private String name;
//
//    /**
//     * MQ的密码
//     */
//    @Value("${mq.password}")
//    private String password;
//
//    /**
//     * 监听原始业务数据的队列名
//     */
//    @Value("${business.orgData.queue}")
//    private String businessDataQueue;
//
//    /**
//     * 处理业务数据的线程数量
//     */
//    @Value("${business.collection.conn}")
//    private int businessCollectionConn;
//
//    /**
//     * 每个线程每次处理多少条数据
//     */
//    @Value("${business.getData.count}")
//    private int businessGetDataCount;
//
////    /**
////     * 同步指标数据到MySql中线程数量
////     */
////    @Value("${save.targetData.conn}")
////    private int saveTargetDataConn;
////
////    /**
////     * 每个线程每次同步多少条指标数据到数据库中
////     */
////    @Value("${save.targetData.count}")
////    private int saveTargetDataCount;
////
////    /**
////     * 同步原始数据到MySql中线程数量
////     */
////    @Value("${save.collectionData.conn}")
////    private int saveCollectionDataConn;
////
////    /**
////     * 每个线程每次同步多少条原始数据到数据库中
////     */
////    @Value("${save.collectionData.count}")
////    private int saveCollectionDataCount;
//
//    /**
//     * 资源数据同步模块监听的MQ队列名
//     */
//    @Value("${sync.resourceData.queue}")
//    private String syncResourceDataQueue;
//    /**
//     * 资源数据同步模块每次从MQ中取出多少条数据
//     */
//    @Value("${sync.resourceData.count}")
//    private int syncResourceDataCount;
//    /**
//     * list 统计
//     */
//    public static int listCount = 0;
//    /**
//     * 开始时间
//     */
//    private static long startTime = 0;
////    @Autowired
////    private CsService csService;
////    @Autowired
////    private DeviceService deviceService;
//    /**
//     * TODO 测试方法，用来记录测试时间以及测试的数据量，后续删除
//     *
//     * @param count
//     */
//    public static synchronized void addListCount(int count) {
//        if (listCount == 0 && count != 0) {
//            startTime = System.currentTimeMillis();
//            System.out.println("startTime : " + startTime);
//        }
//        listCount += count;
//        if (listCount == 200000) {
//            long endTime = System.currentTimeMillis();
//            System.out.println("endTime : " + endTime);
//            System.out.println("totalTime : " + (endTime - startTime));
//            listCount = 0;
//        }
//    }
//    /**
//     * 该方法会被spring在服务启动完成之后自动执行
//     * 利用该方法，可以实现在服务启动之后，自动调用我们需要的功能。
//     * 通过异步，则能实现自定义的各功能模块自动运行
//     *
//     * @param contextRefreshedEvent
//     */
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        try {
//            // 初始化MQ的工厂
//            RabbitMQUtil.initFactory2(host,port,name,password);
//            // 启用业务数据处理功能（从MQ拿原始数据---转换为指标数据---存入redis）
//            listenOrgData();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    private void listenOrgData() {
//        LOGGER.info("================= Mq监听功能启动成功 =================");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    // 通过线程池来控制该业务功能的线程数，具体线程数通过配置文件指定
//                    ExecutorService executorService = Executors.newFixedThreadPool(businessCollectionConn);
//                    while (true){
//                        executorService.execute(new Runnable() {
//                            public void run() {
//                                try {
//                                    RabbitMQUtil.initFactory(host,port);
//                                    // 调用消息接收方法，指定接收的队列以及本次获取的数据数量。队列名以及数据量由配置文件指定
//                                    List<String> orgDatas = RabbitMQUtil.receiveMessages(businessDataQueue, businessGetDataCount);
//                                    if (!orgDatas.isEmpty()){
//                                        System.out.println("监听到的数据为:"+orgDatas);
//                                        //进行数据处理给下层业务判断是否告警
//                                        for (int i =0 ;i<orgDatas.size();i++){
//                                            String orgData = orgDatas.get(i);
//                                            //拿到deviceId进行解析
//                                            /* JSONObject jsonObject = JSON.parseObject(orgData);*/
//                                            DeviceData data = JSON.parseObject(orgData, new TypeReference<DeviceData>() {});
//                                            System.out.println(data.getDeviceId());
//                                            String deviceId = data.getDeviceId();
//                                            String datas = data.getData();
//                                            CSCommon cs = new CSCommon();
//                                            Object commonmq = cs.commonmq(deviceId, datas);
//                                            System.out.println(commonmq);
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    LOGGER.error("数据处理失败", e);
//                                }
//                            }
//                        });
//                        // 每间隔300毫秒创建一个新的线程向MQ取数据//错
//                        //休眠15秒
//                        Thread.sleep(10000);
//                    }
//                } catch (Throwable e) {
//                    LOGGER.error("指标数据持久化功能异常，将于10秒后重新启动", e);
//                    try {
//                        Thread.sleep(10000);
//                    } catch (Exception e1) {
//                    }
//                }
//            }
//        }).start();
//    }
//}
