package lk.supplierUMS.SupplierUMS_REST.controllers;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lk.supplierUMS.SupplierUMS_REST.service.DashBoardService;

@CrossOrigin
@RestController
@RequestMapping(value ="dashboard")
public class DashBoardController {
	@Autowired
	DashBoardService dashboardServie;

	@GetMapping("supplierContract")
	public ResponseEntity<JSONObject> getLastContractSup(HttpServletRequest request){
		try {
//			JSONObject data = contractService.getCompanyContracts(id, request);
//			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
			
			JSONObject data=dashboardServie.getSupliersCompanyContracts(request);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping("supplierBoxval")
	public ResponseEntity<JSONObject> getSummeryBoxValue_supplier(HttpServletRequest request){
		try {
			
			JSONObject data=dashboardServie.getSummaryBoxValues_sup(request);
			System.out.println("before send--->"+data);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("companyBoxval")
	public ResponseEntity<JSONObject> getSummeryBoxValue_company(HttpServletRequest request){
		try {
			
			JSONObject data=dashboardServie.getSummaryBoxValues_com(request);
			System.out.println("before send--->"+data);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("summerchart_sup")
	public ResponseEntity<JSONObject> getSummaryChartData_suppiler(HttpServletRequest request){
		try {
			JSONObject data_obj=dashboardServie.getSummaryChartData_sup(request);
			return new ResponseEntity<JSONObject>(data_obj, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

@GetMapping("supplierNextInvo")	
	public ResponseEntity<JSONObject> getSupNextInvo(HttpServletRequest request){
		try {
			JSONObject data_obj=dashboardServie.getSupplierNextInvoices(request);
			return new ResponseEntity<JSONObject>(data_obj, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


@GetMapping("companyContract")
public ResponseEntity<JSONObject> getLastContractCom(HttpServletRequest request){
	try {
//		JSONObject data = contractService.getCompanyContracts(id, request);
//		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		System.out.println("inside company contarct controller");
		JSONObject data=dashboardServie.getCompanysSuplierContracts(request);
		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
//		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}

@GetMapping("suppliersCompnylist")
public ResponseEntity<JSONObject> getSuppliersCompanyList(HttpServletRequest request){
	try {
		
		JSONObject data=dashboardServie.getSuppliersCompanyes(request);
		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
	
	} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@GetMapping("compnysSupplierlist")
public ResponseEntity<JSONObject> getCompanysSupplierList(HttpServletRequest request){
	try {
		
		JSONObject data=dashboardServie.getCompanyesSuppliers(request);
		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
	
	} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@GetMapping("companyPendingInvoice")
public ResponseEntity<JSONObject> getCompanyPendingInvoice(HttpServletRequest request){
//	getCompanyespendingInvoice
try {
		
		JSONObject data=dashboardServie.getCompanyespendingInvoice(request);
		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
	
	} catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

	@GetMapping("superuser/companies/all")
	public ResponseEntity<JSONObject> getAllCompanies(HttpServletRequest request){
	try {
			
			JSONObject data=dashboardServie.getAllCompanies(request,true);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("superuser/supplier/all")
	public ResponseEntity<JSONObject> getAllSuppliers(HttpServletRequest request){
	try {
			
			JSONObject data=dashboardServie.getAllCompanies(request,false);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("superuser/{type}/count")
	public ResponseEntity<JSONObject> getcounts(@PathVariable String type){
	try {
			
			JSONObject data=dashboardServie.getAllCounts(type);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
