package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.supplierUMS.SupplierUMS_REST.entity.AdditionalFileTenderForSupplier;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarks;
import lk.supplierUMS.SupplierUMS_REST.entity.RfpEvaluationMarks;

public interface AdditionalFileTenderForSupplierRepo extends JpaRepository<AdditionalFileTenderForSupplier, Long>{

	Boolean existsByTenderId(Long id);

	

}
