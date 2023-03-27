// https://docs.cypress.io/api/introduction/api.html

describe("Test creating, editing and deleting listings", () => {
  beforeEach(() => {
    // Log in
    cy.visit("/login");
    cy.once('uncaught:exception', () => false);

    // Fill out form
    cy.get("#email-input").type("user@example.org");
    cy.get("#password-input").type("user");
    cy.get("#login-button").click();

    // Check if redirected to home page
    cy.url().should("eq", Cypress.config().baseUrl + "/");
  });

  it("Create listing", () => {
    // Find create listing button
    cy.get("nav #create-listing-navbutton").click();

    // Check if redirected to /create-listing
    cy.url().should("eq", Cypress.config().baseUrl + "/create-listing");

    // Fill out form
    // Write some text in the location picker
    cy.get("#location-picker").type("Oslo");

    // Wait for location picker to load results
    cy.wait(1000);
    cy.get("#location-picker .dropdown-content > div").first().click();

    // Fill in other fields while we wait for the location picker to load results
    cy.get("#title-input input").type("Test Listing Cypress");
    cy.get("#price-input input").type("100");
    cy.get("#category-picker input").type("Diverse");
    cy.get("#summary-input textarea").type("This is a test listing");
    cy.get("#description textarea").type("This is a test listing");


    cy.get('input[type=file]').selectFile('./cypress/fixtures/image.png');

    // Click create listing button
    cy.get("#create-listing-button").click();

    // Check if redirected to /listing/1
    cy.url().should("eq", Cypress.config().baseUrl + "/listing/1");

    // Check if listing is on homepage
    cy.visit("/");
    cy.get(".card").contains("Test Listing Cypress");
  });

  it("Edit listing", () => {
    cy.visit("/listing/1");

    // Click edit listing button
    cy.get("#edit-btn").click();

    // Change title
    cy.get("#title-input input").clear();
    cy.get("#title-input input").type("Test Listing Cypress Edited");

    // Write some text in the location picker
    cy.get("#location-picker").type("Oslo");

    // Wait for location picker to load results
    cy.wait(1000);
    cy.get("#location-picker .dropdown-content > div").first().click();

    // Click save button
    cy.get("#create-listing-button").click();

    // Check if redirected to /listing/1
    cy.url().should("eq", Cypress.config().baseUrl + "/listing/1");

    // Check if the title is changed
    cy.get("#listing-title").contains("Test Listing Cypress Edited");
  });
});
