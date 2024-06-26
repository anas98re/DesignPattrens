// This example shows how to create complex graphical shapes, composed of simpler shapes and 
// treat both of them uniformly.

//Employee: An interface that declares the operations that objects can perform
//(get name and salary, display details).
interface Employee {
    String getName();
    double getSalary();
    void showDetails();
}


//Developer and Manager: Single objects representing employees. 
//It implements the operations declared in Employee.
class Developer implements Employee {
    private String name;
    private double salary;

    public Developer(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getSalary() {
        return salary;
    }

    @Override
    public void showDetails() {
        System.out.println("Developer: " + getName() + ", Salary: " + getSalary());
    }
}

class Manager implements Employee {
    private String name;
    private double salary;

    public Manager(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getSalary() {
        return salary;
    }

    @Override
    public void showDetails() {
        System.out.println("Manager: " + getName() + ", Salary: " + getSalary());
    }
}


class Manager implements Employee
{
    private $name;
    private $salary;

    public function __construct(string $name, float $salary)
    {
        $this->name = $name;
        $this->salary = $salary;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function getSalary(): float
    {
        return $this->salary;
    }

    public function showDetails(): void
    {
        echo "Manager: " . $this->getName() . ", Salary: " . $this->getSalary() . "\n";
    }
}

//Composite  class  
class Department implements Employee {
    private String name;
    private List<Employee> employees = new ArrayList<>();

    public Department(String name){
        this.name = name;    
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void getSalary(Employee employee){
        double totalSalary = 0;
        for (Employee employee : employees){
            totalSalary += employee.getSalary();
        }
    }

    public functoin addEmployee(Employee employee){
        employees.add(employee);
    }

    @Override
    public void showDetails() {
        System.out.println("Department: " + getName());
        for (Employee employee : employees) {
            employee.showDetails();
        }
    }
}

//Client Code
public class CompositePatternExample {
    public static void main(String[] args) {
        Employee dev1 = new Developer("John Doe", 10000);
        Employee dev2 = new Developer("Jane Doe", 12000);
        Employee manager = new Manager("John Smith", 20000);

        Department developmentDepartment = new Department("Development Department");
        developmentDepartment.addEmployee(dev1);
        developmentDepartment.addEmployee(dev2);

        Department company = new Department("Company");
        company.addEmployee(developmentDepartment);
        company.addEmployee(manager);

        System.out.println("Company Structure:");
        company.showDetails();
        System.out.println("Total Company Salary: " + company.getSalary());
    }
}

// Code explanation:
// Employee: An interface that declares the operations that objects can perform 
// (get name and salary, display details).
// Developer and Manager: Single objects representing employees. 
// It implements the operations declared in Employee.
// Department: A composite object that represents a department 
// containing employees or other departments. It executes operations 
// declared in Employee and distributes them to child objects.
// How to use:
// Create Developer and Manager objects.
// Create a Department object and add employees to it.
// Create another Department object representing the company 
// and add departments and employees to it.
// Call showDetails to view the entire organizational structure 
// and call getSalary to get the total salaries.

// +------------------+
// |    Employee      |
// |------------------|
// | + getName(): String |
// | + getSalary(): double|
// | + showDetails(): void|
// +---------+--------+
//           ^
//           |
//           +-----------------------------+
//           |                             |
// +---------+---------+        +----------+-----------+
// |      Developer     |        |       Manager        |
// |-------------------|        |----------------------|
// | - name: String    |        | - name: String       |
// | - salary: double  |        | - salary: double     |
// |-------------------|        |----------------------|
// | + getName(): String|       | + getName(): String  |
// | + getSalary(): double|     | + getSalary(): double|
// | + showDetails(): void|     | + showDetails(): void|
// +-------------------+        +----------------------+
//           ^
//           |
//           +--------------------------------+
//           |                                |
// +---------+---------+                      |
// |     Department    |                      |
// |-------------------|                      |
// | - name: String    |                      |
// | - employees: List<Employee>|             |
// |-------------------|                      |
// | + getName(): String|                     |
// | + getSalary(): double|                   |
// | + addEmployee(Employee)|                 |
// | + showDetails(): void|                   |
// +-------------------+
