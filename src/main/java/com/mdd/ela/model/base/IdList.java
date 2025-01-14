package com.mdd.ela.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

/**
 * @author oem on 05/03/2024
 * @project mci-backend
 */
@Getter
@Setter
public class IdList {
    @JsonProperty("id_list")
    private List<BigInteger> ids;
}
