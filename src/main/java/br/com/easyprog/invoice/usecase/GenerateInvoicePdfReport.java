package br.com.easyprog.invoice.usecase;

import br.com.easyprog.invoice.domain.Invoice;
import com.lowagie.text.DocumentException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

@Component
public class GenerateInvoicePdfReport {

    final GetInvoice getInvoice;
    private static final String PDF_RESOURCES = "/pdf-resources/";
    final SpringTemplateEngine templateEngine;

    public GenerateInvoicePdfReport(final GetInvoice getInvoice, SpringTemplateEngine templateEngine) {
        this.getInvoice = getInvoice;
        this.templateEngine = templateEngine;
    }

    public File execute(String accountNumber, LocalDateTime startDate, LocalDateTime endDate) throws IOException, DocumentException {
        final Invoice invoice = this.getInvoice.execute(accountNumber, startDate, endDate);

        Context context = getContext(invoice);
        String html = loadAndFillTemplate(context);
        return renderPdf(html);
    }

    private File renderPdf(String html) throws IOException, DocumentException {
        File file = File.createTempFile("invoice", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }

    private Context getContext(Invoice invoice) {
        Context context = new Context();
        context.setVariable("invoice", invoice);
        return context;
    }

    private String loadAndFillTemplate(Context context) {
        return templateEngine.process("pdf_invoice", context);
    }

}
