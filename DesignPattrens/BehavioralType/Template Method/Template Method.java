// In this example, the Template Method pattern provides a “skeleton” for various 
// branches of artificial intelligence in a simple strategy video game.

// All races in the game have almost the same types of units and buildings. Therefore you 
// can reuse the same AI structure for various races, while being able to override some of 
// the details. With this approach, you can override the orcs’ AI to make it more aggressive, 
// make humans more defense-oriented, and make monsters unable to build anything. Adding a new 
// race to the game would require creating a new AI subclass and overriding the default methods 
// declared in the base AI class.



//IMPORTANT
// GameAI base class: Contains the turn() method that defines the general 
// structure of the AI ​​algorithm.
// The abstract method attack(): forces subclasses to provide their own 
// implementation of this method.
// Subclasses (OrcsAI, HumansAI, MonstersAI): Each class provides its 
// own implementation of the attack() method, as well as overriding constructor 
// and module methods if necessary.
// Using this structure, the overall structure of the AI ​​algorithm can be reused 
// with some steps allocated to subclasses, allowing you to create custom behavior 
// for each class without changing the overall structure.


abstract class GameAI {
    public final void turn() {
        collectResources();
        buildStructures();
        buildUnits();
        attack();
    }

    void collectResources() {
        System.out.println("Collecting resources");
    }

    void buildStructures() {
        System.out.println("Building default structures");
    }

    void buildUnits() {
        System.out.println("Building default units");
    }

    abstract void attack();
}

class OrcsAI extends GameAI {
    @Override
    void buildStructures() {
        System.out.println("Building orc structures");
    }

    @Override
    void buildUnits() {
        System.out.println("Building orc units");
    }

    @Override
    void attack() {
        System.out.println("Orcs are attacking aggressively!");
    }
}


class HumansAI extends GameAI {
    @Override
    void buildStructures() {
        System.out.println("Building human structures");
    }

    @Override
    void buildUnits() {
        System.out.println("Building human units");
    }

    @Override
    void attack() {
        System.out.println("Humans are defending!");
    }
}

class MonstersAI extends GameAI {
    @Override
    void buildStructures() {
        // Monsters can't build anything
    }

    @Override
    void buildUnits() {
        System.out.println("Building monster units");
    }

    @Override
    void attack() {
        System.out.println("Monsters are attacking ferociously!");
    }
}

public class TemplateMethodDemo {
    public static void main(String[] args) {
        GameAI orcsAI = new OrcsAI();
        GameAI humansAI = new HumansAI();
        GameAI monstersAI = new MonstersAI();

        System.out.println("Orcs' turn:");
        orcsAI.turn();

        System.out.println("\nHumans' turn:");
        humansAI.turn();

        System.out.println("\nMonsters' turn:");
        monstersAI.turn();
    }
}


//   +-------------------+
//   |   GameAI          | <-----------------------------+
//   +-------------------+                                |
//   | - turn()          |                                |
//   | - collectResources()                               |
//   | - buildStructures()                                |
//   | - buildUnits()                                     |
//   | # attack() <<abstract>>                            |
//   +-------------------+                                |
//             ^                                         |
//             |                                         |
//  +----------+----------+         +--------------------+---------------------+
//  |       OrcsAI       |         |      HumansAI                          |
//  +--------------------+         +-------------------+                      |
//  | + attack()         |         | + attack()        |                      |
//  | + buildStructures()|         | + buildStructures()|                      |
//  | + buildUnits()     |         | + buildUnits()    |                      |
//  +--------------------+         +-------------------+                      |
//                                                                    +----------+------------+
//                                                                    |        MonstersAI      |
//                                                                    +------------------------+
//                                                                    | + attack()             |
//                                                                    | + buildStructures()    |
//                                                                    | + buildUnits()         |
//                                                                    +------------------------+
