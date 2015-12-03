Feature: Login
  as a guest user
  I want to login as user

  Scenario: Successfull Login
    Given the username is "user" and the password is "user"
    When the login button is clicked
    Then the dashboard is shown

  Scenario: Login failed
    Given the username is "user" and the password is "incorrect"
    When the login button is clicked
    Then the login page is shown
    And the error message is "Login fehlgeschlagen!"
