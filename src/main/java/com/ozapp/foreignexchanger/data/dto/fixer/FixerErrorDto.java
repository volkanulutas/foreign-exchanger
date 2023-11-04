package com.ozapp.foreignexchanger.data.dto.fixer;

import lombok.Data;

@Data
public class FixerErrorDto {
    private int code;

    private String type;

    private String info;

    @Override
    public String toString() {
        String detail = ", Detail:'" + info + '\'';
        return "Fixer API Error:" + "Code:" + code + ", Type:'" + type + '\'' + info != null ? detail : "";
    }
}
