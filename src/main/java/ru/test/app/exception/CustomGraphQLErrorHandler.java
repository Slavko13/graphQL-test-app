package ru.test.app.exception;

import org.springframework.stereotype.Component;


@Component
public class CustomGraphQLErrorHandler
{
    //
    // @Override
    // public List<GraphQLError> processErrors(List<GraphQLError> errors) {
    //     return errors.stream()
    //             .map(this::getGraphQLError)
    //             .collect(Collectors.toList());
    // }
    //
    // private GraphQLError getGraphQLError(GraphQLError error) {
    //     if (error instanceof BadRequestException) {
    //         return GraphqlErrorBuilder.newError()
    //                 .message(error.getMessage())
    //                 .build();
    //     }
    //     // Handle other errors if needed
    //     return error;
    // }
}
