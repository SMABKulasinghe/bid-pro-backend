package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="appz_tender_submissions")

public class AppzTenderSubmissions implements Serializable {
	public String getSubmissionid() {
		return submissionid;
	}

	public String getTenderNo() {
		return tenderNo;
	}

	public String getVendor_id() {
		return vendor_id;
	}

	public LocalDate getSubmitteddate() {
		return submitteddate;
	}

	public LocalTime getSubmittedtime() {
		return submittedtime;
	}

	public String getAcceptedlag() {
		return acceptedlag;
	}

	public LocalDate getCompanyaccepteddate() {
		return companyaccepteddate;
	}

	public LocalTime getCompanyacceptedtime() {
		return companyacceptedtime;
	}

	public String getCompanyaccepteduserid() {
		return companyaccepteduserid;
	}

	public LocalDate getConfirmationlettersentdate() {
		return confirmationlettersentdate;
	}

	public String getUploadrfpfilename() {
		return uploadrfpfilename;
	}

	public String getUploadfinancialsfilename() {
		return uploadfinancialsfilename;
	}

	public String getUploadcompanyprofilename() {
		return uploadcompanyprofilename;
	}

	public String getUploadsupportdocone() {
		return uploadsupportdocone;
	}

	public String getUploadsupportdoctwo() {
		return uploadsupportdoctwo;
	}

	public String getUploadsupportdocthree() {
		return uploadsupportdocthree;
	}

	public String getStatus() {
		return status;
	}

	public void setSubmissionid(String submissionid) {
		this.submissionid = submissionid;
	}

	public void setTenderNo(String tenderNo) {
		this.tenderNo = tenderNo;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

	public void setSubmitteddate(LocalDate submitteddate) {
		this.submitteddate = submitteddate;
	}

	public void setSubmittedtime(LocalTime submittedtime) {
		this.submittedtime = submittedtime;
	}

	public void setAcceptedlag(String acceptedlag) {
		this.acceptedlag = acceptedlag;
	}

	public void setCompanyaccepteddate(LocalDate companyaccepteddate) {
		this.companyaccepteddate = companyaccepteddate;
	}

	public void setCompanyacceptedtime(LocalTime companyacceptedtime) {
		this.companyacceptedtime = companyacceptedtime;
	}

	public void setCompanyaccepteduserid(String companyaccepteduserid) {
		this.companyaccepteduserid = companyaccepteduserid;
	}

	public void setConfirmationlettersentdate(LocalDate confirmationlettersentdate) {
		this.confirmationlettersentdate = confirmationlettersentdate;
	}

	public void setUploadrfpfilename(String uploadrfpfilename) {
		this.uploadrfpfilename = uploadrfpfilename;
	}

	public void setUploadfinancialsfilename(String uploadfinancialsfilename) {
		this.uploadfinancialsfilename = uploadfinancialsfilename;
	}

	public void setUploadcompanyprofilename(String uploadcompanyprofilename) {
		this.uploadcompanyprofilename = uploadcompanyprofilename;
	}

	public void setUploadsupportdocone(String uploadsupportdocone) {
		this.uploadsupportdocone = uploadsupportdocone;
	}

	public void setUploadsupportdoctwo(String uploadsupportdoctwo) {
		this.uploadsupportdoctwo = uploadsupportdoctwo;
	}

	public void setUploadsupportdocthree(String uploadsupportdocthree) {
		this.uploadsupportdocthree = uploadsupportdocthree;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Id
	@Column(name="submission_id")
	private String submissionid;
	
	@Column(name="tender_no")
	private String tenderNo;
	
	@Column(name="vendor_id")
	private String vendor_id;
	
	@Column(name="submitted_date")
	private LocalDate submitteddate;

	@Column(name="submitted_time")
	private LocalTime submittedtime;
	
	@Column(name="accepted_lag")
	private String  acceptedlag;
	
	@Column(name="company_accepted_date")
	private LocalDate companyaccepteddate;
	
	@Column(name="company_accepted_time")
	private LocalTime companyacceptedtime;
	
	@Column(name="company_accepted_userid")
	private String companyaccepteduserid;

	@Column(name="confirmation_letter_sent_date")
	private LocalDate confirmationlettersentdate;
	
	@Column(name="upload_rfp_filename")
	private String uploadrfpfilename;
	
	@Column(name="upload_financials_filename")
	private String uploadfinancialsfilename;
	
	@Column(name="upload_company_profilename")
	private String uploadcompanyprofilename;
	
	@Column(name="upload_support_doc_one")
	private String uploadsupportdocone;
	
	@Column(name="upload_support_doc_two")
	private String uploadsupportdoctwo;
	
	@Column(name="upload_support_doc_three")
	private String uploadsupportdocthree;
	
	@Column(name="status")
	private String status;
}
