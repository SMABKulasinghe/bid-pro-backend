package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
@Table(name = "m_payment_header")
public class PaymentHeader implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="m_payment_id")
	private long paymentId;
	
	@Column(name="m_mapping_id")
	private long mappingID;
	
	@Column(name = "m_supplier_name")
	private String suppliername;
	
	@Column(name="m_invoice_id")
	private long invoiceId;
	

	@Column(name = "m_bank_name")
	private String bankname;

	@Column(name = "m_bank_branch")
	private String bankbranch;

	@Column(name = "m_account_number")
	private String accountnumber;

	@Temporal(TemporalType.DATE)
	@Column(name = "m_gr_date")
	private Date grdate;

	@Temporal(TemporalType.DATE)
	@Column(name = "m_invoice_date")
	private Date invoicedate;

	@Column(name = "m_grn_amount")
	private double grnamount;
	
	@Column(name = "m_po_amount")
	private double mpoamount;

	@Column(name = "m_invoice_amount")
	private double invoiceamount;

	@Column(name = "m_payment_type")
	private String paymenttype;

	@Column(name = "m_payment_amount")
	private BigDecimal paymentamount;

	@Column(name = "m_cheque_no")
	private String chequeno;

	@Temporal(TemporalType.DATE)
	@Column(name = "m_payment_date")
	private Date paymentdate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "m_po_date")
	private Date podate;

	@Column(name = "m_payment_time")
	private Time paymenttime;

	@Column(name = "m_debit_note_no")
	private String debitnoteno;

	@Temporal(TemporalType.DATE)
	@Column(name = "m_debit_note_date")
	private Date debitnotedate;

	@Column(name = "m_debit_note_amount")
	private Double debitnoteamount;

	@Column(name = "m_debit_note_reason")
	private String debitnotereason;

	@Column(name = "m_batchfileno")
	private String batchfileno;

	@Column(name = "m_payment_status")
	private String paymentstatus;

	@Column(name = "m_auth_id")
	private String authid;

	@Column(name = "m_input_id")
	private String inputid;

	@Temporal(TemporalType.DATE)
	@Column(name = "m_auth_date")
	private Date authdate;

	@Column(name = "m_auth_time")
	private Time authtime;

	@Temporal(TemporalType.DATE)
	@Column(name = "m_system_date")
	private Date sysdate;

	@Column(name = "m_system_time")
	private Time systime;
	
	// Audit
	
	@CreationTimestamp
	@Column(name = "m_createddate")
	private LocalDateTime createdate;

	@UpdateTimestamp
	@Column(name = "m_updatedate")
	private LocalDateTime updatedate;
	
	@Column(name="m_createduser")
	private String createduser;
	
	@Column(name="m_updateduser")
	private String updateduser;
	
	// Approve
	
	@Column(name="sc_approve_userID_1")
	private String approvedUserID1;
	
	@Column(name="sc_approve_user_1_reason")
	private String approvedUser1Reason;
	
	// Approve Status
	
	@Column(name="sc_approve_user_1_status")
	private String approvedUser1Status;
			
	
	
	@PrePersist
    public void setCreatedOn() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		this.updateduser =  user.getUserid();
    }
	

	public long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}



	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBankbranch() {
		return bankbranch;
	}

	public void setBankbranch(String bankbranch) {
		this.bankbranch = bankbranch;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public Date getGrdate() {
		return grdate;
	}

	public void setGrdate(Date grdate) {
		this.grdate = grdate;
	}

	public Date getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
	}

	public double getGrnamount() {
		return grnamount;
	}

	public void setGrnamount(double grnamount) {
		this.grnamount = grnamount;
	}

	public double getMpoamount() {
		return mpoamount;
	}

	public void setMpoamount(double mpoamount) {
		this.mpoamount = mpoamount;
	}

	public double getInvoiceamount() {
		return invoiceamount;
	}

	public void setInvoiceamount(double invoiceamount) {
		this.invoiceamount = invoiceamount;
	}

	public String getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}

	public BigDecimal getPaymentamount() {
		return paymentamount;
	}

	public void setPaymentamount(BigDecimal paymentamount) {
		this.paymentamount = paymentamount;
	}

	public String getChequeno() {
		return chequeno;
	}

	public void setChequeno(String chequeno) {
		this.chequeno = chequeno;
	}

	public Date getPaymentdate() {
		return paymentdate;
	}

	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}

	public Date getPodate() {
		return podate;
	}

	public void setPodate(Date podate) {
		this.podate = podate;
	}

	public Time getPaymenttime() {
		return paymenttime;
	}

	public void setPaymenttime(Time paymenttime) {
		this.paymenttime = paymenttime;
	}

	public String getDebitnoteno() {
		return debitnoteno;
	}

	public void setDebitnoteno(String debitnoteno) {
		this.debitnoteno = debitnoteno;
	}

	public Date getDebitnotedate() {
		return debitnotedate;
	}

	public void setDebitnotedate(Date debitnotedate) {
		this.debitnotedate = debitnotedate;
	}

	public Double getDebitnoteamount() {
		return debitnoteamount;
	}

	public void setDebitnoteamount(Double debitnoteamount) {
		this.debitnoteamount = debitnoteamount;
	}

	public String getDebitnotereason() {
		return debitnotereason;
	}

	public void setDebitnotereason(String debitnotereason) {
		this.debitnotereason = debitnotereason;
	}

	public String getBatchfileno() {
		return batchfileno;
	}

	public void setBatchfileno(String batchfileno) {
		this.batchfileno = batchfileno;
	}

	public String getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public String getAuthid() {
		return authid;
	}

	public void setAuthid(String authid) {
		this.authid = authid;
	}

	public String getInputid() {
		return inputid;
	}

	public void setInputid(String inputid) {
		this.inputid = inputid;
	}

	public Date getAuthdate() {
		return authdate;
	}

	public void setAuthdate(Date authdate) {
		this.authdate = authdate;
	}

	public Time getAuthtime() {
		return authtime;
	}

	public void setAuthtime(Time authtime) {
		this.authtime = authtime;
	}

	public Date getSysdate() {
		return sysdate;
	}

	public void setSysdate(Date sysdate) {
		this.sysdate = sysdate;
	}

	public Time getSystime() {
		return systime;
	}

	public void setSystime(Time systime) {
		this.systime = systime;
	}

	public long getMappingID() {
		return mappingID;
	}

	public void setMappingID(long mappingID) {
		this.mappingID = mappingID;
	}

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}


	public LocalDateTime getCreatedate() {
		return createdate;
	}


	public LocalDateTime getUpdatedate() {
		return updatedate;
	}


	public String getCreateduser() {
		return createduser;
	}


	public String getUpdateduser() {
		return updateduser;
	}


	public String getApprovedUserID1() {
		return approvedUserID1;
	}


	public String getApprovedUser1Reason() {
		return approvedUser1Reason;
	}


	public void setCreatedate(LocalDateTime createdate) {
		this.createdate = createdate;
	}


	public void setUpdatedate(LocalDateTime updatedate) {
		this.updatedate = updatedate;
	}


	public void setCreateduser(String createduser) {
		this.createduser = createduser;
	}


	public void setUpdateduser(String updateduser) {
		this.updateduser = updateduser;
	}


	public void setApprovedUserID1(String approvedUserID1) {
		this.approvedUserID1 = approvedUserID1;
	}


	public void setApprovedUser1Reason(String approvedUser1Reason) {
		this.approvedUser1Reason = approvedUser1Reason;
	}


	public String getApprovedUser1Status() {
		return approvedUser1Status;
	}


	public void setApprovedUser1Status(String approvedUser1Status) {
		this.approvedUser1Status = approvedUser1Status;
	}

	
	
	

}
