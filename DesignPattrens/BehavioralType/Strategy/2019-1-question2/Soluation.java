interface WorkFlowStrategy {
    Role getInitialRole() 
}

public class MaintenanceStrategy implements WorkFlowStrategy {
    @Override
    public Role getInitialRole(){
        Employee employee = new Employee();
        MaintenanceEngr engr = new MaintenanceEngr();
        MaintenanceMgr mgr = new MaintenanceMgr();

        employee.setNext(engr);
        engr.setNext(mgr);

        return employee;
    }
}

public class LeaveStrategy implements WorkFlowStrategy {
    @Override
    public Role getInitialRole(){
        Employee employee = new Employee();
        MaintenanceEngr engr = new MaintenanceEngr();
        MaintenanceMgr mgr = new MaintenanceMgr();

        employee.setNext(engr);
        engr.setNext(mgr);

        return employee;
    }
}

//Context
public class WorkFlowEngineContext {
    private WorkFlowStrategy strategy;

    public setStrategy(WorkFlowStrategy strategy){
        this.strategy = strategy;
    }

    public void startWorkFlow(Document doc){
        Role initialRole = strategy.getInitialRole();
        doc.setCurrentRole(initialRole);
        soc.process();
    }
}

public class Main{
    public void static main(String[] arge){
        WorkFlowEngineContext engine = new WorkFlowEngineContext();

        Document maintenanceDoc = new MaintenanceDoc(1, "Fix the air conditioner");
        Document leaveDoc = new LeaveDoc(2, "Annual leave request");

        engine.setStrategy(new MaintenanceStrategy());
        engine.startWorkFlow(maintenanceDoc);

        engine.setStrategy(new LeaveStrategy());
        engine.startWorkFlow(leaveDoc);

    }
}