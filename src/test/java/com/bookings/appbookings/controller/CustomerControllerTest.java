package com.bookings.appbookings.controller;

import com.bookings.appbookings.entities.Customer;
import com.bookings.appbookings.service.impl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebMvcTest(controllers = CustomerController.class)
@ActiveProfiles("test")
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerServiceImpl customerService;
    private List<Customer> customerList;

    @BeforeEach
    public void setUp() {
        customerList = new ArrayList<>();
        customerList.add(new Customer(1L, "Juan", "Perez", "12345648", "Av Monterrico 12", "987654321", "k@m.com"));
        customerList.add(new Customer(1L, "Juan", "Rojas", "12345638", "Av Monterrico 123f", "987654322", "k@m.com"));
        customerList.add(new Customer(1L, "Rodrigo", "Perez", "12345668", "Av Monterrico 123r", "987654323", "k@m.com"));
        customerList.add(new Customer(1L, "Luis", "Perez", "12345678", "Av Monterrico 1231", "987654324", "k@m.com"));
        customerList.add(new Customer(1L, "Eduardo", "Camones", "12345674", "Av Monterrico 123k", "987654325", "k@m.com"));
        customerList.add(new Customer(1L, "Victor", "Rosales", "12345675", "Av Monterrico 1236", "987654326", "k@m.com"));

    }

    @Test
    void findAllCustomersTest() throws Exception {
        given(customerService.getAll()).willReturn(customerList);
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk());
    }

    @Test
    void findCustomerByIdTest() throws Exception {
        Long customerId = 1L;
        Customer customer = new Customer(1L, "Juan", "Perez",
                "12345648", "Av Monterrico 12", "987654321", "jp@gmail.com");
        given(customerService.getById(1L)).willReturn(Optional.of(customer));
        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isOk());
    }

    @Test
    void findByDniTest() throws Exception {
        String dni = "12345648";
        Customer customer = new Customer(1L, "Juan", "Perez",
                "12345648", "Av Monterrico 12", "987654321", "jp@gmail.com");

        given(customerService.findByDni(dni)).willReturn(customer);
        mockMvc.perform(get("/api/customers/searchByDni/{dni}", dni))
                .andExpect(status().isOk());
    }

    @Test
    void findByLastNameTest() throws Exception {
        String lastname = "Perez";

        given(customerService.findByLastName(lastname))
                .willReturn(customerList.stream()
                        .filter(c -> c.getLastName().equals(lastname))
                        .collect(Collectors.toList()));
        mockMvc.perform(get("/api/customers/searchLastName/{lastname}", lastname))
                .andExpect(status().isOk());
    }

    @Test
    void findByFirstNameTest() throws Exception {
        String firstName = "Juan";

        given(customerService.findByFirstName(firstName))
                .willReturn(customerList.stream()
                        .filter(c -> c.getFirstName().equals(firstName)).collect(Collectors.toList()));
        mockMvc.perform(get("/api/customers/searchFirstName/{firstName}", firstName))
                .andExpect(status().isOk());
    }

    @Test
    void findByFirstnameAndLastnameTest() throws Exception {
        String firstName = "Juan";
        String lastName = "Perez";

        given(customerService.findByFirstNameAndLastName(firstName, lastName))
                .willReturn(customerList.stream()
                        //.filter(c->c.getFirstName().equals(firstName) && c.getLastName().equals(lastName)).collect(Collectors.toList()));
                        .filter(c -> c.getFirstName().equals(firstName))
                        .filter(c -> c.getLastName().equals(lastName))
                        .collect(Collectors.toList()));
        mockMvc.perform(get("/api/customers/searchFirstNameAndLastName/{firstName}/{lastName}", firstName, lastName))
                .andExpect(status().isOk());
    }

    @Test
    void insertCustomertest() throws Exception {
        Customer customer = new Customer(1L, "Juan", "Perez",
                "12345648", "Av Monterrico 12", "987654321", "jp@gmail.com");
        mockMvc.perform(post("/api/customers")
                        .content(asJsonString(customer))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCustomerTest() throws Exception {
        Long id = 1L;
        Customer customer = new Customer(1L, "Juan", "Perez",
                "12345648", "Av Monterrico 12", "987654321", "m.@gmail.com");

        given(customerService.getById(id)).willReturn(Optional.of(customer));
        mockMvc.perform(put("/api/customers/{id}", id)
                        .content(asJsonString(customer))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomerTest() throws Exception {
        Long id = 1L;
        Customer customer = new Customer(1L, "Juan", "Perez",
                "12345648", "Av Monterrico 12", "987654321", "m.@gmail.com");

        given(customerService.getById(id)).willReturn(Optional.of(customer));
        mockMvc.perform(delete("/api/customers/{id}", id))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
