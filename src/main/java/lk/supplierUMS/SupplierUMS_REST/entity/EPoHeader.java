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
@Table(name="m_po_header")

public class EPoHeader implements Serializable{
	
	private static final String name = null;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long m_id;
	
	@Column(name="m_po_no")
	private String mPoNo;
	
	@Column(name="m_company_code")
	private String mCompanyCode;
	
	@Column(name="m_supplier_code")
	private String mSupplierCode;
	
	@Column(name="m_batchfile")
	private String mBatchFile;
	
	@Column(name="m_comment")
	private String mComment;
	
	@Column(name="m_locationcode")
	private String mLocationCode;
	
	@Column(name="m_locationname")
	private String mLocationName;
	
	@Column(name="m_order_gross_value")
	private String mOrderGrossValue;
	
	@Column(name="m_userid")
	private String mUserID;
	
	@Column(name="m_status")
	private String mStatus;
	
//	new column
	
	public String getmStatus() {
		return mStatus;
	}

	public void setmStatus(String mStatus) {
		this.mStatus = mStatus;
	}

	public String getmUserID() {
		return mUserID;
	}

	public void setmUserID(String userID) {
		this.mUserID = userID;
	}

	@Column(name="m_po_date")
	private Date mPoDate;
	
	@Column(name="m_serverdate")
	private String mServerDate;
	
	@Column(name="m_suppliername")
	private String mSupplierName;
	
	@Column(name="m_total")
	private String mTotal;
	
	@Column(name="m_total_gross_amount")
	private String mTotalGrossAmount;
	
	@Column(name="m_total_vat_value")
	private String mTotalVatValue;
	
	@Column(name="m_tot_bill_discount")
	private String mTotBillDiscount;
	
	@Column(name="m_logged_userid")
	private String mLoggedUserID;

	public String getmLoggedUserID() {
		return mLoggedUserID;
	}

	public void setmLoggedUserID(String mLoggedUserID) {
		this.mLoggedUserID = mLoggedUserID;
	}

	public long getM_id() {
		return m_id;
	}

	public void setM_id(long m_id) {
		this.m_id = m_id;
	}

	public String getmPoNo() {
		return mPoNo;
	}

	public void setmPoNo(String mPoNo) {
		this.mPoNo = mPoNo;
	}

	public String getmCompanyCode() {
		return mCompanyCode;
	}

	public void setmCompanyCode(String mCompanyCode) {
		this.mCompanyCode = mCompanyCode;
	}

	public String getmSupplierCode() {
		return mSupplierCode;
	}

	public void setmSupplierCode(String string) {
		this.mSupplierCode = string;
	}

	public String getmBatchFile() {
		return mBatchFile;
	}

	public void setmBatchFile(String mBatchFile) {
		this.mBatchFile = mBatchFile;
	}

	public String getmComment() {
		return mComment;
	}

	public void setmComment(String mComment) {
		this.mComment = mComment;
	}

	public String getmLocationCode() {
		return mLocationCode;
	}

	public void setmLocationCode(String mLocationCode) {
		this.mLocationCode = mLocationCode;
	}

	public String getmLocationName() {
		return mLocationName;
	}

	public void setmLocationName(String mLocationName) {
		this.mLocationName = mLocationName;
	}

	public String getmOrderGrossValue() {
		return mOrderGrossValue;
	}

	public void setmOrderGrossValue(String mOrderGrossValue) {
		this.mOrderGrossValue = mOrderGrossValue;
	}

	public Date getmPoDate() {
		return mPoDate;
	}

	public void setmPoDate(Date mPoDate) {
		this.mPoDate = mPoDate;
	}

	public String getmServerDate() {
		return mServerDate;
	}

	public void setmServerDate(String mServerDate) {
		this.mServerDate = mServerDate;
	}

	public String getmSupplierName() {
		return mSupplierName;
	}

	public void setmSupplierName(String mSupplierName) {
		this.mSupplierName = mSupplierName;
	}

	public String getmTotal() {
		return mTotal;
	}

	public void setmTotal(String mTotal) {
		this.mTotal = mTotal;
	}

	public String getmTotalGrossAmount() {
		return mTotalGrossAmount;
	}

	public void setmTotalGrossAmount(String mTotalGrossAmount) {
		this.mTotalGrossAmount = mTotalGrossAmount;
	}

	public String getmTotalVatValue() {
		return mTotalVatValue;
	}

	public void setmTotalVatValue(String mTotalVatValue) {
		this.mTotalVatValue = mTotalVatValue;
	}

	public String getmTotBillDiscount() {
		return mTotBillDiscount;
	}

	public void setmTotBillDiscount(String mTotBillDiscount) {
		this.mTotBillDiscount = mTotBillDiscount;
	}
	



	
}
