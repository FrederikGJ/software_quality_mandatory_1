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

    it('generates bulk persons for a valid number', () => {
        cy.get('#bulkCount').clear().type('5')
        cy.contains('button', 'Generer Bulk').click()

        cy.contains('Genereret 5 personer').should('be.visible')
        cy.contains('Person #1').should('be.visible')
        cy.contains('Person #5').should('be.visible')
    })

    it('shows error when bulk count is too low', () => {
        cy.get('#bulkCount').clear().type('1')
        cy.contains('button', 'Generer Bulk').click()

        cy.contains('Antal skal være mellem 2 og 100').should('be.visible')
    })

    it('shows error when bulk count is too high', () => {
        cy.get('#bulkCount').clear().type('101')
        cy.contains('button', 'Generer Bulk').click()

        cy.contains('Antal skal være mellem 2 og 100').should('be.visible')
    })

    it('shows error when bulk input is empty', () => {
        cy.get('#bulkCount').clear()
        cy.contains('button', 'Generer Bulk').click()

        cy.contains('Antal skal være mellem 2 og 100').should('be.visible')
    })
})