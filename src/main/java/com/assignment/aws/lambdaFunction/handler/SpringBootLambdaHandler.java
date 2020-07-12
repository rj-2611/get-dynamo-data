package com.assignment.aws.lambdaFunction.handler;

import java.util.Map;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.assignment.aws.lambdaFunction.model.Feedback;
import com.assignment.aws.lambdaFunction.model.Request;
import com.assignment.aws.lambdaFunction.model.Response;


public class SpringBootLambdaHandler extends SpringBootRequestHandler<String,Response> {

}
