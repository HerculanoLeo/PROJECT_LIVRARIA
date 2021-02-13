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

import br.com.herculano.livararia_api_rest.configuration.handler_errors.RestExceptionHandler;
import br.com.herculano.livararia_api_rest.service.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RestExceptionHandler restExceptionHandler;
	
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

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				//AUTENTICACAO
				.antMatchers(HttpMethod.POST, "/auth").permitAll()
				
				//USUARIO
				.antMatchers(HttpMethod.GET, "/usuario").hasRole("CONSULTA_USUARIOS")
				.antMatchers(HttpMethod.GET, "/usuario/{id}").access("@resourcesSecurity.isAcessoDadosUsuario(authentication, #id)")
				.antMatchers(HttpMethod.POST, "/usuario").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario/root").hasRole("CADASTRO_USUARIO_ROOT")
				.antMatchers(HttpMethod.PUT, "/usuario/perfil").hasRole("ATUALIZA_PERFIL_USUARIO")
				.antMatchers(HttpMethod.PUT, "/usuario/{id}").access("@resourcesSecurity.isAtualizaDadosUsuarioCliente(authentication, #id)")
				.antMatchers(HttpMethod.PUT, "/usuario/root/{id}").access("@resourcesSecurity.isAtualizaDadosUsuarioRoot(authentication, #id)")
				.antMatchers(HttpMethod.POST, "/usuario/troca_senha").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario/troca_senha/valida_codigo").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario/troca_senha/codigo").permitAll()
				
				//PERFIL
				.antMatchers(HttpMethod.GET, "/perfil").hasRole("CONSULTA_PERFIL")
				.antMatchers(HttpMethod.GET, "/perfil/permissoes").hasRole("CONSULTA_PERFIL")
				.antMatchers(HttpMethod.GET, "/perfil/{id}").access("@resourcesSecurity.isAcessoIdPerfil(authentication, #id)")
				.antMatchers(HttpMethod.POST, "/perfil").access("@resourcesSecurity.isCadastroPerfil(authentication, #id)")
				.antMatchers(HttpMethod.POST, "/perfil/{idAdministrador}").access("@resourcesSecurity.isCadastroPerfil(authentication, #id)")
				.antMatchers(HttpMethod.PUT, "/perfil").access("@resourcesSecurity.isCadastroPerfil(authentication, #id)")
				.antMatchers(HttpMethod.DELETE, "/perfil/inativar").access("@resourcesSecurity.isCadastroPerfil(authentication, #id)")
				
				//BIBLIOTECA
				.antMatchers(HttpMethod.GET, "/biblioteca").permitAll()
				.antMatchers(HttpMethod.GET, "/biblioteca/{id}").permitAll()
				.antMatchers(HttpMethod.POST, "/biblioteca").permitAll()
				.antMatchers(HttpMethod.PUT, "/biblioteca/{id}").access("@resourcesSecurity.isAtualizaBiblioteca(authentication, #id)")
				.antMatchers(HttpMethod.GET, "/biblioteca/administrador/{id}").access("@resourcesSecurity.isConsultaAdministrador(authentication, #id)")
				.antMatchers(HttpMethod.POST, "/biblioteca/administrador").hasRole("CADASTRAR_ADMINISTRADOR")
				.antMatchers(HttpMethod.PUT, "/biblioteca/administrador/{id}").access("@resourcesSecurity.isAtualizaAdministrador(authentication, #id)")
				.antMatchers(HttpMethod.POST, "/biblioteca/administrador/{id}/biblioteca").access("@resourcesSecurity.isCadastraNovaBibliotecaNoAdministrador(authentication, #id)")
				.antMatchers(HttpMethod.GET, "/biblioteca/operador").access("@resourcesSecurity.isConsultaTodosOperadores(authentication)")
				.antMatchers(HttpMethod.GET, "/biblioteca/administrador/{id}/operador").access("@resourcesSecurity.isConsultaOperadoresIdAdministrador(authentication, #id)")
				.antMatchers(HttpMethod.GET, "/biblioteca/{id}/operador").access("@resourcesSecurity.isConsultaOperadoresIdBiblioteca(authentication, #id)")
				.antMatchers(HttpMethod.GET, "/biblioteca/operador/{id}").access("@resourcesSecurity.isConsultaIdOperador(authentication, #id)")
				.antMatchers(HttpMethod.PUT, "/biblioteca/operador/{id}").access("@resourcesSecurity.isAtualizaOperador(authentication, #id)")
				.antMatchers(HttpMethod.POST, "/biblioteca/{id}/operador").access("@resourcesSecurity.isCadastroOperador(authentication, #id)")
				
				//AUTOR
				.antMatchers(HttpMethod.GET, "/autor").permitAll()
				.antMatchers(HttpMethod.GET, "/autor/{id}").permitAll()
				.antMatchers(HttpMethod.PUT, "/autor/{id}").access("@resourcesSecurity.isAtualizarAutor(authentication, #id)")
				
				//LIVRO
				.antMatchers(HttpMethod.GET, "/livro").permitAll()
				.antMatchers(HttpMethod.GET, "/livro/{id}").permitAll()
				.antMatchers(HttpMethod.PUT, "/livro/{id}").access("@resourcesSecurity.isAtualizarLivro(authentication, #id)")
				.antMatchers(HttpMethod.GET, "/livro/{id}/autor").permitAll()
				
				.anyRequest().authenticated().and().csrf().disable().exceptionHandling()
				.authenticationEntryPoint(restExceptionHandler)
				.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling().accessDeniedHandler(restExceptionHandler);
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