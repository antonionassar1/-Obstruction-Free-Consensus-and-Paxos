package demo;
import akka.actor.ActorRef;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * Class containing the processes' references
 */
public class Members implements Serializable{
    private static final long serialVersionUID = 123212354545L;
    public final ArrayList<ActorRef> references;
    public final String data;

    public Members(ArrayList<ActorRef> refs) {
        this.references = new ArrayList<ActorRef>(refs);                //copie un peu plus propre
        String s="[ ";
        for (ActorRef a : refs){
            s+=a.path().name()+" ";
        }
        s+="]";    
        data=s;
    }
            
}