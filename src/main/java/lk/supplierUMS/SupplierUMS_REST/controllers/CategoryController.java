package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategoryProducts;
import lk.supplierUMS.SupplierUMS_REST.service.CategoryService;

	

@CrossOrigin
@RestController
@RequestMapping(value = "/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<EligibleCategory>> getCategory() {
		try {
			System.out.println("getcontractlist");
			List<EligibleCategory> category = categoryService.getCategory();
			
			return new ResponseEntity<List<EligibleCategory>>(category, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/getCategoryForRegistration")
	public ResponseEntity<List<EligibleCategory>> getCategoryForRegistration() {
		try {
			System.out.println("getcontractlist");
			List<EligibleCategory> category = categoryService.getCategoryForRegistration();
			
			return new ResponseEntity<List<EligibleCategory>>(category, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}/subcategory")
	public ResponseEntity<List<EligibleSubCategory>> getSubCategory(@PathVariable long id) {
		try {
			System.out.println("getcontractlist");
			List<EligibleSubCategory> subCategory = categoryService.getSubCategory(id);
			
			return new ResponseEntity<List<EligibleSubCategory>>(subCategory, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/{id}/subcategory/{subId}/product")
	public ResponseEntity<List<EligibleSubCategoryProducts>> getCategoryProducts(@PathVariable long id, @PathVariable long subId) {
		try {
			System.out.println("getcontractlist");
			List<EligibleSubCategoryProducts> subCategory = categoryService.getCategoryProducts(id, subId);
			
			return new ResponseEntity<List<EligibleSubCategoryProducts>>(subCategory, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
