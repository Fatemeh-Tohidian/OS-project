package main;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import animal.ServerAnimalHandler;
import model.EatingData;
import model.SimulationObject;
import model.WildLifeUnit;

public class WildLifeController{
	static int r,s,n,m,k,t;
	static int counter = 1  ; 
	public void start(){
		r = SimulationObject.simulateObject.r;
		s = SimulationObject.simulateObject.s;
		n = SimulationObject.simulateObject.n;
		m = SimulationObject.simulateObject.m;
		k = SimulationObject.simulateObject.k;
		t = SimulationObject.simulateObject.t;


		Timer timer = new Timer();
		Task task = new Task();
		//		try {
		//			System.out.println("server is goinig to wait for animals to start");
		//			SimulationObject.simulateObject.waitForAnimalsSem.acquire();
		//			System.out.println("all handlres declare readiness");
		//		} catch (InterruptedException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		synchronized (SimulationObject.lock1) {
		//			SimulationObject.simulateObject.countOfReadyAnimals = 0;
		//		}
		timer.schedule(task,0, 6000);

	}


	static class Task extends TimerTask{
		public static final String ANSI_RESET = "\u001B[0m";
		public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
		public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
		public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
		public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
		public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
		public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
		public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
		public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
		ArrayList<ServerAnimalHandler> animals;
		//		WildLifeUnit[][]wildLifeUnits;
		@Override
		public void run() {

			try {
				synchronized (SimulationObject.lock1) {
					SimulationObject.simulateObject.countOfReadyAnimals = 0;
				}


				System.out.println("controller is starting");

				System.out.println("Controller is asking for stoping animals");
				for(int i = 1; i <= n ; i++){
					for(int j = 1 ; j <= m ; j++){
						animals = SimulationObject.simulateObject.environment[i][j].animals;
						//						System.out.println(animals.size());

						for (ServerAnimalHandler serverAnimalHandler : animals) {

							System.out.println("server is going to send stop signal to handlers");
							serverAnimalHandler.declareStopToAnimal();

						}
					}
				}

				SimulationObject.simulateObject.waitForAnimalsSem.acquire();


				//				checkDeath();
				//				System.out.println("before dead animals lock");
				//				SimulationObject.simulateObject.deadAnimals.acquire();
				//				System.out.println("after dead animals lock");


				//				}
				//				SimulationObject.simulateObject.lock2.lock();
				//				try {
				//										System.out.println("controller is wating for animals to finish their job");
				//
				//					SimulationObject.simulateObject.readyAnimals.await();
				//
				//										System.out.println("all animals stopped successfully");
				//				} finally {
				//					SimulationObject.simulateObject.lock2.unlock();
				//				}



				//				SimulationObject.simulateObject.countOfAllDeaths = checkDeath();
				//				System.out.println("after check death");
				//				System.out.println("countOfAnimals "+SimulationObject.simulateObject.countOfAnimals);
				//				System.out.println("countOfReadyAnimals "+SimulationObject.simulateObject.countOfReadyAnimals);
				//				System.out.println("countOfAllDeaths "+SimulationObject.simulateObject.countOfAllDeaths);
				//				if(SimulationObject.simulateObject.countOfAnimals-SimulationObject.simulateObject.countOfAllDeaths ==
				//						SimulationObject.simulateObject.countOfReadyAnimals){
				////					System.out.println("all right");
				//					SimulationObject.simulateObject.countOfAnimals -=SimulationObject.simulateObject.countOfAllDeaths;
				//				}
				//				else{
				//					
				//				SimulationObject.simulateObject.lock3.lock();
				//				try {
				////					System.out.println("waiting for dead animals to finish");
				//					SimulationObject.simulateObject.death.await();
				////					System.out.println("after await");
				//					SimulationObject.simulateObject.countOfAnimals -=SimulationObject.simulateObject.countOfAllDeaths;
				//				} finally {
				//
				//					SimulationObject.simulateObject.lock3.unlock();
				//				}
				//				}
				//				System.out.println("");
				print();
				//				
				System.out.println("before check birth");
				checkBirth();
				System.out.println("after check birth");
				System.out.println("before wait for newborns");
				SimulationObject.simulateObject.waitForAnimalsSem.acquire();
				System.out.println("after wait for newborns");
				synchronized (SimulationObject.lock1) {


					SimulationObject.simulateObject.countOfReadyAnimals = 0;
				}
				//					SimulationObject.simulateObject.countOfReadyAnimals = 0;
				for(int i = 1; i <= n ; i++){
					for(int j = 1 ; j <= m ; j++){
						animals = SimulationObject.simulateObject.environment[i][j].animals;
						//						System.out.println(animals.size());

						for (ServerAnimalHandler serverAnimalHandler : animals) {

							System.out.println("server is going to send resume signal to handlers");
							serverAnimalHandler.declareResumeToAnimal();;

						}
					}
				}
				synchronized (SimulationObject.lock1) {

					SimulationObject.simulateObject.countOfReadyAnimals = 0;
				}
				SimulationObject.simulateObject.waitForAnimalsSem.acquire();
				synchronized (SimulationObject.lock1) {

					SimulationObject.simulateObject.countOfReadyAnimals = 0;
				}
				System.out.println("controller is going to sleep for the next round");
				//				SimulationObject.simulateObject.lock1.lock();
				//				try {
				//				SimulationObject.simulateObject.readyAnimalsSem.release(SimulationObject.simulateObject.countOfAnimals);

				//				} finally {
				//					SimulationObject.simulateObject.lock1.unlock();
				//				}
				//				//				System.out.println("all animals must start working from now");
				//			} catch (InterruptedException e) {
				//				e.printStackTrace();
				//			}

			} catch (InterruptedException e) {
				//				e.printStackTrace();
			}
		}
		private void print() {
			if(counter == t){
				counter = 1;


				for(int i = 1; i <= n ; i++){
					for(int j = 1 ; j <= m ; j++){

						int id = SimulationObject.simulateObject.environment[i][j].id;
						if(id!=0){
							//							System.out.println("( "+i+" , "+j+" )"+" ---> "+SimulationObject.simulateObject.environment[i][j].animals.size() );
						}
						switch (id) {
						case 0:
							System.out.print( SimulationObject.simulateObject.environment[i][j].animals.size()+" ");

							break;
						case 1:
							System.out.print( ANSI_PURPLE_BACKGROUND +SimulationObject.simulateObject.environment[i][j].animals.size()+ANSI_RESET+" ");

							break;

						case 2:
							System.out.print( ANSI_YELLOW_BACKGROUND +SimulationObject.simulateObject.environment[i][j].animals.size() +ANSI_RESET+" ");

							break;
						case 3:
							System.out.print( ANSI_CYAN_BACKGROUND +SimulationObject.simulateObject.environment[i][j].animals.size()+ANSI_RESET+" ");

							break;
						case 4:
							System.out.print( ANSI_GREEN_BACKGROUND +SimulationObject.simulateObject.environment[i][j].animals.size()+ANSI_RESET+" ");

							break;

						}

						//							System.out.print( ANSI_GREEN_BACKGROUND +SimulationObject.simulateObject.environment[i][j].id+" " +ANSI_RESET);
					}
					System.out.println();

				}
				//					System.out.println("printing is done going to signal");
				System.out.println("---------------");



			}
			else{
				counter++;
			}

		}
		public void checkDeath(){
			synchronized (SimulationObject.lock2) {

				System.out.println("in check dead");
				checkCapacity();
				//				System.out.println("after check capacity");
				checkEating();
				//				System.out.println("after check eating");
				if(SimulationObject.simulateObject.OK){
					System.out.println("in ok release");
					SimulationObject.simulateObject.deadAnimals.release();
				}
				else{
					SimulationObject.simulateObject.OK = true;
				}
			}
		}

