package org.accountclient.client;


import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Account client
 */
public class AccountClient {

    private final RestTemplate restTemplate;
    private final String url;


    public AccountClient(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    /**
     * Retrieves current balance or zero if addAmount() method was not called before for specified id
     *
     * @param id balance id
     */
    public Long getAmount(Integer id){
        URI uri = UriComponentsBuilder.fromUriString(url)
                .pathSegment("api", "amount")
                .queryParam("id", id)
                .build()
                .toUri();

        return restTemplate.getForObject(uri, Long.class);
    }

    /**
     * Increases balance or set if addAmount() method was called first time
     *
     * @param id balance identifier
     * @param value positive or negative value, which must be added to current balance
     */
    public void addAmount(Integer id, Long value){
        URI uri = UriComponentsBuilder.fromUriString(url)
                .pathSegment("api", "amount")
                .queryParam("id", id)
                .queryParam("value", value)
                .build()
                .toUri();

        restTemplate.postForObject(uri, null, void.class);
    }
}
