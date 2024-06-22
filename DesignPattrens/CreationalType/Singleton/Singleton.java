
public class Singleton {
    
    //create an object of SingleObject
    private static Singleton instance = new Singleton();

    // make the constructor private so that this the class cannot be instantiated
    private  SingleObject() {};

    //get  the only object available
    public static Singleton getInstance() {
        return instance;
    }

    public void showMessage(){
        system.out.println("Hello World");
    }
}

public class SingletonPatternDemo {
    public static void main(String[] args){

        //illegal constructor
        //compile time error: the constructor singletoneObject() is not available
        //singleton object() = new Singleton();

        //get the only object available
        singletoneObject object = Singleton.getInstance();

        //show message
        object.showMessage();
    }
}

//Verify the output.

//Hello World!

