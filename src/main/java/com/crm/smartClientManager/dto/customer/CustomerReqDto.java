package com.crm.smartClientManager.dto.customer;

import com.crm.smartClientManager.dto.address.AddressReqDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerReqDto {
    private String name;
    private Date dob;
    private String nic;
    private List<String> mobileNumber;
    private List<Long> memberList;
    private List<AddressReqDto> addressList;

}
