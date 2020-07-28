package model;

import java.awt.Point;
import java.util.concurrent.Semaphore;

import animal.ServerAnimalHandler;


public class SimulationObject {
	public WildLifeUnit[][] environment;
	public static final Object lock1 = new Object();
	public static final Object lock2 = new Object();
	public Semaphore waitForAnimalsSem = new Semaphore(0);
	public Semaphore readyAnimalsSem = new Semaphore(0);
	public Semaphore deadAnimals = new Semaphore(0);
	public Semaphore initialize = new Semaphore(0);
	//	public static final Semaphore mutex1 = new Semaphore(1); 
	//	public static final Semaphore mutex2 = new Semaphore(1); 
	//	public static final Semaphore mutex3 = new Semaphore(1); 
	//	public final Lock lock1 = new ReentrantLock();
//	public boolean movePermission = true;
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
		//		System.out.println("r "+r);
		//		System.out.println("n "+n);
		//		System.out.println("m "+m);
		//		System.out.println("s "+s);


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
		int counter = 0;
		int p = n/r;
		int q= m/s;
		for(int i = 1 ; i <= r ; i++){
			for(int j = 1 ; j <= s ; j++){
				counter++;
				environment[i*p][j*q].animals.get(0).start();
				System.out.println(counter);

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
	//	public synchronized void increaseCountOfAnimals(){
	//		countOfAnimals++;
	//		System.out.println("---- new born animal");
	//		System.out.println("count of animals is now "+countOfAnimals );
	//	}


	//	public synchronized void increaseCountOfReadyAnimals(){
	//		System.out.println("in increase Count Of Ready Animals");
	//		countOfReadyAnimals++;
	//		System.out.println("Count Of Ready Animals now is " + countOfReadyAnimals );
	//		System.out.println(countOfAnimals);
	//
	//		if(countOfAnimals==countOfReadyAnimals){
	//			lock2.lock();
	//			
	//			try {
	//				System.out.println("in signaling ready Animals");
	//				readyAnimals.signal();;
	//			} finally {
	//				lock2.unlock();
	//			}
	//		}
	//	}

	//	public synchronized void increaseCountOfReadyAnimalsForPrint(){
	//		System.out.println("in increase Count Of Ready Animals For Print");
	//		countOfReadyAnimalsForPrint++;
	//		System.out.println("Count Of Ready Animals For Print now is " + countOfReadyAnimalsForPrint );
	//		if(countOfAnimals==countOfReadyAnimalsForPrint){
	//			lock4.lock();
	//			try {
	//				wiatForPrint.signal();
	//				
	//			} finally {
	//				lock4.unlock();
	//			}
	//		}
	//	}
	//	public synchronized void stopAnimals() throws InterruptedException{
	//		SimulationObject.simulateObject.movePermission = false;
	//		SimulationObject.simulateObject.lock2.lock();
	//		try {
	//			System.out.println("stoper is wating for animals to finish their job");
	//			
	//			SimulationObject.simulateObject.readyAnimals.await();
	//			
	//			System.out.println("all animals stopped successfully");
	//		} finally {
	//			SimulationObject.simulateObject.lock2.unlock();
	//		}
	//		
	//		
	////		SimulationObject.simulateObject.lock3.lock();
	////		try {
	////			System.out.println("waiting for stopper to do the job");
	////			SimulationObject.simulateObject.done.await();
	////			
	////		} finally {
	////			SimulationObject.simulateObject.lock3.unlock();
	////		}
	////		System.out.println("stoper is done");
	////		System.out.println("system is going ti signal all");
	//		
	//		
	//		
	////		
	////		SimulationObject.simulateObject.countOfReadyAnimals = 0;
	////		SimulationObject.simulateObject.movePermission = true;
	////		SimulationObject.simulateObject.lock1.lock();
	////		try {
	////			SimulationObject.simulateObject.dontMove.signalAll();
	////			
	////		} finally {
	////			SimulationObject.simulateObject.lock1.unlock();
	////		}
	////		System.out.println("all animals must start working from now");
	//	}


}
