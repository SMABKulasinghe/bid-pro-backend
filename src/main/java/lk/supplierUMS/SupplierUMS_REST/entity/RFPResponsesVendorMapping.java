package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="p_rfp_response_vendor_mapping")
public class RFPResponsesVendorMapping implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="tender_no")
	private String tenderNo;
	
	@Column(name = "vendor_seq")
	private long  vendorseq;
	
	@Column(name = "vendor_no")
	private long  vendorno;
	
	@Column(name="status")
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTenderNo() {
		return tenderNo;
	}

	public void setTenderNo(String tenderNo) {
		this.tenderNo = tenderNo;
	}

	public long getVendorseq() {
		return vendorseq;
	}

	public void setVendorseq(long vendorseq) {
		this.vendorseq = vendorseq;
	}

	public long getVendorno() {
		return vendorno;
	}

	public void setVendorno(long vendorno) {
		this.vendorno = vendorno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
