package br.com.fiap.mottooth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_REGISTRO_BATERIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroBateria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REGISTRO")
    private Long id;

    @Column(name = "DATA_HORA")
    private LocalDateTime dataHora = LocalDateTime.now();

    @Min(value = 0, message = "O nível de bateria deve ser no mínimo 0")
    @Max(value = 100, message = "O nível de bateria deve ser no máximo 100")
    @Column(name = "NIVEL_BATERIA")
    private Integer nivelBateria;

    @ManyToOne
    @JoinColumn(name = "ID_BEACON")
    private Beacon beacon;
}
