package animal;

import java.awt.Point;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;


import model.SimulationObject;
import model.WildLifeUnit;

public class ServerAnimalHandler extends Thread{

	public int id ;
	public Point position;
	Scanner scanner;
	PrintWriter printWriter;
	public Process animal;
	Random random = new Random();
	static int n ,m;
	public int age = 0;
	int r1 ;
	int r2 ;
	WildLifeUnit beginning;
	WildLifeUnit destination;
	ServerAnimalHandler formerAnimal;


	public ServerAnimalHandler(int id , Point position){
		try{

			this.id = id;
			this.position = position;
			n = SimulationObject.simulateObject.n;
			m = SimulationObject.simulateObject.m;
			animal = Runtime.getRuntime().exec("java  -cp /home/fatemeh/eclipse-workspace/WildLifeSimulatorProcess/bin animal.ClientAnimal");
			printWriter = new PrintWriter(animal.getOutputStream());
			scanner  = new Scanner(animal.getInputStream());
//			declareReadinessToServer();
		} 
		catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	@Override
	public void run() {
		try {
		String massage ;
		System.out.println("handler run");
		super.run();


		loop:while(true){
			
			try {
				
				System.out.println("handler is wating for massage");
				if(scanner.hasNext()){
					System.out.println("handler recieved a new massage");
					massage = scanner.nextLine();
					System.out.println(massage);
					
					switch (massage) {

					case "roger resume command":
						declareReadinessToServer();
						break;
						
					case "I'm ready":
						declareReadinessToServer();
						break;
						
					case "Let's go":
						declareReadinessToServer();
						break;

					case "I wanna move":
						tryToMove();
						break;
					}
				}
				Thread.sleep(1000);

			} catch (InterruptedException e) {
				synchronized (SimulationObject.lock2) {
					System.out.println("in kiling process ------------");
					System.out.println("left is "+ (SimulationObject.simulateObject.countOfReadyAnimals-SimulationObject.simulateObject.countOfAnimals));
					SimulationObject.simulateObject.countOfReadyAnimals--;
					if(SimulationObject.simulateObject.countOfAnimals ==
							SimulationObject.simulateObject.countOfReadyAnimals){

						SimulationObject.simulateObject.deadAnimals.release();
					}
					printWriter.println("die");
					printWriter.flush();
					
					
					break loop;

				}
			}
		}
		}
		finally {
			printWriter.close();
			scanner.close();
		}
		
	}
	private void tryToMove() {
		//				System.out.println("in try to move");
		r1 = random.nextInt(3) -1;
		r2= random.nextInt(3) -1;
		if(r1==0 && r2==0){
			return;
		}
		beginning = SimulationObject.simulateObject.environment[position.x][position.y];
		if(position.x+r1>=1 && position.x+r1 <= n && position.y+r2 >= 1 && position.y+r2 <= m){


			destination = SimulationObject.simulateObject.environment[position.x+r1][position.y+r2];

			formerAnimal = this;
			if(destination.MoveIn(beginning,this)){
				beginning.MoveOut(formerAnimal);

			}
		}
	}
	public void declareReadinessToServer(){

		System.out.println("in declareReadinessToServer");
		synchronized (SimulationObject.lock1) {

			SimulationObject.simulateObject.countOfReadyAnimals++;
			System.out.println("after declareReadinessToServer population is: "+SimulationObject.simulateObject.countOfAnimals+"and count of ready animals is : "+SimulationObject.simulateObject.countOfReadyAnimals );
			System.out.println((SimulationObject.simulateObject.countOfAnimals-SimulationObject.simulateObject.countOfReadyAnimals +"is left to declare rediness"));
			if(SimulationObject.simulateObject.countOfAnimals ==
					SimulationObject.simulateObject.countOfReadyAnimals){
//				SimulationObject.simulateObject.countOfReadyAnimals = 0;

				SimulationObject.simulateObject.waitForAnimalsSem.release();
			}
		}
	}

	public void declareStopToAnimal(){
		System.out.println("in declareStopToAnimal");
		printWriter.println("stop");
		printWriter.flush();


	}
	public void declareResumeToAnimal(){
		System.out.println("in declareResumeToAnimal");
		printWriter.println("go on");
		printWriter.flush();


	}
}