package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_subcompany")
public class SubCompany implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long subCompanyID;
	
	@Column(name="sc_company_code")
	private String scompanycode;
	
	@Column(name="sc_name")
	private String scname;
	
	@Column(name="sc_categoryType")
	private String sccategoryType;
	
	@Column(name="sc_identity")
	private boolean identity;  // true - Company || false - supplier
	
	@Column(name="sc_registration_no")
	private String scregistrationno;
	
	@Column(name="sc_reg_date")
	private LocalDate scregdate;
	
	@Column(name="sc_address_1")
	private String scaddress1;

	@Column(name="sc_address_2")
	private String scaddress2;
	
	@Column(name="sc_address_3")
	private String scaddress3;
	
	@Column(name="sc_phone_no1")
	private String scphoneno1;
	
	@Column(name="sc_phone_no2")
	private String scphoneno2;
	
	@Column(name="sc_phone_no3")
	private String scphoneno3;
	
	@Column(name="sc_city")
	private Long sccity;
	
	@Column(name="sc_district")
	private Long scdistrict;
	
	@Column(name="sc_email_admin")
	private String scemailadmin;
	
	@Column(name="sc_email_business_head")
	private String scemailbusinesshead;
	
	@Column(name="sc_email_line_manager")
	private String scemaillinemanager;
	
	@Column(name="sc_system_registered_date")
	private LocalDateTime scsystemregistereddate = LocalDateTime.now();

	@Column(name="sc_comapany_logo")
	private String scomapanylogo;
	
	@Column(name="sc_comapany_prefix")
	private String scomapanyprefix;
	
	@Column(name="sc_created_id")
	private String screatedid;
	
	@Column(name="sc_approved_id")
	private String scapprovedid;
	
	@Column(name="sc_status")
	private String scstatus;
	
	@Column(name="sc_registration_proof")
	private String scRegProof;
	
	@Column(name="sc_address_proof")
	private String scAddProof;
	
	@Column(name="sc_company_dateformat")
	private String dateformat;
	
	@Column(name="sc_company_timeformat")
	private String timeformat;
	
	@Column(name="sc_company_created")
	private String created;
	
	@Column(name="sc_company_updated")
	private String updated;
	
	// Approve Processs
	
	@Column(name="sc_aprove_count")
	private int approveCount;
	
	@Column(name="sc_approve_roleID_1")
	private long approveRoleID1;
	
	@Column(name="sc_approve_roleID_2")
	private long approveRoleID2;
	
	@Column(name="sc_approve_roleID_3")
	private long approveRoleID3;
	
	@Column(name="sc_approve_roleID_4")
	private long approveRoleID4;
	
	@Column(name="sc_approve_roleID_5")
	private long approveRoleID5;

	@Column(name="sc_prifile_private")
	private boolean isPrivate;
	
	// new Fields
	
	@Column(name="sc_product_category")
	private String scproductCategory;
	
	@Column(name="sc_product_sub_category")
	private String scproductSubCategory;
	
	@Column(name="sc_product")
	private String scproduct;
	
	@Column(name="sc_temp_r")
	private String tempR;
	
	@Column(name="is_additional_data_submited")
	private Boolean isAdditionalDataSubmited;
	
	@Column(name="sc_comment")
	private String comment;
	
	@Column(name="sc_approvedby")
	private String approvedBy;
	
	@Column(name="sc_approve_date_and_time")
	private LocalDateTime approveDateTime;
	
	@Column(name="sc_is_paid")
	private Boolean ispaid;
	
	@Column(name="sc_amount")
	private Double amount;
	
	@Column(name="sc_block_comment")
	private String blockcomment;
	
	@Column(name="sc_unblock_comment")
	private String unblockcomment;
	
	@Column(name="sc_name1")
	private String scname1;
	
	@Column(name="sc_designation")
	private String scdesignation;
	
	@Column(name="sc_contact_no")
	private String sccontactno;
	
	@Column(name="sc_email")
	private String scemail;
	
	
	
	
	
	

	public String getUnblockcomment() {
		return unblockcomment;
	}

	public void setUnblockcomment(String unblockcomment) {
		this.unblockcomment = unblockcomment;
	}

	public String getBlockcomment() {
		return blockcomment;
	}

	public void setBlockcomment(String blockcomment) {
		this.blockcomment = blockcomment;
	}

	public LocalDateTime getApproveDateTime() {
		return approveDateTime;
	}

	public void setApproveDateTime(LocalDateTime approveDateTime) {
		this.approveDateTime = approveDateTime;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getIsAdditionalDataSubmited() {
		return isAdditionalDataSubmited;
	}

	public void setIsAdditionalDataSubmited(Boolean isAdditionalDataSubmited) {
		this.isAdditionalDataSubmited = isAdditionalDataSubmited;
	}

	public String getTempR() {
		return tempR;
	}

	public void setTempR(String tempR) {
		this.tempR = tempR;
	}

	@Column(name="sc_province")
	private Long scprovince;
	
	public Long getScprovince() {
		return scprovince;
	}

	public void setScprovince(Long scprovince) {
		this.scprovince = scprovince;
	}

	public SubCompany() {
		super();
	}

	public long getSubCompanyID() {
		return subCompanyID;
	}

	public void setSubCompanyID(long subCompanyID) {
		this.subCompanyID = subCompanyID;
	}

	public String getScompanycode() {
		return scompanycode;
	}

	public void setScompanycode(String scompanycode) {
		this.scompanycode = scompanycode;
	}

	public String getScname() {
		return scname;
	}

	public void setScname(String scname) {
		this.scname = scname;
	}

	public String getSccategoryType() {
		return sccategoryType;
	}

	public void setSccategoryType(String sccategoryType) {
		this.sccategoryType = sccategoryType;
	}

	public boolean isIdentity() {
		return identity;
	}

	public void setIdentity(boolean identity) {
		this.identity = identity;
	}

	public String getScregistrationno() {
		return scregistrationno;
	}

	public void setScregistrationno(String scregistrationno) {
		this.scregistrationno = scregistrationno;
	}

	public LocalDate getScregdate() {
		return scregdate;
	}

	public void setScregdate(LocalDate scregdate) {
		this.scregdate = scregdate;
	}

	public String getScaddress1() {
		return scaddress1;
	}

	public void setScaddress1(String scaddress1) {
		this.scaddress1 = scaddress1;
	}

	public String getScaddress2() {
		return scaddress2;
	}

	public void setScaddress2(String scaddress2) {
		this.scaddress2 = scaddress2;
	}

	public String getScaddress3() {
		return scaddress3;
	}

	public void setScaddress3(String scaddress3) {
		this.scaddress3 = scaddress3;
	}

	public String getScphoneno1() {
		return scphoneno1;
	}

	public void setScphoneno1(String scphoneno1) {
		this.scphoneno1 = scphoneno1;
	}

	public String getScphoneno2() {
		return scphoneno2;
	}

	public void setScphoneno2(String scphoneno2) {
		this.scphoneno2 = scphoneno2;
	}

	public String getScphoneno3() {
		return scphoneno3;
	}

	public void setScphoneno3(String scphoneno3) {
		this.scphoneno3 = scphoneno3;
	}

	public Long getSccity() {
		return sccity;
	}

	public void setSccity(Long sccity) {
		this.sccity = sccity;
	}

	public Long getScdistrict() {
		return scdistrict;
	}

	public void setScdistrict(Long scdistrict) {
		this.scdistrict = scdistrict;
	}

	public String getScemailadmin() {
		return scemailadmin;
	}

	public void setScemailadmin(String scemailadmin) {
		this.scemailadmin = scemailadmin;
	}

	public String getScemailbusinesshead() {
		return scemailbusinesshead;
	}

	public void setScemailbusinesshead(String scemailbusinesshead) {
		this.scemailbusinesshead = scemailbusinesshead;
	}

	public String getScemaillinemanager() {
		return scemaillinemanager;
	}

	public void setScemaillinemanager(String scemaillinemanager) {
		this.scemaillinemanager = scemaillinemanager;
	}

	public LocalDateTime getScsystemregistereddate() {
		return scsystemregistereddate;
	}

	public void setScsystemregistereddate(LocalDateTime scsystemregistereddate) {
		this.scsystemregistereddate = scsystemregistereddate;
	}

	public String getScomapanylogo() {
		return scomapanylogo;
	}

	public void setScomapanylogo(String scomapanylogo) {
		this.scomapanylogo = scomapanylogo;
	}

	public String getScomapanyprefix() {
		return scomapanyprefix;
	}

	public void setScomapanyprefix(String scomapanyprefix) {
		this.scomapanyprefix = scomapanyprefix;
	}

	public String getScreatedid() {
		return screatedid;
	}

	public void setScreatedid(String screatedid) {
		this.screatedid = screatedid;
	}

	public String getScapprovedid() {
		return scapprovedid;
	}

	public void setScapprovedid(String scapprovedid) {
		this.scapprovedid = scapprovedid;
	}

	public String getScstatus() {
		return scstatus;
	}

	public void setScstatus(String scstatus) {
		this.scstatus = scstatus;
	}

	public String getScRegProof() {
		return scRegProof;
	}

	public void setScRegProof(String scRegProof) {
		this.scRegProof = scRegProof;
	}

	public String getScAddProof() {
		return scAddProof;
	}

	public void setScAddProof(String scAddProof) {
		this.scAddProof = scAddProof;
	}

	public int getApproveCount() {
		return approveCount;
	}

	public void setApproveCount(int approveCount) {
		this.approveCount = approveCount;
	}

	public long getApproveRoleID1() {
		return approveRoleID1;
	}

	public void setApproveRoleID1(long approveRoleID1) {
		this.approveRoleID1 = approveRoleID1;
	}

	public long getApproveRoleID2() {
		return approveRoleID2;
	}

	public void setApproveRoleID2(long approveRoleID2) {
		this.approveRoleID2 = approveRoleID2;
	}

	public long getApproveRoleID3() {
		return approveRoleID3;
	}

	public void setApproveRoleID3(long approveRoleID3) {
		this.approveRoleID3 = approveRoleID3;
	}

	public long getApproveRoleID4() {
		return approveRoleID4;
	}

	public void setApproveRoleID4(long approveRoleID4) {
		this.approveRoleID4 = approveRoleID4;
	}

	public long getApproveRoleID5() {
		return approveRoleID5;
	}

	public void setApproveRoleID5(long approveRoleID5) {
		this.approveRoleID5 = approveRoleID5;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getDateformat() {
		return dateformat;
	}

	public void setDateformat(String dateformat) {
		this.dateformat = dateformat;
	}

	public String getTimeformat() {
		return timeformat;
	}

	public void setTimeformat(String timeformat) {
		this.timeformat = timeformat;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getScproductCategory() {
		return scproductCategory;
	}

	public void setScproductCategory(String scproductCategory) {
		this.scproductCategory = scproductCategory;
	}

	public String getScproductSubCategory() {
		return scproductSubCategory;
	}

	public void setScproductSubCategory(String scproductSubCategory) {
		this.scproductSubCategory = scproductSubCategory;
	}

	public String getScproduct() {
		return scproduct;
	}

	public void setScproduct(String scproduct) {
		this.scproduct = scproduct;
	}

	public Boolean getIspaid() {
		return ispaid;
	}

	public void setIspaid(Boolean ispaid) {
		this.ispaid = ispaid;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getScname1() {
		return scname1;
	}

	public void setScname1(String scname1) {
		this.scname1 = scname1;
	}

	public String getScdesignation() {
		return scdesignation;
	}

	public void setScdesignation(String scdesignation) {
		this.scdesignation = scdesignation;
	}

	public String getSccontactno() {
		return sccontactno;
	}

	public void setSccontactno(String sccontactno) {
		this.sccontactno = sccontactno;
	}

	public String getScemail() {
		return scemail;
	}

	public void setScemail(String scemail) {
		this.scemail = scemail;
	}
	
	

	
	
	
}
