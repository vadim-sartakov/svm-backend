# Security

This project is super set over default spring security projects. Also it contains basic security models and useful features.

## OAuth2

### Authorization server

To run authorization server in your project simply define property in `application.properties`
`svm.backend.security.authorization-server.enable=true`

### Resource server

`svm.backend.security.config.ResourceServerBaseConfiguration` is the base configuration of resource server. Simply extend it and override `configure(HttpSecurity http)` method.
	
	@Configuration
	public class ResourceServerConfiguration extends ResourceServerBaseConfiguration {

	    @Override
	    public void configure(HttpSecurity http) throws Exception {
	        http
	                .authorizeRequests()
	                .antMatchers("/home/protected")
	                .authenticated()
	                .antMatchers("/home/public")
	                .permitAll()
	                .antMatchers("/api/orders")
	                .hasAnyAuthority("ROLE_MANAGER");
		}
	}

### JWT

By default JwtTokenStore is used. Default authorization server configuration will try to sign all tokens with key configured in `svm.backend.security.authorization-server` properties set.
Resource server need to set public key value in parameter `security.oauth2.resource.jwt.key-value`

Asymmetric key pair may be generated like this (for Authorization server):
`keytool -genkeypair -alias jwt -keyalg RSA -keypass 123456 -keystore jwt.jks -storepass 123456`

Public key may be exported like this (for Resource server):
`keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey`

## Exception handling

`svm.backend.security.exception.handler` package contains security exception handling beans.
See `web` project.