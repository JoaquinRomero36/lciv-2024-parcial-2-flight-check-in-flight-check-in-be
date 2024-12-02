package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.AirportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.FlightDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.SeatDto;
import ar.edu.utn.frc.tup.lc.iv.entity.Airport;
import ar.edu.utn.frc.tup.lc.iv.entity.Flight;
import ar.edu.utn.frc.tup.lc.iv.entity.Seat;
import ar.edu.utn.frc.tup.lc.iv.repository.AirportRepository;
import ar.edu.utn.frc.tup.lc.iv.repository.FligthRepository;
import ar.edu.utn.frc.tup.lc.iv.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FligthServiceImpTest {

    @Mock
    private FligthRepository fligthRepository;
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private SeatRepository seatRepository;
    @InjectMocks
    private FligthServiceImp fligthServiceImp;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getFlight() {
        Flight fligth = new Flight();
        fligth.setId("a");
        Mockito.when(fligthRepository.findById("a")).thenReturn(Optional.of(fligth));
        Flight result = fligthServiceImp.getFlight("a");
        assertEquals(fligth.getId(), result.getId());
    }

    @Test
    void createFlight() {
        Flight fligth = new Flight();
        fligth.setId("a");
        FlightDto dto = new FlightDto();
        dto.setId(fligth.getId());
        dto.setSeatMap(new ArrayList<>());
        LocalDateTime timepo = LocalDateTime.now().minusHours(7);
        dto.setDeparture(timepo);

        AirportDto airDto = new AirportDto();
        airDto.setCode("asd");
        airDto.setName("asd");
        airDto.setLocation("panamá");
        dto.setAirport(airDto);

        SeatDto seatDto = new SeatDto();
        seatDto.setSeat("a1");
        seatDto.setStatus("available");
        List<SeatDto> seats = new ArrayList<>();
        seats.add(seatDto);
        dto.setSeatMap(seats);

        Seat seat = new Seat();

        Mockito.when(seatRepository.save(seat)).thenReturn(seat);

        List<Seat> list = new ArrayList<>();
        fligth.setSeat_map(list);
        Flight result = fligthServiceImp.createFlight(dto);
        assertNull(result);

        FlightDto dto2 = dto;
        dto2.setDeparture(timepo.plusHours(4));
        Flight result2 = fligthServiceImp.createFlight(dto2);
        assertNull(result2);
    }
}