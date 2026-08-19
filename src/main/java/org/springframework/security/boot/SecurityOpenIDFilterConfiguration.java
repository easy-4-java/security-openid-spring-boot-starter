package org.springframework.security.boot;

import java.util.List;
import java.util.stream.Collectors;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.biz.web.servlet.i18n.LocaleContextFilter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.boot.biz.authentication.AuthenticationListener;
import org.springframework.security.boot.biz.authentication.nested.MatchedAuthenticationEntryPoint;
import org.springframework.security.boot.biz.authentication.nested.MatchedAuthenticationFailureHandler;
import org.springframework.security.boot.biz.authentication.nested.MatchedAuthenticationSuccessHandler;
import org.springframework.security.boot.biz.property.SecuritySessionMgtProperties;
import org.springframework.security.boot.openid.userdetails.OpenIDAuthcUserDetailsService;
import org.springframework.security.boot.utils.WebSecurityUtils;
import org.springframework.security.openid.AxFetchListFactory;
import org.springframework.security.openid.NullAxFetchListFactory;
import org.springframework.security.openid.OpenID4JavaConsumer;
import org.springframework.security.openid.OpenIDAuthenticationFilter;
import org.springframework.security.openid.OpenIDAuthenticationProvider;
import org.springframework.security.openid.OpenIDConsumer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@AutoConfigureBefore(name = {
	"org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterAutoConfiguration",
	"org.springframework.security.boot.SecurityBizWebFilterConfiguration"
})
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = SecurityOpenIDProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ SecurityOpenIDProperties.class, SecurityBizProperties.class })
/**
 * Filter configuration for OpenID-based security authentication.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class SecurityOpenIDFilterConfiguration {

	/**
	 * consumer Manager.
	 *
	 * @return the result
	 */
	@Bean
	@ConditionalOnMissingBean
	public ConsumerManager consumerManager() {
		return new ConsumerManager();
	}

	/**
	 * attributes To Fetch Factory.
	 *
	 * @return the result
	 */
	@Bean
	@ConditionalOnMissingBean
	public AxFetchListFactory attributesToFetchFactory() {
		return new NullAxFetchListFactory();
	}

	/**
	 * open I D Consumer.
	 *
	 * @param consumerManager the consumer manager
	 * @param attributesToFetchFactory the attributes to fetch factory
	 * @return the result
	 * @throws ConsumerException if an error occurs
	 */
	@Bean
	@ConditionalOnMissingBean
	public OpenIDConsumer openIDConsumer(ConsumerManager consumerManager,
			AxFetchListFactory attributesToFetchFactory) throws ConsumerException {
		return new OpenID4JavaConsumer(consumerManager, attributesToFetchFactory);
	}

	/**
	 * open I D Authentication Provider.
	 *
	 * @param openIDAuthcUserDetailsService the open i d authc user details service
	 * @param authoritiesMapper the authorities mapper
	 * @return the result
	 */
	@Bean
	public OpenIDAuthenticationProvider openIDAuthenticationProvider(
			OpenIDAuthcUserDetailsService openIDAuthcUserDetailsService,
			org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper authoritiesMapper) {

		OpenIDAuthenticationProvider authcProvider = new OpenIDAuthenticationProvider();
		authcProvider.setAuthenticationUserDetailsService(openIDAuthcUserDetailsService);
		authcProvider.setAuthoritiesMapper(authoritiesMapper);
		return authcProvider;
	}

	@Configuration
	@EnableConfigurationProperties({ SecurityOpenIDProperties.class, SecurityBizProperties.class })
	@Order(Ordered.HIGHEST_PRECEDENCE + 7)
	static class OpenIDWebSecurityConfigurerAdapter extends WebSecurityBizConfigurerAdapter {

		private final SecurityOpenIDAuthcProperties authcProperties;

		private final LocaleContextFilter localeContextFilter;
		private final AuthenticationEntryPoint authenticationEntryPoint;
		private final AuthenticationSuccessHandler authenticationSuccessHandler;
		private final AuthenticationFailureHandler authenticationFailureHandler;
		private final RememberMeServices rememberMeServices;
		private final SessionAuthenticationStrategy sessionAuthenticationStrategy;

		private final OpenIDAuthenticationFilter openIDAuthenticationFilter;
		private final OpenIDAuthenticationProvider openIDAuthenticationProvider;
		private final OpenIDAuthcUserDetailsService openIDAuthcUserDetailsService;
		private final OpenIDConsumer consumer;
		private final ConsumerManager consumerManager;

		public OpenIDWebSecurityConfigurerAdapter(

				SecurityBizProperties bizProperties,
				SecuritySessionMgtProperties sessionMgtProperties,
				SecurityOpenIDAuthcProperties authcProperties,

				ObjectProvider<OpenIDAuthenticationFilter> openIDAuthenticationFilterProvider,
				ObjectProvider<OpenIDAuthenticationProvider> openIDAuthenticationProvider,
				ObjectProvider<OpenIDAuthcUserDetailsService> openIDAuthcUserDetailsService,
				ObjectProvider<OpenIDConsumer> consumerProvider,
				ObjectProvider<ConsumerManager> consumerManagerProvider,

				ObjectProvider<LocaleContextFilter> localeContextProvider,
				ObjectProvider<AuthenticationProvider> authenticationProvider,
				ObjectProvider<AuthenticationListener> authenticationListenerProvider,
				ObjectProvider<MatchedAuthenticationEntryPoint> authenticationEntryPointProvider,
				ObjectProvider<MatchedAuthenticationSuccessHandler> authenticationSuccessHandlerProvider,
				ObjectProvider<MatchedAuthenticationFailureHandler> authenticationFailureHandlerProvider,
				ObjectProvider<RememberMeServices> rememberMeServicesProvider,
				ObjectProvider<SessionAuthenticationStrategy> sessionAuthenticationStrategyProvider) {

			super(bizProperties, sessionMgtProperties, authenticationProvider.stream().collect(Collectors.toList()));

			this.localeContextFilter = localeContextProvider.getIfAvailable();
			List<AuthenticationListener> authenticationListeners = authenticationListenerProvider.stream().collect(Collectors.toList());
			this.authenticationEntryPoint = WebSecurityUtils.authenticationEntryPoint(authcProperties, sessionMgtProperties, authenticationEntryPointProvider.stream().collect(Collectors.toList()));
			this.authenticationSuccessHandler = WebSecurityUtils.authenticationSuccessHandler(authcProperties, sessionMgtProperties, authenticationListeners, authenticationSuccessHandlerProvider.stream().collect(Collectors.toList()));
			this.authenticationFailureHandler = WebSecurityUtils.authenticationFailureHandler(authcProperties, sessionMgtProperties, authenticationListeners, authenticationFailureHandlerProvider.stream().collect(Collectors.toList()));
			this.rememberMeServices = rememberMeServicesProvider.getIfAvailable();
			this.sessionAuthenticationStrategy = sessionAuthenticationStrategyProvider.getIfAvailable();

			this.authcProperties = authcProperties;
			this.openIDAuthenticationFilter = openIDAuthenticationFilterProvider.getIfAvailable();
			this.openIDAuthenticationProvider = openIDAuthenticationProvider.getIfAvailable();
			this.openIDAuthcUserDetailsService = openIDAuthcUserDetailsService.getIfAvailable();
			this.consumer = consumerProvider.getIfAvailable();
			this.consumerManager = consumerManagerProvider.getIfAvailable();
		}

		/**
		 * authentication Processing Filter.
		 *
		 * @return the result
		 * @throws Exception if an error occurs
		 */
		public OpenIDAuthenticationFilter authenticationProcessingFilter() throws Exception {
			OpenIDAuthenticationFilter authenticationFilter = new OpenIDAuthenticationFilter();
			PropertyMapper map = PropertyMapper.get();
			map.from(getSessionMgtProperties().isAllowSessionCreation()).to(authenticationFilter::setAllowSessionCreation);
			map.from(authenticationManagerBean()).to(authenticationFilter::setAuthenticationManager);
			map.from(authenticationSuccessHandler).to(authenticationFilter::setAuthenticationSuccessHandler);
			map.from(authenticationFailureHandler).to(authenticationFilter::setAuthenticationFailureHandler);
			map.from(authcProperties.getClaimedIdentityFieldName()).to(authenticationFilter::setClaimedIdentityFieldName);
			map.from(authcProperties.getRealmMapping()).to(authenticationFilter::setRealmMapping);
			map.from(authcProperties.getFilterProcessesUrl()).to(authenticationFilter::setFilterProcessesUrl);
			map.from(authcProperties.getReturnToUrlParameters()).to(authenticationFilter::setReturnToUrlParameters);
			map.from(rememberMeServices).to(authenticationFilter::setRememberMeServices);
			map.from(sessionAuthenticationStrategy).to(authenticationFilter::setSessionAuthenticationStrategy);
			map.from(authcProperties.isContinueChainBeforeSuccessfulAuthentication()).to(authenticationFilter::setContinueChainBeforeSuccessfulAuthentication);
			return authenticationFilter;
		}

		/**
		 * open ID Security Filter Chain.
		 *
		 * @param http the http
		 * @return the result
		 * @throws Exception if an error occurs
		 */
		@Bean
		@Order(Ordered.HIGHEST_PRECEDENCE + 7)
		public SecurityFilterChain openIdSecurityFilterChain(HttpSecurity http) throws Exception {
			http.securityMatcher(authcProperties.getPathPattern())
				.exceptionHandling(config -> config.authenticationEntryPoint(authenticationEntryPoint))
				.httpBasic(config -> config.disable())
				.addFilterBefore(localeContextFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(authenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
			super.configure(http);
			return http.build();
		}

	}

}
