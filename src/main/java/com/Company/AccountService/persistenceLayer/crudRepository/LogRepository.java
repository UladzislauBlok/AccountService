package com.Company.AccountService.persistenceLayer.crudRepository;

import com.Company.AccountService.businessLayer.logging.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Integer> {
}
