package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
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
@Table(name="m_supplier_products_map")
public class SupplierProductDetails implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="m_sup_pro_map_id")
	private Long supplierProductMapID;
	
	@Column(name="m_category_id")
	private Long categoryID;
	
	@Column(name="m_sub_category_id")
	private Long subCategoryID;
	
	@Column(name="m_product_id")
	private Long productID;
	
	@Column(name="m_supplier_id")
	private Long supplierID;
	
	@Column(name="m_mapping_id")
	private Long mappingID;
	
	@Column(name="m_payment_due")
	private Long paymentDue;
	
	@Column(name="m_grand_total")
	private Long grandTotal;
	
	@Column(name="m_category_fee")
	private Long categoryFee;
	
	@Column(name="m_sub_category_fee")
	private Long subcategoryFee;
	
	
	
	
	
	
	public Long getSubcategoryFee() {
		return subcategoryFee;
	}

	public void setSubcategoryFee(Long subcategoryFee) {
		this.subcategoryFee = subcategoryFee;
	}

	public Long getCategoryFee() {
		return categoryFee;
	}

	public void setCategoryFee(Long categoryFee) {
		this.categoryFee = categoryFee;
	}

	public Long getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Long grandTotal) {
		this.grandTotal = grandTotal;
	}

		// Audits
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
	
	
	
	

	public Long getSupplierProductMapID() {
		return supplierProductMapID;
	}

	public void setSupplierProductMapID(Long supplierProductMapID) {
		this.supplierProductMapID = supplierProductMapID;
	}

	public Long getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}

	public Long getSubCategoryID() {
		return subCategoryID;
	}

	public void setSubCategoryID(Long subCategoryID) {
		this.subCategoryID = subCategoryID;
	}

	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
		this.productID = productID;
	}

	public Long getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(Long supplierID) {
		this.supplierID = supplierID;
	}

	public Long getMappingID() {
		return mappingID;
	}

	public void setMappingID(Long mappingID) {
		this.mappingID = mappingID;
	}

	public Long getPaymentDue() {
		return paymentDue;
	}

	public void setPaymentDue(Long paymentDue) {
		this.paymentDue = paymentDue;
	}
	
	
	
	
	
	
	
	

}
