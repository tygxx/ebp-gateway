package com.yy.ebp.gateway;

import com.yy.ebp.gateway.sso.filter.UserSessionFilter;
import com.yy.framework.commons.http.HttpClientProperties;
import com.yy.framework.commons.http.HttpManager;
import com.yy.framework.springboot.BaseAppConfig;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

@Configuration
public class GatewayConfig extends BaseAppConfig {

	@Override
	protected ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("接口网关服务")
				.description("ebp接口网关服务的RESTful APIs")
//                .termsOfServiceUrl("http://blog.didispace.com/")
				.version("0.0.1")
				.build();
	}

	@Bean
	public FilterRegistrationBean userSessionFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setName("ssoSessionfilter");
		UserSessionFilter sessionFilter = new UserSessionFilter();
		registration.setFilter(sessionFilter);
		registration.addUrlPatterns("/*");
		registration.setOrder(FilterRegistrationBean.REQUEST_WRAPPER_FILTER_MAX_ORDER);

		return registration;
	}

	@Bean
	public HttpManager httpManager() {
		HttpClientProperties properties = new HttpClientProperties();
		properties.setConnectTimeout(30000);
		properties.setSocketTimeout(180000);
		properties.setMaxTotal(8);
		HttpManager httpManager = new HttpManager(properties);
		return httpManager;
	}
}
