//Event Calass
public class BroadcastEvent extends java.util.EventObject {
    public BroadcastEvent(Object source){
        super(source);
    }
}

//listener interface
public interface BroadcastListener extends java.util.EventListener {
    void onBroadcastReceived(BroadcastEvent event);
}

//Sender Class
class Broadcast {
    private  ArrayList<BroadcastListener> listeners = new ArrayList<>();

    public void senderBroadcast(){
        fireBroadcastReceived();
    }

    public synchronized void addBroadcastListener(BroadcastListener l){
        if(listeners.contains(l)){return;}
        listeners.add(l);
    }

    public synchronized void removeBroadcastListener(BroadcastListener l){
        listeners.remove(l);
    }

    public void fireBroadcastReceived(){
        //The next line of code creates a copy of the list of listeners to ensure 
        //that iteration to recipients is not affected by any changes that may occur 
        //to the list during the notification process:
        ArrayList<BroadcastListener> tempListeners = (ArrayList<BroadcastListener>) listeners.clone();
        
        if (tempListeners.size() == 0) {
            return;
        }

        BroadcastEvent Event = new BroadcastEvent(this);

        for(BroadcastListener l : tempListeners){
            l.onBroadcastReceived(event);
        }
    
    }
}

public class BroadcastReceiver implements BroadcastListener {
    @Override
    public void onBroadcastReceived(BroadcastEvent e) {
        System.out.println("Broadcast received!");
    }
}

public class Main {
    public static void main(String[] args) {
        Broadcaster broadcaster = new Broadcaster();
        BroadcastReceiver receiver = new BroadcastReceiver();

        broadcaster.addBroadcastListener(receiver);

        //send broadcast
        broadcaster.sendBroadcast();
    }
}

// +----------------+      implements       +--------------------+
// |                |<----------------------+                    |
// |  BroadcastListener  |                   | BroadcastReceiver |
// |                |                        |                    |
// +-------+--------+                        +--------+-----------+
//         ^                                          |
//         |                                          |
//         |                                          |
//         |                                          |
//         |  +-----------------+                     |
//         |  |                 |                     |
//         |  |   Broadcaster   |---------------------+
//         |  |                 |  fireBroadcastReceived()
//         |  +-----------------+
//         |        ^       ^
//         |        |       |
//         |        |       |
//         |   addBroadcastListener()
//         |        |
//         |        |
// +-------+--------+  +---------------------+
// |                |  |                     |
// |  BroadcastEvent |  |     Main           |
// |                |  |                     |
// +----------------+  +---------------------+