package lk.supplierUMS.SupplierUMS_REST.model.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.PglogRepo;
import lk.supplierUMS.SupplierUMS_REST.entity.GlobalDetails;
import lk.supplierUMS.SupplierUMS_REST.model.PglobDao;
@Component
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class PglogDaoImpl implements PglobDao{

	@Autowired
	PglogRepo pglogRapo;
	
	@Override
	public String saveGlob(GlobalDetails pglob) {
		String pid="";
		try {
		pglogRapo.save(pglob);
//		pid=p_glob.getIndexNo();
		} catch (Exception e) {
//			pid="not";
			e.printStackTrace();
		}
		return pid;
	}

	@Override
	public GlobalDetails getGlobal(String pg_id) {
		

		GlobalDetails pgdata=pglogRapo.findByIndexNo(pg_id);
		return pgdata;
	}
}
