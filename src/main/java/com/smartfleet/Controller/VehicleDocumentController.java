package com.smartfleet.Controller;

import com.smartfleet.Entity.VehicleDocument;
import com.smartfleet.Service.VehicleDocumentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin
public class VehicleDocumentController {

    private final VehicleDocumentService service;

    public VehicleDocumentController(VehicleDocumentService service) {
        this.service = service;
    }

    @PostMapping
    public VehicleDocument addDocument(@RequestBody VehicleDocument document) {
        return service.addDocument(document);
    }

    @GetMapping
    public List<VehicleDocument> getDocuments() {
        return service.getDocuments();
    }
}