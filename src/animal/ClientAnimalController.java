package animal;

public class ClientAnimalController extends Thread {

	public  boolean stop = true;


	@Override
	public void run() {
		//		System.out.println("starting client animal controller");

		super.run();

		while (true) {
			try {
			System.out.println("controller loop");

			if(stop){
				//				System.out.println("stop in controller loop");

				try {
					synchronized (this) {

						System.out.println("I'm ready");
						this.wait();
						System.out.println("roger resume command");
					}


				} catch (InterruptedException e) {

					e.printStackTrace();
				}

			}
				Thread.sleep(700);
			} catch (InterruptedException e) {
				break;
				
			}
			
			try {
				System.out.println("I wanna move");
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
