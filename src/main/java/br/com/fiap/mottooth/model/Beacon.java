package br.com.fiap.mottooth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_BEACON")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Beacon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BEACON")
    private Long id;

    @NotBlank(message = "O UUID do beacon é obrigatório")
    @Size(max = 100, message = "O UUID deve ter no máximo 100 caracteres")
    @Column(name = "UUID", length = 100, nullable = false, unique = true)
    private String uuid;

    @Min(value = 0, message = "O nível de bateria deve ser no mínimo 0")
    @Max(value = 100, message = "O nível de bateria deve ser no máximo 100")
    @Column(name = "BATERIA")
    private Integer bateria;

    @OneToOne
    @JoinColumn(name = "ID_MOTO")
    private Moto moto;

    @ManyToOne
    @JoinColumn(name = "ID_MODELO_BEACON")
    private ModeloBeacon modeloBeacon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "O UUID do beacon é obrigatório") @Size(max = 100, message = "O UUID deve ter no máximo 100 caracteres") String getUuid() {
        return uuid;
    }

    public void setUuid(@NotBlank(message = "O UUID do beacon é obrigatório") @Size(max = 100, message = "O UUID deve ter no máximo 100 caracteres") String uuid) {
        this.uuid = uuid;
    }

    public @Min(value = 0, message = "O nível de bateria deve ser no mínimo 0") @Max(value = 100, message = "O nível de bateria deve ser no máximo 100") Integer getBateria() {
        return bateria;
    }

    public void setBateria(@Min(value = 0, message = "O nível de bateria deve ser no mínimo 0") @Max(value = 100, message = "O nível de bateria deve ser no máximo 100") Integer bateria) {
        this.bateria = bateria;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    public ModeloBeacon getModeloBeacon() {
        return modeloBeacon;
    }

    public void setModeloBeacon(ModeloBeacon modeloBeacon) {
        this.modeloBeacon = modeloBeacon;
    }
}
