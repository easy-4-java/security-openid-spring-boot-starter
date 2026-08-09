package org.springframework.security.boot.openid.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link OpenIDMatchedAuthenticationFailureHandler}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("OpenIDMatchedAuthenticationFailureHandler Tests")
class OpenIDMatchedAuthenticationFailureHandlerTest {

    private final OpenIDMatchedAuthenticationFailureHandler handler = new OpenIDMatchedAuthenticationFailureHandler();

    @Test
    @DisplayName("Instance can be created via constructor")
    void testInstantiation() {
        assertThat(handler).isNotNull();
    }

    @Test
    @DisplayName("supports returns false for generic AuthenticationException")
    void testSupports() {
        assertThat(handler.supports(new AuthenticationException("test") {})).isFalse();
    }

    @Test
    @DisplayName("onAuthenticationFailure does not throw")
    void testOnAuthenticationFailure() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        handler.onAuthenticationFailure(request, response, new AuthenticationException("test") {});
        // Should not throw
    }
}
