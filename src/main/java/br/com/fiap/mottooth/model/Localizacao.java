package br.com.fiap.mottooth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_LOCALIZACAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOCALIZACAO")
    private Long id;

    @NotNull(message = "A posição X é obrigatória")
    @Column(name = "POSICAO_X", nullable = false)
    private BigDecimal posicaoX;

    @NotNull(message = "A posição Y é obrigatória")
    @Column(name = "POSICAO_Y", nullable = false)
    private BigDecimal posicaoY;

    @Column(name = "DATA_HORA")
    private LocalDateTime dataHora = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "ID_MOTO")
    private Moto moto;

    @ManyToOne
    @JoinColumn(name = "ID_PATIO")
    private Patio patio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "A posição X é obrigatória") BigDecimal getPosicaoX() {
        return posicaoX;
    }

    public void setPosicaoX(@NotNull(message = "A posição X é obrigatória") BigDecimal posicaoX) {
        this.posicaoX = posicaoX;
    }

    public @NotNull(message = "A posição Y é obrigatória") BigDecimal getPosicaoY() {
        return posicaoY;
    }

    public void setPosicaoY(@NotNull(message = "A posição Y é obrigatória") BigDecimal posicaoY) {
        this.posicaoY = posicaoY;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
    }

    public Patio getPatio() {
        return patio;
    }

    public void setPatio(Patio patio) {
        this.patio = patio;
    }
}
