AWS SDK for Java Sample

This sample demonstrates how to make basic requests to AWS services using the AWS SDK for Java.
For lots more information on using the AWS SDK for Java, including information on high-level APIs and advanced features, check out the AWS SDK for Java Developer Guide.

Stay up to date with new features in the AWS SDK for Java by following the AWS Java Developer Blog.

Prerequisites

You must have a valid Amazon Web Services developer account. If you don't have an account yet, you can get started for free.
Running the Sample

Configure your AWS security credentials in a .aws/credentials file in your home directory. For example:
  [default]
  aws_access_key_id     = <your AWS access key>
  aws_secret_access_key = <your AWS secret access key>
More information on configuring ~/.aws/config 

Run mvn package exec:java to build and run the AwsSdkSample class.
