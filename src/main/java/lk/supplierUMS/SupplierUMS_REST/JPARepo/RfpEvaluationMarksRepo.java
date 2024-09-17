package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarks;
import lk.supplierUMS.SupplierUMS_REST.entity.RfpEvaluationMarks;

public interface RfpEvaluationMarksRepo extends JpaRepository<RfpEvaluationMarks, Long>{

	Optional<RfpEvaluationMarks> findByRfpDetailResIdAndTenderIdAndSupplierIdAndEvaluatorUserId(Long ids, Long tenderId,
			Long supplerId, String userId);

	List<RfpEvaluationMarks> findByTenderIdAndMarksGivenByUserIdAndSupplierId(long tenderId, String evaluatorId,
			Long supplierId);

	List<RfpEvaluationMarks> findByRfpDId(Long rfpDId);

	List<RfpEvaluationMarks> findByRfpDIdAndTenderIdAndMarksGivenByUserId(Long rfpDId, long tenderId,
			String submitedUserID);

	List<RfpEvaluationMarks> findByRfpDIdAndTenderId(Long rfpDId, long tenderId);


}
