package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_tender_submissions")
public class TenderSubmissions implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long submission_id;
	
	@Column(name="accepted_lag")
	private String acceptedLag;
	
	@Column(name="company_accepted_date")
	private LocalDate companyAcceptedDate;
	
	@Column(name="company_accepted_time")
	private LocalTime closingAcceptedTime;
	
	@Column(name="company_accepted_userid")
	private String companyAcceptedUserId;
	
	@Column(name="confirmation_letter_sent_date")
	private LocalDate confirmationLetterSentDate;
	
	@Column(name="status")
	private String status;
	
	@Column(name="submitted_date")
	private LocalDate submittedDate;
	
	@Column(name="submitted_time")
	private LocalTime submittedTime;
	
	@Column(name="tender_id")
	private Long tenderNo;
	
	@Column(name="upload_company_profilename")
	private String uploadCompanyProfileName;
	
	@Column(name="upload_financials_filename")
	private String uploadFinancialsFileName;
	
	@Column(name="upload_rfp_filename")
	private String uploadRfpFileName;
	
	@Column(name="upload_support_doc_one")
	private String uploadSupportDocOne;
	
	@Column(name="upload_support_doc_two")
	private String uploadSupportDocTwo;
	
	@Column(name="upload_support_doc_three")
	private String uploadSupportDocThree;
	
	@Column(name="vendor_id")
	private String vendorId;
	
	@Column(name="supplier_id")
	private Long supplierId;
	
	@Column(name="loged_user_id")
	private String userId;
	
	@Column(name="tender_action")
	private String tenderAction;
	
	@Column(name="tender_response")	
	private String tenderResponse;
	
	@Column(name="confirm_eligible_supplier")
	private String confirmEligibleSupplier;
	
	@Column(name="email_notify")
	private String emailNotify;
	
	@Column(name="invite_date")
	private LocalDateTime inviteDate;
	
	@Column(name="t_open_date")
	private LocalDateTime openDate;

	@Column(name="tender_eligibility")
	private String tenderEligibility;
	
	@Column(name="tender_eligibility_logged_user")
	private String tenderEligibilityLoggedUser;
	
	public LocalDateTime getOpenDate() {
		return openDate;
	}

	public void setOpenDate(LocalDateTime openDate) {
		this.openDate = openDate;
	}
	
	public LocalDateTime getInviteDate() {
		return inviteDate;
	}

	public void setInviteDate(LocalDateTime inviteDate) {
		this.inviteDate = inviteDate;
	}
	
	public String getTenderEligibility() {
		return tenderEligibility;
	}

	public String getTenderEligibilityLoggedUser() {
		return tenderEligibilityLoggedUser;
	}

	public void setTenderEligibility(String tenderEligibility) {
		this.tenderEligibility = tenderEligibility;
	}

	public void setTenderEligibilityLoggedUser(String tenderEligibilityLoggedUser) {
		this.tenderEligibilityLoggedUser = tenderEligibilityLoggedUser;
	}

	public String getEmailNotify() {
		return emailNotify;
	}

	public void setEmailNotify(String emailNotify) {
		this.emailNotify = emailNotify;
	}

	public String getConfirmEligibleSupplier() {
		return confirmEligibleSupplier;
	}

	public void setConfirmEligibleSupplier(String confirmEligibleSupplier) {
		this.confirmEligibleSupplier = confirmEligibleSupplier;
	}

	public Long getTenderNo() {
		return tenderNo;
	}

	public void setTenderNo(Long tenderNo) {
		this.tenderNo = tenderNo;
	}
	
	public Long getSupplierId() {
		return supplierId;
	}
	
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getTenderResponse() {
		return tenderResponse;
	}

	public void setTenderResponse(String tenderResponse) {
		this.tenderResponse = tenderResponse;
	}

	public String getTenderAction() {
		return tenderAction;
	}

	public void setTenderAction(String tenderAction) {
		this.tenderAction = tenderAction;
	}

	public Long getSubmission_id() {
		return submission_id;
	}

	public String getAcceptedLag() {
		return acceptedLag;
	}

	public LocalDate getCompanyAcceptedDate() {
		return companyAcceptedDate;
	}

	public LocalTime getClosingAcceptedTime() {
		return closingAcceptedTime;
	}

	public String getCompanyAcceptedUserId() {
		return companyAcceptedUserId;
	}

	public LocalDate getConfirmationLetterSentDate() {
		return confirmationLetterSentDate;
	}

	public String getStatus() {
		return status;
	}

	public LocalDate getSubmittedDate() {
		return submittedDate;
	}

	public LocalTime getSubmittedTime() {
		return submittedTime;
	}

	

	public String getUploadCompanyProfileName() {
		return uploadCompanyProfileName;
	}

	public String getUploadFinancialsFileName() {
		return uploadFinancialsFileName;
	}

	public String getUploadRfpFileName() {
		return uploadRfpFileName;
	}

	public String getUploadSupportDocOne() {
		return uploadSupportDocOne;
	}

	public String getUploadSupportDocTwo() {
		return uploadSupportDocTwo;
	}

	public String getUploadSupportDocThree() {
		return uploadSupportDocThree;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setSubmission_id(Long submission_id) {
		this.submission_id = submission_id;
	}

	public void setAcceptedLag(String acceptedLag) {
		this.acceptedLag = acceptedLag;
	}

	public void setCompanyAcceptedDate(LocalDate companyAcceptedDate) {
		this.companyAcceptedDate = companyAcceptedDate;
	}

	public void setClosingAcceptedTime(LocalTime closingAcceptedTime) {
		this.closingAcceptedTime = closingAcceptedTime;
	}

	public void setCompanyAcceptedUserId(String companyAcceptedUserId) {
		this.companyAcceptedUserId = companyAcceptedUserId;
	}

	public void setConfirmationLetterSentDate(LocalDate confirmationLetterSentDate) {
		this.confirmationLetterSentDate = confirmationLetterSentDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}

	public void setSubmittedTime(LocalTime submittedTime) {
		this.submittedTime = submittedTime;
	}

	

	public void setUploadCompanyProfileName(String uploadCompanyProfileName) {
		this.uploadCompanyProfileName = uploadCompanyProfileName;
	}

	public void setUploadFinancialsFileName(String uploadFinancialsFileName) {
		this.uploadFinancialsFileName = uploadFinancialsFileName;
	}

	public void setUploadRfpFileName(String uploadRfpFileName) {
		this.uploadRfpFileName = uploadRfpFileName;
	}

	public void setUploadSupportDocOne(String uploadSupportDocOne) {
		this.uploadSupportDocOne = uploadSupportDocOne;
	}

	public void setUploadSupportDocTwo(String uploadSupportDocTwo) {
		this.uploadSupportDocTwo = uploadSupportDocTwo;
	}

	public void setUploadSupportDocThree(String uploadSupportDocThree) {
		this.uploadSupportDocThree = uploadSupportDocThree;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	


	
}
