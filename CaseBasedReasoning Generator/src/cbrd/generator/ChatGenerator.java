package cbrd.generator;

import java.util.Random;

import cbrd.model.DataModel;

public class ChatGenerator extends AbstractGenerator {
	
	private static final String HAS_WAP = "WhatsApp (Yes)";
	private static final String NO_WAP = "WhatsApp (No)";
	private static final String HAS_VIBER = "Viber (Yes)";
	private static final String NO_VIBER = "Viber (No)";

	public ChatGenerator(Random random) {
		super(random);
		statistics.put(HAS_WAP, 0);
		statistics.put(NO_WAP, 0);
		statistics.put(HAS_VIBER, 0);
		statistics.put(NO_VIBER, 0);
	}
	
	private void setChatStatistics(boolean wap, boolean viber) {
		if(wap) {
			statistics.put(HAS_WAP, statistics.get(HAS_WAP) + 1);
		} else {
			statistics.put(NO_WAP, statistics.get(NO_WAP) + 1);
		}
		
		if(viber) {
			statistics.put(HAS_VIBER, statistics.get(HAS_VIBER) + 1);
		} else {
			statistics.put(NO_VIBER, statistics.get(NO_VIBER) + 1);
		}
	}

	@Override
	public void generateItem(DataModel data) {
		boolean wap = false, viber = false;
		if(data.getCustomerAge() < 40 && data.getDataMBPerMonth() >= 300) {
			int rnd = random.nextInt(1000);
			
			if(data.getCustomerAge() > 28) {
				if(rnd < 200) {
					viber = true;
				} else if (rnd < 400) {
					wap = true;
				}
			} else {
				if(rnd < 400) {
					viber = true;
				} else if (rnd < 800) {
					wap = true;
				}
			}
		}
		
		setChatStatistics(wap, viber);
		data.setViberFree(viber);
		data.setWhatsappFree(wap);
	}

	@Override
	protected String getName() {
		return "CHAT";
	}

}
