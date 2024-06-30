// Strategy Pattern, which allows different algorithms (display strategies) 
// to be separated from the objects that use those algorithms, making it easier to expand and 
// change in the future.


// Basic steps:
// 1- Definition of interfaces for different strategies:
//  -Interface for display strategy by age segment.
//  -Interface for display strategy by device type.
// 2-Create different display strategies:
//  -Different strategies for each age group.
//  -Different strategies for each device type.
// 3-Context object that uses strategies:
//  -Responsible for identifying and implementing the appropriate strategy 
// based on age segment and device type

interface AgrGroupStrategy {
    public void displayContent(String content);
}

interface TypeDeviceStrategy {
    public void displayContent(String content);
}

//Strategies for AgrGroupStrategy Interface
public class ChildDisplayStrategy implements AgrGroupStrategy {
    @Override
    public void displayContent(String content) {
        System.out.println(""+content);
    }
}

public class TeenDisplayStrategy implements AgrGroupStrategy {
    @Override
    public void displayContent(String content) {
        System.out.println(""+content);
    }
}

public class AdultsDisplayStrategy implements AgrGroupStrategy {
    @Override
    public void displayContent(String content) {
        System.out.println(""+content);
    }
}


//Strategies for TypeDeviceStrategy Interface
public class LaptopDeviceStrategy implements TypeDeviceStrategy {
    @Override
    public void displayContent(String content) {
        System.out.println(""+content);
    }
}

public class TapDeviceStrategy implements TypeDeviceStrategy{
    @Override
    public void displayContent(String content) {
        system.println(""+content);
    }
}

public class MobileDeviceStrategy implements TypeDeviceStrategy{
    @Override
    public void displayContent(String content) {
        System.out.println(""+content();    
    }
}

//Context 
public claas ContextStrategy {
    private AgrGroupStrategy agentGroupStrategy;
    private TypeDeviceStrategy typeDeviceStrategy;

    public void ContextStrategy(AgrGroupStrategy agentGroupStrategy, TypeDeviceStrategy type){
        this.agentGroupStrategy = agentGroupStrategy;
        this.typeDeviceStrategy = typeDeviceStrategy;
    }

    public void displayContent(String content){
        //show the content using ageStrategy
        agentGroupStrategy.displayContent(content);

        //show the content using T
        typeDeviceStrategy.displayContent(content);
    }
}

public class Main {
    public void static void main(String arge[] ){
        //detemine the strtegies according of child age or mobile type, for example
        AgrGroupStrategy ageGroupStrategy = new TeenDisplayStrategy();
        TapDeviceStrategy typeDeviceStrategy = new MobileDeviceStrategy();


        ContextStrategy contextStrategy = new ContextStrategy(ageGroupStrategy , typeDeviceStrategy);

        context.displayContent("Some interesting content");
    }
}

// +------------------------------------+
// |            DisplayContext          |
// |------------------------------------|
// | - ageGroupStrategy: AgeGroupDisplayStrategy |
// | - deviceTypeStrategy: DeviceTypeDisplayStrategy |
// |------------------------------------|
// | + DisplayContext(AgeGroupDisplayStrategy, DeviceTypeDisplayStrategy) |
// | + displayContent(content: String)  |
// +------------------------------------+
//                    / \
//                     |
//                     |
//     +--------------------------------------+
//     |                                      |
// +-----------------------------+   +------------------------------+
// |   AgeGroupDisplayStrategy   |   |   DeviceTypeDisplayStrategy  |
// |-----------------------------|   |------------------------------|
// | + displayContent(content: String) |   | + displayContent(content: String) |
// +-----------------------------+   +------------------------------+
//     /|\                                            /|\
//      |                                              |
//      |                                              |
// +------------------+  +------------------+  +-------------------+  +------------------+  +-------------------+
// | ChildDisplayStrategy |  | TeenDisplayStrategy |  | AdultDisplayStrategy |  | LaptopDisplayStrategy |  | TabletDisplayStrategy |
// |------------------|  |------------------|  |-------------------|  |------------------|  |-------------------|
// | + displayContent(content: String) |  | + displayContent(content: String) |  | + displayContent(content: String) |  | + displayContent(content: String) |  | + displayContent(content: String) |
// +------------------+  +------------------+  +-------------------+  +------------------+  +-------------------+
//                                                                                                             |
//                                                                                                             |
//                                                                                                      +-------------------+
//                                                                                                      | MobileDisplayStrategy |
//                                                                                                      |-------------------|
//                                                                                                      | + displayContent(content: String) |
//                                                                                                      +-------------------+
