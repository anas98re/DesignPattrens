// In this example, the Observer pattern establishes indirect collaboration between 
// objects of a text editor. Each time the Editor object changes, it notifies its 
// subscribers. EmailNotificationListener and LogOpenListener react to these notifications 
// by executing their primary behaviors.

// Subscriber classes aren’t coupled to the editor class and can be reused in other 
// apps if needed. The Editor class depends only on the abstract subscriber interface. 
// This allows adding new subscriber types without changing the editor’s code

//Observer
public interface EventListener {
    void update(String eventType, String file);
}

//ConcreteSubject
public class Editor {
    private List<EventListener> listeners = new ArrayList<>();

    public void attach(EventListener listener){
        listeners.add(listener);
    }

    public void detach(EventListener listener){
        listeners.remove(listener);
    }

    public void notifyObservers(String eventType, String file){
        for(EventListener listener : listeners){
            listener.update(eventType, file);
        }
    }

    public void openFile(String filePath){
        System.out.println("Opening file: " + filePath);
        notifyObservers("open", filePath);
    }

    public void saveFile(String filePath){
        System.out.println("Saving file: " + filePath);
        notifyObservers("save", filePath);
    }
}

//ConcreteObserver
public class EmailNotificationListener implements EventListener{
    private  String email;

    public void EmailNotificationListener(String email){
        this.email = email;
    }

    @Override
    public void upate(String eventType, String file){
        System.out.println("Email to " + email + ": Someone has performed " + eventType + " operation with the following file: " + file);
    }
}

public class LogOpenListener implements EventListener{
    private String logFile;

    public void LogOpenListener(String logFile){
        this.logFile = logFile;
    }

    @Override
    public void upate(String eventType, String file){
        System.out.println("Save to log " + logFile + ": Someone has performed " + eventType + " operation with the following file: " + file);
    }
}

public class Demo {
    public static void main(String[] args) {
        Editor editor = new Editor();

        EmailNotificationListener emailListener = new EmailNotificationListener("admin@example.com");
        LogOpenListener logListener = new LogOpenListener("/path/to/log/file.txt");

        editor.attach(emailListener);
        editor.attach(logListener);

        editor.openFile("test.txt");
        editor.saveFile("test.txt");
    }
}


// +---------------------+
// |      Subject        |
// +---------------------+
// | + attach(Observer)  |
// | + detach(Observer)  |
// | + notifyObservers() |
// +---------------------+
//          ^
//          |
// +---------------------+
// |      Editor         |
// +---------------------+
// | - observers: List   |
// | + attach(Observer)  |
// | + detach(Observer)  |
// | + notifyObservers() |
// | + openFile()        |
// | + saveFile()        |
// +---------------------+
//          |
//          |
// +---------------------+
// |      Observer       |
// +---------------------+
// | + update(eventType) |
// +---------------------+
//          ^
//          |
// +-----------------------------+
// |       ConcreteObserver      |
// +-----------------------------+
// | + update(eventType)         |
// | - EmailNotificationListener |
// | - LogOpenListener           |
// +-----------------------------+
