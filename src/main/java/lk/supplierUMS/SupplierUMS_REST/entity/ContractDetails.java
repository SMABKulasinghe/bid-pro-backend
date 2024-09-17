package lk.supplierUMS.SupplierUMS_REST.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name="m_contract_details")
public class ContractDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_id")
	private Long contractId;
	
	@Column(name="m_mapping_code")
	private Long mappingCode;
	
	@Column(name="m_contract_no")
	private String contractNo;
	
	@Column(name="m_contract_details")
	private String contractDetails;
	
	@Column(name="m_category")
	private String category;
	
	@Column(name="m_original_date")
	private LocalDate originalDate;
	
	@Column(name="m_start_date_period")
	private LocalDate startDatePeriod;
	
	@Column(name="m_expiry_date")
	private Date expiryDate;
	
	@Column(name="m_reminder_date")
	private LocalDate reminderDate;
	
	@Column(name="m_contract_amount")
	private BigDecimal contractAmount;
	
	@Column(name="amc_usd")
	private BigDecimal amcusd;
	
	@Column(name="m_payment_type")
	private String paymentType;
	
	@Column(name="m_annual_payment_amount_usd")
	private Double annualPaymentAmountUSD;
	
	@Column(name="m_annual_payment_amount_lkr")
	private Double annualPaymentAmountLKR;
	
	@Column(name="m_conversion_rate_usd")
	private Double conversionRateUSD;
	
	@Column(name="m_monthly_payment_amount_usd")
	private Double monthlyPaymentAmountUSD;
	
	@Column(name="m_monthly_payment_amount_lkr")
	private Double monthlyPaymentAmountLKR;
	
	@Column(name="m_board_approval_number")
	private String boardApprovalNumber;
	
	@Column(name="m_board_paper_date")
	private LocalDate boardPaperDate;
	
	@Column(name="m_board_approval_date")
	private LocalDate boardApprovalDate;
	
	@Column(name="m_board_paper_originated_by")
	private String boardApprovalOriginatedDateBy;
	
	@Column(name="m_special_remarks_in_the_approval_paper")
	private String specialRemarksInTheApprovalPaper;
	
	@Column(name="m_purchase_order_date")
	private LocalDate purchaseOrderDate;
	
	@Column(name="m_purchase_order_number")
	private String purchaseOrderNumber;
	
	@Column(name="m_contract_sign_by_company")
	private String contractSignByCompany;
	
	@Column(name="m_contract_sign_by_company_designation")
	private String contractSignByCompanyDesignation;
	
	@Column(name="m_contract_sign_by_suppier")
	private String contractSignBySupplier;
	
	@Column(name="m_contract_sign_by_suppier_designation")
	private String contractSignBySupplierDesignation;
	
	@Column(name="m_contract_approval_status")
	private String contractApprovalStatus;
	
	@Column(name="m_contract_approval_reason")
	private String contractApprovalReason;
	
	@Column(name="m_upload_board_approval")
	private String uploadBoardApproval;
	
	@Column(name="m_upload_contract ")
	private String uploadContract;
	
	@Column(name="m_upload_purchase_order")
	private String uploadPurchaseOrder;
	
	@Column(name="renewal_date_period")
	private long RenewalDatePeriod;
	
	@Column(name="m_contract_outstanding_amount")
	private BigDecimal contractOutstandingAmount;
	
	@Column(name="m_renewal_date")
	private LocalDate renewalDate;
	
	
	@CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
    
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
    
	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public Long getMappingCode() {
		return mappingCode;
	}

	public void setMappingCode(Long mappingCode) {
		this.mappingCode = mappingCode;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractDetails() {
		return contractDetails;
	}

	public void setContractDetails(String contractDetails) {
		this.contractDetails = contractDetails;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getOriginalDate() {
		return originalDate;
	}

	public void setOriginalDate(LocalDate originalDate) {
		this.originalDate = originalDate;
	}

	public LocalDate getStartDatePeriod() {
		return startDatePeriod;
	}

	public void setStartDatePeriod(LocalDate startDatePeriod) {
		this.startDatePeriod = startDatePeriod;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDate getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(LocalDate reminderDate) {
		this.reminderDate = reminderDate;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getAnnualPaymentAmountUSD() {
		return annualPaymentAmountUSD;
	}

	public void setAnnualPaymentAmountUSD(Double annualPaymentAmountUSD) {
		this.annualPaymentAmountUSD = annualPaymentAmountUSD;
	}

	public Double getAnnualPaymentAmountLKR() {
		return annualPaymentAmountLKR;
	}

	public void setAnnualPaymentAmountLKR(Double annualPaymentAmountLKR) {
		this.annualPaymentAmountLKR = annualPaymentAmountLKR;
	}

	public Double getConversionRateUSD() {
		return conversionRateUSD;
	}

	public void setConversionRateUSD(Double conversionRateUSD) {
		this.conversionRateUSD = conversionRateUSD;
	}

	public Double getMonthlyPaymentAmountUSD() {
		return monthlyPaymentAmountUSD;
	}

	public void setMonthlyPaymentAmountUSD(Double monthlyPaymentAmountUSD) {
		this.monthlyPaymentAmountUSD = monthlyPaymentAmountUSD;
	}

	public Double getMonthlyPaymentAmountLKR() {
		return monthlyPaymentAmountLKR;
	}

	public void setMonthlyPaymentAmountLKR(Double monthlyPaymentAmountLKR) {
		this.monthlyPaymentAmountLKR = monthlyPaymentAmountLKR;
	}

	public String getBoardApprovalNumber() {
		return boardApprovalNumber;
	}

	public void setBoardApprovalNumber(String boardApprovalNumber) {
		this.boardApprovalNumber = boardApprovalNumber;
	}

	public LocalDate getBoardPaperDate() {
		return boardPaperDate;
	}

	public void setBoardPaperDate(LocalDate boardPaperDate) {
		this.boardPaperDate = boardPaperDate;
	}

	public LocalDate getBoardApprovalDate() {
		return boardApprovalDate;
	}

	public void setBoardApprovalDate(LocalDate boardApprovalDate) {
		this.boardApprovalDate = boardApprovalDate;
	}

	public String getBoardApprovalDateBy() {
		return boardApprovalOriginatedDateBy;
	}

	public void setBoardApprovalDateBy(String boardApprovalDateBy) {
		this.boardApprovalOriginatedDateBy = boardApprovalDateBy;
	}

	public String getSpecialRemarksInTheApprovalPaper() {
		return specialRemarksInTheApprovalPaper;
	}

	public void setSpecialRemarksInTheApprovalPaper(String specialRemarksInTheApprovalPaper) {
		this.specialRemarksInTheApprovalPaper = specialRemarksInTheApprovalPaper;
	}

	public LocalDate getPurchaseOrderDate() {
		return purchaseOrderDate;
	}

	public void setPurchaseOrderDate(LocalDate purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}

	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public String getContractSignByCompany() {
		return contractSignByCompany;
	}

	public void setContractSignByCompany(String contractSignByCompany) {
		this.contractSignByCompany = contractSignByCompany;
	}

	public String getContractSignBySupplier() {
		return contractSignBySupplier;
	}

	public void setContractSignBySupplier(String contractSignBySupplier) {
		this.contractSignBySupplier = contractSignBySupplier;
	}

	public String getUploadBoardApproval() {
		return uploadBoardApproval;
	}

	public void setUploadBoardApproval(String uploadBoardApproval) {
		this.uploadBoardApproval = uploadBoardApproval;
	}

	public String getUploadContract() {
		return uploadContract;
	}

	public void setUploadContract(String uploadContract) {
		this.uploadContract = uploadContract;
	}

	public String getUploadPurchaseOrder() {
		return uploadPurchaseOrder;
	}

	public void setUploadPurchaseOrder(String uploadPurchaseOrder) {
		this.uploadPurchaseOrder = uploadPurchaseOrder;
	}

	public String getBoardApprovalOriginatedDateBy() {
		return boardApprovalOriginatedDateBy;
	}

	public void setBoardApprovalOriginatedDateBy(String boardApprovalOriginatedDateBy) {
		this.boardApprovalOriginatedDateBy = boardApprovalOriginatedDateBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getContractSignByCompanyDesignation() {
		return contractSignByCompanyDesignation;
	}

	public String getContractSignBySupplierDesignation() {
		return contractSignBySupplierDesignation;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setContractSignByCompanyDesignation(String contractSignByCompanyDesignation) {
		this.contractSignByCompanyDesignation = contractSignByCompanyDesignation;
	}

	public void setContractSignBySupplierDesignation(String contractSignBySupplierDesignation) {
		this.contractSignBySupplierDesignation = contractSignBySupplierDesignation;
	}

	public String getContractApprovalStatus() {
		return contractApprovalStatus;
	}

	public String getContractApprovalReason() {
		return contractApprovalReason;
	}

	public void setContractApprovalStatus(String contractApprovalStatus) {
		this.contractApprovalStatus = contractApprovalStatus;
	}

	public void setContractApprovalReason(String contractApprovalReason) {
		this.contractApprovalReason = contractApprovalReason;
	}

	public long getRenewalDatePeriod() {
		return RenewalDatePeriod;
	}

	public void setRenewalDatePeriod(long renewalDatePeriod) {
		RenewalDatePeriod = renewalDatePeriod;
	}

	public BigDecimal getContractOutstandingAmount() {
		return contractOutstandingAmount;
	}

	public void setContractOutstandingAmount(BigDecimal contractOutstandingAmount) {
		this.contractOutstandingAmount = contractOutstandingAmount;
	}

	public BigDecimal getAmcusd() {
		return amcusd;
	}

	public void setAmcusd(BigDecimal amcusd) {
		this.amcusd = amcusd;
	}

	public LocalDate getRenewalDate() {
		return renewalDate;
	}

	public void setRenewalDate(LocalDate renewalDate) {
		this.renewalDate = renewalDate;
	}
	
	
}
