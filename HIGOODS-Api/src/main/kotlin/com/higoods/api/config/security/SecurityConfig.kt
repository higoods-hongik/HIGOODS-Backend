package com.higoods.api.config.security

import com.higoods.common.const.SWAGGER_PATTERNS
import com.higoods.common.helper.SpringEnvironmentHelper
import org.springframework.context.annotation.Bean
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler

@EnableWebSecurity
class SecurityConfig(
    val filterConfig: FilterConfig,
    val springEnvironmentHelper: SpringEnvironmentHelper
) {
//    @Value("\${swagger.user}")
//    lateinit var swaggerUser: String
//
//    @Value("\${swagger.password}")
//    lateinit var swaggerPassword: String

    /** 스웨거용 인메모리 유저 설정  */
//    @Bean
//    fun userDetailsService(): InMemoryUserDetailsManager {
//        val user = User.withUsername(swaggerUser)
//            .password(passwordEncoder().encode(swaggerPassword))
//            .roles("SWAGGER")
//            .build()
//        return InMemoryUserDetailsManager(user)
//    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(8)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.formLogin().disable().cors().and().csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests().expressionHandler(expressionHandler())

        // 베이직 시큐리티 설정
        // 베이직 시큐리티는 ExceptionTranslationFilter 에서 authenticationEntryPoint 에서
        // commence 로 401 넘겨줌. -> 응답 헤더에 www-authenticate 로 인증하라는 응답줌.
        // 브라우저가 basic auth 실행 시켜줌.
        // 개발 환경에서만 스웨거 비밀번호 미설정.
        if (springEnvironmentHelper.isProdAndDevProfile) {
            http.authorizeRequests().mvcMatchers(*SWAGGER_PATTERNS).authenticated().and().httpBasic()
        }
        http.authorizeRequests()
            .mvcMatchers("/v1/example/health")
            .permitAll()
            .mvcMatchers("/v1/test")
            .permitAll()
            .mvcMatchers(*SWAGGER_PATTERNS) // * 표시는 spread operater 임 refrence https://stackoverflow.com/questions/45854994/convert-kotlin-array-to-java-varargs
            .permitAll()
            .mvcMatchers("/v1/auth/oauth/**")
            .permitAll()
            .mvcMatchers("/v1/auth/token/refresh")
            .permitAll()
//            .hasRole("ADMIN") // 인증 이필요한 모든 요청은 USER 권한을 최소한 가지고있어야한다.
            // 스웨거용 인메모리 유저의 권한은 SWAGGER 이다
            // 따라서 스웨거용 인메모리 유저가 basic auth 필터를 통과해서 들어오더라도
            // ( jwt 필터나 , basic auth 필터의 순서는 상관이없다.) --> 왜냐면 jwt는 토큰 여부 파악만하고 있으면 검증이고 없으면 넘김.
            // 내부 소스까지 실행을 못함. 권한 문제 때문에.
            .anyRequest()
            .hasRole("USER")
        http.apply<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>>(filterConfig)
        return http.build()
    }

    @Bean
    fun roleHierarchy(): RoleHierarchyImpl {
        val roleHierarchy = RoleHierarchyImpl()
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER")
        return roleHierarchy
    }

    @Bean
    fun expressionHandler(): DefaultWebSecurityExpressionHandler {
        val expressionHandler = DefaultWebSecurityExpressionHandler()
        expressionHandler.setRoleHierarchy(roleHierarchy())
        return expressionHandler
    }
}
