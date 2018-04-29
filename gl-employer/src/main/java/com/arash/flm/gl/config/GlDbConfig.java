package com.arash.flm.gl.config;

import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

/**
 * Created by anton on 29.04.18.
 *
 */
@Configuration
@EnableMongoRepositories(
        basePackages = "com.arash.flm.gl.repository",
        mongoTemplateRef = "gfMongoTemplate")
public class GlDbConfig {

    @Value("${mongo.db.gf.uri}")
    private String uri;

    @Bean
    public MongoDbFactory gfMongoDbFactory() throws UnknownHostException {
        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        return new SimpleMongoDbFactory(mongoClientURI);
    }

    @Bean
    public MongoTemplate gfMongoTemplate() throws Exception {
        MongoTemplate unitMongoTemplate = new MongoTemplate(gfMongoDbFactory());
        unitMongoTemplate.setWriteConcern(WriteConcern.MAJORITY);
        return unitMongoTemplate;
    }
}
