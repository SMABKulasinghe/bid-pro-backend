package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Entity
@Table(name = "t_contract_invoice_details")
public class TempContractInvoiceDetails implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="m_invoice_details_id")
	private long invoicedetailsid;
	
	@Column(name="m_invoice_header_id")
	private long invoiceheaderid;
	
	@Column(name = "i_contract_no")
	private String contractno;
	
	@Column(name="m_invoice_number")
	private String invoicenumber;
	
	@Column(name="m_item_code")
	private String itemcode;
	
	@Column(name="m_line_description")
	private String linedescription;
	
	@Column(name="m_item_qty")
	private long itemquantity;
	
	@Column(name="m_item_amount")
	private BigDecimal itemamount;
	
	@Column(name="m_item_uom")
	private String uom;
	
	@Column(name="m_unit_price")
	private BigDecimal unitprice;
	
	@Column(name="m_service_charge")
	private BigDecimal servicecharge;
	
	@Column(name="m_item_line_discount")
	private BigDecimal linediscount;
	
	@Column(name="m_location_code")
	private String locationcode;
	
	@Column(name="m_location_name")
	private String locationname;
	
	@Column(name="m_line_no")
	private String linenumber;
	
	@Column(name="m_line_amount")
	private BigDecimal lineamount;
	
	@CreationTimestamp
	@Column(name = "i_createddate")
	private LocalDateTime createdate;

	@UpdateTimestamp
	@Column(name = "i_updatedate")
	private LocalDateTime iupdatedate;

	@Column(name = "i_status")
	private String status;
	
	@Column(name="i_createduser")
	private String createduser;
	
	@Column(name="i_updateduser")
	private String updateduser;
	
	@PrePersist
    public void setCreatedOn() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		this.createduser =  user.getUserid();
		this.updateduser =  user.getUserid();
    }

	public TempContractInvoiceDetails() {
		super();
	}

	public long getInvoicedetailsid() {
		return invoicedetailsid;
	}

	public void setInvoicedetailsid(long invoicedetailsid) {
		this.invoicedetailsid = invoicedetailsid;
	}

	public long getInvoiceheaderid() {
		return invoiceheaderid;
	}

	public void setInvoiceheaderid(long invoiceheaderid) {
		this.invoiceheaderid = invoiceheaderid;
	}

	public String getContractno() {
		return contractno;
	}

	public void setContractno(String contractno) {
		this.contractno = contractno;
	}

	public String getInvoicenumber() {
		return invoicenumber;
	}

	public void setInvoicenumber(String invoicenumber) {
		this.invoicenumber = invoicenumber;
	}

	public String getItemcode() {
		return itemcode;
	}

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public String getLinedescription() {
		return linedescription;
	}

	public void setLinedescription(String linedescription) {
		this.linedescription = linedescription;
	}

	public long getItemquantity() {
		return itemquantity;
	}

	public void setItemquantity(long itemquantity) {
		this.itemquantity = itemquantity;
	}

	public BigDecimal getItemamount() {
		return itemamount;
	}

	public void setItemamount(BigDecimal itemamount) {
		this.itemamount = itemamount;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public BigDecimal getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(BigDecimal unitprice) {
		this.unitprice = unitprice;
	}


	public BigDecimal getServicecharge() {
		return servicecharge;
	}

	public void setServicecharge(BigDecimal servicecharge) {
		this.servicecharge = servicecharge;
	}

	public BigDecimal getLinediscount() {
		return linediscount;
	}

	public void setLinediscount(BigDecimal linediscount) {
		this.linediscount = linediscount;
	}

	public String getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public String getLinenumber() {
		return linenumber;
	}

	public void setLinenumber(String linenumber) {
		this.linenumber = linenumber;
	}

	public BigDecimal getLineamount() {
		return lineamount;
	}

	public void setLineamount(BigDecimal lineamount) {
		this.lineamount = lineamount;
	}

	public LocalDateTime getCreatedate() {
		return createdate;
	}

	public void setCreatedate(LocalDateTime createdate) {
		this.createdate = createdate;
	}

	public LocalDateTime getIupdatedate() {
		return iupdatedate;
	}

	public void setIupdatedate(LocalDateTime iupdatedate) {
		this.iupdatedate = iupdatedate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateduser() {
		return createduser;
	}

	public void setCreateduser(String createduser) {
		this.createduser = createduser;
	}

	public String getUpdateduser() {
		return updateduser;
	}

	public void setUpdateduser(String updateduser) {
		this.updateduser = updateduser;
	}
	
	
}
