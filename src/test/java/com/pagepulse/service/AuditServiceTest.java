package com.pagepulse.service;

import com.pagepulse.model.AuditReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuditServiceTest {

    @Autowired
    private AuditService auditService;

    @Test
    public void testAuditValidUrl_HappyPath() {
        AuditReport report = auditService.auditUrl("https://example.com");
        
        assertNotNull(report);
        assertEquals(200, report.getHttpStatus());
        assertNotNull(report.getPageTitle());
        assertFalse(report.getPageTitle().isEmpty());
        assertTrue(report.getResponseTimeMs() > 0);
        assertTrue(report.getApproximateWordCount() > 0);
    }

    @Test
    public void testAuditInvalidUrl_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            auditService.auditUrl("not-a-valid-url");
        });
    }

    @Test
    public void testAuditUnreachableUrl_ThrowsException() {
        assertThrows(RuntimeException.class, () -> {
            auditService.auditUrl("https://thisdomaindoesnotexist12345.com");
        });
    }
}
