/*
 * (C)2022 Esen System Integration
 * The copyright to the computer program(s) herein is the property of Esen System Integration.
 * The program(s) may be used and/or copied only with the written permission of
 * Esen System Integration or in accordance with the terms and conditions stipulated
 * in the agreement/contract under which the program(s) have been supplied.
 */

package com.ozapp.foreignexchanger.data.model.conversionlist;

import lombok.Getter;

@Getter
public enum EnumConversionListColumn {
    ID("id"),
    DATE("date");

    private final String columnName;

    EnumConversionListColumn(String columnName) {
        this.columnName = columnName;
    }
}
