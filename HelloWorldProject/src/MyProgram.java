//Jonathan Conte pd4 Adams
//This program is meant to simulate a train stattiong which processes train cars using stacks and quesues.  Train objects and their attributes are used for identification and sorting.
import java.util.Scanner;
import java.io.File;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class MyProgram {
	public static int val = 0;

	public static class Event {

		boolean isEngine;
		String engineId;
		String city;
		Train car;

		public Event(Train car){
			isEngine = false;
			this.car = car;
		}

		public Event(String engineId, String city){

			isEngine = true;
			this.engineId = engineId;
			this.city = city;
		
		}

	}

	public static void main(String[] args) {

		int limitTrackA = 100000, limitTrackB = 100000, limitTrackC = 100000;
	
		Queue<Event> track0 = new LinkedList<>();

		Queue<Train> inspection = new LinkedList<>();

		Queue<Train> trackA = new LinkedList<>();   // Trenton
        Queue<Train> trackB = new LinkedList<>(); // Charlotte
        Queue<Train> trackC = new LinkedList<>();      // Baltimore
        Stack<Train> trackD = new Stack<>(); // Other

		int weightA = 0;
		int weightB = 0;
		int weightC = 0;


		Scanner x = new Scanner(System.in);
		try{
			File f = new File("HelloWorldProject/src/data.txt");
			x = new Scanner (f);

			while (x.hasNextLine()){
				String line = x.nextLine().trim();
				if (line.equals("END")) break;
				if (line.length() == 0) continue;

				if (line.startsWith("CAR")){
					String name = line;
					String product = x.nextLine().trim();
					String origin = x.nextLine().trim();
					String destination = x.nextLine().trim();
					int weight = Integer.parseInt(x.nextLine().trim());
					int miles = Integer.parseInt(x.nextLine().trim());

					Train car = new Train(name, product, origin, destination, weight, miles);
					track0.add(new Event(car));
				}
				else if (line.startsWith("ENG")){
					String engineId = line;
					String city = x.nextLine().trim();
					track0.add(new Event(engineId, city));
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally{
			x.close();
		}

		while (!track0.isEmpty()){
			Event e = track0.remove();

			if(e.isEngine){

				if(e.city.equals("Trenton")){
					depart(e.engineId, "Trenton", trackA);
					weightA = 0;
				}
				else if (e.city.equals("Charlotte")){
					depart(e.engineId, "Charlotte", trackB);
					weightB = 0;
				}
				else if (e.city.equals("Baltimore")){
					depart(e.engineId, "Baltimore", trackC);
					weightC = 0;
				}
			
			} 
			else {

				Train car = e.car;

				if (car.getMiles() > 700){
					inspection.add(car);
				}
				else{

					if (car.getDestination().equals("Trenton")){
						if(weightA + car.getWeight() > limitTrackA){
							depart("ENG00000", "Trenton", trackA);
							weightA = 0;
						}

						if (car.getWeight() <= limitTrackA){
							trackA.add(car);
							weightA += car.getWeight();
						}
						else{
							System.out.println(car.getName() + " exceeds Trenton track limit");
						}
					}
					else if (car.getDestination().equals("Charlotte")){

						if (weightB + car.getWeight() > limitTrackB){
							depart("ENG00000", "Charlotte", trackB);
							weightB = 0;
						}

						if(car.getWeight() <= limitTrackB){
							trackB.add(car);
							weightB += car.getWeight();
						}
						else {
							System.out.println(car.getName() + " exceeds Charlotte track limit");
						}
					}
					else if (car.getDestination().equals("Baltimore")){

						if (weightC + car.getWeight() > limitTrackC){
							depart("ENG00000", "Baltimore", trackC);
							weightC = 0;
						}

						if(car.getWeight() <= limitTrackC){
							trackC.add(car);
							weightC += car.getWeight();
						}
						else {
							System.out.println(car.getName() + " exceeds Baltimore track limit");
						}
					}
					else {
						trackD.push(car);
					}
				}
			}
		}

		while(!inspection.isEmpty()){
			Train car = inspection.remove();
			car.setMiles(100);

			if (car.getDestination().equals("Trenton")){
				if(weightA + car.getWeight() > limitTrackA){
					depart("ENG00000", "Trenton", trackA);
					weightA = 0;
				}

				if (car.getWeight() <= limitTrackA){
					trackA.add(car);
					weightA += car.getWeight();
				}
				else {
					System.out.println(car.getName()+ " exceeds Trenton track limit");
				}
			}

			else if (car.getDestination().equals("Charlotte")){
				if(weightB + car.getWeight() > limitTrackB){
					depart("ENG00000", "Charlotte", trackB);
					weightB = 0;
				}

				if (car.getWeight() <= limitTrackB){
					trackB.add(car);
					weightB += car.getWeight();
				}
				else {
					System.out.println(car.getName()+ " exceeds Charlotte track limit");
				}
			}

			else if (car.getDestination().equals("Baltimore")){
				if(weightC + car.getWeight() > limitTrackC){
					depart("ENG00000", "Baltimore", trackC);
					weightC = 0;
				}

				if (car.getWeight() <= limitTrackC){
					trackC.add(car);
					weightC += car.getWeight();
				}
				else {
					System.out.println(car.getName()+ " exceeds Baltimore track limit");
				}
			}
			else{
				trackD.push(car);
			}
		}

		printStationStatus(trackC, trackB, trackA, trackD, inspection);

		depart("ENG00000", "Baltimore", trackC);
		depart("ENG00000", "Charlotte", trackB);
		depart("ENG00000", "Trenton", trackA);
	
	}

	private static void depart(String engineId, String city, Queue<Train> track){
		if (track.isEmpty()) return;

		System.out.println(engineId + " leaving for " + city + " with the following cars:");

		while (!track.isEmpty()){
			Train car = track.remove();
			System.out.println(car.getName() + " containing " + car.getProduct());
		}
	}

	private static void printStationStatus(Queue<Train> baltimore, Queue<Train> charlotte,
										   Queue<Train> trenton, Stack<Train> other,
										   Queue<Train> inspection){

		System.out.println();
		System.out.println("Baltimore\tCharlotte\tTrenton\tOther Destinations\tOverweight");

		System.out.println(
            listIds(baltimore) + " " +
            listIds(charlotte) + " " +
            listIds(trenton) + " " +
            listIdsStack(other) + " " +
            (inspection.isEmpty() ? "" : "SHOULD BE EMPTY")
        );

        System.out.println();
	}

	private static String listIds(Queue<Train> track) {
        if (track.isEmpty()) return "";

        int n = track.size();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            Train car = track.remove();
            sb.append(car.getName());
            if (i < n - 1) sb.append(", ");
            track.add(car);
        }
        return sb.toString();
    }

	private static String listIdsStack(Stack<Train> track) {
        if (track.isEmpty()) return "";

        Stack<Train> temp = new Stack<>();
        StringBuilder sb = new StringBuilder();

        while (!track.isEmpty()) {
            temp.push(track.pop());
        }

        while (!temp.isEmpty()) {
            Train car = temp.pop();
            sb.append(car.getName());
            if (!temp.isEmpty()) sb.append(", ");
            track.push(car);
        }

        return sb.toString();
    }//end
}//main
