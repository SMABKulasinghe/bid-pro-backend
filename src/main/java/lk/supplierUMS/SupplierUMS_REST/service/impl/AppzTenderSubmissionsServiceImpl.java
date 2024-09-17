package lk.supplierUMS.SupplierUMS_REST.service.impl;


import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lk.supplierUMS.SupplierUMS_REST.comman.Common;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EligibleSubCategory;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationCommitee;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationMarks;
import lk.supplierUMS.SupplierUMS_REST.entity.EvaluationSheetCreate;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderStatusDetails;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzTenderSubmissionsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EvaluationCommiteeRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.EvaluationSheetCreateRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.TenderDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.AppzTenderSubmissionsRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.TenderSubmissions;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.service.AppzTenderSubmissionsService;
@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class AppzTenderSubmissionsServiceImpl implements AppzTenderSubmissionsService {

	@Autowired
	AppzTenderSubmissionsRepo appzTenderSubmissionsRepo;
	
	@Autowired
	TenderDetailsRepo tenderDetailsRepo;

	@Autowired
	EvaluationSheetCreateRepo evaluationSheetCreateRepo;
	
	@Autowired
	Common common;
	
	@Autowired
	SubCompanyRepo subCompanyRepo;
	
	@Autowired
	EvaluationCommiteeRepo evaluationCommiteeRepo;
	
	@Override
	public JSONObject confirmEligibleSuppliers(JSONObject data, Long tenderId) {
			// TODO Auto-generated method stub
		  
				JSONObject returnData = new JSONObject();

				try {
					System.out.println(tenderId);
					
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					User user = (User) authentication.getPrincipal();
					Long userCompanyID = new Long(user.getCompanyCode());
					
					List<SubCompany> suppliersList =  tenderDetailsRepo.getEligibleSuppliersForACategoryANDTenders(tenderId);
					
					for (SubCompany subCompany : suppliersList) {
						System.out.println("subCompany---> "+subCompany.getScname());
						Long supplierID = subCompany.getSubCompanyID();
						
						Optional<TenderSubmissions> eligibleSup = appzTenderSubmissionsRepo.findByTenderNoAndSupplierId(tenderId,supplierID);
						//Optional<TenderDetails> tenDetails = tenderDetailsRepo.findById(tenderId);
						
						if (eligibleSup.isPresent()) {
						System.out.println("Test "+eligibleSup.get().getTenderNo());
		
							returnData.put("msg", "Already Confirmed");
							returnData.put("code", "00");
							//return returnData;
						}else {
							// save
							TenderSubmissions tenderSubmissions = new TenderSubmissions();
							
							tenderSubmissions.setTenderNo(Long.valueOf(data.get("tenderID").toString()));
							tenderSubmissions.setSupplierId(Long.valueOf(supplierID));
							tenderSubmissions.setConfirmEligibleSupplier(data.get("confirmEligibleSupplier").toString());
							tenderSubmissions.setTenderResponse("4");
							tenderSubmissions.setVendorId(supplierID.toString());
							tenderSubmissions.setStatus("2");
							tenderSubmissions.setEmailNotify("Mail Sent");
							tenderSubmissions.setInviteDate(LocalDateTime.now());
							
							
							Optional<SubCompany> emailForSend = subCompanyRepo.getEmailsForSubCompany(supplierID);
							Optional<TenderDetails> td = tenderDetailsRepo.findById(Long.valueOf(data.get("tenderID").toString()));
							
							
							
							String subject = "NDB Tenders";
							String body = "Dear Vendor,"
									+ "\n"
									+ "\nTender Number -: " + td.get().getBidno()
									+ "\nTender Name -: " + td.get().getTendername()
									+"\nTenders are hereby called for the supply of,"
									+ "\n"
									+ "\nDescription/Specifications -: Please Refer Attached."
									+ "\n"
									+ "\nNote:- Quotation will be accepted only in LKR."
									+ "\n"
	                                + "\nClosing Time -: " + td.get().getClosingDateTime()
		                            +"\n"
		                            +"\n"
		                            +"\nThe Chairman,"
		                            +"\nProcurement Committee,"
		                            +"\nNational Development Bank PLC"
									+ "\n40, Nawam Mawatha,"
									+ "\nColombo 2."
									+"\n"
									+ "\nPlease do not reply to this email";
							

							 new Thread(new Runnable() {
									
								@Override
								public void run() {
								
									List<String> to = new ArrayList<String>();
									to.add(emailForSend.get().getScemailadmin());
									Boolean result = common.sendEMail(to, subject, body);
									if(result) {
									 //save
										
										//TenderSubmissions tenderSubmission = new TenderSubmissions();
										
										//tenderSubmission.setEmailNotify("Sent");
										//appzTenderSubmissionsRepo.save(tenderSubmission);
										
										System.out.println(10000);
										//ob.put("code", "00");
										//ob.put("msg", "Confirm");
										//.put("clientID", Long.valueOf(data.get("tenderID").toString()));
										
									}else {
										//ob.put("code", "01");
										//ob.put("msg", "Failed");
										//ob.put("clientID", Long.valueOf(data.get("tenderID").toString()));
									}
								}
							}).start();
							 
							TenderDetails tenderDetails = td.get();
							tenderDetails.setStatus("4");
							tenderDetailsRepo.save(tenderDetails);
								
							appzTenderSubmissionsRepo.save(tenderSubmissions);
							returnData.put("msg", "Success");
							returnData.put("code", "01");
							//return returnData;
						}
						
					}
					

					return returnData;
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					returnData.put("msg", "failed");
					returnData.put("code", "02");
					return returnData;
				}
		}


	
	@Override
	public JSONObject confirmTender(JSONObject data, Long tenderId) {
		// TODO Auto-generated method stub
		
		JSONObject returnData = new JSONObject();

		try {
			System.out.println(tenderId);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());

			System.out.println("Long----- " + user.getCompanyCode());
			System.out.println("User ----- " + user.getUserid()+" "+user.getCompanyCode());
			

			Optional<TenderSubmissions> optTenSub = appzTenderSubmissionsRepo.findByTenderNoAndSupplierId(tenderId, Long.valueOf(user.getCompanyCode()));

		//	System.out.println("test1 "+optTenSub.get().getTenderNo());
			if (optTenSub.isPresent()) {
			System.out.println("test "+optTenSub.get().getTenderNo());

				
				returnData.put("msg", "already updated");
				returnData.put("code", "00");
				return returnData;
			}else {
				// save
				TenderSubmissions tenderSubmissions = new TenderSubmissions();
				
				tenderSubmissions.setTenderNo(Long.valueOf(data.get("tenderID").toString()));
				tenderSubmissions.setSupplierId(Long.valueOf(user.getCompanyCode().toString()));
				tenderSubmissions.setTenderAction(data.get("tenderAction").toString());
				tenderSubmissions.setTenderResponse(data.get("tenderResponse").toString());
				tenderSubmissions.setUserId(user.getUserid().toString());
				appzTenderSubmissionsRepo.save(tenderSubmissions);
				
				returnData.put("msg", "Success");
				returnData.put("code", "01");
				return returnData;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnData.put("msg", "falied");
			returnData.put("code", "02");
			return returnData;
		}
}

	@Override
	public JSONObject responseTender(Long selectedTenderID, @RequestBody JSONObject data) {
		// TODO Auto-generated method stub
		
		
		JSONObject returnObj = new JSONObject();

		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			
		
			
			
			Optional<TenderSubmissions> tenderSub = appzTenderSubmissionsRepo
					.findByTenderNoAndSupplierId(selectedTenderID, Long.valueOf(user.getCompanyCode()));

			if (!tenderSub.isPresent()) {
				//System.out.println("test " + tenderSub.get().getTenderNo()+" "+data.get("responseCat").toString());
				TenderSubmissions tenderSubmissions = new TenderSubmissions();
				switch(data.get("responseCat").toString()) {
				  case "Will not participate":
				    // code block
					  tenderSubmissions.setTenderResponse(Long.valueOf("6").toString());
				    break;
				  case "Consider later":
				    // code block
					  tenderSubmissions.setTenderResponse(Long.valueOf("14").toString());
				    break;
				  case "Will Participate":
					  // code block
					  tenderSubmissions.setTenderResponse(Long.valueOf("5").toString());
					  System.out.println("Inside Will Participate");
					  break;
				  case "Not Interested":
					    // code block
						  tenderSubmissions.setTenderResponse(Long.valueOf("7").toString());
						 System.out.println("Inside NOT IN");
					  break;
				  default:
				    // code block
				}

				
				// save
				
				tenderSubmissions.setTenderNo(Long.valueOf(data.get("tenderID").toString()));
				tenderSubmissions.setVendorId(user.getCompanyCode().toString());
				tenderSubmissions.setSupplierId(Long.valueOf(user.getCompanyCode().toString()));
				tenderSubmissions.setTenderAction(data.get("tenderAction").toString());
				tenderSubmissions.setSubmittedDate(LocalDate.now());
				tenderSubmissions.setSubmittedTime(LocalTime.now());
				tenderSubmissions.setUserId(user.getUserid().toString());
				
				appzTenderSubmissionsRepo.save(tenderSubmissions);
				
				returnObj.put("code", "00");
				returnObj.put("msg", "success");

				return returnObj;
			}else {
				//update
				TenderSubmissions tenderSubmissions  = tenderSub.get();
				switch(data.get("responseCat").toString()) {
				  case "Will not participate":
				    // code block
					  tenderSubmissions.setTenderResponse(Long.valueOf("6").toString());
				    break;
				  case "Consider later":
				    // code block
					  tenderSubmissions.setTenderResponse(Long.valueOf("14").toString());
				    break;
				  case "Will Participate":
					  // code block
					  tenderSubmissions.setTenderResponse(Long.valueOf("5").toString());
					  System.out.println("Inside Will Participate");
					  break;
				  case "Not Interested":
					    // code block
						  tenderSubmissions.setTenderResponse(Long.valueOf("7").toString());
						 System.out.println("Inside NOT IN");
					  break;
				  default:
				    // code block
				}

				
				appzTenderSubmissionsRepo.save(tenderSubmissions);
				returnObj.put("code", "02");
				returnObj.put("msg", "updated");
				return returnObj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObj.put("code", "01");
			returnObj.put("msg", "failed");
			return returnObj;
		}
	}

	
	@Override
	public JSONObject EligibleSupplierNotice(JSONObject data, HttpServletRequest request) {

		JSONObject ob = new JSONObject();
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			
			//Optional<SubCompany> sup = subCompanyRepo.findById(id);
//			Optional<SubCompany> com = subCompanyRepo.findById(Long.valueOf(us.getCompanyCode()));
			
     		List<Object[]> esup = appzTenderSubmissionsRepo.findByTenderNoAndConfirmEligibleSupplier(Long.valueOf(data.get("tenderID").toString()), "Yes");
     	
       
				
//			JSONArray ar = new JSONArray();
//			System.out.println("Size -------------"+dataList.size());
			 for (Object[] tenderSubmissions : esup) {
				 if(tenderSubmissions[0] instanceof TenderSubmissions && tenderSubmissions[1] instanceof SubCompany){
					 
					 TenderSubmissions ts = (TenderSubmissions) tenderSubmissions[0];
					 SubCompany subCompany = (SubCompany) tenderSubmissions[1];
					System.out.println(ts.getSubmission_id()+" -- "+subCompany.getSubCompanyID());
					
					 JSONObject object = new JSONObject();
			
					 object.put("tenderNo", ts.getTenderNo());
					 object.put("email",subCompany.getScemailadmin());
					 object.put("tenderNumber",ts.getTenderNo());
					
					
					
					String subject = "BidPro Tenders";
					String body = "Dear Vendor,"
							/*+ "\nYou have been introduced by " + com.get().getScname()*/
							+ "\n"
							+ "\nTender Number -: " + object.get("tenderNumber").toString()
							+"\n\nTenders are hereby called for the supply of,"
							+ "\nItem/Activity:-  "  
							+ "\nOther Details:-  " 
							+ "\nDescription/Specifications -: Please Refer Attached."
							+ "\n<p style=\"color: red\">*Note:- Quotation will be accepted only in LKR.</p>"
//							+ "\nURL -: https://bidpro.com:8443/supplierums/login"
//				     		+ "\n\nPlease note that this account only active for 10 days. Furthermore you can create your own admin account using this account"
							+ "\nEnjoy your new experience with BidPro Portal System."
                            +"\n"
                            +"\n"
                            +"\nThe Chairman,"
                            +"\nBidPro Committee,"
                            +"\nBidPro"
							+ "\n40, Lambert watta,"
							+ "\nMallawapitiya,"
							+ "\nKurunegala."
							+ "\nPlease do not reply to this email";
					

					 new Thread(new Runnable() {
							
						@Override
						public void run() {
						
							List<String> to = new ArrayList<String>();
							to.add(subCompany.getScemailadmin());
							Boolean result = common.sendEMail(to, subject, body);
							if(result) {
							 //save
								
								TenderSubmissions tenderSubmission = new TenderSubmissions();
								
								tenderSubmission.setEmailNotify("Sent");
								appzTenderSubmissionsRepo.save(tenderSubmission);
								
								System.out.println(10000);
								ob.put("code", "00");
								ob.put("msg", "Confirm");
								ob.put("clientID", Long.valueOf(data.get("tenderID").toString()));
								
							}else {
								ob.put("code", "01");
								ob.put("msg", "Failed");
								ob.put("clientID", Long.valueOf(data.get("tenderID").toString()));
							}
						}
					}).start();
					 // remove below code after email working properly.
					 TenderSubmissions tenderSubmission = new TenderSubmissions();
						
						tenderSubmission.setEmailNotify("Sent");
						appzTenderSubmissionsRepo.save(tenderSubmission);
						
						ob.put("code", "02");
						ob.put("msg", "Success");
						ob.put("clientID", Long.valueOf(data.get("tenderID").toString()));
						// end of block
					
				}else {
					ob.put("code", "03");
					ob.put("clientID", Long.valueOf(data.get("tenderID").toString()));
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return ob;
		}
		return ob;
	}


	
	@Override
	public List<TenderDetails> getSubmittedTender() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
	//	LocalDateTime NowDateAndTimee = LocalDateTime.now();
		try {
			List<TenderDetails> next = tenderDetailsRepo.getSubmittedTender();
			System.out.println(next);
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	
	@Override
	public List<SubCompany> getSupplierDetaiilsForTender(Long tenderID) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		try {
			List<SubCompany> next = appzTenderSubmissionsRepo.getSubmittedVender(tenderID);
			System.out.println(next);
			return next;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	
	@Override
	public JSONObject getMarksheetForSupllierAndTender(HttpServletRequest request, Long tenderID, Long venderID) {
		
			JSONObject data= new JSONObject();
			//venderID = (long) 139; 
			try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			
			Optional<EvaluationCommitee> next = evaluationCommiteeRepo.findBySubmitedUserIDAndSupplierIDAndTenderID(user.getUserid(),venderID, tenderID);
			
			Boolean isSubmited = false;
			if (next.isPresent()) {
				System.out.println("ttt "+next.get().getIsSubmited());
				isSubmited = next.get().getIsSubmited();
			}
			
			data.put("draw", request.getParameter("draw"));
			String sorting = "";
			
			switch (request.getParameter("order[0][column]")) {
			  case "0":
				sorting = "criteriaName";
			    break;
			  case "1":
				sorting = "criteria";
			    break;
			  case "2":
				sorting = "maximumMark";
			    break;
			}
		
			Page<EvaluationSheetCreate> evaSheet= null;
			if (request.getParameter("order[0][dir]").equals("asc")) {
			evaSheet = evaluationSheetCreateRepo.getMarkSheetForVendor(request.getParameter("search[value]"),tenderID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
			}
			else {
			evaSheet = evaluationSheetCreateRepo.getMarkSheetForVendor(request.getParameter("search[value]"),tenderID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
			}
			JSONArray ar =  new JSONArray();
			int i =0;
			for (EvaluationSheetCreate objects : evaSheet.getContent()) {
				
			//	if (objects[0] instanceof EvaluationSheetCreate && objects[1] instanceof EvaluationMarks) {
					EvaluationSheetCreate evaSheetCreate = objects;
				//	EvaluationMarks evaluationMark = (EvaluationMarks) objects[1];
					
			
			JSONObject ob = new JSONObject();
		
			ob.put("index", ++i);
			ob.put("evID", evaSheetCreate.getEvaluationmarksheetID());
			ob.put("criteria", evaSheetCreate.getCriteriaName());
			ob.put("maximumMark", evaSheetCreate.getCriteriamaxName());
//			ob.put("givenMark", evaSheetCreate.getScname());
			ob.put("tenderID", evaSheetCreate.getTenderId());
			ob.put("isSubmited", isSubmited);
			
			ar.add(ob);
			System.out.println("ob--- "+ob);
			//}
		}
			
			data.put("recordsTotal",evaSheet.getTotalElements());
			data.put("recordsFiltered",evaSheet.getTotalElements());
			data.put("data", ar); 
			return data;
		}	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public JSONObject getValidatedSupplierDetaiilsForTender(HttpServletRequest request, Long tenderID, Long venderID) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		
		try {
			Optional<EvaluationCommitee> next = evaluationCommiteeRepo.findBySubmitedUserIDAndSupplierIDAndTenderID(user.getUserid(),venderID, tenderID);
			
			JSONObject data = new JSONObject();
			if (next.isPresent()) {
				next.get().getIsSubmited();
				data.put("isSubmited", next.get().getIsSubmited());
			}else {
				data.put("isSubmited", "no record");
			}
			
			return data;
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}





}