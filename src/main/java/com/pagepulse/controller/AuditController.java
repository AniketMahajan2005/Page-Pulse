package com.pagepulse.controller;

import com.pagepulse.model.AuditReport;
import com.pagepulse.model.ErrorResponse;
import com.pagepulse.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/audit")
    public ResponseEntity<?> auditUrl(@RequestParam(required = false) String url) {
        if (url == null || url.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Bad Request", "URL parameter is missing or blank", url));
        }
        AuditReport report = auditService.auditUrl(url);
        return ResponseEntity.ok(report);
    }
}
