
/**
 * What is being tested
 *
 * This Cypress end-to-end test suite verifies the main frontend functionality
 * of the Fake Person Generator application from a user perspective.
 *
 * The tests simulate real user interaction with the interface by opening the page,
 * clicking the available buttons, entering bulk values, and validating the displayed results.
 *
 * The test suite covers:
 * - correct page loading
 * - generation of CPR number
 * - generation of name and gender
 * - generation of name, gender and date of birth
 * - generation of CPR, name and gender
 * - generation of CPR, name, gender and date of birth
 * - generation of address
 * - generation of mobile number
 * - generation of a full fake person
 * - bulk generation with valid input values
 * - validation of invalid and empty bulk input
 *
 * Black-box test design is used for the bulk input field,
 * where valid and invalid partitions are tested:
 * - valid partition: values from 2 to 100
 * - invalid partition: values below 2, above 100, or empty input
 */

describe('Fake Person Generator E2E', () => {
    beforeEach(() => {
        cy.visit('/index.html')
    })

    it('loads the page correctly', () => {
        cy.contains('h1', 'Fake Person Generator').should('be.visible')
        cy.contains('Generer tilfældig fake-information om ikke-eksisterende danske personer').should('be.visible')
        cy.get('#bulkCount').should('have.value', '5')
    })

    it('generates CPR number', () => {
        cy.contains('button', 'CPR-nummer').click()

        cy.contains('h2', 'Resultat').should('be.visible')
        cy.contains('CPR').should('be.visible')
    })

    it('generates name and gender', () => {
        cy.contains('button', 'Navn & Køn').click()

        cy.contains('h2', 'Resultat').should('be.visible')
        cy.contains('Fornavn').should('be.visible')
        cy.contains('Efternavn').should('be.visible')
        cy.contains('Køn').should('be.visible')
    })

    it('generates name, gender and date of birth', () => {
        cy.contains('button', 'Navn, Køn & Fødselsdato').click()

        cy.contains('h2', 'Resultat').should('be.visible')
        cy.contains('Fornavn').should('be.visible')
        cy.contains('Efternavn').should('be.visible')
        cy.contains('Køn').should('be.visible')
        cy.contains('Fødselsdato').should('be.visible')
    })

    it('generates CPR, name and gender', () => {
        cy.contains('button', 'CPR, Navn & Køn').click()

        cy.contains('h2', 'Resultat').should('be.visible')
        cy.contains('CPR').should('be.visible')
        cy.contains('Fornavn').should('be.visible')
        cy.contains('Efternavn').should('be.visible')
        cy.contains('Køn').should('be.visible')
    })

    it('generates CPR, name, gender and date of birth', () => {
        cy.contains('button', 'CPR, Navn, Køn & Fødselsdato').click()

        cy.contains('h2', 'Resultat').should('be.visible')
        cy.contains('CPR').should('be.visible')
        cy.contains('Fornavn').should('be.visible')
        cy.contains('Efternavn').should('be.visible')
        cy.contains('Køn').should('be.visible')
        cy.contains('Fødselsdato').should('be.visible')
    })

    it('generates address', () => {
        cy.contains('button', 'Adresse').click()

        cy.contains('h2', 'Resultat').should('be.visible')
        cy.contains('Adresse').should('be.visible')
    })

    it('generates mobile number', () => {
        cy.contains('button', 'Mobilnummer').click()

        cy.contains('h2', 'Resultat').should('be.visible')
        cy.contains('Mobilnummer').should('be.visible')
    })

    it('generates a full person', () => {
        cy.contains('button', 'Fuld Person').click()

        cy.contains('h2', 'Resultat').should('be.visible')
        cy.contains('CPR').should('be.visible')
        cy.contains('Fornavn').should('be.visible')
        cy.contains('Efternavn').should('be.visible')
        cy.contains('Køn').should('be.visible')
        cy.contains('Fødselsdato').should('be.visible')
        cy.contains('Adresse').should('be.visible')
        cy.contains('Mobilnummer').should('be.visible')
    })

    // Valid bulk input partition: values between 2 and 100
    ;[2, 5, 100].forEach((count) => {
        it(`accepts valid bulk count ${count}`, () => {
            cy.get('#bulkCount').clear().type(String(count))
            cy.contains('button', 'Generer Bulk').click()

            cy.contains(`Genereret ${count} personer`).should('be.visible')
            cy.contains('Person #1').should('be.visible')
            cy.contains(`Person #${count}`).should('be.visible')
        })
    })

    // Invalid bulk input partition: values below 2 or above 100
    ;[1, 101].forEach((count) => {
        it(`rejects invalid bulk count ${count}`, () => {
            cy.get('#bulkCount').clear().type(String(count))
            cy.contains('button', 'Generer Bulk').click()

            cy.contains('Antal skal være mellem 2 og 100').should('be.visible')
        })
    })

    // Invalid bulk input partition: empty input
    it('rejects empty bulk input', () => {
        cy.get('#bulkCount').clear()
        cy.contains('button', 'Generer Bulk').click()

        cy.contains('Antal skal være mellem 2 og 100').should('be.visible')
    })
})