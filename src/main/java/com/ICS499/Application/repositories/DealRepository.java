// language: java
package com.ICS499.Application.repositories;

import com.ICS499.Application.entities.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {
}