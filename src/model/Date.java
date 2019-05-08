package model;

import java.util.Comparator;

public class Date implements Comparator<Flight>{
	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Flight f1, Flight f2) {
		return f1.getDate().compareTo(f2.getDate());
	}

}
