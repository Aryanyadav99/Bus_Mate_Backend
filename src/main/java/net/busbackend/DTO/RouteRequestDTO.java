package net.busbackend.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequestDTO {
    @NotBlank
    private String cityFrom;
    @NotBlank
    private String cityTo;
    @NotBlank
    private Double distanceInKm;
}
