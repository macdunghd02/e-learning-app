package com.mdd.ela.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListQueryDto {

    private String keyword;

    private String searchBy;

    private String orderBy;

    private String orderTp = "ASC";

    private Integer pageSize = 10;

    private Integer pageNum = 1;

    private boolean isPaging = true;

    private Integer limit = 10;

    private Integer offset = 0;
}
