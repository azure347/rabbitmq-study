package cn.whao.rabbitmq.study.simple;

import cn.whao.rabbitmq.study.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author wanghao
 * @description 消费者  - 手动ACK
 * @create 2025-02-27 16:29
 */
public class Recv2 {

    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            /**
             *
             * @param consumerTag 消费者标签，在channel.basicConsume()去指定
             * @param envelope 消息包内容，可以从中获取消息id，消息routingkey，交换机，消息和重转标记（收到消息失败后是否需要重新发送）
             * @param properties 消息属性
             * @param body 消息
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                // body 即消息体
                String msg = new String(body);
//                int i = 1/0;
                System.out.println(" [x] received : " + msg + "!");
                // 手动进行ACK
                /**
                 * 参数明细
                 * 1、deliveryTag：用来标识消息的id
                 * 2、multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
                 */
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 监听队列，第二个参数：false 手动进行ACK
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
