package com.nmpc.kindergarten.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nmpc.kindergarten.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByFirstName(String firstName);

	Optional<User> findByFirstNameAndEmail(String firstName, String email);

	@Query(value = "SELECT play_center_id FROM users WHERE (play_center_id not like '%NMPCADMIN%' AND play_center_id not like '%NMPCTEACHER%') ORDER BY id desc LIMIT 1", nativeQuery = true)
	String findLastPlayCenterId();
	
	Optional<User> findByPlayCenterId(String playCenterId);
	
	@Query(value = "SELECT * FROM users  WHERE (play_center_id not like '%NMPCADMIN%' AND play_center_id not like '%NMPCTEACHER%') ORDER BY id", nativeQuery = true)
	List<User> findAllStudents();
	
	@Query(value = "SELECT * FROM users  WHERE (play_center_id not like '%NMPCADMIN%' AND play_center_id not like '%NMPCSTUDENT%') ORDER BY id", nativeQuery = true)
	List<User> findAllTeachers();

}
