# Setup Guide

Denne guide hjælper dig med at starte alle services og prøve applikationen.

## Forudsætninger

- **Docker** og **Docker Compose** (til databasen)
- **Java 17+** (til backend)
- **Maven** (til at bygge og køre backend)
- En moderne webbrowser (til frontend)

---

## Trin 0: Tjek at Docker kører

Inden du går videre, skal Docker Desktop være startet. Verificer med:

```bash
docker info
```

Hvis du ser output med serverinfo, er Docker klar. Hvis du får en fejl som `Cannot connect to the Docker daemon`, skal du starte Docker Desktop manuelt og vente til ikonet i menulinjen ikke spinner mere.

Du kan også tjekke Docker Compose virker:

```bash
docker compose version
```

---

## Trin 1: Start databasen

Fra projektets rodmappe:

```bash
docker compose up -d
```

Dette starter en MySQL 8.0 container på port `3308` og opretter automatisk:
- Databasen `fake_person`
- Tabellerne `postal_code` og `person_name`
- Seed-data med danske postnumre og personnavne
- Brugerrettigheder til `user`@`fake_person`

Vent ca. 15-20 sekunder på at MySQL initialiserer. Tjek at den er klar:

```bash
docker compose logs mysql | grep "ready for connections"
```

Ser du `ready for connections` i output, kører databasen. Alternativt kan du tjekke status:

```bash
docker compose ps
```

`STATUS` skal vise `Up`.

**Verificer at data er loadet:**

```bash
docker compose exec mysql mysql -uuser -p123456 fake_person -e "SELECT COUNT(*) as postal_codes FROM postal_code; SELECT COUNT(*) as names FROM person_name;"
```

Du skal se ca. 586 postnumre og 536 navne.

> **OBS:** Hvis port `3308` allerede er i brug (f.eks. af MySQL Workbench eller en anden proces), kan du tjekke med `lsof -i :3308`. Luk den pågældende forbindelse eller skift porten i `docker-compose.yml` og `backend/src/main/resources/application.properties`.

---

## Trin 2: Start backend

```bash
cd backend
mvn spring-boot:run
```

Første gang kan Maven bruge lidt tid på at downloade dependencies. Vent til du ser:

```
Started FakePersonApplication in X.XXX seconds
```

API'et kører nu på **http://localhost:8080**.

**Verificer at backend virker:**

```bash
curl http://localhost:8080/api/person | python3 -m json.tool
```

Du skal få et JSON-objekt tilbage med en fake person.

---

## Trin 3: Åbn frontend

Åbn filen `frontend/index.html` direkte i din browser:

```bash
open frontend/index.html        # macOS
xdg-open frontend/index.html    # Linux
start frontend/index.html       # Windows
```

Du kan nu klikke på knapperne for at generere fake data.

---

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

---

## Kør tests

Tests bruger en H2 in-memory database og kræver ikke at Docker kører:

```bash
cd backend
mvn test
```

---

## Stop alt

```bash
docker compose down
```

Tilføj `-v` for også at slette database-data (kræver fuld re-initialisering næste gang):

```bash
docker compose down -v
```
