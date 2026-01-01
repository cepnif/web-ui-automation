# Web UI Automation Framework – Public Service Login

A production‑ready **Web UI Automation Framework** built for a **government‑style public service login journey**.  
The framework is designed with **accessibility‑first principles**, **CI/CD reliability**, and **clear separation of concerns**.

This repository is suitable for **new joiners**, **automation engineers**, and **auditors** who need to understand *what is tested, how it is tested, and how to run it* from scratch.

---

## 1. What This Project Is

This project automates testing of a **Public Service Login prototype** covering:

- ✅ **Functional UI testing**
- ♿ **Accessibility (WCAG‑focused) testing**
- 🚦 **Smoke & Regression suites**
- 🌐 **Cross‑browser execution**
- 🧪 **CI‑friendly execution & reporting**

The framework is intentionally scoped to **web UI only** and focuses on **login‑related journeys**.

---

## 2. What This Project Is NOT

- ❌ Not a mobile automation framework
- ❌ Not a performance/load testing tool
- ❌ Not an API‑only automation framework

---

## 3. Technologies Used

| Area | Technology |
|----|----|
| Language | **Java 21** |
| Automation | **Playwright (Java)** |
| BDD | **Cucumber + Gherkin** |
| Test Engine | **JUnit Platform** |
| Logging | **SLF4J + Logback** |
| Reporting | Cucumber HTML, JSON, JUnit XML |
| Accessibility | Axe‑core (support ready) |
| Build Tool | Maven |

---

## 4. Why Playwright?

Playwright was chosen because it:

- Provides **auto‑waiting** (reduced flakiness)
- Supports **Chromium, Firefox, WebKit**
- Has **first‑class tracing & screenshots**
- Is **CI‑friendly**
- Aligns well with **modern accessibility testing**

---

## 5. Why BDD (Cucumber)?

- Scenarios are **readable by non‑testers**
- Strong **tag‑based execution**
- Encourages **clean separation** between business intent and implementation
- Excellent fit for **government / regulated environments**

---

## 6. Architectural Overview

The framework follows a strict layered architecture:

- **Configuration Layer** – environment, browser, headless mode, timeouts  
- **UI Layer (Pages & Components)** – encapsulated locators and interactions  
- **Flow / Facade Layer** – login journeys and branching flows  
- **Assertion Layer** – functional and accessibility assertions  
- **Hooks & Lifecycle Layer** – browser lifecycle, artefacts, logging  
- **Observability Layer** – logs, screenshots, traces, reports  

---

## 7. Design Principles

- Single responsibility per class
- No duplicated step definitions
- Accessibility assertions live **only** in accessibility helpers
- Navigation steps live **only** in navigation step classes
- Each scenario runs in a **fresh browser context**
- No shared mutable state

---

## 8. Project Structure (EXACT)

The project structure below **matches the repository exactly**:

```
web-ui-automation
├── govuk-login-prototype
│
├── src
│   ├── main
│   │
│   └── test
│       ├── java
│       │   ├── config
│       │   ├── driver
│       │   ├── helpers.accessibility
│       │   ├── helpers.ui
│       │   ├── hooks
│       │   ├── runners
│       │   └── steps
│       │
│       └── resources
│           ├── axe
│           ├── config
│           ├── features.accessibility
│           ├── features.functional
│           ├── testdata
│           ├── cucumber.properties
│           ├── junit-platform.properties
│           └── logback-test.xml
│
├── test-results
│
├── .gitignore
├── pom.xml
└── README.md
```

---

## 9. Tag Strategy

| Tag | Meaning |
|----|--------|
| @ui | UI tests |
| @functional | Functional coverage |
| @accessibility | Accessibility coverage |
| @smoke | Critical fast checks |
| @regression | Full regression |
| @performance | Lightweight NFR checks |

---

## 10. Local Prerequisites

- Java 21  
- Maven  
- Node.js (for Playwright browsers)

Verify Java:

```bash
java -version
```

---

## 11. Clone & Setup

```bash
git clone <repository-url>
cd web-ui-automation
```

---

## 12. Government Prototype (Test Application)

This repository includes:

```
govuk-login-prototype
```

This is the **test application** used by automation.

### To run the prototype locally:

```bash
cd govuk-login-prototype
npm install
npm run dev
```

The app runs locally and is required before executing UI tests.

---

## 13. Key Packages Explained

### `helpers.ui`
Reusable Playwright actions:
- click
- fill
- navigate
- waits
- visibility checks

