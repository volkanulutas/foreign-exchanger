/*
 * (C)2023 Esen System Integration
 * The copyright to the computer program(s) herein is the property of Esen System Integration.
 * The program(s) may be used and/or copied only with the written permission of
 * Esen System Integration or in accordance with the terms and conditions stipulated
 * in the agreement/contract under which the program(s) have been supplied.
 */

package com.ozapp.foreignexchanger.service.impl.conversionlist.search;

import com.ozapp.foreignexchanger.data.model.conversionlist.ConversionListSortModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GenericDetectionSpecification<T> implements Specification<T>, Serializable {
    private final List<SearchCriteria> list;

    private final ConversionListSortModel conversionListSort;

    public GenericDetectionSpecification(ConversionListSortModel conversionListSort) {
        this.conversionListSort = conversionListSort;
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                predicates.add(builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                predicates.add(builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                predicates.add(builder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.LIST_CONTAINS_DETECTION_SOURCE)) {
                /*
                List<String> criteriaList = ((List<Object>) criteria.getValue()).stream().map(String::valueOf).collect(Collectors.toList());
                CriteriaBuilder.In<EnumDetectionSource> inClause = builder.in(root.get(criteria.getKey()));
                for (String title : criteriaList) {
                    inClause.value(EnumDetectionSource.valueOf(title));
                }
                predicates.add(inClause);*/
            } else if (criteria.getOperation().equals(SearchOperation.LIST_CONTAINS_SIGNAL_TYPE)) {
                /*
                List<String> criteriaList = ((List<Object>) criteria.getValue()).stream().map(String::valueOf).collect(Collectors.toList());
                CriteriaBuilder.In<EnumSignalType> inClause = builder.in(root.get(criteria.getKey()));
                for (String title : criteriaList) {
                    inClause.value(EnumSignalType.getValue(title));
                }
                predicates.add(inClause);*/
            } else if (criteria.getOperation().equals(SearchOperation.IDENTIFICATION)) {
                predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
            }
        }
        List<Order> orders = new ArrayList<>();
        if (conversionListSort != null && conversionListSort.getType() != null && conversionListSort.getColumn() != null) {
            orders.add(conversionListSort.getType().build(root, builder, conversionListSort, cq));
        }
        cq.orderBy(orders);
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}