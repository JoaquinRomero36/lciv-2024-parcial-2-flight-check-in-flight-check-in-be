package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.PassengersDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.ReservationDto;
import ar.edu.utn.frc.tup.lc.iv.entity.*;
import ar.edu.utn.frc.tup.lc.iv.repository.FligthRepository;
import ar.edu.utn.frc.tup.lc.iv.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceImpTest {
    @Mock
    private  ReservationRepository reservationRepository;
    @Mock
    private  FligthRepository flightRepository;
    @InjectMocks
    private ReservationServiceImp reservationServiceImp;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void putReservation() {
        Flight flight = new Flight();
        flight.setId("fl");
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat("1","a1",flight);
        seats.add(seat);
        flight.setSeat_map(seats);
        Reservation reservation = new Reservation();
        reservation.setId("res");
        reservation.setStatus("READY-TO-CHECK-IN");
        List<Passenger> listPas = new ArrayList<>();
        Passenger p = new Passenger();
        p.setName("pablo");
        p.setId(1L);
        p.setSeat(seat);
        p.setReservation(reservation);
        listPas.add(p);
        reservation.setPassengers(listPas);


        ReservationDto dto = new ReservationDto();
        dto.setId("aa");
        dto.setStatus("READY-TO-CHECK-IN");
        dto.setFlight("fl");
        List<PassengersDto> listPassengers = new ArrayList<>();
        listPassengers.add(new PassengersDto("pablo", "a1"));
        dto.setPassengers(listPassengers);
        Mockito.when(flightRepository.findById(Mockito.anyString())).thenReturn(Optional.of(flight));
        Mockito.when(reservationRepository.findById(Mockito.anyString())).thenReturn(Optional.of(reservation));
    //     Mockito.when(reservationServiceImp.getReservation("res")).thenReturn(reservation);
    //    Mockito.when(reservationServiceImp.getFlight("fl")).thenReturn(flight);
        ReservationDto result = reservationServiceImp.putReservation(dto);
        assertNotNull(result);
    }

    @Test
    void getReservation() {
        Reservation res = new Reservation();
        res.setId("a");
        Mockito.when(reservationRepository.findById("a")).thenReturn(Optional.of(res));
        Reservation result = reservationServiceImp.getReservation("a");
        assertEquals(res.getId(), result.getId());
    }
}