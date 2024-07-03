public class Program {
    AllViruses = new hashMap();

    void Process(String code){
        List Viruses = AllViruses.values();
        for(int i=0; i<Viruses.size(); i++){
            virus v = Viruses.get(i);
            if(v.find(code)) {v.clean(code);}
        }
    }

    void load (String newVirusName){
        class.forName(newVirusName);    
    }
}

interface Virus{
    boolean find(String code);
    void clean(String code);
}

public class virus1 implements Virus{
    find(){};
    clean(){};

    static{AllViruses.add(new virus1());}
}





//ChatGPT Updated the code 
import java.util.HashMap;
import java.util.List;

public class Program {
    private HashMap<String, Virus> allViruses;

    public Program() {
        allViruses = new HashMap<>();
    }

    public void process(String code) {
        List<Virus> viruses = List.copyOf(allViruses.values());
        for (Virus virus : viruses) {
            if (virus.find(code)) {
                virus.clean(code);
            }
        }
    }

    public void load(String newVirusName) {
        try {
            Class<?> virusClass = Class.forName(newVirusName);
            Virus newVirus = (Virus) virusClass.getDeclaredConstructor().newInstance();
            allViruses.put(newVirusName, newVirus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

interface Virus {
    boolean find(String code);
    void clean(String code);
}

public class Virus1 implements Virus {
    public boolean find(String code) {
        // Implementation for virus1 find method
        return false;
    }

    public void clean(String code) {
        // Implementation for virus1 clean method
    }

    static {
        Program program = new Program();
        program.load("Virus1");
    }
}
