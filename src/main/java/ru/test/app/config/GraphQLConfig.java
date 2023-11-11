package ru.test.app.config;

import graphql.GraphQL;
import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.test.app.service.GraphQLResolver;


/**
 * Config file for graphQL.
 * Author: Viacheslav Petrenko
 */
@Configuration
public class GraphQLConfig {

    @Bean
    public GraphQL graphQL(GraphQLSchema graphQLSchema) {
        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    @Bean
    public GraphQLSchema graphQLSchema(GraphQLResolver graphQLResolver) {
        return SchemaParser.newParser()
                .resolvers(graphQLResolver)
                .file("graphql/test-app-schema.graphql")
                .build()
                .makeExecutableSchema();
    }
}

