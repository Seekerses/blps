package ru.laboratory.blps.configuration.broker;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class MqttPublisher {
    String broker       = "tcp://127.0.0.1:1883";
    String clientId     = "mainService";
    MemoryPersistence persistence = new MemoryPersistence();

    @Bean
    public MqttClient mqttClient(){
        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            log.info("Connecting to broker: " + broker);
            client.connect(connOpts);
            log.info("Connected");
            return client;
        }
        catch (MqttException ex){
            log.info("failed to connect to MQTT broker " + ex.getMessage());
            return null;
        }
    }
}
