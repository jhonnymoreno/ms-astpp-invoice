package br.com.easyprog.invoice.entrypoint.rest;

import br.com.easyprog.invoice.domain.Invoice;
import br.com.easyprog.invoice.domain.types.Regex;
import br.com.easyprog.invoice.usecase.GetInvoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/invoice")
public class InvoiceController {

    GetInvoice getinvoice;

    public InvoiceController(final GetInvoice getInvoice) {
        this.getinvoice = getInvoice;
    }

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String helloInvoice() {
        return "Hello invoice, its working";
    }


    @GetMapping(value = "/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Invoice getInvoice(@PathVariable
                              @Pattern(regexp = Regex.ALPHANUMERIC)
                              @Size(min = 1, max = 12) final String accountNumber,
                              @RequestParam final String startDate,
                              @RequestParam final String endDate) {
        return this.getinvoice.execute(accountNumber, parseDate(startDate, true), parseDate(endDate, false));
    }

    private LocalDateTime parseDate(final String date, boolean isStartDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        final String time = (isStartDate ? " 00:00:00" : " 23:59:59");
        return LocalDateTime.parse(date + time, formatter);
    }
}
