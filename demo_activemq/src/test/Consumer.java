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
	// ���ӹ��� jms ������������
	private ConnectionFactory connectionFactory;
	// jms ����
	private Connection connection;
	// һ�����ͻ������Ϣ���߳�
	private Session session;
	// ��ϢĿ�ĵ�,��Ϣ���͸�˭
	private Destination destination;
	// ��Ϣ����
	private MessageConsumer messageConsumer;

	@Test
	public void consumer() {
		try {
			connectionFactory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_USER,
					ActiveMQConnection.DEFAULT_PASSWORD,
					ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = connectionFactory.createConnection();
			// ��������
			connection.start();
			// ������������
			session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);
			// ��ȡ��������
			session = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			// ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
			destination = session.createQueue("threadMsg");
			messageConsumer = session.createConsumer(destination);
			while (true) {
				// ���ý����߽�����Ϣ��ʱ�䣬Ϊ�˱��ڲ��ԣ�����˭��Ϊ100s
				TextMessage message = (TextMessage) messageConsumer
						.receive(100000);
				if (null != message) {
					log.info(threadName+"  �յ���Ϣ  " + message.getText());
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
