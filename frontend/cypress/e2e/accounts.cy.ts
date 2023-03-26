// https://docs.cypress.io/api/introduction/api.html

describe("Test registering and logging in", () => {
  it('visits the app root url', () => {
    cy.visit("/");
    cy.once('uncaught:exception', () => false);
    cy.get("nav #login-navbutton").click();
    cy.get("#no-account-button").click();
  });

  it("Register", () => {
    cy.visit("/register");
    cy.once('uncaught:exception', () => false);

    // Fill out form
    cy.get("#first-name-input").type("Test");
    cy.get("#last-name-input").type("Cypress");
    cy.get("#email-input").type("test@cypress.com");
    cy.get("#password-input").type("test1234");
    cy.get("#password-repeat-input").type("test1234");
    cy.get("#register-button").click();

    // Check if redirected to home page and have access to logout button and other nav things
    cy.url().should("eq", Cypress.config().baseUrl + "/");
    cy.get("nav #logout-navbutton").should("exist");
    cy.get("nav #mypage-navbutton").should("exist");
    cy.get("nav #create-listing-navbutton").should("exist");
    cy.get("nav #chats-navbutton").should("exist");

    cy.get("nav #login-navbutton").should("not.exist");
  });

  it("Login User", () => {
    cy.visit("/login");
    cy.once('uncaught:exception', () => false);

    // Fill out form
    cy.get("#email-input").type("test@cypress.com");
    cy.get("#password-input").type("test1234");
    cy.get("#login-button").click();

    // Check if redirected to home page and have access to logout button and other nav things
    cy.url().should("eq", Cypress.config().baseUrl + "/");
    cy.get("nav #logout-navbutton").should("exist");
    cy.get("nav #mypage-navbutton").should("exist");
    cy.get("nav #create-listing-navbutton").should("exist");
    cy.get("nav #chats-navbutton").should("exist");
    cy.get("nav #admin-panel-navbutton").should("not.exist");

    // Log out
    cy.get("nav #logout-navbutton").click();
  });

  it("Log out", () => {
    cy.visit("/login");
    cy.once('uncaught:exception', () => false);

    // Fill out form
    cy.get("#email-input").type("test@cypress.com");
    cy.get("#password-input").type("test1234");
    cy.get("#login-button").click();

    // Logout
    cy.get("nav #logout-navbutton").click();

    // Check if redirected to home page and does not have access to chats, admin panel, etc.
    cy.url().should("eq", Cypress.config().baseUrl + "/");
    cy.get("nav #logout-navbutton").should("not.exist");
    cy.get("nav #mypage-navbutton").should("not.exist");
    cy.get("nav #create-listing-navbutton").should("not.exist");
    cy.get("nav #chats-navbutton").should("not.exist");
    cy.get("nav #admin-panel-navbutton").should("not.exist");

    // Log in button should exist
    cy.get("nav #login-navbutton").should("exist");
  });

  it("Login Admin", () => {
    cy.visit("/login");
    cy.once('uncaught:exception', () => false);

    // Fill out form
    cy.get("#email-input").type("admin@example.org");
    cy.get("#password-input").type("admin");
    cy.get("#login-button").click();

    // Check if redirected to home page and have access to logout button and other nav things
    cy.url().should("eq", Cypress.config().baseUrl + "/");
    cy.get("nav #logout-navbutton").should("exist");
    cy.get("nav #mypage-navbutton").should("exist");
    cy.get("nav #create-listing-navbutton").should("exist");
    cy.get("nav #chats-navbutton").should("exist");
    cy.get("nav #admin-panel-navbutton").should("exist");

    // Log out
    cy.get("nav #logout-navbutton").click();
  });
});
