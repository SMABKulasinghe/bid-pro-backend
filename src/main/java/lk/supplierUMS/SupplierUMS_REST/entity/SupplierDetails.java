package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_supplier_details")
public class SupplierDetails implements Serializable {

	
	/*
	 * @Id	 * 
	 * @GeneratedValue(strategy=GenerationType.AUTO) private long companyCode;
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="s_sup_id")
	private Long supplierID;
	
	@Column(name="s_sub_com_id")
	private Long subCompanyID;
	
	@Column(name="s_existing_code")
	private String existingCode;
	
	@Column(name="s_name")
	private String supplierName;
	
	@Column(name="s_registration_no")
	private String registrationNo;
	
	@Column(name="s_reg_date")
	private String regDate;
	
	@Column(name="s_address1")
	private String address1;
	
	@Column(name="s_address2")
	private String address2;
	
	@Column(name="s_address3")
	private String address3;
	
	@Column(name="s_Phone_no1")
	private String phoneNo1;
	
	@Column(name="s_Phone_no2")
	private String phoneNo2;
	
	@Column(name="s_Phone_no3")
	private String phoneNo3;
	
	@Column(name="s_city")
	private String supplierCity;
	
	@Column(name="s_district")
	private String supplierDistrict;
	
	@Column(name="s_email_admin")
	private String emailAdmin;
	
	@Column(name="s_email_business_head")
	private String emailBusinessHead;
	
	@Column(name="s_email_line_manager")
	private String emailLineManager;
	
	@Column(name="s_system_registered_date")
	private String systemRegisteredDate;
	
	@Column(name="s_comapany_logo")
	private String comapanyLogo;
	
  /*@Column(name="s_bank_acctno")
	private String bankAcctNo; */
	
	@Column(name="s_bank_branch_name")
	private String bankBranchName;
	
	@Column(name="s_bank_branch_code")
	private String bankBranchCode;
	
	@Column(name="s_created_ID")
	private String createdID;
	
	@Column(name="s_approved_ID")
	private String approvedID;
	
	@Column(name="s_status")
	private String supplierStatus;
	
	@Column(name="s_regitration_uploaded")
	private String regitrationUploaded;
	
	@Column(name="s_address_proof_uploaded")
	private String addressProofUploaded;
	
	@Column(name="s_company_prefix")
	private String companyPrefix;
	
	@Column(name="s_bank")
	private String bankName;
	
	@Column(name="s_logo_uploaded")
	private String logoUploaded;
	
	@Column(name="s_bank_acct_no")
	private Integer bankAcctNo;
	
	@Column(name="s_time")
	private LocalTime timeCreated;
	
	@Column(name="s_customer_code")
	private String customerCode;

	
	
	// New Fields
	
	
	@Column(name="s_one_time_fee")
	private String oneTimeFee;
	
	@Column(name="s_category_fee")
	private String categoryFee;
	
	@Column(name="s_total_payment_due")
	private String totalPaymentDue;
	
	@Column(name="s_green_policy")
	private String greenPolicy;
	
	@Column(name="s_ictad_registration")
	private String ictadRegistration;
	
	@Column(name="s_kyc_form")
	private String kycForm;
	
	@Column(name="s_list_of_services")
	private String listOfServices;
	
	@Column(name="s_company_profile")
	private String companyProfile;
	
	@Column(name="s_last_six_months")
	private String lastSixMonths;
	
	@Column(name="s_listof_top_fifteen_clients")
	private String listofTopFifteenClients;
	
	@Column(name="s_certification_of_incorp")
	private String certificationOfIncorp;
	
	@Column(name="s_listof_directors")
	private String listofDirectors;
	
	@Column(name="s_articalof_association")
	private String articalofAssociation;
	
	@Column(name="s_last_audited_fin_acc")
	private String lastAuditedFinAcc;
	
	@Column(name="s_bank_branch")
	private String bankBranch; 
	
	@Column(name="s_bank_address_lines")
	private String bankAddressLines;
	
	@Column(name="s_product_category")
	private String productCategory;
	
