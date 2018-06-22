package cbrd.generator;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import cbrd.main.Main;
import cbrd.model.DataModel;

public class DataGenerator {
	
	private static final int MISSING_COUNT = 6;
	
	private ArrayList<DataModel> data;
	private Random random;
	private StringBuilder output;
	private ArrayList<AbstractGenerator> generators;
	private TreeMap<String, Integer> missing;
	
	public DataGenerator() {
		data = new ArrayList<>();
		random = new Random();
		generators = new ArrayList<>();
		
		generators.add(new AgeGenerator(random));
		generators.add(new SmsCountGenerator(random));
		generators.add(new CallCountGenerator(random));
		generators.add(new InternetGenerator(random));
		generators.add(new StreamGenerator(random));
		generators.add(new ChatGenerator(random));
		generators.add(new PlansChangedGenerator(random));
	}
	
	private void addStringData(String data) {
		output.append(data);
		output.append(Main.CSV_DELIMITER);
	}
	
	private void printHeader() {
		addStringData("customerId");
		addStringData("customerAge");
		addStringData("customerPlansChanged");
		addStringData("smsCountPerMonth");
		addStringData("callMinutePerMonth");
		addStringData("dataMBPerMonth");
		addStringData("netflixStream");
		addStringData("pickboxStream");
		addStringData("youtubeStream");
		addStringData("hboGoStream");
		addStringData("viberFree");
		output.append("whatsappFree\n");
	}
	
	public String printModels() {
		output = new StringBuilder();
		printHeader();
		for(DataModel m : data) {
			output.append(m);
			output.append("\n");
		}
		return output.toString();
	}
	
	public String printStats() {
		output = new StringBuilder();
		output.append("MISSING VALUES\n\n");
		for(Map.Entry<String, Integer> miss : missing.entrySet()) {
			output.append(miss.getKey());
			output.append(": ");
			output.append(miss.getValue());
			output.append("\n");
		}
		
		for(AbstractGenerator generator : generators) {
			output.append(generator.printStats());
		}
		return output.toString();
	}
	
	private void removeMissing() {
		DataModel model;		
		int dataSize = data.size();
		int removeCounter = MISSING_COUNT;		
		missing = new TreeMap<>();
		
		for(int i = dataSize - MISSING_COUNT; i < dataSize; i++) {
			model = data.get(i);
			
			if(removeCounter <= (MISSING_COUNT / 2)) {
				missing.put("AGE OF CUSTOMER #" + model.getCustomerId(), model.getCustomerAge());
				model.setCustomerAge(null);
			} else {
				missing.put("PLANS CHANGED OF CUSTOMER #" + model.getCustomerId(), model.getCustomerPlansChanged());
				model.setCustomerPlansChanged(null);
			}
			data.set(i, model);
			removeCounter--;
		}
	}
	
	public void generate(int numberOfData) {
		DataModel model;
		
		for(int i = 0; i < numberOfData; i++) {
			model = new DataModel();
			model.setCustomerId(i + 1);
			for(AbstractGenerator generator : generators) {
				generator.generateItem(model);
			}
			data.add(model);
		}
		
		if(numberOfData > MISSING_COUNT) {
			removeMissing();
		}
		
	}

}
