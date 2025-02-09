package com.securityModel.security;

import ch.qos.logback.classic.Logger;
import com.securityModel.security.jwt.AuthEntryPointJwt;
import com.securityModel.security.jwt.AuthTokenFilter;
import com.securityModel.security.jwt.JwtUtils;
import com.securityModel.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.securityModel.security.jwt.JwtUtils.logger;


@Configuration
//@EnableWebSecurity
//@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig { // extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
//
//	@Autowired
//	private JwtUtils jwtUtils;



	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
//	@Component
//	public class AuthTokenFilter extends OncePerRequestFilter {

//		@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		try {
//			String jwt = parseJwt(request);
//			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//				String username = jwtUtils.getUserNameFromJwtToken(jwt);
//
//				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//				UsernamePasswordAuthenticationToken authentication =
//						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//			}
//		} catch (Exception e) {
//			logger.error("Cannot set user authentication: {}");
//		}
//
//		filterChain.doFilter(request, response);
//	}
//	}
//	private String parseJwt(HttpServletRequest request) {
//		String headerAuth = request.getHeader("Authorization");
//
//		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//			return headerAuth.substring(7);
//		}
//
//		return null;
//	}

//	@Override
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}
	
	@Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }

//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}

	@Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable()
//			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//			.authorizeRequests().antMatchers("/api/auth/**").permitAll()
//			.antMatchers("/api/test/**").permitAll()
//			.anyRequest().authenticated();
//
//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOriginPattern("*"); // Pour Spring Security 6.1+
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())  // Désactiver CSRF
				.exceptionHandling(exceptions ->
						exceptions.authenticationEntryPoint(unauthorizedHandler))  // Gestion des exceptions
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Pas de session
				.authorizeRequests(auth ->
						auth
								.requestMatchers("/api/auth/**").permitAll()
								.requestMatchers("/candidats/**").permitAll()  // Permet l'accès à /candidats/**
								.requestMatchers("/entreprises/**").permitAll()  // Permet l'accès à /entreprises/**
								.requestMatchers("/administrateurs/**").permitAll()  // Permet l'accès à /administrateurs/**
								.requestMatchers("/visiteurs/**").permitAll()  // Permet l'accès à /visiteurs/**
								.requestMatchers("/offreEmplois/**").permitAll()  // Permet l'accès à /offreEmplois/**
								.requestMatchers("/categorys/**").permitAll()  // Permet l'accès à /categorys/**
								.requestMatchers("/tests/**").permitAll()  // Permet l'accès à /tests/**
								.requestMatchers("/commentaires/**").permitAll()  // Permet l'accès à /commentaires/**
								.requestMatchers("/candidatures/**").permitAll()  // Permet l'accès à /candidatures/**
								.anyRequest().authenticated()); // Toutes les autres requêtes nécessitent une authentification


 http.authenticationProvider(authenticationProvider());

		// Ajouter un filtre JWT
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}


}
