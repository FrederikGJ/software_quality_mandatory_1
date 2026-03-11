# Setup Guide

Denne guide hjælper dig med at starte alle services og prøve applikationen.

## Forudsætninger

- **Docker** og **Docker Compose** (til databasen)
- **Java 17+** (til backend)
- **Maven** (til at bygge og køre backend)
- En moderne webbrowser (til frontend)

## 1. Start databasen

```bash
docker compose up -d
```

Dette starter en MySQL 8.0 container på port `3307` og opretter automatisk:
- Databasen `fake_person`
- Tabellerne `postal_code` og `person_name`
- Seed-data med danske postnumre og personnavne

Vent ca. 10-15 sekunder på at MySQL er klar. Du kan tjekke status med:

```bash
docker compose logs mysql
```

Kig efter linjen `ready for connections` for at bekræfte at databasen kører.

## 2. Start backend

```bash
cd backend
mvn spring-boot:run
```

API'et kører nu på **http://localhost:8080**.

Første gang kan Maven bruge lidt tid på at downloade dependencies.

## 3. Åbn frontend

Åbn filen `frontend/index.html` direkte i din browser:

```bash
open frontend/index.html        # macOS
xdg-open frontend/index.html    # Linux
start frontend/index.html       # Windows
```

Du kan nu klikke på knapperne for at generere fake data.

## API-endpoints

Alle endpoints er tilgængelige via `GET`:

| Endpoint | Beskrivelse |
|---|---|
| `/api/cpr` | Fake CPR-nummer |
| `/api/name-gender` | Fornavn, efternavn og køn |
| `/api/name-gender-dob` | Navn, køn og fødselsdato |
| `/api/cpr-name-gender` | CPR, navn og køn |
| `/api/cpr-name-gender-dob` | CPR, navn, køn og fødselsdato |
| `/api/address` | Fake adresse |
| `/api/phone` | Fake mobilnummer |
| `/api/person` | Al info om en fake person |
| `/api/persons/{count}` | Bulk: 2-100 fake personer |

Prøv f.eks. i terminalen:

```bash
curl http://localhost:8080/api/person | python3 -m json.tool
```

## Kør tests

```bash
cd backend
mvn test
```

Tests bruger en H2 in-memory database, så de kører uden Docker.

## Stop alt

```bash
docker compose down
```

Tilføj `-v` for også at slette database-data:

```bash
docker compose down -v
```
