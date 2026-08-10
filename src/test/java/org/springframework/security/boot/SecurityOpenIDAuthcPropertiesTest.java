package org.springframework.security.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link SecurityOpenIDAuthcProperties}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@DisplayName("SecurityOpenIDAuthcProperties Tests")
class SecurityOpenIDAuthcPropertiesTest {

    @Test
    @DisplayName("PREFIX constant has expected value")
    void testPREFIXConstant() {
        assertThat(SecurityOpenIDAuthcProperties.PREFIX).isEqualTo("spring.security.jwt.authc");
    }

    @Test
    @DisplayName("DEFAULT_CLAIMED_IDENTITY_FIELD constant has expected value")
    void testDEFAULT_CLAIMED_IDENTITY_FIELDConstant() {
        assertThat(SecurityOpenIDAuthcProperties.DEFAULT_CLAIMED_IDENTITY_FIELD).isEqualTo("openid_identifier");
    }

    @Test
    @DisplayName("Default values are correct")
    void testDefaultValues() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        assertThat(props.getLoginUrl()).isEqualTo("/authz/login");
        assertThat(props.getLoginUrlPatterns()).isEqualTo("/login");
        assertThat(props.getRedirectUrl()).isEqualTo("/");
        assertThat(props.getSuccessUrl()).isEqualTo("/index");
        assertThat(props.getUnauthorizedUrl()).isEqualTo("/error");
        assertThat(props.getFailureUrl()).isEqualTo("/error");
        assertThat(props.getIdentifierPattern()).isEmpty();
        assertThat(props.getFilterProcessesUrl()).isNull();
        assertThat(props.isAllowSessionCreation()).isTrue();
        assertThat(props.getClaimedIdentityFieldName()).isEqualTo("openid_identifier");
        assertThat(props.getRealmMapping()).isEmpty();
        assertThat(props.getReturnToUrlParameters()).isEmpty();
        assertThat(props.getUsernameParameter()).isEqualTo("username");
        assertThat(props.getPasswordParameter()).isEqualTo("password");
        assertThat(props.isContinueChainBeforeSuccessfulAuthentication()).isFalse();
        assertThat(props.isPostOnly()).isTrue();
        assertThat(props.getRetryTimesWhenAccessDenied()).isEqualTo(3);
        assertThat(props.isUseForward()).isFalse();
    }

    @Test
    @DisplayName("loginUrl getter/setter works")
    void testLoginUrl() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setLoginUrl("/custom/login");
        assertThat(props.getLoginUrl()).isEqualTo("/custom/login");
    }

    @Test
    @DisplayName("loginUrlPatterns getter/setter works")
    void testLoginUrlPatterns() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setLoginUrlPatterns("/custom/**");
        assertThat(props.getLoginUrlPatterns()).isEqualTo("/custom/**");
    }

    @Test
    @DisplayName("redirectUrl getter/setter works")
    void testRedirectUrl() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setRedirectUrl("/custom/redirect");
        assertThat(props.getRedirectUrl()).isEqualTo("/custom/redirect");
    }

    @Test
    @DisplayName("successUrl getter/setter works")
    void testSuccessUrl() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setSuccessUrl("/custom/success");
        assertThat(props.getSuccessUrl()).isEqualTo("/custom/success");
    }

    @Test
    @DisplayName("unauthorizedUrl getter/setter works")
    void testUnauthorizedUrl() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setUnauthorizedUrl("/custom/unauthorized");
        assertThat(props.getUnauthorizedUrl()).isEqualTo("/custom/unauthorized");
    }

    @Test
    @DisplayName("failureUrl getter/setter works")
    void testFailureUrl() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setFailureUrl("/custom/failure");
        assertThat(props.getFailureUrl()).isEqualTo("/custom/failure");
    }

    @Test
    @DisplayName("identifierPattern getter/setter works")
    void testIdentifierPattern() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setIdentifierPattern("https://www.google.com/.*");
        assertThat(props.getIdentifierPattern()).isEqualTo("https://www.google.com/.*");
    }

    @Test
    @DisplayName("filterProcessesUrl getter/setter works")
    void testFilterProcessesUrl() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setFilterProcessesUrl("/openid/login");
        assertThat(props.getFilterProcessesUrl()).isEqualTo("/openid/login");
    }

    @Test
    @DisplayName("allowSessionCreation getter/setter works")
    void testAllowSessionCreation() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setAllowSessionCreation(false);
        assertThat(props.isAllowSessionCreation()).isFalse();
    }

    @Test
    @DisplayName("claimedIdentityFieldName getter/setter works")
    void testClaimedIdentityFieldName() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setClaimedIdentityFieldName("custom_field");
        assertThat(props.getClaimedIdentityFieldName()).isEqualTo("custom_field");
    }

    @Test
    @DisplayName("realmMapping getter/setter works")
    void testRealmMapping() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        Map<String, String> mapping = new HashMap<>();
        mapping.put("http://example.com/login/openid", "http://example.com/realm");
        props.setRealmMapping(mapping);
        assertThat(props.getRealmMapping()).isEqualTo(mapping);
    }

    @Test
    @DisplayName("returnToUrlParameters getter/setter works")
    void testReturnToUrlParameters() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        Set<String> params = new HashSet<>();
        params.add("param1");
        props.setReturnToUrlParameters(params);
        assertThat(props.getReturnToUrlParameters()).isEqualTo(params);
    }

    @Test
    @DisplayName("usernameParameter getter/setter works")
    void testUsernameParameter() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setUsernameParameter("user");
        assertThat(props.getUsernameParameter()).isEqualTo("user");
    }

    @Test
    @DisplayName("passwordParameter getter/setter works")
    void testPasswordParameter() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setPasswordParameter("pass");
        assertThat(props.getPasswordParameter()).isEqualTo("pass");
    }

    @Test
    @DisplayName("continueChainBeforeSuccessfulAuthentication getter/setter works")
    void testContinueChainBeforeSuccessfulAuthentication() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setContinueChainBeforeSuccessfulAuthentication(true);
        assertThat(props.isContinueChainBeforeSuccessfulAuthentication()).isTrue();
    }

    @Test
    @DisplayName("postOnly getter/setter works")
    void testPostOnly() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setPostOnly(false);
        assertThat(props.isPostOnly()).isFalse();
    }

    @Test
    @DisplayName("retryTimesKeyParameter getter/setter works")
    void testRetryTimesKeyParameter() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setRetryTimesKeyParameter("custom_key");
        assertThat(props.getRetryTimesKeyParameter()).isEqualTo("custom_key");
    }

    @Test
    @DisplayName("retryTimesKeyAttribute getter/setter works")
    void testRetryTimesKeyAttribute() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setRetryTimesKeyAttribute("custom_attr");
        assertThat(props.getRetryTimesKeyAttribute()).isEqualTo("custom_attr");
    }

    @Test
    @DisplayName("retryTimesWhenAccessDenied getter/setter works")
    void testRetryTimesWhenAccessDenied() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setRetryTimesWhenAccessDenied(5);
        assertThat(props.getRetryTimesWhenAccessDenied()).isEqualTo(5);
    }

    @Test
    @DisplayName("useForward getter/setter works")
    void testUseForward() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        props.setUseForward(true);
        assertThat(props.isUseForward()).isTrue();
    }

    @Test
    @DisplayName("toString returns non-null string")
    void testToString() {
        SecurityOpenIDAuthcProperties props = new SecurityOpenIDAuthcProperties();
        assertThat(props.toString()).isNotNull();
    }
}
