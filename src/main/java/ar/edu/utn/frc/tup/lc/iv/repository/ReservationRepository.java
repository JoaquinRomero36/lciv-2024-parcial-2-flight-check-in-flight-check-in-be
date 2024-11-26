package ar.edu.utn.frc.tup.lc.iv.repository;


import ar.edu.utn.frc.tup.lc.iv.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

}
