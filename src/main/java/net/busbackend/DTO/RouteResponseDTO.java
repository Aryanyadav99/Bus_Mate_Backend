package net.busbackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponseDTO {
    private Long routeId;
    private String cityFrom;
    private String cityTo;
    private Double distanceInKm;
}
