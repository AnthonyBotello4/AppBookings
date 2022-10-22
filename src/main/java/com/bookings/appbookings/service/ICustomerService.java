package com.bookings.appbookings.service;

import com.bookings.appbookings.entities.Customer;

import java.util.List;

public interface ICustomerService extends CrudService<Customer> {
    Customer findByDni(String dni) throws Exception;

    List<Customer> findByLastName(String lastname) throws Exception;

    List<Customer> findByFirstNameAndLastName(String firstname, String lastname) throws Exception;

    List<Customer> findByFirstName(String firstname) throws Exception;
}
