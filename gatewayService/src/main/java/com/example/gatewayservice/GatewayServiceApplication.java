package com.example.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@CrossOrigin(origins = "*")
@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    RouteLocator routeLocator (RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_route", r -> r.path("/api/auth/**").uri("lb://AUTHENTIFICATIONSERVICE"))
                .route("candidat_route", r ->r.path("/candidats/**").uri("lb://CANDIDATSERVICE"))
                .route("entreprise_route", r -> r.path("/entreprises/**").uri("lb://ENTREPRISESERVICE"))
                .route("administrateur_route", r -> r.path("/administrateurs/**").uri("lb://ADMINISTRATEURSERVICE"))
                .route("visiteur_route", r -> r.path("/visiteurs/").uri("lb://VISITEURSERVICE"))
                .route("offre_emploi_route", r -> r.path("/offreEmplois/").uri("lb://OFFREEMPLOISERVICE"))
                .route("category_route", r -> r.path("/categorys/").uri("lb://CATEGORIESERVICE"))
                .route("test_route", r -> r.path("/tests/").uri("lb://TESTSERVICE"))
                .route("commentaire_route", r ->r.path("/commentaires/").uri("lb://COMMENTAIRESERVICE"))
                .route("candidature_route", r -> r.path("/candidatures/").uri("lb://CANDIDATURESERVICE"))
                .build();

    }
    @Bean
    DiscoveryClientRouteDefinitionLocator locator(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
        return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
    }
    // Configuration CORS
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:4200");
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsWebFilter(source);
    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Frontend autorisé
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
//        configuration.setAllowCredentials(true); // Permettre les cookies/credentials
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source; // Retourner la configuration CORS
//    }

}
