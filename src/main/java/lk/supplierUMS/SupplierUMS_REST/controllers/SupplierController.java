package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.service.SupplierService;

@CrossOrigin
@RestController
@RequestMapping(value = "supplier/")
public class SupplierController {
 
	@Autowired
	SupplierService supplierService;
	
	@Value("${supplierregistration.doc.path}")
	String supplierRegistrationDataPath;
	
	@Value("${file.sup.format.path}")
	String fileSupplierFormatInPath;
	
	@Value("${file.sup.format.name}")
	String fileSupplierFormatFileName;
	
	
	@PostMapping(value="addsupplier/{param}")
	public ResponseEntity<JSONObject> addSupplier(@RequestBody JSONObject data, @PathVariable String param) {
		
		try {
			JSONObject res = supplierService.addSupplier(data, param);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value="updatesupplier")
	public ResponseEntity<JSONObject> updateSupplier(@RequestBody JSONObject data) {
		
		try {
			JSONObject res = supplierService.updateSupplier(data);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	//PDF Uploading
	@GetMapping(value = "download-additional-supplier-files/{id}/{fileName}")
	public  ResponseEntity<Resource> downloadAdditionalSupplierFiles(HttpServletRequest request, @PathVariable String id,@PathVariable String fileName) {
		
		System.out.println("File id------------>"+id);
		System.out.println("File name------------>"+fileName);
	//	  File iFolder = new File(fileTenderFormatInPath); if (!iFolder.exists()) {
	//	  iFolder.mkdirs(); }
		 
		String fileNameWithExtension = fileName+".pdf";
		System.out.println("fileName With Extension------------>"+fileName);
	//	String filename = fileTenderFormatFileName;
		File file = new File(supplierRegistrationDataPath+"/"+id+"/"+fileName+".pdf");
		System.out.println("Link------------------>"+supplierRegistrationDataPath+id+"/"+fileName+".pdf");
		InputStreamResource resource = null;
		 
		
		 try {
			 resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 HttpHeaders h = new HttpHeaders();
		 h.add("Content-Disposition", "filename=" + fileName+".pdf");
		 
		    return ResponseEntity.ok()
		            .headers(h)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/pdf"))
		            .body(resource);
		//return null;
	}
	
	
	@PostMapping(value="addproductObject/{param}")
	public ResponseEntity<JSONObject> addProductObject(@RequestBody JSONObject data, @PathVariable Long param) {
		
		try {
			JSONObject res = supplierService.addProductObject(data, param);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	
	
	
	@PostMapping(value="supplierselfreg/{param}")
	public ResponseEntity<JSONObject> supplierSelfReg(@RequestBody JSONObject data, @PathVariable String param) {
		
		try {
			Long res = supplierService.supplierSelfReg(data, param);
			
			JSONObject suppliers = supplierService.SupplierApprove(res,"approve");
			
			return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
			
			//return new ResponseEntity<Long>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="supplier")
	public ResponseEntity<JSONObject> getSupplier(@PathParam(value = "search") String search,@PathParam(value = "page") Long page) {
		try {
			
			JSONObject suppliers = supplierService.grtSupplierList(search, page);
			
			return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="getsuppliersforpayment")
	public ResponseEntity<JSONObject> getSuppliersForPayment(@PathParam(value = "search") String search,@PathParam(value = "page") Long page) {
		try {
			
			JSONObject suppliers = supplierService.getSuppliersForPayment(search, page);
			
			return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="supplier/{id}")
	public ResponseEntity<JSONObject> getSupplierbyId(@PathVariable long id) {
		try {
			System.out.println(id);
			JSONObject supplierData = supplierService.grtSupplierById(id);
			
			return new ResponseEntity<JSONObject>(supplierData, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="subcompany/{id}")
	public ResponseEntity<JSONObject> getSubCompanybyId(@PathVariable long id) {
		try {
			System.out.println(id);
			JSONObject supplierData = supplierService.getSubCompanyById(id);
			
			return new ResponseEntity<JSONObject>(supplierData, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.NO_CONTENT);
		}
		/*System.out.println("FIND-------- "+id);
		return null;*/
	}
	
	@GetMapping(value="all")
	public ResponseEntity<JSONObject> getSupplierAll(HttpServletRequest req) {
		try {
			
			JSONObject suppliers = supplierService.grtSupplierList(req);
			
			return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="partners")
	public ResponseEntity<JSONObject> getPartnerSupplier(@PathParam(value = "search") String search,@PathParam(value = "page") Long page) {
		try {
			
			JSONObject suppliers = supplierService.grtPartnerSupplierList(search, page);
			
			return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="auth")
	public ResponseEntity<JSONObject> getSupplierForAuth(HttpServletRequest req) {
		try {
			
			JSONObject suppliers = supplierService.getSupplierUnAutherized(req);
			
			return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	

	
	@GetMapping("category/{supplierID}") 
	public ResponseEntity<JSONObject> categorySuppliers(HttpServletRequest req, @PathVariable Long supplierID){
		try {

			JSONObject data=supplierService.categorySuppliers(req, supplierID);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}

	
	
	
	
	@GetMapping(value="authcompany")
	public ResponseEntity<JSONObject> getCompaniesForAuth(HttpServletRequest req) {
		try {
			
			JSONObject suppliers = supplierService.getCompanyForAutherization(req);
			
			return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
/*	  @GetMapping(value="{id}/{approve}") public ResponseEntity<JSONObject>
	  SupplierApprove(@PathVariable long id,@PathVariable String approve) { try {
	  
	  JSONObject suppliers = supplierService.SupplierApprove(id,approve);
	  
	  return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK); } catch
	  (Exception e) { e.printStackTrace(); return new
	  ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR); }
	  
	  }*/
	 
	
	@GetMapping(value="{id}/supplierApprove/{approve}")
	public ResponseEntity<JSONObject> supplierApprove(@PathVariable long id,@PathVariable String approve) {
		try {
			
			JSONObject suppliers = supplierService.supplierApprove(id,approve);
			
			return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping(value="getSupplierDetails")
	public ResponseEntity<JSONObject> getSupplierDetails() {
		try {
			
			JSONObject suppliers = supplierService.getSupplierDetails();
			
			return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	@PutMapping(value="add-supplier-comment/{parameter}")
	public ResponseEntity<JSONObject> addSupplierComment(@RequestBody JSONObject data, @PathVariable String parameter) {
		
		try {
			JSONObject res = supplierService.addSupplierComment(data, parameter);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
		
	@PostMapping(value="/createcategory")
	public ResponseEntity<String> createcategory (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = supplierService.createcategory(data,request);
		if(msg != null && msg == "Success" || msg != null && msg == "Already created" ) {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/category-view")
	public ResponseEntity<JSONObject> categoryView(HttpServletRequest request){
		
	try {
		 JSONObject data = supplierService.categoryView(request);
		   return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
	}catch(Exception e){
		 e.printStackTrace();
		 return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(value="/category-status/{id}/{approve}")
	public ResponseEntity<JSONObject> categoryAuth(@PathVariable long id,@PathVariable String approve, HttpServletRequest request){
		try {
			JSONObject financialCode = supplierService.categoryAuth(id,approve);
			return new ResponseEntity<JSONObject>(financialCode, HttpStatus.OK);
		} catch (Exception e) {
		  e.printStackTrace();
		  return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping(value="edit-category-view")
	public ResponseEntity<JSONObject> editCategoryView(@RequestBody JSONObject data) {		
		try {			
			JSONObject res = supplierService.editCategoryView(data);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	
	@GetMapping(value="/isexists-category/by/{type}/{enteredValue}")
	public JSONObject getWetherCategoryExists(@PathVariable String enteredValue, @PathVariable String type) {
		
		JSONObject js = new JSONObject();
		Boolean isExists = null;
		System.out.println("Type----" + type);
		switch(type){
		case "categoryname":
			isExists = supplierService.getWetherCategoryExists(enteredValue);			
			js.put("idtype", "categoryname");
			js.put("msg", isExists);
			break;
		
		case "categorydescription":
			isExists = supplierService.getWetherCategoryDesExists(enteredValue);			
			js.put("idtype", "categorydescription");
			js.put("msg", isExists);
			break;
			
		case "categorycode":
			isExists = supplierService.getWetherCategoryCodeExists(enteredValue);			
			js.put("idtype", "categorycode");
			js.put("msg", isExists);
			break;
			
		case "categoryfee":
			isExists = supplierService.getWetherCategoryFeeExists(enteredValue);			
			js.put("idtype", "categoryfee");
			js.put("msg", isExists);
			break;	
		}
		System.out.println("Return----" + js);
		return js;		
	}
	
	
	@GetMapping(value="viewsuppliers")
	public ResponseEntity<JSONObject> getSupplierForView(HttpServletRequest req) {
		try {			
			JSONObject suppliers = supplierService.getSupplierForView(req);			
			return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	
	@GetMapping("viewcategory/{supplierID}") 
	public ResponseEntity<JSONObject> viewCategorySuppliers(HttpServletRequest req, @PathVariable Long supplierID){
		try {

			JSONObject data=supplierService.viewCategorySuppliers(req, supplierID);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	
	//PDF Uploading
		@GetMapping(value = "download-additional-view-supplier-files/{id}/{fileName}")
		public  ResponseEntity<Resource> downloadAdditionalViewSupplierFiles(HttpServletRequest request, @PathVariable String id,@PathVariable String fileName) {
			
			System.out.println("File id------------>"+id);
			System.out.println("File name------------>"+fileName);
		//	  File iFolder = new File(fileTenderFormatInPath); if (!iFolder.exists()) {
		//	  iFolder.mkdirs(); }
			 
			String fileNameWithExtension = fileName+".pdf";
			System.out.println("fileName With Extension------------>"+fileName);
		//	String filename = fileTenderFormatFileName;
			File file = new File(supplierRegistrationDataPath+"/"+id+"/"+fileName+".pdf");
			System.out.println("Link------------------>"+supplierRegistrationDataPath+id+"/"+fileName+".pdf");
			InputStreamResource resource = null;
			 
			
			 try {
				 resource = new InputStreamResource(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			 HttpHeaders h = new HttpHeaders();
			 h.add("Content-Disposition", "filename=" + fileName+".pdf");
			 
			    return ResponseEntity.ok()
			            .headers(h)
			            .contentLength(file.length())
			            .contentType(MediaType.parseMediaType("application/pdf"))
			            .body(resource);
			//return null;
		}
		
		
		@GetMapping("company-profile/{companyID}")
		public ResponseEntity<JSONObject> companyProfile(HttpServletRequest request,@PathVariable Long companyID){
			try {
				JSONObject data = supplierService.companyProfile(request,companyID);
				return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		}
		
		
		
		//PDF Uploading
		@GetMapping(value = "download-companydetails-view-files/{id}/{fileName}")
		public  ResponseEntity<Resource> downloadCompanyDetailsViewFiles(HttpServletRequest request, @PathVariable String id,@PathVariable String fileName) {
			
			System.out.println("File id------------>"+id);
			System.out.println("File name------------>"+fileName);
		//	  File iFolder = new File(fileTenderFormatInPath); if (!iFolder.exists()) {
		//	  iFolder.mkdirs(); }
			 
			String fileNameWithExtension = fileName+".pdf";
			System.out.println("fileName With Extension------------>"+fileName);
		//	String filename = fileTenderFormatFileName;
			File file = new File(supplierRegistrationDataPath+"/"+id+"/"+fileName+".pdf");
			System.out.println("Link------------------>"+supplierRegistrationDataPath+id+"/"+fileName+".pdf");
			InputStreamResource resource = null;
			 try {
				 resource = new InputStreamResource(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			 HttpHeaders h = new HttpHeaders();
			 h.add("Content-Disposition", "filename=" + fileName+".pdf");
			 
			    return ResponseEntity.ok()
			            .headers(h)
			            .contentLength(file.length())
			            .contentType(MediaType.parseMediaType("application/pdf"))
			            .body(resource);
			//return null;
		}
		
		
		@GetMapping("company-view-category/{companyID}") 
		public ResponseEntity<JSONObject> companyViewCategory(HttpServletRequest req, @PathVariable Long companyID){
			try {
				JSONObject data=supplierService.companyViewCategory(req, companyID);
				return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		}
		
		
		@GetMapping("select-additional-category/{companyID}") 
		public ResponseEntity<JSONObject> selectAdditionalCategory(HttpServletRequest req, @PathVariable Long companyID){
			try {
				JSONObject data=supplierService.selectAdditionalCategory(req, companyID);
				return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		}
		
		
		@PostMapping("addnewcategories") 
		public ResponseEntity<JSONObject> submitNewCategory(HttpServletRequest req, @RequestBody JSONObject data){
			try {
				JSONObject res=supplierService.submitNewCategory(req, data);
				return new ResponseEntity<JSONObject>(res, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		}
		
		
		@GetMapping(value="blocksuppliers")
		public ResponseEntity<JSONObject> getSupplierForBlock(HttpServletRequest req) {
			try {			
				JSONObject suppliers = supplierService.getSupplierForBlock(req);			
				return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
			}		
		}
		
		
		@GetMapping("viewblockcategory/{supplierID}") 
		public ResponseEntity<JSONObject> blockCategorySuppliers(HttpServletRequest req, @PathVariable Long supplierID){
			try {
				JSONObject data=supplierService.blockCategorySuppliers(req, supplierID);
				return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		}
		
		
		@PutMapping(value="block-supplier")
		public ResponseEntity<JSONObject> blockSupplier(@RequestBody JSONObject data) {
			try {
				JSONObject res = supplierService.blockSupplier(data);
				  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		
		@GetMapping(value="view-block-suppliers")
		public ResponseEntity<JSONObject> viewBlockSuppliers(HttpServletRequest req) {
			try {			
				JSONObject suppliers = supplierService.viewBlockSuppliers(req);			
				return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
			}		
		}
		
		
		@GetMapping("view-supplier-block-category/{supplierID}") 
		public ResponseEntity<JSONObject> viewBlockCategorySuppliers(HttpServletRequest req, @PathVariable Long supplierID){
			try {
				JSONObject data=supplierService.viewBlockCategorySuppliers(req, supplierID);
				return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		}
		
		
		@PutMapping(value="unblock-supplier")
		public ResponseEntity<JSONObject> unblockSupplier(@RequestBody JSONObject data) {
			try {
				JSONObject res = supplierService.unblockSupplier(data);
				  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		
		@GetMapping(value = "/downloadallfiles")
		public  ResponseEntity<Resource> downloadAllFiles(HttpServletRequest request) {
			
			File iFolder = new File(fileSupplierFormatInPath);
			if (!iFolder.exists()) {
				iFolder.mkdirs();
			}
			
			// filename is SuppFileFormat.zip in src folder 
			String filename = fileSupplierFormatFileName;
			File file = new File(fileSupplierFormatInPath+"/"+fileSupplierFormatFileName);
			InputStreamResource resource = null;
			
			 try {
				 resource = new InputStreamResource(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 HttpHeaders h = new HttpHeaders();
			 h.add("Content-Disposition", "filename=" + filename);
			 
			    return ResponseEntity.ok()
			            .headers(h)
			            .contentLength(file.length())
			            .contentType(MediaType.parseMediaType("application/zip"))
			            .body(resource);
		}
		
		
		@GetMapping(value = "isexists/by/{type}/{enteredValue}")
		public JSONObject getWetherTenderExists(@PathVariable String enteredValue, @PathVariable String type) {
			JSONObject js = new JSONObject();
			Boolean isExists = null;
			switch (type) {

			case "regno":
				System.out.println("regno Check---- " + enteredValue);
				isExists = supplierService.getWetherSupplierExistsByregno(enteredValue);
				System.out.println("regno" + isExists);

				js.put("idtype", "regno");
				js.put("msg", isExists);
				break;
				
			case "contactno":
				System.out.println("contactno Check---- " + enteredValue);
				isExists = supplierService.getWetherSupplierExistsBycontactno(enteredValue);
				System.out.println("contactno" + isExists);

				js.put("idtype", "contactno");
				js.put("msg", isExists);
				break;	
				
			case "conemail":
				System.out.println("conemail Check---- " + enteredValue);
				isExists = supplierService.getWetherSupplierExistsByconemail(enteredValue);
				System.out.println("conemail" + isExists);

				js.put("idtype", "conemail");
				js.put("msg", isExists);
				break;		
				
			default:
			}
			return js;
		}
		
		
		//Supplier Registration File Uploading
		@PutMapping(value="/submit/{filename}")
		public ResponseEntity<String> supplierFileSubmit (@RequestBody JSONObject data, HttpServletRequest request,@PathVariable String filename) {
			
			String msg = supplierService.supplierFileSubmit(data,request,filename);
			
			if(msg == "Success") {
				System.out.println("INN");
				return new ResponseEntity<String>(msg,HttpStatus.OK);
			}else {
				System.out.println("OUTTTTTT");
				return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		


	
}
