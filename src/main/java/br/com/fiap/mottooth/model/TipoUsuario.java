package br.com.fiap.mottooth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_TIPO_USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_USUARIO")
    private Long id;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 50, message = "A descrição deve ter no máximo 50 caracteres")
    @Column(name = "DESCRICAO", length = 50, nullable = false)
    private String descricao;
}
