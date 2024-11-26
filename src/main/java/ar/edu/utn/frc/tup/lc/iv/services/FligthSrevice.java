package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.FlightDto;
import ar.edu.utn.frc.tup.lc.iv.entity.Flight;

public interface FligthSrevice {
    Flight getFlight(String id);
    Flight createFlight(FlightDto flight);
}
