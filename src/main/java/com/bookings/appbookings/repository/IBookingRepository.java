package com.bookings.appbookings.repository;

import com.bookings.appbookings.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

// Clase - Tipo de dato del PK
@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {
    @Query("select b from Booking b where b.checkinDate =: checkin and b.checkoutDate=:checkout")
    List<Booking> findBookingByDates(@Param("checkin") Date checkin_date,
                                     @Param("checkout") Date checkout_date);
}
