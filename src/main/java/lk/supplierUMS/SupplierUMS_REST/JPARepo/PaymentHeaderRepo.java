package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.supplierUMS.SupplierUMS_REST.entity.PaymentHeader;

public interface PaymentHeaderRepo extends JpaRepository<PaymentHeader, Long> {

}
