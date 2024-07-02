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
abstract class ApplicationFactory {
    abstract Document createDocument();

    void newDocument(){
        Document doc = createDocument();
        doc.open();
    }
}

//TextApplication class
class TextApplicationFactroy extends ApplicationFactory {
    @Override
    Document createDocument(){
        return new TextDocument();
    }
}

//SpreadsheetApplication class
class SpreadsheetApplicationFactory extends ApplicationFactory{
    @Override
    Document createDocument(){
        return new SpreadsheetDocument();
    }
}

//Main class
public class Main{
    public static void main(String[] args){
        ApplicationFactory App = new TextApplicationFactroy();
        app.newDocument();

        ApplicationFactory AppSpreadsheet = new SpreadsheetApplicationFactory();
        AppSpreadsheet.newDocument();
    }
}
