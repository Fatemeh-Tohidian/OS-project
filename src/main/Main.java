package main;

import java.util.Scanner;

import main.WildLifeController;
import model.SimulationObject;

public class Main {
	int r,s,n,m,k,t;

	public static void main(String [] args){

		Scanner sc = new Scanner(System.in);

		int r = sc.nextInt();
		int n = sc.nextInt();
		int m = sc.nextInt();
		int s = sc.nextInt();
		int k = sc.nextInt();
		int t = sc.nextInt();
//		System.out.println("r "+r);
//		System.out.println("n "+n);
//		System.out.println("m "+m);
//		System.out.println("s "+s);

		sc.close();

		//		for(int i = 1; i <= n ; i++){
		//			for(int j = 1 ; j <= m ; j++){
		//				System.out.print(SimulationObject.simulateObject.environment[i][j].id);
		//				
		//			}
		//			System.out.println();
		//		}
//		try {

			SimulationObject.initialize(r, n, m, s, k, t);
			try {
				SimulationObject.simulateObject.initialize.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

					WildLifeController wildLifeController = new WildLifeController();
			////		Printer printer = new Printer();
					wildLifeController.start();
			////		printer.start();
			SimulationObject.simulateObject.startAnimals();
//		}
//		finally {
//			WildLifeUnit[][] wildLifeUnits = SimulationObject.simulateObject.environment;
//			ArrayList<ServerAnimalHandler> animals;
//			
//			for(int i = 0; i < n ; i++) {
//				for(int j =0 ; j< m ; j++) {
//					
//					animals = wildLifeUnits[i][j].animals;
//					for (ServerAnimalHandler serverAnimalHandler : animals) {
//						System.out.println("destroyed");
//						serverAnimalHandler.animal.destroy();
//					}
//				}
//			}
//		}

	}

}
