package com.wang.blog.helper.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

@EnableWebSecurity(debug = false)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    //UsernamePasswordAuthenticationFilter

    @Value("${cors.allowed-origins-port}")
    private String allowedOriginsPort;
    @Value("${cors.allowed-origins-host}")
    private String allowedOriginsHost;
    @Value("${cors.isOnlyHost}")
    private Boolean isOnlyHost;

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

  /*  @Bean
    @Qualifier("myjackson")
    protected HttpMessageConverter jackson(){
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    @Qualifier("myjackson")
    @Autowired
    protected UnauthorizedErrorAuthenticationEntryPoint unauthorizedErrorAuthenticationEntryPoint(HttpMessageConverter httpMessageConverter){
        UnauthorizedErrorAuthenticationEntryPoint authenticationEntryPoint = new UnauthorizedErrorAuthenticationEntryPoint();
        authenticationEntryPoint.setMessageConverter(httpMessageConverter);
        return authenticationEntryPoint;
    }*/


    private UnauthorizedErrorAuthenticationEntryPoint unauthorizedErrorAuthenticationEntryPoint = new UnauthorizedErrorAuthenticationEntryPoint();


    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new MySavedRequestAwareAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
        filter.setFilterProcessesUrl("/login");

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(isOnlyHost?allowedOriginsHost:allowedOriginsHost+":"+allowedOriginsPort,"http://www.dev.com:"+allowedOriginsPort));
        configuration.setAllowedMethods(Arrays.asList(HttpMethod.OPTIONS.name(),"GET","POST",HttpMethod.DELETE.name()));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type","X-Requested-With"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
//PreInvocationAuthorizationAdviceVoter
    //WebExpressionVoter
    /**
     * 开启security debug
     * @param web
     * @throws Exception
     */
   // @Override public void configure(WebSecurity web) throws Exception { web.debug(true); }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().csrf().disable()
                .cors().and()
                .authorizeRequests()
                .anyRequest()
                .permitAll()

                .and().rememberMe().tokenValiditySeconds(19200);
                //.and().exceptionHandling().authenticationEntryPoint(unauthorizedErrorAuthenticationEntryPoint);

        http.addFilterAt(customAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
    }
}
