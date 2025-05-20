package br.com.fiap.mottooth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotoDTO {
    private Long id;
    
    @NotBlank(message = "A placa é obrigatória")
    @Size(max = 10, message = "A placa deve ter no máximo 10 caracteres")
    @Pattern(regexp = "[A-Z0-9]+", message = "A placa deve conter apenas letras maiúsculas e números")
    private String placa;
    
    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clienteId;
    
    @NotNull(message = "O ID do modelo da moto é obrigatório")
    private Long modeloMotoId;
    
    private String nomeCliente;
    private String modeloNome;
    private String fabricante;
}
