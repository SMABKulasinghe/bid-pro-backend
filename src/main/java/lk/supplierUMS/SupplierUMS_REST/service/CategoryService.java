package lk.supplierUMS.SupplierUMS_REST.service;

import java.util.List;

import org.json.simple.JSONObject;

import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategoryProducts;

public interface CategoryService {

	List<EligibleCategory> getCategory();

	List<EligibleSubCategory> getSubCategory(long id);

	List<EligibleSubCategoryProducts> getCategoryProducts(long id, long subId);

	List<EligibleCategory> getCategoryForRegistration();


}
