package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="t_evaluation_marks")
public class EvaluationMarks implements Serializable {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="t_evaluation_marks_id")
	private Long evaluationmarksID;
	
	@Column(name="m_evaluation_marksheet_id")
	private Long evaluationmarksheetID;
	
	@Column(name="tender_id")
	private Long tenderId;
	
	@Column(name="criteria_max_mark")
	private Double criteriamaxName;
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="create_date")
	private LocalDate createDate;
	
	@Column(name="eve_marks")
	private Double eveMarks;
	
	@Column(name="c_code")
	private String companyCode; // venoorID on subcompany -- should be a Long
	
	@Column(name="comment")
	private String comment;
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@CreationTimestamp
    private Date createdAt;
	
	
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Long getEvaluationmarksID() {
		return evaluationmarksID;
	}

	public Long getEvaluationmarksheetID() {
		return evaluationmarksheetID;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public Double getCriteriamaxName() {
		return criteriamaxName;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public Double getEveMarks() {
		return eveMarks;
	}

	public void setEvaluationmarksID(Long evaluationmarksID) {
		this.evaluationmarksID = evaluationmarksID;
	}

	public void setEvaluationmarksheetID(Long evaluationmarksheetID) {
		this.evaluationmarksheetID = evaluationmarksheetID;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public void setCriteriamaxName(Double criteriamaxName) {
		this.criteriamaxName = criteriamaxName;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public void setEveMarks(Double eveMarks) {
		this.eveMarks = eveMarks;
	}
	 
	
}
