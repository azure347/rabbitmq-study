package cn.whao.rabbitmq.study.topic;

import cn.whao.rabbitmq.study.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * 生产者，模拟为商品服务
 */
public class Send {
    private final static String EXCHANGE_NAME = "topic_exchange_test";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        // 声明exchange，指定类型为topic
        /**
         * 声明exchange，指定类型为topic
         * 参数1：exchange 交换机名称
         * 参数2：type 交换机类型
         * 参数3：durable 是否持久化
         */
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);

        // 消息内容
        String message = "更新商品 : id = 1001";
        // 发送消息，并且指定routing key 为：insert ,代表新增商品
        channel.basicPublish(EXCHANGE_NAME, "item.update", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println(" [商品服务：] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}