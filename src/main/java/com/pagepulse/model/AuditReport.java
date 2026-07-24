package com.pagepulse.model;

public class AuditReport {
    private String url;
    private int httpStatus;
    private long responseTimeMs;
    private String pageTitle;
    private String metaDescription;
    private int h1Count;
    private int imagesMissingAlt;
    private int totalImages;
    private int approximateWordCount;
    private String auditedAt;

    public AuditReport() {
    }

    public AuditReport(String url, int httpStatus, long responseTimeMs, String pageTitle, String metaDescription,
                       int h1Count, int imagesMissingAlt, int totalImages, int approximateWordCount, String auditedAt) {
        this.url = url;
        this.httpStatus = httpStatus;
        this.responseTimeMs = responseTimeMs;
        this.pageTitle = pageTitle;
        this.metaDescription = metaDescription;
        this.h1Count = h1Count;
        this.imagesMissingAlt = imagesMissingAlt;
        this.totalImages = totalImages;
        this.approximateWordCount = approximateWordCount;
        this.auditedAt = auditedAt;
    }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public int getHttpStatus() { return httpStatus; }
    public void setHttpStatus(int httpStatus) { this.httpStatus = httpStatus; }

    public long getResponseTimeMs() { return responseTimeMs; }
    public void setResponseTimeMs(long responseTimeMs) { this.responseTimeMs = responseTimeMs; }

    public String getPageTitle() { return pageTitle; }
    public void setPageTitle(String pageTitle) { this.pageTitle = pageTitle; }

    public String getMetaDescription() { return metaDescription; }
    public void setMetaDescription(String metaDescription) { this.metaDescription = metaDescription; }

    public int getH1Count() { return h1Count; }
    public void setH1Count(int h1Count) { this.h1Count = h1Count; }

    public int getImagesMissingAlt() { return imagesMissingAlt; }
    public void setImagesMissingAlt(int imagesMissingAlt) { this.imagesMissingAlt = imagesMissingAlt; }

    public int getTotalImages() { return totalImages; }
    public void setTotalImages(int totalImages) { this.totalImages = totalImages; }

    public int getApproximateWordCount() { return approximateWordCount; }
    public void setApproximateWordCount(int approximateWordCount) { this.approximateWordCount = approximateWordCount; }

    public String getAuditedAt() { return auditedAt; }
    public void setAuditedAt(String auditedAt) { this.auditedAt = auditedAt; }
}
