package com.mdd.ela.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.model.entity.Account;

/**
 * @author dungmd
 * @created 1/4/2025 10:36 上午
 * @project e-learning-app
 */

@JsonIgnoreProperties({"id","createUserId", "createTime", "modifyUserId", "modifyTime", "email", "password"})
public class AccountRequest extends Account {
}
