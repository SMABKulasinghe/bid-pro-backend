package lk.supplierUMS.SupplierUMS_REST.model;

import lk.supplierUMS.SupplierUMS_REST.entity.GlobalDetails;

public interface PglobDao {
	public String saveGlob(GlobalDetails pglob);
	public GlobalDetails getGlobal(String pg_id);
}
