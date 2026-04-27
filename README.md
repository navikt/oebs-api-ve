# oebs-api-ve

REST API-tjeneste som integrerer OEBS (Oracle E-Business Suite) med Eye-Share og Vieri. Løsningen eksponerer PL/SQL-prosedyrer fra OEBS som REST-endepunkter.

## Funksjonalitet

### Eye-Share API-er (Faktura- og dokumenthåndtering)

Disse endepunktene spør OEBS etter data relatert til fakturaer og dokumenter:

- **`GET /api/v1/bilagsnummer`** - Hent fakturanummer basert på Eye-Share GUID
- **`GET /api/v1/bokfoertstatus`** - Hent bokføringsstatus for et dokument
- **`GET /api/v1/bestillingsinfo`** - Hent innkjøpsordre-informasjon
- **`GET /api/v1/konteringsinfo`** - Hent konteringsinfo med filtermuligheter for segment
- **`POST /api/v1/fakturainfo`** - Lagre fakturainformasjon

### Vieri API-er (Dimensjoner og masterdata)

Disse endepunktene henter data fra OEBS og sender det videre til Vieris API for dimensjonsanalyse:

- **`POST /api/v1/konteringsinfovieri`** - Send konteringsdimensjoner til Vieris dimensjons-API
- **`POST /api/v1/konteringsinfo-hb-vieri`** - Send konteringshierarki og konti til Vieris konto-API
- **`POST /api/v1/leverandorinfo-vieri`** - Send leverandørinformasjon til Vieris leverandør-API

### Helsekontroller

- **`GET /internal/isready`** - Beredskapssjekk (sjekker databasekoblingen)
- **`GET /internal/isalive`** - Liv-sjekk (sjekker databasekoblingen)
- **`GET /api/v1/ping`** - Manuell databasekoblingskontroll

## Teknisk Stack

- **Java 21** med Spring Boot 3.5.9
- **Oracle JDBC** - Tilkobling til OEBS-database
- **Spring WebFlux** - Reaktiv HTTP-kommunikasjon
- **Spring Data JPA/Hibernate** - Persistering
- **Swagger/OpenAPI 3.0** - API-dokumentasjon
- **NAV Token Validation** - OAuth/JWT-sikkerhet
- **Logstash/Kibana** - Sentralisert logging
- **Prometheus** - Metrikker

## Sentrale Funksjoner

### HTTP-logging
Alle API-kall logges til databasetabellen `XXRTV_RESTAPI_LOGG` med:
- Korrelasjon-ID
- Tidsstempel
- Request/response-innhold
- Kjøringstid
- Statuskode

### PL/SQL-integrasjon
Kjører OEBS lagrede prosedyrer som f.eks. `xxrtv_restapi_oebs_ve_v1.xxrtv_betalingsdato`

### Sikkerhet
- Beskyttede endepunkter krever JWT-token via NAVs token-validering

### Spørring og Filtrering
- Støtter segmentbasert filtrering
- Støtter datobasert oppdateringskontroll
- Org-ID parameter for organisasjonsfiltrering

## Oppsett

### Krav
- Java 21+
- Maven 3.8+
- Oracle-database tilgang

### Bygge
```bash
mvn clean package
```

### Kjøre Lokalt
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### Docker
Appen er containerisert med Dockerfile. Bygg og kjør:
```bash
docker build -t oebs-api-ve .
docker run -p 8080:8080 oebs-api-ve
```

## Konfigurasjon

Konfigurasjonsfiler finnes i `src/main/resources`:
- `application.properties` - Standardkonfigurasjon
- `application-local.properties` - Lokal utvikling
- Vault-secrets for produksjon via `.nais`-katalog

## Struktur

```
src/main/java/no/nav/oebs/
├── restapi/
│   ├── api/
│   │   ├── eye_share/         # Eye-Share endepunkter
│   │   ├── vieri/             # Vieri endepunkter
│   │   └── ping/              # Helsekontroller
│   ├── config/                # Spring-konfigurasjon
│   ├── db/
│   │   ├── entity/            # JPA-entiteter
│   │   └── repository/        # Database-lagring
│   ├── exception/             # Egendefinerte unntak
│   └── health/                # Helsekontroller
```

## Logging

Applikasjonen bruker SLF4J med Logback. Logger er integrert med Kibana for sentralisert logging i NAV-miljøet.

Viktige logger-kategorier:
- `no.nav.oebs.restapi` - Applikasjonslogs
- HTTP-requests/responses logges automatisk via filter

## Swagger/OpenAPI

API-dokumentasjon er tilgjengelig via Swagger UI når applikasjonen kjører:
```
http://localhost:8080/swagger-ui.html
```

## Lisens

MIT License - se LICENSE.md

## Kontakt

Se CODEOWNERS for team ansvarlig for denne koden.
