package com.impllife.bankmock.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBillingRequest {
    @NotBlank(message = "Can't be blank")
    private String units;
    @NotBlank(message = "Can't be blank")
    private UUID accessToken;
    @NotBlank(message = "Can't be blank")
    private UUID appId;
    private Date dateCreate;
    private String paymentDescription;
    private String completePaymentLink;
}
