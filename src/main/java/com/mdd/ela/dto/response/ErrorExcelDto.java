package com.mdd.ela.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorExcelDto {

    @JsonProperty("line")
    private int line;

    @JsonProperty("sheet")
    private int sheet;

    @JsonProperty("error_list")
    private List<String> errorList;

    public void addError(String errorDescription) {
        if (errorList == null) {
            errorList = new ArrayList<>();
        }

        errorList.add(errorDescription);
    }

    @JsonIgnore
    public boolean isError() {
        return errorList != null && !errorList.isEmpty();
    }
}
