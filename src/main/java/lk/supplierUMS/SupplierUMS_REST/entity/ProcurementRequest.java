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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
@Table(name="m_procurement_request")
public class ProcurementRequest implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pr_id")
	private Long prID;
	
	@Column(name="rfp_id")
	private Long rfpID;
	
	@Column(name="pr_number")
	private String procurementRequestNumber;
	
	@Column(name="pr_filename")
	private String procurementRequestFileName;
	
	@Column(name="pr_department")
	private String Department;
	
	@Column(name="pr_technical_evolution_team")
	private String technicalEvolutionTeam;
	
	@Column(name="pr_date")
	private LocalDate date;
	
	@Column(name="pr_requester")
	private String requester;
	
	@Column(name="pr_designation")
	private String designation;
	
	@Column(name="pr_head_approval")
	private String headApproval;
	
	@Column(name="pr_name")
	private String name;
	
	@Column(name="pr_budgeted")
	private String budgeted;
	
	@Column(name="pr_expenditure")
	private String expenditure ;
	
	@Column(name="pr_project_cost")
	private Long projectCost;
	
	@Column(name="pr_existing_supplier")
	private String existingSupplier;
	
	@Column(name="pr_existing_prices")
	private Long existingPrices;
	
	@Column(name="pr_approved_date")
	private LocalDate approvedDate;
	
	@Column(name="pr_type")
	private String type;
	
	@Column(name="pr_service_category")
	private String serviceCategory;
	
	@Column(name="pr_bidding_period")
	private String biddingPeriod;
	
	@Column(name="pr_technical_clarifications")
	private String technicalClarifications;
	
	@Column(name="pr_sample_requirement")
	private String sampleRequirement;
	
	@Column(name="pr_payment_terms")
	private String paymentTerms;
	
	@Column(name="pr_quotation_validity")
	private String quotationValidity;
	
	@Column(name="pr_expected_validity")
	private String expectedValidity;
	
	@Column(name="pr_warranty_period")
	private String warrantyPeriod;
	
	@Column(name="pr_meeting_requirement")
	private String meetingRequirement;
	
	@Column(name="pr_other_vendors")
	private String otherVendors;
	
	@Column(name="pr_noted_consider")
	private String notedConsider;
	
	@Column(name="pr_status")
	private Long status;
	
	
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	// Audits
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
    
    @PrePersist
    public void setCreatedOn() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		this.createdBy = user.getUserid();
		this.modifiedBy =  user.getUserid();
		
		
		if (authentication.getPrincipal() instanceof User) {
			User us = (User) authentication.getPrincipal();
			
			System.out.println("userID--------- "+ us.getUserid());
			System.out.println("userComapnyCode--------- "+ us.getUserCompanyCode());		
			System.out.println("userRoleInBusiness--------- "+ us.getUserRoleInBussiness());
			
			
	
			System.out.println("userComapnyName--------- "+ us.getMmiddlenname());
			System.out.println("userRoleID--------- "+ us.getUserRoll());
			
		}
    }
    
    @PreUpdate
    public void preUpdate() {
        String modifiedByUser =  SecurityContextHolder.getContext().getAuthentication().getName();
        this.modifiedBy = modifiedByUser;
    }

    public String getTechnicalEvolutionTeam() {
		return technicalEvolutionTeam;
	}

	public void setTechnicalEvolutionTeam(String technicalEvolutionTeam) {
		this.technicalEvolutionTeam = technicalEvolutionTeam;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(LocalDate approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getHeadApproval() {
		return headApproval;
	}

	public void setHeadApproval(String headApproval) {
		this.headApproval = headApproval;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBudgeted() {
		return budgeted;
	}

	public void setBudgeted(String budgeted) {
		this.budgeted = budgeted;
	}

	public String getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(String expenditure) {
		this.expenditure = expenditure;
	}

	public Long getProjectCost() {
		return projectCost;
	}

	public void setProjectCost(Long projectCost) {
		this.projectCost = projectCost;
	}

	public String getExistingSupplier() {
		return existingSupplier;
	}

	public void setExistingSupplier(String existingSupplier) {
		this.existingSupplier = existingSupplier;
	}

	public Long getExistingPrices() {
		return existingPrices;
	}

	public void setExistingPrices(Long existingPrices) {
		this.existingPrices = existingPrices;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getServiceCategory() {
		return serviceCategory;
	}

	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	public String getBiddingPeriod() {
		return biddingPeriod;
	}

	public void setBiddingPeriod(String biddingPeriod) {
		this.biddingPeriod = biddingPeriod;
	}

	public String getTechnicalClarifications() {
		return technicalClarifications;
	}

	public void setTechnicalClarifications(String technicalClarifications) {
		this.technicalClarifications = technicalClarifications;
	}

	public String getSampleRequirement() {
		return sampleRequirement;
	}

	public void setSampleRequirement(String sampleRequirement) {
		this.sampleRequirement = sampleRequirement;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getQuotationValidity() {
		return quotationValidity;
	}

	public void setQuotationValidity(String quotationValidity) {
		this.quotationValidity = quotationValidity;
	}

	public String getExpectedValidity() {
		return expectedValidity;
	}

	public void setExpectedValidity(String expectedValidity) {
		this.expectedValidity = expectedValidity;
	}

	public String getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public void setWarrantyPeriod(String warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public String getMeetingRequirement() {
		return meetingRequirement;
	}

	public void setMeetingRequirement(String meetingRequirement) {
		this.meetingRequirement = meetingRequirement;
	}

	public String getOtherVendors() {
		return otherVendors;
	}

	public void setOtherVendors(String otherVendors) {
		this.otherVendors = otherVendors;
	}

	public String getNotedConsider() {
		return notedConsider;
	}

	public void setNotedConsider(String notedConsider) {
		this.notedConsider = notedConsider;
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


	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public Long getPrID() {
		return prID;
	}

	public void setPrID(Long prID) {
		this.prID = prID;
	}

	public Long getRfpID() {
		return rfpID;
	}

	public void setRfpID(Long rfpID) {
		this.rfpID = rfpID;
	}

	public String getProcurementRequestNumber() {
		return procurementRequestNumber;
	}

	public void setProcurementRequestNumber(String procurementRequestNumber) {
		this.procurementRequestNumber = procurementRequestNumber;
	}

	public String getProcurementRequestFileName() {
		return procurementRequestFileName;
	}

	public void setProcurementRequestFileName(String procurementRequestFileName) {
		this.procurementRequestFileName = procurementRequestFileName;
	}
	
	
}