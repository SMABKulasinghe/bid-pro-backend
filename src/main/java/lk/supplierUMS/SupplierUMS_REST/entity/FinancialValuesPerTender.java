package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="t_financial_values_per_tender")
public class FinancialValuesPerTender implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="financial_values_per_tender_id")
	private Long financialId;
	
	@Column(name="t_financial_per_tender_params_id")
	private Long financialPerTenderParamID;
	
	@Column(name="tender_id")
	private Long tenderId;
	
	@Column(name="supplier_id")
	private Long supplierId;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="created_date_time")
	private LocalDateTime createdDateTime;
	
	@Column(name="comment")
	private String comment;
	
	@Column(name="cost_description")
	private String costDescription;

	@Column(name="cappex")
	private String cappex;
	
	@Column(name="currency")
	private String currency;
	
	
	public String getCostDescription() {
		return costDescription;
	}

	public void setCostDescription(String costDescription) {
		this.costDescription = costDescription;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getFinancialId() {
		return financialId;
	}

	public void setFinancialId(Long financialId) {
		this.financialId = financialId;
	}

	public Long getFinancialPerTenderParamID() {
		return financialPerTenderParamID;
	}

	public void setFinancialPerTenderParamID(Long financialPerTenderParamID) {
		this.financialPerTenderParamID = financialPerTenderParamID;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCappex() {
		return cappex;
	}

	public void setCappex(String cappex) {
		this.cappex = cappex;
	}

	
	
	

}
