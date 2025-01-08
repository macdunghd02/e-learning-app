package com.mdd.ela.dto.request.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.dto.model.Account;

/**
 * @author dungmd
 * @created 1/7/2025 6:27 下午
 * @project e-learning-app
 */
@JsonIgnoreProperties("password")
public class AccountResponse extends Account {
}
