package org.accountclient.configuration;

import org.accountclient.client.AccountClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Account client configuration
 */
@Configuration
public class AccountClientConfiguration {
    @Bean
    public HttpClientBuilder httpClientBuilder(
            @Value("${http.connect.timeout:100}") int connectTimeout,
            @Value("${http.socket.timeout:100}") int socketTimeout
    ) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build();

        return HttpClients.custom().setDefaultRequestConfig(requestConfig);
    }
    @Bean
    public RestTemplate restOperations(
            @Value("${timeout.connect:500}") int connectionTimeout,
            @Value("${timeout.read:1000}") int readTimeout,
            HttpClientBuilder httpClientBuilder
    ) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeout);
        requestFactory.setReadTimeout(readTimeout);
        requestFactory.setHttpClient(httpClientBuilder.build());


        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    @Bean
    public AccountClient accountClient(
            @Qualifier("restOperations") RestTemplate restTemplate,
            @Value("${url}") String url
    ) {
        return new AccountClient(restTemplate, url);
    }
}
