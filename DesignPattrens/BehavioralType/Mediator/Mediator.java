// We can use Mediator Pattern to manage different window interactions in the Graphical User 
// Interface (GUI). Instead of windows communicating directly with each other, they can 
// communicate via an intermediary object.

public interface Mediator {
    void notify(Component sender , String event);
}

public class ConcreteMediator implements Mediator {
    private Button button;
    private TextBox textBox;
    private ListBox listBox;

    public void setButton(Button button) {
        this.button = button;
    }

    public void setTextBox(TextBox textBox) {
        this.textBox = textBox;
    }

    public void setListBox(ListBox listBox) {
        this.listBox = listBox;
    }

    @Override
    public void notify(Component sender, String event) {
        if(sender == button && event.equals("click")) {
            textBox.setText("Button clicked!");
            listBox.update("Button clicked event");
        } else if(sender == textBox && event.equals("textChanged")) {
            listBox.update("Text changed: " + textBox.getText());
        }
    }
}

public abstract class Component {
    protected Mediator mediator;

    public Component(Mediator mediator) {
        this.mediator = mediator;
    }

    public void setMediator(Mediator mediator){
        this.mediator = mediator;
    }
}

public class Button extends Component {
    public Button(Mediator mediator){
        super(mediator);
    }

    public void click(){
        mediator.notify(this,click);
    }
}

public class TextBox extends Component {
    private String text;

    public TextBox(Mediator mediator){
        super(mediator);
    }

    public void setText(String text){
        this.text = text;
        mediator.notify(this, "textChanged");
    }

    public String getText() {
        return text;
    }
}

public class ListBox extends Component {
    public ListBox(Mediator mediator) {
        super(mediator);
    }

    public void update(String message) {
        System.out.println("ListBox updated with: " + message);
    }
}

public class Main {
    public static void main(String[] args) {
        ConcreteMediator mediator = new ConcreteMediator();

        Button button = new Button(mediator);
        TextBox textBox = new TextBox(mediator);
        ListBox listBox = new ListBox(mediator);

        mediator.setButton(button);
        mediator.setTextBox(textBox);
        mediator.setListBox(listBox);

        button.click();
        textBox.setText("New Text");
    }
}

// +-----------------+          +-----------------------+
// |    Mediator     |<---------|   ConcreteMediator    |
// |-----------------|          |-----------------------|
// | + notify(...)   |          | - button: Button      |
// +-----------------+          | - textBox: TextBox    |
//                              | - listBox: ListBox    |
//                              |-----------------------|
//                              | + notify(sender, event)|
//                              +-----------------------+
//                                       /|\
//                                        |
//                                        |
//                            +-----------------------+
//                            |      Component        |
//                            |-----------------------|
//                            | - mediator: Mediator  |
//                            |-----------------------|
//                            | + setMediator(mediator)|
//                            +-----------------------+
//                                       /|\
//                                        |
//         +----------------+  +-----------------+  +---------------+
//         |    Button      |  |    TextBox      |  |    ListBox    |
//         |----------------|  |-----------------|  |---------------|
//         | + click()      |  | + setText(text) |  | + update(msg) |
//         +----------------+  | + getText()     |  +---------------+
//                             +-----------------+

// Drawing explanation:
// 1-Mediator: An interface that defines a node for peer communication.
// 2-ConcreteMediator: The class that implements the mediator interface and 
//   -coordinates communications between objects.
//   -Contains references to participating objects (Button, TextBox, ListBox).
//   It contains a notify method that responds to events from colleagues.
// 3-Component: An interface or abstract class that defines the objects that interact with the medium.
//  It contains a reference to the mediator and the setMediator method to specify the mediator.
// 4-Button, TextBox, ListBox: Specific classes that implement the Component interface and handle 
// the mediator for communication.