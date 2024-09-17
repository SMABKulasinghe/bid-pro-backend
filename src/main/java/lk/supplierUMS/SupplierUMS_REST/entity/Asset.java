package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name="m_asset")
public class Asset implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long assetID;
	
	@Column(name="category")
	private Long category;
	
	@Column(name="sub_category")
	private Long subCategory;
	
	@Column(name="subcompany_ID")
	private Long subCompanyID; // Asset Belonging Company ID on SubCompany Table
	
	@Column(name="supplier_code")
	private Long supplierCode;
	
	@Column(name="supplier_name")
	private String supplierName;
	
	@Column(name="asset_code")
	private String assetCode;
	
	@Column(name="email")
	private String email;
	
	@Column(name="brand")
	private String brnad;
	
	@Column(name="model")
	private String model;
	
	@Column(name="memory_gb")
	private Double memory;
	
	@Column(name="hdd_gb")
	private Double hdd;
	
	@Column(name="processor_ghz")
	private Double processor;
	
	@Column(name="current_market_value")
	private Double currentMarketValue;
	
	@Column(name="disposable_amount")
	private Double disposableAmout;
	
	@Column(name="deprecation_method")
	private String deprecationMethod;
	
	@Column(name="deprecation_amount")
	private Double deprecationAmount;
	
	@Column(name="current_condition_code")
	private String currentConditionCode;
	
	@Column(name="transfered_from_location")
	private String transferedFromLocation;
	
	@Column(name="current_location_code")
	private String currentLocationCode;
	
	@Column(name="transfered_date")
	private LocalDateTime transferedDate;
	
	@Column(name="status")
	private String status;
	
	@Column(name="last_repair_date")
	private LocalDateTime lastRepairDate;
	
	@Column(name="last_inspected_by")
	private String lastInspectedBy;
	
	@Column(name="buying_date")
	private LocalDateTime buyingDate;
	
	@Column(name="color")
	private String color;
	
	@Column(name="registration_no")
	private String registrationNo;
	
	@Column(name="serial_no")
	private String serialNo;
	
	@Column(name="movement_staus")
	private String assetmovementStatus;
	
	@Column(name="transfered_to_location")
	private String transferedToLocation; // Destination
	
	@Column(name="transfer_comment")
	private String transferComment; // Comment
	
	
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
		String currentPrincipalName = authentication.getName();
		this.createdBy = currentPrincipalName;
		this.modifiedBy = currentPrincipalName;
		
		
		if (authentication.getPrincipal() instanceof User) {
			User us = (User) authentication.getPrincipal();
			
			System.out.println("userID--------- "+ us.getUserid());
			System.out.println("userComapnyCode--------- "+ us.getUserCompanyCode());		
			System.out.println("userRoleInBusiness--------- "+ us.getUserRoleInBussiness());
			
			
			/*this.setActUserId(us.getUserid());
			this.setActUserBelongCompany(us.getUserCompanyCode());
			this.setActUserRoleInBusiness(us.getUserRoleInBussiness());*/
			
			System.out.println("userComapnyName--------- "+ us.getMmiddlenname());
			System.out.println("userRoleID--------- "+ us.getUserRoll());
			
		}
    }
    
    @PreUpdate
    public void preUpdate() {
        String modifiedByUser =  SecurityContextHolder.getContext().getAuthentication().getName();
        this.modifiedBy = modifiedByUser;
    }
	
	

	public Long getAssetID() {
		return assetID;
	}

	
	public Long getSupplierCode() {
		return supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public String getEmail() {
		return email;
	}

	public String getBrnad() {
		return brnad;
	}

	public String getModel() {
		return model;
	}

	public Double getMemory() {
		return memory;
	}

	public Double getHdd() {
		return hdd;
	}

	public Double getProcessor() {
		return processor;
	}

	public Double getCurrentMarketValue() {
		return currentMarketValue;
	}

	public Double getDisposableAmout() {
		return disposableAmout;
	}

	public String getDeprecationMethod() {
		return deprecationMethod;
	}

	public Double getDeprecationAmount() {
		return deprecationAmount;
	}

	public String getCurrentConditionCode() {
		return currentConditionCode;
	}

	public String getTransferedFromLocation() {
		return transferedFromLocation;
	}

	public String getCurrentLocationCode() {
		return currentLocationCode;
	}

	public LocalDateTime getTransferedDate() {
		return transferedDate;
	}

	public String getStatus() {
		return status;
	}

	public LocalDateTime getLastRepairDate() {
		return lastRepairDate;
	}

	public String getLastInspectedBy() {
		return lastInspectedBy;
	}

	public LocalDateTime getBuyingDate() {
		return buyingDate;
	}

	public String getColor() {
		return color;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setAssetID(Long assetID) {
		this.assetID = assetID;
	}

	
	public void setSupplierCode(Long supplierCode) {
		this.supplierCode = supplierCode;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBrnad(String brnad) {
		this.brnad = brnad;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setMemory(Double memory) {
		this.memory = memory;
	}

	public void setHdd(Double hdd) {
		this.hdd = hdd;
	}

	public void setProcessor(Double processor) {
		this.processor = processor;
	}

	public void setCurrentMarketValue(Double currentMarketValue) {
		this.currentMarketValue = currentMarketValue;
	}

	public void setDisposableAmout(Double disposableAmout) {
		this.disposableAmout = disposableAmout;
	}

	public void setDeprecationMethod(String deprecationMethod) {
		this.deprecationMethod = deprecationMethod;
	}

	public void setDeprecationAmount(Double deprecationAmount) {
		this.deprecationAmount = deprecationAmount;
	}

	public void setCurrentConditionCode(String currentConditionCode) {
		this.currentConditionCode = currentConditionCode;
	}

	public void setTransferedFromLocation(String transferedFromLocation) {
		this.transferedFromLocation = transferedFromLocation;
	}

	public void setCurrentLocationCode(String currentLocationCode) {
		this.currentLocationCode = currentLocationCode;
	}

	public void setTransferedDate(LocalDateTime transferedDate) {
		this.transferedDate = transferedDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setLastRepairDate(LocalDateTime lastRepairDate) {
		this.lastRepairDate = lastRepairDate;
	}

	public void setLastInspectedBy(String lastInspectedBy) {
		this.lastInspectedBy = lastInspectedBy;
	}

	public void setBuyingDate(LocalDateTime buyingDate) {
		this.buyingDate = buyingDate;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}


	public Long getCategory() {
		return category;
	}


	public Long getSubCategory() {
		return subCategory;
	}


	public void setCategory(Long category) {
		this.category = category;
	}


	public void setSubCategory(Long subCategory) {
		this.subCategory = subCategory;
	}


	public String getAssetmovementStatus() {
		return assetmovementStatus;
	}


	public void setAssetmovementStatus(String assetmovementStatus) {
		this.assetmovementStatus = assetmovementStatus;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getSubCompanyID() {
		return subCompanyID;
	}

	public void setSubCompanyID(Long subCompanyID) {
		this.subCompanyID = subCompanyID;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getTransferedToLocation() {
		return transferedToLocation;
	}

	public void setTransferedToLocation(String transferedToLocation) {
		this.transferedToLocation = transferedToLocation;
	}

	public String getTransferComment() {
		return transferComment;
	}

	public void setTransferComment(String transferComment) {
		this.transferComment = transferComment;
	}


	

	
}
