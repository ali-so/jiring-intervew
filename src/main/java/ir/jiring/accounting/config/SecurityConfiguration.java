package ir.jiring.accounting.config;

import ir.jiring.accounting.security.JwtAuthenticationEntryPoint;
import ir.jiring.accounting.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
        // dont authenticate this particular request
                .authorizeRequests().antMatchers("/api/login").permitAll();
        //swagger antMatchers
                httpSecurity.authorizeRequests().antMatchers("/v2/api-docs**").permitAll();
                httpSecurity.authorizeRequests().antMatchers("/swagger-ui.html**").permitAll();
                httpSecurity.authorizeRequests().antMatchers("/swagger-resources**").permitAll();
                httpSecurity.authorizeRequests().antMatchers("/api/v2/api-docs**").permitAll();
                httpSecurity.authorizeRequests().antMatchers("/api/swagger-ui.html**").permitAll();
                httpSecurity.authorizeRequests().antMatchers("/webjars/springfox-swagger-ui/**").permitAll();
                httpSecurity.authorizeRequests().antMatchers("/swagger-resources/configuration/ui").permitAll();
                httpSecurity.authorizeRequests().antMatchers("/swagger-resources/configuration/security").permitAll();
                httpSecurity.authorizeRequests().antMatchers("/swagger-ui").permitAll();
        // all other requests need to be authenticated
        httpSecurity.authorizeRequests().anyRequest().authenticated().and().
        // make sure we use stateless session; session won't be used to
        // store user's state.
        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/swagger-ui/index.html");
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("1234"));
        //$2a$10$uB3pWghIGXyHqlX2YqRS6u0w7g3fN4hdZTScCXd0i4ZIf5gglQVwy
    }
}
