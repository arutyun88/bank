package com.neoflex.address.configuration;

import com.neoflex.address.model.Account;
import com.neoflex.address.model.Address;
import com.neoflex.address.service.AddressGenerateService;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaConfiguration {
    private final String BOOTSTRAP_SERVER;
    private final String APP_NAME;
    private final String TOPIC_NAME_FOR_READ;
    private final String TOPIC_NAME_FOR_WRITE;

    public KafkaConfiguration(@Value(value = "${kafka.bootstrap.servers}") String bootstrapServers,
                              @Value(value = "${kafka.appName}") String appName,
                              @Value(value = "${kafka.topic.for.read}") String topicNameForRead,
                              @Value(value = "${kafka.topic.for.write}") String topicNameForWrite) {
        this.BOOTSTRAP_SERVER = bootstrapServers;
        this.APP_NAME = appName;
        this.TOPIC_NAME_FOR_READ = topicNameForRead;
        this.TOPIC_NAME_FOR_WRITE = topicNameForWrite;
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration config() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APP_NAME);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        return new KafkaStreamsConfiguration(props);
    }

    private Serde<Account> accountSerde() {
        JsonDeserializer<Account> accountJsonDeserializer = new JsonDeserializer<>(Account.class);
        accountJsonDeserializer.addTrustedPackages("*");
        accountJsonDeserializer.setRemoveTypeHeaders(false);
        accountJsonDeserializer.setUseTypeMapperForKey(true);
        JsonSerializer<Account> accountJsonSerializer = new JsonSerializer<>();
        return Serdes.serdeFrom(accountJsonSerializer, accountJsonDeserializer);
    }

    private Serde<Address> addressSerde() {
        JsonDeserializer<Address> addressJsonDeserializer = new JsonDeserializer<>(Address.class);
        JsonSerializer<Address> addressJsonSerializer = new JsonSerializer<>();
        addressJsonSerializer.setUseTypeMapperForKey(true);
        return Serdes.serdeFrom(addressJsonSerializer, addressJsonDeserializer);
    }

    @Bean
    public Topology createTopology(StreamsBuilder kStreamBuilder) {

        KTable<UUID, Account> accountKTable = kStreamBuilder
                .table(TOPIC_NAME_FOR_READ, Consumed.with(Serdes.UUID(), accountSerde()));

        KStream<UUID, Address> accountKStream = accountKTable.toStream()
                .filter((key, value) -> value.getLastName().charAt(0) == 'А' || value.getLastName().charAt(0) == 'A')
                .mapValues(value -> AddressGenerateService.find());

        accountKStream.to(TOPIC_NAME_FOR_WRITE, Produced.with(Serdes.UUID(), addressSerde()));
        accountKStream.print(Printed.<UUID, Address>toSysOut().withLabel("app:" + APP_NAME + "/topic:" + TOPIC_NAME_FOR_WRITE));
        return kStreamBuilder.build();
    }

    @Bean
    public NewTopic createTopicNameForRead() {
        return new NewTopic(TOPIC_NAME_FOR_READ, 3,(short) 1);
    }

    @Bean
    public NewTopic createTopicNameForWrite() {
        return new NewTopic(TOPIC_NAME_FOR_WRITE, 3,(short) 1);
    }
}