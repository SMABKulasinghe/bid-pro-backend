package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import lk.supplierUMS.SupplierUMS_REST.entity.ProcurementRequest;
import lk.supplierUMS.SupplierUMS_REST.entity.RFP;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.service.RfpService;

@CrossOrigin
@RestController
@RequestMapping(value="rfp")
public class RfpController {
	@Autowired
	RfpService rfpService;
	
	@Value("${rfpDownloads.doc.path}")
	String rfpDownloadRfp;
	
	@Value("${rfpSubmit.doc.path}")
	String rfpSubmitDataPath;
	
	@PostMapping(value="/addrfp")
	public ResponseEntity<String> addRfp (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = rfpService.addRfp(data,request);
		
		if(msg != null && msg != "Error") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//end addRFP
	
	@GetMapping(value="/isexists/by/{type}/{enteredValue}")
	public JSONObject getWetherRfpExists(@PathVariable String enteredValue, @PathVariable String type) {
		
		JSONObject js = new JSONObject();
		Boolean isExists = null;
		System.out.println("Type----" + type);
		switch(type){
		case "rfpNo":
			isExists = rfpService.getWetherRfpExists(enteredValue);
			
			js.put("idtype", "rfpNo");
			js.put("msg", isExists);
			break;
		
		case "tenderId":
			isExists = rfpService.getWetherTenderExists(enteredValue);
			js.put("idtype", "tenderId");
			js.put("msg", isExists);
			break;
		}
		System.out.println("Return----" + js);
		return js;
		
	}
	
	@GetMapping("/rfpresponses")
	public ResponseEntity<JSONObject> getRfpResponses(HttpServletRequest request){
		try {
			
		JSONObject data = rfpService.getRfpResponse(request);
		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		
		}catch(Exception e){
			
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/rfpdetail/{id}")
	public ResponseEntity<JSONObject>getRfpDetails(@PathVariable long id){
		try {
			
			JSONObject data = rfpService.getRfpDetails(id);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
			
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="{id}/{approve}")
	public ResponseEntity<JSONObject> RfpAuthorization(@PathVariable long id,@PathVariable String approve, HttpServletRequest request){
				try {
					
					JSONObject rfpauth = rfpService.RfpAuthorization(id,approve);
					
					return new ResponseEntity<JSONObject>(rfpauth, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
				}
		
	}
	
	@GetMapping("/rfp-update-data")
	public ResponseEntity<JSONObject> getRfpUpdate(HttpServletRequest request){
		try {
			
		JSONObject data = rfpService.getRfpUpdate(request);
		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		
		}catch(Exception e){
			
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value="/update-submit")
	public ResponseEntity<String> updateSubmitRfp (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String msg = rfpService.updateSubmitRfp(data,request);
		
		if(msg != null && msg == "Success") {
			System.out.println("IN");
			return new ResponseEntity<String>(msg,HttpStatus.OK);
		}else {
			System.out.println("OUT");
			return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/rfp-view-data")
	public ResponseEntity<JSONObject> getRfpView(HttpServletRequest request){
		try {
			
		JSONObject data = rfpService.getRfpView(request);
		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		
		}catch(Exception e){
			
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/rfp-view-data-for-company")
	public ResponseEntity<JSONObject> getRfpViewForComputer(HttpServletRequest request){
		try {
			
		JSONObject data = rfpService.getRfpViewForComputer(request);
		return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		
		}catch(Exception e){
			
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/download-rfp/{id}")
	public ResponseEntity<JSONObject>getDownloadRfpData(@PathVariable long id){
		try {
			
			JSONObject data = rfpService.getDownloadRfpData(id);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
			
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value = "/download-rfp-csv/{id}")
	public  ResponseEntity<Resource> downloadTenFileFormats(HttpServletRequest request, @PathVariable String id) {
		
		File iFolder = new File(rfpDownloadRfp);
		if (!iFolder.exists()) {
			iFolder.mkdirs();
		}
		
		// filename is InvFileFormat.zip in src folder 
		String filename = "RfpDetails.csv";
		File file = new File(rfpDownloadRfp+"/"+id);
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
		 
			
			/*
			 * try { Files.deleteIfExists( Paths.get(rfpDownloadRfp+"/"+id)); } catch
			 * (NoSuchFileException e) { System.out.println(
			 * "No such file/directory exists"); } catch (DirectoryNotEmptyException e) {
			 * System.out.println("Directory is not empty."); } catch (IOException e) {
			 * System.out.println("Invalid permissions."); }
			 */
		
	 
	        //System.out.println("Deletion successful.");
	    
		  
			    
		    return ResponseEntity.ok()
		            .headers(h)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType("application/csv"))
		            .body(resource);
		   
	}
	
	
	  @GetMapping(value = "/delete-rfp-csv/{id}") 
	  public ResponseEntity<Resource>deleteTenFileFormats(HttpServletRequest request, @PathVariable String id) {
			
		  File file = new File(rfpDownloadRfp+"/"+id);
		  if (file.exists()) { 
			  file.delete(); 
			  System.out.println(file.getName() + " is deleted!"); 
		 
		  } else { 
			  System.out.println("Delete operation is failed.");
		  }
			
		  
			/*
			 * File file = new File(rfpDownloadRfp+"/"+id); try {
			 * System.out.println("File created - " + file.createNewFile());
			 * Thread.sleep(10000); Path filePath = Paths.get(id); Files.delete(filePath);
			 * System.out.println("File deleted"); } catch (IOException |
			 * InterruptedException ioException) { ioException.printStackTrace(); }
			 */
		  return null;
	  }
	 
	  @GetMapping
		public ResponseEntity<List<RFP>> getFileName() {
			try {
				System.out.println("getcontractlist");
				List<RFP> rfp_filename = rfpService.getFileName();
				
				return new ResponseEntity<List<RFP>>(rfp_filename, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	  
	  
	  @GetMapping(value="/categoryID/{categoryID}/{subCatId}")
	  public ResponseEntity<List<RFP>> getRFPForCategory(@PathVariable Long categoryID, @PathVariable Long subCatId) {
		  try {
			  System.out.println("getRFPForCategory");
			  List<RFP> rfp_filename = rfpService.getRFPForCategory(categoryID,subCatId);
			  
			  return new ResponseEntity<List<RFP>>(rfp_filename, HttpStatus.OK);
		  } catch (Exception e) {
			  e.printStackTrace();
			  return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	  }
	  
	  @PutMapping(value="add-rfp-comment")
		public ResponseEntity<JSONObject> addRfpComment(@RequestBody JSONObject data) {
			
			try {
				
				JSONObject res = rfpService.addRfpComment(data);
				  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
	  
	  
	  @PostMapping(value="procurementrequest")
		public ResponseEntity<JSONObject> procurementRequestDetails(@RequestBody JSONObject data) {			
			try {
				JSONObject res = rfpService.procurementRequestDetails(data);
				  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		}
	  
	  @GetMapping(value="tender-details-for-rfp/{rfpId}")
		public ResponseEntity<JSONObject> getAllTenderDetails(HttpServletRequest request, @PathVariable Long rfpId) {
			JSONObject data = null;
			System.out.println("Controller parameter "+rfpId);
			try {
				
				data = rfpService.getAllTenderDetails(request, rfpId);
				System.out.println("Controller return "+data);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
			
			
		}
	  
	  @GetMapping("/viewprocurement/{id}")
		public ResponseEntity<JSONObject>viewProcurementDetails(@PathVariable long id){
			try {				
				JSONObject data = rfpService.viewProcurementDetails(id);
				return new ResponseEntity<JSONObject>(data, HttpStatus.OK);				
			}catch(Exception e){
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}			
		}
	  
	  @GetMapping(value = "rfp-file-view/{fileName}")
		public  ResponseEntity<Resource> financialFileUpload(HttpServletRequest request, @PathVariable Long fileName) {
			
			String fileNameWithExtension = fileName+".zip";
			System.out.println("fileName With Extension------------>"+fileName);
			File file = new File(rfpSubmitDataPath+fileName+".zip");
			
			InputStreamResource resource = null;
			 
			
			 try {
				 resource = new InputStreamResource(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
			 HttpHeaders h = new HttpHeaders();
			 h.add("Content-Disposition", "filename=" + fileName+".zip");
			 
			    return ResponseEntity.ok()
			            .headers(h)
			            .contentLength(file.length())
			            .contentType(MediaType.parseMediaType("application/zip"))
			            .body(resource);
		
		}
	  
	  @GetMapping(value = "rfp-id-for-revised-rfp-submittion")
		public ResponseEntity<List<RFP>> rfpIdForRevisedRfpSubmittion() {
			try {
				List<RFP> rfp = rfpService.rfpIdForRevisedRfpSubmittion();
				return new ResponseEntity<List<RFP>>(rfp, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	  
	  @GetMapping(value = "get-rfp-details-for-edit/{id}")
		public ResponseEntity <JSONObject> getRfpDetailsForEdit(@PathVariable Long id) {
			System.out.println(id);
			  try {
			  
				  JSONObject res = rfpService.getRfpDetailsForEdit(id);
				  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
				  
			  } catch (Exception e)
			   { 
				  e.printStackTrace(); 
				  return null; 
				}
		}
	  
	  @GetMapping("/rfp-details-for-edit-table/{id}")
		public ResponseEntity<JSONObject> getRfpDetailsForEditTable(HttpServletRequest request,@PathVariable long id){
			try {
				
			JSONObject data = rfpService.getRfpDetailsForEditTable(request,id);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
			
			}catch(Exception e){
				
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	  
	  @GetMapping(value = "tendeid-for-rfp-change-id")
		public ResponseEntity<List<TenderDetails>> getTenderIdForRfpChangeId() {
			try {
				List<TenderDetails> data = rfpService.getTenderIdForRfpChangeId();
				return new ResponseEntity<List<TenderDetails>>(data, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	  
	  @PostMapping(value="/tender-rfp-changed")
		public ResponseEntity<String> tenderRfpChange (@RequestBody JSONObject data, HttpServletRequest request) {
			
			String msg = rfpService.tenderRfpChange(data,request);
			
			if(msg != null && msg != "Error") {
				
				return new ResponseEntity<String>(msg,HttpStatus.OK);
			}else {
				
				return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}//end addRFP
	  
	  
	  @GetMapping(value = "rfp-for-select/{tenderId}")
		public ResponseEntity<JSONObject> getRfpForSelect(@PathVariable long tenderId) {
			try {
				
				JSONObject data = rfpService.getRfpForSelect(tenderId);
				
				return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	  
	  
	  @GetMapping(value = "prID")
		public ResponseEntity<List<ProcurementRequest>> getTenderDetails() {
			try {
				List<ProcurementRequest> td = rfpService.getprID();
				return new ResponseEntity<List<ProcurementRequest>>(td, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	  
	  
	  @PostMapping(value="/addrfpevaluationcommittee")
		public ResponseEntity<JSONObject> addRfpEveCommittee (@RequestBody JSONObject data, HttpServletRequest request) {
			try {
				JSONObject msg = rfpService.addRfpEveCommittee(data,request);
				return new ResponseEntity<JSONObject>(msg, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<JSONObject>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
	  
	  @GetMapping(value = "tenderids-for-rfp-eva-com-creation")
		public ResponseEntity<JSONObject> getTenderForRfpEvaComCreation() {
			try {
				JSONObject td = rfpService.getTenderForRfpEvaComCreation();
				return new ResponseEntity<JSONObject>(td, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	  
	  @GetMapping(value = "/rfp-committeeview/{tendrID}")
		public ResponseEntity <JSONObject> rfpCommitteeView(HttpServletRequest request, @PathVariable Long tendrID) {
			  try {
			  
				  JSONObject res = rfpService.rfpCommitteeView(request, tendrID);
				  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
				  
			  } catch (Exception e)
			   { 
				  e.printStackTrace(); 
				  return null; 
				}
		}
	  
	  @GetMapping(value = "tenderids-for-rfp-eva-making")
		public ResponseEntity<JSONObject> getTenderForRfpEvaMaking() {
			try {
				JSONObject td = rfpService.getTenderForRfpEvaMaking();
				return new ResponseEntity<JSONObject>(td, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	  
	  @GetMapping(value = "/rfp-response-details/{tendrID}/{supplierID}")
		public ResponseEntity <JSONObject> rfpResponseDetails(HttpServletRequest request, @PathVariable Long tendrID,@PathVariable Long supplierID) {
			  try {
			  
				  JSONObject res = rfpService.rfpResponseDetails(request, tendrID,supplierID);
				  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
				  
			  } catch (Exception e)
			   { 
				  e.printStackTrace(); 
				  return null; 
				}
		}
	  
	  @PostMapping(value="/set-rfp-marks")
		public ResponseEntity<String> addRfpMarks (@RequestBody JSONObject data, HttpServletRequest request) {
			
			String msg = rfpService.addRfpMarks(data,request);
			
			if(msg != null && msg != "Error") {
				System.out.println("IN");
				return new ResponseEntity<String>(msg,HttpStatus.OK);
			}else {
				System.out.println("OUT");
				return new ResponseEntity<String>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}//end addRFP
	  
	  @GetMapping(value = "get-rfp-details-for-submit-button/{tender_ID}/{supplier_ID}")
	  	public ResponseEntity <JSONObject> getRfpDetailsForSubmitButton(@PathVariable Long tender_ID, @PathVariable Long supplier_ID) {
			//System.out.println(id);
			  try {
			  
				  JSONObject res = rfpService.getRfpDetailsForSubmitButton(tender_ID,supplier_ID);
				  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
				  
			  } catch (Exception e)
			   { 
				  e.printStackTrace(); 
				  return null; 
				}
		}
	  
	  @GetMapping(value = "get-rfp-evaluated-results/{tenderId}")
		public ResponseEntity<JSONObject> getRfpEvaluationResults(@PathVariable long tenderId) {
			try {
				JSONObject em = rfpService.getRfpEvaluationResults(tenderId);
				return new ResponseEntity<JSONObject>(em, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	  
}//end controller
