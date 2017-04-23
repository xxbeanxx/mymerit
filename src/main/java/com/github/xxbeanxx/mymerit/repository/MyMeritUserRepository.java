package com.github.xxbeanxx.mymerit.repository;

import com.github.xxbeanxx.mymerit.domain.MyMeritUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MyMeritUser entity.
 */
@SuppressWarnings("unused")
public interface MyMeritUserRepository extends JpaRepository<MyMeritUser,Long> {

}
