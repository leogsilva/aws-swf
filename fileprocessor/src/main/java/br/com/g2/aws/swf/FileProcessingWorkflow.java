package br.com.g2.aws.swf;

import java.io.IOException;

import com.amazonaws.services.simpleworkflow.flow.annotations.Execute;
import com.amazonaws.services.simpleworkflow.flow.annotations.GetState;
import com.amazonaws.services.simpleworkflow.flow.annotations.Workflow;
import com.amazonaws.services.simpleworkflow.flow.annotations.WorkflowRegistrationOptions;

/**
 * Contract for file processing workflow
 */
@Workflow
@WorkflowRegistrationOptions(defaultExecutionStartToCloseTimeoutSeconds = 300, defaultTaskStartToCloseTimeoutSeconds = 10)
public interface FileProcessingWorkflow {

  @Execute(name = "ProcessFile", version = "1.0")
  public void processFile(String sourceBucketName, String sourceFilename, String targetBucketName,
      String targetFilename) throws IOException;

  @GetState
  public String getState();

}