package com.assignment.aws.lambdaFunction.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.assignment.aws.lambdaFunction.model.Feedback;
import com.assignment.aws.lambdaFunction.model.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class GetDataHandler implements Function<String,Response> {
	
	private Logger logger = LoggerFactory.getLogger(GetDataHandler.class);
	Response resp = new Response();
	@Override
	public Response apply(final String string) {
		
	        
			AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
			// Change to your Table_Name (you can load dynamically from lambda env as well)
			DynamoDBMapperConfig mapperConfig = new DynamoDBMapperConfig.Builder().withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement("feedback")).build();

			DynamoDBMapper mapper = new DynamoDBMapper(client, mapperConfig);

			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

			// Change to your model class   
			List<Feedback> scanResult = mapper.scan(Feedback.class, scanExpression);
			String response = "{";
			Map<String,String> dataMap = new HashMap<>();
			for(Feedback feedback: scanResult) {
				logger.info("id = " + feedback.getId());
				logger.info("rating = " + feedback.getRating());
				String key = "\"" + feedback.getId() + "\"";
				String value = "\"" + feedback.getRating() + "\"";
				dataMap.put(key, value);
			}
			Response resp = new Response();
			resp.setResponse(scanResult);
			response = dataMap.toString() + "}";
	
			// Check the count and iterate the list and perform as desired.
			logger.info("Size = " + scanResult.size());
			logger.info("Feedback Data = " + dataMap);
			
			return resp;
//		} catch(Exception e) {
//			//return "<!DOCTYPE html><h3>Something was not right</h3>";
//			return resp;
//		}
	}
}
