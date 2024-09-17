package lk.supplierUMS.SupplierUMS_REST.service;

import org.json.simple.JSONArray;

import lk.supplierUMS.SupplierUMS_REST.entity.GlobalDetails;

public interface PglobService {
 public String saveglob(GlobalDetails pglob);
 public GlobalDetails getGlobal(String pg_id);
 
 public JSONArray getNotifications();
}
