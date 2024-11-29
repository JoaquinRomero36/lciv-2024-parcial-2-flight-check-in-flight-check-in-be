package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.FlightDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.SeatDto;
import ar.edu.utn.frc.tup.lc.iv.entity.Flight;
import ar.edu.utn.frc.tup.lc.iv.services.FligthServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightControllerTest {
    @Mock
    private FligthServiceImp fligthService;
    @InjectMocks
    private FlightController flightController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getfligthById() {
        Flight flight = new Flight();
        flight.setId("a");
        Mockito.when(fligthService.getFlight("a")).thenReturn(flight);
        ResponseEntity<Flight>  response = flightController.getfligthById("a");
        assertEquals(flight.getId(), response.getBody().getId());

        Mockito.when(fligthService.getFlight("invalid-id")).thenThrow(new IllegalArgumentException());
        ResponseEntity<Flight> response2 = flightController.getfligthById("invalid-id");
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    }

    @Test
    void postfligth() {
        FlightDto dto = new FlightDto();
        dto.setId("a");
        List<SeatDto> seats = new ArrayList<>();
        SeatDto seat = new SeatDto();
        seat.setStatus("available");
        seat.setSeat("1a");
        seats.add(seat);
        dto.setSeatMap(seats);
        Mockito.when(fligthService.createFlight(dto)).thenReturn(new Flight());
        ResponseEntity<Flight>  response = flightController.postfligth(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}