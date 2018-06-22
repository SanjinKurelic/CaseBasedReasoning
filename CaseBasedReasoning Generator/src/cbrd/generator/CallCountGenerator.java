package cbrd.generator;

import java.util.Random;

import cbrd.model.DataModel;

public class CallCountGenerator extends SmsCountGenerator {

	public CallCountGenerator(Random random) {
		super(random);
	}
	
	private int getCallCount(int planCategory) {
		int val = super.getSmsCount(planCategory);
		if(val > 5000) {
			val = 44640;
		}
		return val;
	}
	
	@Override
	public void generateItem(DataModel data) {
		int planCategory = getCallCount(random.nextInt(8));
		if(data.getCustomerAge() >= 40) {
			int rnd = random.nextInt(1000);
			if(rnd < 750) {
				planCategory = getCallCount(0); // NO PLAN
			} else if (rnd < 950){
				planCategory = getCallCount(1); // 300
			} else {
				planCategory = getCallCount(2); // 500
			}
		} else {
			if(data.getSmsCountPerMonth() < 1000) {
				planCategory = data.getSmsCountPerMonth();
			} else if(data.getSmsCountPerMonth() > 5000) {
				planCategory = 44640;
			} else {
				planCategory = getCallCount(random.nextInt(4) + 3);
			}
		}
		data.setCallMinutePerMonth(planCategory);
	}
	
	@Override
	protected String getName() {
		return "TALK MINUTE/MONTH ";
	}

}
