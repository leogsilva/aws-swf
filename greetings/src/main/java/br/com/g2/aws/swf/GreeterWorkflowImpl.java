package br.com.g2.aws.swf;

import com.amazonaws.services.simpleworkflow.flow.DecisionContext;
import com.amazonaws.services.simpleworkflow.flow.DecisionContextProvider;
import com.amazonaws.services.simpleworkflow.flow.DecisionContextProviderImpl;
import com.amazonaws.services.simpleworkflow.flow.WorkflowClock;
import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.worker.LambdaFunctionClient;

public class GreeterWorkflowImpl implements GreeterWorkflow {

  private GreeterActivitiesClientImpl operations = new GreeterActivitiesClientImpl();
  private DecisionContextProvider contextProvider = new DecisionContextProviderImpl();
  private WorkflowClock clock = contextProvider.getDecisionContext().getWorkflowClock();

  private DecisionContext decisionContext = contextProvider.getDecisionContext();
  private LambdaFunctionClient lambdaClient = decisionContext.getLambdaFunctionClient();

  @Override
  public void greet() {
    Promise<String> name = operations.getName();
    String lambdaFunctionName = "HelloWorld";
    String lambdaFunctionInput = "\"AWS Fans\"";
    Promise<String> scheduleLambdaFunction = lambdaClient.scheduleLambdaFunction(lambdaFunctionName,
        Promise.asPromise(lambdaFunctionInput));
    Promise<String> greeting = getGreeting(name,scheduleLambdaFunction);
    operations.say(greeting);
  }

  @Asynchronous
  private Promise<String> getGreeting(Promise<String> name, Promise<String> lambdaResult) {
    String returnString = "Hello " + name.get() + "! " + lambdaResult.get();
    return Promise.asPromise(returnString);
  }

}
