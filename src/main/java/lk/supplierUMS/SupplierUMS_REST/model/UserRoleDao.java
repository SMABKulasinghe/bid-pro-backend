package lk.supplierUMS.SupplierUMS_REST.model;

import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;

@Component
public interface UserRoleDao {
	public long addUserRole(UserRole userRole);

}
