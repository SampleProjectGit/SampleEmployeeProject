package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.EmployeeEntity;

/**
 * This is repository class employee table.
 */

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
	/* @Query("select e from EmployeePojo e where e.status='active'") */
	public List<EmployeeEntity> findByStatus(String status);

}