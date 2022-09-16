package ru.laboratory.blps.configuration.broker;

import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Log4j2
@Configuration
public class MqttPublisher {
//    @Value("${spring.activemq.broker-url}")
//    String BROKER_URL;
//    @Value("${spring.activemq.user}")
//    String USER;
//    @Value("${spring.activemq.password}")
//    String PASSWORD;
//    @Value("${broker.id}")
//    String CLIENT_ID;
//
//    @Bean
//    public MqttPahoClientFactory mqttClientFactory() {
//        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//        MqttConnectOptions options = new MqttConnectOptions();
//        options.setServerURIs(new String[] { "tcp://127.0.0.0:1883" });
//        options.setUserName(USER);
//        options.setPassword(PASSWORD.toCharArray());
//        options.setConnectionTimeout(30000);
//        options.setKeepAliveInterval(30000);
//        factory.setConnectionOptions(options);
//        return factory;
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "mqttOutboundChannel")
//    public MessageHandler mqttOutbound() {
//        MqttPahoMessageHandler messageHandler =
//                new MqttPahoMessageHandler("CLIENT_ID", mqttClientFactory());
//        messageHandler.setAsync(true);
//        messageHandler.setDefaultTopic("test");
//        messageHandler.setDefaultQos(1);
//        return messageHandler;
//    }
//
//    @Bean
//    public MessageChannel mqttOutboundChannel() {
//        return new DirectChannel();
//    }
//
//    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
//    public interface MQTTGateway {
//        void sendToMqtt(String data);
//
//    }
}
