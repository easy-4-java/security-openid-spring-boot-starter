package org.springframework.security.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.boot.openid.userdetails.OpenIDAuthcUserDetailsService;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.openid.AxFetchListFactory;
import org.springframework.security.openid.NullAxFetchListFactory;
import org.springframework.security.openid.OpenIDAuthenticationProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link SecurityOpenIDFilterConfiguration}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("SecurityOpenIDFilterConfiguration Tests")
class SecurityOpenIDFilterConfigurationTest {

    private final SecurityOpenIDFilterConfiguration config = new SecurityOpenIDFilterConfiguration();

    @Test
    @DisplayName("Instance can be created via constructor")
    void testInstantiation() {
        assertThat(config).isNotNull();
    }

    @Test
    @DisplayName("attributesToFetchFactory bean is created")
    void testAttributesToFetchFactory() {
        AxFetchListFactory factory = config.attributesToFetchFactory();
        assertThat(factory).isNotNull();
        assertThat(factory).isInstanceOf(NullAxFetchListFactory.class);
    }

    @Test
    @DisplayName("openIDAuthenticationProvider bean is created")
    void testOpenIDAuthenticationProvider() {
        OpenIDAuthcUserDetailsService userDetailsService = mock(OpenIDAuthcUserDetailsService.class);
        GrantedAuthoritiesMapper mapper = new NullAuthoritiesMapper();
        OpenIDAuthenticationProvider provider = config.openIDAuthenticationProvider(userDetailsService, mapper);
        assertThat(provider).isNotNull();
    }
}
