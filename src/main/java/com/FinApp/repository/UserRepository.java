package com.FinApp.repository;

import com.FinApp.dto.UserDTO;
import com.FinApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    UserDetails findByEmail(String email);
    @Query("SELECT new com.FinApp.dto.UserDTO(u.username, u.email) FROM users u " +
            "JOIN u.role r " +
            "WHERE r.name = :roleName " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM users u2 " +
            "    JOIN u2.role r2 " +
            "    WHERE u2.id = u.id " +
            "    AND (" +
            "        (r2.name = 'BOSS' AND :roleName IN ('MANAGER', 'SELLER')) OR " +
            "        (r2.name = 'MANAGER' AND :roleName = 'SELLER')" +
            "    )" +
            ")")
    List<UserDTO> findUsersByRoleName(@Param("roleName") String roleName);
}