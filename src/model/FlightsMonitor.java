package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FlightsMonitor {
	//Constants
	public final static String AIRLINES = "data/airlines.txt";
	public final static String DESTINATIONS = "data/destinations.txt";

	public final static int SORTED_BY_AIRLINE = 1;
	public final static int SORTED_BY_BOARDING_GATE = 2;
	public final static int SORTED_BY_DATE = 3;
	public final static int SORTED_BY_DESTINATIONS = 4;
	public final static int SORTED_BY_FLIGHT_NUMBER= 5;
	public final static int SORTED_BY_TIME = 6;
	
	//Attributes
	private int sorted;
	private ObservableList<Flight> flights;
	
	/* Method that allows to create a FlightsMonitor object.
	 * @param an ObservableList of Flight type.
	 */
	public FlightsMonitor(ObservableList<Flight> flights) { 
		this.flights = FXCollections.observableArrayList();
	}
	/* Method that generates a determinate number of flights randomly.
	 * @param an integer that represents the number of flights to generate.
	 * @return an ObservableList of Flight type.
	 */
	public ObservableList<Flight> generateFlights(int n) throws IOException {
		flights.clear();
		String airline = "";
		String destinations = "";
		int boardGate = 0;
		int flightNumber = 0;
		Calendar date = null;
		String time = null;
		int numLineas=100;
		int contador=0;
		String airLineData []=new String [numLineas];
		BufferedReader reader = new BufferedReader(new FileReader(AIRLINES));
		String linea = reader.readLine();
		while (linea != null && contador<numLineas)
		{
			airLineData[contador]=linea;
			linea = reader.readLine();
			contador++;
		}
		reader.close();
		int numLineas2=196;
		int contador2=0;
		String destinationsData []=new String [numLineas2];
		BufferedReader reader2 = new BufferedReader(new FileReader(DESTINATIONS));
		String linea2 = reader2.readLine();
		while (linea2 != null && contador2<numLineas2)
		{
			destinationsData[contador2]=linea2;
			linea2 = reader2.readLine();
			contador2++;
		}
		reader2.close();
		for (int i = 0; i < n; i++ ) {
			boardGate = (int) (Math.random() * 30) + 1;
			flightNumber = (int) (Math.random() * 10000000) + 1;
			Random random = new Random();
			date = Calendar.getInstance();
			date.set (random.nextInt(10)+2019, random.nextInt(12)+1, random.nextInt(30)+1);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMMM/yyyy");
			int h = (int) (Math.random() * 12) + 1;
			int m = (int) (Math.random() * 59) + 1;
			if (i%2==0) {
				time = h + ":" + m + " PM";
			} else if (i%2 != 0) {
				time = h + ":" + m + " AM";
			}
			destinations = (String) destinationsData[(int) (Math.random() * 195) + 1];
			airline = (String) airLineData[(int) (Math.random() * 99) + 1];
			Flight f = new Flight(airline, sdf.format(date.getTime()), time, destinations, flightNumber, boardGate);
			flights.add(f);
		}
		return flights;
	}

	/* Method that allows to obtain an integer that represent the list state.
	 * @return an integer that represent the list state.
	 */
	public int getSorted() {
		return sorted;
	}
	/* Method that allows to set an integer that represent the list state.
	 * @param an integer that represent the list state.
	 */
	public void setSorted(int sorted) {
		this.sorted = sorted;
	}
	/* Method that allows to obtain an ObservableList of Flight type.
	 * @return an ObservableList of Flight type.
	 */
	public ObservableList<Flight> getFlights() {
		return flights;
	}
	/* Method that allows to set an ObservableList of Flight type.
	 * @param an ObservableList of Flight type.
	 */
	public void setFlights(ObservableList<Flight> flights) {
		this.flights = flights;
	}
	/* Method that allows to sort the flights list by a determinate criterion.
	 * @param a String that represents the sorting criterion.
	 * @return an integer that represents the sorting list state.
	 */
	public int sort(String criteria) {
		sorted = 0;
		if (criteria.equals("datetimesort")) {
			FXCollections.sort(flights);
			sorted = SORTED_BY_DATE;
		}
		if (criteria.equals("airlinesort")) {
			FXCollections.sort(flights, new Airline());
			sorted = SORTED_BY_AIRLINE;
		}
		if (criteria.equals("boardsort")) {
			FXCollections.sort(flights, new BoardingGate());
			sorted = SORTED_BY_BOARDING_GATE;
		}
		if (criteria.equals("flightsort")) {
			FXCollections.sort(flights, new FlightNumber());
			sorted = SORTED_BY_FLIGHT_NUMBER;
		}
		if (criteria.equals("timesort")) {
			FXCollections.sort(flights, new Time());
			sorted = SORTED_BY_TIME;
		}
		if (criteria.equals("datesort")) {
			FXCollections.sort(flights, new Date());
			sorted = SORTED_BY_DATE;
		}
		if (criteria.equals("dessort")) {
			FXCollections.sort(flights, new Destination());
			sorted = SORTED_BY_DESTINATIONS;
		}
		return sorted;
	}
	/* Method that allows to search a determinate flight by a criterion.
	 * @param a String that represents the searching criterion.
	 * @param a String that represents the word to find in the list.
	 * @return a Flight object.
	 */
	public Flight search(String criteria, String toFind) {
		Flight found = null;
		if(sorted == 0 && criteria.equals("airsearch")) {
			for (int i = 0; i < flights.size(); i++) {
				if(flights.get(i).getAirLine().equalsIgnoreCase(toFind) && found == null) {
					found = flights.get(i);
				}
			}
		}
		if(sorted == 0 && criteria.equals("boardsearch")) {
		if(criteria.equals("boardsearch")) {
			for (int i = 0; i < flights.size(); i++) {
				if(flights.get(i).getBoardingGate() == Integer.parseInt(toFind) && found == null) {
					found = flights.get(i);
				}
			}
		}
		if(sorted == 0 && criteria.equals("datesearch")) {
			for (int i = 0; i < flights.size(); i++) {
				if(flights.get(i).getDate().equalsIgnoreCase(toFind) && found == null) {
					found = flights.get(i);
				}
			}
		}
		if(sorted == 0 && criteria.equals("destinationsearch")) {
			for (int i = 0; i < flights.size(); i++) {
				if(flights.get(i).getDestination().equalsIgnoreCase(toFind) && found == null) {
					found = flights.get(i);
				}
			}
		}
		if(sorted == 0 && criteria.equals("flightsearch")) {
			for (int i = 0; i < flights.size(); i++) {
				if(flights.get(i).getFlightNumber() == Integer.parseInt(toFind) && found == null) {
					found = flights.get(i);
				}
			}
		}
		if(sorted == 0 && criteria.equals("timesearch")) {
			for (int i = 0; i < flights.size(); i++) {
				if(flights.get(i).getTime().equalsIgnoreCase(toFind) && found == null) {
					found = flights.get(i);
				}
			}
		}
		
	}
		return found;
}
}	
