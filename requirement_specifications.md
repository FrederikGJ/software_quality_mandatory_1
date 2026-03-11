# Personal Test Data Generator

> **Gruppearbejde:** 4–5 studerende pr. gruppe. Skriv jeres navne i **Groups – First Mandatory Assignment.xlsx** i Microsoft Teams.
> Ved særlige omstændigheder kontaktes Head of Education **Marc Kluge** på [klu@ek.dk](mailto:klu@ek.dk).

---

## System Under Test

Applikationen genererer fake-information om ikke-eksisterende danske personer og består af:

- **Backend** – serverer information via et API
- **Grafisk UI** – forbruger API'et

---

## Backend – Returnerede Data

### Navn og køn
Sammensat information, tilfældigt udtrukket fra `person-names.json`:

- Fornavn
- Efternavn
- Køn

### CPR-nummer
10 numeriske cifre:

- De **første 6** angiver fødselsdato på formatet `ddMMyy`
- De **sidste 4** er tilfældigt genererede med følgende begrænsning:
  - Sidste ciffer er **lige** for kvinder
  - Sidste ciffer er **ulige** for mænd

### Fødselsdato
Skal matche datoen i CPR-nummeret.

### Adresse
Sammensat information:

- **Vejnavn** – en tilfældig kombination af alfabetiske tegn
- **Nummer** – et tal fra 1 til 999, eventuelt efterfulgt af et stort bogstav (f.eks. `43B`)
- **Etage** – enten `st` eller et tal fra 1 til 99
- **Dør** – `th`, `mf`, `tv`, et tal fra 1 til 50, eller et lille bogstav eventuelt efterfulgt af en bindestreg og 1–3 cifre (f.eks. `c3`, `d-14`)
- **Postnummer og by** – tilfældigt udtrukket fra `addresses.sql`

Mere information: [danmarksadresser.dk](https://danmarksadresser.dk/regler-og-vejledning/adresser)

### Mobilnummer
- Format: 8 numeriske cifre
- Skal starte med én af følgende cifrerkombinationer:
  `2, 30, 31, 40, 41, 42, 50, 51, 52, 53, 60, 61, 71, 81, 91, 92, 93, 342, 344–349, 356–357, 359, 362, 365–366, 389, 398, 431, 441, 462, 466, 468, 472, 474, 476, 478, 485–486, 488–489, 493–496, 498–499, 542–543, 545, 551–552, 556, 571–574, 577, 579, 584, 586–587, 589, 597–598, 627, 629, 641, 649, 658, 662–665, 667, 692–694, 697, 771–772, 782–783, 785–786, 788–789, 826–827, 829`

Mere information: [nummervejledning-2025.pdf](https://digst.dk/media/4knkrjpk/nummervejledning-2025.pdf)

---

## Funktionaliteter

Der skal være én funktion/metode og et tilsvarende API-endpoint for hver af følgende:

1. Returner et fake CPR-nummer
2. Returner fake fornavn, efternavn og køn
3. Returner fake fornavn, efternavn, køn og fødselsdato
4. Returner fake CPR, fornavn, efternavn og køn
5. Returner fake CPR, fornavn, efternavn, køn og fødselsdato
6. Returner en fake adresse
7. Returner et fake mobilnummer
8. Returner al information om en fake person (CPR, fornavn, efternavn, køn, fødselsdato, adresse, mobilnummer)
9. Returner fake personinformation i bulk (al information for 2–100 personer)

---

## Valgfrie teknologier

Gruppen vælger selv:

- Programmeringssprog og framework(s)
- API-format (f.eks. REST)
- Eventuel alternativ database til MySQL/MariaDB (datamigration kræves)
- Outputformat for funktioner/metoder og API (f.eks. JSON, XML, CSV, formateret tekst)

Inspiration og eksempelkode:

- Backend (MariaDB / PHP8, Dockerized): [github.com/arturomorarioja/fake_info](https://github.com/arturomorarioja/fake_info/)
- Frontend (JavaScript / CSS3 / HTML5): [github.com/arturomorarioja/js_fake_info_frontend](https://github.com/arturomorarioja/js_fake_info_frontend)

---

## Testing

Følgende testopgaver skal implementeres:

- Skriv **unit tests** og **integrationstest** hvor det er passende
- Skriv **API-tests** inkl. testscripts i Postman, Insomnia, ThunderClient eller lignende
- Design testcases baseret på:
  - **Black-box teknikker** (manuel analyse)
  - **White-box teknikker** (automatiseret analyse med værktøjer)
  - **Experience-based teknikker** (edge cases, forretningslogik og andre interessante cases)
- Brug **statiske testværktøjer** (ud over linters) til at analysere og forbedre koden
- Opret et **Continuous Integration-job eller -pipeline** til at teste integrationen
- Implementer **kontinuerlig testning** ved at køre alle unit- og integrationstests i CI-processen
- Design og skriv **end-to-end tests** i Selenium WebDriver, Cypress, Playwright eller lignende (ikke Selenium IDE)

---

## Aflevering

**Deadline: 6. april 2026, kl. 23:59** – aflever i Itslearning som én zip-fil indeholdende:

- Kildekode for applikationen (hvis forskellig fra den udleverede)
- Database-oprettelses- og populationsscript (hvis forskellig fra det udleverede)
- Kildekode for unit tests, integrationstests og end-to-end tests
- CI-konfigurationsfiler (YAML, XML eller andet format – screenshot er acceptabelt)
- En PDF-fil der beskriver brugen af black-box teknikker til at finde testcases (f.eks. gyldige/ugyldige partitioner, grænseværdier, beslutningstabeller)
- Screenshots af de anvendte statiske testværktøjer der viser deres værdi

---

## Præsentation

**Dato: 7. april 2026** – hver gruppe præsenterer hele opgaven for resten af klassen.

Præsentationen skal indeholde:

- Demo af applikationen i brug
- Gennemgang af applikationskoden
- Gennemgang og kørsel af unit-, integrations- og end-to-end tests
- Gennemgang af API-tests
- Begrundelse for testcasedesign:
  - Black-box teknikker
  - White-box teknikker
  - Experience-based teknikker
- Værdien af de anvendte statiske testværktøjer (inkl. white-box analyse hvis relevant)
- CI-job eller -pipeline:
  - Gennemgang af konfigurationen
  - Demo af processen

> **Tidsbegrænsning: 8 minutter pr. gruppe. Alle gruppemedlemmer skal præsentere en del.**
