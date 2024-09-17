package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="m_tag_mapping")
public class TagMapping implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "m_tag_mapping_id")
	private long tagMappingId;
	
	@Column(name = "m_tags_tagid")
	Long Tags_TagId;
	
	@Column(name = "m_sub_companyID")
	private long subCompany_SubCompanyID;

	public long getTagMappingId() {
		return tagMappingId;
	}

	public void setTagMappingId(long tagMappingId) {
		this.tagMappingId = tagMappingId;
	}

	public Long getTags_TagId() {
		return Tags_TagId;
	}

	public void setTags_TagId(Long tags_TagId) {
		Tags_TagId = tags_TagId;
	}

	public long getSubCompany_SubCompanyID() {
		return subCompany_SubCompanyID;
	}

	public void setSubCompany_SubCompanyID(long subCompany_SubCompanyID) {
		this.subCompany_SubCompanyID = subCompany_SubCompanyID;
	}
	
	
	

}
