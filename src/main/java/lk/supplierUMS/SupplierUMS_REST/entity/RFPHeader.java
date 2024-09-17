package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="p_rfp_header")
public class RFPHeader implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rfp_h_id")
	private Long rfpHId;
	
	@Column(name="rfp_id")
	private Long rfpId;
	
	@Column(name="rfp_h_name")
	private String rfpHeaderName;

	public void setRfpHeaderName(String rfpHeaderName) {
		this.rfpHeaderName = rfpHeaderName;
	}

	public Long getRfpHId() {
		return rfpHId;
	}

	public Long getRfpId() {
		return rfpId;
	}

	public String getRfpHeaderName() {
		return rfpHeaderName;
	}

	public void setRfpHId(Long rfpHId) {
		this.rfpHId = rfpHId;
	}

	public void setRfpId(Long rfpId) {
		this.rfpId = rfpId;
	}
	
}
