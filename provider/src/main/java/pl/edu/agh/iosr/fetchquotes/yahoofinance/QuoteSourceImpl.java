package pl.edu.agh.iosr.fetchquotes.yahoofinance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class QuoteSourceImpl implements QuoteSource {

	@Override
	public BufferedReader getBufferedReader(Collection<String> companiesSymbols)
			throws IOException {

		String query = StringUtils.join(companiesSymbols, '+');
		URL url = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + query
				+ "&f=sl");
		return new BufferedReader(new InputStreamReader(url.openStream()));
	}

}
