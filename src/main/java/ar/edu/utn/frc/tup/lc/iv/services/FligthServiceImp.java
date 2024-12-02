package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.FlightDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.SeatDto;
import ar.edu.utn.frc.tup.lc.iv.entity.Airport;
import ar.edu.utn.frc.tup.lc.iv.entity.Flight;
import ar.edu.utn.frc.tup.lc.iv.entity.Seat;
import ar.edu.utn.frc.tup.lc.iv.repository.AirportRepository;
import ar.edu.utn.frc.tup.lc.iv.repository.FligthRepository;
import ar.edu.utn.frc.tup.lc.iv.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FligthServiceImp implements FligthSrevice{

    @Autowired
    private FligthRepository fligthRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private AirportRepository airportRepository;

    @Override
    public Flight getFlight(String id) {
       return fligthRepository.findById(id).orElse(null);
    }

    @Override
    public Flight createFlight(FlightDto flight) {
        Flight fli = new Flight();

        Airport airport = new Airport();
        fli.setAircraft(flight.getAircraft());
        fli.setId(flight.getId());
        if(!timeValidator(flight.getDeparture())){
            return null;
        }
        fli.setDeparture(flight.getDeparture());

        if(flight.getAirport() != null){
            airport.setCode(flight.getAirport().getCode());
            airport.setName(flight.getAirport().getName());
            airport.setLocation(flight.getAirport().getLocation());
           fli.setAirport(airportRepository.save(airport));
        }

        List<Seat> listSeat = new ArrayList<>();

        for(SeatDto seat : flight.getSeatMap()){
            Seat seat1 = new Seat();
            seat1.setStatus("available");
            seat1.setSeatId(seat.getSeat());
            seat1.setFlight(fligthRepository.save(fli));
            listSeat.add(seatRepository.save(seat1));
        }
        fli.setSeat_map(listSeat);
        return fligthRepository.save(fli);
    }

    public boolean timeValidator(LocalDateTime time){
        LocalDateTime now = LocalDateTime.now().minusHours(6L);
        if(time.isBefore(now)){
            return true;
        }
        return false;
    }
}
