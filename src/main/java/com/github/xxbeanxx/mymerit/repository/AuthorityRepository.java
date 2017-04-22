package com.github.xxbeanxx.mymerit.repository;

import com.github.xxbeanxx.mymerit.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
