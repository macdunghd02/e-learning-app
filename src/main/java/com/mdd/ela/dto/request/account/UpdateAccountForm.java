package com.mdd.ela.dto.request.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.dto.model.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 1/4/2025 10:36 上午
 * @project e-learning-app
 */


@JsonIgnoreProperties({"id","create_user_id", "create_time", "modify_user_id", "modify_time", "email", "password"})
public class UpdateAccountForm extends Account {
}
