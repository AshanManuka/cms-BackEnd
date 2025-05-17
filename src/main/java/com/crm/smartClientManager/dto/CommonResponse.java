package com.crm.smartClientManager.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommonResponse<T> {
    private boolean success;
    private String message;
    private T body;

    public CommonResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CommonResponse(boolean success, T body) {
        this.success = success;
        this.body = body;
    }
}
