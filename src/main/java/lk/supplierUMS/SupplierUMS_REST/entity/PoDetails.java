package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_po")

public class PoDetails implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="m_id")
	private long mPoId;
	
	@Column(name="m_tender_id")
	private Long mTenderId;
	
	@Column(name="m_rfp_id")
	private Long mRfpId;
	
	@Column(name="m_supplier_code")
	private String mSupplierCode;
	
	@Column(name="m_po_no")
	private String mPoNo;
	
	@Column(name="m_batchfile")
	private String mBatchFile;
	
	@Column(name="m_status")
	private String mStatus;
	
	@Column(name="m_delivery")
	private String mDelivery;
	
	@Column(name="m_warranty")
	private String mWarranty;
	
	@Column(name="m_payment_terms")
	private String mPaymentTerms;
	
	@Column(name="bank_vat_no")
	private String bankVatNo;
	
	@Column(name="m_panelty")
	private Double panelty;
	
	@Column(name="m_po_email_copy")
	private String mPoEmailCopy;
	
	
	
	public String getmPoEmailCopy() {
		return mPoEmailCopy;
	}

	public void setmPoEmailCopy(String mPoEmailCopy) {
		this.mPoEmailCopy = mPoEmailCopy;
	}

	public String getmDelivery() {
		return mDelivery;
	}

	public String getmWarranty() {
		return mWarranty;
	}

	public String getmPaymentTerms() {
		return mPaymentTerms;
	}

	public String getBankVatNo() {
		return bankVatNo;
	}

	

	public void setmDelivery(String mDelivery) {
		this.mDelivery = mDelivery;
	}

	public void setmWarranty(String mWarranty) {
		this.mWarranty = mWarranty;
	}

	public void setmPaymentTerms(String mPaymentTerms) {
		this.mPaymentTerms = mPaymentTerms;
	}

	public void setBankVatNo(String bankVatNo) {
		this.bankVatNo = bankVatNo;
	}

	

	public Long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}

	@Column(name="m_qty")
	private Long mQty;
	
	@Column(name="m_unit_price")
	private Long mUnitPrice;
	
	@Column(name="m_total_cost")
	private Long mTotalCost;
	
	
	
	public String getmSupplierCode() {
		return mSupplierCode;
	}

	public Long getmUnitPrice() {
		return mUnitPrice;
	}

	public Long getmTotalCost() {
		return mTotalCost;
	}

	public void setmUnitPrice(Long mUnitPrice) {
		this.mUnitPrice = mUnitPrice;
	}

	public void setmTotalCost(Long mTotalCost) {
		this.mTotalCost = mTotalCost;
	}

	@Column(name="submission_id")
	private Long submissionId;

	public long getmPoId() {
		return mPoId;
	}

	public Long getmTenderId() {
		return mTenderId;
	}

	public Long getmRfpId() {
		return mRfpId;
	}

	public String getmSupplierCode(String string) {
		return mSupplierCode;
	}

	public String getmPoNo() {
		return mPoNo;
	}

	public String getmBatchFile() {
		return mBatchFile;
	}

	public String getmStatus() {
		return mStatus;
	}

	public Long getmQty() {
		return mQty;
	}

	public void setmPoId(long mPoId) {
		this.mPoId = mPoId;
	}

	public void setmTenderId(Long mTenderId) {
		this.mTenderId = mTenderId;
	}

	public void setmRfpId(Long mRfpId) {
		this.mRfpId = mRfpId;
	}

	public void setmSupplierCode(String mSupplierCode) {
		this.mSupplierCode = mSupplierCode;
	}

	public void setmPoNo(String mPoNo) {
		this.mPoNo = mPoNo;
	}

	public void setmBatchFile(String mBatchFile) {
		this.mBatchFile = mBatchFile;
	}

	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	public void setmQty(Long mQty) {
		this.mQty = mQty;
	}

	public Double getPanelty() {
		return panelty;
	}

	public void setPanelty(Double panelty) {
		this.panelty = panelty;
	}
	
	//getters setters
	
	

}
