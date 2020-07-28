package animal;

import java.util.concurrent.Semaphore;

public class ClientAnimal {
	
	public  boolean stop = false;
	public  Object lock1 = new Object();
	public Semaphore animal = new Semaphore(0);
	
	public static void main(String [] args) {
		
		ClientAnimalController animalController = new ClientAnimalController();
		ClientAnimalListener animalListener = new ClientAnimalListener(animalController);
		
		animalListener.start();
		animalController.start();
		
	}
}
