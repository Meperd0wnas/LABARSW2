package edu.eci.arsw.primefinder;


public class Main {

	public static void main(String[] args) {
		
		int max = 30000000;
		int hilos = 3;
		int range = max/hilos;

		PrimeFinderThread hilo1 = new PrimeFinderThread(0, range);
		PrimeFinderThread hilo2 = new PrimeFinderThread(range+1, 2*range);
		PrimeFinderThread hilo3 = new PrimeFinderThread(2*range+1, max);

		
		hilo1.start();
		hilo2.start();
		hilo3.start();
		
		try{
			Thread.sleep(5000);
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		
		
	}
	
}