	@Column(name="s_product_sub_category")
	private String productSubCategory;
	
	@Column(name="s_product")
	private String product;
	
	@Column(name="s_main_application")
	private String mainApplication;
	
	@Column(name="s_category_list")
	private String categoryList;
	
	@Column(name="s_supplier_code_of_conduct")
	private String supplierCodeofConduct;
	
	@Column(name="s_supplier_esquestions")
	private String supplierESQuestions;
	
	@Column(name="s_br_form")
	private String brForm;
	
	@Column(name="s_bci_form")
	private String bciForm;
	
	
	


	public Long getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(Long supplierID) {
		this.supplierID = supplierID;
	}

	public Long getSubCompanyID() {
		return subCompanyID;
	}

	public void setSubCompanyID(Long subCompanyID) {
		this.subCompanyID = subCompanyID;
	}

	public String getExistingCode() {
		return existingCode;
	}

	public void setExistingCode(String existingCode) {
		this.existingCode = existingCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getPhoneNo1() {
		return phoneNo1;
	}

	public void setPhoneNo1(String phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}

	public String getPhoneNo2() {
		return phoneNo2;
	}

	public void setPhoneNo2(String phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}

	public String getPhoneNo3() {
		return phoneNo3;
	}

	public void setPhoneNo3(String phoneNo3) {
		this.phoneNo3 = phoneNo3;
	}

	public String getSupplierCity() {
		return supplierCity;
	}

	public void setSupplierCity(String supplierCity) {
		this.supplierCity = supplierCity;
	}

	public String getSupplierDistrict() {
		return supplierDistrict;
	}

	public void setSupplierDistrict(String supplierDistrict) {
		this.supplierDistrict = supplierDistrict;
	}

	public String getEmailAdmin() {
		return emailAdmin;
	}

	public void setEmailAdmin(String emailAdmin) {
		this.emailAdmin = emailAdmin;
	}

	public String getEmailBusinessHead() {
		return emailBusinessHead;
	}

	public void setEmailBusinessHead(String emailBusinessHead) {
		this.emailBusinessHead = emailBusinessHead;
	}

	public String getEmailLineManager() {
		return emailLineManager;
	}

	public void setEmailLineManager(String emailLineManager) {
		this.emailLineManager = emailLineManager;
	}

	public String getSystemRegisteredDate() {
		return systemRegisteredDate;
	}

	public void setSystemRegisteredDate(String systemRegisteredDate) {
		this.systemRegisteredDate = systemRegisteredDate;
	}

	public String getComapanyLogo() {
		return comapanyLogo;
	}

	public void setComapanyLogo(String comapanyLogo) {
		this.comapanyLogo = comapanyLogo;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getCreatedID() {
		return createdID;
	}

	public void setCreatedID(String createdID) {
		this.createdID = createdID;
	}

	public String getApprovedID() {
		return approvedID;
	}

	public void setApprovedID(String approvedID) {
		this.approvedID = approvedID;
	}

	public String getSupplierStatus() {
		return supplierStatus;
	}

	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}

	public String getRegitrationUploaded() {
		return regitrationUploaded;
	}

	public void setRegitrationUploaded(String regitrationUploaded) {
		this.regitrationUploaded = regitrationUploaded;
	}

	public String getAddressProofUploaded() {
		return addressProofUploaded;
	}

	public void setAddressProofUploaded(String addressProofUploaded) {
		this.addressProofUploaded = addressProofUploaded;
	}

	public String getCompanyPrefix() {
		return companyPrefix;
	}

	public void setCompanyPrefix(String companyPrefix) {
		this.companyPrefix = companyPrefix;
	}
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getLogoUploaded() {
		return logoUploaded;
	}

	public void setLogoUploaded(String logoUploaded) {
		this.logoUploaded = logoUploaded;
	}

	public Integer getBankAcctNo() {
		return bankAcctNo;
	}

	public void setBankAcctNo(Integer bankAcctNo) {
		this.bankAcctNo = bankAcctNo;
	}

	public LocalTime getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(LocalTime timeCreated) {
		this.timeCreated = timeCreated;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	
	// New Fields
	
	public String getOneTimeFee() {
		return oneTimeFee;
	}

	public void setOneTimeFee(String oneTimeFee) {
		this.oneTimeFee = oneTimeFee;
	}
	
	
	public String getCategoryFee() {
		return categoryFee;
	}

	public void setCategoryFee(String categoryFee) {
		this.categoryFee = categoryFee;
	}
	
	public String getTotalPaymentDue() {
		return totalPaymentDue;
	}

	public void setTotalPaymentDue(String totalPaymentDue) {
		this.totalPaymentDue = totalPaymentDue;
	}

	public String getGreenPolicy() {
		return greenPolicy;
	}

	public void setGreenPolicy(String greenPolicy) {
		this.greenPolicy = greenPolicy;
	}

	public String getIctadRegistration() {
		return ictadRegistration;
	}

	public void setIctadRegistration(String ictadRegistration) {
		this.ictadRegistration = ictadRegistration;
	}

	public String getKycForm() {
		return kycForm;
	}

	public void setKycForm(String kycForm) {
		this.kycForm = kycForm;
	}
	
	public String getListOfServices() {
		return listOfServices;
	}

	public void setListOfServices(String listOfServices) {
		this.listOfServices = listOfServices;
	}
	
	public String getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(String companyProfile) {
		this.companyProfile = companyProfile;
	}
	
	public String getLastSixMonths() {
		return lastSixMonths;
	}

	public void setLastSixMonths(String lastSixMonths) {
		this.lastSixMonths = lastSixMonths;
	}
	
	public String getCertificationOfIncorp() {
		return certificationOfIncorp;
	}

	public void setCertificationOfIncorp(String certificationOfIncorp) {
		this.certificationOfIncorp = certificationOfIncorp;
	}
	
	public String getListofDirectors() {
		return listofDirectors;
	}

	public void setListofDirectors(String listofDirectors) {
		this.listofDirectors = listofDirectors;
	}

	public String getArticalofAssociation() {
		return articalofAssociation;
	}

	public void setArticalofAssociation(String articalofAssociation) {
		this.articalofAssociation = articalofAssociation;
	}

	public String getLastAuditedFinAcc() {
		return lastAuditedFinAcc;
	}

	public void setLastAuditedFinAcc(String lastAuditedFinAcc) {
		this.lastAuditedFinAcc = lastAuditedFinAcc;
	}

	public String getListofTopFifteenClients() {
		return listofTopFifteenClients;
	}

	public void setListofTopFifteenClients(String listofTopFifteenClients) {
		this.listofTopFifteenClients = listofTopFifteenClients;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getBankAddressLines() {
		return bankAddressLines;
	}

	public void setBankAddressLines(String bankAddressLines) {
		this.bankAddressLines = bankAddressLines;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductSubCategory() {
		return productSubCategory;
	}

	public void setProductSubCategory(String productSubCategory) {
		this.productSubCategory = productSubCategory;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getMainApplication() {
		return mainApplication;
	}

	public void setMainApplication(String mainApplication) {
		this.mainApplication = mainApplication;
	}

	public String getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(String categoryList) {
		this.categoryList = categoryList;
	}

	public String getSupplierCodeofConduct() {
		return supplierCodeofConduct;
	}

	public void setSupplierCodeofConduct(String supplierCodeofConduct) {
		this.supplierCodeofConduct = supplierCodeofConduct;
	}

	public String getSupplierESQuestions() {
		return supplierESQuestions;
	}

	public void setSupplierESQuestions(String supplierESQuestions) {
		this.supplierESQuestions = supplierESQuestions;
	}

	public String getBrForm() {
		return brForm;
	}

	public void setBrForm(String brForm) {
		this.brForm = brForm;
	}

	public String getBciForm() {
		return bciForm;
	}

	public void setBciForm(String bciForm) {
		this.bciForm = bciForm;
	}
	
	
	
	
	
	
	
}
