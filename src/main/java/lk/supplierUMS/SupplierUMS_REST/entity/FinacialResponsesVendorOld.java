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

/**
 * @author ACER
 *
 */
@Entity
@Table(name="t_finacial_responses_old")
public class FinacialResponsesVendorOld implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="tender_id")
	private Long tenderID;
	
	@Column(name = "amount_lkr")
	private Double amountlkr;
	
	@Column(name = "amount_usd")
	private Double amountusd;
	
	@Column(name = "exchange_rate")
	private Double exchangerate;
	
	@Column(name = "user_id")
	private String userID;

	@Column(name = "subcompany_code")
	private Long subCompanyCode;
	
	@Column(name = "submit_date_time")
	private LocalDateTime SubmitDateTime;
	
	@Column(name = "rfp_id")
	private Double RFPID;
	
	@Column(name="financial_document")
	private String financialDocument;
	
	@Column(name = "t_finacial_responses_id")
	private Long financialResponsesId;
	
	public Long getFinancialResponsesId() {
		return financialResponsesId;
	}

	public void setFinancialResponsesId(Long financialResponsesId) {
		this.financialResponsesId = financialResponsesId;
	}

	public String getFinancialDocument() {
		return financialDocument;
	}

	public void setFinancialDocument(String financialDocument) {
		this.financialDocument = financialDocument;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTenderID() {
		return tenderID;
	}

	public void setTenderID(Long tenderID) {
		this.tenderID = tenderID;
	}

	public Double getAmountlkr() {
		return amountlkr;
	}

	public void setAmountlkr(Double amountlkr) {
		this.amountlkr = amountlkr;
	}

	public Double getAmountusd() {
		return amountusd;
	}

	public void setAmountusd(Double amountusd) {
		this.amountusd = amountusd;
	}

	public Double getExchangerate() {
		return exchangerate;
	}

	public void setExchangerate(Double exchangerate) {
		this.exchangerate = exchangerate;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Long getSubCompanyCode() {
		return subCompanyCode;
	}

	public void setSubCompanyCode(Long subCompanyCode) {
		this.subCompanyCode = subCompanyCode;
	}

	public LocalDateTime getSubmitDateTime() {
		return SubmitDateTime;
	}

	public void setSubmitDateTime(LocalDateTime submitDateTime) {
		SubmitDateTime = submitDateTime;
	}

	public Double getRFPID() {
		return RFPID;
	}

	public void setRFPID(Double rFPID) {
		RFPID = rFPID;
	}

	
	
	
}
