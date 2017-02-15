package br.com.g2.aws.swf;

public class GreeterActivitiesImpl implements GreeterActivities {
  
  @Override
  public String getName() {
    return "World";
  }

  @Override
  public void say(String what) {
    System.out.println(what);
  }

}
