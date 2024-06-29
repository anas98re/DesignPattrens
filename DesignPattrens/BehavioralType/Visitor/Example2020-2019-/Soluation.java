interface Visitor{
    void visitProject(Project project);
    void visitTask(Task task);
}

interface Entity {
    void accept(Visitor visitor);
}

public class Project implements Visitor{
    private Date startDate;
    private Date endDate;
    private List<Task> tasks = new ArrayList<>();

    public Project(Date startDate, Date endDate){
        this.startDate = startDate;
        that.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitProject(this);
        for(Task task : tasks){
            task.accept(visitor);
        }
    }
}

public class Task implements Visitor {
    private Date startDate;
    private Date endDate;
    private int effort;

    public Task(Date startDate, Date endDate, int effort){
        this.startDate = startDate;
        this.endDate = endDate;
        this.effort = effort;
    }

    public Date getStartDate(){
        return startDate;
    }

    public Date getEndDate(){
        return endDate;
    }

    public int getEffort(){
        return effort;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitTask(this);
    }
}

public class StartEndTimeVisitor implements Visitor{
    private Date earliestStart;
    private Date latestEnd;

    public Date getEarliestStart(){
        return earliestStart;
    } 

    public Date getLatestEnd(){
        return latestEnd;
    }

    @Override
    public void visitProject(Project project){
        for(Task task : project.getTasks()){
            if(earliestStart == null || task.getStartDate.before(earliestStart)){
                earliestStart = task.getStartDate;
            } 
            if(latestEnd == null || task.getEndDate.after(latestEnd)){
                latestEnd = task.getEndDate;
            }
        }
    }

    @Override
    public void visitTask(Task task) {
        // No specific task calculation required for this visitor
    } 
}

public class TotalEffortVisitor implements Visitor{
    private int TotalEffort = 0;

    public int getTotalEffort(){
        return TotalEffort;
    }

    @Override
    public void visitProject(Project project) {
        for(Task task : project.getTasks){
            TotalEffort += task.getTotalEffort();
        }
    }

    @Override
    public void visitTask(Task task) {
        TotalEffort += task.getTotalEffort();
    }
}

public class Demo {
    public static void main(String[] args) {
        Project project = new Project(new Date(), new Date());\
        Calender calender = calender.getInstance();

        clalender.set(2023, calender.JULI, 1);
        Task task = new Task(calender.getTime(), calender.getTime(), 5);

        calendar.set(2023, Calendar.JULY, 5);
        Task task2 = new Task(calendar.getTime(), calendar.getTime(), 10);

        project.addTask(task1);
        project.addTask(task2);

        StartEndTimeVisitor timeVisitor = new StartEndTimeVisitor();
        project.accept(timeVisitor);

        TotalEffortVisitor effortVisitor = new TotalEffortVisitor();
        project.accept(effortVisitor);

        System.out.println("Earliest Start Date: " + timeVisitor.getEarliestStart());
        System.out.println("Latest End Date: " + timeVisitor.getLatestEnd());
        System.out.println("Total Effort: " + effortVisitor.getTotalEffort());
    }
}


            //                            +----------------------+
            //                            |      Visitor         |
            //                            +----------------------+
            //                            | + visitProject()     |
            //                            | + visitTask()        |
            //                            +----------+-----------+
            //                                       |
            //               +-----------------------+------------------------+
            //               |                                                |
            // +-------------+-------------+                    +-------------+-------------+
            // |     StartEndTimeVisitor   |                    |   TotalEffortVisitor      |
            // +---------------------------+                    +---------------------------+
            // | + visitProject()          |                    | + visitProject()          |
            // | + visitTask()             |                    | + visitTask()             |
            // +---------------------------+                    +---------------------------+
            //               |                                                |
            //               +----------------+----------------+               +----------------+----------------+
            //                                |                                |
            //            +-------------------+----------------+ +-------------+-------------+ +-------------------+----------------+
            //            |         Entity (interface)         | |           Project         | |          Task (Task)               |
            //            +------------------------------------+ +--------------------------+ +--------------------------+
            //            | + accept(visitor: Visitor)         | | + accept(visitor: Visitor)| | + accept(visitor: Visitor)         |
            //            +------------------------------------+ +--------------------------+ +--------------------------+
            //                                                       |                                           |
            //                                                       |                                           |
            //                                       +----------------+----------------+        +----------------+----------------+
            //                                       |                                 |        |                                 |
            //                            +----------+----------+            +---------+----------+            +----------+----------+
            //                            |     Project         |            |         Task      |            |         Task         |
            //                            +---------------------+            +-------------------+            +---------------------+
            //                            | - startDate: Date   |            | - startDate: Date |            | - startDate: Date   |
            //                            | - endDate: Date     |            | - endDate: Date   |            | - endDate: Date     |
            //                            | - tasks: List<Task> |            | - effort: int     |            | - effort: int       |
            //                            +---------------------+            +-------------------+            +---------------------+


