package com.bookings.appbookings.service.impl;

import com.bookings.appbookings.entities.Booking;
import com.bookings.appbookings.repository.IBookingRepository;
import com.bookings.appbookings.service.IBookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookingServiceImpl implements IBookingService {

    // Inyección de dependencias
    // @Autowired
    // private IBookingRepository bookingRepository;
    private final IBookingRepository bookingRepository;

    public BookingServiceImpl(IBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public Booking save(Booking booking) throws Exception {
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> getAll() throws Exception {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> getById(Long id) throws Exception {
        return bookingRepository.findById(id);
    }

    @Override
    public List<Booking> findBookingByDates(Date checkin_date, Date checkout_date) throws Exception {
        return bookingRepository.findBookingByDates(checkin_date, checkout_date);
    }
}
