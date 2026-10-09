package com.smartfleet.Controller;

import com.smartfleet.Entity.Invoice;
import com.smartfleet.Service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @PostMapping
    public Invoice addInvoice(@RequestBody Invoice invoice) {
        return service.addInvoice(invoice);
    }

    @GetMapping
    public List<Invoice> getInvoices() {
        return service.getInvoices();
    }
}