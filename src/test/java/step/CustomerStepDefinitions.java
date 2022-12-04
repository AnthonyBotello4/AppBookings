package step;

import com.bookings.appbookings.entities.Customer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerStepDefinitions {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int randomServerPort;
    private String endpointPath;
    private ResponseEntity<String> responseEntity;

    @Given("the endpoint {string} is available")
    public void theEndpointIsAvailable(String endpointPath) {
        this.endpointPath = String.format(endpointPath, randomServerPort);
    }

    @When("a customer request is sent with values {string}, {string}, {string}, {string}, {string}, {string}")
    public void aCustomerRequestIsSentWithValues(String firstName, String lastName, String dni, String address, String phone, String email) {
        Customer customer = new Customer(0L, firstName, lastName, dni, address, phone, email);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Customer> request = new HttpEntity<>(customer, headers);
        responseEntity = testRestTemplate.postForEntity(endpointPath, request, String.class);
    }

    @Then("a customer with status code {int} is received")
    public void aCustomerWithStatusCodeIsReceived(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @When("a customer delete is sent with id value {string}")
    public void aCustomerDeleteIsSentWithIdValue(String id_customer) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id_customer);
        testRestTemplate.delete(endpointPath+"/{id}", params);
        responseEntity= new ResponseEntity<>(HttpStatus.OK);
    }

    @When("a customer selected is sent with id value {string}")
    public void aCustomerSelectedIsSentWithIdValue(String id_customer) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id_customer);
        Customer customer = testRestTemplate.getForObject(endpointPath+"/{id}",Customer.class, params);
        responseEntity= new ResponseEntity<>(customer.toString(), HttpStatus.OK);

    }

    @When("all customers who are registered in DB")
    public void allCustomersWhoAreRegisteredInDB() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        responseEntity = testRestTemplate.exchange(endpointPath, HttpMethod.GET, entity, String.class);
        System.out.println(responseEntity);
    }

    @Then("List of customers with status code {int} is received")
    public void listOfCustomersWithStatusCodeIsReceived(int expectedStatusCode) {
        int actualStatusCode = responseEntity.getStatusCodeValue();
        assertThat(expectedStatusCode).isEqualTo(actualStatusCode);
    }

    @When("a customer update is sent with id value {string} and values {string}, {string}, {string}, {string}, {string}, {string}")
    public void aCustomerUpdateIsSentWithIdValueAndValues(String id_customer, String firstName, String lastName, String dni, String address, String phone, String email) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id_customer);
        Customer customerUpdated = new Customer(0L, firstName, lastName, dni, address, phone, email);
        testRestTemplate.put(endpointPath+"/{id}", customerUpdated, params);
        responseEntity= new ResponseEntity<>(customerUpdated.toString(), HttpStatus.OK);
    }
}
