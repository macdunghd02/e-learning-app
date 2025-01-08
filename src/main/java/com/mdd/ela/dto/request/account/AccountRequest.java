package com.mdd.ela.dto.request.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.dto.model.Account;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dungmd
 * @created 1/4/2025 10:36 上午
 * @project e-learning-app
 */

@JsonIgnoreProperties({"id","createUserId", "createTime", "modifyUserId", "modifyTime", "email", "password"})
public class AccountRequest extends Account {
}
