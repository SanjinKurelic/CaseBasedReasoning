package cbrd.generator;

import java.util.Random;

import cbrd.model.DataModel;

public class PlansChangedGenerator extends AbstractGenerator {

	public PlansChangedGenerator(Random random) {
		super(random);
	}

	@Override
	public void generateItem(DataModel data) {
		int predict = 1, offset = 0;

		if (data.isHboGoStream() || data.isNetflixStream() || data.isPickboxStream() || data.isYoutubeStream()) {
			offset = 4;
			predict = 2;
		} else if (data.isWhatsappFree() || data.isViberFree()) {
			offset = 3;
			predict = 1;
		} else if (data.getDataMBPerMonth() != 0) {
			if (data.getDataMBPerMonth() > 1000) {
				if (data.getDataMBPerMonth() > 5000) {
					offset = 3;
					predict = 5;
				} else {
					offset = 2;
					predict = 5;
				}
			}
			if (data.getSmsCountPerMonth() > 1000 || data.getCallMinutePerMonth() > 1000) {
				offset = 2;
				predict = 4;
			} else if (data.getCustomerAge() > 28) {
				offset = 1;
				predict = 2;
			} else {
				offset = 1;
				predict = 3;
			}
		} else if (data.getSmsCountPerMonth() != 0 || data.getCallMinutePerMonth() != 0){
			if (data.getSmsCountPerMonth() > 1000 || data.getCallMinutePerMonth() > 1000) {
				predict = 3;
			} else {
				predict = 2;
			}
		}

		int plansChanged = random.nextInt(predict) + offset;

		if (statistics.containsKey(String.valueOf(plansChanged))) {
			statistics.put(String.valueOf(plansChanged), statistics.get(String.valueOf(plansChanged)) + 1);
		} else {
			statistics.put(String.valueOf(plansChanged), 1);
		}
		data.setCustomerPlansChanged(plansChanged);
	}

	@Override
	protected String getName() {
		return "PLANS CHANGED";
	}

}
