Feature: This should describe the process of display book details

  Scenario: Book does not exist in database and the user cant retrieve book details
    When user lookup a book which does not exist with id 'notindatabase'
    Then then the status is 404

  Scenario: Retrieve book details
    Given book is in database
    When user lookup a book with id 'book'
    Then the book is found
    Then the title of the book is 'title'
    Then the isbn of the book is 'isbn'
