package test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class Producter {
	// 连接工厂 jms 用它创建连接
	private ConnectionFactory connectionFactory;
	// jms 连接
	private Connection connection;
	// 一个发送或接收消息的线程
	private Session session;
	// 消息目的地,消息发送给谁
	private Destination destination;
	// 消息发送者
	private MessageProducer messageProducer;
	private final int SEND_NUMBER = 10;
	@org.junit.Test
	public void producter() {
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
			destination = session.createQueue("threadMsg");
			// 得到生产者对像
			messageProducer = session.createProducer(destination);
			// 设置不持久化，此处学习，实际根据项目决定
			messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			sendMessage(session, messageProducer);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
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
	
	private void sendMessage(Session session, MessageProducer producer) throws Exception{
//		 for (int i = 1; i <= SEND_NUMBER; i++) {
	            TextMessage message = session
	                    .createTextMessage("ActiveMq 发送的消息" + index);
	            // 发送消息到目的地方
	            log.info("发送消息：" + "ActiveMq 发送的消息" +index);
	            producer.send(message);
	            Thread.sleep(500);
//	        }
	}
	
	
	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	} 

	private int index;
	private Logger log = Logger.getLogger(Producter.class);
}
