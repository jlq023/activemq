package test.thread;

import test.Consumer;

public class ConsumerThread extends Thread {
	public ConsumerThread(Consumer consumer) {
		this.consumer=consumer;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub 
		consumer.setThreadName(Thread.currentThread().getName());
		consumer.consumer();
	}

	private Consumer consumer;
}
