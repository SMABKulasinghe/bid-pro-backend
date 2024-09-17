package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="p_rfp_details")
public class RFPDetails implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rfp_d_id")
	private Long rfpDId;
	
	@Column(name="rfp_h_id")
	private Long rfpHId;
	
	@Column(name="rfp_d_filed_name")
	private String rfpDFiledNmae;
	


	@Column(name="rfp_id")
	private Long rfpId;
	
	public Long getRfpId() {
		return rfpId;
	}

	public void setRfpId(Long rfpId) {
		this.rfpId = rfpId;
	}

	public Long getRfpDId() {
		return rfpDId;
	}

	public Long getRfpHId() {
		return rfpHId;
	}

	public String getRfpDFiledNmae() {
		return rfpDFiledNmae;
	}

	public void setRfpDId(Long rfpDId) {
		this.rfpDId = rfpDId;
	}

	public void setRfpHId(Long rfpHId) {
		this.rfpHId = rfpHId;
	}

	public void setRfpDFiledNmae(String rfpDFiledNmae) {
		this.rfpDFiledNmae = rfpDFiledNmae;
	}
	
	
}
