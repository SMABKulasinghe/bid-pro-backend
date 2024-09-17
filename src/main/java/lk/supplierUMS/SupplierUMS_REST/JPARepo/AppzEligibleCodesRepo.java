package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.AppzEligibleCodes;

@Component
public interface AppzEligibleCodesRepo extends JpaRepository<AppzEligibleCodes, Long>{

	List<AppzEligibleCodes> findByTenderNo(Long valueOf);



}
