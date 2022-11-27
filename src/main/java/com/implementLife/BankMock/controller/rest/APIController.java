package com.implementLife.BankMock.controller.rest;

import com.implementLife.BankMock.data.dto.BillingInfo;
import com.implementLife.BankMock.data.dto.CreateBillingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
