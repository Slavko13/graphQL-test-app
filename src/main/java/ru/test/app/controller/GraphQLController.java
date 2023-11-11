package ru.test.app.controller;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.app.exception.BadRequestException;

import java.util.Map;

/**
 * Controller to handle GraphQL requests.
 */
@RestController
public class GraphQLController {

    private final GraphQL graphQL;

    /**
     * Constructor to inject GraphQL bean.
     *
     * @param graphQL The GraphQL bean.
     */
    @Autowired
    public GraphQLController(GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> graphql(@RequestBody Map<String, Object> request) {
        // обработка GraphQL-запроса с использованием GraphQL-объекта
        if (request.get("variables") == null) {
            throw new BadRequestException("Укажите variables параметр");
        }
        ExecutionResult executionResult = graphQL.execute(new ExecutionInput.Builder()
                                                                  .query((String) request.get("query"))
                                                                  .operationName((String) request.get("operationName"))
                                                                  .variables((Map<String, Object>) request.get("variables"))
                                                                  .context(request)
                                                                  .build());

        Map<String, Object> resultSpecification = executionResult.toSpecification();

        return new ResponseEntity(resultSpecification, HttpStatus.OK);
    }


}

