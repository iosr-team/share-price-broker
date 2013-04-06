package pl.edu.agh.iosr.fetchprices;

import java.util.Date;

public class SharePrice {
	Date date;

	public Date getDate() {
		return date;
	}

	public double getValue() {
		return value;
	}

	double value;

	public SharePrice(Date date, double value) {
		super();
		this.date = date;
		this.value = value;
	}

}
