package com.example.ordem_servico;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Define o caminho para o qual o CORS será habilitado, "/" habilita para todas as rotas.
                        .allowedOrigins("http://localhost:4200") // Define qual origem (domínio) pode acessar a API. Altere conforme necessário.
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Define os métodos HTTP permitidos.
                        .allowedHeaders("*") // Permite todos os cabeçalhos.
                        .allowCredentials(true); // Permite o envio de cookies/autenticação.
            }
        };
    }
}
