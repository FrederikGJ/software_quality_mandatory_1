# Mangler - Hvad der stadig skal laves

Oversigt over krav fra `requirement_specifications.md` og deres status.

## Applikation

| Krav | Status |
|---|---|
| Backend med 9 API-endpoints | Done |
| Grafisk UI der forbruger API'et | Done |
| Database med postnumre og personnavne | Done |

## Testing

| Krav | Status | Beskrivelse |
|---|---|---|
| Unit tests | Done | CprServiceTest, PhoneServiceTest, AddressServiceTest (515 tests) |
| Integrationstests | Done | FullIntegrationTest, ApiIntegrationTest, FakePersonControllerTest (116 tests) |
| API-tests i Postman/Insomnia/ThunderClient | **MANGLER** | Skal oprettes som collection med testscripts for alle 9 endpoints |
| End-to-end tests (Selenium/Cypress/Playwright) | **MANGLER** | Skal teste frontend-flowet via browser |
| CI-pipeline (GitHub Actions el.lign.) | **MANGLER** | Skal køre unit + integrationstests automatisk |
| Kontinuerlig testning i CI | **MANGLER** | Hænger sammen med CI-pipeline |

## Testdesign og analyse

| Krav | Status | Beskrivelse |
|---|---|---|
| Black-box teknikker (manuel analyse) | **MANGLER** | PDF med partitioner, grænseværdier, beslutningstabeller |
| White-box teknikker (automatiseret analyse) | **MANGLER** | Code coverage rapport, f.eks. med JaCoCo |
| Experience-based teknikker | **MANGLER** | Edge cases og forretningslogik dokumenteret |
| Statiske testværktøjer (ud over linters) | **MANGLER** | F.eks. SpotBugs, PMD, SonarQube - med screenshots |

## Afleveringsmateriale

| Krav | Status |
|---|---|
| Kildekode for applikation | Done |
| Database-scripts | Done |
| Kildekode for tests (unit, integration, E2E) | Delvist (E2E mangler) |
| CI-konfigurationsfiler | **MANGLER** |
| PDF med black-box testcase-design | **MANGLER** |
| Screenshots af statiske testværktøjer | **MANGLER** |

## Prioriteret rækkefølge

1. **CI-pipeline** - GitHub Actions workflow der kører `mvn test`
2. **E2E tests** - Playwright eller Cypress tests af frontend
3. **API-tests** - Postman collection med testscripts
4. **Statiske testværktøjer** - JaCoCo + SpotBugs/PMD setup + screenshots
5. **Black-box PDF** - Dokument med partitioner, grænseværdier, beslutningstabeller
6. **White-box dokumentation** - Coverage rapport + analyse
7. **Experience-based dokumentation** - Edge cases beskrevet
