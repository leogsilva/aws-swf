package br.com.g2.aws.swf;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfilesConfigFile;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;

public class GreeterActivitiesWorker {

  public static final String HELP = "h";
  public static final String DOMAIN = "d";
  public static final String TASK_LIST = "l";

  public static void main(String[] args) throws Exception {
    Options options = new Options();
    options.addOption(Option.builder(DOMAIN).longOpt("domain").desc("The name of SWF domain").hasArg().argName("DOMAIN")
        .required().build());
    options.addOption(Option.builder(TASK_LIST).longOpt("task list").desc("The name of SWF task list").hasArg().argName("TASKLIST")
        .required().build());
    options.addOption(Option.builder("v").desc("Print the version of the application").longOpt("version").build());

    options.addOption(Option.builder("h").desc("Print the help of the application").longOpt("help").build());
    
    String domain = "helloWorldWalkthrough3";
    String region = "us-east-1";
    String taskList = "HelloWorldAsyncList";
    try {
      // parse the command line arguments
      CommandLineParser parser = new DefaultParser();
      CommandLine line = parser.parse(options, args);
    } catch (ParseException exp) {
      String header = "Do something useful with an input file\n\n";
      String footer = "\nPlease report issues at http://example.com/issues";
      System.err.println("Parsing failed.  Reason: " + exp.getMessage());
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("myapp", header, options, footer, true);
      System.exit(1);
    }

    ClientConfiguration config = new ClientConfiguration().withSocketTimeout(70 * 1000);

    File configFile = new File(System.getProperty("user.home"), ".aws/credentials");
    AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider(new ProfilesConfigFile(configFile),
        "default");

    EndpointConfiguration endpointConfiguration = new EndpointConfiguration("https://swf.us-east-1.amazonaws.com",
        "us-east-1");
    AmazonSimpleWorkflow service = AmazonSimpleWorkflowClientBuilder.standard().withClientConfiguration(config)
        .withEndpointConfiguration(endpointConfiguration)
        .withCredentials(new AWSStaticCredentialsProvider(credentialsProvider.getCredentials())).build();

    String taskListToPoll = "HelloWorldAsyncList";

    ActivityWorker aw = new ActivityWorker(service, domain, taskListToPoll);
    aw.addActivitiesImplementation(new GreeterActivitiesImpl());
    aw.start();
  }
}
