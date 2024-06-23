interface Document {
    void open();
    void close();
    void save();
}

//TextDocument class
class TextDocument implements Document {
    @Override
    public void open(){
        system.output.println("open");
    }

    @Override
    public void close(){
        system.output.println("close");
    }

    @Override
    public void save(){
        system.output.println("save");
    }
}

//SpreadsheetDocument class 
class SpreadsheetDocument implements Document {
    @Override
    public void open(){
        system.output.println("open spreadsheet ");
    }

    @Override
    public void close(){
        system.output.println("close spreadsheet ");
    }

    @Override
    public void save(){
        system.output.println("save spreadsheet ");
    }
}

//Application Abstract class
abstract class Application {
    abstract Document createDocument();

    void newDocument(){
        Document doc = createDocument();
        doc.open();
    }
}

//TextApplication class
class TextApplication extends Application {
    @Override
    Document createDocument(){
        return new TextDocument();
    }
}

//SpreadsheetApplication class
class SpreadsheetApplication extends Application{
    @Override
    Document createDocument(){
        return new SpreadsheetDocument();
    }
}

//Main class
public class Main{
    public static void main(String[] args){
        Application App = new TextApplication();
        app.newDocument();

        Application AppSpreadsheet = new SpreadsheetApplication();
        AppSpreadsheet.newDocument();
    }
}
