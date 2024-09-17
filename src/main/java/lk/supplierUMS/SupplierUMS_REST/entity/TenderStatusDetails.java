package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_tender_status")
public class TenderStatusDetails implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="t_status_id")
	private long tender_status_id;
	
	@Column(name="t_name")
	private String tender_status_name;
	
	@Column(name="t_discription")
	private String tender_status_description;

	public long getTender_status_id() {
		return tender_status_id;
	}

	public void setTender_status_id(long tender_status_id) {
		this.tender_status_id = tender_status_id;
	}

	public String getTender_status_name() {
		return tender_status_name;
	}

	public void setTender_status_name(String tender_status_name) {
		this.tender_status_name = tender_status_name;
	}

	public String getTender_status_description() {
		return tender_status_description;
	}

	public void setTender_status_description(String tender_status_description) {
		this.tender_status_description = tender_status_description;
	}
	
	
}
