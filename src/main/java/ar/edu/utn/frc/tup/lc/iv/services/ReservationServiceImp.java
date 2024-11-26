package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.PassengersDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.ReservationDto;
import ar.edu.utn.frc.tup.lc.iv.entity.Flight;
import ar.edu.utn.frc.tup.lc.iv.entity.Passenger;
import ar.edu.utn.frc.tup.lc.iv.entity.Reservation;
import ar.edu.utn.frc.tup.lc.iv.entity.Seat;
import ar.edu.utn.frc.tup.lc.iv.repository.FligthRepository;
import ar.edu.utn.frc.tup.lc.iv.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImp implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final FligthRepository flightRepository;


    @Transactional
    public ReservationDto putReservation(ReservationDto reservationDto) {
        Reservation reservationEntity = getReservation(reservationDto.getId());
        Flight flightEntity = getFlight(reservationDto.getFlight());
        if(!reservationEntity.getStatus().equals("READY-TO-CHECK-IN")){
            throw new Error("no esta disponible");
        }
        reservationEntity.setStatus("CHECK-IN");

        for (PassengersDto passengerDto : reservationDto.getPassengers()) {
            Optional<Passenger> optPassenger = reservationEntity
                    .getPassengers().stream().filter(
                            m->m.getName().equals(passengerDto.getName())
                    ).findFirst();
            if(optPassenger.isEmpty()){
                throw new Error("El pasajero no esta en la reserva");
            }
            Optional<Seat> optSeat = flightEntity.getSeat_map().stream().filter(
                    m->m.getSeatId().equals(passengerDto.getSeat())
            ).findFirst();
            if(optSeat.isEmpty()){
                throw  new Error("no se encontro el asiento");
            }
            Seat seatEntity = optSeat.get();
            if (seatEntity.getStatus().equals("reserved")){
                throw  new Error("El asiento ya esta reservado");
            }
            seatEntity.setStatus("reserved");
        }
        reservationEntity=reservationRepository.save(reservationEntity);
        ReservationDto dto = new ReservationDto();
        dto.setId(reservationEntity.getId());
        dto.setFlight(reservationEntity.getFlight().getId());
        List<PassengersDto> listPasageros = new ArrayList<>();
        for(Passenger passenger : reservationEntity.getPassengers()){
            PassengersDto p = new PassengersDto();
            p.setName(passenger.getName());
            p.setSeat(passenger.getSeat().getSeatId());
            listPasageros.add(p);
        }
        dto.setPassengers(listPasageros);
        dto.setStatus(reservationEntity.getStatus());
        return dto;
    }
    public Reservation getReservation(String reservationId) {
        Optional<Reservation> optional = reservationRepository.findById(reservationId);
        if (optional.isEmpty()){
            throw new Error("no se encontro la reserva");
        }
        return optional.get();
    }
    @Override
    public Reservation postRservation(ReservationDto reservationDto) {

        Flight fli = getFlight(reservationDto.getFlight());
        if(fli == null){
            throw new Error("no se encontro el vuelo");
        }

        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setFlight(reservation.getFlight());
        reservation.setStatus("READY-TO-CHECK-IN");
        List<Passenger> listPasageros = new ArrayList<>();
        reservation.setPassengers(listPasageros);
        for(PassengersDto passengerDto : reservationDto.getPassengers()){
            Passenger passenger = new Passenger();
            passenger.setName(passengerDto.getName());
            Seat seat = new Seat();
            seat.setFlight(fli);
            seat.setSeatId(passengerDto.getSeat());
            seat.setStatus("reserved");
            passenger.setSeat(seat);
            passenger.setReservation(reservation);
            reservation.getPassengers().add(passenger);
        }
        return reservationRepository.save(reservation);
    }

    public Flight getFlight(String flightId) {
        Optional<Flight> optional = flightRepository.findById(flightId);
        if (optional.isEmpty()){
            throw new Error("No se encontro la flight");
        }
        return optional.get();
    }
}