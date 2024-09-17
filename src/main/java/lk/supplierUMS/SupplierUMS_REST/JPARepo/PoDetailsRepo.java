package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.EPoHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.PoDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;

@Component
public interface PoDetailsRepo extends JpaRepository<PoDetails, Long> {

	@Query("SELECT po FROM PoDetails po WHERE po.mTenderId = ?1")
	Optional<PoDetails> findByTenderId(Long valueOf);

}
