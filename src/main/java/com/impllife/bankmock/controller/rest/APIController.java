package com.impllife.bankmock.controller.rest;

import com.impllife.bankmock.data.dto.BillingInfo;
import com.impllife.bankmock.data.dto.CreateBillingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//@SecurityRequirement(name = "Session cookie", scopes = {"JSESSIONID"})
@ApiResponse(
    responseCode = "403",
    content = {@Content(schema = @Schema(hidden = true))}
)

@RequestMapping("/api")
public interface APIController {
    @Operation(responses = {
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "403")
    })
    @PostMapping("createBilling")
    @ResponseStatus(HttpStatus.CREATED)
    BillingInfo createBilling(@RequestBody CreateBillingRequest request);

    @GetMapping("getBillingInfo")
    BillingInfo getBillingInfo(@RequestParam UUID billingId);
}
