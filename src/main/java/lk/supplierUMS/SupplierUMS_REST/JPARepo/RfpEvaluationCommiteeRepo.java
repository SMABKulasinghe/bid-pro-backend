package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationCommitee;
import lk.supplierUMS.SupplierUMS_REST.entity.RfpEvaluationCommitee;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.User;

@Component
public interface RfpEvaluationCommiteeRepo extends JpaRepository<RfpEvaluationCommitee,Long>{

	Boolean existsBySubmitedUserIDAndTenderIDAndSupplierID(String committeemember, Long tenderID, Long supplierId);

	@Query("SELECT ts,td from TenderSubmissions ts INNER JOIN TenderDetails td ON ts.tenderNo = td.trnderid WHERE td.closingDateTime<=?1 AND ts.tenderResponse = 8 GROUP BY ts.tenderNo")
	List<Object[]> getTenderForRfpEvaComCreation(LocalDateTime dateTime);

	@Query("SELECT c,td FROM RfpEvaluationCommitee c INNER JOIN TenderDetails td ON td.trnderid = c.tenderID WHERE c.tenderID = ?2 AND (c.submitedUserID LIKE %?1% OR td.bidno LIKE %?1% OR td.tendername LIKE %?1%) GROUP BY  c.submitedUserID") //td.trnderid
	Page<Object[]> getrfpCommitteeView(String parameter, Long tendrID, PageRequest of);

	@Query("SELECT rec,td from RfpEvaluationCommitee rec INNER JOIN TenderDetails td ON rec.tenderID = td.trnderid WHERE rec.submitedUserID=?1 GROUP BY rec.tenderID")
	List<Object[]> getTenderForRfpEvaMaking(String userid);

	Optional<RfpEvaluationCommitee> findByTenderIDAndSupplierIDAndSubmitedUserID(Long valueOf, Long valueOf2,
			String userId);

	List<RfpEvaluationCommitee> findByTenderID(long tenderId);

	
	
}
