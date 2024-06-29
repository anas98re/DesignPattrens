// Each task can have subtasks. This enables us to organize tasks and projects 
// hierarchically, making software project management more flexible and scalable.

abstract class Component {
    protected Date startDate;
    protected int plannedDuration;
    protected int actualDuration;
    protected Date expectedEndDate;

    public Date getStartDate() {
        return startDate;
    }

    public int getPlannedDuration() {
        return plannedDuration;
    }

    public int getActualDuration() {
        return actualDuration;
    }

    public Date getExpectedEndDate() {
        return expectedEndDate;
    }

    public void add(Component component) {
        throw new UnsupportedOperationException();
    }

    public void remove(Component component) {
        throw new UnsupportedOperationException();
    }

    public Component getChild(int index) {
        throw new UnsupportedOperationException();
    }

    public abstract Date getEndDate();
}


class Project implements Component {
    private List<Component> components = new ArrayList<>();

    @Override
    public void add(Component component) {
        components.add(component);
    }

    @Override
    public void remove(Component component) {
        components.remove(component);
    }

    @Override
    public Component getChild(int index) {
        return components.get(index);
    }

    @Override
    public Date getStartDate(){
        Date earliestStartDate = null;
        for (Component component : components) {
            if (earliestStartDate == null || component.getStartDate().before(earliestStartDate)) {
                earliestStartDate = component.getStartDate();
            }
        }
        return earliestStartDate;
    }

    @Override
    public Date getEndDate() {
        Date latestEndDate = null;
        for (Component component : components) {
            if (latestEndDate == null || component.getEndDate().after(latestEndDate)) {
                latestEndDate = component.getEndDate();
            }
        }
        return latestEndDate;
    }

    public int getTotalEffort(){
        int totalEffort = 0;

        for (Component component : components) {
            totalEffort += component.getTotalEffort();
        }
        return totalEffort;
    }
}

class Task extends Component{
    public Task(Date startDate, Date endDate, int actualDuration, int expectedDuration){
        this.startDate = startDate;
        this.plannedDuration = plannedDuration;
        this.actualDuration = actualDuration;
        this.expectedEndDate = expectedEndDate;
    }

    @Override
    public Date getEndDate(){
        return expectedEndDate;
    }
}

public class ProjectManagement {
    public static void main(String[] args) {
        //Create Tasks
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1);
        Task task1 = new Task(calendar.getTime(), 10, 8, calendar.getTime());
        calendar.set(2023, Calendar.FEBRUARY, 1);
        Task task2 = new Task(calendar.getTime(), 20, 18, calendar.getTime());
        
        // Create project
        Project project = new Project();
        project.add(task1);
        project.add(task2);

        //show project details
        System.out.println("Project Start Date: " + project.getStartDate());
        System.out.println("Project End Date: " + project.getEndDate());
        System.out.println("Total Effort: " + project.getTotalEffort());
    }
}

//                  +-------------------------+
//                  |       Component         |
//                  +-------------------------+
//                  | - startDate: Date       |
//                  | - plannedDuration: int  |
//                  | - actualDuration: int   |
//                  | - expectedEndDate: Date |
//                  +-------------------------+
//                  | + getStartDate(): Date  |
//                  | + getEndDate(): Date    |
//                  | + getPlannedDuration(): int |
//                  | + getActualDuration(): int  |
//                  | + getExpectedEndDate(): Date|
//                  | + add(component: Component): void |
//                  | + remove(component: Component): void|
//                  | + getChild(index: int): Component  |
//                  +-------------------------+
//                            |
//           +----------------+----------------+
//           |                                 |
// +---------+----------+            +---------+----------+
// |      Project       |            |         Task       |
// +--------------------+            +--------------------+
// | - components: List<Component>   |                    |
// +--------------------+            +--------------------+
// | + getStartDate()   |            | + getStartDate()   |
// | + getEndDate()     |            | + getEndDate()     |
// | + getPlannedDuration() |        | + getPlannedDuration() |
// | + getActualDuration()  |        | + getActualDuration()  |
// | + getExpectedEndDate() |        | + getExpectedEndDate() |
// | + add(component: Component): void |
// | + remove(component: Component): void |
// | + getChild(index: int): Component   |
// +--------------------+            +--------------------+
