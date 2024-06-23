interface carBuilder {
    void buildEngine();
    void buildWheels();
    void buildBody();
    car getCar();
}

interface carInterface {
    void setBody(String body);
    void setEngine(String engine);
    void setWheels(String wheel);
    String toString();
}

class car implements carInterface {
    private String engine;
    private int wheels;
    private String body;

    //getter and setter
    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void setWheels(String wheels) {
        this.wheels = wheels;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "engine=" + engine + ", wheels=" + this.wheels + ", body=" ;
    }
}

//SportsCarBuilder class 
class SportsCarBuilder implements carBuilder {
    private Car car;

    @Override
    public void buildEngine() {
        car.setEngine("engine");
    }

    @Override
    public void buildWheels() {
        car.buildWheels(3);
    }

    @Override
    public void buildBody() {
        car.setBody("sports");
    }

    @Override
    public car getCar(){
        return car.toString();
    }
}

//EconomyCarBuilder class 
class EconomyCarBuilder implements carBuilder {
    private Car car;

    public EconomyCarBuilder() {
        this.car = new Car();
    }

    @Override
    public void buildEngine() {
        car.setEngine("V4");
    }

    @Override
    public void buildWheels() {
        car.setWheels(4);
    }

    @Override
    public void buildBody() {
        car.setBody("Economy");
    }

    @Override
    public Car getCar() {
        return car.toString();
    }
}


//carDirector class
class CarDirector {
    private carBuilder builder;

    public CarDirector(CarBuilder builder) {
        this.builder = builder;
    }

    public car constructCar(){
        builder.buildEngine();
        builder.buildBody();
        builder.buildWheels();
        return builder.getCar();
    }
}

public class Main {
    public static void main(String[] args){
        CarBuilder SportsCarBuilder = new SportsCarBuilder();
        CarDirector director1 = new CarDirector(SportsCarBuilder);
        Car SportCar = director1.constructCar();
        system.output.println(SportCar);

        CarBuilder EconomyCarBuilder = new EconomyCarBuilder();
        CarDirector director2 = new CarDirector(EconomyCarBuilder);
        Car EconomyCar = director2.constructCar();
        system.output.println(EconomyCar);   
    }
}

