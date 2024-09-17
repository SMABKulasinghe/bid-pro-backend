package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationCommitee;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.User;

@Component
public interface EvaluationCommiteeRepo extends JpaRepository<EvaluationCommitee,Long>{
	
	Optional<EvaluationCommitee> findBySubmitedUserIDAndSupplierIDAndTenderID(String loggedInUserID, Long supplierID, Long tenderID);

	
	@Query("SELECT td FROM TenderDetails td WHERE td.status = 15")
	List<TenderDetails> getTenderDetailsForMarks();
	
	@Query("SELECT m FROM User m WHERE m.companyCode= 119")
	List<User> getTenderEvaluationForCommittee();

	Boolean existsBySubmitedUserIDAndTenderID(String committeemember, Long tenderID);
	
	Boolean existsBySubmitedUserIDAndTenderIDAndSupplierID(String committeemember, Long tenderID, Long supplierID);
	
	Optional<EvaluationCommitee> findBySubmitedUserIDAndTenderIDAndSupplierID(String loggedCommitteMember, Long tenderID, Long vendorID);
	
	@Query("SELECT COUNT(ec) FROM EvaluationCommitee ec WHERE ec.tenderID = ?1 AND ec.supplierID  =?2 ")
	Long submitedCountForTenderAndVendor(Long tenderID, Long vendorID);


	@Query("SELECT c,td FROM EvaluationCommitee c INNER JOIN TenderDetails td ON td.trnderid = c.tenderID WHERE c.tenderID = ?2 AND (c.submitedUserID LIKE %?1% OR td.bidno LIKE %?1% OR td.tendername LIKE %?1%) GROUP BY  c.submitedUserID") //td.trnderid
	Page<Object[]> getCommitteeView(String parameter, Long tenderID, PageRequest of);

	@Query("SELECT c, td, sc FROM EvaluationCommitee c INNER JOIN TenderDetails td ON td.trnderid = c.tenderID INNER JOIN SubCompany sc ON sc.subCompanyID = c.supplierID WHERE c.submitedUserID = ?2 AND (td.bidno LIKE %?1% OR c.submitedUserID LIKE %?1% OR td.tendername LIKE %?1% OR c.supplierID LIKE %?1% OR c.createdAt LIKE %?1% OR sc.scname LIKE %?1% OR td.tendername LIKE %?1%) ")
	Page<Object[]> getMarkSheetView(String parameter, String userID, PageRequest of); //GROUP BY td.trnderid

	@Query("SELECT evcommi, td FROM EvaluationCommitee evcommi INNER JOIN TenderDetails td ON td.trnderid = evcommi.tenderID WHERE evcommi.tenderID = ?2 AND (td.tendername LIKE %?1% OR evcommi.submitedUserID LIKE %?1%) GROUP BY evcommi.submitedUserID")
	Page<Object[]> getAuthorizeevCommittee(String parameter, Long tendrID, PageRequest of);
	
	
}
