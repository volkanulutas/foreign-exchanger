package com.ozapp.foreignexchanger.data.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "DbSourceExchangeRate")
@Table(name = "DbSourceExchangeRate")
public class DbSourceExchangeRateEntity {
    @Id
    @Column
    private String base;

    @Lob
    private String exchangeRate;

    public Map<String, Double> getJsonDataAsMap() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(exchangeRate, new TypeReference<Map<String, Double>>() {});
        } catch (JsonProcessingException e) {
            log.error("Error is occurred while getting exchange data from Db. Detail: ", e);
            return new HashMap<>();
        }
    }

    public void setJsonDataFromMap(Map<String, Double> data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            exchangeRate = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("Error is occurred while getting exchange data from Db. Detail: ", e);
            exchangeRate = null;
        }
    }
}
