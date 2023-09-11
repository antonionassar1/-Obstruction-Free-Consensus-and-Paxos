package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class Process extends UntypedAbstractActor {
    
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);// Logger attached to actor
    private final int N;//number of processes
    private final int id;//id of current process
    private Members processes;//other processes' references
    private Integer proposal;
    private int ballot;
    private int readballot;
    private int imposeballot;
    private Integer estimate;
    private Integer states[][] ;
    private boolean fault_prone_mode;
    private int gather_count;
    private int ack_count;
    private boolean propose_launched;
    private boolean value_decided;
    public double probability_failure;
    private boolean silent_mode;
    private Integer input_value;
    private boolean hold;
    private long starttime;

    public final int get_id() {return id;}
    public Process(int ID, int nb,double probability_failure) {
        this.probability_failure=probability_failure;
        this.N = nb;
        this.id = ID;
        this.ballot =this.id-this.N;
        this.gather_count = 0;
        this.proposal=null;
        this.readballot=0;
        this.imposeballot=this.id-this.N;
        this.estimate=null;
        this.states = new Integer[this.N][2];
        for (int i = 0; i < this.N; i++) {
            this.states[i][0] = null;
            this.states[i][1] = 0;
        }
        this.ack_count = 0;
        this.fault_prone_mode=false;
        this.propose_launched=false;
        this.silent_mode=false;
        this.value_decided=false;
        this.hold=false;
        
    }
    
    //Static function creating actor
    public static Props createActor(int ID, int nb,double probability_failure) {
        return Props.create(Process.class, () -> {
            return new Process(ID, nb,probability_failure);
        });
    }
    public String toString() {
        return "Process{" + " id = " + id + " }";
    }

    
    private void Propose(Integer v) {
 
        this.propose_launched=true;
        this.proposal = v;
        this.gather_count=0;
        this.ballot=this.ballot+this.N;
        for (int i = 0; i < this.N; i++) {
            this.states[i][0] = null;
            this.states[i][1] = 0;
        }

        log.info("p"+self().path().name()+" propose " + Integer.toString(v));
        for (ActorRef actor : this.processes.references) {
            actor.tell(new ReadMsg(ballot), getSelf());
            
        }
    }


    private void readReceived(int newBallot, ActorRef pj) {
        
        if (this.readballot > newBallot || this.imposeballot > newBallot) {
            pj.tell(new AbortMsg(newBallot), getSelf());
            
        } else {
            this.readballot = newBallot;
            pj.tell(new GatherMsg(newBallot, this.imposeballot, this.estimate), getSelf());
        }
        
    }

    private void abortReceived(int ballot) { 
        if (ballot==this.ballot){                                   
            this.propose_launched=false;
            if (this.value_decided==false && this.hold==false){
                this.Propose(this.input_value);
            }    
        }
        
        return;
    }

    private void gatherReceived(int ballot,int estballot, Integer est, ActorRef pj) {
        if (this.propose_launched && this.ballot==ballot){
            this.gather_count++;
            int indice_pj=0;
            for (int i=0;i<this.N;i++){
                if (pj==this.processes.references.get(i)){
                    indice_pj=i;
                }
            }
            this.states[indice_pj][0] = est;
            this.states[indice_pj][1] = estballot;

            if (gather_count > this.N/2) {
                int max_estballot=-1;
                int indice_max_estballot=0;
                for (int i=0;i<N;i++){
                    if (max_estballot<this.states[i][1]){
                        max_estballot=this.states[i][1];
                        indice_max_estballot=i;
                    }
                }

                if (max_estballot > 0){
                    this.proposal = this.states[indice_max_estballot][0];
                }
                
                for (int i = 0; i < this.N; i++) {
                    this.states[i][0] = null;
                    this.states[i][1] = 0;
                }
                //log.info("impose sent " + self().path().name() );
                this.ack_count=0;
                this.gather_count = 0;
                for (ActorRef actor : this.processes.references){
                    actor.tell(new ImposeMsg(this.ballot, this.proposal), getSelf());
                }
            }
        }
        
    }


    private void imposeReceived(int newBallot, Integer value, ActorRef pj) {
        if (this.readballot > newBallot || this.imposeballot > newBallot) {
            pj.tell(new AbortMsg(newBallot), getSelf());
           
        } else {
            this.estimate = value;
            this.imposeballot = newBallot;
            pj.tell(new AckMsg(newBallot), getSelf());
            
        }
    }


    private void ackReceived(int ballot) {
        if (this.propose_launched && this.ballot==ballot){
            this.ack_count++;
            if (this.ack_count > this.N/2) {
                for (ActorRef actor : this.processes.references){
                    actor.tell(new DecideMsg(this.proposal), getSelf());
                }
                this.ack_count = 0;
            }
        }
    }


    private void decideReceived(Integer value) {
        log.info(self().path().name()+" decide: " + value);
        this.propose_launched=false;
        this.value_decided=true;
        
                                                           
      /*  for (ActorRef actor : this.processes.references){
            actor.tell(new DecideMsg(value), getSelf());
        }*/
        
    }

    public void onReceive(Object message) throws Throwable {
        if (message instanceof Members) {//save the system's info
            Members m = (Members) message;
            processes = m;
            //log.info("p" + self().path().name() + " received processes info");
        } else if (message instanceof String){
            if (message=="crash"){
                this.fault_prone_mode=true;
            }
            else if (message=="launch"){
                int val=(int)(Math.random() * (2));
                this.input_value=val;
                this.Propose(this.input_value);
            }
            else if (message=="hold"){
                //log.info(self().path().name() + " hold");
                this.hold=true;
            }
        }
        else if (message instanceof StartTimeMsg) {
			StartTimeMsg m = (StartTimeMsg) message;
			this.starttime = m.starttime;
        }
        else{
            double valeur_aleatoire=Math.random();
            if (this.fault_prone_mode==true && this.silent_mode==false && valeur_aleatoire<this.probability_failure){
                this.silent_mode=true;
                log.info(self().path().name() +" crash");
            }
            if (this.silent_mode==false){
                if (message instanceof ReadMsg) {
                    ReadMsg m = (ReadMsg) message;
                    this.readReceived(m.ballot, getSender());
                } else if (message instanceof AbortMsg) {
                    AbortMsg m = (AbortMsg) message;
                    this.abortReceived(m.ballot);
                } else if (message instanceof GatherMsg) {
                    GatherMsg m = (GatherMsg) message;
                    this.gatherReceived(m.ballot,m.estballot, m.est, getSender());
                } else if (message instanceof ImposeMsg) {
                    ImposeMsg m = (ImposeMsg) message;
                    this.imposeReceived(m.ballot, m.value, getSender());
                } else if (message instanceof AckMsg) {
                    AckMsg m = (AckMsg) message;
                    this.ackReceived(m.ballot);
                }    else if (message instanceof DecideMsg) {
                    DecideMsg m = (DecideMsg) message;
                    long endtime = System.currentTimeMillis();		
				    log.info("p" + getSelf().path().name() + " decided " + m.value +" with consensus latency " + Long.toString((endtime - this.starttime)));
                    this.decideReceived(m.value);
                    
                }
            }
        }
    }


    
}