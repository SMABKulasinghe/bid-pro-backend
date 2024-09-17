package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarks;

public interface EvaluationMarksRepo extends JpaRepository<EvaluationMarks, Long>{
	
	Optional<List<EvaluationMarks>> findAllByTenderIdAndCreatedUserAndCompanyCode(Long tenderID, String loggedUser, String supplierID);

}
