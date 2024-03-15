package br.com.equipe.clientes;

// AppConfig.java
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("br.com.equipe.clientes") // Substitua com o pacote onde estão seus controladores
public class AppConfig {
}

