package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="m_reissue_po")

public class Re_issuePO implements Serializable{ // ReIssuePO

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="re_issue_po_id")
	private long reIssuePoId;
	
	@Column(name="tender_id")
	private Long tenderId;
	
	@Column(name="category")
	private String category;

	@Column(name="re_issue_po_no")
	private String reIssuePoNo; // auto number
	
	@Column(name="qty")
	private Long qty; 
	
	@Column(name="unit_price")
	private Long unitPrice; // Double
	
	@CreationTimestamp
    private Date createdAt;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public long getReIssuePoId() {
		return reIssuePoId;
	}

	public void setReIssuePoId(long reIssuePoId) {
		this.reIssuePoId = reIssuePoId;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public String getReIssuePoNo() {
		return reIssuePoNo;
	}

	public void setReIssuePoNo(String reIssuePoNo) {
		this.reIssuePoNo = reIssuePoNo;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}

	
	
	
	
	
}