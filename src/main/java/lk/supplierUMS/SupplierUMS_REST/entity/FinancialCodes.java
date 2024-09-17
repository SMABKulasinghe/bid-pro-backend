package lk.supplierUMS.SupplierUMS_REST.entity;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="t_financial_code")
public class FinancialCodes implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="financial_id")
	private Long financialCodeId;
	
	@Column(name="financial_code")
	private String code;

	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private Long status;
	
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getFinancialCodeId() {
		return financialCodeId;
	}

	public void setFinancialCodeId(Long financialCodeId) {
		this.financialCodeId = financialCodeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



}
