package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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

import lk.supplierUMS.SupplierUMS_REST.JPARepo.GlobalStatusRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.PglogRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRoleOptionsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRoleRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractInvoiceHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.GlobalStatus;
import lk.supplierUMS.SupplierUMS_REST.entity.GlobalDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;
import lk.supplierUMS.SupplierUMS_REST.model.UserRoleDao;
import lk.supplierUMS.SupplierUMS_REST.service.UserRoleService;
import lk.supplierUMS.SupplierUMS_REST.service.UserService;

@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class UserRoleServiceImpl implements UserRoleService {
	
	@Autowired
	UserRoleDao userRollDao;
	
	@Autowired
	UserRoleRepo userRollRepo;
	
	@Autowired
	UserRoleOptionsRepo userRoleOption;
	
	@Autowired
	GlobalStatusRepo globalStatusRepo;
	
	@Autowired
	SubCompanyRepo subCompanyRepo;
	@Autowired
	PglogRepo pglogRapo;

	@Override
	public long addUserRole(UserRole userRole) {
		System.out.println("Add user Role-----addUserRole------- ");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		long nextID = userRollRepo.getMaxId()+1;
		System.out.println("nextID "+ nextID);
		
		System.out.println("user.getUserCompanyCode()----addUserRole------- "+user.getUserCompanyCode());
		userRole.setUserRoleID(userRollRepo.getMaxId() == 0 ? 1 : nextID);
		userRole.setStatusflag("N");
		userRole.setUserRoleStatus(1);
		userRole.setCompanyCode(Long.valueOf(user.getCompanyCode()));
		userRollDao.addUserRole(userRole);
		return userRole.getUserRoleID();
	}

	@Override
	public UserRole getUserRole(UserRole userRoll) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoleOptions> addUserRollOption(String id, List<UserRoleOptions> optionList) {
		// TODO Auto-generated method stub
		UserRole userRoll = userRollRepo.findById(new Long(id)).get();
//		System.out.println("------------------"+optionList.size());
		ArrayList<UserRoleOptions> data = new ArrayList<UserRoleOptions>();
		
		for(UserRoleOptions Op : optionList){
		 data.add(userRoleOption.findById(Op.getUserRoleOptionsId()).get());	
		}
		System.out.println("------------------"+data.size());
		userRoll.getOptions().addAll(data);
		userRollRepo.save(userRoll);
		
		return userRoll.getOptions();
	}

	@Override
	public Boolean getWetherUserRoleExists(String enteredValue) {
		// TODO Auto-generated method stub
		
		return userRollRepo.existsByUserRoleName(enteredValue);
	}

	@Override
	public UserRole getRoleOptionsByRoleID(String enteredValue) {
		// TODO Auto-generated method stub
		UserRole userRoleDetails = userRollRepo.findByUserRoleName(enteredValue);
		
		
		return userRoleDetails;
	}

	@Override
	public JSONObject getAlluserRoletoAuthorize(HttpServletRequest request,String para) {
	
			System.out.println("Inside ServiceImpl  getAlluserRoletoAuthorize----------"+para);
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
					sorting = "userRoleID";
				    break;
				  case "2":
					sorting = "userRoleName";
				    break;
				  case "3":
						sorting = "userRoleStatus";
					    break;
				  case "4":
						sorting = "createdAt";
					    break;
				
				
				}
				
			
				
				Page<UserRole> dataList = null;
				if(para.equals("all")){
					
					System.out.println("5678676786789667--- -------------------");
					
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =userRollRepo.getuserRoletoAuthorize( request.getParameter("search[value]"),userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =userRollRepo.getuserRoletoAuthorize(request.getParameter("search[value]"),userCompanyID, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}else{
			long para1 = Long.parseLong(para);
				System.out.println("para1----- -------------------"+para1);
				if (request.getParameter("order[0][dir]").equals("asc")) {
					dataList =userRollRepo.getuserRoletoAuthorizerolewise( request.getParameter("search[value]"),userCompanyID,para1, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.ASC, sorting)));
				}else {
					dataList =userRollRepo.getuserRoletoAuthorizerolewise(request.getParameter("search[value]"),userCompanyID,para1, PageRequest.of(Integer.parseInt(request.getParameter("start"))/Integer.parseInt(request.getParameter("length")),Integer.parseInt(request.getParameter("length")), Sort.by(Sort.Direction.DESC, sorting)));
				}
			}
				JSONArray ar = new JSONArray();

				 for (UserRole ur : dataList.getContent()) {
			
					
						
						 JSONObject header = new JSONObject();
						 GlobalStatus stsdec =globalStatusRepo.findById(new Long(ur.getUserRoleStatus().toString())).get();
						
							header.put("userRoleID", ur.getUserRoleID());
							header.put("userRoleName",ur.getUserRoleName());
							header.put("userRoleDescription", ur.getUserRoleDescription());
							header.put("userRoleStatus", stsdec.getGlobalStatusDescription());
							header.put("createdAt", ur.getCreatedAt());
							header.put("createdBy", ur.getCreatedBy());
					
						
							ar.add(header);
					

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
	public String approveUserRoleList(JSONObject data) {
		System.out.println("UserRoleServiceImpl.approveUserRoleList------------- "+data);
		String returnMsg="Nothing";
		try {
			
			Set<String> keys = data.keySet();
			
			
			for (String key : keys) {
				System.out.println(key);
				System.out.println(data.get(key));
				
		
				UserRole userrole = userRollRepo.getOne(Long.parseLong(key.toString()));
				Map address = ((Map)data.get(key)); 
				Iterator<Map.Entry> itr1 = address.entrySet().iterator(); 
		        while (itr1.hasNext()) { 
		            Map.Entry pair = itr1.next(); 
		            System.out.println("test-----------"+pair.getKey() + " : " + pair.getValue()); 
		            
		           
		            System.out.println("11--roleOfuserRole------------- "+pair.getKey().equals("roleOfuserRole"));
		            if(pair.getKey().equals("roleOfuserRole")){
		            	  System.out.println("Action-- pair.getValue()------------- "+ pair.getValue());
		            	  if(pair.getValue().equals("Accept")) {
		            		userrole.setUserRoleStatus(3);
		            		userrole.setStatusflag("Y");
		            	  }else {
		            		  
		            	  }
		            	
		            }
		       
		            if(pair.getKey().equals("statusReason")){
		             	System.out.println("22------------- ");
		            	userrole.setRoleApprovalReason(pair.getValue().toString());
		            }
		            
		        } 
		        userRollRepo.save(userrole);
		        returnMsg = "Success";
			}
			
			
			
			System.out.println("returnMsg------------- "+returnMsg);
			
		} catch (Exception e) {
			e.printStackTrace();
			 returnMsg = "Error";
		}
		
		return returnMsg;
	}

	@Override
	public List<UserRole> getUserRole(long roleid) {
		System.out.println("logged user role id------------- "+roleid);
		List<UserRole> list = new ArrayList<>();
		try {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			System.out.println("userCompanyID code----- "+userCompanyID);
		
			SubCompany subcompanyidentity =subCompanyRepo.getSubCompanyIdentity(userCompanyID);
			
			GlobalDetails pglob =	pglogRapo.findById("1").get();
			UserRole loggedUserRole = userRollRepo.findById(new Long(roleid)).get();
			UserRole requestedSupplerUserRole = userRollRepo.findById(new Long(pglob.getGbsupplieradminroleid())).get();
			UserRole requestedCompanyUserRole2 = userRollRepo.findById(new Long(pglob.getGbcompanyadminroleid())).get();
			Boolean isIdentical = subcompanyidentity.isIdentity();
			
			if(loggedUserRole.equals(userRollRepo.findById(new Long(pglob.getGbonetimadminroleid())).get()) && !isIdentical) {
				list.add(requestedSupplerUserRole);
				System.out.println("INSIDE__------------  "+requestedSupplerUserRole.getUserRoleDescription());
			}else if(loggedUserRole.equals(userRollRepo.findById(new Long(pglob.getGbonetimadminroleid())).get()) && isIdentical){
				list.add(requestedCompanyUserRole2);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public JSONObject getuserRoleforautho(String search, Long page) {
		System.out.println("getuserRoleforautho------------  "+search);
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			Long userCompanyID = new Long(user.getCompanyCode());
			JSONObject ob = new JSONObject();
			
			System.out.println("userCompanyID--------------------------------  "+userCompanyID);
			
			int pagevalue;
			if (page == null) {
				pagevalue = 0;
			}else {
				pagevalue = page.intValue();
			}
			Integer stst=1;
			String flag ="N";
			List<UserRole> userrolelist = userRollRepo.findByUserRoleNameLikeAndUserRoleStatusAndStatusflagAndCompanyCode(search+"%",stst,flag,userCompanyID,PageRequest.of(pagevalue, 30));
			long count = subCompanyRepo.countByScnameLikeAndIsPrivateFalse(search+"%");
			ob.put("total_count", count);
			if ((pagevalue*30)<count) {
				ob.put("incomplete_results", true);
			}else {
				ob.put("incomplete_results", false);
			}
			ArrayList data = new ArrayList();
			for (UserRole role : userrolelist) {
				
					JSONObject sup = new JSONObject();
					sup.put("name", role.getUserRoleName());
					sup.put("id", role.getUserRoleID());
					data.add(sup);
				
				
			}
			ob.put("items", data);
			
			
			return ob;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<UserRole> getUserRoleForSubComapny(long usersSubCompanyID) {
		// TODO Auto-generated method stub
		try {
			return userRollRepo.findByCompanyCode(usersSubCompanyID);
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		
	}

	

}
