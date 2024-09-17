package lk.supplierUMS.SupplierUMS_REST.model.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRoleOptionsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRoleRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;
import lk.supplierUMS.SupplierUMS_REST.model.UserRoleDao;

@Component
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class UserRoleDaoImpl implements UserRoleDao{
	
	@Autowired
	UserRoleRepo userRollRepo;
	
	@Autowired
	UserRoleOptionsRepo uro;

	@Override
	public long addUserRole(UserRole userRole) {
		// TODO Auto-generated method stub
		long userRollId = 0;
		UserRole userR1 = null;
				
		try {
			System.out.println(userRole.getOptions().size());
			
			ArrayList<UserRoleOptions> data = new ArrayList<UserRoleOptions>();
			
			for(UserRoleOptions userOptions : userRole.getOptions()){
				data.add(uro.findById(userOptions.getUserRoleOptionsId()).get());
			}
			userRole.getOptions().clear();
			
//--------------- add to list one by one------------------//
//			for (int i = 0; i < data.size(); i++) {
//				userRole.getOptions().add(data.get(i));
//			}
			
			long noOfValues = userRollRepo.count();

			userRole.getOptions().addAll(data);
			userR1 = userRollRepo.save(userRole);
			userRollId = userR1.getUserRoleID();
			
//----------- add one ------------------------------------//			
			
			/*userRole.getOptions().clear();
			//UserRoleOptions uro = new UserRoleOptions();
			//uro.setUserRoleOptionsName("Test1");
			userRole.getOptions().add(uro1.get());
			UserRole userR = userRollRepo.save(userRole);
			userRollId = userR.getUserRoleID();*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userRollId;
	}

}
