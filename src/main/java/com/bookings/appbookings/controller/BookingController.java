package com.bookings.appbookings.controller;

import com.bookings.appbookings.entities.Booking;
import com.bookings.appbookings.entities.Customer;
import com.bookings.appbookings.service.IBookingService;
import com.bookings.appbookings.service.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
@Api(value = "Web Service RESTful of Bookings")
public class BookingController {

    private final IBookingService bookingService;
    private final ICustomerService customerService;

    public BookingController(IBookingService bookingService, ICustomerService customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Bookings List", notes = "Method to list all bookings")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Bookings found"),
            @ApiResponse(code = 404, message = "Bookings not found")
    })
    public ResponseEntity<List<Booking>> findAllBookings() {
        try {
            List<Booking> bookings = bookingService.getAll();
            if (bookings.size() > 0)
                return new ResponseEntity<>(bookings, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Booking Search by Id", notes = "Method to find a bookings by id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Booking found"),
            @ApiResponse(code = 404, message = "Booking not found"),
            @ApiResponse(code = 501, message = "Internal server error")
    })
    public ResponseEntity<Booking> findBookingById(@PathVariable("id") Long id) {
        try {
            Optional<Booking> booking = bookingService.getById(id);
            if (!booking.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(booking.get(), HttpStatus.OK);
            // Como es un Optional se require del método get para devolver el elemento
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchBetweenDates", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Booking Search between dates", notes = "Method to find a bookings between dates")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Bookings found"),
            @ApiResponse(code = 404, message = "Bookings not found"),
            @ApiResponse(code = 501, message = "Internal server error")
    })
    public ResponseEntity<List<Booking>> findBookingBetweenDates(
            @RequestParam(name="checkin_date") String checkin_string,
            @RequestParam(name = "checkout_date") String checkout_string) {
        try{
            Date checkin_date = ParseDate(checkin_string);
            Date checkout_date = ParseDate(checkout_string);
            List<Booking> bookings = bookingService.findBookingByDates(checkin_date, checkout_date);
            if(bookings.size()>0)
                return new ResponseEntity<>(bookings, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static Date ParseDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date result = null;
        try{
            result = format.parse(date);
        }catch (Exception ex){}
        return result;
    }

    @PostMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Register one customer booking", notes = "Method to register a new booking")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Booking created"),
            @ApiResponse(code = 404, message = "Booking not created"),
            @ApiResponse(code = 501, message = "Internal server error")
    })
    public ResponseEntity<Booking> insertBooking(@PathVariable("id") Long id,
                                                 @Valid @RequestBody Booking booking){
        try{
            Optional<Customer> customer = customerService.getById(id);
            if(customer.isPresent()){
                booking.setCustomer(customer.get());
                Booking bookingNew = bookingService.save(booking);
                return ResponseEntity.status(HttpStatus.CREATED).body(bookingNew);
            }
            else return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);

        }catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Booking", notes = "Method to update a booking")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Booking updated"),
            @ApiResponse(code = 404, message = "Booking not updated"),
            @ApiResponse(code = 501, message = "Internal server error")
    })
    public ResponseEntity<Booking> updateBooking(@PathVariable("id") Long idBooking,
                                                 @Valid @RequestBody Booking booking )
    {
        try{
            Optional<Booking> bookingOld = bookingService.getById(idBooking);
            if (!bookingOld.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else {
                booking.setId(idBooking);
                return new ResponseEntity<>(booking, HttpStatus.OK);
            }
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete Booking by id", notes = "Method to delete a booking")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Booking deleted"),
            @ApiResponse(code = 404, message = "Booking not found"),
            @ApiResponse(code = 501, message = "Internal server error")
    })
    public ResponseEntity<Booking> deleteBooking(@PathVariable("id") Long id){
        try{
            Optional<Booking> bookingDelete = bookingService.getById(id);
            if(bookingDelete.isPresent()){
                bookingService.delete(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}

