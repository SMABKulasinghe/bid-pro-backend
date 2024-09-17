package lk.supplierUMS.SupplierUMS_REST.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lk.supplierUMS.SupplierUMS_REST.JPARepo.UserRepo;
import lk.supplierUMS.SupplierUMS_REST.config.CustomUserDetails;
import lk.supplierUMS.SupplierUMS_REST.entity.User;

@EnableJpaRepositories("lk.supplierUMS.SupplierUMS_REST.JPARepo")
@Component
public class customUserDetailService implements UserDetailsService {

	@Autowired
	UserRepo ur;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> optionalUser = ur.findByUserid(username);

		optionalUser.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

		return optionalUser.map(CustomUserDetails::new).get();
	}

}
