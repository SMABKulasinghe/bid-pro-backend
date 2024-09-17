package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Reader;
import java.net.URLConnection;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.EvaluationAvarageMarksRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzTenderSubmissionsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EvaluationCommiteeRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EvaluationCommitteeAuthRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EvaluationMarksRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EvaluationMarksAllRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EvaluationSheetCreateRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.MITDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TenderDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarks;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarksAll;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;
import lk.supplierUMS.SupplierUMS_REST.entity.FinancialCodes;
import lk.supplierUMS.SupplierUMS_REST.entity.MITDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.Province;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.RFPHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SupplierProductDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import lk.supplierUMS.SupplierUMS_REST.entity.City;
import lk.supplierUMS.SupplierUMS_REST.entity.District;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategoryProducts;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationAvarageMarkSheet;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationCommitee;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationCommiteeAuth;
import lk.supplierUMS.SupplierUMS_REST.service.EvaluationSheetCreateService;

import org.apache.commons.io.FileUtils;
import lk.supplierUMS.SupplierUMS_REST.comman.Common;
import lk.supplierUMS.SupplierUMS_REST.comman.CommonEmail;
import lk.supplierUMS.SupplierUMS_REST.comman.DeEnCode;


@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class EvaluationServiceImpl implements EvaluationSheetCreateService {
	
	@Value("${mitDetails.doc.path}")
	String mitDetailsDataPath;
	
	@Autowired
	EvaluationSheetCreateRepo evaluationSheetCreateRepo;

	@Autowired
	TenderDetailsRepo tenderDetailsRepo;
	
	@Autowired
	EvaluationMarksRepo evaluationMarksRepo;
	
	@Autowired
	EvaluationCommiteeRepo evaluationCommiteeRepo;

	@Autowired
	EvaluationMarksAllRepo evaluationMarksAllRepo;
	
	@Autowired
	EvaluationAvarageMarksRepo evaluationAvarageMarksRepo;
	
	@Autowired
	EvaluationCommitteeAuthRepo evaluationCommiteeAuthRepo;

	@Autowired
	AppzTenderSubmissionsRepo appzTenderSubmissionsRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	Common common;
	
	@Autowired
	SubCompanyRepo subCompanyRepo;
	
	@Autowired
	MITDetailsRepo mitDetailsRepo;
	
	@Override
	public List<TenderDetails> getTenderId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		try {
			List<TenderDetails> next = tenderDetailsRepo.getTenderId();
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	@Override
	public String addevaluation(JSONObject data, HttpServletRequest request) {
		String msg = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		
		try {
			
		Optional<List<EvaluationSheetCreate>> markCriteriaList = evaluationSheetCreateRepo.findAllByTenderId(Long.valueOf(data.get("trnderid").toString()));
		
		if(markCriteriaList.isPresent()) {
			msg = "Already created";
		}else {
			
				HashMap<String, Object> table = (HashMap<String, Object>) data.get("table");
				System.out.println(table);
			
				ArrayList<Object> evaArray = (ArrayList<Object>) data.get("evaArray");
			

			for (Iterator iterator = evaArray.iterator(); iterator.hasNext();) {
				HashMap<String, Object> items = (HashMap) iterator.next();
				LinkedHashMap costingChemicalDetail = (LinkedHashMap) items;
				
			EvaluationSheetCreate evCreate = new EvaluationSheetCreate();
			
			evCreate.setTenderId(Long.valueOf(data.get("trnderid").toString()));
			evCreate.setCriteriaName(costingChemicalDetail.get("evcrname").toString());
			evCreate.setCriteriamaxName(Double.valueOf(costingChemicalDetail.get("evmaxmark").toString()));
			evCreate.setCreateDate(LocalDate.now());		
			evCreate.setCreatedUser(user.getUserid());
			evaluationSheetCreateRepo.save(evCreate);
			
			msg = "Success";
			}
			
			
			
		}
		
		} catch (Exception e) {
			  e.printStackTrace();
			  msg = "Error";
			}
		
	
		return msg;
	}


	@Override
	public String addmarksheet(JSONObject data, HttpServletRequest request) {
		
		// Mandotory to check wether already submitedd before excute bellow by running committe table 
		// is_submited && vendorID && tenderID
		
		
		String msg = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		try {
			System.out.println(data);
			
			Optional<List<EvaluationMarks>> optionalList = evaluationMarksRepo.findAllByTenderIdAndCreatedUserAndCompanyCode(Long.valueOf(data.get("tenderID").toString()), user.getUsername(), data.get("vendorID").toString());
			if (optionalList.isPresent()) {
				msg = "Already Marked";
			} else {
				
				ArrayList<Object> evaArray = (ArrayList<Object>) data.get("marksArray");
				
				for (Iterator iterator = evaArray.iterator(); iterator.hasNext();) {
					HashMap<String, Object> items = (HashMap) iterator.next();
					LinkedHashMap costingChemicalDetail = (LinkedHashMap) items;
				
			EvaluationMarks evaMark = new EvaluationMarks();

			evaMark.setEveMarks(Double.valueOf(costingChemicalDetail.get("enteredMark").toString()));
			evaMark.setCriteriamaxName(Double.valueOf(costingChemicalDetail.get("maxMark").toString()));
			evaMark.setEvaluationmarksheetID(Long.valueOf(costingChemicalDetail.get("evID").toString()));
			evaMark.setComment(costingChemicalDetail.get("enteredComment").toString());
			evaMark.setTenderId(Long.valueOf(data.get("tenderID").toString()));
			evaMark.setCompanyCode(data.get("vendorID").toString());
			evaMark.setCreateDate(LocalDate.now());		
			evaMark.setCreatedUser(user.getUserid());
			evaMark = evaluationMarksRepo.save(evaMark); 
			
			// findbysubmitteduserIDAndTenderID
			Optional<EvaluationCommitee> opevCom = evaluationCommiteeRepo.findBySubmitedUserIDAndTenderIDAndSupplierID(user.getUsername(), Long.valueOf(data.get("tenderID").toString()), Long.valueOf(data.get("vendorID").toString()));
			if(opevCom.isPresent()) {
				EvaluationCommitee ecom = opevCom.get();
				ecom.setSupplierID(Long.valueOf(data.get("vendorID").toString()));
				ecom.setIsSubmited(true);
				evaluationCommiteeRepo.save(ecom);
			}else {
				System.out.println("No such member ----");
			}
			
			
			Optional<EvaluationAvarageMarkSheet>  eamOP = evaluationAvarageMarksRepo.findByCompanyCodeAndTenderIdAndEvaluationmarksheetID(data.get("vendorID").toString(), Long.valueOf(data.get("tenderID").toString()), evaMark.getEvaluationmarksheetID());
			if (eamOP.isPresent()) {
				EvaluationAvarageMarkSheet evaAVMk = eamOP.get();
				
				Double newTotal = Double.sum(evaAVMk.getTotalMarks(), Double.valueOf(costingChemicalDetail.get("enteredMark").toString()));
				evaAVMk.setTotalMarks(newTotal);
				
				Long totEvsCount  = evaAVMk.getEvaluatorsCount() + 1;
				
				evaAVMk.setEvaluatorsCount(totEvsCount);
				evaAVMk.setAvarageMarks(newTotal / totEvsCount);
				evaluationAvarageMarksRepo.save(evaAVMk);
				System.out.println("Already ----");
				
			}else {
				EvaluationAvarageMarkSheet evaAVMkMew = new EvaluationAvarageMarkSheet();
				evaAVMkMew.setTenderId(Long.valueOf(data.get("tenderID").toString()));
				evaAVMkMew.setCompanyCode(data.get("vendorID").toString());
				evaAVMkMew.setEvaluatorsCount(Long.valueOf(1));
				evaAVMkMew.setAvarageMarks(Double.valueOf(costingChemicalDetail.get("enteredMark").toString()));
				evaAVMkMew.setEvaluationmarksheetID(evaMark.getEvaluationmarksheetID());
				evaAVMkMew.setTotalMarks(Double.valueOf(costingChemicalDetail.get("enteredMark").toString()));
				evaluationAvarageMarksRepo.save(evaAVMkMew);
				
				
				
				
			}
			
			}
				
				Optional<EvaluationCommitee> evaluationCommitee = evaluationCommiteeRepo.findBySubmitedUserIDAndSupplierIDAndTenderID(user.getUserid(), Long.valueOf(data.get("vendorID").toString()), Long.valueOf(data.get("tenderID").toString()));
				
				if(evaluationCommitee.isPresent()) {
					evaluationCommitee.get().setIsSubmited(true);
					evaluationCommiteeRepo.save(evaluationCommitee.get());
				}else {
					msg = "not assigned";
				}
				
				boolean bb =setAverageMarks(Long.valueOf(data.get("tenderID").toString()), Long.valueOf(data.get("vendorID").toString()));
				/*
				 * if (bb) { msg = "Success"; }else { msg = "Falied"; }
				 */
				msg = "Success";

			}
			
			
			
		}  catch (Exception e) {
			e.printStackTrace();
			msg = "Error";
		}
		return msg;
	}
	
	
	@Override
	public String getsubmitedcounts(Long tenderID, Long venderID) {
		/*
		 * Long totalSubmits =
		 * evaluationCommiteeRepo.submitedCountForTenderAndVendor(tenderID,venderID);
		 * System.out.println("totalSubmits--> "+totalSubmits);
		 * 
		 * 
		 * String evaluatorCount =
		 * evaluationAvarageMarksRepo.findFirstByTenderIdAndCompanyCode(tenderID,
		 * String.valueOf(venderID)).getEvaluatorsCount().toString();
		 * 
		 * return totalSubmits+" "+evaluatorCount;
		 */
		

		Long totalSubmits = evaluationCommiteeRepo.submitedCountForTenderAndVendor(tenderID,venderID);
		
		Long evaluatorCount = evaluationAvarageMarksRepo.findFirstByTenderIdAndCompanyCode(tenderID, String.valueOf(venderID)).getEvaluatorsCount();
		
		System.out.println("totalSubmits--> "+totalSubmits+" evaluatorCount--> "+evaluatorCount);
		Double total = 0.0;
		if(totalSubmits == evaluatorCount) {
			List<EvaluationAvarageMarkSheet> EveAll = evaluationAvarageMarksRepo.findAllByTenderIdAndCompanyCode(tenderID, String.valueOf(venderID));
			for (EvaluationAvarageMarkSheet evaluationAvarageMarkSheet : EveAll) {
				 total += evaluationAvarageMarkSheet.getAvarageMarks();
			}
			System.out.println(total);
		}
		return String.valueOf(total);
	
		
	}
	
	
	
	public boolean setAverageMarks(Long tenderID, Long venderID) {
		boolean  b = false;
		Long totalSubmits = evaluationCommiteeRepo.submitedCountForTenderAndVendor(tenderID,venderID);
		
		Long evaluatorCount = evaluationAvarageMarksRepo.findFirstByTenderIdAndCompanyCode(tenderID, String.valueOf(venderID)).getEvaluatorsCount();
		
		int evaluatorsCount = evaluatorCount.intValue();
		int totalSub = totalSubmits.intValue();
		
		System.out.println("totalSubmits--> "+totalSubmits+" evaluatorCount--> "+evaluatorCount);
		Double total = 0.0;
		if(totalSub == evaluatorsCount) {
			List<EvaluationAvarageMarkSheet> EveAll = evaluationAvarageMarksRepo.findAllByTenderIdAndCompanyCode(tenderID, String.valueOf(venderID));
			for (EvaluationAvarageMarkSheet evaluationAvarageMarkSheet : EveAll) {
				total += evaluationAvarageMarkSheet.getAvarageMarks();
			}
			System.out.println(total);
			
			
			EvaluationMarksAll newMarkAll = new EvaluationMarksAll();
			newMarkAll.setAveMarks(total);
			newMarkAll.setCompanyCode(String.valueOf(venderID));
			newMarkAll.setTenderId(tenderID);
			newMarkAll.setStatus("2");
			evaluationMarksAllRepo.save(newMarkAll);
			b = true;
		}
		return b;
	}
	
	
	
	@Override
	public JSONObject getEvaluatioedMarks(long tenderId, String venderId) {
		JSONObject data = new JSONObject();
		String tenderIDString=String.valueOf(tenderId);
		try {
			List<Object[]> emc = evaluationSheetCreateRepo.getMarkSheetVals(tenderId,venderId);
			JSONArray arrSheetCreate = new JSONArray();
			HashSet UserNames = new HashSet();
			
			ArrayList<Double> avgTotalArr = new ArrayList<Double>();
			int n = 1;
			Double GlobleAvgsum = 0.0;
			for (Object[] object : emc) {
				if (object[0] instanceof EvaluationSheetCreate && object[1] instanceof EvaluationAvarageMarkSheet) {
				EvaluationSheetCreate esc1 = (EvaluationSheetCreate) object[0];
				EvaluationAvarageMarkSheet eam= (EvaluationAvarageMarkSheet) object[1];
				
				JSONObject sheetCreateData = new JSONObject();
				sheetCreateData.put("markSheetID", esc1.getEvaluationmarksheetID());
				sheetCreateData.put("criteriaName", esc1.getCriteriaName());
				sheetCreateData.put("maxMark", esc1.getCriteriamaxName());
				
				sheetCreateData.put("avgMark", eam.getAvarageMarks());
				
				avgTotalArr.add(eam.getAvarageMarks());
				
				Double Avgsum = 0.0;
				for(int i = 0; i < avgTotalArr.size(); i++) {
					Avgsum += avgTotalArr.get(i);
				}
				System.out.println("sum-------->>>"+Avgsum);
				GlobleAvgsum = Avgsum;
				
				Long evaluationmarksheetID = null;
				evaluationmarksheetID = esc1.getEvaluationmarksheetID();
				
				List<Object[]> em = evaluationSheetCreateRepo.getEvaluatedMarksForTable(tenderId,venderId,evaluationmarksheetID);
				Double Total = null;
				JSONArray ar = new JSONArray();
				
				
				ArrayList<Integer> evaMarksArr = new ArrayList<Integer>();
				for (Object[] objects : em) {
					
					if (objects[0] instanceof EvaluationSheetCreate && objects[1] instanceof EvaluationMarks && objects[2] instanceof User) {
						EvaluationSheetCreate esc = (EvaluationSheetCreate) objects[0];
						EvaluationMarks emtbldata = (EvaluationMarks) objects[1];
						User user = (User) objects[2];
						
						JSONObject allData = new JSONObject();
						
						
						allData.put("eveMark", emtbldata.getEveMarks());
						int marks= emtbldata.getEveMarks().intValue(); 
						evaMarksArr.add(marks);
						//Total = Total + marks;
						
						UserNames.add(user.getUsername());
						ar.add(allData);
					}
					//System.out.println(evaMarksArr);
					
				}//END INSIDE FOR
				//int AvgCount = UserNames.size();
				
				//int avg = (int) (Total/AvgCount);
				//System.out.println("avarage-------->>>"+avg);
				//Total = null;
				//sheetCreateData.put("eveAvarage", avg);
				
				//System.out.println("sum-------->>>"+sum);
				sheetCreateData.put("eveMarks", ar);
				sheetCreateData.put("UserNames", UserNames);
				//sheetCreateData.put("avgSum", Avgsum);
				arrSheetCreate.add(sheetCreateData);
				n++;
				}
			}
			
			
			data.put("avarage", "Avarage");
			data.put("dataMarkSheet", arrSheetCreate);
			data.put("UserNames", UserNames);
			data.put("avgSum", GlobleAvgsum);
			System.out.println("Globle sum-------->>>"+GlobleAvgsum);
			return data;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		//return null;
	}



	@Override
	public JSONObject getTenderIds() {
		//LocalDate NowDate = LocalDate.now();
		JSONObject data = new JSONObject();
		try {
			List<Object[]> tenderDetails = tenderDetailsRepo.getTenderIdsForEvaluationSummery();
			JSONArray ar = new JSONArray();
			for (Object[] objects : tenderDetails) {
				if (objects[0] instanceof TenderDetails && objects[1] instanceof EvaluationMarksAll) {
					
					TenderDetails td = (TenderDetails) objects[0];
					EvaluationMarksAll eam = (EvaluationMarksAll) objects[1];
					
					JSONObject tenderIDs = new JSONObject();
					
					tenderIDs.put("tenderId", td.getTrnderid());	
					tenderIDs.put("tenderName", td.getTendername());
					tenderIDs.put("tBidNumber", td.getBidno());
					tenderIDs.put("closingDateTime", td.getClosingDateTime());
					
					ar.add(tenderIDs);
				}
			}
			System.out.println(ar);
			data.put("data", ar);
			return data;
			//return tenderDetails;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}



	@Override
	public JSONObject getEvaluationAllMarks(HttpServletRequest request,Long id) {
		
		/*
		 * JSONObject data = new JSONObject(); try { List<Object[]> evaluationMarksAll =
		 * evaluationMarksAllRepo.getEvaluationAllMarks(id); JSONArray ar = new
		 * JSONArray(); for (Object[] objects : evaluationMarksAll) { if (objects[0]
		 * instanceof TenderDetails && objects[1] instanceof EvaluationMarksAll &&
		 * objects[2] instanceof User) {
		 * 
		 * TenderDetails td = (TenderDetails) objects[0]; EvaluationMarksAll eam =
		 * (EvaluationMarksAll) objects[1]; User user = (User) objects[2]; JSONObject
		 * tenderIDs = new JSONObject();
		 * 
		 * //tenderIDs.put("tenderId", td.getTrnderid()); //tenderIDs.put("tenderName",
		 * td.getTendername()); //tenderIDs.put("tBidNumber", td.getBidno());
		 * //tenderIDs.put("closingDateTime", td.getClosingDateTime());
		 * //tenderIDs.put("closingDate",td.getClosingdate());
		 * //tenderIDs.put("closingTime",td.getClosingtime());
		 * tenderIDs.put("venderName", user.getUsername()); tenderIDs.put("mark",
		 * eam.getAveMarks());
		 * 
		 * 
		 * ar.add(tenderIDs); } } System.out.println(ar); data.put("data", ar); return
		 * data; //return tenderDetails; } catch (Exception e) { e.printStackTrace();
		 * return null; }
		 */
		
		try {

			JSONObject data = new JSONObject();
			
			
			Page<Object[]> evaluationMarksAll = null;
			data.put("draw", request.getParameter("draw"));
			String sorting = "";

			switch (request.getParameter("order[0][column]")) {
			case "0":
				sorting = "sc.scname";
				break;
			case "1":
				sorting = "ema.aveMarks";
				break;

			}
			
			if (request.getParameter("order[0][dir]").equals("asc")) {
				System.out.println("ASC ");
				evaluationMarksAll = evaluationMarksAllRepo.getEvaluationAllMarks(request.getParameter("search[value]"),
						id,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
										Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.ASC, sorting)));

				System.out.println("end ");
			} else {
				System.out.println("DESC ");
				evaluationMarksAll = evaluationMarksAllRepo.getEvaluationAllMarks(request.getParameter("search[value]"),
						id,
						PageRequest.of(
								Integer.parseInt(request.getParameter("start"))
										/ Integer.parseInt(request.getParameter("length")),
										Integer.parseInt(request.getParameter("length")),
								Sort.by(Sort.Direction.DESC, sorting)));
				
			}

			JSONArray ar = new JSONArray();
			long count = evaluationMarksAll.getSize();
			//lo
			System.out.println("get cn--->" + evaluationMarksAll.getContent().size());
			int place = 1;
			
			for (Object[] objects : evaluationMarksAll.getContent()) {
				
					if (objects[0] instanceof TenderDetails && objects[1] instanceof EvaluationMarksAll && objects[2] instanceof SubCompany) {
					
					TenderDetails td = (TenderDetails) objects[0];
					EvaluationMarksAll eam = (EvaluationMarksAll) objects[1];
					SubCompany sc = (SubCompany) objects[2];
					
					JSONObject tenderIDs = new JSONObject();
					//JSONObject ob = new JSONObject();
					
					tenderIDs.put("id", eam.getEvaluationAllMarksID());
					tenderIDs.put("venderName", sc.getScname());
					tenderIDs.put("mark", eam.getAveMarks());
					tenderIDs.put("place", place);
					tenderIDs.put("tenderId", eam.getTenderId());
					tenderIDs.put("status", eam.getStatus());
					tenderIDs.put("companyCode", eam.getCompanyCode());
					
					tenderIDs.put("date", LocalDate.now());
					tenderIDs.put("tenderName", td.getTendername());
					tenderIDs.put("cordinatorName1", td.getCordinator1name());
					tenderIDs.put("cordinatorTP1", td.getCordinator1contactno());
					tenderIDs.put("cordinatorName2", td.getCordinator2name());
					tenderIDs.put("cordinatorTP2", td.getCordinator2contactno());
					
					tenderIDs.put("votes", eam.getVoteFromProcument());
					  /*if(place == 1) {
						  tenderIDs.put("place", "1st");
					  }else if(place == 2) {
						  tenderIDs.put("place", "2nd");
					  }else if(place == 3) {
						  tenderIDs.put("place", "3rd");
					  }else if(place == 4) {
						  tenderIDs.put("place", "4th");
					  }else if(place == 5) {
						  tenderIDs.put("place", "5th");
					  }else {
						  tenderIDs.put("place", "None");
					  }*/
					 
					 place++;
					ar.add(tenderIDs);

				}

			}
			data.put("recordsTotal", count);
			data.put("recordsFiltered", count);
			data.put("data", ar);
			System.out.println("data ds--->" + data);
			return data;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
		//evaluationMarksAllRepo
		//return null;
	}



	@Override
	public List<TenderDetails> getTenderDetailsForMarks() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		try {
			List<TenderDetails> next = evaluationCommiteeRepo.getTenderDetailsForMarks();
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public List<User> getTenderEvaluationForCommittee() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		try {
			List<User> next = evaluationCommiteeRepo.getTenderEvaluationForCommittee();
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
	}
}



	@Override
	public JSONObject addevaluationcommittee(JSONObject data, HttpServletRequest request) {
		JSONObject returnData = new JSONObject();
		
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			Long tenderID = Long.valueOf(data.get("tenderID").toString());
			String committeemember = data.get("committeeMember").toString();
			
			Optional<List<TenderSubmissions>> optionalList = appzTenderSubmissionsRepo.findAllByTenderNoAndTenderResponse(Long.valueOf(data.get("tenderID").toString()), "8");
			if(optionalList.isPresent()) {
				System.out.println("Submited Vendors111111--> "+optionalList.get());
				List<TenderSubmissions> tenderSubmitList = optionalList.get();
				for (TenderSubmissions tenderSubmissions : tenderSubmitList) {
					System.out.println("Submited Vendors--> "+tenderSubmissions.getVendorId());
					
					
					
					Boolean evaCommite = evaluationCommiteeRepo.existsBySubmitedUserIDAndTenderIDAndSupplierID(
							committeemember, tenderID, tenderSubmissions.getSupplierId());
					System.out.println("IS ---- " + evaCommite);
					if (!evaCommite) { // create

						EvaluationCommitee evCommittee = new EvaluationCommitee();
						evCommittee.setTenderID(Long.valueOf(data.get("tenderID").toString()));
						evCommittee.setSubmitedUserID(committeemember);
						evCommittee.setSupplierID(tenderSubmissions.getSupplierId());
						evCommittee.setCreatedAt(LocalDateTime.now());
						evCommittee.setIsSubmited(false);
						evCommittee.setStatus(3);
						evaluationCommiteeRepo.save(evCommittee);

						
						  returnData.put("msg", "Success"); 
						  returnData.put("code", "00");// return
						 // returnData;
						 

					} else { // msg = "already exisits" returnData.put("msg", "already exists");
						returnData.put("code", "01");
					//	return returnData;
					}
					
				}
			}else {
				
			}
			
			
			
		} catch (Exception e) {
		  e.printStackTrace();
		  return returnData;
		}
		return returnData;
	}


	@Override
	public JSONObject submitEvaluationAllMarks(JSONObject data) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long userCompanyID = new Long(user.getCompanyCode());
		String userId = user.getUserid();
		
		JSONObject returnData = new JSONObject();
		try {
			
			Long tenderId = Long.valueOf(data.get("tenderId").toString());
			Long allMarksId = Long.valueOf(data.get("allMarksId").toString());
			String companyCode = data.get("companyCode").toString();	
			
			List<EvaluationMarksAll> evaMarks = evaluationMarksAllRepo.submitEvaluationAllMarks(tenderId);
			
			List<TenderSubmissions> tsDis = appzTenderSubmissionsRepo.tenderSubmissionDetailsForDesqulified(tenderId);
			
			for (EvaluationMarksAll eveMark : evaMarks) {
				if (eveMark != null) {
					eveMark.setStatus("9"); 
					eveMark.setUpdatedDateTime(LocalDateTime.now());
					eveMark.setLoggedUser(userId);
					eveMark.setOfferingSupplier(data.get("name").toString());
					eveMark.setReason(data.get("description").toString());
					evaluationMarksAllRepo.save(eveMark);
				}
			}
			
			for (TenderSubmissions ts : tsDis) {
				if (ts != null) {
					ts.setTenderEligibility("9");
					ts.setTenderEligibilityLoggedUser(userId);
					appzTenderSubmissionsRepo.save(ts);
				}
			}
			
			Optional<EvaluationMarksAll> ema = evaluationMarksAllRepo.findById(allMarksId);
			
			Optional<TenderSubmissions> tsQua = appzTenderSubmissionsRepo.findByVendorIdAndTenderNo(companyCode,tenderId);
			
			if (ema.isPresent()) {
				System.out.println("Inside ema");
				EvaluationMarksAll evaluationMarksAll = ema.get();
				evaluationMarksAll.setStatus("10");
				evaluationMarksAll.setUpdatedDateTime(LocalDateTime.now());
				evaluationMarksAll.setLoggedUser(userId);
				evaluationMarksAll.setOfferingSupplier(data.get("name").toString());
				evaluationMarksAll.setReason(data.get("description").toString());
				evaluationMarksAllRepo.save(evaluationMarksAll);
			}
			
			if(tsQua.isPresent()) {
				System.out.println("Inside tsQua");
				TenderSubmissions tenderSubmissions = tsQua.get();
				tenderSubmissions.setTenderEligibility("10");
				tenderSubmissions.setTenderEligibilityLoggedUser(userId);
				appzTenderSubmissionsRepo.save(tenderSubmissions);
			}
			
			Optional<TenderDetails> td = tenderDetailsRepo.findById(tenderId);
			Optional<User> userDetailss = userRepo.findById(td.get().getCreatedBy());
				new Thread(new Runnable() {
					String dear = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Dear "+userDetailss.get().getUsername()+",</span></p>";
					String LineSpace ="<p style=\"font-size: 14px; line-height: 140%;\"> </p>";
					String contentOfEmail = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">The tender you created has been offered.</span></p>";
					String thankyou = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Thank you</span></p>";
					String tenderNumber = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">Tender number : "+td.get().getBidno()+"</span></p>";
					String tenderName = "<p style=\"font-size: 14px; line-height: 140%;\"><span style=\"font-size: 18px; line-height: 25.2px; color: #666666;\">"+td.get().getTendername()+"</span></p>";
					
					String content = dear+LineSpace+tenderNumber+LineSpace+tenderName+LineSpace+contentOfEmail+LineSpace+thankyou+LineSpace;
					CommonEmail comEmail = new CommonEmail("Tender offered", content);
					String body = comEmail.getFirstPart()+comEmail.getHeadingOfEmail()+comEmail.getSecondPart()+comEmail.getContentOfEmail()+comEmail.getThirdPart();
					String subject = "BidPro Portal";
					
					@Override
					public void run() {
						String emails = userDetailss.get().getEmail();
						
						List<String> to = new ArrayList<String>();
						to.add(emails);
						
						common.sendEMailHtml(to, subject, body);
					}
				}).start();
			
			List<EvaluationMarksAll> evaStatuses = evaluationMarksAllRepo.submitEvaluationAllMarksAfterSubmit(tenderId);
			for (EvaluationMarksAll evaStatus : evaStatuses) {
				if (evaStatus != null) {
					String Status = evaStatus.getStatus();
					Long Ccode = Long.valueOf(evaStatus.getCompanyCode());
					String CcodeString = evaStatus.getCompanyCode();
					//Optional<SubCompany> userDetails = evaluationMarksAllRepo.getUserDetails(CcodeString);
					
					Optional<SubCompany> subCompany = evaluationMarksAllRepo.getSubCompany(Ccode);
					
					Optional<TenderDetails> tenderDetail = evaluationMarksAllRepo.getTenderDetail(tenderId);
					//subCompanyRepo
					
					
					LocalDate date = LocalDate.now();
						
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			returnData.put("responseText", "Success");
			return returnData;
		}
		return returnData;
	}



	@Override
	public JSONObject committeeview(HttpServletRequest request, Long tendrID) {
		try {
			JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
		/*	  case "0":
				sorting = "index";
				break;*/
			  case "0":
				sorting = "submitedUserID";
			    break;
			  case "1":
				sorting = "tendername";
				break;
			  case "2":
				sorting = "bidno";
				break;
			  
			}
			Page<Object[]> evaCom= null;
			if (request.getParameter("order[0][dir]").equals("asc")) {
				evaCom =evaluationCommiteeRepo.getCommitteeView(request.getParameter("search[value]"), tendrID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
				evaCom =evaluationCommiteeRepo.getCommitteeView(request.getParameter("search[value]"), tendrID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar =  new JSONArray();
			int i =0;
			System.out.println("evaCom.getContent()"+evaCom.getContent().size());
			for (Object[] objects : evaCom.getContent()) {
				System.out.println("index--- "+i+" "+evaCom.getSize());
				if (objects[0] instanceof EvaluationCommitee && objects[1] instanceof TenderDetails ) {
					EvaluationCommitee evaCommittee = (EvaluationCommitee) objects[0];
					TenderDetails tenderDetails = (TenderDetails) objects[1];
					
					JSONObject ob = new JSONObject();
					
					ob.put("index", ++i);
					ob.put("tenderNo",tenderDetails.getBidno());
					ob.put("committeMember", evaCommittee.getSubmitedUserID());
					ob.put("tenderID",tenderDetails.getTrnderid());
					ob.put("tenderName",tenderDetails.getTendername());
					
					ob.put("status",evaCommittee.getStatus());
					
					ar.add(ob);
					System.out.println("ob--- "+ob);
					System.out.println("index--- "+i+" "+tenderDetails.getBidno());
				}
			}
			 data.put("recordsTotal",evaCom.getTotalElements());
			 data.put("recordsFiltered",evaCom.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public JSONObject viewMarksheetsForEvaluator(HttpServletRequest request) {
		System.out.println("Inside ServiceImpl");
		
		try {
		JSONObject data= new JSONObject();	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- "+companyID);
		String loggedUserID = user.getUserid();
		
		data.put("draw", request.getParameter("draw"));
		String sorting = "";
		switch (request.getParameter("order[0][column]")) {
			case "0":
			sorting = "submitedUserID";
			break;
		case "1":
			sorting = "bidno";
			break;
		case "2":
			sorting = "tendername";
			break;
		case "3":
			sorting = "supplierID";
			break;
		case "4":
			sorting = "createdAt";
			break;	
		case "5":
			sorting = "scname";
			break;	
		}
		    
		Page<Object[]> evaSheet= null;
		
		if (request.getParameter("order[0][dir]").equals("asc")) {
			evaSheet = evaluationCommiteeRepo.getMarkSheetView(request.getParameter("search[value]"), loggedUserID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		} 
		else {
			evaSheet = evaluationCommiteeRepo.getMarkSheetView(request.getParameter("search[value]"), loggedUserID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		}
		
		JSONArray arr = new JSONArray();
		int i =0;
		for (Object[] objects : evaSheet.getContent()) {
			
			if (objects[0] instanceof EvaluationCommitee && objects[1] instanceof TenderDetails ) {
				EvaluationCommitee evaCommittee = (EvaluationCommitee) objects[0];
				TenderDetails tenderDetails = (TenderDetails) objects[1];
				SubCompany subCompany = (SubCompany) objects[2];
					  
			  JSONObject ob = new JSONObject(); 
			  ob.put("index", ++i);
			  ob.put("tenderNo",tenderDetails.getBidno());
			  ob.put("tenderName",tenderDetails.getTendername());
//			  ob.put("supplierId",evaCommittee.getSupplierID());
			  ob.put("supplierName",subCompany.getScname());
			  ob.put("createdDateTime", evaCommittee.getCreatedAt());
			  ob.put("isMarked",evaCommittee.getIsSubmited());
//concat	  ob.put("tenderName",tenderDetails.getBidno()+"-"+tenderDetails.getTendername()+""); 		  
			  
			  arr.add(ob); 
			  }
		 }
			data.put("recordsTotal", evaSheet.getTotalElements());
			data.put("recordsFiltered", evaSheet.getTotalElements());
			data.put("data", arr);
			System.out.println("data ds11111--->" + data);
			return data;
		}catch (Exception e) {
		 e.printStackTrace();
		 return null;
	   }
}



	@Override
	public JSONObject viewEvaluationSheetDetails(HttpServletRequest request, Long tenderID) {
		try {
		JSONObject data= new JSONObject();	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		Long companyID = new Long(user.getCompanyCode());
		System.out.println("Long----- "+companyID);
		String loggedUserID = user.getUserid();
		//Long tenderID = Long.valueOf("417");
		
		data.put("draw", request.getParameter("draw"));
		String sorting = "";
		switch (request.getParameter("order[0][column]")) {
			case "0":
			sorting = "createdUser";
			break;
		case "1":
			sorting = "bidno";
			break;
		case "2":
			sorting = "tendername";
			break;
		case "3":
			sorting = "criteriaName";
			break;
		case "4":
			sorting = "criteriamaxName";
			break;	
		}
		    
		Page<Object[]> evaCreate= null;
		
		if (request.getParameter("order[0][dir]").equals("asc")) {
			evaCreate = evaluationSheetCreateRepo.getEvaluationSheetDetails(request.getParameter("search[value]"), loggedUserID, tenderID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		} 
		else {
			evaCreate = evaluationSheetCreateRepo.getEvaluationSheetDetails(request.getParameter("search[value]"), loggedUserID, tenderID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
		}
		
		JSONArray arr = new JSONArray();
		int i =0;
		for (Object[] objects : evaCreate.getContent()) {
			
			if (objects[0] instanceof EvaluationSheetCreate && objects[1] instanceof TenderDetails ) {
				EvaluationSheetCreate evaSheet = (EvaluationSheetCreate) objects[0];
				TenderDetails tenderDetails = (TenderDetails) objects[1];
					  
			  JSONObject ob = new JSONObject(); 
			  ob.put("index", ++i);
			  ob.put("criteria",evaSheet.getCriteriaName());
			  ob.put("maximumMark",evaSheet.getCriteriamaxName());
			  ob.put("trnderid",tenderDetails.getTrnderid());
			  
//concat	  ob.put("tenderName",tenderDetails.getBidno()+"-"+tenderDetails.getTendername()+""); 		  
			  
			  arr.add(ob); 
			  }
		 }
			data.put("recordsTotal", evaCreate.getTotalElements());
			data.put("recordsFiltered", evaCreate.getTotalElements());
			data.put("data", arr);
			System.out.println("data ds11111--->" + data);
			return data;
		}catch (Exception e) {
		 e.printStackTrace();
		 return null;
	   }
	}



	@Override
	public List<TenderDetails> getViewForTenderDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		try {
			List<TenderDetails> next = tenderDetailsRepo.getViewForTenderDetails();
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public List<TenderDetails> getViewEveluationEditSheet() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		try {
			List<TenderDetails> next = tenderDetailsRepo.getViewEveluationEditSheet();
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public JSONObject editsheet(HttpServletRequest request, Long tendrID) {
		try {
			JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			String loggedUserID = user.getUserid();
			
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "criteriaName";
				break;
			  case "1":
				sorting = "bidno";
				break;
			  case "2":
				sorting = "criteriamaxName";
				break;
			  
			} 
			Page<Object[]> sheetCreate= null;
			if (request.getParameter("order[0][dir]").equals("asc")) {
				sheetCreate = evaluationSheetCreateRepo.getEditSheet(request.getParameter("search[value]"), loggedUserID, tendrID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
				sheetCreate = evaluationSheetCreateRepo.getEditSheet(request.getParameter("search[value]"), loggedUserID, tendrID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar =  new JSONArray();
			int i =0;
			for (Object[] objects : sheetCreate.getContent()) {
				
				if (objects[0] instanceof EvaluationSheetCreate && objects[1] instanceof TenderDetails ) {
					EvaluationSheetCreate evaSheet = (EvaluationSheetCreate) objects[0];
					TenderDetails tenderDetails = (TenderDetails) objects[1];
					
					JSONObject ob = new JSONObject();
					
					ob.put("index", ++i);
					ob.put("criteria",evaSheet.getCriteriaName());
					ob.put("maxMark", evaSheet.getCriteriamaxName());
					ob.put("evTenderID",tenderDetails.getTrnderid());
					ob.put("tenderName",tenderDetails.getTendername());
					ob.put("evMarkSheetID",evaSheet.getEvaluationmarksheetID());
					
					ar.add(ob);
					System.out.println("ob--- "+ob);
				}
			}
			 data.put("recordsTotal",sheetCreate.getTotalElements());
			 data.put("recordsFiltered",sheetCreate.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public JSONObject confirmEditSheet(Long selectedMarkShetID, JSONObject data) {
		JSONObject returnObj = new JSONObject();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long companyID = new Long(user.getCompanyCode());
			
			Optional<EvaluationSheetCreate> evaSheet = evaluationSheetCreateRepo.findById(Long.valueOf(data.get("selectedMarkSheetID").toString()));

			if (evaSheet.isPresent()) {
				EvaluationSheetCreate evaluationSheet = evaSheet.get();
	
				// update
				evaluationSheet.setCriteriaName(data.get("criteria").toString());
				evaluationSheet.setCriteriamaxName(Double.valueOf(data.get("maxMark").toString()));
				evaluationSheetCreateRepo.save(evaluationSheet);
				
				returnObj.put("code", "00");
				returnObj.put("msg", "success");
				return returnObj;
			}else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObj.put("code", "01");
			returnObj.put("msg", "failed");
			return returnObj;
		}
		return null;
	}



	@Override
	public JSONObject addNewCriteria(Long selectedTID, JSONObject data) {
		
		JSONObject returnObj = new JSONObject();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long companyID = new Long(user.getCompanyCode());
			
			Optional<EvaluationSheetCreate> evaSheet = evaluationSheetCreateRepo.findByTenderIdAndCriteriaName(selectedTID, data.get("newCriteria").toString());

			if (evaSheet.isPresent()) {
				
				returnObj.put("msg", "already exists");
				returnObj.put("code", "01");
				return returnObj;
				 
			}else {
				EvaluationSheetCreate evaluationSheet = new EvaluationSheetCreate();
				 evaluationSheet.setCriteriaName(data.get("newCriteria").toString());
				 evaluationSheet.setCriteriamaxName(Double.valueOf(data.get("newMaxMark").toString()));
				 evaluationSheet.setTenderId(Long.valueOf(data.get("selectedTID").toString()));
				 evaluationSheet.setCreateDate(LocalDate.now());		
				 evaluationSheet.setCreatedUser(user.getUserid());
				 evaluationSheetCreateRepo.save(evaluationSheet);
				  
				 returnObj.put("code", "00"); 
				 returnObj.put("msg", "success"); 
				 return returnObj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnObj;
		}
	}



	@Override
	public List<TenderDetails> getMITDetailsForTenderDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		try {
			List<TenderDetails> next = mitDetailsRepo.getMITDetailsForTenderDetails();
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public JSONObject enterMitDetails(JSONObject data, HttpServletRequest request) {		
		String returnMsg = null;
		
		MITDetails mitDetails = new MITDetails();
		
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			System.out.println("Long----- " + user.getCompanyCode());
						
					
				mitDetails.setTenderID(Long.valueOf(data.get("tenderID").toString()));
				mitDetails.setDescription(data.get("description").toString()); 
		//		mitDetails.setMitID(Long.valueOf(data.get("mitId").toString())); 
				

					if (data.containsKey("inputImage_logo")) {
						String[] logoData = data.get("inputImage_logo").toString().split(",");
						byte[] logodecodedBytes = Base64.getDecoder().decode(logoData[1]);
						String mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(logodecodedBytes)).split("/")[1];
						FileUtils.writeByteArrayToFile(new File(mitDetailsDataPath+data.get("tenderID").toString()+"/picture."+mimeType), logodecodedBytes);
						mitDetails.setPicuturePath(mitDetailsDataPath+data.get("tenderID").toString()+"/picture."+mimeType);
					}

					
					if (data.containsKey("inputImage_File")) {
						String headerData[]=data.get("inputImage_File").toString().split("base64,");
						String extention[]=data.get("inputImage_File").toString().split("[/;]");
						String exten = extention[1];
						System.out.println("split------------------->"+exten);
						
						String head=headerData[1].substring(0, (headerData[1].length()));
						new DeEnCode().decodeMethod(mitDetailsDataPath,  data.get("tenderID").toString()+"-"+user.getCompanyCode().toString()+ "/MitFileForm",exten, head);
						
						mitDetails.setFilePath(
							mitDetailsDataPath + data.get("tenderID").toString()+"-"+user.getCompanyCode().toString() + "/MitFileForm." + exten);
					}
					
					mitDetails = mitDetailsRepo.save(mitDetails);

					JSONObject returnObj =  new JSONObject();
					returnObj.put("code", "00");
					returnObj.put("msg", "success");
					return returnObj;
				 
			    }catch (Exception e) {
					e.printStackTrace();
					JSONObject returnObj =  new JSONObject();
					returnObj.put("code", "01");
					returnObj.put("msg", "failed");
					return returnObj;
				}
	}



	@Override
	public List<TenderDetails> getViewMITDetailsForTenderDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		try {
			List<TenderDetails> next = mitDetailsRepo.getViewMITDetailsForTenderDetails();
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public JSONObject viewMitDetails(HttpServletRequest req, Long tendrID) {
		try {
			JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
//			String loggedUserID = user.getUserid();
//			Long tenderID = Long.valueOf(data.get("tenderID").toString());
//			Long tenderID = 412L;
			
			data.put("draw", req.getParameter("draw"));
			String sorting = "";
			
			switch (req.getParameter("order[0][column]")) {
			  case "1":
				sorting = "picuturePath";
			    break;
			  case "2":
				sorting = "filePath";
			    break;
			  case "3":
				sorting = "description";
				break;
			}
			
			Page<Object[]> mitDetails = null;
			if (req.getParameter("order[0][dir]").equals("asc")) {
				mitDetails = mitDetailsRepo.getviewMitDetailsRelatedTenderName(req.getParameter("search[value]"), tendrID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
				mitDetails = mitDetailsRepo.getviewMitDetailsRelatedTenderName(req.getParameter("search[value]"), tendrID, PageRequest.of(Integer.parseInt(req.getParameter("start"))/Integer.parseInt(req.getParameter("length")),Integer.parseInt(req.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			
			JSONArray ar =  new JSONArray();
			int i =0;
			for (Object[] objects : mitDetails) {
				if (objects[0] instanceof MITDetails && objects[1] instanceof TenderDetails) {
							MITDetails mitd = (MITDetails) objects[0];
							TenderDetails td = (TenderDetails) objects[1];
				
					JSONObject ob = new JSONObject();
					
					ob.put("index", ++i);
					ob.put("tenderid",mitd.getTenderID());
					ob.put("description",mitd.getDescription());
					ob.put("tendername",td.getTendername());
					ob.put("trnderid",td.getTrnderid());
					ob.put("uploadFile", mitd.getFilePath());
					ob.put("mitID", mitd.getMitID());

					ar.add(ob);
					System.out.println("ob--- "+ob);
				}				
			}
			 data.put("recordsTotal",mitDetails.getTotalElements());
			 data.put("recordsFiltered",mitDetails.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public List<TenderDetails> getTenderDetailsForAuthorize() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		try {
			List<TenderDetails> next = tenderDetailsRepo.getTenderDetailsForAuthorize();
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public JSONObject authorizeevaluation(HttpServletRequest request, Long tendrID) {
		try {
			JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "criteriaName";
				break;
			  case "1":
				sorting = "criteriamaxName";
				break;
			}
			
			Page<Object[]> authEvaluation = null;
			if (request.getParameter("order[0][dir]").equals("asc")) {
				authEvaluation = evaluationSheetCreateRepo.getAuthorizeEvaluation(request.getParameter("search[value]"), tendrID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
				authEvaluation = evaluationSheetCreateRepo.getAuthorizeEvaluation(request.getParameter("search[value]"), tendrID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			
			JSONArray ar =  new JSONArray();
			int i =0;
			System.out.println("authEvaluation.getContent()"+authEvaluation.getContent().size());
			for (Object[] objects : authEvaluation.getContent()) {
				System.out.println("index--- "+i+" "+authEvaluation.getSize());
				
				if (objects[0] instanceof EvaluationSheetCreate && objects[1] instanceof TenderDetails ) {
					EvaluationSheetCreate evSheet = (EvaluationSheetCreate) objects[0];
					TenderDetails tenderDetails = (TenderDetails) objects[1];
					
					JSONObject ob = new JSONObject();
					
					ob.put("index", ++i);
					ob.put("tenderNo",tenderDetails.getBidno());
					ob.put("criteriaName", evSheet.getCriteriaName());
					ob.put("tenderID",tenderDetails.getTrnderid());
					ob.put("maximumMark",evSheet.getCriteriamaxName());
			//		ob.put("status",evSheet.getStatus());
					ar.add(ob);
				}
			}
			 data.put("recordsTotal",authEvaluation.getTotalElements());
			 data.put("recordsFiltered",authEvaluation.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public JSONObject authorizeevcommittee(HttpServletRequest request, Long tendrID) {
		try {
			JSONObject data= new JSONObject();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
		/*	  case "0":
				sorting = "tendername";
				break;*/
			  case "0":
				sorting = "submitedUserID";
				break;
			}
			
			Page<Object[]> authEvCommittee = null;
			if (request.getParameter("order[0][dir]").equals("asc")) {
				authEvCommittee = evaluationCommiteeRepo.getAuthorizeevCommittee(request.getParameter("search[value]"), tendrID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
				authEvCommittee = evaluationCommiteeRepo.getAuthorizeevCommittee(request.getParameter("search[value]"), tendrID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			
			JSONArray ar =  new JSONArray();
			int i =0;
			System.out.println("authEvCommittee.getContent()"+authEvCommittee.getContent().size());
			for (Object[] objects : authEvCommittee.getContent()) {
				System.out.println("index--- "+i+" "+authEvCommittee.getSize());
				
				if (objects[0] instanceof EvaluationCommitee && objects[1] instanceof TenderDetails ) {
					EvaluationCommitee evCommittee = (EvaluationCommitee) objects[0];
					TenderDetails tenderDetails = (TenderDetails) objects[1];
					
					JSONObject ob = new JSONObject();
					
					ob.put("index", ++i);
					ob.put("tenderNo",tenderDetails.getBidno());
					ob.put("tenderName", tenderDetails.getTendername());
					ob.put("tenderID",tenderDetails.getTrnderid());
					ob.put("committeeMember",evCommittee.getSubmitedUserID());
			//		ob.put("status",evSheet.getStatus());
					ar.add(ob);
				}
			}
			 data.put("recordsTotal",authEvCommittee.getTotalElements());
			 data.put("recordsFiltered",authEvCommittee.getTotalElements());
			 data.put("data", ar);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public JSONObject addAuthorizeComment(JSONObject data, String parameter) {
		JSONObject ob = new JSONObject();
		String msg = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User us = (User) authentication.getPrincipal();
		String userId = (us.getUserid());
		
		try {
			System.out.println(data.get("selectedTenderID").toString());
			Long tenderID = Long.valueOf(data.get("selectedTenderID").toString());

			Optional<EvaluationCommiteeAuth> evaComAuth = evaluationCommiteeAuthRepo.findByTenderID(tenderID);
			if(!evaComAuth.isPresent()) {
				System.out.println("Add supplier comment");
				EvaluationCommiteeAuth newAuth = new EvaluationCommiteeAuth();
				
				newAuth.setComment(data.get("comment").toString());
				newAuth.setTenderID(Long.valueOf(data.get("selectedTenderID").toString()));
				
				
				if(parameter.equals("approve")) {
					newAuth.setIsApproved(1);
					JSONObject item = new JSONObject();
					item.put("selectedTenderID", tenderID);
					item.put("reason", data.get("comment").toString()); 
					
				}else  if(parameter.equals("reject")){
					newAuth.setIsApproved(0);
					JSONObject item = new JSONObject();
					item.put("selectedTenderID", tenderID);
					item.put("reason", data.get("comment").toString());
			
				}else {
					System.out.println("Other value--> "+parameter);
				}
				evaluationCommiteeAuthRepo.saveAndFlush(newAuth);
				ob.put("responseText", "Success");	
				ob.put("code", "01");
				
			}else {
				System.out.println("Already exists");
				ob.put("responseText", "Already exists");	
				ob.put("code", "01");
			}
			return ob;
		}catch (Exception e) {
		     e.printStackTrace();
		     return null;
		}
		//return ob;
	}



	
}
