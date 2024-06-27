
//Chain of Responsibility example
public abstract class Handler {
    private Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void HandlerRequest(String request){
        if(nextHandler != null){
            nextHandler.HandlerRequest(request);
        }
    }
}

//ConcreteHandler
public class ConcreteHandler1 extends Handler{
    @Override
    public void HandlerRequest(String request){
        if(request.equals("Request1")){
            System.out.println("ConcreteHandler1 is handling the request: " + request);
        } else {
            super.HandlerRequest(request);
        }
    }
}

//ConcreteHandler2
public class ConcreteHandler2 extends Handler{
    @Override
    public void HandlerRequest(String request){
        if(request.equals("Request2")){
            System.out.println("ConcreteHandler2 is handling the request: " + request);
        } else {
            super.HandlerRequest(request);
        }
    }
}

//ConcreteHandler3
public class ConcreteHandler3 extends Handler{
    @Override
    public void HandlerRequest(String request){
        if(request.equals("Request3")){
            System.out.println("ConcreteHandler3 is handling the request: " + request);
        } else {
            super.HandlerRequest(request);
        }
    }
}

public class Client {
    public static void main(String[] args) {
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();

        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);

        handler1.HandlerRequest("Request1");
        handler1.HandlerRequest("Request2");
        handler1.HandlerRequest("Request3");
        handler1.HandlerRequest("UnknownRequest");
    }
}



