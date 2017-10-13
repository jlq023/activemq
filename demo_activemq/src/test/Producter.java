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
	// ���ӹ��� jms ������������
	private ConnectionFactory connectionFactory;
	// jms ����
	private Connection connection;
	// һ�����ͻ������Ϣ���߳�
	private Session session;
	// ��ϢĿ�ĵ�,��Ϣ���͸�˭
	private Destination destination;
	// ��Ϣ������
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
			// ��������
			connection.start();
			// ������������
			session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("threadMsg");
			// �õ������߶���
			messageProducer = session.createProducer(destination);
			// ���ò��־û����˴�ѧϰ��ʵ�ʸ�����Ŀ����
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
	                    .createTextMessage("ActiveMq ���͵���Ϣ" + index);
	            // ������Ϣ��Ŀ�ĵط�
	            log.info("������Ϣ��" + "ActiveMq ���͵���Ϣ" +index);
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
