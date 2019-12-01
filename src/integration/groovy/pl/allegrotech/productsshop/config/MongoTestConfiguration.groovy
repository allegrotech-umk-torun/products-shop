package pl.allegrotech.productsshop.config

import com.mongodb.MongoClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.GenericContainer

@Configuration
class MongoTestConfiguration {

    private final int mongoPort

    MongoTestConfiguration(@Value('\${mongo.integration.port}') int mongoPort) {
        this.mongoPort = mongoPort
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    GenericContainer mongoContainer() {
        return new GenericContainer("mongo:3.4")
                .withExposedPorts(mongoPort)
    }

    @Bean
    MongoClient mongoClient(GenericContainer mongoContainer) {
        new MongoClient(
                mongoContainer.containerIpAddress,
                mongoContainer.getMappedPort(mongoPort))
    }
}