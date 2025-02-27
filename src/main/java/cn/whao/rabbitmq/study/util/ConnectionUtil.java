package cn.whao.rabbitmq.study.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wanghao
 * @description RabbitMQ连接工具类
 * @create 2025-02-27 16:05
 */
public class ConnectionUtil {

    public static Connection getConnection() throws Exception {
        // 定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置服务地址
        factory.setHost("127.0.0.1");
        // 端口
        factory.setPort(5672);
        // 设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("/whao");
        factory.setUsername("whao");
        factory.setPassword("123456");
        // 通过工厂获取连接
        Connection connection = factory.newConnection();
        return connection;
    }

    public static void main(String[] args) {
        try {
            Connection con = ConnectionUtil.getConnection();
            System.out.println(con);
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
