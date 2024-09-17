package lk.supplierUMS.SupplierUMS_REST.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.hibernate.sql.ordering.antlr.GeneratedOrderByFragmentParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.JSONPObject;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.CompanyDetailsRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.CompanySupplierMappingRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.ContractInvoiceHeaderRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.PglogRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.SubCompanyRepo;
import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanyDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.CompanySupplierMapping;
import lk.supplierUMS.SupplierUMS_REST.entity.ContractInvoiceHeader;
import lk.supplierUMS.SupplierUMS_REST.entity.GlobalDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.SubCompany;
import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRoleOptions;
import lk.supplierUMS.SupplierUMS_REST.model.PglobDao;
import lk.supplierUMS.SupplierUMS_REST.service.PglobService;


@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class PglobServiceImpl implements PglobService{
	@Autowired
	PglobDao pglobDao;
	
	@Autowired
	PglogRepo pglogRapo;
	
	@Autowired
	CompanySupplierMappingRepo companyMappingRepo;
	
	@Autowired
	SubCompanyRepo subCompanyRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	ContractInvoiceHeaderRepo contractInvoiceHeaderRepo;
	
	@Override
	public String saveglob(GlobalDetails pglob) {


		String pid="";
		try {
			GlobalDetails p_glob=pglogRapo.save(pglob);
			pid=pglob.getIndexNo();
			} catch (Exception e) {
				pid="not";
				e.printStackTrace();
			}
			return pid;
	}

	@Override
	public GlobalDetails getGlobal(String pg_id) {
//		Pglob pg_data = pglogRapo.findByIndexNo(pg_id);
		System.out.println("pg id -------------------------> "+pg_id);
		
		
		
		
		
		GlobalDetails pg_data = pglogRapo.findById(pg_id).get();
		System.out.println("Inside od=f service---->"+pg_data.getIndexNo());
		return pg_data;
	}

	@Override
	public JSONArray getNotifications() {
		try {
			JSONArray arr = new JSONArray();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) authentication.getPrincipal();
			 int check = userRepo.checkAuth(user.getUserid(), "Approve Partnership");
			if (check == 1) {
				List<CompanySupplierMapping> data = companyMappingRepo.findBySupplierIDAndStatus(new Long(user.getCompanyCode()), "P");
				
				for (CompanySupplierMapping companySupplierMapping : data) {
					Optional<SubCompany> company = subCompanyRepo.findById(companySupplierMapping.getCompanyID());
					
					if (company.isPresent()) {
						JSONObject ob = new JSONObject();
						ob.put("type", "partnership");
						ob.put("status", companySupplierMapping.getStatus());
						ob.put("company", company.get().getScname());
						ob.put("mappingId", companySupplierMapping.getMappingID());
						
						arr.add(ob);
					}
				}
			}
			
			if (userRepo.checkAuth(user.getUserid(), "Invoice Authorization") == 1) {
				List<Object[]> data = contractInvoiceHeaderRepo.findpendingInvoices(Long.valueOf(user.getCompanyCode()));
				for (Object[] objects : data) {
					if (objects[0] instanceof ContractInvoiceHeader && objects[1] instanceof CompanySupplierMapping) {
						ContractInvoiceHeader ih= (ContractInvoiceHeader) objects[0];
						CompanySupplierMapping map = (CompanySupplierMapping) objects[1];
						Optional<SubCompany> company = subCompanyRepo.findById(map.getSupplierID());
						JSONObject ob = new JSONObject();
						ob.put("type", "Invoice Raised");
						ob.put("status", ih.getStatus());
						ob.put("company", company.get().getScname());
						ob.put("mappingId", map.getMappingID());
						arr.add(ob);
					}
				}
			}
			
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
