package com.example.sb115.config;

import com.example.sb115.domain.user.UserEnum;
import com.example.sb115.util.CustomResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean // Ioc 컨테이너에 BCryptPasswordEncoder() 객체가 등록됨.
    public BCryptPasswordEncoder passwordEncoder(){
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.debug("디버그 : SecurityConfig의 filterChain");
        http.headers().frameOptions().disable(); // iframe 허용안됨.
        http.csrf().disable(); // enable이면 post맨 작동안됨.
        http.cors().configurationSource(configurationSource());

        // jSessionId를 서버쪽에서 관리안함.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 앱으로 요청 예정
        http.formLogin().disable();
        // httpBasic()은 브라우저가 팝업창을 이용해서 사용자 인증을 진행.
        http.httpBasic().disable();

        // Excpetion 가로채기
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            CustomResponseUtil.fail(response, "로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED);
        });

        // 권한 실패
        http.exceptionHandling().accessDeniedHandler((request, response, e) -> {
            CustomResponseUtil.fail(response, "권한이 없습니다", HttpStatus.FORBIDDEN);
        });

        http.authorizeHttpRequests()
                .antMatchers("/api/s/**").authenticated()
                .antMatchers("/api/admin/**").hasRole("" + UserEnum.ADMIN) // ROLE_ 안붙여도 됨
                .anyRequest().permitAll();

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        log.debug("디버그 : SecurityConfig의 configurationSource");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        configuration.addAllowedOriginPattern("*"); // 프론트 서버의 주소 등록
        configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
