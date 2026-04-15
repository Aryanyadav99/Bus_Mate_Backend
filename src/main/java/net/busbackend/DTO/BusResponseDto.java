package net.busbackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusResponseDto {
    private Long busId;
    private String busName;
    private String busType;
    private int totalSeats;
    private String busNumber;
}
