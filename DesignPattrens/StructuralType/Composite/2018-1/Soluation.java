abstract class ProjectUnit {
    protected String name;
    protected Date startDate;
    protected int plannedDuration; // in days
    protected int actualDuration;  // in days
    protected Date expectedEndDate;

    public ProjectUnit(String name, Date startDate, int plannedDuration){
        this.name = name;
        this.startDate = startDate;
        this.plannedDuration = plannedDuration;
    }

    public abstract void calculateAttributes();

    // Methods to manage child units
    public void add(ProjectUnit unit) {
        throw new UnsupportedOperationException();
    }

    public void remove(ProjectUnit unit) {
        throw new UnsupportedOperationException();
    }

    public List<ProjectUnit> getUnits() {
        throw new UnsupportedOperationException();
    }

    // Getters and setters for attributes
}

public class Task extends ProjectUnit {
    public Task(String name, Date startDate, int plannedDuration){
        super(name, startDate, plannedDuration);
    }

    @Override
    public void calculateAttributes() {
        // Calculate actualDuration and expectedEndDate for the task
        this.actualDuration = this.plannedDuration; // Example calculation
        this.expectedEndDate = new Date(this.startDate.getTime()
             + (long)this.plannedDuration * 24 * 60 * 60 * 1000);
    }
}

public class phase extends ProjectUnit {
    private List<ProjectUnit> units = new ArrayList<ProjectUnit>();
    public phase(String name, Date startDate, int plannedDuration){
        super(name, startDate, plannedDuration);
    }

    @Override
    public void add(ProjectUnit unit){
        units.add(unit);
    }

    @Override
    public void remove(ProjectUnit unit){
        units.remove(unit);
    }

    @Override
    public List<ProjectUnit> getUnits(){
        return units;
    }

    @Override
    public void calculateAttributes() {
         // Calculate attributes by aggregating child units
        for (ProjectUnit unit : units) {
            unit.calculateAttributes();
            this.plannedDuration += unit.plannedDuration;
            this.actualDuration += unit.actualDuration;
            // Update startDate and expectedEndDate based on child units
        }
    }
}

public class Project extends ProjectUnit {
    private List<ProjectUnit> units = new ArrayList<>();

    public Project(String name, Date startDate, int plannedDuration) {
        super(name, startDate, plannedDuration);
    }

    @Override
    public void add(ProjectUnit unit) {
        units.add(unit);
    }

    @Override
    public void remove(ProjectUnit unit) {
        units.remove(unit);
    }

    @Override
    public List<ProjectUnit> getUnits() {
        return units;
    }

    @Override
    public void calculateAttributes() {
        // Reset attributes before calculating
        this.plannedDuration = 0;
        this.actualDuration = 0;
        
        for (ProjectUnit unit : units) {
            unit.calculateAttributes();
            this.plannedDuration += unit.plannedDuration;
            this.actualDuration += unit.actualDuration;
        }

        // Calculate expected end date based on the latest unit's end date
        Date latestEndDate = this.startDate;
        for (ProjectUnit unit : units) {
            if (unit.expectedEndDate.after(latestEndDate)) {
                latestEndDate = unit.expectedEndDate;
            }
        }
        this.expectedEndDate = latestEndDate;
    }
}

//                  +-------------------------+
//                  |       ProjectUnit       |
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
//           +----------------+----------------+------------------------|
//           |                                 |                        |
// +---------+----------+            +---------+----------+             V
// |      Project       |            |         Task       |    adding phases
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

