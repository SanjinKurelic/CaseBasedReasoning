package cbrd.generator;

import java.util.Random;

import cbrd.model.DataModel;

public class InternetGenerator extends AbstractGenerator {

	private static final String NO_PLAN = "NO PLAN";
	private static final String PLAN_100 = "100 MB";
	private static final String PLAN_200 = "200 MB";
	private static final String PLAN_300 = "300 MB";
	private static final String PLAN_500 = "500 MB";
	
	private static final String PLAN_1000 = "1 GB";
	private static final String PLAN_2000 = "2 GB";
	private static final String PLAN_3000 = "3 GB";
	private static final String PLAN_5000 = "5 GB";
	
	private static final String PLAN_10000 = "10 GB";
	private static final String PLAN_15000 = "15 GB";
	private static final String PLAN_20000 = "20 GB";
	private static final String PLAN_25000 = "25 GB";
	private static final String PLAN_50000 = "50 GB";
	private static final String PLAN_60000 = "60 GB";
	
	private static final String UNLIMITED_PLAN = "UNLIMITED PLAN";
	
	public InternetGenerator(Random random) {
		super(random);
		statistics.put(NO_PLAN, 0);
		statistics.put(PLAN_100, 0);
		statistics.put(PLAN_200, 0);
		statistics.put(PLAN_300, 0);
		statistics.put(PLAN_500, 0);
		statistics.put(PLAN_1000, 0);
		statistics.put(PLAN_2000, 0);
		statistics.put(PLAN_3000, 0);
		statistics.put(PLAN_5000, 0);
		statistics.put(PLAN_10000, 0);
		statistics.put(PLAN_15000, 0);
		statistics.put(PLAN_20000, 0);
		statistics.put(PLAN_25000, 0);
		statistics.put(PLAN_50000, 0);
		statistics.put(PLAN_60000, 0);
		statistics.put(UNLIMITED_PLAN, 0);
	}
	
	protected int getPlanSize(int planCategory) {
		switch(planCategory) {
			case 0:
				statistics.put(NO_PLAN, statistics.get(NO_PLAN) + 1);
				return 0;
			case 1:
				statistics.put(PLAN_100, statistics.get(PLAN_100) + 1);
				return 100;
			case 2:
				statistics.put(PLAN_200, statistics.get(PLAN_200) + 1);
				return 200;
			case 3:
				statistics.put(PLAN_300, statistics.get(PLAN_300) + 1);
				return 300;
			case 4:
				statistics.put(PLAN_500, statistics.get(PLAN_500) + 1);
				return 500;
			case 5:
				statistics.put(PLAN_1000, statistics.get(PLAN_1000) + 1);
				return 1024;
			case 6:
				statistics.put(PLAN_2000, statistics.get(PLAN_2000) + 1);
				return 2 * 1024;
			case 7:
				statistics.put(PLAN_3000, statistics.get(PLAN_3000) + 1);
				return 3 * 1024;
			case 8:
				statistics.put(PLAN_5000, statistics.get(PLAN_5000) + 1);
				return 5 * 1024;
			case 10:
				statistics.put(PLAN_10000, statistics.get(PLAN_10000) + 1);
				return 10 * 1024;
			case 11:
				statistics.put(PLAN_15000, statistics.get(PLAN_15000) + 1);
				return 15 * 1024;
			case 12:
				statistics.put(PLAN_20000, statistics.get(PLAN_20000) + 1);
				return 20 * 1024;
			case 13:
				statistics.put(PLAN_25000, statistics.get(PLAN_25000) + 1);
				return 25 * 1024;
			case 14:
				statistics.put(PLAN_50000, statistics.get(PLAN_50000) + 1);
				return 50 * 1024;
			case 15:
				statistics.put(PLAN_60000, statistics.get(PLAN_60000) + 1);
				return 60 * 1024;
			case 9:
			default:
				statistics.put(UNLIMITED_PLAN, statistics.get(UNLIMITED_PLAN) + 1);
				return 10 * 1024 * 1024; // 10 TB
		}
	}

	@Override
	public void generateItem(DataModel data) {
		int planSize;
		if(data.getCustomerAge() > 40) {
			planSize = getPlanSize(0);
		} else if(data.getCustomerAge() < 25) {
			int offset = 10, rnd = random.nextInt(1000);
			if(rnd < 900) {
				offset = 5;
			}
			planSize = getPlanSize(random.nextInt(5) + offset);
		} else {
			int rnd = random.nextInt(1000);
			if((rnd < 400) && (data.getCallMinutePerMonth() < 1000 || data.getSmsCountPerMonth() < 1000)) { // MB category
				planSize = getPlanSize(random.nextInt(4));
			} else if(rnd < 650) { // GB category
				planSize = getPlanSize(random.nextInt(3) + 5);
			} else { // big category
				planSize = getPlanSize(random.nextInt(6) + 9);
			}
		}
		data.setDataMBPerMonth(planSize);
	}

	@Override
	protected String getName() {
		return "DATA";
	}

}
