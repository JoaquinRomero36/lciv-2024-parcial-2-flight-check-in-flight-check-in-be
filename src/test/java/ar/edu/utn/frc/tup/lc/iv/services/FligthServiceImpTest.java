package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.FlightDto;
import ar.edu.utn.frc.tup.lc.iv.entity.Flight;
import ar.edu.utn.frc.tup.lc.iv.entity.Seat;
import ar.edu.utn.frc.tup.lc.iv.repository.AirportRepository;
import ar.edu.utn.frc.tup.lc.iv.repository.FligthRepository;
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

class FligthServiceImpTest {

    @Mock
    private FligthRepository fligthRepository;
    @Mock
    private AirportRepository airportRepository;
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

        List<Seat> list = new ArrayList<>();
        fligth.setSeat_map(list);
        //Mockito.when(AirportRepository.save(fligth.getAirport()));
        Flight result = fligthServiceImp.createFlight(dto);
        assertNull(result);
    }
}