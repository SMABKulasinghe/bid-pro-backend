package lk.supplierUMS.SupplierUMS_REST.entity;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="t_financial_per_tender_params")
public class FinancialDetailsPerTender implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="tender_id")
	private Long tenderID;
	
	@Column(name="financial_id")
	private Long financialCodeId;
	
	@Column(name="cappex")
	private String cappex;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTenderID() {
		return tenderID;
	}

	public void setTenderID(Long tenderID) {
		this.tenderID = tenderID;
	}

	public String getCappex() {
		return cappex;
	}

	public void setCappex(String cappex) {
		this.cappex = cappex;
	}

	public Long getFinancialCodeId() {
		return financialCodeId;
	}

	public void setFinancialCodeId(Long financialCodeId) {
		this.financialCodeId = financialCodeId;
	}
	
	


}
