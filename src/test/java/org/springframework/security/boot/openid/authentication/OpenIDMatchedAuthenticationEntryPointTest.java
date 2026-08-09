package org.springframework.security.boot.openid.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link OpenIDMatchedAuthenticationEntryPoint}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("OpenIDMatchedAuthenticationEntryPoint Tests")
class OpenIDMatchedAuthenticationEntryPointTest {

    private final OpenIDMatchedAuthenticationEntryPoint entryPoint = new OpenIDMatchedAuthenticationEntryPoint();

    @Test
    @DisplayName("Instance can be created via constructor")
    void testInstantiation() {
        assertThat(entryPoint).isNotNull();
    }

    @Test
    @DisplayName("supports returns false for generic AuthenticationException")
    void testSupports() {
        assertThat(entryPoint.supports(new AuthenticationException("test") {})).isFalse();
    }

    @Test
    @DisplayName("commence does not throw")
    void testCommence() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        entryPoint.commence(request, response, new AuthenticationException("test") {});
        // Should not throw
    }
}
