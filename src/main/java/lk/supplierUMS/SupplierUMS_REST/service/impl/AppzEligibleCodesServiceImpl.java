package lk.supplierUMS.SupplierUMS_REST.service.impl;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import lk.supplierUMS.SupplierUMS_REST.service.AppzEligibleCodesService;
@Service
@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
public class AppzEligibleCodesServiceImpl implements AppzEligibleCodesService {

}
