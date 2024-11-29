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
    @Mock
    private FligthServiceImp fligthService;
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
        Seat seat = new Seat("a1","avaiable",flight);
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
        reservation.setFlight(flight);


        ReservationDto dto = new ReservationDto();
        dto.setId("aa");
        dto.setStatus("READY-TO-CHECK-IN");
        dto.setFlight("fl");
        List<PassengersDto> listPassengers = new ArrayList<>();
        listPassengers.add(new PassengersDto("pablo", "a1"));
        dto.setPassengers(listPassengers);
        Mockito.when(flightRepository.findById(Mockito.anyString())).thenReturn(Optional.of(flight));
        Mockito.when(reservationRepository.findById(Mockito.anyString())).thenReturn(Optional.of(reservation));
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);
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

    @Test
    void postRservation() {
        ReservationDto reservadto = new ReservationDto();
        reservadto.setId("a");
        reservadto.setStatus("READY-TO-CHECK-IN");

        Flight flight = new Flight();
        flight.setId("aaa");
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat("1B","avaiable",flight));
        flight.setSeat_map(seats);
        Mockito.when(flightRepository.findById("aaa")).thenReturn(Optional.of(flight));

        reservadto.setFlight("aaa");
        List<PassengersDto> listPas = new ArrayList<>();
        PassengersDto p = new PassengersDto();
        p.setName("pablo");
        p.setSeat("1B");
        listPas.add(p);
        reservadto.setPassengers(listPas);
        Reservation res = new Reservation();
        res.setId("a");
     //   Mockito.when(reservationRepository.save(res)).thenReturn(res);
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Reservation response = reservationServiceImp.postRservation(reservadto);
        assertEquals("a", response.getId());
    }

    @Test
    void testThrowErrorWhenFlightNotFound() {

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setFlight("invalid-flight-id");
        Mockito.when(fligthService.getFlight("invalid-flight-id")).thenReturn(null);

        Error exception = assertThrows(Error.class, () -> {
            reservationServiceImp.postRservation(reservationDto);
        });

        assertEquals("No se encontro la flight", exception.getMessage());
    }
}