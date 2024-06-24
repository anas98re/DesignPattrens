interface Prototype {
    Prototype clone();
}

//Circle class implementation
class Circle implements Prototype{
    private int redis;

    public Circle(int redis){
        this.redis = redis;
    }

    public void setRedis(int redis){
        this.redis = redis;
    }

    public int getRedis(){
        return redis;
    }

    @Override
    public Prototype clone(){
        return new Circle(redis);
    }

    public String toString(){
        return "Circle" + redis;
    }
    
}

//rectangle class implementation
class Rectangle implements Prototype{
    private int width, height;

    public Rectangle(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    @Override
    public Prototype clone(){
        return new Rectangle(width, height);
    }

    @Override
    public String toString(){
        return "Rectangle"+width+","+height;
    }
}


//Demo
public class PrototypeDemo{
    public static void main(String[] args){
        Circle OrginalCircle = new Circle(10);
        Circle CloneCircle = (clone) OrginalCircle.clone();
        CloneCircle.setRedis(20);

        System.out.println("Original Circle: " + originalCircle);
        System.out.println("Cloned Circle: " + clonedCircle);

        Rectangle originalCectangle = new Rectangle(5,10);
        Rectangle cloneRectangle = (clone) OrginalCircle.clone;
        cloneRectangle.setWidth(15);

        System.out.println("Original Rectangle: " + originalRectangle);
        System.out.println("Cloned Rectangle: " + clonedRectangle);
    }
}

// +----------------+           +------------------+
// |   Prototype    |           | ConcretePrototype|
// | <<interface>>  |<--------- |    Circle        |
// | + clone()      |           +------------------+
// +----------------+           | - radius: int    |
//                              | + clone(): Circle|
//                              +------------------+
//                              |                  |
//                              |   Rectangle      |
//                              | - width: int     |
//                              | - height: int    |
//                              | + clone():       |
//                              +------------------+
