package common;

/**
 * @author xie.wei
 * @date created at 2021-11-15 17:43
 */
public interface Constant {

    String MQ_HOST = "127.0.0.1";

    String DIRECT_QUEUE = "direct";
    String WORKING_QUEUE = "test_work_queue";
    String FANOUT_EXCHANGE = "test_fanout_exchange";
    String DIRECT_EXCHANGE = "test_direct_exchange";
    String TOPIC_EXCHANGE = "test_topic_exchange";
}
