package com.crm.smartClientManager.dto.address;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AddressUpdateReqDto {
    private Long id;
    private String lineOne;
    private String lineTwo;
    private Long cityId;
    private String countryId;
}
