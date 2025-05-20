package br.com.fiap.mottooth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mottooth API")
                        .description("API para gerenciamento de motos, beacons e localizações da Mottu")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe Mottooth")
                                .email("contato@mottooth.com.br")
                                .url("https://www.mottooth.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}