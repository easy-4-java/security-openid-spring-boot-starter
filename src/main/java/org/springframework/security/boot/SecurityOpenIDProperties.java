package org.springframework.security.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Configuration properties.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = SecurityOpenIDProperties.PREFIX)
@Getter
@Setter
@ToString
public class SecurityOpenIDProperties {

	public static final String PREFIX = "spring.security.openid";

	/** Whether Enable OpenID Authentication. */
	private boolean enabled = false;

}
