package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "m_inv_header")
public class InvoiceHeader implements Serializable {

	/*@EmbeddedId
	private InvoiceHeaderPrimaryKey invoiceheaderId;*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="m_invoice_id")
	private long invoiceId;
	
	@Column(name="m_mapping_id")
	private long mappingID;

	@Column(name = "i_grn_no")
	private String igrnno;

	@Column(name = "i_date")
    @Temporal(TemporalType.DATE)
	private Date idate;

	@Column(name = "i_tot_line_discount")
	private Double itotlinediscount;

	@Column(name = "i_tot_bill_discount")
	private Double itotbilldiscount;

	@Column(name = "i_total")
	private Double itotal;

	@Column(name = "i_total_tax")
	private Double itotaltax;

	@Column(name = "i_net_amount")
	private Double inetamount;

	@Column(name = "i_no_of_lines")
	private Double ino_oflines;

	@Column(name = "i_locationcode")
	private String ilocationcode;

	@Column(name = "i_locationname")
	private String ilocationname;

	@Column(name = "i_sysdate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date isysdate;

	@Column(name = "i_serverdate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date iserverdate;

	@Column(name = "i_updatedate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date iupdatedate;

	@Column(name = "i_status")
	private String istatus;
	
	@Column(name = "i_cheque_no")
	private String ichequeno;

	@Column(name = "i_paid_amount")
	private BigDecimal ipaidamount;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "i_payment_date")
	private Date ipaymentdate;
	
	@Column(name = "i_batchfile")
	private String ibatchfile;

	@Column(name = "i_imageid")
	private String iimageid;
	
	@Column(name = "i_time")
	private Time i_time;
	
	@Column(name = "i_csvfilepath")
	private String icsvfilepath;
	
	@Column(name="i_logged_userid")
	private String iloggeduser;
	
	//
	
	@Column(name="sc_invoice_type")
	private String invoiceType;
	
	@Column(name="sc_invoice_rejected_reason")
	private String invoiceRejectedReason;
	
	@Column(name = "contract_id")
	private Long contractId;
	
	
	// Approve Processs
		
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
		
		

	

	public InvoiceHeader( String igrnno, Date idate, Double itotlinediscount,
			Double itotbilldiscount, Double itotal, Double itotaltax, Double inetamount, Double ino_oflines,
			String ilocationcode, String ilocationname, Date isysdate, Date iserverdate, Date iupdatedate,
			String istatus, String ibatchfile, String iimageid, Time i_time, String icsvfilepath, String iloggeduser) {
		super();
		//this.invoiceheaderId = invoiceheaderId;
		this.igrnno = igrnno;
		this.idate = idate;
		this.itotlinediscount = itotlinediscount;
		this.itotbilldiscount = itotbilldiscount;
		this.itotal = itotal;
		this.itotaltax = itotaltax;
		this.inetamount = inetamount;
		this.ino_oflines = ino_oflines;
		this.ilocationcode = ilocationcode;
		this.ilocationname = ilocationname;
		this.isysdate = isysdate;
		this.iserverdate = iserverdate;
		this.iupdatedate = iupdatedate;
		this.istatus = istatus;
		this.ibatchfile = ibatchfile;
		this.iimageid = iimageid;
		this.i_time = i_time;
		this.icsvfilepath = icsvfilepath;
		this.iloggeduser = iloggeduser;
	}

	/*public InvoiceHeaderPrimaryKey getInvoiceheaderId() {
		return invoiceheaderId;
	}*/

	public InvoiceHeader() {
		super();
	}

	/*public void setInvoiceheaderId(InvoiceHeaderPrimaryKey invoiceheaderId) {
		this.invoiceheaderId = invoiceheaderId;
	}*/

	
	public String getIgrnno() {
		return igrnno;
	}

	public void setIgrnno(String igrnno) {
		this.igrnno = igrnno;
	}

	public Date getIdate() {
		return idate;
	}

	public void setIdate(Date idate) {
		this.idate = idate;
	}

	public Double getItotlinediscount() {
		return itotlinediscount;
	}

	public void setItotlinediscount(Double itotlinediscount) {
		this.itotlinediscount = itotlinediscount;
	}

	public Double getItotbilldiscount() {
		return itotbilldiscount;
	}

	public void setItotbilldiscount(Double itotbilldiscount) {
		this.itotbilldiscount = itotbilldiscount;
	}

	public Double getItotal() {
		return itotal;
	}

	public void setItotal(Double itotal) {
		this.itotal = itotal;
	}

	public Double getItotaltax() {
		return itotaltax;
	}

	public void setItotaltax(Double itotaltax) {
		this.itotaltax = itotaltax;
	}

	public Double getInetamount() {
		return inetamount;
	}

	public void setInetamount(Double inetamount) {
		this.inetamount = inetamount;
	}

	public Double getIno_oflines() {
		return ino_oflines;
	}

	public void setIno_oflines(Double ino_oflines) {
		this.ino_oflines = ino_oflines;
	}

	public String getIlocationcode() {
		return ilocationcode;
	}

	public void setIlocationcode(String ilocationcode) {
		this.ilocationcode = ilocationcode;
	}

	public String getIlocationname() {
		return ilocationname;
	}

	public void setIlocationname(String ilocationname) {
		this.ilocationname = ilocationname;
	}

	public Date getIsysdate() {
		return isysdate;
	}

	public void setIsysdate(Date isysdate) {
		this.isysdate = isysdate;
	}

	public Date getIserverdate() {
		return iserverdate;
	}

	public void setIserverdate(Date iserverdate) {
		this.iserverdate = iserverdate;
	}

	public Date getIupdatedate() {
		return iupdatedate;
	}

	public void setIupdatedate(Date iupdatedate) {
		this.iupdatedate = iupdatedate;
	}

	public String getIstatus() {
		return istatus;
	}

	public void setIstatus(String istatus) {
		this.istatus = istatus;
	}

	public String getIbatchfile() {
		return ibatchfile;
	}

	public void setIbatchfile(String ibatchfile) {
		this.ibatchfile = ibatchfile;
	}

	public String getIimageid() {
		return iimageid;
	}

	public void setIimageid(String iimageid) {
		this.iimageid = iimageid;
	}

	public Time getI_time() {
		return i_time;
	}

	public void setI_time(Time i_time) {
		this.i_time = i_time;
	}

	public String getIcsvfilepath() {
		return icsvfilepath;
	}

	public void setIcsvfilepath(String icsvfilepath) {
		this.icsvfilepath = icsvfilepath;
	}

	public String getIloggeduser() {
		return iloggeduser;
	}

	public void setIloggeduser(String iloggeduser) {
		this.iloggeduser = iloggeduser;
	}

	public String getIchequeno() {
		return ichequeno;
	}

	public void setIchequeno(String ichequeno) {
		this.ichequeno = ichequeno;
	}

	public BigDecimal getIpaidamount() {
		return ipaidamount;
	}

	public void setIpaidamount(BigDecimal ipaidamount) {
		this.ipaidamount = ipaidamount;
	}

	public Date getIpaymentdate() {
		return ipaymentdate;
	}

	public void setIpaymentdate(Date ipaymentdate) {
		this.ipaymentdate = ipaymentdate;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
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

	
}
