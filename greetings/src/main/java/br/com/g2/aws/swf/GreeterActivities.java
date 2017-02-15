package br.com.g2.aws.swf;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;

@Activities(version="3.4")
@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 300, defaultTaskStartToCloseTimeoutSeconds = 10)
public interface GreeterActivities {

  public String getName();
  public void say(String what);
}
