package pl.edu.agh.iosr.fetchprices;

public interface PriceListener {
	public void newSharePrice(String name, SharePrice sharePrice);
}
