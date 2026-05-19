# oebs-api-ve

REST API service that integrates OEBS (Oracle E-Business Suite) with Eye-Share and Vieri. The service exposes PL/SQL stored procedures from OEBS as REST endpoints, enabling external systems to query and push data related to invoices, purchase orders, accounting entries, and supplier information.

---

## Architecture

```
Eye-Share                        Vieri
    │                               │
    │  GET/POST /api/v1/...         │  POST /api/v1/...vieri
    ▼                               ▼
┌──────────────────────────────────────────┐
│              oebs-api-ve                 │
│  Spring Boot REST API (Java 21)          │
│                                          │
│  Controllers → Services → Repository    │
│                    │                     │
│           PL/SQL Procedure calls         │
└──────────────────────────────────────────┘
                     │
                     ▼
             ┌───────────────┐
             │  OEBS Oracle  │
             │   Database    │
             └───────────────┘
                              \
                               ──► Vieri REST API
                                   (dimensions, accounts, suppliers)
```

The service acts as a middleware between Eye-Share/Vieri and the OEBS Oracle database. Incoming HTTP requests trigger PL/SQL stored procedures via JDBC, and the results are returned to the caller or forwarded to Vieri's API.

---

## Functionality

### Instances and OEBS environments
The service currently runs with four instances in the secure zone: u1, t1, q1, and prod. External services do not have direct access to the ingress.
To allow Vieri and Eye-Share to reach the service, firewall rules have been opened from the secure zone (fss) to external users — but only for the u1, q1, and prod ingresses.
The t1 instance is therefore only accessible from within the secure zone.

To allow consumers to use oebst1 rather than oebsu1, **the t1 instance exposes data from oebsu1, while u1 exposes data from oebst1**.
As a result, t1 is the preferred environment for development and testing, and is also the first to be deployed in the pipeline. Deployment order: **t1 → u1 → q1 → prod**.

This setup deviates from the standard and should be corrected as soon as possible. The URLs cannot be changed since firewall rules reference the u1 URL specifically. However, the instance names should be updated to align with other services.

### Data flow and transformations

- Incoming requests carry query parameters (e.g. `orgid`, Eye-Share GUIDs, supplier names, dates).
- The service maps these parameters to PL/SQL procedure input objects and calls OEBS via JDBC.
- Results from OEBS are returned as JSON strings.
- For Vieri endpoints, the data fetched from OEBS is further forwarded via HTTP (Spring WebFlux) to Vieri's REST API.
- All requests and responses are logged to the OEBS database table `XXRTV_RESTAPI_LOGG`, including correlation ID, timestamps, duration, and status codes.

### OEBS PL/SQL procedures
**_todo: link to the OEBS repo for relevant files and add a short description of what each procedure does._**

---

## Dependencies

| System | Purpose |
|--------|---------|
| **OEBS Oracle Database** | Source of all business data; accessed via PL/SQL stored procedures |
| **Vieri REST API** | Target for dimension, account, and supplier data forwarded from OEBS |
| **Eye-Share** | Consumer of invoice/document APIs |
| **NAV Token Validation** | OAuth2/JWT security via Azure AD |
| **NAIS platform** | Container orchestration, secrets management, and deployment |

---

## Running Locally

To run the service locally, use the `local` profile and set the following environment variables. Values for all secrets can be retrieved from the NAIS console for the application `oebs-api-ve-t1`:

- `OEBS_USERNAME` – username for OEBS
- `OEBS_PASSWORD` – password for OEBS
- `OEBS_URL` – URL for OEBS
- `AZURE_APP_WELL_KNOWN_URL` – discovery URL for the Azure AD app

You must also have connectivity to oebsu1, which is located in the secure zone.
You can either use **vdi-utvikler-oebs** (a VDI set up for development in the secure zone) or the **Global Secure Access Client**.
For more information, see the [oksty developer documentation](https://github.com/navikt/oksty-documentation).

Note that when running locally, the service connects to **oebsu1**, even though credentials are fetched from the `oebs-api-ve-t1` instance.
For more information about the different instances, see [Instances and OEBS environments](#instances-and-oebs-environments) under Functionality.

[Swagger UI](http://localhost:8080/swagger-ui/index.html) is available when running locally,
but all endpoints are protected by Entra ID by default. To test endpoints without authentication,
replace the `@Protected` annotation in a controller with `@Unprotected`.

---

## Testing

Unit tests are set up using JUnit and Mockito. No integration tests are currently configured.

---

## Monitoring and Alerting

No alerting is currently configured. Issues must be detected by users experiencing errors when calling the API, or through observed problems in OEBS that can be traced back to the API.

**_todo: update links for the different instances._**

Standard application monitoring is available via Grafana dashboards:
- [Grafana dashboard for u1](https://grafana.nav.cloud.nais.io/a/nais-apm-app/services/team-oebs/okonomimodell-api-t1?namespace=team-oebs&environment=dev)
- [Grafana dashboard for t1](https://grafana.nav.cloud.nais.io/a/nais-apm-app/services/team-oebs/okonomimodell-api-t1?namespace=team-oebs&environment=dev)
- [Grafana dashboard for q1](https://grafana.nav.cloud.nais.io/a/nais-apm-app/services/team-oebs/okonomimodell-api-q1?namespace=team-oebs&environment=dev)
- [Grafana dashboard for prod](https://grafana.nav.cloud.nais.io/a/nais-apm-app/services/team-oebs/okonomimodell-api?namespace=team-oebs&environment=prod)

---

## Deploy

### Branching strategy
- Feature development should happen on dedicated branches with a PR to `main`.
- Merging to `main` triggers deployment to **all environments** (T1, U1, Q1, and production).

### Referencing Jira tasks
Include the Jira task key in the branch name and/or commit message. All PRs are squash-merged into main, so the most important thing is that the Jira issue is referenced in the squash commit message and that the PR title references the Jira issue.
For example, if working on `OEBS-123`, the commit message should include `feat(OEBS-123): new rest endpoint` and the PR title should follow the same format.
If a PR covers multiple Jira issues, all should be referenced, e.g. `feat(OEBS-123, OEBS-124): new rest endpoint and tests`.
All individual commits should be listed in the PR description.

### Deployment pipeline
Deployments are handled by GitHub Actions (`.github/workflows/build-deploy-oebs-api-ve.yaml`).

### Promotion criteria
Before deploying to production:
- All tests must pass (`mvn verify`).
- SonarCloud analysis must not introduce new critical issues.

---

## Documentation

### Swagger / OpenAPI
Swagger UI is available when the application is running:

- [Swagger u1](https://oebs-api-ve-u1.dev.intern.nav.no/swagger-ui/index.html#/)
- [Swagger t1](https://oebs-api-ve-t1.dev.intern.nav.no/swagger-ui/index.html#/)
- [Swagger q1](https://oebs-api-ve-q1.dev.intern.nav.no/swagger-ui/index.html#/)
- [Swagger prod](https://oebs-api-ve.intern.nav.no/swagger-ui/index.html#/)

### Confluence
**todo: add confluence link here**

---
