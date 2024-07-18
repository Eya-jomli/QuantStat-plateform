package com.example.msusermanagement.Repositories;

//////import com.bezkoder.springjwt.models.ERole;
/////////////import com.bezkoder.springjwt.models.Role;
import com.example.msusermanagement.Entities.ERole;
import com.example.msusermanagement.Entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
