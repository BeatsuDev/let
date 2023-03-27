// https://docs.cypress.io/api/introduction/api.html

describe("Sending and receiving chats", () => {
  it("Send a chat", () => {
    // Log in as admin in order to contact the seller of a test listing
    // (most test listings are created by the normal example user)
    cy.visit("/login");
    cy.once('uncaught:exception', () => false);

    cy.get("#email-input").type("admin@example.org");
    cy.get("#password-input").type("admin");
    cy.get("#login-button").click();

    // Check if redirected to home page
    cy.url().should("eq", Cypress.config().baseUrl + "/");

    // Click on the first listing
    cy.get(".card").first().click();

    // Contact seller
    cy.get("#contact-seller-button").click();

    // Check if redirected to /chat/1
    cy.url().should("eq", Cypress.config().baseUrl + "/chats/1");

    // Send a chat
    cy.get("#chat-input").type("This is a test chat");

    // Click send button
    cy.get("#send-chat-button").click();

    // Check if chat is displayed
    cy.get(".chat-message").contains("This is a test chat");
  });

  it("Receiving chat", () => {
    cy.visit("/login");
    cy.once('uncaught:exception', () => false);

    cy.get("#email-input").type("user@example.org");
    cy.get("#password-input").type("user");
    cy.get("#login-button").click();

    // Click on chats nav button
    cy.get("nav #chats-navbutton").click();

    // Check if redirected to /chats
    cy.url().should("eq", Cypress.config().baseUrl + "/chats");

    // Click on the first chat
    cy.get(".chat").first().click();

    // Check if redirected to /chat/1
    cy.url().should("eq", Cypress.config().baseUrl + "/chats/1");

    // Check if chat is displayed
    cy.get(".chat-message").contains("This is a test chat");
  });
});
