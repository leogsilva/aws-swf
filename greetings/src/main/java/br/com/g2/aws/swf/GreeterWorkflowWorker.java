package br.com.g2.aws.swf;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
import com.amazonaws.services.simpleworkflow.flow.StartWorkflowOptions;
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker;
import com.amazonaws.services.simpleworkflow.model.WorkflowExecution;

public class GreeterWorkflowWorker {
  public static void main(String[] args) throws Exception {

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
    String taskListToPoll = "HelloWorldAsyncList";
    String lambdaRole = "arn:aws:iam::314558500693:role/swf-lambda-function";

     WorkflowWorker wfw = new WorkflowWorker(service, domain, taskListToPoll);
     wfw.addWorkflowImplementationType(GreeterWorkflowImpl.class);
     wfw.start();
//    GreeterWorkflowClientExternalFactory factory = new GreeterWorkflowClientExternalFactoryImpl(service, domain);
//    GreeterWorkflowClientExternal workflow = factory.getClient();
//    StartWorkflowOptions options = new StartWorkflowOptions().withLambdaRole(lambdaRole);
//    workflow.greet(options);
//
//    WorkflowExecution workflowExecution = workflow.getWorkflowExecution();
//    System.out.println("Started helloLambda workflow with workflowId=\"" + workflowExecution.getWorkflowId()
//        + "\" and runId=\"" + workflowExecution.getRunId() + "\"");
  }
}
