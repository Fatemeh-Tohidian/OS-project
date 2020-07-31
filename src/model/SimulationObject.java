package model;

import java.awt.Point;
import java.util.concurrent.Semaphore;

import animal.ServerAnimalHandler;


public class SimulationObject {
	public WildLifeUnit[][] environment;
	public static final Object lock1 = new Object();
//	public static final Object lock2 = new Object();
//	public static final Object lock3 = new Object();
	public Semaphore waitForAnimalsSem = new Semaphore(0);
	public Semaphore readyAnimalsSem = new Semaphore(0);
//	public Semaphore deadAnimals = new Semaphore(0);
	public Semaphore initialize = new Semaphore(0);
	
	
	//	public static final Semaphore mutex1 = new Semaphore(1); 
	//	public static final Semaphore mutex2 = new Semaphore(1); 
	//	public static final Semaphore mutex3 = new Semaphore(1); 
	//	public final Lock lock1 = new ReentrantLock();
	public boolean movePermission = true;
	public boolean OK = true;
	//	public final Condition dontMove = lock1.newCondition();

	//	public boolean printTime = false;

	//	public final Lock lock3 = new ReentrantLock();
	//	public final Condition death = lock3.newCondition();

	//	public final Lock lock4 = new ReentrantLock();
	//	public final Condition wiatForPrint = lock4.newCondition();

	public  int  countOfReadyAnimals = 0;
	//	public final Lock lock2 = new ReentrantLock();
	//	public final Condition readyAnimals = lock2.newCondition();
	public static SimulationObject simulateObject;
	public int r,s,n,m,k,t;
	public int countOfAnimals;
	public int countOfAllDeaths;
	public int countOfNewBorns;
	public static void initialize(int r,int n,int m ,int s ,int k ,int t) {

		simulateObject = new SimulationObject();
		simulateObject.r = r;
		simulateObject.n = n;
		simulateObject.m = m;
		simulateObject.s = s;
		simulateObject.k = k;
		simulateObject.t = t;
		simulateObject.countOfAnimals = r*s;
		simulateObject.buildEnvironment(n,m);
		simulateObject.AddAnimals(r,n,m,s,k,t);
		simulateObject.initialize.release();
	}
	private  void AddAnimals(int r, int n, int m, int s, int k, int t) {
		int p = n/r;
		int q= m/s;
		
		for(int i = 1 ; i <= r ; i++){
			for(int j = 1 ; j <= s ; j++){
				//				System.out.println(i*p + " , "+ j*q);
				environment[i*p][j*q].id = i;
				ServerAnimalHandler newAnimal = new ServerAnimalHandler(i, new Point(i*p,j*q));
				environment[i*p][j*q].animals.add(newAnimal);
				//				newAnimal.start();

			}
		}

	}
	public void startAnimals(){
//		int counter = 0;
		int p = n/r;
		int q= m/s;
		for(int i = 1 ; i <= r ; i++){
			for(int j = 1 ; j <= s ; j++){
//				counter++;
				environment[i*p][j*q].animals.get(0).start();
//				System.out.println(counter);

			}
		}
	}
	private  void buildEnvironment(int n, int m) {
		environment = new WildLifeUnit[n+1][m+1];
		for(int i = 1; i <= n ; i++){
			for(int j = 1 ; j <= m ; j++){
				environment[i][j] = new WildLifeUnit(new Point(i,j));
			}
		}

	}
	
}
