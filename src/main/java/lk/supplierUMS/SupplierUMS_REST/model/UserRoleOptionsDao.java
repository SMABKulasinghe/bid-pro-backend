package lk.supplierUMS.SupplierUMS_REST.model;

import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;

@Component
public interface UserRoleOptionsDao {
	public int addUserRoleOptions(UserRoleOptions userRoleOptions);

}
