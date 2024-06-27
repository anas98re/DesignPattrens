
import com.sun.source.tree.Tree;

// The Flyweight design model is one of the structural design models in object-oriented 
// programming, aiming to reduce memory usage and improve performance by sharing similar objects.

// Flyweight concept
// In applications that deal with a large number of small, similar objects, Flyweight can 
// be used to reduce memory load by sharing objects rather than creating a new object for 
// each request. The object state is divided into two states:

// Intrinsic State: Constant and shared by many objects.
// Extrinsic State: Variable and unique to each object.

// Practical example
// Let's say we're building an application to display trees in a forest. Each tree 
// has characteristics such as type and color, but the 
// position of the tree varies. Flyweight can be used to share common properties between trees.


// How the model works
// Factory: Used to provide shared TreeType objects.
// Tree: The TreeType object is used to draw the tree with the specified coordinates.
// Client: Creates Tree objects and tells them to map themselves using shared TreeType objects.
// Using Flyweight, we can reduce the number of objects used and significantly 
// improve memory usage in applications that need to create a large number of similar objects.

interface TreeType {
    void draw(int x, int y);
}

// Concrete Flyweight class
// ConcreteTreeType: A physical object that implements the TreeType
//  interface, containing the internal state (name, color, texture).
class ConcreteTreeType implements TreeType {
    private String name;
    private String color;
    private String texture;

    public ConcreteTreeType(String name, String color, String texture) {
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    @Override
    public void draw(int x, int y){
        System.out.println("Drawing a " + name + " tree of color " + color + " at (" + x + ", " + y + ")");
    }
}

// Flyweight Factory
// TreeTypeFactory: A factory that provides shared TreeType 
// objects, uses a map to store created and reused objects.
class TreeTypeFactory { 
    private static final Map<String , TreeType> treeTypes = new HashMap<>();

    public static TreeType  getTreeType(String name, String color, String texture){
        String key = name + "-" + color + "-" + texture;
        TreeType result = treeTypes.get(key);
        if(result == null){
            result = new ConcreteTreeType(name, color, texture); 
            treeTypes.put(key, result);
        }
        return result;
    }
}

//Tree: The object that contains the external state (coordinates) and uses TreeType for drawing.
class Tree2 {
    private int x;
    private int y;
    private TreeType type;

    public Tree2(int x, int y, TreeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw() {
        type.draw(x, y);
    }
}

// Client code
class FlyweightPatternDemo {
    public static void main(String[] args) {
        TreeType oak = TreeTypeFactory.getTreeType("Oak", "Green", "Rough");
        TreeType pine = TreeTypeFactory.getTreeType("Pine", "Green", "Smooth");

        Tree2 tree1 = new Tree2(10, 20, oak);
        Tree2 tree2 = new Tree2(30, 40, pine);
        Tree2 tree3 = new Tree2(50, 60, oak);
        
        tree1.draw();
        tree2.draw();
        tree3.draw();
    }
}

// +--------------------------+            +-------------------------+
// |      TreeTypeFactory     |<---------+ |       TreeType          |
// +--------------------------+          | +-------------------------+
// | - treeTypes: Map<String, TreeType>  | | - name: String          |
// +--------------------------+          | | - color: String         |
// | + getTreeType(name, color, texture) | | - texture: String       |
// +--------------------------+          | +-------------------------+
//              ^                         | | + draw(x, y): void      |
//              |                         | +-------------------------+
//              |                         |
//              |                         |
// +------------+------------+      +-------------+
// |     ConcreteTreeType    |      |    Tree     |
// +-------------------------+      +-------------+
// | - name: String          |      | - x: int    |
// | - color: String         |      | - y: int    |
// | - texture: String       |      | - type: TreeType  |
// +-------------------------+      +-------------+
// | + draw(x, y): void      |      | + draw(): void     |
// +-------------------------+      +-------------+
