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

import br.com.herculano.livararia_api_rest.auth.token.UsuarioDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth").permitAll()
				.antMatchers(HttpMethod.GET, "/usuario").hasRole("CONSULTA_USUARIO")
				.antMatchers(HttpMethod.POST, "/usuario").hasRole("CADASTRAR_USUARIO")
				.antMatchers(HttpMethod.GET, "/usuario/*").hasRole("CONSULTA_USUARIO_POR_ID")
				.antMatchers(HttpMethod.PUT, "/usuario/*").hasRole("ATUALIZAR_USUARIO")
				.antMatchers(HttpMethod.PUT, "/usuario/*/grupo").hasRole("CADASTRAR_USUARIO_GRUPOS")
				.antMatchers(HttpMethod.POST, "/usuario/trocaSenha").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario/trocaSenha/validaCodigo").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario/trocaSenha/codigo").permitAll()
				.antMatchers(HttpMethod.GET, "/grupo").hasRole("CONSULTA_GRUPOS").antMatchers(HttpMethod.POST, "/grupo")
				.hasRole("CADASTRAR_GRUPO").antMatchers(HttpMethod.GET, "/grupo/*").hasRole("CONSULTA_GRUPO_POR_ID")
				.antMatchers(HttpMethod.PUT, "/grupo/*").hasRole("ATUALIZAR_GRUPO")
				.antMatchers(HttpMethod.DELETE, "/grupo/*").hasRole("DELETE_GRUPO")
				.antMatchers(HttpMethod.GET, "/grupo/permissoes").hasRole("CONSULTA_PERMISSOES")
				.antMatchers(HttpMethod.GET, "/livro").hasRole("CONSULTA_LIVROS").antMatchers(HttpMethod.POST, "/livro")
				.hasRole("CADASTRAR_LIVRO").antMatchers(HttpMethod.POST, "/livro/*").hasRole("CONSULTA_LIVRO_POR_ID")
				.antMatchers(HttpMethod.PUT, "/livro/*").hasRole("ATUALIZAR_LIVRO")
				.antMatchers(HttpMethod.DELETE, "/livro/*").hasRole("DELETE_LIVRO")
				.antMatchers(HttpMethod.GET, "/livro/*/autor").hasRole("CONSULTA_AUTORES_POR_ID_LIVRO")
				.antMatchers(HttpMethod.PUT, "/livro/*/autor/*").hasRole("LIVRO_ADICIONAR_AUTOR")
				.antMatchers(HttpMethod.DELETE, "/livro/*/autor/*").hasRole("LIVRO_DELETE_AUTOR")
				.antMatchers(HttpMethod.GET, "/autor").hasRole("CONSULTA_AUTORES")
				.antMatchers(HttpMethod.POST, "/autor").hasRole("CADASTRAR_AUTOR")
				.antMatchers(HttpMethod.GET, "/autor/*").hasRole("CONSULTA_AUTOR_POR_ID")
				.antMatchers(HttpMethod.PUT, "/autor/*").hasRole("ATUALIZAR_AUTOR")
				.antMatchers(HttpMethod.DELETE, "/autor/*").hasRole("DELETE_AUTOR").anyRequest().authenticated().and()
				.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**/*")
					.allowedOrigins("http://localhost:4200")
					.allowedOrigins("http://localhost:8081");
			}
		};
	}
}