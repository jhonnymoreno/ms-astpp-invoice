package br.com.easyprog.invoice.entrypoint.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/invoice")
public class InvoiceController {


    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String helloInvoice() {
        return "Hello invoice, its working";
    }
}
