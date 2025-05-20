package br.com.fiap.mottooth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_MODELO_MOTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloMoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MODELO_MOTO")
    private Long id;

    @NotBlank(message = "O nome do modelo é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(name = "NOME", length = 100, nullable = false)
    private String nome;

    @NotBlank(message = "O fabricante é obrigatório")
    @Size(max = 100, message = "O fabricante deve ter no máximo 100 caracteres")
    @Column(name = "FABRICANTE", length = 100, nullable = false)
    private String fabricante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "O nome do modelo é obrigatório") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome do modelo é obrigatório") @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres") String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "O fabricante é obrigatório") @Size(max = 100, message = "O fabricante deve ter no máximo 100 caracteres") String getFabricante() {
        return fabricante;
    }

    public void setFabricante(@NotBlank(message = "O fabricante é obrigatório") @Size(max = 100, message = "O fabricante deve ter no máximo 100 caracteres") String fabricante) {
        this.fabricante = fabricante;
    }
}
