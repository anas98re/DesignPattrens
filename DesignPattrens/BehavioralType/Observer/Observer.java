// Let's create a simple example of a weather monitoring system where we notify 
// observers (such as weather apps) when the weather changes.

interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers();
}

interface Observer {
    void update(float temperature, float humidity, float pressure);
}

//ConcreteSubject
class WeatherData implements Subject{
    private List<Observer> observers ;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData(){
        observers  = new ArrayList<>();
    }

    @Override
    public void attach(Observer o){
        observers.add(o);
    }

    @Override
    public void detach(Observer o){
        observers.remove(o);
    }

    @Override
    public void notifyObservers(){
        for(Observer observer : observers){
            observer.update(temperature, humidity, pressure);
        }
    }

    public void setMeasurements(float temperature, float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public void measurementsChanged(){
        notifyObservers();
    }
}

class CurrentConditionsDisplay implements Observer {
    private float temperature;
    private float humidity;

    @Override
    public void update(float temperature, float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    public void display() {
        System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + "% humidity");
    }
}


public class WeatherStation {
    public class void main(String[] args){
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();

        weatherData.attach(currentDisplay);

        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
    }
}

// +----------------+        +-----------------+
// |    Subject     |        |    Observer     |
// +----------------+        +-----------------+
// |+ attach()      |        |+ update()       |
// |+ detach()      |        +-----------------+
// |+ notify()      |                ^
// +----------------+                |
//          ^                        |
//          |                        |
//          |                        |
// +----------------+        +-----------------+
// |ConcreteSubject |        | ConcreteObserver|
// +----------------+        +-----------------+
// | - state        |        | - state         |
// |+ getState()    |        |+ update()       |
// |+ setState()    |        +-----------------+
// +----------------+
