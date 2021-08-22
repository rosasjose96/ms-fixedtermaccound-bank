package com.bootcamp.fixedtermaccound.models.dto;

import lombok.*;


/**
 * The type Customer dto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

    private String name;
    private String code;
    private String customerIdentityNumber;
}
