package test;

import test.thread.ConsumerThread;
import test.thread.ProducterThread;

public class Test {
	public static void main(String[] args) {
		new Test().threadTest();
	}
	@org.junit.Test
	public void threadTest() {
		ProducterThread pt=new ProducterThread(new Producter());
		ConsumerThread ct=new ConsumerThread(new Consumer());
		Thread productThread=new Thread(pt);
		productThread.setPriority(10);
		productThread.start();
		Thread consumerThread=new Thread(ct,"��һ�������߳�:");
		consumerThread.setPriority(8);
		consumerThread.start();
		
		
//		Thread consumerThread1=new Thread(ct,"�ڶ��������߳�:");
//		consumerThread1.setPriority(7);
//		consumerThread1.start();
//		
//		Thread consumerThread2=new Thread(ct,"�����������߳�:");
//		consumerThread2.setPriority(6);
//		consumerThread2.start();
	}
}
