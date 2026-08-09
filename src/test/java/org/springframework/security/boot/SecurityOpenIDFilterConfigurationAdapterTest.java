package org.springframework.security.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link SecurityOpenIDFilterConfiguration.OpenIDWebSecurityConfigurerAdapter}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("SecurityOpenIDFilterConfiguration.OpenIDWebSecurityConfigurerAdapter Tests")
class SecurityOpenIDFilterConfigurationAdapterTest {

    @Test
    @DisplayName("Inner class exists")
    void testInnerClassExists() {
        Class<?>[] innerClasses = SecurityOpenIDFilterConfiguration.class.getDeclaredClasses();
        assertThat(innerClasses).isNotEmpty();
        boolean found = false;
        for (Class<?> clazz : innerClasses) {
            if (clazz.getSimpleName().equals("OpenIDWebSecurityConfigurerAdapter")) {
                found = true;
                break;
            }
        }
        assertThat(found).isTrue();
    }
}
