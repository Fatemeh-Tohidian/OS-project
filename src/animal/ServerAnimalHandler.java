package animal;

import java.awt.Point;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import model.SimulationObject;
import model.WildLifeUnit;

public class ServerAnimalHandler extends Thread{

	public int id ;
	public Point position;
	Scanner scanner;
	PrintWriter printWriter;
	public Process animal;
	static int n ,m;
	public int age = 0;
	int r1 ;
	int r2 ;
	WildLifeUnit beginning;
	public  AtomicBoolean running = new AtomicBoolean(true);
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
		//		try {
		String massage ;
		//			System.out.println("handler run");



		loop:while(running.get()){


			if(scanner.hasNext()){

				try {


					massage = scanner.next();

//					System.out.println(massage);

					switch (massage) {


					case "recieved":
						declareReadinessToServer();
						scanner.nextLine();
						break;

					case "ready":
						declareReadinessToServer();
						scanner.nextLine();

						break;



					case "move":

						tryToMove(Integer.valueOf(scanner.next()),Integer.valueOf(scanner.next()));
						printWriter.println("request checked");
						printWriter.flush();
						break;
					}
					Thread.sleep(500);

				} catch (Exception e) {
					break loop;

				}
			}

		}


	}
	private void tryToMove(int r1 , int r2) {

		if(SimulationObject.simulateObject.movePermission) {



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


	}
	public void declareReadinessToServer(){

		//		System.out.println("in declareReadinessToServer");
		synchronized (SimulationObject.lock1) {

			SimulationObject.simulateObject.countOfReadyAnimals++;

//			System.out.println("after declare rediness left is "+ (SimulationObject.simulateObject.countOfAnimals-SimulationObject.simulateObject.countOfReadyAnimals));

//			System.out.println("after declareReadinessToServer population is: "+SimulationObject.simulateObject.countOfAnimals+"and count of ready animals is : "+SimulationObject.simulateObject.countOfReadyAnimals );
//			System.out.println((SimulationObject.simulateObject.countOfAnimals-SimulationObject.simulateObject.countOfReadyAnimals +"is left to declare rediness"));
			if(SimulationObject.simulateObject.countOfAnimals <=
					SimulationObject.simulateObject.countOfReadyAnimals){
								SimulationObject.simulateObject.countOfReadyAnimals = SimulationObject.simulateObject.countOfAnimals;

				SimulationObject.simulateObject.waitForAnimalsSem.release();
			}
		}
	}

	public void declareStopToAnimal(){
		//		System.out.println("in declareStopToAnimal");
		printWriter.println("stop");
		printWriter.flush();


	}
	public void declareResumeToAnimal(){
		//		System.out.println("in declareResumeToAnimal");
		printWriter.println("go on");
		printWriter.flush();


	}
}