package com.ozapp.foreignexchanger.repository;

import com.ozapp.foreignexchanger.data.entity.ConversionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConversionRepository extends JpaRepository<ConversionEntity, Long>, JpaSpecificationExecutor<ConversionEntity> {}
