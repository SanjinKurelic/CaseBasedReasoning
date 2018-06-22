package cbrd.generator;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import cbrd.model.DataModel;

public abstract class AbstractGenerator {
	
	protected Random random;
	protected TreeMap<String, Integer> statistics;
	
	public AbstractGenerator(Random random) {
		this.random = random;
		statistics = new TreeMap<>(); 
	}
	
	public abstract void generateItem(DataModel data);
	
	protected abstract String getName();
	
	public String printStats() {
		StringBuilder out = new StringBuilder();
		out.append("\n");
		out.append(getName());
		out.append(" STAST:\n");
		for(Map.Entry<String, Integer> stats : statistics.entrySet()) {
			out.append(stats.getKey());
			out.append(" => ");
			out.append(stats.getValue());
			out.append("\n");
		}
		return out.toString();
	}

}
