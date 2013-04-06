package pl.edu.agh.iosr.fetchprices;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSharePriceSource implements SharePriceSource {
	List<PriceListener> listeners = new ArrayList<PriceListener>();
	
	@Override
	public void addPriceListener(PriceListener priceListener) {
		listeners.add(priceListener);
	}

	protected void newSharePrice(String name, SharePrice sharePrice) {
		for(PriceListener listener : listeners)
			listener.newSharePrice(name, sharePrice);
	}
}
