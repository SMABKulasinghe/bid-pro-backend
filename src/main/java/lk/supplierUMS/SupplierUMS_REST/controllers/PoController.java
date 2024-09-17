package lk.supplierUMS.SupplierUMS_REST.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

import lk.supplierUMS.SupplierUMS_REST.service.PoService;

@CrossOrigin
@RestController
@RequestMapping(value="po/")
public class PoController {
	
	@Autowired
	PoService poService;
	
	@Value("${po.data.path}")
	String poDataPath;
	
	@Value("${po.email.doc.path}")
	String poEmailDocPath;
	
	@PostMapping(value="addtopo")
	public ResponseEntity<String> addPo (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String id = poService.addPo(data,request);
		//String msg = "Cantract creation failed";
		
		if(id != null) {
			id = "Success";
			return new ResponseEntity<String>(id,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(id,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "addreissuepo")
	public ResponseEntity<JSONObject> addReIssuePo(@RequestBody JSONObject data, HttpServletRequest request) {
		try {
		JSONObject id = poService.addReIssuePo(data, request);

		return new ResponseEntity<JSONObject>(id, HttpStatus.OK);
	}catch(Exception e)
	{
		e.printStackTrace();
		return null;
	}
	
}

	
	@GetMapping("poauthorization")
	public ResponseEntity<JSONObject> getLastContractCom(HttpServletRequest request){
		try {

			System.out.println("inside company contarct controller");
			JSONObject data=poService.getPoAuthorization(request);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping("po-details-for-table")
	public ResponseEntity<JSONObject> getPoDetailsForTable(HttpServletRequest request){
		try {

			System.out.println("inside company contarct controller");
			JSONObject data=poService.getPoDetailsForTable(request);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping("po-details-for-supllier-table")
	public ResponseEntity<JSONObject> getPoDetailsForSupplierTable(HttpServletRequest request){
		try {

			System.out.println("inside company contarct controller");
			JSONObject data=poService.getPoDetailsForSupplierTable(request);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping(value="{id}/{approve}")
	public ResponseEntity<JSONObject> PoAuthorization(@PathVariable long id,@PathVariable String approve, HttpServletRequest request) {
		try {
			
			JSONObject poauth = poService.PoAuthorization(id,approve);
			
			return new ResponseEntity<JSONObject>(poauth, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("podetails")
	public ResponseEntity<JSONObject> getPoDetails(HttpServletRequest request){
		try {

			/* System.out.println("inside company contarct controller"); */
			JSONObject data=poService.getPoDetails(request);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping(value = "get-tender-ids")
	public ResponseEntity<JSONObject> getTenderDetailsIds() {
		try {
			JSONObject td = poService.getTenderIds();
			return new ResponseEntity<JSONObject>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	@GetMapping(value = "get-tenders")
	public ResponseEntity<JSONObject> getTendersDetails() {
		try {
			JSONObject td = poService.getTenders();
			return new ResponseEntity<JSONObject>(td, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	@GetMapping(value = "get-tender-details-for-reissuepo/{id}")
	public ResponseEntity <JSONObject> getTenderDetailsForReIssuePo(HttpServletRequest request,@PathVariable Long id) {
		System.out.println("selected TenderID "+id);
		  try {
		  
			  JSONObject data = poService.getTenderDetailsForReIssuePo(request,id);
			  return new  ResponseEntity<JSONObject>(data, HttpStatus.OK);
			  
		  } catch (Exception e)
		   { 
			  e.printStackTrace(); 
			  return null; 
			}
	}
	
	@GetMapping(value = "get-tender-details-for-po/{id}")
	public ResponseEntity <JSONObject> getTenderDetailsForPo(@PathVariable Long id) {
		System.out.println(id);
		  try {
		  
			  JSONObject res = poService.getTenderDetailsForPo(id);
			  return new  ResponseEntity<JSONObject>(res, HttpStatus.OK);
			  
		  } catch (Exception e)
		   { 
			  e.printStackTrace(); 
			  return null; 
			}
	}
	
	
	/*@PutMapping(value="send-email-and-auth/{tenderSubmissionId}/{companyCode}")
	public ResponseEntity<JSONObject> sendEmailAndAuth(@PathVariable Long tenderSubmissionId, @PathVariable Long companyCode) {
		
		try {
			
			JSONObject res = poService.sendEmailAndAuth(tenderSubmissionId,companyCode);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}*/
	
	@PutMapping(value="send-email-and-auth")
	public ResponseEntity<JSONObject> sendEmailAndAuth(@RequestBody JSONObject data) {
		
		try {
			
			JSONObject res = poService.sendEmailAndAuth(data);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value="po-reject")
	public ResponseEntity<JSONObject> poReject(@RequestBody JSONObject data) {
		
		try {
			
			JSONObject res = poService.poReject(data);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value = "download-batch-file/{fileName}/{extention}")
	public  ResponseEntity<Resource> downloadBidedTenderFiles(HttpServletRequest request,@PathVariable String fileName,@PathVariable String extention) {
		
		//System.out.println("File id------------>"+id);
		System.out.println("File name------------>"+fileName);
	//	  File iFolder = new File(fileTenderFormatInPath); if (!iFolder.exists()) {
	//	  iFolder.mkdirs(); }
		 
		String fileNameWithExtension = fileName+extention;
		System.out.println("fileName With Extension------------>"+fileName);
	//	String filename = fileTenderFormatFileName;
		//File file = new File(poDataPath+id+"/"+fileName+".pdf");
		File file = new File(poDataPath+"/"+fileName+"."+extention);
		System.out.println("Link------------------>"+poDataPath+"/"+fileName+".pdf");
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
	
	@GetMapping(value = "download-email-attachment/{fileName}")
	public  ResponseEntity<Resource> downloadEmailAttachment(HttpServletRequest request, @PathVariable String fileName) {
		
		//System.out.println("File id------------>"+id);
		System.out.println("File name------------>"+fileName);
	//	  File iFolder = new File(fileTenderFormatInPath); if (!iFolder.exists()) {
	//	  iFolder.mkdirs(); }
		 
		String fileNameWithExtension = fileName+".pdf";
		System.out.println("fileName With Extension------------>"+fileName);
	//	String filename = fileTenderFormatFileName;
		File file = new File(poEmailDocPath+fileName+".pdf");
		//System.out.println("Link------------------>"+tenderSubmitDataPath+id+"/"+fileName+".pdf");
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
	
	@PostMapping(value="add-term-and-conditions")
	public ResponseEntity<String> addTermsAndConditions (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String id = poService.addTermsAndConditions(data,request);
		
		if(id != null) {
			id = "Success";
			return new ResponseEntity<String>(id,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(id,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "get-terms-and-con")
	public ResponseEntity<JSONObject> getTermsAndConditions() {
		try {
			JSONObject trmNCon = poService.getTermsAndConditions();
			return new ResponseEntity<JSONObject>(trmNCon, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	@PostMapping(value="add-term-and-conditions-to-trans-tbl")
	public ResponseEntity<String> addTermsAndConditionsToTransTable (@RequestBody JSONObject data, HttpServletRequest request) {
		
		String id = poService.addTermsAndConditionsToTransTable(data,request);
		
		if(id != null) {
			id = "Success";
			return new ResponseEntity<String>(id,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(id,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("terms-and-conditions-table/{tenderId}")
	public ResponseEntity<JSONObject> getTermsAndConditionsForTable(HttpServletRequest request,@PathVariable Long tenderId){
		try {
			
			JSONObject data=poService.getTermsAndConditionsForTable(request,tenderId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="terms-and-conditions-status/{id}/{approve}")
	public ResponseEntity<JSONObject> termsAndConditionsStatusTransTbl(@PathVariable long id,@PathVariable String approve, HttpServletRequest request){
				try {
					
					JSONObject termsAndCon = poService.termsAndConditionsStatusTransTbl(id,approve);
					
					return new ResponseEntity<JSONObject>(termsAndCon, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
				}
		
	}
	
	@GetMapping("view-terms-and-conditions")
	public ResponseEntity<JSONObject> viewTermsAndConditions(HttpServletRequest request){
		try {
			
			JSONObject data=poService.viewTermsAndConditions(request);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="view-terms-and-conditions-status/{id}/{approve}")
	public ResponseEntity<JSONObject> termsAndConditionsStatusTbl(@PathVariable long id,@PathVariable String approve, HttpServletRequest request){
				try {
					
					JSONObject termsAndCon = poService.termsAndConditionsStatusTbl(id,approve);
					
					return new ResponseEntity<JSONObject>(termsAndCon, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
				}
		
	}
	
	@GetMapping(value="/terms-con/isexists/by/{termsAndCon}")
	public JSONObject getWetherTermsConExists(@PathVariable String termsAndCon) {
		 
		JSONObject js = new JSONObject();
		Boolean isExists = null;
		isExists = poService.getWetherTermsConExists(termsAndCon);
		js.put("idtype", "termsCon");
		js.put("msg", isExists);
		
		return js;
		
	}
	
	@GetMapping(value="/terms-con/isexists/by/{tenderId}/{termsCon}")
	public JSONObject getWetherTermsConTenderExists(@PathVariable Long tenderId, @PathVariable Long termsCon) {
		 
		JSONObject js = new JSONObject();
		Boolean isExists = null;
		isExists = poService.getWetherTermsConTenderExists(tenderId,termsCon);
		js.put("idtype", "termsConTure");
		js.put("msg", isExists);
		
		return js;
		
	}
	
	@PutMapping(value="edit-terms-and-conditions")
	public ResponseEntity<JSONObject> editTermsAndConditions(@RequestBody JSONObject data) {
		
		try {
			
			JSONObject res = poService.editTermsAndConditions(data);
			  return new ResponseEntity<JSONObject>(res,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(false,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value = "get-terms-and-condions-for-issue-po-page/{tenderId}")
	public ResponseEntity<JSONObject> getTermsAndConditionsForIssuePoPage(@PathVariable Long tenderId) {
		try {
			JSONObject data = poService.getTermsAndConditionsForIssuePoPage(tenderId);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	@GetMapping("/get-terms-and-con-for-view/{id}")
	public ResponseEntity<JSONObject>getTermsAndConView(@PathVariable long id){
		try {
			
			JSONObject data = poService.getTermsAndConView(id);
			return new ResponseEntity<JSONObject>(data, HttpStatus.OK);
			
		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
