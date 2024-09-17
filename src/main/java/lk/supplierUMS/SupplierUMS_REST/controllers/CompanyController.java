package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.supplierUMS.SupplierUMS_REST.entity.CompanyDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.service.CompanyDetailsService;

@CrossOrigin
@RestController
@RequestMapping(value = "company/")
public class CompanyController {
	
	@Autowired
	CompanyDetailsService companydetailsservice;
	
	
	/*@PostMapping(value="addcompany/")
	public ResponseEntity<Boolean> addCompany(@RequestBody JSONObject data) {
		boolean res = false;
		try {
			  System.out.println("Controller"+data);
			  res = companydetailsservice.addcompany(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Boolean>(res, HttpStatus.OK);
	}*/
	
	@PostMapping(value = "addcompany")
	public ResponseEntity<String> addCompany(@RequestBody JSONObject companydata) {
		System.out.println("Inside addCompany");
		String id = companydetailsservice.addcompany(companydata);
		String msg = "Company creation failed";
		
		if(id!=null){
			msg = "Success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	@GetMapping(value="partner/{company}/supplier/{supplier}")
	public ResponseEntity<Boolean> addCompany(@PathVariable long company, @PathVariable long supplier) {
		boolean res = false;
		try {
//			 
			JSONObject ob = companydetailsservice.addPartnership(company,supplier);
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Boolean>(res, HttpStatus.OK);
	}
	
	@GetMapping(value="all")
	public ResponseEntity<JSONObject> getAllcompany(HttpServletRequest request) {
		JSONObject data = null;
		System.out.println("Controller");
		
		try {
			
			data = companydetailsservice.getAllCompany(request);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		
		
	}
	
	@GetMapping(value="company")
	public ResponseEntity<JSONObject> getSupplier(@PathParam(value = "search") String search,@PathParam(value = "page") Long page) {
		System.out.println("Why this");
		try {
			
			JSONObject suppliers = companydetailsservice.getCompanyList(search, page);
			
			return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="{id}")
	public ResponseEntity<SubCompany> getSupplierById(@PathVariable long id) {
		try {
			
			SubCompany suppliers = companydetailsservice.getCompanybyId(id);
			return new ResponseEntity<SubCompany>(suppliers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="auth")
	public ResponseEntity<JSONObject> pendingAuthCompanies(HttpServletRequest req) {
		try {
			
			JSONObject companies = companydetailsservice.pendingAuthCompanies(req);
			return new ResponseEntity<JSONObject>(companies, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="{id}/auth/{status}")
	public ResponseEntity<SubCompany> authCompanyById(@PathVariable long id,@PathVariable String status) {
		try {
			
			SubCompany suppliers = companydetailsservice.authCompanybyId(id,status);
			return new ResponseEntity<SubCompany>(suppliers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
