package com.higoods.api.config.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.web.filter.ForwardedHeaderFilter

@Component
class FilterConfig(
    val jwtTokenFilter: JwtTokenFilter,
    val accessDeniedFilter: AccessDeniedFilter,
    val jwtExceptionFilter: JwtExceptionFilter
) :
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    @Bean
    fun forwardedHeaderFilter(): ForwardedHeaderFilter {
        return ForwardedHeaderFilter()
    }

    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter::class.java)
        builder.addFilterBefore(jwtExceptionFilter, JwtTokenFilter::class.java)
        builder.addFilterBefore(accessDeniedFilter, FilterSecurityInterceptor::class.java)
    }
}
