// In this example, we would want to export a set of geometric shapes into XML. 
// The catch is that we don’t want to change the code of shapes directly or at 
// least keep it to the minimum.

// In the end, the Visitor pattern establishes an infrastructure that allows us 
// to add any behaviors to the shapes hierarchy without changing the existing code 
// of those classes.

//Common shape interface

// This interface represents the geometric shape and contains functions to 
// move and draw the shape, in addition to the accept function that accepts 
// a visitor of type Visitor.
public interface Shape {
    void move(int x, int y);    
    void drow();
    String accept(Visitor visitor);
}


//A Dot
//This class represents a point on the screen and realizes the Shape interface.
public class Dot implements Shape {
    private int x;
    private int y;
    private int id;

    public Dot(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;`
    }

    @Override
    public void move(int x, int y) {
        // move shape
    }

    @Override
    public void draw() {
        // draw shape
    }

    @Override
    public void accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public int getId() {
        return id;
    }

    public void getX() {
        return x;
    }

    public void getY() {
        return y;
    }
}

//A circle
//This class represents a circle. It inherits from the Dot class and adds the radius property.
public class Circle extends Dot {
    private int radius;

    public Circle(int x, int y, int radius, int id) {
        super(x,,y,id);
        this.radius = radius;
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visitCircle(this);
    }

    public void getRadius(){
        return radius;
    }
}

//A rectangle
public class rectangle implements Shape {
    private int id;
    private int x;
    private int y;
    private int width;
    private int height;

    public Rectangle(int id, int x, int y, int width, int height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visitRectangle(this);
    }

    @Override
    public void move(int x, int y) {
        // move shape
    }

    @Override
    public void draw() {
        // draw shape
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

// A compound shape
//This class represents a complex shape that contains a group of other shapes.
public class CompoundShape implements Shape {
    public int id;
    public List<Shape> children = new ArrayList<>();

    public CompoundShape(int id) {
        that.id = id;
    }

    @Override
    public void move(int x, int y) {
        // move shape
    }

    @Override
    public void draw() {
        // draw shape
    }

    public int getId() {
        return id;
    }

    public String accept(Visitor visitor) {
        return visitor.visitCompoundGraphic(this);
    }

    public void add(Shape shape){
        children.add(shape);
    }
}

//Common visitor interface
//The visitor interface contains visit functions for each type of form.
public interface Visitor{
    String visitDot(Dot dot);
    String visitCircle(Circle circle);
    String visitRectangle(Rectangle rectangle);
    String visitCompoundGraphic(CompoundShape cg);
}

// Concrete visitor, exports all shapes into XML
//This class implements the Visitor interface and exports shapes to XML format.
public class XMLExportVisitor implements Visitor{
    public String export(Shape... args) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");
        for (Shape shape : args) {
            sb.append(shape.accept(this)).append("\n");
        }
        return sb.toString();
    }

    public String visitDot(Dot d) {
        return "<dot>" + "\n" +
                "    <id>" + d.getId() + "</id>" + "\n" +
                "    <x>" + d.getX() + "</x>" + "\n" +
                "    <y>" + d.getY() + "</y>" + "\n" +
                "</dot>";
    }

    public String visitCircle(Circle c) {
        return "<circle>" + "\n" +
                "    <id>" + c.getId() + "</id>" + "\n" +
                "    <x>" + c.getX() + "</x>" + "\n" +
                "    <y>" + c.getY() + "</y>" + "\n" +
                "    <radius>" + c.getRadius() + "</radius>" + "\n" +
                "</circle>";
    }

    public String visitRectangle(Rectangle r) {
        return "<rectangle>" + "\n" +
                "    <id>" + r.getId() + "</id>" + "\n" +
                "    <x>" + r.getX() + "</x>" + "\n" +
                "    <y>" + r.getY() + "</y>" + "\n" +
                "    <width>" + r.getWidth() + "</width>" + "\n" +
                "    <height>" + r.getHeight() + "</height>" + "\n" +
                "</rectangle>";
    }

    public String visitCompoundGraphic(CompoundShape cg) {
        return "<compound_graphic>" + "\n" +
                "   <id>" + cg.getId() + "</id>" + "\n" +
                _visitCompoundGraphic(cg) +
                "</compound_graphic>";
    }

    private String _visitCompoundGraphic(CompoundShape cg) {
        StringBuilder sb = new StringBuilder();
        for (Shape shape : cg.children) {
            String obj = shape.accept(this);
            // Proper indentation for sub-objects.
            obj = "    " + obj.replace("\n", "\n    ") + "\n";
            sb.append(obj);
        }
        return sb.toString();
    }

}


//Demo 
public class Demo {
    public static void main(String[] args) {
        Dot dot = new Dot(1, 10, 55);
        Circle circle = new Circle(2, 23, 15, 10);
        Rectangle rectangle = new Rectangle(3, 10, 17, 20, 30);

        CompoundShape compoundShape = new CompoundShape(4);
        compoundShape.add(dot);
        compoundShape.add(circle);
        compoundShape.add(rectangle);

        CompoundShape c = new CompoundShape(5);
        c.add(dot);
        compoundShape.add(c);

        export(circle, compoundShape);
    }

    private static void export(Shape... shapes) {
        XMLExportVisitor exportVisitor = new XMLExportVisitor();
        System.out.println(exportVisitor.export(shapes));
    }
}

//                                       +-----------------+
//                                       |     Visitor     |
//                                       +-----------------+
//                                       | + visitDot()    |
//                                       | + visitCircle() |
//                                       | + visitRect()   |
//                                       | + visitComp()   |
//                                       +--------+--------+
//                                                |
//               +--------------------------------+-------------------+
//               |                                |                   |
// +-------------+------------+     +-------------+------------+      |
// |    XMLExportVisitor      |     |    JSONExportVisitor     |      |
// | (ConcreteVisitor)        |     | (ConcreteVisitor)        |      |
// +--------------------------+     +--------------------------+      |
// | + visitDot()             |     | + visitDot()             |      |
// | + visitCircle()          |     | + visitCircle()          |      |
// | + visitRectangle()       |     | + visitRectangle()       |      |
// | + visitCompoundGraphic() |     | + visitCompoundGraphic() |      |
// +-------------+------------+     +-------------+------------+      |
//               |                                |                   |
//               +----^---------------------------^-------------------+
//                    |
//                    |
//              +-----+------+
//              |   Shape    |
//              +-----+------+
//                    |
//     +--------------+--------------+
//     |              |              |
//     |              |              |
// +---+---+      +---+---+      +---+---+
// |  Dot  |      | Circle|      | Rect  |
// |       |      |       |      |       |
// +-------+      +-------+      +-------+
