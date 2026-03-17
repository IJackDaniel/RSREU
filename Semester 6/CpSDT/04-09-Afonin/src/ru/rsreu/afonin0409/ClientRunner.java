package ru.rsreu.afonin0409;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

public class ClientRunner {
	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();
	private static final int[] SIZES = new int[] {5, 4, 4, 3, 6, 5};
	private static final int LOWER_BOUND = -5;
	private static final int UPPER_BOUND = 5;
	
	public static void main(String[] args) {
		A a = new A(ClientRunner.SIZES);
		a.fillRandomValuesFromInterval(ClientRunner.LOWER_BOUND, ClientRunner.UPPER_BOUND);
		
		// Add original two dimension array to stringBuilder
		
		a.method();
		
		// Add processed two dimension array to stringBuilder
		System.out.println(a.toString());
	}

}
