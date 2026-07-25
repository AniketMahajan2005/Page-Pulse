# 🔍 Page Pulse — URL Auditing Tool

A web application that audits any URL and generates a detailed report covering HTTP status, performance, SEO metadata, accessibility, and content metrics.

## 🌐 Live Demo

Frontend:
https://page-pulse-frontend-one.vercel.app/

Backend API:
https://page-pulse-frtm.onrender.com

## 🔗 Repository

GitHub: https://github.com/AniketMahajan2005/Page-Pulse-frontend
GitHub: https://github.com/AniketMahajan2005/Page-Pulse

---

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Setup & Running Locally](#setup--running-locally)
- [API Contract](#api-contract)
- [Running Tests](#running-tests)
- [Design Decisions](#design-decisions)
- [Project Structure](#project-structure)

---

## ✨ Features

- **URL Auditing**: Enter any URL and get a comprehensive health report
- **SEO Analysis**: Page title, meta description, H1 tag count
- **Accessibility Check**: Detects images missing `alt` attributes
- **Performance Metrics**: Response time measurement
- **Content Analysis**: Approximate word count
- **Error Handling**: Graceful handling of invalid URLs, timeouts, and non-HTML responses
- **Responsive Design**: Works on desktop and mobile devices

---

## 🛠️ Tech Stack

| Layer    | Technology              |
|----------|-------------------------|
| Backend  | Java 21, Spring Boot 3.3 |
| HTML Parser | Jsoup 1.17.2          |
| Frontend | React 18, Vite 5        |
| HTTP Client | Axios                |
| Styling  | Vanilla CSS (Dark Theme) |

---

## 🚀 Deployment

Frontend: Vercel

Backend: Render

## 🚀 Setup & Running Locally

### Prerequisites

- **Java 21** (JDK) — [Download](https://adoptium.net/)
- **Maven 3.9+** — [Download](https://maven.apache.org/download.cgi) (or use the project's `mvnw` wrapper)
- **Node.js 18+** — [Download](https://nodejs.org/)



### Quick Test

1. Open https://page-pulse-frontend-one.vercel.app/
2. Enter `https://example.com` and click **Audit URL**

---

## 📡 API Contract

### `GET /api/audit`

Audits a given URL and returns a JSON report.

#### Request

| Parameter | Type   | Required | Description                |
|-----------|--------|----------|----------------------------|
| `url`     | string | Yes      | The URL to audit (must start with `http://` or `https://`) |

**Example**:
```
GET https://page-pulse-frtm.onrender.com/api/audit?url=https://example.com
```

#### Success Response (200 OK)

```json
{
  "url": "https://example.com",
  "httpStatus": 200,
  "responseTimeMs": 342,
  "pageTitle": "Example Domain",
  "metaDescription": "",
  "h1Count": 1,
  "imagesMissingAlt": 0,
  "totalImages": 0,
  "approximateWordCount": 28,
  "auditedAt": "2025-07-24T14:10:00.123Z"
}
```

#### Error Response (400 Bad Request — Invalid URL)

```json
{
  "error": "Bad Request",
  "message": "Invalid URL format: URL must start with http:// or https://",
  "url": "not-a-url"
}
```

#### Error Response (408 Request Timeout)

```json
{
  "error": "Request Timeout",
  "message": "Request timed out after 10 seconds",
  "url": "https://very-slow-site.com"
}
```

#### Error Response (415 Unsupported Media Type — Non-HTML)

```json
{
  "error": "Unsupported Media Type",
  "message": "URL does not point to an HTML page",
  "url": "https://example.com/file.pdf"
}
```

#### Error Response (502 Bad Gateway — Fetch Failed)

```json
{
  "error": "Bad Gateway",
  "message": "Failed to fetch URL: Connection refused",
  "url": "https://unreachable-domain.com"
}
```

---

## 🧪 Running Tests

```bash
cd page-pulse-backend
mvn test
```

### Test Cases

| Test | Description | Expected |
|------|-------------|----------|
| **Happy Path** | Audits `https://example.com` | Returns valid report with HTTP 200, non-empty title, word count > 0 |
| **Invalid URL** | Audits `not-a-valid-url` | Throws `IllegalArgumentException` |
| **Unreachable URL** | Audits `https://thisdomaindoesnotexist12345.com` | Throws `RuntimeException` |

---

## 🏗️ Design Decisions

### 1. Jsoup over HttpClient + Regex for HTML Parsing

**Decision**: Use Jsoup as a combined HTTP client and HTML parser rather than Java's `HttpClient` with regex-based extraction.

**Reasoning**: Jsoup provides a robust, battle-tested HTML parser that handles malformed HTML gracefully — a common occurrence on real-world web pages. Regex-based HTML parsing is notoriously fragile (it cannot handle nested elements, self-closing tags, or attribute variations reliably). Jsoup also provides a jQuery-like selector API (`doc.select("meta[name=description]")`), making extraction code readable and maintainable. Additionally, Jsoup's built-in HTTP client handles redirects, cookies, and timeouts out of the box, reducing the amount of boilerplate code needed.

### 2. Synchronous Fetching with Configurable Timeout

**Decision**: Use synchronous (blocking) HTTP fetching with a 10-second timeout rather than asynchronous/reactive processing.

**Reasoning**: For a single-URL audit tool, synchronous processing is simpler, more predictable, and easier to debug. The request-response lifecycle is straightforward: the client sends a URL, the server fetches and parses it, then returns results. Introducing reactive/async patterns (e.g., WebFlux) would add significant complexity without measurable benefit for this use case. The 10-second timeout prevents the server from hanging on unresponsive URLs while still allowing enough time for slower pages to load. If this tool needed to audit multiple URLs concurrently, async would be reconsidered.

### 3. Centralized Exception Handling via @ControllerAdvice

**Decision**: Use Spring's `@RestControllerAdvice` with `@ExceptionHandler` methods for all error handling, rather than try-catch blocks in controllers.

**Reasoning**: This approach separates error-handling concerns from business logic, keeping the controller thin and focused on the happy path. All exceptions (invalid URLs, timeouts, non-HTML responses, server errors) are caught in one place and mapped to consistent, structured JSON error responses with appropriate HTTP status codes. This prevents stack traces from leaking to clients (a security best practice) and makes it easy to add new error types without modifying existing controller code. The pattern follows the Single Responsibility Principle — the controller handles routing, the service handles business logic, and the exception handler handles error formatting.

---

## 📁 Project Structure

```
page-pulse/
├── README.md
├── page-pulse-backend/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/pagepulse/
│       │   ├── PagePulseApplication.java
│       │   ├── config/
│       │   │   └── CorsConfig.java
│       │   ├── controller/
│       │   │   └── AuditController.java
│       │   ├── exception/
│       │   │   └── GlobalExceptionHandler.java
│       │   ├── model/
│       │   │   ├── AuditReport.java
│       │   │   └── ErrorResponse.java
│       │   └── service/
│       │       └── AuditService.java
│       ├── main/resources/
│       │   └── application.properties
│       └── test/java/com/pagepulse/service/
│           └── AuditServiceTest.java
└── page-pulse-frontend/
    ├── index.html
    ├── package.json
    ├── vite.config.js
    └── src/
        ├── App.jsx
        ├── index.css
        ├── main.jsx
        └── components/
            ├── AuditForm.jsx
            ├── AuditReport.jsx
            ├── ErrorDisplay.jsx
            └── Footer.jsx
```

---

## 📝 Self-Critique / What I'd Change With More Time

1. **Add unit tests for the controller layer** using `MockMvc` to test the REST API endpoints directly, including request validation and response format.
2. **Implement caching** — repeated audits of the same URL within a short window (e.g., 5 minutes) could return cached results to reduce external fetches.
3. **Extract URL validation into a dedicated `UrlValidator` utility class** for better reusability and testability, rather than embedding validation logic in the service.

---
## 📄 License

This project is licensed under the MIT License.

Built for [Digital Heroes Training Task](https://digitalheroesco.com)
