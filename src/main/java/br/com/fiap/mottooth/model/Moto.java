package br.com.fiap.mottooth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_MOTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOTO")
    private Long id;

    @NotBlank(message = "A placa é obrigatória")
    @Size(max = 10, message = "A placa deve ter no máximo 10 caracteres")
    @Pattern(regexp = "[A-Z0-9]+", message = "A placa deve conter apenas letras maiúsculas e números")
    @Column(name = "PLACA", length = 10, nullable = false, unique = true)
    private String placa;

    @Column(name = "DATA_REGISTRO")
    private LocalDateTime dataRegistro = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ID_MODELO_MOTO")
    private ModeloMoto modeloMoto;

    @OneToOne(mappedBy = "moto", cascade = CascadeType.ALL)
    private Beacon beacon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "A placa é obrigatória") @Size(max = 10, message = "A placa deve ter no máximo 10 caracteres") @Pattern(regexp = "[A-Z0-9]+", message = "A placa deve conter apenas letras maiúsculas e números") String getPlaca() {
        return placa;
    }

    public void setPlaca(@NotBlank(message = "A placa é obrigatória") @Size(max = 10, message = "A placa deve ter no máximo 10 caracteres") @Pattern(regexp = "[A-Z0-9]+", message = "A placa deve conter apenas letras maiúsculas e números") String placa) {
        this.placa = placa;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ModeloMoto getModeloMoto() {
        return modeloMoto;
    }

    public void setModeloMoto(ModeloMoto modeloMoto) {
        this.modeloMoto = modeloMoto;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }
}
