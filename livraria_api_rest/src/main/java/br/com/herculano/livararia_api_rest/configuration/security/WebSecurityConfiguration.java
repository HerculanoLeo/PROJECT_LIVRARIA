package br.com.herculano.livararia_api_rest.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.herculano.livararia_api_rest.service.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService).passwordEncoder(encoder());
	}

	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	// TODO revisar toda parte de seguranca da API
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth").permitAll()
				.antMatchers(HttpMethod.GET, "/usuario").hasRole("CONSULTA_USUARIOS")
				.antMatchers(HttpMethod.GET, "/usuario/{id}").access("@resourcesSecurity.isAcessoDadosUsuario(authentication, #id)")
				.antMatchers(HttpMethod.POST, "/usuario").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario/root").hasRole("CADASTRO_ROOT")
				.antMatchers(HttpMethod.PUT, "/usuario/{id}").access("@resourcesSecurity.isAtualizaDadosUsuarioCliente(authentication, #id)")
				.antMatchers(HttpMethod.PUT, "/usuario/root/{id}").access("@resourcesSecurity.isAtualizaDadosUsuarioRoot(authentication, #id)")
				.antMatchers(HttpMethod.POST, "/trocaSenha").permitAll()
				.antMatchers(HttpMethod.POST, "/trocaSenha/validaCodigo").permitAll()
				.antMatchers(HttpMethod.POST, "/trocaSenha/codigo").permitAll()
				
				
				
				.anyRequest().authenticated().and().csrf().disable().exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**/*").allowedOrigins("http://localhost:4200").allowedOrigins("http://localhost:8081");
			}
		};
	}
}