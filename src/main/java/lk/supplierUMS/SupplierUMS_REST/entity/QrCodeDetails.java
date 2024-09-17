package lk.supplierUMS.SupplierUMS_REST.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="qr_code_details")
public class QrCodeDetails implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="qr_id")
	private long qrId;
	
	@Column(name="telephone_number")
	private long tpNumber;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="ticket_number")
	private Long ticketNumber;
	
	@Column(name="status")
	private long status;
	
	@Column(name="ticket_date")
	private LocalDate ticketDate;
	
	@Column(name="accessed_date")
	private LocalDate accessedDate;
	
	@Column(name="accessed_time")
	private LocalTime accessedTime;
	
	@Column(name="email_status")
	private long emailStatus;
	
	@Column(name="qr_path")
	private String qrPath;

	public long getQrId() {
		return qrId;
	}

	public long getTpNumber() {
		return tpNumber;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setQrId(long qrId) {
		this.qrId = qrId;
	}

	public void setTpNumber(long tpNumber) {
		this.tpNumber = tpNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public Long getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(Long ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public LocalDate getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(LocalDate ticketDate) {
		this.ticketDate = ticketDate;
	}

	public LocalDate getAccessedDate() {
		return accessedDate;
	}

	public void setAccessedDate(LocalDate accessedDate) {
		this.accessedDate = accessedDate;
	}

	public LocalTime getAccessedTime() {
		return accessedTime;
	}

	public void setAccessedTime(LocalTime accessedTime) {
		this.accessedTime = accessedTime;
	}

	public long getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(long emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getQrPath() {
		return qrPath;
	}

	public void setQrPath(String qrPath) {
		this.qrPath = qrPath;
	}
	
	
	
}
