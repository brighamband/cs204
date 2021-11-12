

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.Gauge;
import io.prometheus.client.Summary;


import java.io.IOException;


@Aspect
public class PrometheusAspect {

  //Here the prometheus metric is Built and registered
  //Notice that these metrics don't need to be static like the Main method Counters
  Counter numberOfIterations = Counter.build()
        .namespace("java")
        .name("number_of_iterations")
        .help("Counts the number of attempted inserts and removes")
        .register();

    // Failed removes metric
    Counter numFailedRemoves = Counter.build()
            .namespace("PrometheusMetrics")
            .name("Number_of_Failed_Removes")
            .help("This metric keeps track of how many times a remove has failed.")
            .register();

    // Failed adds metric
    Counter numFailedAdds = Counter.build()
            .namespace("PrometheusMetrics")
            .name("Number_of_Failed_Adds")
            .help("This metric keeps track of how many times an add has failed.")
            .register();

    // Current nodes metric
    Gauge numCurrentNodes = Gauge.build()
            .namespace("PrometheusMetrics")
            .name("Number_of_Current_Nodes")
            .help("This metric keeps track of how many nodes are currently on the server.")
            .register();

    // Add time metric
    Summary addRunTime = Summary.build()
            .namespace("PrometheusMetrics")
            .name("Time_it_takes_to_run_add")
            .help("This metric keeps track of how long it takes for BST add to run.")
            .register();


  /**
   * This pointcut targets the serverOperation method in the Main package
   */
  @Pointcut("execution(* Main.serverOperation(..))")
  public void serverOperationExecution(){}

  /**
   * This pointcut targets the startThread Method in the Main.java
   */
  @Pointcut("execution(* Main.startThread(..))")
  public void startThreadPointcut(){}

  //Add your other pointcut definitions here

  @Pointcut("execution(* BST.remove(T))")
  public void removeNodePointcut() {}

  @Pointcut("execution(* BST.add(T)))")
  public void addNodePointcut() {}

  /**
   * This After Advice tells the numberOfIterations to increment after the serverOperation finishes
   * @param joinPoint Holds a reference to the method that was executed
   */
  @After("serverOperationExecution()")
  public void afterServerOperation(JoinPoint joinPoint) {
    numberOfIterations.inc();
  }


  /**
   * Starts the Prometheus Exporter Server
   * All prometheus data is viewable on localhost:SERVER_PORT/metrics if prometheus is running and configured to scrape data from your given server port
   *@param joinPoint Holds a reference to the method that was executed
   */
  @Before("startThreadPointcut()")
  public void afterThreadInitialization(JoinPoint joinPoint) {
    final int SERVER_PORT = 8080;
          try {
            HTTPServer server = new HTTPServer(SERVER_PORT);
            System.out.println("Prometheus exporter running on port " + SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Prometheus exporter was unable to start");
            e.printStackTrace();
        }

  }

    @Around("removeNodePointcut()")
    public Object aroundAdviceRemove(ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            Object returnObject = joinPoint.proceed();
            numCurrentNodes.dec();
            return returnObject;
        }catch (FailedRemoveException e){
            numFailedRemoves.inc();
        }

        return null;
    }

    @Around("addNodePointcut()")
    public Object aroundAdviceAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        Summary.Timer timer;
        timer = addRunTime.startTimer();
        Object returnObject = joinPoint.proceed();
        timer.observeDuration();

        if (!(Boolean)returnObject) {      // If add was successful
            numCurrentNodes.inc();
        } else {                        // If add failed
            numFailedAdds.inc();
        }
        return returnObject;
    }


}
