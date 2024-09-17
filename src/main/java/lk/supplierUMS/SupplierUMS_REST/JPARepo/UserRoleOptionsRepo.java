package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;

@Component
public interface UserRoleOptionsRepo extends JpaRepository<UserRoleOptions, Integer> {

}
