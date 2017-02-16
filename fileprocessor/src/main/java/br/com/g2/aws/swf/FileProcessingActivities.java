package br.com.g2.aws.swf;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;

@Activities(version="1.0")
@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 60, defaultTaskStartToCloseTimeoutSeconds = 60)
public interface FileProcessingActivities {

    public void processFile(String inputFileName, String outputFileName) throws Exception;
    
}