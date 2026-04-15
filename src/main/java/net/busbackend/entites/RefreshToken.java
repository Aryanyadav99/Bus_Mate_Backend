package net.busbackend.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class RefreshToken {
    @Id
    private String id;

    private String token;

    @ManyToOne
    private User user;
}
