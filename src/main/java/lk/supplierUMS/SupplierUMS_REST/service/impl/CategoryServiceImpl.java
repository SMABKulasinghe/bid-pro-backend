package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.EligibleCategoryRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EligibleSubCategoryProductsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EligibleSubCategoryRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategoryProducts;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.service.CategoryService;

@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	EligibleCategoryRepo eligibleCategoryRepo;
	
	@Autowired
	EligibleSubCategoryRepo eligibleSubCategoryRepo;
	
	@Autowired
	EligibleSubCategoryProductsRepo eligibleSubCategoryProductsRepo;
	
	@Override
	public List<EligibleCategory> getCategory() {
		try {
			
			List<EligibleCategory> data = eligibleCategoryRepo.findAll();
			
			return data;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<EligibleSubCategory> getSubCategory(long id) {
		try {
			
			List<EligibleSubCategory> data = eligibleSubCategoryRepo.findByEligibleCategortID(id);
			
			return data;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<EligibleSubCategoryProducts> getCategoryProducts(long id, long subId) {
		try {
			
			List<EligibleSubCategoryProducts> data = eligibleSubCategoryProductsRepo.findByEligibleSubcatId(subId);
			
			return data;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public List<EligibleCategory> getCategoryForRegistration() {
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			List<EligibleCategory> data = eligibleCategoryRepo.getCategoryForRegistration(Long.valueOf(user.getCompanyCode()));
			
			return data;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
