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
@Table(name = "t_contract_invoice_header")
public class TempContractInvoiceHeader implements Serializable{

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="m_invoice_header_id")
	private long invoiceheaderid;
	
	@Column(name = "i_contract_id")
	private long contractid;
	
	@Column(name = "i_contract_no")
	private String contractno;
	
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
	
	
		
		@PrePersist
	    public void setCreatedOn() {
	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			this.updateduser =  user.getUserid();
	    }
		
		public TempContractInvoiceHeader() {
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

		public String getBatchnumber() {
			return batchnumber;
		}

		public void setBatchnumber(String batchnumber) {
			this.batchnumber = batchnumber;
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

		public long getContractid() {
			return contractid;
		}

		public void setContractid(long contractid) {
			this.contractid = contractid;
		}

}
