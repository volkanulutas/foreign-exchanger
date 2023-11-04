package com.ozapp.foreignexchanger.repository;

import com.ozapp.foreignexchanger.data.entity.DbSourceExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbSourceExchangeRateRepository extends JpaRepository<DbSourceExchangeRateEntity, String> {}