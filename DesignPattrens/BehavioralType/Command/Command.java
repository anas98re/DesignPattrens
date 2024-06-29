// Consider an app to control smart devices in the home. Command Pattern can be 
// used to allow users to control lights, air conditioners, and other devices by 
// sending commands through a unified interface (such as a smartphone app), where 
// users can easily turn on or off devices, and enjoy the flexibility of changing 
// commands without modifying the main structure of the application.

interface Command {
    void excute();
}

// Receiver for Light
class Light {
    public void turnOn(){
        System.out.println("Light is turned ON");
    }

    public void turnOff(){
        System.out.println("Light is turned OFF");
    }
}

// Receiver for AirConditioner
class AirConditioner {
    public void turnOn(){
        System.out.println("AirConditioner is turned ON");
    }

    public void turnOff(){
        System.out.println("AirConditioner is turned OFF");
    }
}

// Concrete Command for turning on the light
class TurnOnLightCommand implements Command{
    private Light light;

    public void TurnOnLightCommand(Light light){
        this.light = light;
    }

    @Override
    public void execute(){
        light.turnOn();
    }
}

// Concrete Command for turning off the light
class TurnOffLightCommand implements Command {
    private Light light;

    public TurnOffLightCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }
}

// Concrete Command for turning on the AirConditioner
class TurnOnAirConditionerCommand implements Command{
    private AirConditioner airCondition;

    public void TurnOnAirConditionerCommand(AirConditioner airCondition){
        this.airCondition = airCondition;
    }

    @Override
    public void execute() {
        airCondition.turnOn();
    }
}

// Concrete Command for turning off the air conditioner
class TurnOffAirConditionerCommand implements Command {
    private AirConditioner airConditioner;

    public TurnOffAirConditionerCommand(AirConditioner airConditioner) {
        this.airConditioner = airConditioner;
    }

    @Override
    public void execute() {
        airConditioner.turnOff();
    }
}

// Invoker
//RemoteControl is the caller that receives commands 
//and executes them when the button is pressed.
class RemotrControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressPattren(){
        command.execute();
    }
}

// Client
public class SmartHomeClient {
    public static void main(String[] args) {
        // Create receivers
        Light livingRoomLight = new Light();
        AirConditioner livingRoomLightAC = new AirConditioner();

        // Create commands
        Command turnOnLight = new TurnOnLightCommand(livingRoomLight);
        Command turnOffLight = new TurnOffLightCommand(livingRoomLight);
        Command TurnOnAc = new TurnOnAirConditionerCommand(livingRoomLightAC);
        Command TurnOffAir = new TurnOffAirConditionerCommand(livingRoomLightAC);

        // Create invoker
        RemoteControl remote = new RemoteControl();

        // Control light
        remote.setCommand(turnOnLight);
        remote.pressPattren(); // Output: Light is turned ON

        remote.setCommand(turnOffLight);
        remote.pressPattren(); // Output: Light is OFF
    }
}


// +------------------+         +---------------------+
// |     Command      |<--------|    RemoteControl    |
// |------------------|         |---------------------|
// | + execute()      |         | - command: Command  |
// +------------------+         |---------------------|
//        / \                   | + setCommand(c: Command) |
//       /   \                  | + pressButton()     |
//      /     \                 +---------------------+
// +------------------+              |
// | TurnOnLightCommand |              |
// |------------------|              |
// | - light: Light   |              |
// |------------------|              |
// | + execute()      |              |
// +------------------+              |
//      /     \                      |
// +------------------+         +---------------------+
// | TurnOffLightCommand|     |    Light             |
// |------------------|         |---------------------|
// | - light: Light   |         | + turnOn()          |
// |------------------|         | + turnOff()         |
// | + execute()      |         +---------------------+
// +------------------+
//      /     \
// +------------------------+
// | TurnOnAirConditionerCommand|
// |------------------------|
// | - airConditioner: AirConditioner |
// |------------------------|
// | + execute()            |
// +------------------------+
//      /     \
// +------------------------+
// | TurnOffAirConditionerCommand|
// |------------------------|
// | - airConditioner: AirConditioner |
// |------------------------|
// | + execute()            |
// +------------------------+
//          |
//          |
//          v
// +---------------------+
// |    AirConditioner   |
// |---------------------|
// | + turnOn()          |
// | + turnOff()         |
// +---------------------+
