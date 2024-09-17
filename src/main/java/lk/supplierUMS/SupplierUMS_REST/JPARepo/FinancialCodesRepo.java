package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;


@Component
public interface FinancialCodesRepo extends JpaRepository<FinancialCodes,Long>{

	

	//Boolean existsByCodeAndStatus(String enteredValue, Long Status);

	//Boolean existsByDescription(String enteredValue);

	//Boolean existsByDescriptionAndStatus(String enteredValue, Long valueOf);

	Boolean existsByCodeAndStatus(String enteredValue, Long valueOf);

	Boolean existsByDescriptionAndStatus(String enteredValue, Long valueOf);


}
