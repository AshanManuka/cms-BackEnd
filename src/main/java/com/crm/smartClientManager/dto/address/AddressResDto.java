package com.crm.smartClientManager.dto.address;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AddressResDto {
    private Long id;
    private String lineOne;
    private String lineTwo;
    private String cityName;
    private String countryName;
}
