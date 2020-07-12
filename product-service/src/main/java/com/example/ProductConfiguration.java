package com.example;

import com.example.products.domain.ProductRepository;
import com.example.products.domain.ProductService;
import com.example.products.sagas.ProductCommandHandler;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import({SagaParticipantConfiguration.class, OptimisticLockingDecoratorConfiguration.class})
@EnableJpaRepositories
@EnableAutoConfiguration
public class ProductConfiguration {

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductService(productRepository);
    }

    @Bean
    public ProductCommandHandler customerCommandHandler(ProductService productService) {
        return new ProductCommandHandler(productService);
    }

    @Bean
    public CommandDispatcher consumerCommandDispatcher(ProductCommandHandler target,
                                                       SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {

        return sagaCommandDispatcherFactory.make("productCommandDispatcher", target.commandHandlerDefinitions());
    }
}
