package lk.supplierUMS.SupplierUMS_REST.model.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.model.UserDao;

@Component
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class UserDaoImpl implements UserDao {

	@Autowired
	UserRepo UserRepo;

	@Override
	public String addUser(User user) {
		String id = "";
		try {
			System.out.println("User--> "+user.getLocationID()+" "+user.getDeptID());
			User us = UserRepo.save(user);
			id = us.getUserid();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

}
