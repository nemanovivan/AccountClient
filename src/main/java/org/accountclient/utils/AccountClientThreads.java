package org.accountclient.utils;

import org.accountclient.client.AccountClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Class that creates threads
 */
@Component
public class AccountClientThreads {

    private final AccountClient accountClient;
    private final ScheduledExecutorService scheduledExecutorServiceGet;
    private final ScheduledExecutorService scheduledExecutorServiceAdd;
    private final List<Integer> idList;

    public AccountClientThreads(AccountClient accountClient,
                                @Value("${rcount}") Integer rcount,
                                @Value("${wcount}") Integer wcount,
                                @Value("${idList}") String idList){
        this.idList = Arrays.stream(idList.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        this.accountClient = accountClient;
        this.scheduledExecutorServiceGet = Executors.newScheduledThreadPool(rcount, new AccountClientThreadFactoryImpl());
        this.scheduledExecutorServiceAdd = Executors.newScheduledThreadPool(wcount, new AccountClientThreadFactoryImpl());
    }

    @PostConstruct
    void init() {
        scheduledExecutorServiceGet.scheduleWithFixedDelay(this::funcGet, 2, 1, TimeUnit.SECONDS);
        scheduledExecutorServiceAdd.scheduleWithFixedDelay(this::funcAdd, 0, 1, TimeUnit.SECONDS);
    }

    @PreDestroy
    void stop() {
        scheduledExecutorServiceGet.shutdownNow();
        scheduledExecutorServiceAdd.shutdownNow();
    }

    public void funcGet() {

        try {
            accountClient.getAmount(randomId());
        } catch (HttpServerErrorException ignored) {

        }
    }

    public void funcAdd() {
        accountClient.addAmount(randomId(), 100L);
    }

    private int randomId () {
        Random random = new Random();
        return idList.get(random.nextInt(idList.size()));
    }
}
