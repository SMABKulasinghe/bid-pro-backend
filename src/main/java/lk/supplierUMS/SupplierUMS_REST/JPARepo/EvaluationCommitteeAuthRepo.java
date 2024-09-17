package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationCommiteeAuth;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarks;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;

@Component
public interface EvaluationCommitteeAuthRepo extends JpaRepository<EvaluationCommiteeAuth, Long> {

	Optional<EvaluationCommiteeAuth> findByTenderID(Long tenderID);
	
}
