package animal;

import java.util.Scanner;

public class ClientAnimalListener extends Thread{
	ClientAnimalController animalController;
	Scanner scanner  = new Scanner(System.in);

	public ClientAnimalListener(ClientAnimalController  animalController) {
		this.animalController = animalController;

	}

	@Override
	public void run() {
		try {
			super.run();
			loop:	while(true){
				if(scanner.hasNext()){

					String massage = scanner.nextLine();
					switch (massage) {

					case "stop":

						System.out.println("stop recived in client side");
						animalController.stop = true;

						break;

					case "die":
						animalController.interrupt();

						break loop;


					case "go on":
						animalController.stop = false;
						System.out.println("client recieved go on cammand");
						synchronized (animalController) {

							animalController.notify();
						}

						break;

					}
				}
			}
		}finally {
			scanner.close();}
	}
}
