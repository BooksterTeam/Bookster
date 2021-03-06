package io.github.bookster.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;

@Configuration
public class MongoConfiguration {
    @Bean
    public Mongo mongo() throws IOException {
        System.setProperty("DB.TRACE", "true");
        try {
            MongoClient localhost = new MongoClient("localhost");
            return localhost;
        } catch (Exception e) {
            return new EmbeddedMongoBuilder()
                    .version("2.6.0")
                    .bindIp("127.0.0.1")
                    .port(allocateRandomPort())
                    .build();
        }

    }

    public static int allocateRandomPort() {
        try {
            ServerSocket server = new ServerSocket(0);
            int port = server.getLocalPort();
            server.close();
            return port;
        } catch (IOException e) {
            throw new RuntimeException("Failed to acquire a random free port", e);
        }
    }
}
