//Abstract Factory & Abstract Product
interface GUIFactory {
    Button createButton();
    Window createWindow();
}

interface Button {
    void click();
}

interface Window {
    void open();
}
//

//Concrete Factory & Concrete Product Implementation
class WindowsButton implements Button {
    @Override
    public void click() {
        system.output.println("Windows button clicked");
    }
}

class MacButton implements Button {
    @Override
    public void click() {
        system.output.println("Mac button clicked");
    }
}

class WindowsWindow implements Window {
    @Override
    public void open() {
        system.output.println("Windows open"); 
    }
}

class MacWindow implements Window {
    @Override
    public void open() {
        system.output.println("Mac open"); 
    }
}
//

//Concrete Factory
class WindowsFactory implements GUIFactory{
    @Override
    public void createButton(){
        return new WindowsButton();
    }

    @Override
    public void createWindow(){
        return new WindowsWindow();
    }
}

class MacFactory implements GUIFactory{
    @Override
    public void createButton(){
        return new MacButton();
    }

    @Override
    public void createWindow(){
        return new MacWindow();
    }
}
//

//Client
public class Application{
    private Button button;
    private Window window;

    public Application(GUIFactory factory){
        button = factory.createButton();
        window = factory.createWindow();
    }

    public void run(){
        button.click();
        window.open();
    }

    public static void main(String[] args){
        GUIFactory factory ;

        String osName = System.getProperty("os.name").toLowerCase();

        if(osName.contains("Windows")){
            factory = new WindowsFactory();
        } else {
            factory = new MacFactory();
        }

        Application App = new Application(factory);
        App.run();
    }
}
//
