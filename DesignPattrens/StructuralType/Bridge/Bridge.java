//Example:

// Bridge between devices and remote controls
// This example shows separation between the classes
// of remotes and devices that they control.

// Remotes act as abstractions, and devices are their
// implementations. Thanks to the common interfaces,
// the same remotes can work with different devices and vice versa.

// The Bridge pattern allows changing or even creating 
// new classes without touching the code of the opposite hierarchy.

public interface Device {
    boolean isEnabled();

    void enable();

    void disable();

    int getVolume();

    void setVolume(int percent);

    int getChannel();

    void setChannel(int channel);

    void printStatus();
}

//Radio class implementation
public class Radio implements Device {
    private boolean on = false;
    private int volume = 30;
    private int channel = 1;

    @Override
    public boolean isEnabled() {
        return on;
    }

    @Override
    public void enable() {
        return on = true;
    }

    @Override
    public void disable(){
        return on = false;
    }

    @Override
    public int getVolume(){
        return volume;
    }

    @Override
    public void setVolume(int volume) {
        if (volume > 100) {
            this.volume = 100;
        } else if (volume < 0) {
            this.volume = 0;
        } else {
            this.volume = volume;
        }
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public void setChannel(int channel) {
        this.channel = channel;
    }

    @Override
    public void printStatus() {
        System.out.println("------------------------------------");
        System.out.println("| I'm radio.");
        System.out.println("| I'm " + (on ? "enabled" : "disabled"));
        System.out.println("| Current volume is " + volume + "%");
        System.out.println("| Current channel is " + channel);
        System.out.println("------------------------------------\n");
    }
}

//Tv class implementation
public class Tv implements Device {
    private boolean on = false;
    private int volume = 30;
    private int channel = 1;

    @Override
    public boolean isEnabled() {
        return on;
    }

    @Override
    public void enable() {
        on = true;
    }

    @Override
    public void disable() {
        on = false;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int volume) {
        if (volume > 100) {
            this.volume = 100;
        } else if (volume < 0) {
            this.volume = 0;
        } else {
            this.volume = volume;
        }
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public void setChannel(int channel) {
        this.channel = channel;
    }

    @Override
    public void printStatus() {
        System.out.println("------------------------------------");
        System.out.println("| I'm TV set.");
        System.out.println("| I'm " + (on ? "enabled" : "disabled"));
        System.out.println("| Current volume is " + volume + "%");
        System.out.println("| Current channel is " + channel);
        System.out.println("------------------------------------\n");
    }
}

//remote interface
public interface Remote {
    void power();

    void volumeDown();

    void volumeUp();

    void channelDown();

    void channelUp();
}

//BasicRemote class
public class BasicRemote implements Remote {
    protected Device device;

    public BasicRemote(Device device) {
        this.device = device;
    }

    @Override
    public void power() {
        System.out.println("Remote: power toggle");
        if (device.isEnabled()) {
            device.disable();
        } else {
            device.enable();
        }
    }

    @Override
    public void volumeDown() {
        System.out.println("Remote: volume down");
        device.setVolume(device.getVolume() - 10);
    }

    @Override 
    public void volumeUp(){
        System.out.println("Remote: volume up");
        device.setVolume(device.getVolume() + 10);
    }

    @Override
    public void channelDown() {
        System.out.println("Remote: channel down");
        device.setChannel(device.getChannel() - 1);
    }

    @Override
    public void channelUp() {
        System.out.println("Remote: channel up");
        device.setChannel(device.getChannel() + 1);
    }
}

//this class to add other services
//AdvancedRemote class to add other services
public class AdvancedRemote extends BasicRemote {
    public AdvancedRemote(Device device) {
        super.device = device;
    }

    public void mute(){
        System.out.println("Remote: mute");
        device.setVolume(0);
    }
}

//Demo Main Class
public class Demo{
    public static void main(String[] args) {

    }

    public static void testDevice(Device device){
        System.out.println("Tests with basic remote.");
        BasicRemote basicRemote = new BasicRemote(device);
        basicRemote.power();
        device.printStatus();

        System.out.println("Tests with advanced remote.");
        AdvancedRemote advancedRemote = new AdvancedRemote(device);
        advancedRemote.power();
        advancedRemote.mute();
        device.printStatus();
    }
}

//             +----------------------+
//             |      Device          |
//             +----------------------+
//             | + isEnabled(): bool  |
//             | + enable(): void     |
//             | + disable(): void    |
//             | + getVolume(): int   |
//             | + setVolume(int): void|
//             | + getChannel(): int  |
//             | + setChannel(int): void|
//             | + printStatus(): void|
//             +----------------------+
//                        ^
//                        |implementation
//          +-------------+-------------+
//          |                           |
// +-------------------+       +-------------------+
// |       Tv          |       |      Radio        |
// +-------------------+       +-------------------+
// | - on: bool        |       | - on: bool        |
// | - volume: int     |       | - volume: int     |
// | - channel: int    |       | - channel: int    |
// +-------------------+       +-------------------+
// | + isEnabled()     |       | + isEnabled()     |
// | + enable()        |       | + enable()        |
// | + disable()       |       | + disable()       |
// | + getVolume()     |       | + getVolume()     |
// | + setVolume()     |       | + setVolume()     |
// | + getChannel()    |       | + getChannel()    |
// | + setChannel()    |       | + setChannel()    |
// | + printStatus()   |       | + printStatus()   |
// +-------------------+       +-------------------+
//                        ^
//                        |
//          +-------------+-------------+
//          |                           |
// +-------------------+        +-------------------+
// |   BasicRemote     |        |   AdvancedRemote  |
// +-------------------+        +-------------------+
// | - device: Device  |        | - device: Device  |
// +-------------------+ inherts+-------------------+
// | + power()         |<-------| + mute()          |
// | + volumeUp()      |        +-------------------+
// | + volumeDown()    |        | + power()         |
// | + channelUp()     |        | + volumeUp()      |
// | + channelDown()   |        | + volumeDown()    |
// +-------------------+        | + channelUp()     |
//                              | + channelDown()   |
//                              +-------------------+
