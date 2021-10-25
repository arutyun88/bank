package com.neoflex.consumer.config;

import com.neoflex.consumer.model.Account;
import com.neoflex.consumer.model.AccountWithAddress;
import com.neoflex.consumer.model.Address;
import com.neoflex.consumer.model.BankAccountInfo;
import com.neoflex.consumer.repository.BankAccountInfoRepository;
import com.neoflex.consumer.service.BankAccountInfoJoinerService;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final String BOOTSTRAP_SERVERS;
    private final String APP_NAME;
    private final String TOPIC_ACCOUNT;
    private final String TOPIC_ADDRESS;
    private final BankAccountInfoRepository bankAccountInfoRepository;

    @Autowired
    public KafkaConfiguration(@Value(value = "${kafka.bootstrap.servers}") String bootstrapServers,
                              @Value(value = "${kafka.appName}") String appName,
                              @Value(value = "${kafka.topic.account}") String topicAccount,
                              @Value(value = "${kafka.topic.address}")String topicAddress,
                              BankAccountInfoRepository bankAccountInfoRepository) {
        this.BOOTSTRAP_SERVERS = bootstrapServers;
        this.APP_NAME = appName;
        this.TOPIC_ACCOUNT = topicAccount;
        this.TOPIC_ADDRESS = topicAddress;
        this.bankAccountInfoRepository = bankAccountInfoRepository;
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration config() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APP_NAME);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        return new KafkaStreamsConfiguration(props);
    }

    private Serde<Account> accountSerde() {
        JsonDeserializer<Account> accountJsonDeserializer = new JsonDeserializer<>(Account.class);
        accountJsonDeserializer.setUseTypeHeaders(false);
        JsonSerializer<Account> accountJsonSerializer = new JsonSerializer<>();
        return Serdes.serdeFrom(accountJsonSerializer, accountJsonDeserializer);
    }

    private Serde<Address> addressSerde() {
        JsonDeserializer<Address> addressJsonDeserializer = new JsonDeserializer<>(Address.class);
        addressJsonDeserializer.setUseTypeHeaders(false);
        JsonSerializer<Address> addressJsonSerializer = new JsonSerializer<>();
        return Serdes.serdeFrom(addressJsonSerializer, addressJsonDeserializer);
    }

    @Bean
    public Topology createTopology(StreamsBuilder kStreamBuilder) {

        KTable<UUID, Account> accountKTable = kStreamBuilder.table(TOPIC_ACCOUNT, Consumed.with(Serdes.UUID(), accountSerde()));
        KTable<UUID, Address> addressKTable = kStreamBuilder.table(TOPIC_ADDRESS, Consumed.with(Serdes.UUID(), addressSerde()));

        KTable<UUID, AccountWithAddress> bankAccountInfoKTable = accountKTable
                .join(addressKTable, new BankAccountInfoJoinerService());

        KStream<UUID, AccountWithAddress> bankAccountInfoKStream = bankAccountInfoKTable.toStream();

        bankAccountInfoKStream.foreach((key, value) -> bankAccountInfoRepository.save(
                new BankAccountInfo(key, value.getAccount(), value.getAddress())
        ));

        bankAccountInfoKStream
                .print(Printed.<UUID, AccountWithAddress> toSysOut().withLabel("app:" + APP_NAME + "/topic:" + TOPIC_ADDRESS));



        kStreamBuilder
                .table(TOPIC_ACCOUNT, Consumed.with(Serdes.UUID(), accountSerde()))
                .join(kStreamBuilder.table(TOPIC_ADDRESS, Consumed.with(Serdes.UUID(), addressSerde())), new BankAccountInfoJoinerService())
                .toStream()
                .foreach((key, value) -> bankAccountInfoRepository.save(new BankAccountInfo(key, value.getAccount(), value.getAddress())));



        return kStreamBuilder.build();
    }

    @Bean
    public NewTopic createTopicAccount() {
        return new NewTopic(TOPIC_ACCOUNT, 3,(short) 1);
    }

    @Bean
    public NewTopic createTopicAddress() {
        return new NewTopic(TOPIC_ADDRESS, 3,(short) 1);
    }
}
