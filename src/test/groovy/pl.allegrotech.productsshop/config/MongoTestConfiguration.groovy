package pl.allegrotech.productsshop.config

import com.mongodb.MongoClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.GenericContainer

@Configuration
class MongoTestConfiguration {

    private static final int MONGO_PORT = 27017

    @Bean(initMethod = "start", destroyMethod = "stop")
    GenericContainer mongoContainer() {
        return new GenericContainer("mongo:3.4")
                .withExposedPorts(MONGO_PORT)
    }

    @Bean
    MongoClient mongoClient(GenericContainer mongoContainer) {
        new MongoClient(
                mongoContainer.containerIpAddress,
                mongoContainer.getMappedPort(MONGO_PORT))
    }
}