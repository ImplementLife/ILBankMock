package com.impllife.bankmock.controller.rest;

import com.impllife.bankmock.data.dto.BillingInfo;
import com.impllife.bankmock.data.dto.CreateBillingRequest;
import com.impllife.bankmock.services.interfaces.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class APIControllerImpl implements APIController {
    @Autowired
    private ExternalApiService apiService;

    @Override
    @PostMapping("/createBilling")
    public ResponseEntity<BillingInfo> createBilling(@RequestBody CreateBillingRequest request) {
        BillingInfo billing = apiService.createBilling(request);
        if (billing == null) ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(billing);
    }

    @Override
    @GetMapping("/getBillingInfo")
    public ResponseEntity<BillingInfo> getBillingInfo(@RequestParam UUID billingId) {
        throw new UnsupportedOperationException();
    }
}
