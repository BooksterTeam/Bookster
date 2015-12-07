Feature: This should describe the process of display book details

  Scenario: Retrieve book details
    Given user authenticated and navigated to the market
    When user lookup a book with id '1001'
    Then the title of the book is 'Master Software Engineering'
    Then the isbn of the book is '1401'
