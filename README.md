# OrangeHRM BDD Automation Framework

A production-ready, layered test automation framework for the **OrangeHRM** application
(`https://opensource-demo.orangehrmlive.com`), built with **Selenium 4 + Cucumber 7 (BDD) +
TestNG + Maven**.

It implements the Page Object Model, a thread-safe driver layer for parallel/cross-browser
execution, data-driven testing, Allure reporting, retry/self-healing on flaky failures, and is
fully CI/CD-ready (Jenkins, GitHub Actions, Docker + Selenium Grid).

---

## Table of Contents
1. [Tech Stack](#tech-stack)
2. [Project Structure](#project-structure)
3. [Architecture](#architecture)
4. [Business Flows Covered](#business-flows-covered)
5. [Framework Features](#framework-features)
6. [Prerequisites](#prerequisites)
7. [How to Run](#how-to-run)
8. [Reporting](#reporting)
9. [Configuration](#configuration)
10. [CI/CD](#cicd)
11. [Design Decisions](#design-decisions)

---

## Tech Stack

| Layer            | Technology                          |
|------------------|-------------------------------------|
| Language         | Java 17                             |
| Build            | Maven                               |
| Browser driver   | Selenium WebDriver 4 + WebDriverManager |
| BDD              | Cucumber 7 (Gherkin)                |
| Test runner      | TestNG                              |
| DI (state)       | Cucumber PicoContainer              |
| Reporting        | Allure + Cucumber HTML/JSON         |
| Logging          | Log4j2                              |
| Assertions       | AssertJ                             |
| Test data        | Jackson (JSON), Apache POI (Excel), DataFaker |
| Bonus            | RestAssured (API), JDBC (DB), PDFBox (PDF) |
| Containers / CI  | Docker, Selenium Grid, Jenkins, GitHub Actions |

---

## Project Structure

```
orangehrm-automation/
├── pom.xml                       # Dependencies, plugins, profiles
├── Jenkinsfile                   # Jenkins pipeline (parameterised)
├── Dockerfile                    # Containerised test runner
├── docker-compose.yml            # Selenium Grid (hub + chrome/firefox) + tests
├── .github/workflows/ci.yml      # GitHub Actions pipeline
├── docs/
│   └── ARCHITECTURE.md           # Architecture diagram + layer explanation
│
├── src/main/java/com/orangehrm/
│   ├── config/                   # ConfigLoader (singleton) + ConfigManager (resolution)
│   ├── constants/                # FrameworkConstants (paths, timeouts)
│   ├── driver/                   # DriverManager (ThreadLocal) + DriverFactory
│   ├── enums/                    # BrowserType, ConfigProperties, WaitStrategy
│   ├── exceptions/               # FrameworkException
│   ├── factories/                # ExplicitWaitFactory (wait-strategy -> condition)
│   ├── pages/
│   │   ├── base/                 # BasePage (wrapped Selenium primitives)
│   │   └── modules/              # LoginPage, DashboardPage, PimPage, AdminPage,
│   │                             #   LeavePage, RecruitmentPage, PerformancePage
│   ├── api/                      # ApiClient  (bonus: API validation)
│   ├── db/                       # DatabaseClient (bonus: DB validation)
│   └── utils/                    # Screenshot, JSON/Excel readers, Faker, File, PdfReader
│
└── src/test/
    ├── java/com/orangehrm/
    │   ├── runner/               # Base/Regression/Smoke/Failed Cucumber runners
    │   ├── stepdefinitions/      # One step class per business module
    │   ├── hooks/                # Hooks (driver lifecycle + failure screenshots)
    │   ├── context/              # ScenarioContext (PicoContainer-injected state)
    │   └── listeners/            # RetryAnalyzer, RetryTransformer, TestListener
    └── resources/
        ├── features/             # *.feature (Gherkin) for each module
        ├── config/               # config.properties
        ├── testdata/             # users.json, leave.json, kpi.json
        ├── uploads/              # profile.png, resume.pdf (upload assets)
        ├── suites/               # testng / smoke / regression / parallel xml
        ├── log4j2.xml            # logging config
        └── cucumber.properties   # Allure results dir
```

---

## Architecture

See **[docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)** for the full diagram.

In short, the framework is split into four layers so that a change in one rarely
ripples into the others:

1. **Feature layer** – business-readable Gherkin (`.feature`).
2. **Glue layer** – step definitions + hooks; orchestrate pages, hold no locators.
3. **Page layer** – Page Objects extending `BasePage`; own the locators and the
   business methods (`login`, `addEmployee`).
4. **Core/utility layer** – driver management, config, waits, reporting, data.

State that must travel between steps is passed through a PicoContainer-injected
`ScenarioContext`, never through static fields — that is what keeps parallel
execution safe.

---

## Business Flows Covered

| # | Module       | Scenario(s)                                                        | Feature file          |
|---|--------------|--------------------------------------------------------------------|-----------------------|
| 1 | Login        | Valid login + dashboard validation, invalid login, field validation | `login.feature`       |
| 2 | PIM          | Create employee, upload profile image, search                       | `pim.feature`         |
| 3 | Leave        | Search / process leave                                              | `leave.feature`       |
| 4 | Admin        | Create user, assign role/status, search                             | `admin.feature`       |
| 5 | Recruitment  | Add candidate, upload resume                                        | `recruitment.feature` |
| 6 | Performance  | Create KPI for a job title                                          | `performance.feature` |

---

## Framework Features

- **Page Object Model** with a rich `BasePage` (waits, logging, JS fallbacks).
- **Thread-safe parallel execution** via `ThreadLocal<WebDriver>` + Cucumber
  `@DataProvider(parallel=true)`.
- **Cross-browser**: Chrome / Firefox / Edge, headed or headless, local or remote (Grid).
- **Config management** with system-property override (`-Dbrowser=firefox`).
- **Explicit-wait factory** — zero `Thread.sleep`, no implicit/explicit wait mixing.
- **Data-driven** — JSON, Excel and runtime `DataFaker` data.
- **Allure reporting** with steps, screenshots-on-failure and defect categories.
- **Retry mechanism** — config-driven `RetryAnalyzer` auto-applied to every test.
- **Logging** — Log4j2 to console + rolling file.
- **CI/CD-ready** — Jenkinsfile, GitHub Actions, Docker + docker-compose Grid.
- **Bonus layers** — API (RestAssured), DB (JDBC), PDF (PDFBox) validation utilities.

---

## Prerequisites

- Java 17+ (`java -version`)
- Maven 3.9+ (`mvn -version`)
- Chrome/Firefox/Edge installed (WebDriverManager resolves the drivers automatically)
- (Optional) Docker, for Grid-based parallel runs

---

## How to Run

```bash
# Full regression, default browser (chrome) headless
mvn clean test

# Smoke suite only
mvn clean test -P smoke

# Specific browser, headed
mvn clean test -Dbrowser=firefox -Dheadless=false

# Parallel scenario execution (3 threads)
mvn clean test -P parallel -Dthread.count=3

# Filter by Cucumber tags
mvn clean test -Dcucumber.filter.tags="@pim and @smoke"

# Re-run only the failures from the previous run
mvn test -Dtest=FailedRunner
```

### Run in Docker against a Selenium Grid
```bash
docker compose up --build --abort-on-container-exit
```

---

## Reporting

```bash
# After a run, open the interactive Allure report
mvn allure:serve

# Or generate a static report into target/site/allure-maven-plugin
mvn allure:report
```

Artifacts produced per run:
- `target/allure-results/` → Allure raw results
- `target/cucumber-reports/` → Cucumber HTML + JSON + rerun.txt
- `target/screenshots/` → failure screenshots (the "execution screenshots" deliverable)
- `target/logs/automation.log` → execution log

---

## Configuration

All settings live in `src/test/resources/config/config.properties` and **any** of
them can be overridden at runtime with `-Dkey=value`. System property always wins
over the file — this is what lets the same build behave differently locally vs CI.

Key properties: `baseurl`, `browser`, `headless`, `remote`, `gridurl`,
`explicitwait`, `pageloadtimeout`, `retrycount`, plus the bonus `apibaseurl` /
`dburl` / `dbuser` / `dbpassword`.

---

## CI/CD

- **Jenkins** (`Jenkinsfile`): parameterised by browser / suite / tags, publishes
  Allure, archives screenshots & logs.
- **GitHub Actions** (`.github/workflows/ci.yml`): runs on push/PR, nightly cron,
  and manual dispatch; uploads Allure results and publishes the report to GitHub Pages.
- **Docker** (`Dockerfile` + `docker-compose.yml`): containerised runner against a
  Selenium Grid hub with Chrome + Firefox nodes.

---

## Design Decisions

- **Why explicit waits only?** Mixing implicit and explicit waits causes
  unpredictable timeouts. Implicit wait is set to `0`; every interaction goes
  through `ExplicitWaitFactory`.
- **Why ScenarioContext over static state?** Static fields are shared across
  threads and break parallel runs. PicoContainer gives each scenario its own
  context, injected into every step class.
- **Why enums for config keys?** Compile-time safety and IDE auto-complete — no
  silent failures from a mistyped property name.
- **Why a single `FrameworkException`?** Consistent, readable failure messages in
  reports and one place to wrap low-level checked exceptions.
