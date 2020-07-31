package animal;

import java.util.concurrent.ThreadLocalRandom;

public class ClientAnimalController extends Thread {

	public  boolean stop = true;



	@Override
	public void run() {

		while (true) {
			try {


				if(stop){


					synchronized (this) {

						System.out.println("ready");
						this.wait();
						System.out.println("recieved");
					}
				}
				Thread.sleep(700);
				synchronized (this) {
					
					System.out.println("move "+ThreadLocalRandom.current().nextInt(-1,2)+" "+ThreadLocalRandom.current().nextInt(-1,2));
					this.wait();
				}

			} catch (InterruptedException e) {
				break;

			}



		}
	}
}
