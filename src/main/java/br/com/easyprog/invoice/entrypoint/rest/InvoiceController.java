package br.com.easyprog.invoice.entrypoint.rest;

import br.com.easyprog.invoice.domain.Invoice;
import br.com.easyprog.invoice.domain.types.Regex;
import br.com.easyprog.invoice.usecase.GenerateInvoicePdfReport;
import br.com.easyprog.invoice.usecase.GetInvoice;
import com.lowagie.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/invoice")
public class InvoiceController {

    final GetInvoice getinvoice;
    final GenerateInvoicePdfReport generateInvoicePdfReport;

    public InvoiceController(final GetInvoice getInvoice, GenerateInvoicePdfReport generateInvoicePdfReport) {
        this.getinvoice = getInvoice;
        this.generateInvoicePdfReport = generateInvoicePdfReport;
    }

    @GetMapping(value = "/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Invoice getInvoice(@PathVariable
                              @Pattern(regexp = Regex.ALPHANUMERIC)
                              @Size(min = 1, max = 12) final String accountNumber,
                              @RequestParam final String startDate,
                              @RequestParam final String endDate) {
        return this.getinvoice.execute(accountNumber, parseDate(startDate, true), parseDate(endDate, false));
    }

    @GetMapping(value = "/{accountNumber}/generateInvoice", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Resource> downloadPDFResource(@PathVariable
                                    @Pattern(regexp = Regex.ALPHANUMERIC)
                                    @Size(min = 1, max = 12) final String accountNumber,
                                                        @RequestParam final String startDate,
                                                        @RequestParam final String endDate) throws IOException, DocumentException {

            final File invoiceFile = this.generateInvoicePdfReport
                    .execute(accountNumber, parseDate(startDate, true), parseDate(endDate, false));
            final Path file = Paths.get(invoiceFile.getAbsolutePath());

                ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file));

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("Content-Disposition",
                        "attachment; filename=" + file.getFileName());

                return ResponseEntity.ok()
                        .headers(responseHeaders)
                        .contentLength(invoiceFile.length())
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
    }

    private LocalDateTime parseDate(final String date, boolean isStartDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        final String time = (isStartDate ? " 00:00:00" : " 23:59:59");
        return LocalDateTime.parse(date + time, formatter);
    }
}
