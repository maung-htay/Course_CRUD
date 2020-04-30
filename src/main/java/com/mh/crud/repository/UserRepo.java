package com.mh.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mh.crud.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	@Query(value="Select * from user where email=?1" , nativeQuery=true)
	User checkEmail(String email);

	@Query(value="Select * from user where email=?1 and password=?2" , nativeQuery=true)
	User checkLogin(String email, String password);
	
	
}
