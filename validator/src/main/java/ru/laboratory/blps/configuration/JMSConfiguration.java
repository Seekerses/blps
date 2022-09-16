package ru.laboratory.blps.configuration;


import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.*;

@Configuration
@EnableJms
public class JMSConfiguration {

    @Bean
    public JmsListenerContainerFactory<?> BLPSFactory(ConnectionFactory connectionFactory,
                                                      DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        return new MessageConverter(){
            @Override
            public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
                BytesMessage message = session.createBytesMessage();
                if (object instanceof String){
                    message.writeBytes(((String) object).getBytes());
                } else if (object instanceof byte[]) {
                    message.writeBytes((byte[]) object);
                } else throw new IllegalArgumentException();
                return message;
            }

            @Override
            public Object fromMessage(Message message) throws JMSException, MessageConversionException {
                return message;
            }
        };
    }

}
