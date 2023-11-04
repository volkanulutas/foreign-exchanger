/*
 * (C)2023 Esen System Integration
 * The copyright to the computer program(s) herein is the property of Esen System Integration.
 * The program(s) may be used and/or copied only with the written permission of
 * Esen System Integration or in accordance with the terms and conditions stipulated
 * in the agreement/contract under which the program(s) have been supplied.
 */

package com.ozapp.foreignexchanger.data.model.conversionlist;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

public enum EnumConversionListSortType {
    ASC {
        public <T> Order build(Root<T> root, CriteriaBuilder cb, ConversionListSortModel request, CriteriaQuery criteriaQuery) {
            return cb.asc(root.get(request.getColumn().getColumnName().toString()));
        }
    },
    DESC {
        public <T> Order build(Root<T> root, CriteriaBuilder cb, ConversionListSortModel request, CriteriaQuery criteriaQuery) {
            return cb.desc(root.get(request.getColumn().getColumnName().toString()));
        }
    };

    public abstract <T> Order build(Root<T> root, CriteriaBuilder cb, ConversionListSortModel request, CriteriaQuery criteriaQuery);
}