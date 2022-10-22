package com.bookings.appbookings.controller;

import com.bookings.appbookings.entities.Customer;
import com.bookings.appbookings.service.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>>findAll(){
        try{
            List<Customer> customers = customerService.getAll();
            if(customers.size() > 0)
                return new ResponseEntity<>(customers, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> findById(@PathVariable("id") Long id){
        try{
            Optional<Customer> customer = customerService.getById(id);
            if(!customer.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/searchByDni/{dni}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> findByDni(@PathVariable("dni") String dni){
        try{
            Customer customer = customerService.findByDni(dni);
            if(customer==null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(customer, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchLastName/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> findByLastName(@PathVariable ("lastname") String lastname){
        try{
            List<Customer> customers = customerService.findByLastName(lastname);
            if(customers.size() > 0)
                return new ResponseEntity<>(customers, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchFirstName/{firstname}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> findByFirstName(@PathVariable ("firstname") String firstname){
        try{
            List<Customer> customers = customerService.findByFirstName(firstname);
            if(customers.size() > 0)
                return new ResponseEntity<>(customers, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchFirstNameAndLastName/{firstname}/{lastname}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> findByFirstNameAndLastName(
            @PathVariable ("firstname") String firstname,
            @PathVariable ("lastname") String lastname)
    {
        try{
            List<Customer> customers = customerService.findByFirstNameAndLastName(firstname, lastname);
            if(customers.size() > 0)
                return new ResponseEntity<>(customers, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> insertCustomer(@Valid @RequestBody Customer customer){
        try{
            Customer customerNew = customerService.save(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(customerNew);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer (@PathVariable ("id") Long id, @Valid @RequestBody Customer customer){
        try{

            Optional<Customer> customerUpdate = customerService.getById(id);

            if(!customerUpdate.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            customer.setId(id);
            customerService.save(customer);
            return new ResponseEntity<>(customer, HttpStatus.OK);

        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id){
        try{
            Optional<Customer> customerDelete = customerService.getById(id);
            if(!customerDelete.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else
                customerService.delete(id);
                return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
