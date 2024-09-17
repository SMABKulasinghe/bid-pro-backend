package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.ProcurementRequest;
import lk.supplierUMS.SupplierUMS_REST.entity.QrCodeDetails;

@Component
public interface QrCodeDetailsRepo extends JpaRepository<QrCodeDetails, Long>{

	@Query("SELECT qr FROM QrCodeDetails qr WHERE qr.ticketDate = ?2 AND (qr.name LIKE %?1% OR qr.email LIKE %?1% OR qr.tpNumber LIKE %?1% OR qr.ticketNumber LIKE %?1%)")
	Page<QrCodeDetails> getQrCodeTableDetails(String parameter, LocalDate localDate, PageRequest of);

	@Query("SELECT qr FROM QrCodeDetails qr WHERE qr.ticketNumber =?1 ")
	Optional<QrCodeDetails> findByTicketNumber(Long id);

	@Query("SELECT qr FROM QrCodeDetails qr WHERE qr.emailStatus =1 ")
	List<QrCodeDetails> getAll();

	@Query("SELECT qr FROM QrCodeDetails qr WHERE qr.status =2 AND qr.ticketDate=?1")
	List<QrCodeDetails> getAttendedParticipants(LocalDate nowDateDone);
	
	@Query("SELECT qr FROM QrCodeDetails qr WHERE qr.status =1 AND qr.ticketDate=?1")
	List<QrCodeDetails> getPendingParticipants(LocalDate nowDateDone);
	
	@Query("SELECT qr FROM QrCodeDetails qr WHERE qr.ticketDate=?1")
	List<QrCodeDetails> getAttendedPendingParticipants(LocalDate nowDateDone);
	
}