package test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

public class Consumer {
	// 连接工厂 jms 用它创建连接
	private ConnectionFactory connectionFactory;
	// jms 连接
	private Connection connection;
	// 一个发送或接收消息的线程
	private Session session;
	// 消息目的地,消息发送给谁
	private Destination destination;
	// 消息接收
	private MessageConsumer messageConsumer;

	@Test
	public void consumer() {
		try {
			connectionFactory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_USER,
					ActiveMQConnection.DEFAULT_PASSWORD,
					ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = connectionFactory.createConnection();
			// 启动连接
			connection.start();
			// 创建操作连接
			session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);
			// 获取操作连接
			session = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
			destination = session.createQueue("threadMsg");
			messageConsumer = session.createConsumer(destination);
			while (true) {
				// 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
				TextMessage message = (TextMessage) messageConsumer
						.receive(100000);
				if (null != message) {
					log.info(threadName+"  收到消息  " + message.getText());
				} else {
//					break;
				}
				Thread.sleep(1000);
			} 
		} catch (Exception e) {
			// TODO: handle exception\
			e.printStackTrace();
		} finally {
			log.info(threadName+"  end");
			if(connection!=null){
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	private String threadName;
	private Logger log = Logger.getLogger(Consumer.class);
}
