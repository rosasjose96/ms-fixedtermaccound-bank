package com.bootcamp.fixedtermaccound.models.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {


    private String name;
    private String customerIdentityType;
    private String customerIdentityNumber;
}
