package com.cloudage.membercenter.repository;

import org.springframework.stereotype.Repository;

import com.cloudage.membercenter.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface IUserRepository extends PagingAndSortingRepository<User, Integer>{
	@Query("from User user where user.account = ?1")
	User findByAccount(String account);

	@Query("from User user where user.id = ?1")
	User findById(Integer uid);

	@Query("from User user where user.email = ?1")
	User findByEmail(String email);





}
