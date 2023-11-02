/*
 * (C)2022 Esen System Integration
 * The copyright to the computer program(s) herein is the property of Esen System Integration.
 * The program(s) may be used and/or copied only with the written permission of
 * Esen System Integration or in accordance with the terms and conditions stipulated
 * in the agreement/contract under which the program(s) have been supplied.
 */

package com.ozapp.foreignexchanger.service.impl.conversionlist.search;

public enum SearchOperation {
    GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_EQUAL,
    LESS_THAN_EQUAL,
    NOT_EQUAL,
    EQUAL,
    MATCH,
    MATCH_END,
    LIST_CONTAINS_DETECTION_SOURCE,
    IDENTIFICATION,
    LIST_CONTAINS_SIGNAL_TYPE
}