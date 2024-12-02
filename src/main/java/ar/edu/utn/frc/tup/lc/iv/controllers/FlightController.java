package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.FlightDto;
import ar.edu.utn.frc.tup.lc.iv.entity.Flight;
import ar.edu.utn.frc.tup.lc.iv.services.FligthServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FligthServiceImp flightService;
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getfligthById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(flightService.getFlight(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Flight> postfligth(@RequestBody FlightDto fligth) {
        Flight fligthPosted = flightService.createFlight(fligth);
        if(fligthPosted == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(fligthPosted);
    }
}
