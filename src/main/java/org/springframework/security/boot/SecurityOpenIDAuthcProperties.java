/*
 * Copyright (c) 2018, hiwepy (https://github.com/easy-4-java).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.security.boot;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.boot.biz.authentication.AuthenticatingFailureCounter;
import org.springframework.security.boot.biz.authentication.PostRequestAuthenticationProcessingFilter;
import org.springframework.security.boot.biz.property.SecurityAuthcProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Configuration properties.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = SecurityOpenIDAuthcProperties.PREFIX)
@Getter
@Setter
@ToString
public class SecurityOpenIDAuthcProperties extends SecurityAuthcProperties {

	public static final String PREFIX = "spring.security.jwt.authc";
	public static final String DEFAULT_CLAIMED_IDENTITY_FIELD = "openid_identifier";
	
	/** 登录地址：会话不存在时访问的地址 */
	private String loginUrl = "/authz/login";;
	private String loginUrlPatterns = "/login";;
	/** 重定向地址：会话注销后的重定向地址 */
	private String redirectUrl = "/";
	/** 系统主页：登录成功后跳转路径 */
	private String successUrl = "/index";;
	/** 未授权页面：无权限时的跳转路径 */
	private String unauthorizedUrl = "/error";
	/** 异常页面：认证失败时的跳转路径 */
	private String failureUrl = "/error";

	/** the regular expression for matching on OpenID's (i.e."https://www.google.com/.*", ".*yahoo.com.*", etc) */
	private String identifierPattern = "";
	
	/** The URL that determines if authentication is required */
	private String filterProcessesUrl;

	private boolean allowSessionCreation = true;
	/**
	 * The name of the request parameter containing the OpenID identity, as
	 * submitted from the initial login form. Defaults to "openid_identifier"
	 */
	private String claimedIdentityFieldName = DEFAULT_CLAIMED_IDENTITY_FIELD;

	/**
	 * Maps the <tt>return_to url</tt> to a realm, for example:
	 *
	 * <pre>
	 * http://www.example.com/login/openid -&gt; http://www.example.com/realm
	 * </pre>
	 *
	 * If no mapping is provided then the returnToUrl will be parsed to extract the
	 * protocol, hostname and port followed by a trailing slash. This means that
	 * <tt>http://www.example.com/login/openid</tt> will automatically become
	 * <tt>http://www.example.com:80/</tt>
	 */
	private Map<String, String> realmMapping = Collections.emptyMap();

	/**
	 * Specifies any extra parameters submitted along with the identity field which
	 * should be appended to the return_to URL which is assembled by
	 * buildReturnToUrl.
	 * <p>
	 * If not set, it will default to the parameter name used by the
	 * RememberMeServices obtained from the parent class (if one is set).
	 */
	private Set<String> returnToUrlParameters = Collections.emptySet();

	
	
	/** the username parameter name. Defaults to "username". */
	private String usernameParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
	/** the password parameter name. Defaults to "password". */
	private String passwordParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;
	/**
	 * Indicates if the filter chain should be continued prior to delegation to
	 * {@link #successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)}
	 * , which may be useful in certain environment (such as Tapestry applications).
	 * Defaults to <code>false</code>.
	 */
	private boolean continueChainBeforeSuccessfulAuthentication = false;
	private boolean postOnly = true;
	private String retryTimesKeyParameter = AuthenticatingFailureCounter.DEFAULT_RETRY_TIMES_KEY_PARAM_NAME;
	private String retryTimesKeyAttribute = PostRequestAuthenticationProcessingFilter.DEFAULT_RETRY_TIMES_KEY_ATTRIBUTE_NAME;
	/** Maximum number of retry to login . */
	private int retryTimesWhenAccessDenied = 3;
	private boolean useForward = false;
	
	/**
	 * Returns the allow session creation.
	 *
	 * @return the allow session creation
	 */
	public boolean isAllowSessionCreation() {
		return allowSessionCreation;
	}
	/**
	 * Sets the allow session creation.
	 *
	 * @param allowSessionCreation the allow session creation
	 */
	public void setAllowSessionCreation(boolean allowSessionCreation) {
		this.allowSessionCreation = allowSessionCreation;
	}
	/**
	 * Returns the filter processes url.
	 *
	 * @return the filter processes url
	 */
	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}
	/**
	 * Sets the filter processes url.
	 *
	 * @param filterProcessesUrl the filter processes url
	 */
	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}
	/**
	 * Returns the claimed identity field name.
	 *
	 * @return the claimed identity field name
	 */
	public String getClaimedIdentityFieldName() {
		return claimedIdentityFieldName;
	}
	/**
	 * Sets the claimed identity field name.
	 *
	 * @param claimedIdentityFieldName the claimed identity field name
	 */
	public void setClaimedIdentityFieldName(String claimedIdentityFieldName) {
		this.claimedIdentityFieldName = claimedIdentityFieldName;
	}
	/**
	 * Returns the realm mapping.
	 *
	 * @return the realm mapping
	 */
	public Map<String, String> getRealmMapping() {
		return realmMapping;
	}
	/**
	 * Sets the realm mapping.
	 *
	 * @param realmMapping the realm mapping
	 */
	public void setRealmMapping(Map<String, String> realmMapping) {
		this.realmMapping = realmMapping;
	}
	/**
	 * Returns the return to url parameters.
	 *
	 * @return the return to url parameters
	 */
	public Set<String> getReturnToUrlParameters() {
		return returnToUrlParameters;
	}
	/**
	 * Sets the return to url parameters.
	 *
	 * @param returnToUrlParameters the return to url parameters
	 */
	public void setReturnToUrlParameters(Set<String> returnToUrlParameters) {
		this.returnToUrlParameters = returnToUrlParameters;
	}
	/**
	 * Returns the login url.
	 *
	 * @return the login url
	 */
	public String getLoginUrl() {
		return loginUrl;
	}
	/**
	 * Sets the login url.
	 *
	 * @param loginUrl the login url
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl; 
	}
	/**
	 * Returns the login url patterns.
	 *
	 * @return the login url patterns
	 */
	public String getLoginUrlPatterns() {
		return loginUrlPatterns;
	}
	/**
	 * Sets the login url patterns.
	 *
	 * @param loginUrlPatterns the login url patterns
	 */
	public void setLoginUrlPatterns(String loginUrlPatterns) {
		this.loginUrlPatterns = loginUrlPatterns;
	}
	/**
	 * Returns the redirect url.
	 *
	 * @return the redirect url
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}
	/**
	 * Sets the redirect url.
	 *
	 * @param redirectUrl the redirect url
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	/**
	 * Returns the success url.
	 *
	 * @return the success url
	 */
	public String getSuccessUrl() {
		return successUrl;
	}
	/**
	 * Sets the success url.
	 *
	 * @param successUrl the success url
	 */
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	/**
	 * Returns the unauthorized url.
	 *
	 * @return the unauthorized url
	 */
	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}
	/**
	 * Sets the unauthorized url.
	 *
	 * @param unauthorizedUrl the unauthorized url
	 */
	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}
	/**
	 * Returns the failure url.
	 *
	 * @return the failure url
	 */
	public String getFailureUrl() {
		return failureUrl;
	}
	/**
	 * Sets the failure url.
	 *
	 * @param failureUrl the failure url
	 */
	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}
	/**
	 * Returns the identifier pattern.
	 *
	 * @return the identifier pattern
	 */
	public String getIdentifierPattern() {
		return identifierPattern;
	}
	/**
	 * Sets the identifier pattern.
	 *
	 * @param identifierPattern the identifier pattern
	 */
	public void setIdentifierPattern(String identifierPattern) {
		this.identifierPattern = identifierPattern;
	}
	/**
	 * Returns the username parameter.
	 *
	 * @return the username parameter
	 */
	public String getUsernameParameter() {
		return usernameParameter;
	}
	/**
	 * Sets the username parameter.
	 *
	 * @param usernameParameter the username parameter
	 */
	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}
	/**
	 * Returns the password parameter.
	 *
	 * @return the password parameter
	 */
	public String getPasswordParameter() {
		return passwordParameter;
	}
	/**
	 * Sets the password parameter.
	 *
	 * @param passwordParameter the password parameter
	 */
	public void setPasswordParameter(String passwordParameter) {
		this.passwordParameter = passwordParameter;
	}
	/**
	 * Returns the continue chain before successful authentication.
	 *
	 * @return the continue chain before successful authentication
	 */
	public boolean isContinueChainBeforeSuccessfulAuthentication() {
		return continueChainBeforeSuccessfulAuthentication;
	}
	/**
	 * Sets the continue chain before successful authentication.
	 *
	 * @param continueChainBeforeSuccessfulAuthentication the continue chain before successful authentication
	 */
	public void setContinueChainBeforeSuccessfulAuthentication(boolean continueChainBeforeSuccessfulAuthentication) {
		this.continueChainBeforeSuccessfulAuthentication = continueChainBeforeSuccessfulAuthentication;
	}
	/**
	 * Returns the post only.
	 *
	 * @return the post only
	 */
	public boolean isPostOnly() {
		return postOnly;
	}
	/**
	 * Sets the post only.
	 *
	 * @param postOnly the post only
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
	/**
	 * Returns the retry times key parameter.
	 *
	 * @return the retry times key parameter
	 */
	public String getRetryTimesKeyParameter() {
		return retryTimesKeyParameter;
	}
	/**
	 * Sets the retry times key parameter.
	 *
	 * @param retryTimesKeyParameter the retry times key parameter
	 */
	public void setRetryTimesKeyParameter(String retryTimesKeyParameter) {
		this.retryTimesKeyParameter = retryTimesKeyParameter;
	}
	/**
	 * Returns the retry times key attribute.
	 *
	 * @return the retry times key attribute
	 */
	public String getRetryTimesKeyAttribute() {
		return retryTimesKeyAttribute;
	}
	/**
	 * Sets the retry times key attribute.
	 *
	 * @param retryTimesKeyAttribute the retry times key attribute
	 */
	public void setRetryTimesKeyAttribute(String retryTimesKeyAttribute) {
		this.retryTimesKeyAttribute = retryTimesKeyAttribute;
	}
	/**
	 * Returns the retry times when access denied.
	 *
	 * @return the retry times when access denied
	 */
	public int getRetryTimesWhenAccessDenied() {
		return retryTimesWhenAccessDenied;
	}
	/**
	 * Sets the retry times when access denied.
	 *
	 * @param retryTimesWhenAccessDenied the retry times when access denied
	 */
	public void setRetryTimesWhenAccessDenied(int retryTimesWhenAccessDenied) {
		this.retryTimesWhenAccessDenied = retryTimesWhenAccessDenied;
	}
	/**
	 * Returns the use forward.
	 *
	 * @return the use forward
	 */
	public boolean isUseForward() {
		return useForward;
	}
	/**
	 * Sets the use forward.
	 *
	 * @param useForward the use forward
	 */
	public void setUseForward(boolean useForward) {
		this.useForward = useForward;
	}
	
	

}
