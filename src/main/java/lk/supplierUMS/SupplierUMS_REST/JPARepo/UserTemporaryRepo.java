package lk.supplierUMS.SupplierUMS_REST.JPARepo;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.entity.User;
import lk.supplierUMS.SupplierUMS_REST.entity.UserRole;
import lk.supplierUMS.SupplierUMS_REST.entity.UserTemporary;

@Component
public interface UserTemporaryRepo extends JpaRepository<UserTemporary, Long> {
	
	
	
}
