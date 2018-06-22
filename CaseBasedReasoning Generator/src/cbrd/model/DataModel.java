package cbrd.model;

import cbrd.main.Main;

public class DataModel {
	
	private int customerId;
	
	// 12 - 150
	private Integer customerAge;
	
	// how many plans has customer already changed
	private Integer customerPlansChanged;
	
	// max is 2 678 400 (1 SMS per second)
	private int smsCountPerMonth;
	
	// max is 44 640 (number of minutes in 31 day)
	private int callMinutePerMonth;
	
	// max is 10 485 760 (10 TB)
	private int dataMBPerMonth;
	
	private boolean netflixStream;
	
	private boolean pickboxStream;
	
	private boolean youtubeStream;
	
	private boolean hboGoStream;
	
	private boolean viberFree;
	
	private boolean whatsappFree;
	
	private StringBuilder output;
	
	private void addStringData(Object data) {
		output.append(data);
		output.append(Main.CSV_DELIMITER);
	}
	
	@Override
	public String toString() {
		if(output != null) {
			return output.toString();
		}
		
		output = new StringBuilder();
		addStringData(customerId);
		addStringData(customerAge);
		addStringData(customerPlansChanged);
		addStringData(smsCountPerMonth);
		addStringData(callMinutePerMonth);
		addStringData(dataMBPerMonth);
		addStringData(netflixStream);
		addStringData(pickboxStream);
		addStringData(youtubeStream);
		addStringData(hboGoStream);
		addStringData(viberFree);
		output.append(whatsappFree);
		return output.toString();
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public Integer getCustomerAge() {
		return customerAge;
	}

	public void setCustomerAge(Integer customerAge) {
		this.customerAge = customerAge;
	}

	public Integer getCustomerPlansChanged() {
		return customerPlansChanged;
	}

	public void setCustomerPlansChanged(Integer customerPlansChanged) {
		this.customerPlansChanged = customerPlansChanged;
	}

	public int getSmsCountPerMonth() {
		return smsCountPerMonth;
	}

	public void setSmsCountPerMonth(int smsCountPerMonth) {
		this.smsCountPerMonth = smsCountPerMonth;
	}

	public int getCallMinutePerMonth() {
		return callMinutePerMonth;
	}

	public void setCallMinutePerMonth(int callMinutePerMonth) {
		this.callMinutePerMonth = callMinutePerMonth;
	}

	public int getDataMBPerMonth() {
		return dataMBPerMonth;
	}

	public void setDataMBPerMonth(int dataMBPerMonth) {
		this.dataMBPerMonth = dataMBPerMonth;
	}

	public boolean isNetflixStream() {
		return netflixStream;
	}

	public void setNetflixStream(boolean netflixStream) {
		this.netflixStream = netflixStream;
	}

	public boolean isPickboxStream() {
		return pickboxStream;
	}

	public void setPickboxStream(boolean pickboxStream) {
		this.pickboxStream = pickboxStream;
	}

	public boolean isYoutubeStream() {
		return youtubeStream;
	}

	public void setYoutubeStream(boolean youtubeStream) {
		this.youtubeStream = youtubeStream;
	}

	public boolean isHboGoStream() {
		return hboGoStream;
	}

	public void setHboGoStream(boolean hboGoStream) {
		this.hboGoStream = hboGoStream;
	}

	public boolean isViberFree() {
		return viberFree;
	}

	public void setViberFree(boolean viberFree) {
		this.viberFree = viberFree;
	}

	public boolean isWhatsappFree() {
		return whatsappFree;
	}

	public void setWhatsappFree(boolean whatsappFree) {
		this.whatsappFree = whatsappFree;
	}

}
