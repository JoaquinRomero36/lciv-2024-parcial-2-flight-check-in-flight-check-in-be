package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.ReservationDto;
import ar.edu.utn.frc.tup.lc.iv.entity.Reservation;
import ar.edu.utn.frc.tup.lc.iv.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PutMapping("/reservations")
    public ResponseEntity<ReservationDto> updateReservation(@RequestBody ReservationDto reservation) {
        ReservationDto updatedReservation = reservationService.putReservation(reservation);
        return ResponseEntity.ok(updatedReservation);
    }
    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable String id) {
        try{
            return ResponseEntity.ok(reservationService.getReservation(id));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

    }
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservation) {
        try{
            return ResponseEntity.ok(reservationService.postRservation(reservation));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }
}
