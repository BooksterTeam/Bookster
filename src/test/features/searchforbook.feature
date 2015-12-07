Feature: This should describe the feature of displaying books

  Scenario: Retrieve searched book
    Given user is authenticated and navigated to the market
    Given user search for 'gesch'
    Then a book is found which has the id 'details1003'

  Scenario: There is no searched book
    Given user is authenticated and navigated to the market
    Given user search for 'jqwerajksndfnjk'
    Then no book is found
