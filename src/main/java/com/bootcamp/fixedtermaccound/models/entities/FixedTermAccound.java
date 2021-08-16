package com.bootcamp.fixedtermaccound.models.entities;

import com.bootcamp.fixedtermaccound.models.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "fixedTermAccound")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FixedTermAccound {

    @Id
    private String id;

    private String typeOfAccount;

    @NotNull
    private String customerIdentityNumber;

    @NotNull
    private String accountNumber;

    private double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate = LocalDateTime.now();

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date operationDate;

    @NotNull
    private CustomerDTO customer;

    private int maxLimitMovementPerMonth;

    private int movementPerMonth;

}
