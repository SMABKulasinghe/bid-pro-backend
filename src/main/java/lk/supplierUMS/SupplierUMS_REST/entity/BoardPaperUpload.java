package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="m_board_paper_upload")
public class BoardPaperUpload implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="board_paper_id")
	private Long boardId;
	
	@Column(name="tender_id")
	private Long tenderId;

	@Column(name="board_paper_file_name")
	private String boardPaperFileName;
	
	@Column(name="status")
	private String status;

	@Column(name="created_date_and_time")
	private LocalDateTime createdDateTime;
	
	@Column(name="created_user")
	private String createdUser;
	
	@Column(name="cappex_file_name")
	private String cappexFileName;
	
	@Column(name="oppexx_file_name")
	private String oppexFileName;
	
	@Column(name="memo_file_name")
	private String memoFileName;

	public Long getBoardId() {
		return boardId;
	}

	public Long getTenderId() {
		return tenderId;
	}

	public String getBoardPaperFileName() {
		return boardPaperFileName;
	}
	
	

	public String getCappexFileName() {
		return cappexFileName;
	}

	public String getOppexFileName() {
		return oppexFileName;
	}

	public String getMemoFileName() {
		return memoFileName;
	}

	public void setCappexFileName(String cappexFileName) {
		this.cappexFileName = cappexFileName;
	}

	public void setOppexFileName(String oppexFileName) {
		this.oppexFileName = oppexFileName;
	}

	public void setMemoFileName(String memoFileName) {
		this.memoFileName = memoFileName;
	}

	public String getStatus() {
		return status;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	public void setTenderId(Long tenderId) {
		this.tenderId = tenderId;
	}

	public void setBoardPaperFileName(String boardPaperFileName) {
		this.boardPaperFileName = boardPaperFileName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	
	
}
