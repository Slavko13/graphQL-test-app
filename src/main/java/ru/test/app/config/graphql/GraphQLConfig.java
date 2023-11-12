package ru.test.app.config.graphql;

import graphql.GraphQL;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.kickstart.tools.SchemaParser;
import graphql.kickstart.tools.SchemaParserBuilder;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * Config file for graphQL.
 * Author: Viacheslav Petrenko
 */
@Configuration
@ComponentScan(basePackages = "ru.test.app.service")
public class GraphQLConfig {

    private final List<GraphQLResolver<?>> graphQLResolvers;

    public GraphQLConfig(final List<GraphQLResolver<?>> graphQLResolvers)
    {
        this.graphQLResolvers = graphQLResolvers;
    }

    @Bean
    public GraphQL graphQL(GraphQLSchema graphQLSchema) {
        return GraphQL.newGraphQL(graphQLSchema).build();
    }

    @Bean
    public GraphQLSchema graphQLSchema() {
        SchemaParserBuilder schemaParserBuilder = SchemaParser.newParser();
        return schemaParserBuilder
                .resolvers(graphQLResolvers)
                .file("graphql/test-app-schema.graphql")
                .build()
                .makeExecutableSchema();
    }

}

