package cbrd.generator;

import java.util.Random;

import cbrd.model.DataModel;

public class StreamGenerator extends AbstractGenerator {
	
	private static final String HAS_NETFLIX = "Netflix (Yes)";
	private static final String NO_NETFLIX = "Netflix (No)";
	private static final String HAS_PICKBOX = "Pickbox (Yes)";
	private static final String NO_PICKBOX = "Pickbox (No)";
	private static final String HAS_YOUTUBE = "Youtube (Yes)";
	private static final String NO_YOUTUBE = "Youtube (No)";
	private static final String HAS_HBOGO = "HBO GO (Yes)";
	private static final String NO_HBOGO = "HBO GO (No)";

	public StreamGenerator(Random random) {
		super(random);
		statistics.put(HAS_NETFLIX, 0);
		statistics.put(NO_NETFLIX, 0);
		statistics.put(HAS_PICKBOX, 0);
		statistics.put(NO_PICKBOX, 0);
		statistics.put(HAS_YOUTUBE, 0);
		statistics.put(NO_YOUTUBE, 0);
		statistics.put(HAS_HBOGO, 0);
		statistics.put(NO_HBOGO, 0);
	}
	
	private boolean hasItem() {
		return random.nextBoolean();
	}
	
	private void setStreamStatistics(boolean netflix, boolean pickbox, boolean hbo, boolean youtube) {
		if(netflix) {
			statistics.put(HAS_NETFLIX, statistics.get(HAS_NETFLIX) + 1);
		} else {
			statistics.put(NO_NETFLIX, statistics.get(NO_NETFLIX) + 1);
		}
		
		if(pickbox) {
			statistics.put(HAS_PICKBOX, statistics.get(HAS_PICKBOX) + 1);
		} else {
			statistics.put(NO_PICKBOX, statistics.get(NO_PICKBOX) + 1);
		}
		
		if(hbo) {
			statistics.put(HAS_HBOGO, statistics.get(HAS_HBOGO) + 1);
		} else {
			statistics.put(NO_HBOGO, statistics.get(NO_HBOGO) + 1);
		}
		
		if(youtube) {
			statistics.put(HAS_YOUTUBE, statistics.get(HAS_YOUTUBE) + 1);
		} else {
			statistics.put(NO_YOUTUBE, statistics.get(NO_YOUTUBE) + 1);
		}
	}

	@Override
	public void generateItem(DataModel data) {
		boolean netflix = false, pickbox = false, youtube = false, hbo = false;
		if(data.getCustomerAge() < 35 && data.getCustomerAge() > 18 && data.getDataMBPerMonth() > 300) {
			int rnd = random.nextInt(1000);
			if(rnd > 439) {
				if(rnd < 626) {
					netflix = true;
				} else if(rnd < 813) {
					pickbox = true;
				} else {
					hbo = true;
				}
			}
			if(hasItem()) {
				youtube = true;
			}
		}
		
		setStreamStatistics(netflix, pickbox, hbo, youtube);
		data.setNetflixStream(netflix);
		data.setYoutubeStream(youtube);
		data.setPickboxStream(pickbox);
		data.setHboGoStream(hbo);
	}

	@Override
	protected String getName() {
		return "STREAM";
	}

}
