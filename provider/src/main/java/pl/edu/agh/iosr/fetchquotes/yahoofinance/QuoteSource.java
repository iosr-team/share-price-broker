package pl.edu.agh.iosr.fetchquotes.yahoofinance;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

public interface QuoteSource {
	BufferedReader getBufferedReader(Collection<String> companiesSymbols) throws IOException;
}
