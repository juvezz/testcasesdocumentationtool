Feature: add or delete feature

  Scenario: add feature on feature page
    When open start page of test cases tool
    And click features link
    And add click add new feature
    And write new name for feature file
    And click save button
    And close alert window
    Then new feature file is added

  Scenario: delete feature on feature page
    When open start page of test cases tool
    And click features link
    And add click add new feature
    And write new name for feature file
    And click save button
    And close alert window
    Then new feature file is added
    And delete added feature
    And check new amount of features