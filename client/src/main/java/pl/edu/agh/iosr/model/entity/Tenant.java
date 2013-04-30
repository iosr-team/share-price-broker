package pl.edu.agh.iosr.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TENANT")
public class Tenant {
    public static final String SUPERUSER_TENANT_NAME = "SUPERUSER_TENANT";

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String description;

    private Boolean enabled;

	@ManyToMany
	@JoinTable(
			name = "TENANT_STOCK_INDICIES", 
			joinColumns = { @JoinColumn(name = "TENANT_ID", referencedColumnName = "ID") }, 
			inverseJoinColumns = { @JoinColumn(name = "STOCK_INDEX_ID", referencedColumnName = "ID") })
	private List<StockIndex> observedStockIndicies;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<StockIndex> getObservedStockIndicies() {
		return observedStockIndicies;
	}

	public void setObservedStockIndicies(List<StockIndex> observedStockIndicies) {
		this.observedStockIndicies = observedStockIndicies;
	}
	
	public String toString(){
		return this.getDescription();
	}

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSuperuser(){
        return SUPERUSER_TENANT_NAME.equals(this.name);
    }
}
