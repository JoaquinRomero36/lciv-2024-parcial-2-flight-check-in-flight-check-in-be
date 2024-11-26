package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.ReservationDto;
import ar.edu.utn.frc.tup.lc.iv.entity.Reservation;

public interface ReservationService {
    ReservationDto putReservation(ReservationDto reservationDto);
    Reservation getReservation(String reservationId);
    Reservation postRservation(ReservationDto reservationDto);
}
