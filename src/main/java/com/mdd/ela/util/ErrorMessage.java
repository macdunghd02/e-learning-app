package com.mdd.ela.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author oem on 27/01/2024
 * @project Ela-backend
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private String error;

    private String errorDescription;

    private String errorMessage;
}