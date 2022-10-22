package com.bookings.appbookings.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

// Lombok necesita que sea serializable

@Entity
// Para nombrar la entidad
@Table(name = "bookings")
// Construir getters y setters
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description", nullable = false, length = 200)
    private String description;
    @Column(name = "number_persons", nullable = false)
    private int numberOfPersons;
    @Column(name = "create_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Temporal(TemporalType.DATE)
    @Column(name = "chekin_date", nullable = false)
    private Date checkinDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "chekout_date", nullable = false)
    private Date checkoutDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Customer customer;
}
