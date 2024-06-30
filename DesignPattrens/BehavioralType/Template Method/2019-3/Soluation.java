// we can use the Template Method. This pattern allows the general structure of an 
// algorithm to be defined in a method in the superclass while leaving the specific 
// details to be implemented in subclasses. In this way, each employee screen can 
// leverage the shared code of previous screens while retaining the ability to customize 
// each screen's implementation details.

// 1-Definition of a base class (Abstract Class) that contains the general structure of the algorithm.
// 2-Defining specific methods (Concrete Methods) in the subcategories that pertain to each screen

public abstract class EmployeeScreen {

    public final void ProcesseDocument(){
        displayPreviousData();
        inputData();
        saveData();
    }

    //it may empty in the first class
    protected void displayPreviousData(){
        //code to display the previous
    }

    protected abstract void inputData();

    // a way to save the data (may be same)
    protected abstract void saveData(){
        //code to save the data
    }
}

public class FirstEmployeeScreen extends EmailScreen{
    @Override
    protected void inputData(){
        //code to input data
        System.out.println("First employee entering data...");
    }
}

public class SecondEmployeeScreen extends EmailScreen{
    @Override
    public void displayPreviousData(){
        // a code to display the previous employee
        System.out.println("Second employee viewing data from first employee...");
    }

    @Override
    protected void inputData(){
        //code to input data
        System.out.println("second employee entering data...");
    }
}

public class ThirdEmployeeScreen extends EmailScreen{
    @Override
    public void displayPreviousData(){
        // a code to display the previous employee
        System.out.println("Third employee viewing data from first employee...");
    }

    @Override
    protected void inputData(){
        //code to input data
        System.out.println("Third employee entering data...");
    }
}

public class Main{
    public void static main(String[] args){
        EmployeeScreen firstScreen = new FirstEmployeeScreen();
        firstScreen.processDocument();

        EmployeeScreen secondScreen = new SecondEmployeeScreen();
        secondScreen.processDocument();

        EmployeeScreen thirdScreen = new ThirdEmployeeScreen();
        thirdScreen.processDocument();
    }
}
// +-------------------------+
// |      EmployeeScreen     |
// |-------------------------|
// | + processDocument()     |
// |-------------------------|
// | + displayPreviousData() |
// | + inputData()           |
// | + saveData()            |
// +-------------------------+
//            /|\
//             |
//             |
// +-------------------------+   +-------------------------+   +-------------------------+
// |  FirstEmployeeScreen    |   |  SecondEmployeeScreen   |   |  ThirdEmployeeScreen    |
// |-------------------------|   |-------------------------|   |-------------------------|
// | + inputData()           |   | + displayPreviousData() |   | + displayPreviousData() |
// |                         |   | + inputData()           |   | + inputData()           |
// +-------------------------+   +-------------------------+   +-------------------------+