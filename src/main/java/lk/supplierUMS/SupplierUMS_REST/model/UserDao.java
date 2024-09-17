package lk.supplierUMS.SupplierUMS_REST.model;

import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.User;

@Component
public interface UserDao {
	public String addUser(User user);

}
