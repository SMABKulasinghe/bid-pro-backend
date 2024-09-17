package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
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
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.exolab.castor.types.DateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
@Table(name="m_tender_details")
public class TenderDetails implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="t_id")
	private long trnderid;
	
	@Column(name="t_bidno")
	private String bidno;
	
	@Column(name="t_name")
	private String tendername;
	
	@Column(name="t_discription")
	private String tenderdescription;
	
	@Column(name="t_quantity")
	private Integer quantity;

	@Column(name="t_closing_date")
	private LocalDate closingdate;
	
	@Column(name="t_closing_time")
	private LocalTime closingtime;
	
	@Column(name="t_cordinator1_name")
	private String cordinator1name;
	
	@Column(name="t_cordinator1_designation")
	private String cordinator1designation;
	
	@Column(name="t_cordinator1_eail")
	private String cordinator1email;
	
	@Column(name="t_cordinator1_contactno")
	private String cordinator1contactno;
	
	@Column(name="t_cordinator2_name")
	private String cordinator2name;
	
	@Column(name="t_cordinator2_designation")
	private String cordinator2designation;
	
	@Column(name="t_cordinator2_eail")
	private String cordinator2email;
	
	@Column(name="t_cordinator2_contactno")
	private String cordinator2contactno;
	
	@Column(name="t_comments")
	private String comments;
	
	@Column(name="t_rfp_filename")
	private String rfpfilename;
	
	@Column(name="t_supportdoc1_name")
	private String supportdoc1name;
	
	@Column(name="t_supportdoc2_name")
	private String supportdoc2name;
	
	@Column(name="t_status")
	private String status;
	
	@Column(name="t_approval_status")
	private String approvalstatus;
	
	@Column(name="approve_reason")
	private String approveReason;
	
	@Column(name="tender_approvedby")
	private String approvedBy;
	
	@Column(name="tender_approve_date_and_time")
	private LocalDateTime approveDateTime;

	@Column(name="t_open_date")
	private LocalDate openingdate;
	
	@Column(name="eligible_cat_id")	
	private long eligibleCategortID;
	
	@Column(name = "eligible_sub_cat_id")
	private long eligibleSubcatId;
	
	@Column(name = "eligible_sub_cat_prod_id")
	private long eligiblesubcatProdid;
	
	@Column(name="t_confirm")	
	private String confirm;
	
	@Column(name="t_response")	
	private String response;
	
	@Column(name="rfp_id")
	private Long rfpId;
	
	@Column(name="t_closing_date_and_time")
	private LocalDateTime closingDateTime;

	public LocalDateTime getClosingDateTime() {
		return closingDateTime;
	}

	public void setClosingDateTime(LocalDateTime closingDateTime) {
		this.closingDateTime = closingDateTime;
	}

	public Long getRfpId() {
		return rfpId;
	}

	public void setRfpId(Long rfpId) {
		this.rfpId = rfpId;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovalstatus() {
		return approvalstatus;
	}
	
	public LocalDateTime getApproveDateTime() {
		return approveDateTime;
	}

	public void setApproveDateTime(LocalDateTime approveDateTime) {
		this.approveDateTime = approveDateTime;
	}

	public void setApprovalstatus(String approvalstatus) {
		this.approvalstatus = approvalstatus;
	}
	
	public long getEligibleCategortID() {
		return eligibleCategortID;
	}

	public void setEligibleCategortID(long eligibleCategortID) {
		this.eligibleCategortID = eligibleCategortID;
	}


	public long getEligibleSubcatId() {
		return eligibleSubcatId;
	}


	public void setEligibleSubcatId(long eligibleSubcatId) {
		this.eligibleSubcatId = eligibleSubcatId;
	}


	public long getEligiblesubcatProdid() {
		return eligiblesubcatProdid;
	}


	public void setEligiblesubcatProdid(long eligiblesubcatProdid) {
		this.eligiblesubcatProdid = eligiblesubcatProdid;
	}

	
	
	@CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by", nullable = false, updatable = false)
    @CreatedBy
    private String createdBy;
 
    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;
    
    
    public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	
	

	@PrePersist
    public void setCreatedOn() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		this.createdBy = currentPrincipalName;
		this.modifiedBy = currentPrincipalName;
		
		
		if (authentication.getPrincipal() instanceof User) {
			User us = (User) authentication.getPrincipal();
		
			
		}
    }
    

    @PreUpdate
    public void preUpdate() {
    	
    	if(SecurityContextHolder.getContext().getAuthentication().getName() == null) {
    		this.modifiedBy = "SYSTEM";
    	}else {
    		String modifiedByUser =  SecurityContextHolder.getContext().getAuthentication().getName();
    		this.modifiedBy = modifiedByUser;
    	}
    	
		/*
		 * try { String modifiedByUser =
		 * SecurityContextHolder.getContext().getAuthentication().getName();
		 * this.modifiedBy = modifiedByUser; } catch (Exception e) {
		 * e.printStackTrace(); this.modifiedBy = "SYSTEM"; }
		 */
       
    }
	
    public String getApproveReason() {
		return approveReason;
	}

	public void setApproveReason(String approveReason) {
		this.approveReason = approveReason;
	} 
    
	public LocalDate getOpeningdate() {
		return openingdate;
	}


	public void setOpeningdate(LocalDate openingdate) {
		this.openingdate = openingdate;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public long getTrnderid() {
		return trnderid;
	}

	public void setTrnderid(long trnderid) {
		this.trnderid = trnderid;
	}

	public String getBidno() {
		return bidno;
	}

	public void setBidno(String bidno) {
		this.bidno = bidno;
	}

	public String getTendername() {
		return tendername;
	}

	public void setTendername(String tendername) {
		this.tendername = tendername;
	}

	public String getTenderdescription() {
		return tenderdescription;
	}

	public void setTenderdescription(String tenderdescription) {
		this.tenderdescription = tenderdescription;
	}


	public LocalDate getClosingdate() {
		return closingdate;
	}

	public void setClosingdate(LocalDate closingdate) {
		this.closingdate = closingdate;
	}

	public LocalTime getClosingtime() {
		return closingtime;
	}

	public void setClosingtime(LocalTime closingtime) {
		this.closingtime = closingtime;
	}

	public String getCordinator1name() {
		return cordinator1name;
	}

	public void setCordinator1name(String cordinator1name) {
		this.cordinator1name = cordinator1name;
	}

	public String getCordinator1designation() {
		return cordinator1designation;
	}

	public void setCordinator1designation(String cordinator1designation) {
		this.cordinator1designation = cordinator1designation;
	}

	public String getCordinator1email() {
		return cordinator1email;
	}

	public void setCordinator1email(String cordinator1email) {
		this.cordinator1email = cordinator1email;
	}

	public String getCordinator1contactno() {
		return cordinator1contactno;
	}

	public void setCordinator1contactno(String cordinator1contactno) {
		this.cordinator1contactno = cordinator1contactno;
	}

	public String getCordinator2name() {
		return cordinator2name;
	}

	public void setCordinator2name(String cordinator2name) {
		this.cordinator2name = cordinator2name;
	}

	public String getCordinator2designation() {
		return cordinator2designation;
	}

	public void setCordinator2designation(String cordinator2designation) {
		this.cordinator2designation = cordinator2designation;
	}

	public String getCordinator2email() {
		return cordinator2email;
	}

	public void setCordinator2email(String cordinator2email) {
		this.cordinator2email = cordinator2email;
	}

	public String getCordinator2contactno() {
		return cordinator2contactno;
	}

	public void setCordinator2contactno(String cordinator2contactno) {
		this.cordinator2contactno = cordinator2contactno;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRfpfilename() {
		return rfpfilename;
	}

	public void setRfpfilename(String rfpfilename) {
		this.rfpfilename = rfpfilename;
	}

	public String getSupportdoc1name() {
		return supportdoc1name;
	}

	public void setSupportdoc1name(String supportdoc1name) {
		this.supportdoc1name = supportdoc1name;
	}

	public String getSupportdoc2name() {
		return supportdoc2name;
	}

	public void setSupportdoc2name(String supportdoc2name) {
		this.supportdoc2name = supportdoc2name;
	}


	
	
	
}