### `helpers.accessibility`
Centralised **accessibility assertions**:
- landmarks
- labels
- keyboard navigation
- focus management
- error summaries

### `steps`
Cucumber Step Definitions:
- Functional steps
- Accessibility‑only steps
- No duplication of responsibilities

### `hooks`
Lifecycle management:
- Browser setup / teardown
- Screenshots on failure
- Tracing control

### `runners`
Multiple Cucumber runners:
- Smoke
- Regression
- Functional only
- Accessibility only
- Run‑all (parent)

---



## 14. Environment Configuration

### Default Environment
- `env=test`
- Browser: `chromium`
- Headless: `true`

### Override via CLI
```bash
-Denv=test
-Dbrowser=firefox
-Dheadless=false
```

Configuration files:
```
src/test/resources/config
```

---

## 15. Running Tests Locally

### Run all tests
```bash
mvn clean test
```

### Smoke tests
```bash
mvn test -Dcucumber.filter.tags=@smoke
```

### Accessibility only
```bash
mvn test -Dcucumber.filter.tags=@accessibility
```

### Functional only
```bash
mvn test -Dcucumber.filter.tags=@functional
```

### Regression
```bash
mvn test -Dcucumber.filter.tags=@regression
```

---

## 16. Cucumber Runners

Each runner maps to a test intent:

| Runner | Purpose |
|----|----|
| RunSmokeTests | PR validation |
| RunAccessibilityTests | WCAG coverage |
| RunFunctionalTests | Business flows |
| RunRegressionTests | Full suite |
| RunAllTests | Everything |

---

## 17. Reporting & Artefacts

Generated automatically under:

```
test-results/
```

Includes:
- Screenshots on failure
- Playwright traces
- HTML report
- JSON report
- JUnit XML

---

## 18. Accessibility Strategy

Accessibility tests validate:

- Keyboard‑only navigation
- Focus visibility
- Error summary behaviour
- ARIA roles & landmarks
- Label associations

Assertions are **custom‑built**, deterministic, and audit‑friendly.

---

## 19. CI/CD Friendly Design

- Tag‑driven execution
- No shared state
- Parallel‑ready
- Deterministic outcomes

---

## 20. New Joiner – First Day Checklist

1. Clone repo
2. Install Java 21
3. Install Node.js
4. Run prototype
5. Run smoke tests
6. Open HTML report

You are ready 🚀

---


---

## Authorship and Design Attribution

This framework has been **designed, architected, and incrementally developed by Fatih Cepni**, with **all technical decisions, architectural directions, and implementation steps explicitly defined and guided by Fatih**, and supported through iterative collaboration with **ChatGPT as an engineering assistant**.

The overall design, structure, testing strategy, accessibility approach, and execution model reflect **real-world public sector delivery standards** and have been intentionally shaped to align with **enterprise-grade quality, maintainability, and auditability expectations**.

This framework has been **designed and tailored**, with a strong focus on:
- accessibility-first principles,
- high-traffic public service use cases,
- CI/CD reliability,
- and long-term extensibility.

ChatGPT has been used strictly as a **tooling and acceleration aid**, while **all ownership of design intent, validation, and final decisions remains with Fatih Çepni**.

---

## Known Limitations and Future Improvements

While the framework is production-ready for its current scope, the following areas are intentionally left as **future enhancement opportunities**:

1. **Advanced Accessibility Tooling Integration**
    - Deeper integration with tools such as **axe-core reporting dashboards** or **Lighthouse CI**
    - Automated WCAG success-criteria mapping per scenario

2. **Visual Regression Testing**
    - Integration of visual snapshot comparison (e.g. Playwright screenshots with baseline diffs)
    - Useful for UI consistency checks in regression pipelines

3. **API-Assisted Test Flows**
    - Hybrid UI + API setup steps (e.g. authenticated state via API)
    - Would significantly reduce execution time for some scenarios

4. **Extended Performance Budgets**
    - More granular performance thresholds (e.g. TTFB, LCP, CLS where applicable)
    - Optional trend analysis across builds

5. **Mobile and Responsive Coverage**
    - Mobile viewport execution and responsive layout validation
    - Currently, out of scope by design

6. **Centralised Test Data Service**
    - Externalised test data management (e.g. via a test data API or secrets manager)
    - Particularly useful for large-scale environments and parallel execution

These enhancements have been deliberately deferred to keep the current framework **focused, lightweight, and stable**, while leaving a clear roadmap for future evolution.

---
