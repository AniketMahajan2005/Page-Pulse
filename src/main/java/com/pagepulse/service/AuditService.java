package com.pagepulse.service;

import com.pagepulse.model.AuditReport;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.time.Instant;

@Service
public class AuditService {

    public AuditReport auditUrl(String urlString) {
        if (urlString == null || (!urlString.startsWith("http://") && !urlString.startsWith("https://"))) {
            throw new IllegalArgumentException("Invalid URL format: URL must start with http:// or https://");
        }
        
        try {
            new URL(urlString);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL format: " + e.getMessage());
        }

        long startTime = System.currentTimeMillis();
        AuditReport report = new AuditReport();
        report.setUrl(urlString);
        
        try {
            Connection connection = Jsoup.connect(urlString).timeout(10000);
            Connection.Response response = connection.execute();
            
            if (response.contentType() == null || !response.contentType().contains("text/html")) {
                throw new RuntimeException("URL does not point to an HTML page");
            }
            
            Document doc = response.parse();
            long responseTimeMs = System.currentTimeMillis() - startTime;
            
            report.setHttpStatus(response.statusCode());
            report.setResponseTimeMs(responseTimeMs);
            report.setPageTitle(doc.title());
            
            Elements metaTags = doc.select("meta[name=description]");
            if (!metaTags.isEmpty()) {
                report.setMetaDescription(metaTags.attr("content"));
            } else {
                report.setMetaDescription("");
            }
            
            report.setH1Count(doc.select("h1").size());
            
            Elements images = doc.select("img");
            report.setTotalImages(images.size());
            
            int missingAltCount = 0;
            for (org.jsoup.nodes.Element img : images) {
                String alt = img.attr("alt");
                if (alt == null || alt.trim().isEmpty()) {
                    missingAltCount++;
                }
            }
            report.setImagesMissingAlt(missingAltCount);
            
            if (doc.body() != null) {
                String text = doc.body().text();
                if (!text.isEmpty()) {
                    report.setApproximateWordCount(text.split("\\s+").length);
                } else {
                    report.setApproximateWordCount(0);
                }
            } else {
                report.setApproximateWordCount(0);
            }
            
            report.setAuditedAt(Instant.now().toString());
            
            return report;
            
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL format: " + e.getMessage());
        } catch (SocketTimeoutException e) {
            throw new RuntimeException("Request timed out after 10 seconds");
        } catch (UnsupportedMimeTypeException e) {
            throw new RuntimeException("URL does not point to an HTML page");
        } catch (HttpStatusException e) {
            long responseTimeMs = System.currentTimeMillis() - startTime;
            report.setHttpStatus(e.getStatusCode());
            report.setResponseTimeMs(responseTimeMs);
            report.setPageTitle("");
            report.setMetaDescription("");
            report.setAuditedAt(Instant.now().toString());
            return report;
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch URL: " + e.getMessage());
        }
    }
}
