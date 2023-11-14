package com.trabajoPractico.Gateway.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebFluxSecurity
public class GWConfig {
    private static final Logger logger = LoggerFactory.getLogger(GWConfig.class);

    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("${apunte-api-gw-tpi.url-microservicio-alquileres}") String uriAlquileres,
                                        @Value("${apunte-api-gw-tpi.url-microservicio-estaciones}") String uriEstaciones) {
        return builder.routes()
                // Ruteo al Microservicio de Alquileres
                .route(p -> p
                        .path("/api/v1/alquileres/**")
                        .uri(uriAlquileres))
                // Ruteo al Microservicio de Estaciones
                .route(p -> p
                        .path("/api/v1/estaciones/**")
                        .uri(uriEstaciones))
                .build();

    }
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchanges -> exchanges
                //Cliente Puede realizar consultas sobre las estaciones, realizar alquileres y devoluciones.
                .pathMatchers(HttpMethod.POST,"/api/v1/alquileres")
                .hasRole("CLIENTE")
                .pathMatchers(HttpMethod.PUT,"/api/v1/alquileres/**")
                .hasRole("CLIENTE")
                .pathMatchers(HttpMethod.GET,"/api/v1/estaciones/**")
                .hasRole("CLIENTE")
                //Administrador
                                // Puede agregar nuevas estaciones
                                //Puede obtener listados sobre los alquileres realizados
                .pathMatchers(HttpMethod.POST,"/api/v1/estaciones")
                .hasRole("ADMINISTRADOR")
                .pathMatchers(HttpMethod.GET,"/api/v1/alquileres/**")
                .hasRole("ADMINISTRADOR")

                        // Cualquier otra petición...
//                        .anyExchange()
//                        .permitAll()

                ).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        // Se especifica el nombre del claim a analizar
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        // Se agrega este prefijo en la conversión por una convención de Spring (ROLE_)
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        // Se asocia el conversor de Authorities al Bean que convierte el token JWT a un objeto Authorization
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter));

        return jwtAuthenticationConverter;
    }




}
