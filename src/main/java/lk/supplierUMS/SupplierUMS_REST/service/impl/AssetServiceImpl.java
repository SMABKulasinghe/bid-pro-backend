package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.exolab.castor.types.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.AssetCategoryRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.AssetRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.AssetSubCategoryRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.Asset;
import lk.supplierUMS.SupplierUMS_REST.entity.AssetCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.AssetSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.service.AssetService;

@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class AssetServiceImpl implements AssetService{
	
	@Autowired
	AssetRepo assetRepo;
	
	@Autowired
	AssetCategoryRepo assetCategoryRepo;
	
	@Autowired
	AssetSubCategoryRepo assetSubCategoryRepo;

	@Override
	public String addAsset(JSONObject data, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		
		System.out.println("Long----- "+userCompanyID);
		
		Asset newAsset = new Asset();
		newAsset.setCategory(Long.parseLong(data.get("category").toString()));
		newAsset.setAssetCode(data.get("assetCode").toString());
		newAsset.setSupplierName(data.get("supplierName").toString());
		newAsset.setSubCategory(Long.parseLong(data.get("assetSubCategory").toString()));
		newAsset.setSupplierCode(Long.parseLong(data.get("assetSupplierCode").toString()));
		newAsset.setSupplierName(data.get("supplierName").toString());
		newAsset.setEmail(data.get("userEmail").toString());
		newAsset.setBrnad(data.get("brandName").toString());
		newAsset.setModel(data.get("ModelName").toString());
		newAsset.setMemory(Double.parseDouble(data.get("RAM").toString()));
		newAsset.setHdd(Double.parseDouble(data.get("HDD").toString()));
		newAsset.setProcessor(Double.parseDouble(data.get("processor").toString()));
		newAsset.setCurrentMarketValue(Double.parseDouble(data.get("currentMarketValue").toString()));
		newAsset.setDisposableAmout(Double.parseDouble(data.get("disposableAmount").toString()));
		newAsset.setDeprecationAmount(Double.parseDouble(data.get("depricationAmount").toString()));
		newAsset.setDeprecationMethod(data.get("depricationMethod").toString());
		newAsset.setDisposableAmout(Double.parseDouble(data.get("disposableAmount").toString()));
		newAsset.setCurrentConditionCode(data.get("currentConditionCode").toString());
		newAsset.setAssetmovementStatus(data.get("assetmovementStatus").toString());
		newAsset.setCurrentLocationCode(data.get("currentLocation").toString());
		newAsset.setTransferedFromLocation(data.get("transferedFrom").toString());
		
		newAsset.setLastInspectedBy(data.get("lastInspectedBy").toString());
		newAsset.setRegistrationNo(data.get("registrationNo").toString());
		newAsset.setSerialNo(data.get("serialNo").toString());
		newAsset.setStatus("P");
		newAsset.setColor(data.get("color").toString());
		newAsset.setLastRepairDate(Instant.ofEpochMilli(new Long(data.get("lastRepairDate").toString())).atZone(ZoneId.systemDefault()).toLocalDateTime());
		
		newAsset.setTransferedDate(Instant.ofEpochMilli(new Long(data.get("transferredDate").toString())).atZone(ZoneId.systemDefault()).toLocalDateTime());
		newAsset.setBuyingDate(Instant.ofEpochMilli(new Long(data.get("buyingDate").toString())).atZone(ZoneId.systemDefault()).toLocalDateTime());
		
		newAsset.setSubCompanyID(userCompanyID);
		//
		assetRepo.save(newAsset);
		
		return "success";
	}

	@Override
	public JSONObject getAllAssets(HttpServletRequest request, String parameter) {
		// TODO Auto-generated method stub

		
		System.out.println("Inside ServiceImpl "+parameter);
		// TODO Auto-generated method stub
		JSONObject data = new JSONObject();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		
		System.out.println("Long----- "+userCompanyID);
		try {
			data.put("draw", request.getParameter("draw"));
			String sorting="";
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "";
			    break;
			  case "1":
				sorting = "supplierName";
			    break;
			  case "2":
				sorting = "email";
			    break;
			  case "3":
				sorting = "brnad";
			    break;
			  case "4":
				sorting = "model";
			    break;
			  case "5":
				  sorting = "currentMarketValue";
				  break;
			  case "6":
				  sorting = "currentConditionCode";
				  break;
			}
			Page<Object> dataList = null;
			
			if(parameter.equals("viewall")){
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =assetRepo.getAssetDetails(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =assetRepo.getAssetDetails(request.getParameter("search[value]"), userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}
			
			JSONArray ar = new JSONArray();
//			System.out.println("Size -------------"+dataList.size());
			 for (Object contractDetails : dataList.getContent()) {
				 if(contractDetails instanceof Asset){
					 Asset ph = (Asset) contractDetails;
				//	 SubCompany subcompany = (SubCompany) contractDetails[1];
					 JSONObject header = new JSONObject();
					 
					
					 	header.put("assetCode", ph.getAssetID());
					 	header.put("category", ph.getCategory());
					 	header.put("assetSubCategory", ph.getSubCategory());
					 	header.put("assetSupplierCode", ph.getSupplierCode());
						header.put("supplierName", ph.getSupplierName());
						header.put("email", ph.getEmail());
						header.put("brnad", ph.getBrnad());
						header.put("model", ph.getModel());
						header.put("RAM", ph.getMemory());
						header.put("HDD", ph.getHdd());
						header.put("processor", ph.getProcessor());
						header.put("currentMarketValue", ph.getCurrentMarketValue());
						header.put("disposableAmount", ph.getDisposableAmout());
						header.put("depricationMethod", ph.getDeprecationMethod());
						header.put("depricationAmount", ph.getDeprecationAmount());
						header.put("currentConditionCode", ph.getCurrentConditionCode());
						header.put("assetmovementStatus", ph.getAssetmovementStatus());
						header.put("transferredDate", ph.getTransferedDate());
						header.put("buyingDate", ph.getBuyingDate());
						header.put("transferedFrom", ph.getTransferedFromLocation());
						header.put("currentLocation", ph.getCurrentLocationCode());
						header.put("lastInspectedBy", ph.getLastInspectedBy());
						header.put("registrationNo", ph.getRegistrationNo());
						header.put("serialNo", ph.getSerialNo());
						header.put("color", ph.getColor());
						header.put("lastRepairDate", ph.getLastRepairDate());
						
						
						
						
						
						JSONArray view = new JSONArray();
						view.add(ph.getEmail());
						view.add(ph.getEmail());
						view.add(ph.getEmail());
						view.add(ph.getEmail());
						view.add(ph.getEmail());
						header.put("details", view);
						ar.add(header);
				 }
				
				
				
			}
			 data.put("recordsTotal",dataList.getTotalElements());
			 data.put("recordsFiltered",dataList.getTotalElements());
			 data.put("data", ar);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	
		
		
	}

	@Override
	public String removeAsset(JSONObject data) {
		// TODO Auto-generated method stub
		
		try {
			Asset removeAsset = assetRepo.findById(Long.parseLong(data.get("assetCode").toString())).get();
			removeAsset.setStatus("R");
			assetRepo.save(removeAsset);
			
			return "success";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "failed";
		}
		
	
		
		
	}

	@Override
	public JSONObject getAssetsID(HttpServletRequest request, String parameter) {
		// TODO Auto-generated method stub
		JSONObject header = new JSONObject();
		try {
			
			
			Asset ph = (Asset) assetRepo.findById(Long.parseLong(parameter)).get();
			 
			
		 	header.put("assetCode", ph.getAssetID());
		 	header.put("category", ph.getCategory());
		 	header.put("assetSubCategory", ph.getSubCategory());
		 	header.put("assetSupplierCode", ph.getSupplierCode());
			header.put("supplierName", ph.getSupplierName());
			header.put("email", ph.getEmail());
			header.put("brnad", ph.getBrnad());
			header.put("model", ph.getModel());
			header.put("RAM", ph.getMemory());
			header.put("HDD", ph.getHdd());
			header.put("processor", ph.getProcessor());
			header.put("currentMarketValue", ph.getCurrentMarketValue());
			header.put("disposableAmount", ph.getDisposableAmout());
			header.put("depricationMethod", ph.getDeprecationMethod());
			header.put("depricationAmount", ph.getDeprecationAmount());
			header.put("currentConditionCode", ph.getCurrentConditionCode());
			header.put("assetmovementStatus", ph.getAssetmovementStatus());
			header.put("transferredDate", ph.getTransferedDate());
			header.put("buyingDate", ph.getBuyingDate());
			header.put("transferedFrom", ph.getTransferedFromLocation());
			header.put("currentLocation", ph.getCurrentLocationCode());
			header.put("lastInspectedBy", ph.getLastInspectedBy());
			header.put("registrationNo", ph.getRegistrationNo());
			header.put("serialNo", ph.getSerialNo());
			header.put("color", ph.getColor());
			header.put("lastRepairDate", ph.getLastRepairDate());
			
			JSONArray view = new JSONArray();
			view.add(ph.getEmail());
			view.add(ph.getEmail());
			view.add(ph.getEmail());
			view.add(ph.getEmail());
			view.add(ph.getEmail());
			header.put("details", view);
			
			return header;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return header;
		}
		
	
	}

	@Override
	public String updateAsset(JSONObject data) {
		// TODO Auto-generated method stub
		
		try {
			Asset newAsset = (Asset) assetRepo.findById(Long.parseLong(data.get("assetCode").toString())).get();
			
			newAsset.setCategory(Long.parseLong(data.get("category").toString()));
			newAsset.setSupplierName(data.get("supplierName").toString());
			newAsset.setSubCategory(Long.parseLong(data.get("assetSubCategory").toString()));
			newAsset.setSupplierCode(Long.parseLong(data.get("assetSupplierCode").toString()));
			newAsset.setSupplierName(data.get("supplierName").toString());
			newAsset.setEmail(data.get("userEmail").toString());
			newAsset.setBrnad(data.get("brandName").toString());
			newAsset.setModel(data.get("ModelName").toString());
			newAsset.setMemory(Double.parseDouble(data.get("RAM").toString()));
			newAsset.setHdd(Double.parseDouble(data.get("HDD").toString()));
			newAsset.setProcessor(Double.parseDouble(data.get("processor").toString()));
			newAsset.setCurrentMarketValue(Double.parseDouble(data.get("currentMarketValue").toString()));
			newAsset.setDisposableAmout(Double.parseDouble(data.get("disposableAmount").toString()));
			newAsset.setDeprecationAmount(Double.parseDouble(data.get("depricationAmount").toString()));
			newAsset.setDeprecationMethod(data.get("depricationMethod").toString());
			newAsset.setDisposableAmout(Double.parseDouble(data.get("disposableAmount").toString()));
			newAsset.setCurrentConditionCode(data.get("currentConditionCode").toString());
			newAsset.setAssetmovementStatus(data.get("assetmovementStatus").toString());
			newAsset.setCurrentLocationCode(data.get("currentLocation").toString());
			newAsset.setTransferedFromLocation(data.get("transferedFrom").toString());
			
			newAsset.setLastInspectedBy(data.get("lastInspectedBy").toString());
			newAsset.setRegistrationNo(data.get("registrationNo").toString());
			newAsset.setSerialNo(data.get("serialNo").toString());
			newAsset.setStatus("P");
			newAsset.setColor(data.get("color").toString());
			newAsset.setLastRepairDate(Instant.ofEpochMilli(new Long(data.get("lastRepairDate").toString())).atZone(ZoneId.systemDefault()).toLocalDateTime());
			
			newAsset.setTransferedDate(Instant.ofEpochMilli(new Long(data.get("transferredDate").toString())).atZone(ZoneId.systemDefault()).toLocalDateTime());
			newAsset.setBuyingDate(Instant.ofEpochMilli(new Long(data.get("buyingDate").toString())).atZone(ZoneId.systemDefault()).toLocalDateTime());
			
			//
			assetRepo.save(newAsset);
			
			return "success";
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public JSONObject getAssetCategory(HttpServletRequest request, String parameter) {
		// TODO Auto-generated method stub
		List<AssetCategory> assetCategoryList  = assetCategoryRepo.findAll();
		JSONArray ar = new JSONArray();
		JSONObject jsonOut = new JSONObject(); 
		for (AssetCategory assetCategory : assetCategoryList) {
			JSONObject header = new JSONObject(); 
			
			header.put("categoryID", assetCategory.getAssetCategoryID());
			header.put("categoryName", assetCategory.getCategoryName());
			
			ar.add(header);
		}
		jsonOut.put("subCategory", ar);
		return jsonOut;
	}

	@Override
	public JSONObject getAssetSubCategory(HttpServletRequest request, String parameter) {
		// TODO Auto-generated method stub
		JSONArray ar = new JSONArray();
		JSONObject jsonOut = new JSONObject(); 
		try {
			System.out.println("Long Para "+new Long(parameter));
			/*List<AssetSubCategory> assetCategoryList  =   assetSubCategoryRepo.findAll();*/
			List<AssetSubCategory> assetCategoryList  =   assetSubCategoryRepo.findByCategoryID(new Long(parameter));
			for (AssetSubCategory assetSubCategory : assetCategoryList) {
				JSONObject header = new JSONObject();
				header.put("subCategoryID", assetSubCategory.getAssetSubCategoryID());
				header.put("subCategoryName", assetSubCategory.getSubCategoryName());
				
				ar.add(header);
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jsonOut.put("subCategory", ar);
		return jsonOut;
	}

	@Override
	public JSONObject getAllAssetsForTransfer(HttpServletRequest request, Long category, Long subcategory) {
		// TODO Auto-generated method stub

		
		System.out.println("Inside getAllAssetsForTransfer ServiceImpl "+category+" "+subcategory);
		// TODO Auto-generated method stub
		JSONObject data = new JSONObject();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		
		System.out.println("Long----- "+userCompanyID);
		try {
			data.put("draw", request.getParameter("draw"));
			String sorting="";
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "";
			    break;
			  case "1":
				sorting = "supplierName";
			    break;
			  case "2":
				sorting = "email";
			    break;
			  case "3":
				sorting = "brnad";
			    break;
			  case "4":
				sorting = "model";
			    break;
			  case "5":
				  sorting = "currentMarketValue";
				  break;
			  case "6":
				  sorting = "currentConditionCode";
				  break;
			}
			Page<Object> dataList = null;
			
			if(true){
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =assetRepo.getAssetDetailsForCatWithSubCat(request.getParameter("search[value]"), userCompanyID, category, subcategory, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =assetRepo.getAssetDetailsForCatWithSubCat(request.getParameter("search[value]"), userCompanyID, category, subcategory, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}
			
			JSONArray ar = new JSONArray();
//			System.out.println("Size -------------"+dataList.size());
			 for (Object contractDetails : dataList.getContent()) {
				 if(contractDetails instanceof Asset){
					 Asset ph = (Asset) contractDetails;
				//	 SubCompany subcompany = (SubCompany) contractDetails[1];
					 JSONObject header = new JSONObject();
					 
					
					 	header.put("assetCode", ph.getAssetID());
					 	header.put("category", ph.getCategory());
					 	header.put("assetSubCategory", ph.getSubCategory());
					 	header.put("assetSupplierCode", ph.getSupplierCode());
						header.put("supplierName", ph.getSupplierName());
						header.put("email", ph.getEmail());
						header.put("brnad", ph.getBrnad());
						header.put("model", ph.getModel());
						header.put("RAM", ph.getMemory());
						header.put("HDD", ph.getHdd());
						header.put("processor", ph.getProcessor());
						header.put("currentMarketValue", ph.getCurrentMarketValue());
						header.put("disposableAmount", ph.getDisposableAmout());
						header.put("depricationMethod", ph.getDeprecationMethod());
						header.put("depricationAmount", ph.getDeprecationAmount());
						header.put("currentConditionCode", ph.getCurrentConditionCode());
						header.put("assetmovementStatus", ph.getAssetmovementStatus());
						header.put("transferredDate", ph.getTransferedDate());
						header.put("buyingDate", ph.getBuyingDate());
						header.put("transferedFrom", ph.getTransferedFromLocation());
						header.put("currentLocation", ph.getCurrentLocationCode());
						header.put("lastInspectedBy", ph.getLastInspectedBy());
						header.put("registrationNo", ph.getRegistrationNo());
						header.put("serialNo", ph.getSerialNo());
						header.put("color", ph.getColor());
						header.put("lastRepairDate", ph.getLastRepairDate());
						
						header.put("transferedToLocation", ph.getTransferedToLocation());
						header.put("transferComment", ph.getTransferComment());
						
						
						
						
						
						
						
						JSONArray view = new JSONArray();
						view.add(ph.getEmail());
						view.add(ph.getEmail());
						view.add(ph.getEmail());
						view.add(ph.getEmail());
						view.add(ph.getEmail());
						header.put("details", view);
						ar.add(header);
				 }
				
				
				
			}
			 data.put("recordsTotal",dataList.getTotalElements());
			 data.put("recordsFiltered",dataList.getTotalElements());
			 data.put("data", ar);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	
		
		
	}

	@Override
	public String transferAsset(JSONObject data) {
		// TODO Auto-generated method stub
		
		try {
			Asset newAsset = (Asset) assetRepo.findById(Long.parseLong(data.get("assetCode").toString())).get();
			
			String locationBeforeMove = newAsset.getCurrentLocationCode();
			
			newAsset.setCurrentLocationCode(data.get("transferedToLocation").toString());
			newAsset.setTransferedFromLocation(locationBeforeMove);
			
			newAsset.setTransferedToLocation(locationBeforeMove+" to "+ data.get("transferedToLocation").toString());
			newAsset.setTransferComment(data.get("transferComment").toString());
			
			newAsset.setTransferedDate(LocalDateTime.now());
			
			assetRepo.save(newAsset);
			
			return "success";
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	


}
