package com.bookings.appbookings.service;

import com.bookings.appbookings.entities.Customer;
import com.bookings.appbookings.repository.ICustomerRepository;
import com.bookings.appbookings.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock
    private ICustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void saveTest(){
        Customer  customer = new Customer(1L, "Juan",
                "Perez", "12345678", "Av Monterrico 123",
                "987654321", "jp@gmail.com");

        given(customerRepository.save(customer)).willReturn(customer);

        Customer savedCustomer = null;

        try {
            savedCustomer = customerService.save(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThat(savedCustomer).isNotNull();
        assertEquals(customer, savedCustomer);
    }

    @Test
    public void deleteTest() throws Exception {
        Long id = 1L;
        customerService.delete(id);
        verify(customerRepository, times(1)).deleteById(id);
    }

    @Test
    public void getAllTest() throws Exception {
        List<Customer> list = new ArrayList<>();
        list.add( new Customer(1L, "Juan",
                "Perez", "12345678", "Av Monterrico 123",
                "987654321", "a@km.c"));
        list.add( new Customer(2L, "Daniel", "Perez",
                "12345678", "Av Monterrico 123",
                "987654321", "2@m.c"));
        list.add( new Customer(3L, "Eduardo", "Perez",
                "12345678", "Av Monterrico 123",
                "987654321", "2@m.c"));
        list.add( new Customer(4L, "Raúl", "Perez",
                "12345678", "Av Monterrico 123",
                "987654321", "2@m.c"));
        list.add( new Customer(5L, "Rosa", "Perez",
                "12345678", "Av Monterrico 123",
                "987654321", "2@m.c"));

        given(customerRepository.findAll()).willReturn(list);
        List<Customer> listExpected = customerService.getAll();
        assertEquals(listExpected, list);
    }

    @Test
    public void getByIdTest() throws Exception{
        Long id = 1L;
        Customer customer = new Customer(1L, "Juan",
                "Perez", "12345678", "Av Monterrico 123",
                "987654321", "jp@gmail.com");

        given(customerRepository.findById(id)).willReturn(Optional.of(customer));
        Optional<Customer> customerExpected = customerService.getById(id);
        assertEquals(customerExpected, Optional.of(customer));
    }

    @Test
    public void findByDNITest() throws Exception {
        String dni = "12345678";
        Customer customer = new Customer(1L, "Juan",
                "Perez", "12345678", "Av Monterrico 123",
                "987654321", "@ksks.c");

        given(customerRepository.findByDni(dni)).willReturn(customer);
        Customer customerExpected = customerService.findByDni(dni);
        assertThat(customerExpected).isNotNull();
        assertEquals(customerExpected, customer);
    }

    @Test
    public void findByLastName() throws Exception {
        String lastName = "Perez";
        List<Customer> list = new ArrayList<>();
        list.add( new Customer(1L, "Juan",
                "Perez", "12345678", "Av Monterrico 123",
                "987654321", "a@km.c"));
        list.add( new Customer(2L, "Daniel", "Perez",
                "12345678", "Av Monterrico 123",
                "987654321", "2@m.c"));
        list.add( new Customer(3L, "Eduardo", "Perez",
                "12345678", "Av Monterrico 123",
                "987654321", "2@m.c"));
        list.add( new Customer(4L, "Raúl", "Perez",
                "12345678", "Av Monterrico 123",
                "987654321", "2@m.c"));
        list.add( new Customer(5L, "Rosa", "Perez",
                "12345678", "Av Monterrico 123",
                "987654321", "2@m.c"));

        given(customerRepository.findByLastName(lastName)).willReturn(list);
        List<Customer> listExpected = customerService.findByLastName(lastName);
        assertEquals(listExpected, list);
    }

    @Test
    public void findByFirstName() throws Exception {
        String firstname = "Juan";
        List<Customer> list = new ArrayList<>();
        list.add( new Customer(1L, "Juan",
                "Perez", "12345678", "Av Monterrico 123",
                "987654321", "a@km.c"));
        list.add( new Customer(2L, "Juan", "Rojas",
                "12345678", "Av Monterrico 123",
                "987654321", "2@m.c"));
        list.add( new Customer(3L, "Juan", "Quispe",
                "12345678", "Av Monterrico 123",
                "987654321", "2@m.c"));
        list.add( new Customer(4L, "Juan", "Perez",
                "12345678", "Av Monterrico 123",
                "987654321", "2@m.c"));
        list.add( new Customer(5L, "Juan", "Perez",
                "12345678", "Av Monterrico 123",
                "987654321", "2@m.c"));

        given(customerRepository.findByFirstName(firstname)).willReturn(list);
        List<Customer> listExpected = customerService.findByFirstName(firstname);
        assertEquals(listExpected, list);
    }

    @Test
    public void findByFirstnameAndLastName() throws Exception {
        String firstname = "Juan";
        String lastname = "Perez";
        List<Customer> list = new ArrayList<>();
        list.add(new Customer(1L, "Juan",
                "Perez", "12345678", "Av Monterrico 123",
                "987654321", "a@km.c"));
        list.add(new Customer(2L, "Juan",
                "Perez", "12345678", "Av Monterrico 123",
                "987654321", "a@km.c"));
        list.add(new Customer(3L, "Juan",
                "Perez", "12345678", "Av Monterrico 123",
                "987654321", "a@km.c"));
        list.add(new Customer(4L, "Juan",
                "Perez", "12345678", "Av Monterrico 123",
                "987654321", "a@km.c"));

        given(customerRepository.findByFirstNameAndLastName(firstname, lastname)).willReturn(list);
        List<Customer> listExpected = customerService.findByFirstNameAndLastName(firstname, lastname);
        assertEquals(listExpected, list);

    }
}
