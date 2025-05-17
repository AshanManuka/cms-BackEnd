package com.crm.smartClientManager.dto.address;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AddressReqDto {

    private String lineOne;
    private String lineTwo;
    private Long cityId;
    private Long countryId;
}
