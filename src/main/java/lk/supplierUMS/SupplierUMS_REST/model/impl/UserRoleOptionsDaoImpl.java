package lk.supplierUMS.SupplierUMS_REST.model.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRoleOptionsRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;
import lk.supplierUMS.SupplierUMS_REST.model.UserRoleOptionsDao;

@Component
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class UserRoleOptionsDaoImpl implements UserRoleOptionsDao {
	
	@Autowired
	UserRoleOptionsRepo userRoleOptionsRepo;

	@Override
	public int addUserRoleOptions(UserRoleOptions userRoleOptions) {
		// TODO Auto-generated method stub
		UserRoleOptions userRoleOption = null;
		try {
			userRoleOption = userRoleOptionsRepo.save(userRoleOptions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userRoleOption.getUserRoleOptionsId();
	}

}
