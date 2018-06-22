package cbrd.generator;

import java.util.Random;

import cbrd.model.DataModel;

public class SmsCountGenerator extends AbstractGenerator {
	
	private static final String NO_PLAN = "NO PLAN";
	private static final String PLAN_300 = "PLAN 300";
	private static final String PLAN_500 = "PLAN 500";
	private static final String PLAN_1000 = "PLAN 1000";
	private static final String PLAN_2000 = "PLAN 2000";
	private static final String PLAN_3000 = "PLAN 3000";
	private static final String PLAN_5000 = "PLAN 5000";
	private static final String UNLIMITED_PLAN = "PLAN FLAT";

	public SmsCountGenerator(Random random) {
		super(random);
		statistics.put(NO_PLAN, 0);
		statistics.put(PLAN_300, 0);
		statistics.put(PLAN_500, 0);
		statistics.put(PLAN_1000, 0);
		statistics.put(PLAN_2000, 0);
		statistics.put(PLAN_3000, 0);
		statistics.put(PLAN_5000, 0);
		statistics.put(UNLIMITED_PLAN, 0);
	}

	@Override
	protected String getName() {
		return "SMS COUNT";
	}
	
	protected int getSmsCount(int planCategory) {
		switch(planCategory) {
			case 0: 
				statistics.put(NO_PLAN, statistics.get(NO_PLAN) + 1);
				return 0;
			case 1:
				statistics.put(PLAN_300, statistics.get(PLAN_300) + 1);
				return 300;
			case 2: 
				statistics.put(PLAN_500, statistics.get(PLAN_500) + 1);
				return 500;
			case 3: 
				statistics.put(PLAN_1000, statistics.get(PLAN_1000) + 1);
				return 1000;
			case 4: 
				statistics.put(PLAN_2000, statistics.get(PLAN_2000) + 1);
				return 2000;
			case 5: 
				statistics.put(PLAN_3000, statistics.get(PLAN_3000) + 1);
				return 3000;
			case 6: 
				statistics.put(PLAN_5000, statistics.get(PLAN_5000) + 1);
				return 5000;
			case 7: default: 
				statistics.put(UNLIMITED_PLAN, statistics.get(UNLIMITED_PLAN) + 1);
				return 2678400;
		}
	}

	@Override
	public void generateItem(DataModel data) {
		int planCategory = getSmsCount(random.nextInt(8));
		if(data.getCustomerAge() >= 40) {
			int rnd = random.nextInt(1000);
			if(rnd < 750) {
				planCategory = getSmsCount(0); // NO PLAN
			} else if (rnd < 950){
				planCategory = getSmsCount(1); // 300
			} else {
				planCategory = getSmsCount(2); // 500
			}
		}
		data.setSmsCountPerMonth(planCategory);
	}
	
}
