package br.com.g2.aws.swf;

import java.io.File;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfilesConfigFile;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;

public class GreeterActivitiesWorker {
  public static void main(String[] args) throws Exception {
    ClientConfiguration config = new ClientConfiguration().withSocketTimeout(70 * 1000);

    File configFile = new File(System.getProperty("user.home"), ".aws/credentials");
    AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider(
        new ProfilesConfigFile(configFile), "default");

    EndpointConfiguration endpointConfiguration = new EndpointConfiguration("https://swf.us-east-1.amazonaws.com",
        "us-east-1");
    AmazonSimpleWorkflow service = AmazonSimpleWorkflowClientBuilder.standard().withClientConfiguration(config)
        .withEndpointConfiguration(endpointConfiguration)
        .withCredentials(new AWSStaticCredentialsProvider(credentialsProvider.getCredentials())).build();
    
    String domain = "helloWorldWalkthrough3";
    String taskListToPoll = "HelloWorldAsyncList";

    ActivityWorker aw = new ActivityWorker(service, domain, taskListToPoll);
    aw.addActivitiesImplementation(new GreeterActivitiesImpl());
    aw.start();
  }
}
