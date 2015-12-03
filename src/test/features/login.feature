Feature: Sign in
  as a guest user
  I want to Sign in as user

  Scenario: Successfull Sign in
    Given the username is "user" and the password is "user"
    When the sign in button is clicked
    Then the dashboard is shown

  Scenario: Sign in failed
    Given the username is "user" and the password is "incorrect"
    When the sign in button is clicked
    Then the sign in page is shown