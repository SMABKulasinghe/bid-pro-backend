package lk.supplierUMS.SupplierUMS_REST.controllers;

import javax.servlet.http.HttpServletRequest;

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

import lk.supplierUMS.SupplierUMS_REST.service.AssetService;

@CrossOrigin
@RestController
@RequestMapping(value ="asset")
public class AssetController {
	
	@Autowired
	AssetService assetService;
	
	@PostMapping(value = "register")
	public ResponseEntity<String> addAsset(@RequestBody JSONObject data, HttpServletRequest request) {
		System.out.println("addAsset "+data);
		
		String returnMsg = assetService.addAsset(data, request);
		String msg = "addForPayment has been failed";
		
		if(returnMsg.equals("success")){
			msg = "success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	@GetMapping(value="{parameter}")
	public ResponseEntity<JSONObject> getAllAssets(HttpServletRequest request, @PathVariable String parameter) {
		JSONObject data = null;
		System.out.println("Controller parameter "+parameter);
		
		try {
			
			data = assetService.getAllAssets(request, parameter);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		
		
	}
	
	@PostMapping(value = "remove")
	public ResponseEntity<String> removeAsset(@RequestBody JSONObject data) {
		System.out.println("removeAsset "+data);
		
		String returnMsg = assetService.removeAsset(data);
		String msg = "removeAsset has been failed";
		
		if(returnMsg.equals("success")){
			msg = "success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	@GetMapping(value="getasset/{parameter}")
	public ResponseEntity<JSONObject> getAssetsID(HttpServletRequest request, @PathVariable String parameter) {
		JSONObject data = null;
		System.out.println("Controller getAssetsID parameter "+parameter);
		
		try {
			
			
			data = assetService.getAssetsID(request, parameter);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		
		
	}
	
	
	@PostMapping(value = "updateasset")
	public ResponseEntity<String> updateAsset(@RequestBody JSONObject data) {
		System.out.println("updateAsset "+data);
		
		String returnMsg = assetService.updateAsset(data);
		String msg = "updateAsset has been failed";
		
		if(returnMsg.equals("success")){
			msg = "success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	

	@GetMapping(value="getassetcategory/{parameter}")
	public ResponseEntity<JSONObject> getAssetCategory(HttpServletRequest request, @PathVariable String parameter) {
		JSONObject data = null;
		System.out.println("Controller getAssetCategory parameter "+parameter);
		
		try {
			
			data = assetService.getAssetCategory(request, parameter);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		
		
	}
	
	@GetMapping(value="getassetsubcategory/{parameter}")
	public ResponseEntity<JSONObject> getAssetSubCategory(HttpServletRequest request, @PathVariable String parameter) {
		JSONObject data = null;
		System.out.println("Controller getAssetSubCategory parameter "+parameter);
		
		try {
			
			data = assetService.getAssetSubCategory(request, parameter);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		
		
	}
	

	@GetMapping(value="assettransfer/category/{category}/subcategory/{subcategory}")
	public ResponseEntity<JSONObject> getAllAssetsForTransfer(HttpServletRequest request, @PathVariable Long category,
			 @PathVariable Long subcategory) {
		JSONObject data = null;
		System.out.println("Controller getAllAssetsForTransfer "+category+" "+subcategory);
		
		try {
			
			data = assetService.getAllAssetsForTransfer(request, category, subcategory);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<JSONObject>(data,HttpStatus.OK);
		
		
	}
	
	
	@PostMapping(value = "transferAsset")
	public ResponseEntity<String> transferAsset(@RequestBody JSONObject data) {
		System.out.println("transferAsset "+data);
		
		String returnMsg = assetService.transferAsset(data);
		
		String msg = "transferAsset has been failed";
		
		if(returnMsg.equals("success")){
			msg = "success";
			return new ResponseEntity<String>(msg, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	

}
