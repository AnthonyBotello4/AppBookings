package com.bookings.appbookings.cucumber;

import com.bookings.appbookings.AppBookingsApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = AppBookingsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = AppBookingsApplication.class, loader = SpringBootContextLoader.class)
public class CucumberSpringConfiguration {

}
