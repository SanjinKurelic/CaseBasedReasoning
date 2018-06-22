package cbrd.main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import cbrd.generator.DataGenerator;

public class Main {
	
	public static final char CSV_DELIMITER = ',';

	public static void main(String[] args) throws FileNotFoundException {
		DataGenerator dg = new DataGenerator();
		PrintWriter writer = new PrintWriter("telecom.csv");
		
		dg.generate(4000);
		
		writer.write(dg.printModels());
		writer.flush();
		writer.close();
		
		writer = new PrintWriter("telecomStats.txt");
		writer.write(dg.printStats());
		writer.flush();
		writer.close();
		
		System.out.println("Generated");
	}

}
