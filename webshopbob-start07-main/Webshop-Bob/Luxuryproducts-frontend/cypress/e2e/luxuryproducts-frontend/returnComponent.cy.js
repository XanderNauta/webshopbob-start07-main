context('Return customer', () => {


    it("Should go to return page", ()=>{
        cy.intercept("POST", 'http://localhost:8080/api/account/login', {fixture: 'loginResponse.json'});
        cy.intercept('GET', '/api/retour', { fixture: 'product.json' }).as('product');

        cy.visit('/account/login');


        cy.get('input[formControlName="email"]').type('bob@bobsluxuryenterprise.com');
        cy.get('input[formControlName="password"').type('Test123!');
        cy.get('button.btn').click();

        cy.visit('/return');
        cy.url().should("include", '/return');


    })

    it("Should go to forms page", ()=> {
        cy.intercept("POST", 'http://localhost:8080/api/account/login', {fixture: 'loginResponse.json'});
        cy.intercept('GET', '/api/retour', { fixture: 'product.json' }).as('product');

        cy.visit('/account/login');


        cy.get('input[formControlName="email"]').type('bob@bobsluxuryenterprise.com');
        cy.get('input[formControlName="password"').type('Test123!');
        cy.get('button.btn').click();
        cy.visit('/return');

        cy.visit('/returnFormulier')
        cy.visit('/return');

    })

    it("Should send the form with button submit", ()=> {
        cy.intercept("POST", 'http://localhost:8080/api/account/login', {fixture: 'loginResponse.json'});
        cy.intercept('GET', '/api/retour', {fixture: 'product.json'}).as('product');

        cy.visit('/account/login');


        cy.get('input[formControlName="email"]').type('bob@bobsluxuryenterprise.com');
        cy.get('input[formControlName="password"').type('Test123!');
        cy.get('button.btn').click();
        cy.visit('/return');

        cy.visit('/returnFormulier')
        cy.get("button.btn-primary").click()
        cy.visit('/return');
    })

    it("Should cancel the form with button cancel", ()=> {
        cy.intercept("POST", 'http://localhost:8080/api/account/login', {fixture: 'loginResponse.json'});
        cy.intercept('GET', '/api/retour', {fixture: 'product.json'}).as('product');

        cy.visit('/account/login');


        cy.get('input[formControlName="email"]').type('bob@bobsluxuryenterprise.com');
        cy.get('input[formControlName="password"').type('Test123!');
        cy.get('button.btn').click();
        cy.visit('/return');

        cy.visit('/returnFormulier')
        cy.get("button.btn-danger").click()
        cy.visit('/return');
    })


})

context("return Admin", ()=>{
    it('should go to admin panel', () => {
        cy.intercept("POST", 'http://localhost:8080/api/account/login', {fixture: 'loginResponseAdmin.json'});
        cy.intercept("GET", ' http://localhost:8080/api/retour/requests', {fixture: 'requestResponse.json'})


        cy.visit("/account/login")
        cy.get('[formControlName="email"]').type('bob@bobsluxuryenterprise.com');
        cy.get('[formControlName="password"]').type('Test123!');
        cy.get('button.btn').click();
        cy.visit('/returnAdmin')
    })

        it('should press accept to stock in admin panel', () => {
        cy.intercept("POST", 'http://localhost:8080/api/account/login', {fixture: 'loginResponseAdmin.json'});
        cy.intercept("GET", ' http://localhost:8080/api/retour/requests', {fixture: 'requestResponse.json'})
        cy.intercept("POST", ' http://localhost:8080/api/retour/return/tostock', {fixture: 'requestResponse.json'})


        cy.visit("/account/login")
        cy.get('[formControlName="email"]').type('bob@bobsluxuryenterprise.com');
        cy.get('[formControlName="password"]').type('Test123!');
        cy.get('button.btn').click();
        cy.get('#requests').click()
        cy.get('button.btn-success').click();
    })

    it('should press accept not to stock in admin panel', () => {
        cy.intercept("POST", 'http://localhost:8080/api/account/login',
            {fixture: 'loginResponseAdmin.json'});
        cy.intercept("GET", ' http://localhost:8080/api/retour/requests',
            {fixture: 'requestResponse.json'})
        cy.intercept("POST", ' http://localhost:8080/api/retour/return/accept',
            {fixture: 'requestResponse.json'})


        cy.visit("/account/login")
        cy.get('[formControlName="email"]').type('bob@bobsluxuryenterprise.com');
        cy.get('[formControlName="password"]').type('Test123!');
        cy.get('button.btn').click();
        cy.get('#requests').click()
        cy.get('button.btn-primary').click();
    })

    it('should press decline return in admin panel', () => {
        cy.intercept("POST", 'http://localhost:8080/api/account/login',
            {fixture: 'loginResponseAdmin.json'});
        cy.intercept("GET", ' http://localhost:8080/api/retour/requests',
            {fixture: 'requestResponse.json'})
        cy.intercept("POST", ' http://localhost:8080/api/retour/return/decline',
            {fixture: 'requestResponse.json'})

        cy.visit("/account/login")
        cy.get('[formControlName="email"]').type('bob@bobsluxuryenterprise.com');
        cy.get('[formControlName="password"]').type('Test123!');
        cy.get('button.btn').click();
        cy.get('#requests').click()
        cy.get('button.btn-danger').click();
    })

    it('should type in the orderID', () => {
        cy.intercept("POST", 'http://localhost:8080/api/account/login', {fixture: 'loginResponseAdmin.json'});
        cy.intercept("GET", ' http://localhost:8080/api/retour/requests', {fixture: 'requestResponse.json'})


        cy.visit("/account/login")
        cy.get('[formControlName="email"]').type('bob@bobsluxuryenterprise.com');
        cy.get('[formControlName="password"]').type('Test123!');
        cy.get('button.btn').click();
        cy.get('#requests').click()
        cy.get('#clickhier').type('5d6');
    })


})