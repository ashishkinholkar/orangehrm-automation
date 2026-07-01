# Framework Architecture

## Layered design

```mermaid
flowchart TD
    subgraph FEATURE["1 - Feature Layer (Gherkin)"]
        F1["login.feature"]
        F2["pim.feature"]
        F3["leave.feature / admin.feature / recruitment.feature / performance.feature"]
    end

    subgraph RUNNER["Runners (TestNG + Cucumber)"]
        R1["BaseRunner / RegressionRunner / SmokeRunner / FailedRunner"]
    end

    subgraph GLUE["2 - Glue Layer"]
        S1["Step Definitions"]
        H1["Hooks (driver lifecycle, screenshots)"]
        C1["ScenarioContext (PicoContainer DI)"]
    end

    subgraph PAGE["3 - Page Layer (POM)"]
        P0["BasePage (waits, logging, JS)"]
        P1["LoginPage / DashboardPage"]
        P2["PimPage / AdminPage / LeavePage"]
        P3["RecruitmentPage / PerformancePage"]
    end

    subgraph CORE["4 - Core / Utility Layer"]
        D1["DriverFactory + DriverManager (ThreadLocal)"]
        CFG["ConfigManager + ConfigLoader"]
        W1["ExplicitWaitFactory"]
        U1["Screenshot / Json / Excel / Faker / File / Pdf"]
        EX["FrameworkException"]
    end

    subgraph BONUS["Bonus Layers"]
        API["ApiClient (RestAssured)"]
        DB["DatabaseClient (JDBC)"]
        PDF["PdfReaderUtil (PDFBox)"]
    end

    subgraph REPORT["Reporting & CI/CD"]
        AL["Allure + Cucumber HTML/JSON"]
        LOG["Log4j2"]
        CI["Jenkins / GitHub Actions / Docker Grid"]
    end

    FEATURE --> RUNNER --> GLUE
    GLUE --> PAGE
    PAGE --> P0
    P0 --> CORE
    GLUE --> CORE
    GLUE --> BONUS
    CORE --> REPORT
    GLUE --> REPORT
```

## Execution flow (single scenario)

```mermaid
sequenceDiagram
    participant TestNG
    participant Runner as CucumberRunner
    participant Hooks
    participant Step as StepDefinition
    participant Page as PageObject
    participant Driver as DriverFactory
    participant Report as Allure

    TestNG->>Runner: start scenario (thread)
    Runner->>Hooks: @Before
    Hooks->>Driver: initDriver() -> ThreadLocal WebDriver
    Runner->>Step: execute Given/When/Then
    Step->>Page: business method (login, addEmployee)
    Page->>Driver: WebDriver actions via ExplicitWaitFactory
    Page-->>Step: result
    Step-->>Runner: assertion (AssertJ)
    Runner->>Hooks: @After
    alt scenario failed
        Hooks->>Report: attach screenshot
        Hooks->>Hooks: RetryAnalyzer may re-run
    end
    Hooks->>Driver: quitDriver() + ThreadLocal.remove()
```

## Why these layers

- **Separation of concerns** — locators live only in page objects, orchestration
  only in steps, infrastructure only in core. A UI change touches one page object.
- **Parallel-safe by construction** — the driver is `ThreadLocal` and cross-step
  state flows through an injected `ScenarioContext`, never static fields.
- **Config-driven** — browser, environment, timeouts, grid URL and retry count are
  all externalised, so the same artifact runs locally and in CI without edits.
