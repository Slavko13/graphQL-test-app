package ru.test.app.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;
import java.util.Map;

/**
 * Exception thrown when bad request(HTTP 400) is made.
 * Author: Viacheslav Petrenko
 */
public class BadRequestException extends RuntimeException implements GraphQLError
{

    public BadRequestException(final String message) {
        super(message);
    }

    @Override
    public List<SourceLocation> getLocations()
    {
        return null;
    }

    @Override
    public ErrorClassification getErrorType()
    {
        return null;
    }

    @Override
    public List<Object> getPath()
    {
        return GraphQLError.super.getPath();
    }

    @Override
    public Map<String, Object> toSpecification()
    {
        return GraphQLError.super.toSpecification();
    }

    @Override
    public Map<String, Object> getExtensions()
    {
        return GraphQLError.super.getExtensions();
    }
}
