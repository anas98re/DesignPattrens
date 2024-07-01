// Professional example: Editing texts with the ability to undo
// Example components:
// TextEditor (Originator): Represents a text editor that can save and restore its state.
// TextState (Memento): Represents the saved state of the text editor.
// History (Caretaker): Keeps a list of text states and allows undoing of them



//Memento Interface 
public interface Memento {

}

//Originator Interface 
public interface Originator {
    Memento save();
    void restore(Memento memento);
}

//Caretaker Interface 
public interface Caretaker {
    void save();
    void undo();
}

// TextState (Memento)
public class TextState implements Memento {
    private final String text;

    public TextState(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

//TextEditor (Originator implementation)
public class TextEditor implements Originator {
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void save(){
        return new TextState(text);
    }


    // if (memento instanceof TextState): Check if the memento object is an 
    // instance of the TextState class.
    // this.text = ((TextState) memento).getText();: If memento is an 
    // instance of TextState, it retrieves the text value from the memento 
    // object by calling getText() and saves it in the current text variable text
    @Override
    public void restore(Memento memento){
        if(memento instanceof TextState){
            this.text = ((TextState)memento).getText();
        }
    }

}

//History (Caretaker Implementation)
public class History implements Caretaker {
    private stack<Memento> history = new Stack<>();
    private final Originator originator ;

    public History(Originator originator){
        this.originator = originator
    }

    @Override
    public void save(){
        history.push(Originator.save());
    }

    @Override
    public void undo(){
        if(!history.isEmpty()){
            originator.restore(history.pop());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        History history = new History(editor);

        editor.setText("First Version");
        history.save();
        System.out.println("Text: " + editor.getText());

        editor.setText("Second Version");
        history.save();
        System.out.println("Text: " + editor.getText());

        editor.setText("Third Version");
        System.out.println("Text: " + editor.getText());

        history.undo();
        System.out.println("Undo: " + editor.getText());

        history.undo();
        System.out.println("Undo: " + editor.getText());
    }
}

// +------------------+                     +-------------------+
// |   Originator     |                     |     Memento       |
// |------------------|                     |-------------------|
// | + save(): Memento|                     |                   |
// | + restore(memento: Memento) |----------|                   |
// +------------------+                     +-------------------+
//         ^
//         |
// +------------------+
// |   TextEditor    |
// |-----------------|
// | - text: String  |
// |-----------------|
// | + setText(text) |
// | + getText()     |
// | + save(): Memento |
// | + restore(memento: Memento) |
// +------------------+
//         |
// +------------------+
// |   Caretaker     |
// |-----------------|
// | + save()        |
// | + undo()        |
// +-----------------+
//         ^
//         |
// +------------------+
// |     History     |
// |-----------------|
// | - history: Stack|
// | - originator: Originator |
// |-----------------|
// | + save()        |
// | + undo()        |
// +-----------------+