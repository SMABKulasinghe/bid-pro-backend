package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialValuesPerTender;


@Component
public interface FinancialValuesPerTenderRepo extends JpaRepository<FinancialValuesPerTender,Long>{

	FinancialCodes save(FinancialCodes code);

	List<FinancialValuesPerTender> findByTenderIdAndSupplierId(Long tenderId, Long subCompId);

	void deleteByTenderIdAndSupplierId(Long tenderId, Long subCompId);


}

