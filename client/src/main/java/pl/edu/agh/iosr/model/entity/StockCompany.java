package pl.edu.agh.iosr.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STOCK_INDEX")
public class StockCompany {
	/*
	 * @Id
	 * 
	 * @GeneratedValue private Long id;
	 */

	@Id
	private String symbol;

	private String name;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
