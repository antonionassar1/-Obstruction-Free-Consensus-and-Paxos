package demo;
import akka.actor.*;
import java.util.*;



public class Main {

    public static int N = 100;
    public static int f=49;
    public static double tle=2;
    public static double probability_failure=1;


    public static void main(String[] args) throws InterruptedException {

        // Instantiate an actor system
        final ActorSystem system = ActorSystem.create("system");
        system.log().info("System started with N=" + N );

        ArrayList<ActorRef> references = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            // Instantiate processes
            final ActorRef a = system.actorOf(Process.createActor(i + 1, N,probability_failure), "" + i);
            references.add(a);
        }

        //give each process a view of all the other processes
        Members m = new Members(references);
        for (ActorRef actor : references) {
            actor.tell(m, ActorRef.noSender());
        }

       
        Collections.shuffle(references);
        for (int i=0;i<f;i++){
            references.get(i).tell("crash", ActorRef.noSender());
        }

        long starttime = System.currentTimeMillis();
        system.log().info("START");
        for (int i=0;i<N;i++){
          references.get(i).tell(new StartTimeMsg(starttime),ActorRef.noSender());
          references.get(i).tell("launch", ActorRef.noSender());
        }

        Thread.sleep((long) (tle*1000));
        for (int i=0;i<N-1;i++){                                      //The (N-1) process is the leader
          references.get(i).tell("hold", ActorRef.noSender());
        }

        // We wait 5 seconds before ending the system
	    try {
			  waitBeforeTerminate();
      } catch (InterruptedException e) {
          e.printStackTrace();
      } finally {
          system.terminate();
      }
    }
    public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(5000);
	}
}