package com.bookings.appbookings.repository;

import com.bookings.appbookings.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByDni(String dni);

    List<Customer> findByLastName(String lastname);

    List<Customer> findByFirstNameAndLastName(String firstname, String lastname);

    List<Customer> findByFirstName(String firstname);
}
