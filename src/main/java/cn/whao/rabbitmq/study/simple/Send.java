package cn.whao.rabbitmq.study.simple;

import cn.whao.rabbitmq.study.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author wanghao
 * @description 生产者
 * @create 2025-02-27 16:21
 */
public class Send {

    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道，使用通道才能完成消息相关的操作
        Channel channel = connection.createChannel();
        // 声明（创建）队列，必须声明队列才能够发送消息到指定的队列中
        /**
         * 参数明细
         * 1、queue 队列名称
         * 2、durable 是否持久化（true表示是，队列将在服务器重启时生存）
         * 3、exclusive 是否独占队列（创建者可以使用的私有队列，断开后自动删除）
         * 4、autoDelete 当所有消费者客户端连接断开时是否自动删除队列
         * 5、arguments 队列的其他参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 消息内容
        String message = "Hello World!";
        // 向指定的队列中发送消息
        /**
         * 参数明细
         * 1、exchange 交换机，如果不指定将使用mq的默认交换机（设置为""）
         * 2、routingKey 路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列名称
         * 3、props 消息的属性
         * 4、body 消息内容
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        // 关闭通道和连接
        channel.close();
        connection.close();
    }
}