		private int checkEating() {
			//			synchronized (SimulationObject.lock2) {


			int ans = 0;
			for(int z = 1 ; z <= r ; z++){
				for(int i =1 ; i <= n ; i++){
					loop:	for(int j = 1 ; j <= m ; j++){
						WildLifeUnit unit = SimulationObject.simulateObject.environment[i][j];
						if(unit.id == z){
							ArrayList<EatingData> types = getTypes(i,j);
							int power = unit.id * unit.animals.size();

							if(unit.id > r/2){
								for (EatingData eatingData : types) {
									if(eatingData.id < unit.id){

										if(power < eatingData.id*eatingData.specialCount && unit.id != eatingData.id){
											ans+=unit.animals.size();
											for (ServerAnimalHandler diedAnimal : unit.animals) {
												SimulationObject.simulateObject.countOfAnimals--;
												SimulationObject.simulateObject.OK = false;
												diedAnimal.interrupt();
											}
											unit.animals.clear();
											unit.id = 0;
											break loop;

										}
									}
									else{
										if(power < eatingData.id*eatingData.realCount && unit.id != eatingData.id){
											ans+=unit.animals.size();
											for (ServerAnimalHandler diedAnimal : unit.animals) {
												SimulationObject.simulateObject.countOfAnimals--;
												SimulationObject.simulateObject.OK = false;
												diedAnimal.interrupt();
											}
											unit.animals.clear();
											unit.id = 0;
											break loop;

										}

									}

								}

							}
							else{

								for (EatingData eatingData : types) {

									if(power < eatingData.id*eatingData.realCount && unit.id != eatingData.id){
										ans+=unit.animals.size();
										for (ServerAnimalHandler diedAnimal : unit.animals) {
											SimulationObject.simulateObject.countOfAnimals--;
											SimulationObject.simulateObject.OK = false;
											diedAnimal.interrupt();
										}
										unit.animals.clear();
										unit.id = 0;
										break loop;

									}
								}
							}


						}

					}
				}
			}
			return ans;
			//			}
		}
		private ArrayList<EatingData> getTypes(int i, int j) {
			ArrayList<EatingData> types = new ArrayList<EatingData>();

			if(i>1){
				WildLifeUnit Nunit = SimulationObject.simulateObject.environment[i-1][j];
				types.add(new EatingData(Nunit.id, Nunit.animals.size(), Nunit.animals.size()));
			}
			if(j>1){
				WildLifeUnit Wunit = SimulationObject.simulateObject.environment[i][j-1];
				if(!types.isEmpty() && types.get(0).id == Wunit.id){

					types.get(0).realCount += Wunit.animals.size();
					types.get(0).specialCount += Wunit.animals.size();
				}

				else{

					types.add(new EatingData(Wunit.id, Wunit.animals.size(), Wunit.animals.size()));	

				}

			}
			if(i<n){

				WildLifeUnit Sunit = SimulationObject.simulateObject.environment[i+1][j];

				if(!types.isEmpty() && types.get(0).id == Sunit.id){
					types.get(0).realCount += Sunit.animals.size();
					types.get(0).specialCount += Sunit.animals.size();
				}
				else if(types.size()>1 && types.get(1).id == Sunit.id){

					types.get(1).realCount += Sunit.animals.size();
					types.get(1).specialCount += Sunit.animals.size();
				}
				else{
					types.add(new EatingData(Sunit.id, Sunit.animals.size(), Sunit.animals.size()));
				}

			}
			if(j<m){

				WildLifeUnit Eunit = SimulationObject.simulateObject.environment[i][j+1];

				if(!types.isEmpty() && types.get(0).id == Eunit.id){
					types.get(0).realCount += Eunit.animals.size();
					types.get(0).specialCount += Eunit.animals.size();
				}
				else if(types.size()>1 && types.get(1).id == Eunit.id){

					types.get(1).realCount += Eunit.animals.size();
					types.get(1).specialCount += Eunit.animals.size();
				}
				else if(types.size()>2 && types.get(2).id == Eunit.id){

					types.get(2).realCount += Eunit.animals.size();
					types.get(2).specialCount += Eunit.animals.size();
				}
				else{
					types.add(new EatingData(Eunit.id, Eunit.animals.size(), Eunit.animals.size()));
				}
			}
			if(i>1 && j>1){

				WildLifeUnit NWunit = SimulationObject.simulateObject.environment[i-1][j-1];

				if(!types.isEmpty() && types.get(0).id == NWunit.id){
					types.get(0).realCount += NWunit.animals.size();
				}
				else if(types.size()>1 && types.get(1).id == NWunit.id){

					types.get(1).realCount += NWunit.animals.size();
				}
				else if(types.size()>2 && types.get(2).id == NWunit.id){

					types.get(2).realCount += NWunit.animals.size();
				}
				else if(types.size()>3 && types.get(3).id == NWunit.id){

					types.get(3).realCount += NWunit.animals.size();
				}
				else{
					types.add(new EatingData(NWunit.id, NWunit.animals.size(), NWunit.animals.size()));
				}

			}
			if(i<n && j<m){

				WildLifeUnit SEunit = SimulationObject.simulateObject.environment[i+1][j+1];

				if(!types.isEmpty() && types.get(0).id == SEunit.id){
					types.get(0).realCount += SEunit.animals.size();
				}
				else if(types.size()>1 && types.get(1).id == SEunit.id){

					types.get(1).realCount += SEunit.animals.size();
				}
				else if(types.size()>2 && types.get(2).id == SEunit.id){

					types.get(2).realCount += SEunit.animals.size();
				}
				else if(types.size()>3 && types.get(3).id == SEunit.id){

					types.get(3).realCount += SEunit.animals.size();
				}
				else if(types.size()>4 && types.get(4).id == SEunit.id){

					types.get(4).realCount += SEunit.animals.size();
				}
				else{
					types.add(new EatingData(SEunit.id, SEunit.animals.size(), SEunit.animals.size()));
				}


			}
			if(i<n && j>1){


				WildLifeUnit SWunit = SimulationObject.simulateObject.environment[i+1][j-1];

				if(!types.isEmpty() && types.get(0).id == SWunit.id){
					types.get(0).realCount += SWunit.animals.size();
				}
				else if(types.size()>1 && types.get(1).id == SWunit.id){

					types.get(1).realCount += SWunit.animals.size();
				}
				else if(types.size()>2 && types.get(2).id == SWunit.id){

					types.get(2).realCount += SWunit.animals.size();
				}
				else if(types.size()>3 && types.get(3).id == SWunit.id){

					types.get(3).realCount += SWunit.animals.size();
				}
				else if(types.size()>4 && types.get(4).id == SWunit.id){

					types.get(4).realCount += SWunit.animals.size();
				}
				else if(types.size()>5 && types.get(5).id == SWunit.id){

					types.get(5).realCount += SWunit.animals.size();
				}
				else{
					types.add(new EatingData(SWunit.id, SWunit.animals.size(), SWunit.animals.size()));
				}


			}

			if(i>1 && j<m){

				WildLifeUnit NEunit = SimulationObject.simulateObject.environment[i-1][j+1];

				if(!types.isEmpty() && types.get(0).id == NEunit.id){
					types.get(0).realCount += NEunit.animals.size();
				}
				else if(types.size()>1 && types.get(1).id == NEunit.id){

					types.get(1).realCount += NEunit.animals.size();
				}
				else if(types.size()>2 && types.get(2).id == NEunit.id){

					types.get(2).realCount += NEunit.animals.size();
				}
				else if(types.size()>3 && types.get(3).id == NEunit.id){

					types.get(3).realCount += NEunit.animals.size();
				}
				else if(types.size()>4 && types.get(4).id == NEunit.id){

					types.get(4).realCount += NEunit.animals.size();
				}
				else if(types.size()>5 && types.get(5).id == NEunit.id){

					types.get(5).realCount += NEunit.animals.size();
				}
				else if(types.size()>6 && types.get(6).id == NEunit.id){

					types.get(6).realCount += NEunit.animals.size();
				}
				else if(types.size()>7 && types.get(7).id == NEunit.id){

					types.get(7).realCount += NEunit.animals.size();
				}
				else{
					types.add(new EatingData(NEunit.id, NEunit.animals.size(), NEunit.animals.size()));
				}

			}
			return types;
		}
		private  void checkCapacity() {

			//			synchronized (SimulationObject.lock2) {
			for(int i =1 ; i <= n ; i++){
				for(int j = 1 ; j <= m ; j++){
					WildLifeUnit unit = SimulationObject.simulateObject.environment[i][j];
					if(unit.id != 0){
						while(unit.animals.size()> k/unit.id){
							SimulationObject.simulateObject.OK = false;
							unit.animals.get(0).interrupt();
							unit.animals.remove(0);
							SimulationObject.simulateObject.countOfAnimals--;
						}
						if(unit.animals.isEmpty()){
							unit.id = 0;
						}
					}
				}
			}
			//			}

		}
		public void checkBirth(){

			synchronized (SimulationObject.lock1) {

				for(int i =1 ; i <= n ; i++){
					for(int j = 1; j <= m ; j++){


						ArrayList<ServerAnimalHandler> animals = SimulationObject.simulateObject.environment[i][j].animals;
						ArrayList<ServerAnimalHandler> newBornAnimals = new ArrayList<ServerAnimalHandler>();

						for (ServerAnimalHandler animal : animals) {
							animal.age++;
							if( animal.age % animal.id == 0 ){
								ServerAnimalHandler newAnimal = new ServerAnimalHandler(animal.id, animal.position);

								newBornAnimals.add(newAnimal);
								SimulationObject.simulateObject.countOfAnimals++;
								//								SimulationObject.simulateObject.countOfReadyAnimals++;
							}

						}

						animals.addAll(newBornAnimals);
						for (ServerAnimalHandler animal : newBornAnimals) {
							System.out.println("we have newborn");
							animal.start();
						}
					}
				}
				System.out.println("after executiing checkBirth population is : "+SimulationObject.simulateObject.countOfAnimals+"and count of ready animals is : "+SimulationObject.simulateObject.countOfReadyAnimals );
			}
		}

	} 
}
