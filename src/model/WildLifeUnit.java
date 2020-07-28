package model;

import java.awt.Point;
import java.util.ArrayList;

import animal.ServerAnimalHandler;

public class WildLifeUnit {

	public ArrayList<ServerAnimalHandler> animals = new ArrayList<ServerAnimalHandler>();
	public int id;
	Point position;

	public WildLifeUnit(Point position){

		this.position = position;
	}

	public synchronized boolean MoveIn(WildLifeUnit beginning,ServerAnimalHandler animal) {

		if(this.hasCapacity() && (this.id == beginning.id || this.id==0)){

			this.id = animal.id;
			animal.position = position;
			animals.add(animal);

			return true;

		}
		return false;
	}
	
	public synchronized void MoveOut(ServerAnimalHandler animal) {

		animals.remove(animal);

		if(animals.isEmpty()){
			this.id = 0;
		}
	}
	
	public synchronized boolean hasCapacity(){
		if(SimulationObject.simulateObject.k - (animals.size()*id)>=id){
			return true;
		}
		return false;
	}
}
