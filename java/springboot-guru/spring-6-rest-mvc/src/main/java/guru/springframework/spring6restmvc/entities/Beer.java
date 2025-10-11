package guru.springframework.spring6restmvc.entities;

import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Beer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // auto-generate the primary key value
    @Column(length = 36, nullable = false, updatable = false, columnDefinition = "VARCHAR") // constraints on the column
    private UUID id;
    @Version
    private Integer version;

    @NotNull
    @NotBlank
    @Column(length = 50) // with just this, sql exception will be thrown if size exceeds 50. not best for user experience
    @Size(max = 50) // with this, validation exception will be thrown if size exceeds 50. better for user experience
    private String beerName;

    @NotNull
    private BeerStyle beerStyle;

    @NotNull
    @NotBlank
    private String upc;
    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
