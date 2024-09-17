package lk.supplierUMS.SupplierUMS_REST.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;
import lk.supplierUMS.SupplierUMS_REST.model.UserRoleOptionsDao;
import lk.supplierUMS.SupplierUMS_REST.service.UserRoleOptionsService;

@Service
public class UserRoleOptionsServiceImpl implements UserRoleOptionsService {
	
	@Autowired
	UserRoleOptionsDao userRoleOptionsDao;

	@Override
	public int addUserRoleOptions(UserRoleOptions userRoleOptions) {
		// TODO Auto-generated method stub
		userRoleOptionsDao.addUserRoleOptions(userRoleOptions);
		return userRoleOptions.getUserRoleOptionsId();
	}

}
