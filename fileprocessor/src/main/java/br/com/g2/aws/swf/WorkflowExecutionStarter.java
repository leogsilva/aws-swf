package br.com.g2.aws.swf;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.model.WorkflowExecution;

/**
 * This is used for launching a Workflow instance of
 * FileProcessingWorkflowExample
 */
public class WorkflowExecutionStarter {

  private static AmazonSimpleWorkflow swfService;
  private static String domain;

  public static void main(String[] args) throws Exception {

    // Load configuration
    ConfigHelper configHelper = ConfigHelper.createConfig();

    // Create the client for Simple Workflow Service
    swfService = configHelper.createSWFClient();
    domain = configHelper.getDomain();

    // Start Workflow instance
    String sourceBucketName = configHelper
        .getValueFromConfig(FileProcessingConfigKeys.WORKFLOW_INPUT_SOURCEBUCKETNAME_KEY);
    String sourceFilename = configHelper.getValueFromConfig(FileProcessingConfigKeys.WORKFLOW_INPUT_SOURCEFILENAME_KEY);
    String targetBucketName = configHelper
        .getValueFromConfig(FileProcessingConfigKeys.WORKFLOW_INPUT_TARGETBUCKETNAME_KEY);
    String targetFilename = configHelper.getValueFromConfig(FileProcessingConfigKeys.WORKFLOW_INPUT_TARGETFILENAME_KEY);

    FileProcessingWorkflowClientExternalFactory clientFactory = new FileProcessingWorkflowClientExternalFactoryImpl(
        swfService, domain);
    FileProcessingWorkflowClientExternal workflow = clientFactory.getClient();
    workflow.processFile(sourceBucketName, sourceFilename, targetBucketName, targetFilename);

    // WorkflowExecution is available after workflow creation
    WorkflowExecution workflowExecution = workflow.getWorkflowExecution();
    System.out.println("Started periodic workflow with workflowId=\"" + workflowExecution.getWorkflowId()
        + "\" and runId=\"" + workflowExecution.getRunId() + "\"");

    System.exit(0);
  }
}