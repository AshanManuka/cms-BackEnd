package com.crm.smartClientManager.dto.customer;

import com.crm.smartClientManager.dto.address.AddressReqDto;
import com.crm.smartClientManager.dto.address.AddressResDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerFullResDto {
    private Long id;
    private String name;
    private Date dob;
    private String nic;
    private List<String> mobileNumber;
    private List<CustomerBasicResDto> memberList;
    private List<AddressResDto> addressList;
}
