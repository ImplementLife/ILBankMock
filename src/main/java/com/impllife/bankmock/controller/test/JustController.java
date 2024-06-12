package com.impllife.bankmock.controller.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.impllife.bankmock.data.dto.NotifyMessage;
import com.impllife.bankmock.services.interfaces.ImageCodeGenerator;
import com.impllife.bankmock.services.interfaces.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Controller
public class JustController {
    @ResponseBody
    @GetMapping("/console")
    public String hi(HttpServletRequest request) {
        log.info("\t/console is called from " + request.getRemoteAddr());

        return "Hello " + request.getRemoteAddr() + " ))";
    }

    @Autowired
    private ApplicationContext context;

    @ResponseBody
    @PostMapping("/test")
    public String test(@RequestBody NotifyMessage msg, HttpServletRequest request) {
        log.warn("test -> " + msg.toString());
        return "ok";
    }
    @ResponseBody
    @GetMapping("/test2")
    public String test2() {
        ObjectMapper mapper = new ObjectMapper();
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String urlSendResult = "http://127.0.0.1/test";
        try {
            NotifyMessage msg = new NotifyMessage();
            msg.setId("1");
            msg.setAccToken("2");
            msg.setStatus("3");
            String msgAsJson = mapper.writeValueAsString(msg);
            template.postForLocation(urlSendResult, new HttpEntity<>(msgAsJson, headers));
        } catch (JsonProcessingException e) {
            log.error("", e);
        }
        return "ok";
    }



    @Autowired
    private ImageCodeGenerator imageCodeGenerator;
    @Autowired
    private MailService mailService;

    @ResponseBody
    @GetMapping("/mailTst")
    public String mailTst(@RequestParam String email) {
        mailService.sendMessage(email, "tst mail ");
        return "ok";
    }

    @ResponseBody
    @GetMapping(value = "/getCode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> barbecueEAN13Barcode(@RequestParam String text) throws IOException {
        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        ImageIO.write(imageCodeGenerator.generateQRCodeImage(text), "png", byteArrayStream);
        return ResponseEntity.ok(byteArrayStream.toByteArray());
    }
}
