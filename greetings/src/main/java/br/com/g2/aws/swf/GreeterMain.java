package br.com.g2.aws.swf;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
import com.amazonaws.services.simpleworkflow.flow.StartWorkflowOptions;

public class GreeterMain {
  public static void main(String[] args) {
    ClientConfiguration config = new ClientConfiguration().withSocketTimeout(70 * 1000);

    String swfAccessId = System.getenv("AWS_ACCESS_ID");
    String swfSecretKey = System.getenv("AWS_SECRET_KEY");
    AWSCredentials awsCredentials = new BasicAWSCredentials(swfAccessId, swfSecretKey);

    EndpointConfiguration endpointConfiguration = new EndpointConfiguration("https://swf.us-east-1.amazonaws.com",
        "us-east-1");
    AmazonSimpleWorkflow service = AmazonSimpleWorkflowClientBuilder.standard().withClientConfiguration(config)
        .withEndpointConfiguration(endpointConfiguration)
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

    String domain = "helloWorldWalkthrough3";

    GreeterWorkflowClientExternalFactory factory = new GreeterWorkflowClientExternalFactoryImpl(service,
        domain);
    String lambdaRole = "arn:aws:iam::314558500693:role/swf-lambda-function";
    StartWorkflowOptions options = new StartWorkflowOptions().withLambdaRole(lambdaRole);
    GreeterWorkflowClientExternal greeter = factory.getClient("someID3");
    greeter.greet(options);
  }
}
