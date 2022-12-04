Feature: Customer adding
  As s Developer
  I want to add a customer through the API
  So that it can be available to applications.

  Background:
    Given the endpoint "http://localhost:%d/api/customers" is available

  @post-adding
  Scenario: Add a customer
    When a customer request is sent with values "Juan", "Perez", "18574613", "Av Aliaga 132", "999999999", "jp@upc.edu.pe"
    Then a customer with status code 201 is received

  @delete-customer
  Scenario: Delete a customer
    When a customer delete is sent with id value "5"
    Then a customer with status code 200 is received

  @get-customer-by-id
  Scenario: Get cutomer by id
    When a customer selected is sent with id value "4"
    Then a customer with status code 200 is received

  @get-all-customers
  Scenario: Get all customers
    When all customers who are registered in DB
    Then List of customers with status code 200 is received

  @update-customer
  Scenario: Update a customer
    When a customer update is sent with id value "4" and values "Victor", "Perez", "18574621", "Av Aliaga 987", "999999999", "vz@gmai.com"
    Then a customer with status code 200 is received