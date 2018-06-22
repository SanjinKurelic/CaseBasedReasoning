package cbrd.generator;

import java.util.Random;

import cbrd.model.DataModel;

public class AgeGenerator extends AbstractGenerator {
	
	private static final String TEENAGERS = "14 - 18";
	private static final String YOUNG = "18 - 28";
	private static final String MIDDLEAGE = "28 - 35";
	private static final String MATURE = "35 - 40";
	private static final String OLD = "40+";
	
	public AgeGenerator(Random random) {
		super(random);
		statistics.put(TEENAGERS, 0);
		statistics.put(YOUNG, 0);
		statistics.put(MIDDLEAGE, 0);
		statistics.put(MATURE, 0);
		statistics.put(OLD, 0);
	}
	
	@Override
	public void generateItem(DataModel data) {
		int minAge, maxAge;
		int ageCategory = random.nextInt(1000); // 0 - 1000 random
		
		if(ageCategory < 180) { // 14 - 18
			minAge = 14;
			maxAge = 18;
			statistics.put(TEENAGERS, statistics.get(TEENAGERS) + 1);
		} else if(ageCategory < 600) { // 18 - 28
			minAge = 18;
			maxAge = 28;
			statistics.put(YOUNG, statistics.get(YOUNG) + 1);
		} else if(ageCategory < 800) { // 28 - 35
			minAge = 28;
			maxAge = 35;
			statistics.put(MIDDLEAGE, statistics.get(MIDDLEAGE) + 1);
		} else if(ageCategory < 900) { // 35 - 40
			minAge = 35;
			maxAge = 40;
			statistics.put(MATURE, statistics.get(MATURE) + 1);
		} else  { // 40+
			minAge = 40;
			maxAge = 75;
			statistics.put(OLD, statistics.get(OLD) + 1);
		}
		data.setCustomerAge(random.nextInt(maxAge - minAge + 1) + minAge);
	}

	@Override
	protected String getName() {
		return "AGE";
	}
	
}
