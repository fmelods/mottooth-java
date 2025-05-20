package br.com.fiap.mottooth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_LOGRADOURO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Logradouro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOGRADOURO")
    private Long id;

    @NotBlank(message = "O nome do logradouro é obrigatório")
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres")
    @Column(name = "NOME", length = 255, nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "ID_BAIRRO")
    private Bairro bairro;
}
