package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzTenderSubmissionsRepo;
import lk.supplierUMS.SupplierUMS_REST.service.AppzTenderSubmissionsService;
import lk.supplierUMS.SupplierUMS_REST.service.TenderDetailsService;

import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;

@CrossOrigin
@RestController
@RequestMapping(value ="tender/")
public class TenderController {

	@Autowired
	TenderDetailsService tenderDetailsService;
	
	@Autowired
	AppzTenderSubmissionsService appzTenderSubmissionsService;
	
	@Value("${tenderadditional.doc.path}")
	String tenderAdditinalPath;
	
	@Value("${file.ten.format.path}")
	String fileTenderFormatInPath;
	
	@Value("${tender.financialdoc.path}")
	String financialDocPath;

	@Value("${file.ten.format.name}")
	String fileTenderFormatFileName;
	
	@Value("${tendersubmit.doc.path}")
	String tenderSubmitDataPath;
	
	@Value("${tender.doc.path}")
	String tenderDataPath;
	
	@Value("${financialview.doc.path}")
	String financialViewDocPath;
	
	@Value("${tenderadditionalsupplier.doc.path}")
	String tenderAdditinalSupplierPath;
	
	
	@PostMapping(value = "addtender")
	public ResponseEntity<String> createTender(@RequestBody JSONObject data) {
		String returnMsg = tenderDetailsService.createTender(data);

		if (returnMsg != null) {
			return new ResponseEntity<String>(returnMsg, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(returnMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping(value="tenderview")
	public ResponseEntity<JSONObject> getTenderForView(HttpServletRequest req) {
		try {
			
			JSONObject tenders = tenderDetailsService.getTenderForView(req);
			
			return new ResponseEntity<JSONObject>(tenders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	

	@PutMapping(value="/{tenderId}/confirm")
	public ResponseEntity<JSONObject> confirmTender(@RequestBody JSONObject data, @PathVariable Long tenderId) {
		
		try {
			System.out.println("selectedTenderID-- "+tenderId);
			JSONObject res = appzTenderSubmissionsService.confirmTender(data, tenderId);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
		
		
		@PostMapping(value = "/{selectedTenderID}/response")
		public ResponseEntity<JSONObject> responseTender(@PathVariable Long selectedTenderID, @RequestBody JSONObject data) {
			
		System.out.println("bidno--"+selectedTenderID);
		try {

			JSONObject res = appzTenderSubmissionsService.responseTender(selectedTenderID, data);

			return new ResponseEntity<JSONObject>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

		
		
	/*
	 * System.out.println("Response Check---- "+ enteredValue);
	 * //tenderDetails.setResponse(enteredValue); JSONObject res =
	 * tenderDetailsService.getTenderResponse(enteredValue);
	 * 
	 * //System.out.println("Response availability---- "+ isExists);
	 * 
	 * res.put("idtype", "userId"); // js.put("msg", isExists);
	 * 
	 * return res;
	 * 
	 * }
	 */
	
	
	
	@GetMapping(value = "/downloadtenfileformats")
	public  ResponseEntity<Resource> downloadTenFileFormats(HttpServletRequest request) {
		
		File iFolder = new File(fileTenderFormatInPath);
		if (!iFolder.exists()) {
			iFolder.mkdirs();
		}
		
		// filename is InvFileFormat.zip in src folder 
		String filename = fileTenderFormatFileName;
		File file = new File(fileTenderFormatInPath+"/"+fileTenderFormatFileName);
		InputStreamResource resource = null;
		 
		/*if(filename.equals("InvoiceHeader")){
			System.out.println("Header");
			filename = "InvoiceHeader.csv";
			file = new File("src//InvFileFormat/InvoiceHeader.csv");
		}else if(filename.equals("InvoiceDetails")){
			System.out.println("Details");
			filename = "InvoiceDetails.csv";
			file = new File("src//InvFileFormat/InvoiceDetails.csv");
		}else{
			
		}
		*/
		
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
	
	
	
	@GetMapping("tenderauthorization")
	public ResponseEntity<JSONObject> getTenderAuthorization(HttpServletRequest req) {
		try {

			System.out.println("inside tender controller");
			JSONObject data = tenderDetailsService.getTenderAuthorization(req);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value="add-tender-auth-reason/{approve}")
	public ResponseEntity<JSONObject> addTenderAuthReason(@RequestBody JSONObject data, @PathVariable String approve) {
		
		try {
			JSONObject res = tenderDetailsService.addTenderAuthReason(data, approve);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	@GetMapping("tenderopen")
	public ResponseEntity<JSONObject> getTenderOpen(HttpServletRequest req) {
		try {

			System.out.println("inside tender controller");
			JSONObject data = tenderDetailsService.getTenderOpen(req);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(value = "{id}/{approve}")
	public ResponseEntity<JSONObject> TenderAuthorization(@PathVariable long id, @PathVariable String approve,
			HttpServletRequest req) {
		try {

			JSONObject tenderauth = tenderDetailsService.TenderAuthorization(id, approve);

			return new ResponseEntity<JSONObject>(tenderauth, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping(value = "{id}/open")
	public ResponseEntity<JSONObject> TenderStatusOpen(@PathVariable long id, HttpServletRequest request) {
		try {

			JSONObject tenderopen = tenderDetailsService.TenderStatusOpen(id, request);

			return new ResponseEntity<JSONObject>(tenderopen, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@GetMapping(value = "isexists/by/{type}/{enteredValue}")
	public JSONObject getWetherTenderExists(@PathVariable String enteredValue, @PathVariable String type) {

		System.out.println("TenderNo Check---- " + enteredValue);
		System.out.println("type Check---- " + type);
		JSONObject js = new JSONObject();
		Boolean isExists = null;

		switch (type) {

		case "bidno":
			// System.out.println("bidno Check---- "+ enteredValue);
			isExists = tenderDetailsService.getWetherTenderExistsByBidno(enteredValue);
			System.out.println("bidno " + isExists);

			js.put("idtype", "bidno");
			js.put("msg", isExists);

			break;

		case "cordinator1email":
			System.out.println("Coordinator 1 email Check---- " + enteredValue);
			isExists = tenderDetailsService.getWetherUserExistsByCordinator1email(enteredValue);
			System.out.println("cordinator1email " + isExists);

			js.put("idtype", "cordinator1email");
			js.put("msg", isExists);

			break;
			
		case "cordinator1contactno":
			System.out.println("Coordinator 1 contactno Check---- " + enteredValue);
			isExists = tenderDetailsService.getWetherUserExistsByCordinator1contactno(enteredValue);
			System.out.println("cordinator1contactno " + isExists);

			js.put("idtype", "cordinator1contactno");
			js.put("msg", isExists);

			break;

		case "cordinator2email":
			System.out.println("Coordinator 2 email Check---- " + enteredValue);
			isExists = tenderDetailsService.getWetherUserExistsByCordinator2email(enteredValue);
			System.out.println("cordinator2email " + isExists);

			js.put("idtype", "cordinator2email");
			js.put("msg", isExists);

			break;

		case "cordinator2contactno":
			System.out.println("Coordinator 2 contactno Check---- " + enteredValue);
			isExists = tenderDetailsService.getWetherUserExistsByCordinator2contactno(enteredValue);
			System.out.println("cordinator2contactno " + isExists);

			js.put("idtype", "cordinator2contactno");
			js.put("msg", isExists);

			break;

		default:

		}

		return js;
	}
	
	
	
	// @GetMapping(value = "all")
	// public ResponseEntity<JSONObject> getTenders(HttpServletRequest req) {
		
	@GetMapping(value="{parameter}")
	public ResponseEntity<JSONObject> getAllTender(HttpServletRequest request, @PathVariable String parameter) {
		JSONObject data = null;
		System.out.println("Controller parameter "+parameter);
		
		try {
			
			data = tenderDetailsService.getAllTender(request, parameter);
			System.out.println("Controller return "+data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		
		
	}
	

	@GetMapping(value = "{id}/eligible/vender")
	public ResponseEntity<JSONObject> getEligibleVenders(@PathVariable long id) {
		try {

			JSONObject data = tenderDetailsService.getEligibleVenders(id);

			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("get-tender-details")
	public ResponseEntity<JSONObject> getTenderDetailsForSupplier(HttpServletRequest request){
		try {

			JSONObject data=tenderDetailsService.getTenderDetailsForSupplier(request);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping("get-tender-details-for-supplier-view")
	public ResponseEntity<JSONObject> getTenderDetailsForSupplierView(HttpServletRequest request){
		try {

			JSONObject data=tenderDetailsService.getTenderDetailsForSupplierView(request);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping("get-tender-details-for-tender-details-view")
	public ResponseEntity<JSONObject> getTenderDetailsForDetailsViewAll(HttpServletRequest request){
		try {

			JSONObject data=tenderDetailsService.getTenderDetailsForDetailsViewAll(request);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping("get-eligible-supplier/{tenderID}")
	public ResponseEntity<JSONObject> getEligibleSuppliers(HttpServletRequest request, @PathVariable Long tenderID){
		try {

			JSONObject data=tenderDetailsService.getEligibleSuppliers(request, tenderID);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
			
	@PutMapping(value="/sendnotify")
	public ResponseEntity<JSONObject> EligibleSupplierNotice(@RequestBody JSONObject data, HttpServletRequest request) {
	
		try {
			
			JSONObject suppliers = appzTenderSubmissionsService.EligibleSupplierNotice(data, request);
			
			return new ResponseEntity<JSONObject>(suppliers, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	
	@PutMapping(value="/submit/{filename}")
	public ResponseEntity<String> tendersubmit (@RequestBody JSONObject data, HttpServletRequest request,@PathVariable String filename) {
		
		String msg = tenderDetailsService.tendersubmit(data,request,filename);
		
		if(msg != null && msg == "Success" && msg == "FilesNotEqual") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value="/submit/rfpfile")
	public ResponseEntity<String> tendersubmitRfp (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = tenderDetailsService.tendersubmitRfp(data,request);
		
		if(msg != null && msg == "Success" && msg == "FilesNotEqual") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(value = "updatetender")
	public ResponseEntity<JSONObject> updateSupplier(@RequestBody JSONObject data) {

		try {
			JSONObject res = tenderDetailsService.updateTender(data);
			return new ResponseEntity<JSONObject>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping(value = "get-tender-ids")
	public ResponseEntity<JSONObject> getTenderDetailsIds() {
		try {
			JSONObject td = tenderDetailsService.getTenderIds();
			return new ResponseEntity<JSONObject>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	@GetMapping(value = "get-tender-ids-for-tender-participators")
	public ResponseEntity<JSONObject> getTenderDetailsIdsForTenderParticipators() {
		try {
			JSONObject td = tenderDetailsService.getTenderDetailsIdsForTenderParticipators();
			return new ResponseEntity<JSONObject>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	@GetMapping(value = "get-tender-details-for-view/{id}")
	public ResponseEntity <JSONObject> getTenderDetailsForView(@PathVariable Long id) {
		System.out.println(id);
		  try {
		  
			  JSONObject res = tenderDetailsService.getTenderDetailsForView(id);
			  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
			  
		  } catch (Exception e)
		   { 
			  e.printStackTrace(); 
			  return null; 
			}
	}
	
	@GetMapping(value = "get-tender-details-for-view-tender-participators/{id}")
	public ResponseEntity <JSONObject> getTenderDetailsForViewTenderParticipators(@PathVariable Long id) {
		System.out.println(id);
		  try {
		  
			  JSONObject res = tenderDetailsService.getTenderDetailsForViewTenderParticipators(id);
			  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
			  
		  } catch (Exception e)
		   { 
			  e.printStackTrace(); 
			  return null; 
			}
	}
	
	@GetMapping("get-tender-details-for-view-table/{id}")
	public ResponseEntity<JSONObject> getTenderDetailsForViewTable(HttpServletRequest request,@PathVariable Long id){
		try {

			JSONObject data=tenderDetailsService.getTenderDetailsForViewTable(request,id);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping("get-financial_doc-for-view/{id}")
	public ResponseEntity<JSONObject> getFinancialDocForView(HttpServletRequest request,@PathVariable Long id){
		try {

			JSONObject data=tenderDetailsService.getFinancialDocForView(request,id);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping("get-tender-details-for-view-table-for-participators/{id}")
	public ResponseEntity<JSONObject> getTenderDetailsForViewTableForParticipators(HttpServletRequest request,@PathVariable Long id){
		try {

			JSONObject data=tenderDetailsService.getTenderDetailsForViewTableForParticipators(request,id);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	
	@GetMapping("financial-response/{tenderId}")
	public ResponseEntity<JSONObject> financialResponse(HttpServletRequest request,@PathVariable Long tenderId){
		System.out.println("selected TenderID "+tenderId);
		try {

			JSONObject data=tenderDetailsService.financialResponse(request,tenderId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	
	  @GetMapping("submitted")
			public ResponseEntity<List<TenderDetails>> getTenderForFinancialResponse() {
				try {
					System.out.println("getcontractlist");
					List<TenderDetails> tender_id = tenderDetailsService.getTenderForFinancialResponse();
					
					return new ResponseEntity<List<TenderDetails>>(tender_id, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
	  

	  @GetMapping("code-details")
			public ResponseEntity<List<TenderDetails>> getFinancialCodeDetailsForTender() {
				try {
					System.out.println("getcontractlist");
					List<TenderDetails> tender_id = tenderDetailsService.getFinancialCodeDetailsForTender();
					
					return new ResponseEntity<List<TenderDetails>>(tender_id, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			
	  
/*		@GetMapping("financial-code-details/{tenderId}")
		public ResponseEntity<JSONObject> getFinancialCodeDetails(HttpServletRequest request,@PathVariable Long tenderId){
			System.out.println("selected TenderID "+tenderId);
			try {

				JSONObject data=tenderDetailsService.getFinancialCodeDetails(request,tenderId);
				return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
				
		}
*/		
	  
	/*  @PostMapping(value = "response")
		public ResponseEntity<String> tenderFinancialResponse(@RequestBody JSONObject data) {
			String id = tenderDetailsService.tenderFinancialResponse(data);
			String msg = "Failed";

			if (id != null) {
				msg = "Success";
				return new ResponseEntity<String>(msg, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		*/
	  
	  @PostMapping(value = "values")
		public ResponseEntity<String> financialValuesPerTender(@RequestBody JSONObject data) {
			String msg = tenderDetailsService.financialValuesPerTender(data);

			if (msg != null) {
				return new ResponseEntity<String>(msg, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	  
	  
	@GetMapping(value = "trnderid")
	public ResponseEntity<List<TenderDetails>> getSubmittedTender() {
		try {
			List<TenderDetails> td = appzTenderSubmissionsService.getSubmittedTender();
			return new ResponseEntity<List<TenderDetails>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	
	@GetMapping(value = "/tenderId/{tenderID}")
	public ResponseEntity<List<SubCompany>> getSupplierDetaiilsForTender(@PathVariable Long tenderID) {
		try {
			System.out.println("selected VendorID "+tenderID);
			List<SubCompany> td = appzTenderSubmissionsService.getSupplierDetaiilsForTender(tenderID);
			return new ResponseEntity<List<SubCompany>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@GetMapping(value = "validated/tenderId/{tenderID}/venderID/{venderID}")
	public ResponseEntity<JSONObject> getValidatedSupplierDetaiilsForTender(HttpServletRequest request, @PathVariable Long tenderID, @PathVariable Long venderID) {
		try {
			System.out.println("selected VendorID "+tenderID);
			JSONObject td = appzTenderSubmissionsService.getValidatedSupplierDetaiilsForTender(request, tenderID, venderID);
			return new ResponseEntity<JSONObject>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@GetMapping(value = "/marksheet/tenderID/{tenderID}/venderID/{venderID}")
	public ResponseEntity<JSONObject> getMarksheetForSupllierAndTender(HttpServletRequest request, @PathVariable Long tenderID, @PathVariable Long venderID) {
		try {
			System.out.println("selected VendorID "+tenderID);
			JSONObject td = appzTenderSubmissionsService.getMarksheetForSupllierAndTender(request, tenderID, venderID);
			return new ResponseEntity<JSONObject>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@GetMapping(value = "download-bidded-tender-files/{id}/{fileName}")
	public  ResponseEntity<Resource> downloadBidedTenderFiles(HttpServletRequest request, @PathVariable String id,@PathVariable String fileName) {
		
		System.out.println("File id------------>"+id);
		System.out.println("File name------------>"+fileName);
	//	  File iFolder = new File(fileTenderFormatInPath); if (!iFolder.exists()) {
	//	  iFolder.mkdirs(); }
		 
		String fileNameWithExtension = fileName+".pdf";
		System.out.println("fileName With Extension------------>"+fileName);
	//	String filename = fileTenderFormatFileName;
		File file = new File(tenderSubmitDataPath+"/"+id+"/"+fileName+".pdf");
		System.out.println("Link------------------>"+tenderSubmitDataPath+id+"/"+fileName+".pdf");
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
	
	@GetMapping(value = "download-bidded-tender-rfp-files/{id}/{fileName}")
	public  ResponseEntity<Resource> downloadBidedTenderRfpFiles(HttpServletRequest request, @PathVariable String id,@PathVariable String fileName) {
		
		System.out.println("File id------------>"+id);
		System.out.println("File name------------>"+fileName);
	//	  File iFolder = new File(fileTenderFormatInPath); if (!iFolder.exists()) {
	//	  iFolder.mkdirs(); }
		 
		String fileNameWithExtension = fileName+".csv";
		System.out.println("fileName With Extension------------>"+fileName);
	//	String filename = fileTenderFormatFileName;
		File file = new File(tenderSubmitDataPath+"/"+id+"/"+fileName+".csv");
		System.out.println("Link------------------>"+tenderSubmitDataPath+id+"/"+fileName+".csv");
		InputStreamResource resource = null;
		 
		
		 try {
			 resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 HttpHeaders h = new HttpHeaders();
		 h.add("Content-Disposition", "filename=" + fileName+".csv");
		 
		    return ResponseEntity.ok()
		            .headers(h)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("text/csv"))
		            .body(resource);
		//return null;
	}
	
	
	
	@GetMapping(value = "download-rfp-tender-files/{id}/{fileName}")
	public  ResponseEntity<Resource> downloadRfpTenderFiles(HttpServletRequest request, @PathVariable String id,@PathVariable String fileName) {
		
		System.out.println("File id------------>"+id);
		System.out.println("File name------------>"+fileName);
	//	  File iFolder = new File(fileTenderFormatInPath); if (!iFolder.exists()) {
	//	  iFolder.mkdirs(); }
		 
		String fileNameWithExtension = fileName+".pdf";
		System.out.println("fileName With Extension------------>"+fileName);
	//	String filename = fileTenderFormatFileName;
		File file = new File(tenderDataPath+"/"+id+"/"+fileName);
		System.out.println("Link------------------>"+tenderDataPath+id+"/"+fileName);
		InputStreamResource resource = null;
		 
		
		 try {
			 resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 HttpHeaders h = new HttpHeaders();
		 h.add("Content-Disposition", "filename=" + fileName);
		 
		    return ResponseEntity.ok()
		            .headers(h)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/pdf"))
		            .body(resource);
		//return null;
	}
	
	@PostMapping(value="/financial_code")
	public ResponseEntity<String> financial_code (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = tenderDetailsService.financial_code(data,request);
		
		if(msg != null && msg == "Success") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("get-financial-detail-for-cappex/{tenderId}/{supplierId}")
	public ResponseEntity<JSONObject> getFinancialDetailsForCappex(HttpServletRequest request,@PathVariable Long tenderId, @PathVariable Long supplierId){
		try {

			JSONObject data=tenderDetailsService.getFinancialDetailsForCappex(request,tenderId,supplierId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping("get-financial-detail-for-oppex/{tenderId}/{supplierId}")
	public ResponseEntity<JSONObject> getFinancialDetailsForOppex(HttpServletRequest request,@PathVariable Long tenderId, @PathVariable Long supplierId){
		try {

			JSONObject data=tenderDetailsService.getFinancialDetailsForOppex(request,tenderId,supplierId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping("get-financial-detail-for-cappex-opexx/{tenderId}/{supplierId}")
	public ResponseEntity<JSONObject> getFinancialDetailsForCappexOppex(HttpServletRequest request,@PathVariable Long tenderId, @PathVariable Long supplierId){
		System.out.println("selected TenderID "+tenderId);
		try {

			JSONObject data=tenderDetailsService.getFinancialDetailsForCappexOppex(request,tenderId,supplierId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@PostMapping(value="/test-emails")
	public ResponseEntity<String> testEmails (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = tenderDetailsService.testEmails(data,request);
		
		if(msg != null && msg == "Success") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(value = "tender-auth-file-upload/{id}/{fileName}")
	public  ResponseEntity<Resource> tenderAuthFileUpload(HttpServletRequest request, @PathVariable String id,@PathVariable String fileName) {
		
		System.out.println("File id------------>"+id);
		System.out.println("File name------------>"+fileName);
	//	  File iFolder = new File(fileTenderFormatInPath); if (!iFolder.exists()) {
	//	  iFolder.mkdirs(); }
		 
		String fileNameWithExtension = fileName+".pdf";
		System.out.println("fileName With Extension------------>"+fileName);
	//	String filename = fileTenderFormatFileName;
		//File file = new File(tenderSubmitDataPath+"/"+id+"/"+fileName+".pdf");
		File file = new File(tenderDataPath+"/"+id+"/"+fileName+".pdf");
		System.out.println("Link------------------>"+tenderDataPath+id+"/"+fileName+".pdf");
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
	
	@GetMapping(value = "financial-response-file-upload-view/{id}/{fileName}")
	public  ResponseEntity<Resource> financialFileUpload(HttpServletRequest request, @PathVariable String id,@PathVariable String fileName) {
		
		System.out.println("File id------------>"+id);
		System.out.println("File name------------>"+fileName);
		 
		String fileNameWithExtension = fileName+".pdf";
		System.out.println("fileName With Extension------------>"+fileName);
		File file = new File(financialDocPath+"/"+id+"/"+fileName+".pdf");
		System.out.println("Link------------------>"+financialDocPath+id+"/"+fileName+".pdf");
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
	
	}
	
	@PostMapping(value="/financial-code-submit")
	public ResponseEntity<String> addRfp (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = tenderDetailsService.financialCodeCreation(data,request);
		
		if(msg != null && msg != "Error") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/financial-code-view")
	public ResponseEntity<JSONObject> financialCodeView(HttpServletRequest request){
		try {
			
		JSONObject data = tenderDetailsService.financialCodeView(request);
		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		
		}catch(Exception e){
			
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="financial-code-status/{id}/{approve}")
	public ResponseEntity<JSONObject> financialCodeAuth(@PathVariable long id,@PathVariable String approve, HttpServletRequest request){
				try {
					
					JSONObject financialCode = tenderDetailsService.financialCodeAuth(id,approve);
					
					return new ResponseEntity<JSONObject>(financialCode, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
				}
		
	}
	
	@PutMapping(value="edit-financial-code")
	public ResponseEntity<JSONObject> editFinancialCode(@RequestBody JSONObject data) {
		
		try {
			
			JSONObject res = tenderDetailsService.editFinancialCode(data);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="/isexists-financial/by/{type}/{enteredValue}")
	public JSONObject getWetherFinancialExists(@PathVariable String enteredValue, @PathVariable String type) {
		
		JSONObject js = new JSONObject();
		Boolean isExists = null;
		System.out.println("Type----" + type);
		switch(type){
		case "financialCode":
			isExists = tenderDetailsService.getWetherFinancialExists(enteredValue);
			
			js.put("idtype", "financialCode");
			js.put("msg", isExists);
			break;
		
		case "description":
			isExists = tenderDetailsService.getWetherFinancialDesExists(enteredValue);
			
			js.put("idtype", "description");
			js.put("msg", isExists);
			break;
		
		case "tenderidForAdditionalFile":
			isExists = tenderDetailsService.getAdditionalFileExists(enteredValue);
			
			js.put("idtype", "tenderidForAdditionalFile");
			js.put("msg", isExists);
			break;
		/*
		 * case "tenderId": isExists = rfpService.getWetherTenderExists(enteredValue);
		 * js.put("idtype", "tenderId"); js.put("msg", isExists); break;
		 */
		}
		System.out.println("Return----" + js);
		return js;
		
	}
	
	@GetMapping(value = "tendeid-for-financial-per-tender")
	public ResponseEntity<List<TenderDetails>> getTenderForFinancialPerTender() {
		try {
			List<TenderDetails> td = tenderDetailsService.getTenderForFinancialPerTender();
			return new ResponseEntity<List<TenderDetails>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "financialid-for-financial-per-tender")
	public ResponseEntity<List<FinancialCodes>> getFinancialForFinancialPerTender() {
		try {
			List<FinancialCodes> td = tenderDetailsService.getFinancialForFinancialPerTender();
			return new ResponseEntity<List<FinancialCodes>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping(value="/financial-code-per-tender-submit")
	public ResponseEntity<String> financialCodePerTenderSubmit (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = tenderDetailsService.financialCodePerTenderSubmit(data,request);
		
		if(msg != null && msg != "Error") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/financial-per-tender-view/{tenderId}")
	public ResponseEntity<JSONObject> financialPerTenderView(HttpServletRequest request,@PathVariable Long tenderId){
		try {
			
		JSONObject data = tenderDetailsService.financialPerTenderView(request,tenderId);
		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		
		}catch(Exception e){
			
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value="/eligible-supplier-confirm/{tenderId}")
	public ResponseEntity<JSONObject> confirmEligibleSuppliers(@RequestBody JSONObject data, @PathVariable Long tenderId){
		System.out.println("selected TenderID "+tenderId);
		
		try {
			
			JSONObject res = appzTenderSubmissionsService.confirmEligibleSuppliers(data, tenderId);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping(value="/aditional-files-for-tender-params")
	public ResponseEntity<String> aditionalFileForTenderParams (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = tenderDetailsService.aditionalFileForTenderParams(data,request);
		
		if(msg != null && msg != "Error") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/aditional-details-for-edit/{tenderId}")
	public ResponseEntity<JSONObject> aditionalDetailsForEditTble(HttpServletRequest request,@PathVariable Long tenderId){
		try {

			JSONObject data=tenderDetailsService.aditionalDetailsForEditTble(request,tenderId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping(value="aditional-files-status/{id}/{approve}")
	public ResponseEntity<JSONObject> aditionalFilesStatus(@PathVariable long id,@PathVariable String approve, HttpServletRequest request){
				try {
					
					JSONObject adFiles = tenderDetailsService.aditionalFilesStatus(id,approve);
					
					return new ResponseEntity<JSONObject>(adFiles, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
				}
		
	}
	
	@PutMapping(value="edit-additional-file-name")
	public ResponseEntity<JSONObject> editAdditionalFileName(@RequestBody JSONObject data) {
		
		try {
			
			JSONObject res = tenderDetailsService.editAdditionalFileName(data);
			return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//TenderExtend
	@GetMapping(value = "tenderID")
	public ResponseEntity<List<TenderDetails>> getTenderExtendForTenderDetails() {
		try {
			List<TenderDetails> td = tenderDetailsService.getTenderExtendForTenderDetails();
			return new ResponseEntity<List<TenderDetails>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@PutMapping(value="tenderextend")
	public ResponseEntity<JSONObject> updateTenderExtend(@RequestBody JSONObject data) {
		try {
			JSONObject res = tenderDetailsService.updateTenderExtend(data);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="/aditional-files/isexists/by/{tenderId}/{fileName}")
	public JSONObject getWetherAddtionalFilesExists(@PathVariable Long tenderId,@PathVariable String fileName) {
		 
		JSONObject js = new JSONObject();
		Boolean isExists = null;
		isExists = tenderDetailsService.getWetherAddtionalFilesExists(tenderId,fileName);
		js.put("idtype", "addtionalFile");
		js.put("msg", isExists);
		
		return js;
		
	}
	
	@GetMapping(value = "get-additional-files-to-page/{tenderId}")
	public ResponseEntity<JSONObject> getAdditionalFilesToPage(@PathVariable Long tenderId) {
		try {
			JSONObject data = tenderDetailsService.getAdditionalFilesToPage(tenderId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@GetMapping(value = "get-tender-Ids")
	public ResponseEntity<List<TenderDetails>> getViewFinancialDetails() {
		try {
			List<TenderDetails> td = tenderDetailsService.getViewFinancialDetails();
			return new ResponseEntity<List<TenderDetails>>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping(value="/additional-file-upload/{id}")
	public ResponseEntity<String> aditionalFileUpload (@RequestBody JSONObject data, HttpServletRequest request,@PathVariable Long id) {
		
		String msg = tenderDetailsService.aditionalFileUpload(data,request,id);
		
		if(msg != null && msg == "Success") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/aditional-details-for-view-tble/{tenderId}")
	public ResponseEntity<JSONObject> aditionalDetailsForViewTble(HttpServletRequest request,@PathVariable Long tenderId){
		try {

			JSONObject data=tenderDetailsService.aditionalDetailsForViewTble(request,tenderId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping(value = "get-financial-view/tendrID/{tendrID}")
	public ResponseEntity <JSONObject> getTenderDetailsForFinancialView(HttpServletRequest request, @PathVariable Long tendrID) {
		  try {
			  JSONObject res = tenderDetailsService.getTenderDetailsForFinancialView(request, tendrID);
			  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
		  } catch (Exception e)
		   { 
			  e.printStackTrace(); 
			  return null; 
			}
	}
	
	
	@GetMapping("get-view-financial-detail-for-cappex/{tenderId}/{supplierId}")
	public ResponseEntity<JSONObject> getViewFinancialDetailsForCappex(HttpServletRequest request,@PathVariable Long tenderId, @PathVariable Long supplierId){
		try {
			JSONObject data=tenderDetailsService.getViewFinancialDetailsForCappex(request,tenderId,supplierId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping(value = "/aditional-files-view/{tenderId}/{id}")
	public  ResponseEntity<Resource> additionalFileViews(HttpServletRequest request, @PathVariable Long tenderId,@PathVariable Long id) {
		
		
		File file = new File(tenderAdditinalPath+tenderId+"-"+id+".pdf");
		
		InputStreamResource resource = null;
		 try {
			 resource = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		 HttpHeaders h = new HttpHeaders();
		  h.add("Content-Disposition", "filename=" + tenderId+"-"+id+".pdf");
		return ResponseEntity.ok()
		        .headers(h)
		        .contentLength(file.length())
		        .contentType(MediaType.parseMediaType("application/pdf"))
		        .body(resource);
		
	}
	
	
	@GetMapping("get-view-financial-detail-for-oppex/{tenderId}/{supplierId}")
	public ResponseEntity<JSONObject> getViewFinancialDetailsForOppex(HttpServletRequest request,@PathVariable Long tenderId, @PathVariable Long supplierId){
		try {
			JSONObject data=tenderDetailsService.getViewFinancialDetailsForOppex(request,tenderId,supplierId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("get-view-financial-detail-for-cappex-opexx/{tenderId}/{supplierId}")
	public ResponseEntity<JSONObject> getViewFinancialDetailsForCappexOppex(HttpServletRequest request,@PathVariable Long tenderId, @PathVariable Long supplierId){
		System.out.println("selected TenderID "+tenderId);
		try {
			JSONObject data=tenderDetailsService.getViewFinancialDetailsForCappexOppex(request,tenderId,supplierId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(value = "download-financial-response-file/{id}/{fileName}")
	public  ResponseEntity<Resource> downloadFinancialFileView(HttpServletRequest request, @PathVariable String id,@PathVariable String fileName) {
		
		System.out.println("File id------------>"+id);
		System.out.println("File name------------>"+fileName);
		 
		String fileNameWithExtension = fileName+".pdf";
		System.out.println("fileName With Extension------------>"+fileName);
		File file = new File(tenderSubmitDataPath+"/"+id+"/"+fileName+".pdf");
		System.out.println("Link------------------>"+tenderSubmitDataPath+id+"/"+fileName+".pdf");
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
	
	@PutMapping(value="lock-addiotnal-files")
	public ResponseEntity<JSONObject> lockAdditionalFileUpload(@RequestBody JSONObject data) {
		
		try {
			
			JSONObject res = tenderDetailsService.lockAdditionalFileUpload(data);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value = "resubmit-financial-details-allow-option")
	public ResponseEntity<JSONObject> getResubmitFinancialDetailsAllowOption() {
		try {
			JSONObject td = tenderDetailsService.getResubmitFinancialDetailsAllowOption();
			return new ResponseEntity<JSONObject>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	@GetMapping(value = "resubmit-financial-details-allow-option-supplier/{tenderId}")
	public ResponseEntity<JSONObject> getResubmitFinancialDetailsAllowOptionSupplier(@PathVariable Long tenderId) {
		try {
			JSONObject td = tenderDetailsService.getResubmitFinancialDetailsAllowOptionSupplier(tenderId);
			return new ResponseEntity<JSONObject>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	@PostMapping(value="/financial-resubmit-creation")
	public ResponseEntity<String> financialResubmitCreation (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = tenderDetailsService.financialResubmitCreation(data,request);
		
		if(msg != null && msg == "Success") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "sub-category/{catId}")
	public ResponseEntity<List<EligibleSubCategory>> getSubCat(@PathVariable Long catId) {
		try {
			List<EligibleSubCategory> subCat = tenderDetailsService.getSubCat(catId);
			return new ResponseEntity<List<EligibleSubCategory>>(subCat, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	 @GetMapping(value = "closed-tenders")
	public ResponseEntity<JSONObject> closedTenders() {
		try {
			JSONObject td = tenderDetailsService.closedTenders();
			return new ResponseEntity<JSONObject>(td, HttpStatus.OK);
		} catch (Exception e) {
				e.printStackTrace();
			return null;
		}
	}
	 
	 @PostMapping(value="/additional-file-upload-invitation-for-supplier")
	public ResponseEntity<String> additionalFileUploadInvitationForSupplier (@RequestBody JSONObject data, HttpServletRequest request) {
			
		String msg = tenderDetailsService.additionalFileUploadInvitationForSupplier(data,request);
			
		if(msg != null && msg != "Error") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//end addRFP
	 
	@GetMapping("/supplier-additinal-file-upload")
	public ResponseEntity<JSONObject> getSupplierAdditinalFileUpload(HttpServletRequest request){
		 try {
				
			 JSONObject data = tenderDetailsService.getSupplierAdditinalFileUpload(request);
			 return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
			
		}catch(Exception e){
				
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 }
	 
	 @PostMapping(value="/additinal-file-upload")
	public ResponseEntity<String> additinalFileUpload(@RequestBody JSONObject data, HttpServletRequest request) {
			
		String msg = tenderDetailsService.additinalFileUpload(data,request);
			
		if(msg != null && msg != "Error") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 
	 @GetMapping("/supplier-additinal-file-download/{tenderId}")
	public ResponseEntity<JSONObject> getSupplierAdditinalFileDownload(HttpServletRequest request,@PathVariable Long tenderId){
		try {
					
			JSONObject data = tenderDetailsService.getSupplierAdditinalFileDownload(request,tenderId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
				
		}catch(Exception e){
					
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 
	 @GetMapping(value = "supplier-additinal-file-download-to-pc/{fileName}")
	 public  ResponseEntity<Resource> additionalFileUpload(HttpServletRequest request, @PathVariable Long fileName) {
	 	
	 	try {
	 		
	 		String stringFileName = String.valueOf("additionalFile"+fileName);
	 		String fileNameWithExtension = stringFileName+".zip";
	 		
	 		System.out.println("fileName With Extension------------>"+stringFileName);
	 		File file = new File(tenderAdditinalSupplierPath+stringFileName+".zip");
	 		
	 		InputStreamResource resource = null;
	 		 
	 		
	 		 try {
	 			 resource = new InputStreamResource(new FileInputStream(file));
	 		} catch (FileNotFoundException e) {
	 			e.printStackTrace();
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 		}
	 		 
	 		 HttpHeaders h = new HttpHeaders();
	 		 h.add("Content-Disposition", "filename=" + stringFileName+".zip");
	 		 
	 		    return ResponseEntity.ok()
	 		            .headers(h)
	 		            .contentLength(file.length())
	 		            .contentType(MediaType.parseMediaType("application/zip"))
	 		            .body(resource);
	 		
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		return null;
	 	}
	 	

	 }

	
	/*@GetMapping(value = "example/{catId}")
	public BimsaraExample getBimsara(@PathVariable Long catId) {
		try {
			return new BimsaraExample();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	class BimsaraExample{
		String name = "Bimsara";
		String ID = "3256";
		public String getName() {
			return name;
		}
		public String getID() {
			return ID;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setID(String iD) {
			ID = iD;
		}
		
	}
	
	BimsaraExample BimsaraExample = new BimsaraExample();*/
	
}