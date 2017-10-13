package test.thread;

import test.Producter;

public class ProducterThread implements Runnable {
	public ProducterThread(Producter producter) {
		this.producter = producter;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int index=0;
		while(true){
			producter.setIndex(++index);
			producter.producter();
//			System.out.println("·¢ËÍÏûÏ¢");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Producter producter;
}
