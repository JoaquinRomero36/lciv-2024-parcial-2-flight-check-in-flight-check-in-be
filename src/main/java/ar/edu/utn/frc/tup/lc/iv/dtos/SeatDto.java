package ar.edu.utn.frc.tup.lc.iv.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeatDto {
    private String seat;
    private String status;
}
