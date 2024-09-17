package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
@Table(name = "m_contract_invoice_header")
public class ContractInvoiceHeader implements Serializable{

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="m_invoice_header_id")
	private long invoiceheaderid;
	
	@Column(name = "i_contract_no")
	private String contractno;
	
	@Column(name = "i_contract_id")
	private long contractid;
	
	@Column(name="m_invoice_number")
	private String invoicenumber;
	
	@Column(name="m_mapping_id")
	private long mappingid;
	
	@Column(name = "i_date")
	private LocalDate invoicedate;
	
	@Column(name = "i_time")
	private LocalTime invoicetime;
	
	@Column(name = "i_locationcode")
	private String locationcode;
	
	@Column(name = "i_locationname")
	private String locationname;

	@Column(name = "i_line_discount")
	private BigDecimal linediscount;
	
	@Column(name = "i_invoice_value")
	private BigDecimal invoicevalue;
	
	@Column(name = "i_total")
	private BigDecimal total;

	@Column(name = "i_total_tax")
	private BigDecimal totaltax;

	@Column(name = "i_net_amount")
	private BigDecimal netamount;

	@Column(name = "i_number_of_lines")
	private long numberoflines;

	@Column(name = "i_batch_number")
	private String batchnumber;
	
	@CreationTimestamp
	@Column(name = "i_createddate")
	private LocalDateTime createdate;

	@UpdateTimestamp
	@Column(name = "i_updatedate")
	private LocalDateTime iupdatedate;

	@Column(name = "i_status")
	private String status;
	
	@Column(name = "i_cheque_no")
	private String chequeno;

	@Column(name = "i_paid_amount")
	private BigDecimal paidamount;
	
	@Column(name = "i_payment_date")
	private LocalDateTime paymentdate;
	
	@Column(name = "i_invoice_image")
	private String invoiceimage;
	
	@Column(name="i_createduser")
	private String createduser;
	
	@Column(name="i_updateduser")
	private String updateduser;
	
	@Column(name="sc_invoice_type")
	private String invoiceType;
	
	@Column(name="sc_invoice_rejected_reason")
	private String invoiceRejectedReason;
	
	@Column(name = "i_comments")
	private String coments;
	
	@Column(name = "i_approve_complete")
	private String approvecomplete;
	
	
	// Approve Users
		
		@Column(name="sc_approve_userID_1")
		private String approvedUserID1;
		
		@Column(name="sc_approve_userID_2")
		private String approvedUserID2;
		
		@Column(name="sc_approve_userID_3")
		private String approvedUserID3;
		
		@Column(name="sc_approve_userID_4")
		private String approvedUserID4;
		
		@Column(name="sc_approve_userID_5")
		private String approvedUserID5;
		
	// Approve Reasons
		
		@Column(name="sc_approve_user_1_reason")
		private String approvedUser1Reason;
		
		@Column(name="sc_approve_user_2_reason")
		private String approvedUser2Reason;
		
		@Column(name="sc_approve_user_3_reason")
		private String approvedUser3Reason;
		
		@Column(name="sc_approve_user_4_reason")
		private String approvedUser4Reason;
		
		@Column(name="sc_approve_user_5_reason")
		private String approvedUser5Reason;
		
		// Approve Status
		
		@Column(name="sc_approve_user_1_status")
		private String approvedUser1Status;
		
		@Column(name="sc_approve_user_2_status")
		private String approvedUser2Status;
		
		@Column(name="sc_approve_user_3_status")
		private String approvedUser3Status;
		
		@Column(name="sc_approve_user_4_status")
		private String approvedUser4Status;
		
		@Column(name="sc_approve_user_5_status")
		private String approvedUser5Status;
		
		
		
		@PrePersist
	    public void setCreatedOn() {
	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			this.updateduser =  user.getUserid();
	    }
		
		public ContractInvoiceHeader() {
			super();
		}

		public long getInvoiceheaderid() {
			return invoiceheaderid;
		}

		public void setInvoiceheaderid(long invoiceheaderid) {
			this.invoiceheaderid = invoiceheaderid;
		}

		public String getContractno() {
			return contractno;
		}

		public void setContractno(String contractno) {
			this.contractno = contractno;
		}

		public String getInvoicenumber() {
			return invoicenumber;
		}

		public void setInvoicenumber(String invoicenumber) {
			this.invoicenumber = invoicenumber;
		}

		public long getMappingid() {
			return mappingid;
		}

		public void setMappingid(long mappingid) {
			this.mappingid = mappingid;
		}

		public LocalDate getInvoicedate() {
			return invoicedate;
		}

		public void setInvoicedate(LocalDate invoicedate) {
			this.invoicedate = invoicedate;
		}

		public LocalTime getInvoicetime() {
			return invoicetime;
		}

		public void setInvoicetime(LocalTime invoicetime) {
			this.invoicetime = invoicetime;
		}

		public String getLocationcode() {
			return locationcode;
		}

		public void setLocationcode(String locationcode) {
			this.locationcode = locationcode;
		}

		public String getLocationname() {
			return locationname;
		}

		public void setLocationname(String locationname) {
			this.locationname = locationname;
		}

		public BigDecimal getLinediscount() {
			return linediscount;
		}

		public void setLinediscount(BigDecimal linediscount) {
			this.linediscount = linediscount;
		}

		public BigDecimal getTotal() {
			return total;
		}

		public void setTotal(BigDecimal total) {
			this.total = total;
		}

		public BigDecimal getTotaltax() {
			return totaltax;
		}

		public void setTotaltax(BigDecimal totaltax) {
			this.totaltax = totaltax;
		}

		public BigDecimal getNetamount() {
			return netamount;
		}

		public void setNetamount(BigDecimal netamount) {
			this.netamount = netamount;
		}

		public long getNumberoflines() {
			return numberoflines;
		}

		public void setNumberoflines(long numberoflines) {
			this.numberoflines = numberoflines;
		}

		public LocalDateTime getCreatedate() {
			return createdate;
		}

		public void setCreatedate(LocalDateTime createdate) {
			this.createdate = createdate;
		}

		public LocalDateTime getIupdatedate() {
			return iupdatedate;
		}

		public void setIupdatedate(LocalDateTime iupdatedate) {
			this.iupdatedate = iupdatedate;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getChequeno() {
			return chequeno;
		}

		public void setChequeno(String chequeno) {
			this.chequeno = chequeno;
		}

		public BigDecimal getPaidamount() {
			return paidamount;
		}

		public void setPaidamount(BigDecimal paidamount) {
			this.paidamount = paidamount;
		}

		public LocalDateTime getPaymentdate() {
			return paymentdate;
		}

		public void setPaymentdate(LocalDateTime paymentdate) {
			this.paymentdate = paymentdate;
		}

		public String getInvoiceimage() {
			return invoiceimage;
		}

		public void setInvoiceimage(String invoiceimage) {
			this.invoiceimage = invoiceimage;
		}

		public String getCreateduser() {
			return createduser;
		}

		public void setCreateduser(String createduser) {
			this.createduser = createduser;
		}

		public String getUpdateduser() {
			return updateduser;
		}

		public void setUpdateduser(String updateduser) {
			this.updateduser = updateduser;
		}

		public String getInvoiceType() {
			return invoiceType;
		}

		public void setInvoiceType(String invoiceType) {
			this.invoiceType = invoiceType;
		}

		public String getInvoiceRejectedReason() {
			return invoiceRejectedReason;
		}

		public void setInvoiceRejectedReason(String invoiceRejectedReason) {
			this.invoiceRejectedReason = invoiceRejectedReason;
		}

		public String getComents() {
			return coments;
		}

		public void setComents(String coments) {
			this.coments = coments;
		}

		public String getApprovedUserID1() {
			return approvedUserID1;
		}

		public void setApprovedUserID1(String approvedUserID1) {
			this.approvedUserID1 = approvedUserID1;
		}

		public String getApprovedUserID2() {
			return approvedUserID2;
		}

		public void setApprovedUserID2(String approvedUserID2) {
			this.approvedUserID2 = approvedUserID2;
		}

		public String getApprovedUserID3() {
			return approvedUserID3;
		}

		public void setApprovedUserID3(String approvedUserID3) {
			this.approvedUserID3 = approvedUserID3;
		}

		public String getApprovedUserID4() {
			return approvedUserID4;
		}

		public void setApprovedUserID4(String approvedUserID4) {
			this.approvedUserID4 = approvedUserID4;
		}

		public String getApprovedUserID5() {
			return approvedUserID5;
		}

		public void setApprovedUserID5(String approvedUserID5) {
			this.approvedUserID5 = approvedUserID5;
		}

		public BigDecimal getInvoicevalue() {
			return invoicevalue;
		}

		public void setInvoicevalue(BigDecimal invoicevalue) {
			this.invoicevalue = invoicevalue;
		}

		public String getApprovecomplete() {
			return approvecomplete;
		}

		public void setApprovecomplete(String approvecomplete) {
			this.approvecomplete = approvecomplete;
		}

		public String getBatchnumber() {
			return batchnumber;
		}

		public void setBatchnumber(String batchnumber) {
			this.batchnumber = batchnumber;
		}

		public long getContractid() {
			return contractid;
		}

		public void setContractid(long contractid) {
			this.contractid = contractid;
		}

		public String getApprovedUser1Reason() {
			return approvedUser1Reason;
		}

		public String getApprovedUser2Reason() {
			return approvedUser2Reason;
		}

		public String getApprovedUser3Reason() {
			return approvedUser3Reason;
		}

		public String getApprovedUser4Reason() {
			return approvedUser4Reason;
		}

		public String getApprovedUser5Reason() {
			return approvedUser5Reason;
		}

		public void setApprovedUser1Reason(String approvedUser1Reason) {
			this.approvedUser1Reason = approvedUser1Reason;
		}

		public void setApprovedUser2Reason(String approvedUser2Reason) {
			this.approvedUser2Reason = approvedUser2Reason;
		}

		public void setApprovedUser3Reason(String approvedUser3Reason) {
			this.approvedUser3Reason = approvedUser3Reason;
		}

		public void setApprovedUser4Reason(String approvedUser4Reason) {
			this.approvedUser4Reason = approvedUser4Reason;
		}

		public void setApprovedUser5Reason(String approvedUser5Reason) {
			this.approvedUser5Reason = approvedUser5Reason;
		}

		public String getApprovedUser1Status() {
			return approvedUser1Status;
		}

		public String getApprovedUser2Status() {
			return approvedUser2Status;
		}

		public String getApprovedUser3Status() {
			return approvedUser3Status;
		}

		public String getApprovedUser4Status() {
			return approvedUser4Status;
		}

		public String getApprovedUser5Status() {
			return approvedUser5Status;
		}

		public void setApprovedUser1Status(String approvedUser1Status) {
			this.approvedUser1Status = approvedUser1Status;
		}

		public void setApprovedUser2Status(String approvedUser2Status) {
			this.approvedUser2Status = approvedUser2Status;
		}

		public void setApprovedUser3Status(String approvedUser3Status) {
			this.approvedUser3Status = approvedUser3Status;
		}

		public void setApprovedUser4Status(String approvedUser4Status) {
			this.approvedUser4Status = approvedUser4Status;
		}

		public void setApprovedUser5Status(String approvedUser5Status) {
			this.approvedUser5Status = approvedUser5Status;
		}
		
		

	
		
		
}
