package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.PassengersDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.ReservationDto;
import ar.edu.utn.frc.tup.lc.iv.entity.Reservation;
import ar.edu.utn.frc.tup.lc.iv.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationControllerTest {
    @Mock
    private ReservationService reservationService;
    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateReservation() {
        ReservationDto dto = new ReservationDto();
        dto.setStatus("CHEK-NOT-IN");
        dto.setId("a");
        dto.setFlight("aaa");
        List<PassengersDto> list = new ArrayList<>();
        list.add(new PassengersDto("a","a1"));
        Mockito.when(reservationService.putReservation(dto)).thenReturn(dto);
        ResponseEntity<ReservationDto> response = reservationController.updateReservation(dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getReservation() {
        Reservation reservation = new Reservation();
        reservation.setId("a");
        Mockito.when(reservationService.getReservation("a")).thenReturn(reservation);
        ResponseEntity<Reservation> response = reservationController.getReservation("a");
        assertEquals(reservation.getId(), response.getBody().getId());

        Mockito.when(reservationService.getReservation("invalid-id")).thenThrow(new IllegalArgumentException());
        ResponseEntity<Reservation> response2 = reservationController.getReservation("invalid-id");
        assertEquals(400, response2.getStatusCodeValue());
    }

    @Test
    void createReservation() {
        ReservationDto dto =  new ReservationDto();
        dto.setStatus("CHEK-NOT-IN");
        dto.setId("a");
        dto.setFlight("aaa");
        List<PassengersDto> list = new ArrayList<>();
        list.add(new PassengersDto("a","a1"));

        ReservationDto dto2 =  new ReservationDto();
        dto.setId("a");

        Reservation reservation = new Reservation();
        reservation.setId("a");

        Mockito.when(reservationService.postRservation(dto)).thenReturn(reservation);
        ResponseEntity<Reservation> response = reservationController.createReservation(dto);
        assertEquals(200, response.getStatusCodeValue());

        Mockito.when(reservationService.postRservation(dto2)).thenThrow(new IllegalArgumentException());
        ResponseEntity<Reservation> response2 = reservationController.createReservation(dto2);
        assertEquals(400, response2.getStatusCodeValue());
    }
}