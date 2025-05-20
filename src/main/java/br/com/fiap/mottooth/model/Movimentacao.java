package br.com.fiap.mottooth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_MOVIMENTACAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MOVIMENTACAO")
    private Long id;

    @Column(name = "DATA_MOVIMENTACAO")
    private LocalDateTime dataMovimentacao = LocalDateTime.now();

    @Column(name = "OBSERVACAO", columnDefinition = "CLOB")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_MOTO")
    private Moto moto;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_MOVIMENTACAO")
    private TipoMovimentacao tipoMovimentacao;
}
