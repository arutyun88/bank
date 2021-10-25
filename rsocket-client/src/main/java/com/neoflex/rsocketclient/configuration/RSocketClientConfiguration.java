package com.neoflex.rsocketclient.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;

@Configuration
public class RSocketClientConfiguration {
    private final String HOST_NAME;
    private final int PORT;

    @Autowired
    public RSocketClientConfiguration(@Value(value = "${rsocket.server.hostname}") String hostName,
                                      @Value(value = "${rsocket.server.port}") String port) {
        this.HOST_NAME = hostName;
        this.PORT = Integer.parseInt(port);
    }

    @Bean
    public RSocketRequester config(RSocketStrategies strategies) {
        return RSocketRequester.builder()
                .rsocketStrategies(strategies)
                .connectTcp(HOST_NAME, PORT)
                .block();
    }
}
