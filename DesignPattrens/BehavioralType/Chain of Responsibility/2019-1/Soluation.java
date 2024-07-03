public abstract class Document {
    protected int id;
    protected String content;
    protected String status;
    protected Roel currentRole;

    public Document(int id, String content, String status){
        this.id = id;
        this.content = content;
        this.status = "Created";
    }

    public abstract void process();

    public void setCurrentRole(Role role){
        this.currentRole = role;
    }

    public void getCurrentRole(){
        return currentRole;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void getContent(){
        return content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

public class MaintenanceDoc extends Document{
    public MaintenanceDoc(int id, String content){
        super(id, content);
    }

    @Override
    public void process(){
        currentRole.handle(this);
    }
}

public class LeaveDoc extends Document{
    public LeaveDoc(int id, String content){
        super(id, content);
    }

    @Override
    public void process(){
        currentRole.handle(this);
    }
}

//Role
public abstract class Role {
    protected String name;
    protected Role next;

    public Role(String name, Role next){
        this.name = name;
    }

    public void setNext(Role next){
        this.next = next;
    }

    public abstract String handle(Document doc);
}

public class Employee extends Role {
    public Employee(){
        super("Employee")
    }

    @Override
    public String handle(Document doc){
        // Specific handling logic for Employee
        System.out.println("Employee handling document: " + doc.getContent());
        if(!next){
            doc.setCurrentRole(next);
            doc.process();
        } else {
            doc.setState("Completed");
        }
    }
}

public class MaintenanceEngr extends Role {
    public MaintenanceEngr(){
        super("MaintenanceEngr")
    }

    @Override
    public String handle(Document doc){
         // Specific handling logic for MaintenanceEngr
        System.out.println("MaintenanceEngr handling document: " + doc.getContent());
        if(!next){
            doc.setCurrentRole(next);
            doc.process();
        } else {
            doc.setState("Completed");
        }
    }
}

public class MaintenanceMgr extends Role {
    public MaintenanceEngr(){
        super("MaintenanceEngr")
    }

    @Override
    public String handle(Document doc){
    // Specific handling logic for Maintenance Manager
        System.out.println("Maintenance Manager handling document: " + doc.getContent());
        if (next != null) {
            doc.setCurrentRole(next);
            doc.process();
        } else {
            doc.setStatus("Completed");
        }    
    }
} 

public class DirectManager extends Role {
    public DirectManager() {
        super("Direct Manager");
    }

    @Override
    public void handle(Document doc) {
        // Specific handling logic for Direct Manager
        System.out.println("Direct Manager handling document: " + doc.getContent());
        if (next != null) {
            doc.setCurrentRole(next);
            doc.process();
        } else {
            doc.setStatus("Completed");
        }
    }
}

public class GeneralManager extends Role {
    public GeneralManager() {
        super("General Manager");
    }

    @Override
    public void handle(Document doc) {
        // Specific handling logic for General Manager
        System.out.println("General Manager handling document: " + doc.getContent());
        if (next != null) {
            doc.setCurrentRole(next);
            doc.process();
        } else {
            doc.setStatus("Completed");
        }
    }
}

public class main{
    public static void main(String[] args){
        // Create document
        Document document = new MaintenanceDoc(1, "MaintenanceDoc");

        Role employee = new Employee();
        Role maintenanceEngr = new MaintenanceEngr();
        Role maintenanceMgr = new MaintenanceMgr();
        Role directManager = new DirectManager();
        Role generalManager = new GeneralManager();

        // Set role hierarchy
        employee.setNext(maintenanceEngr);
        maintenanceEngr.setNext(maintenanceMgr);
        maintenanceMgr.setNext(directManager);
        directManager.setNext(generalManager);

        // Set current role for the document
        document.setCurrentRole(employee);

        // Process the document
        document.process();

        // Check the document status
        System.out.println("Document status: " + document.getStatus());

    }
}


// Employee      MaintenanceEngr     MaintenanceMgr     DirectManager     GeneralManager
//      |                |                   |                 |                     |
//      |                |                   |                 |                     |
//      |                |                   |                 |                     |
//      v                v                   v                 v                     v
//  Document        Document           Document         Document            Document