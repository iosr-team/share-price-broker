package pl.edu.agh.iosr.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "STOCK_QUOTE")
public class StockQuote {
	@Id
	@GeneratedValue
	private Long id;

    private String companyName;

	private Date date;
	
	private Double value;

    private Double change;

	@ManyToOne
	@JoinColumn(name = "TENANT_ID")
	private Tenant tenant;
	
	@ManyToOne
	@JoinColumn(name = "STOCK_INDEX_ID")
	private StockIndex stockIndex;
	
	//TODO: other params
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public StockIndex getStockIndex() {
		return stockIndex;
	}

	public void setStockIndex(StockIndex stockIndex) {
		this.stockIndex = stockIndex;
	}

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
