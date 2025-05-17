package com.crm.smartClientManager.dto.customer;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerBasicResDto {
    private Long id;
    private String name;
    private String nic;
    private Date dob;
}
