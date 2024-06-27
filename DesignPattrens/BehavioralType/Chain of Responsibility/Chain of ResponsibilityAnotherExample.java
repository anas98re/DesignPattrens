
import javax.xml.namespace.QName;

abstract class ReqestHandler {
    private  RequestHandler nextHandler ;

    public void setNextHandler(Request handler){
        this.nextRequest = handler;
    }

    public void HandlerRequest(UserRequest request){
        if(request != null){
            nextHandler.HandlerRequest(request);
        }
    }
}

class UserRequest {
    private String username;
    private String password;
    private String role;
    private boolean authenticated;
    private boolean authorized;
    private boolean valid;

    public UserRequest(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}

class AuthenticationHandler  extends ReqestHandler {
    @Override
    public void HandlerRequest(UserRequest request){
        if ("user".equals(request.getUsername()) && "password".equals(request.getPassword())) {
            request.setAuthenticated(true);
            System.out.println("User authenticated.");
        } else {
            request.setAuthenticated(false);
            System.out.println("User authenticated failed.");
        }
    }
    super.handleRequest(request);
} 


class AuthorizationHandler extends ReqestHandler{
    @Override
    public void HandlerRequest(UserRequest request){
        if (request.isAuthenticated() && "admin".equals(request.getRole())) {
            request.setAuthorized(true);
            System.out.println("User authorized.");
        } else {
            request.setAuthorized(false);
            System.out.println("User not authorized.");
        }
    }
    super.handleRequest(request);
}

class ValidationHandler extends ReqestHandler{
    @Override
    public void HandlerRequest(UserRequest request){
        if (request.isAuthenticated() && request.isAuthorized()) {
            request.setValid(true);
            System.out.println("Request is valid.");
        } else{
            request.setValid(false);
            System.out.println("Request is invalid.");
        }
    }
    super.handleRequest(request);
}   

public class ChainOfResponsibilityDemo {
    public static void main(String[] args) {
        ReqestHandler authenticationHandler  = new AuthenticationHandler();
        ReqestHandler authorizationHandler   = new AuthorizationHandler();
        ReqestHandler validationHandler   = new ValidationHandler();

        //String setting        
        authenticationHandler.setNextHandler(authorizationHandler);
        authorizationHandler.setNextHandler(validationHandler);

        //Reqest Handling
        UserRequest request = new UserRequest("user", "password", "admin");

        //Request processing
        authenticationHandler.HandlerRequest(request);

        //statuse Request Checking
        if (request.isValid()) {
            System.out.println("Request processed successfully.");
        } else {
            System.out.println("Request processing failed.");
        }

    }
}

// ┌──────────────────────────┐    ┌──────────────────────────┐    ┌──────────────────────────┐    ┌──────────────────────────┐
// │       Client             │    │   AuthenticationHandler  │    │   AuthorizationHandler   │    │   ValidationHandler       │
// └──────────┬───────────────┘    └──────────┬───────────────┘    └──────────┬───────────────┘    └──────────┬───────────────┘
//            │                                │                            │                            │
//            │ handleRequest(request)         │                            │                            │
//            ─────────────►                   │                            │                            │
//            │                                │ handleRequest(request)     │                            │
//            │                                ─────────────►               │                            │
//            │                                │                            │                            │
//            │                                │ User authenticated         │                            │
//            │                                │                            │ handleRequest(request)     │
//            │                                │                            ─────────────►               │
//            │                                │                            │                            │
//            │                                │                            │ User authorized            │ handleRequest(request)
//            │                                │                            │                            ─────────────►
//            │                                │                            │                            │
//            │                                │                            │                            │ Request is valid
//            │                                │                            │                            │
//            │                                │                            │                            │
//            │                                │                            │                            │
//            │                                │                            │                            │
//            │                                │                            │                            │
//            │                                │                            │                            │
//            │ handleRequest(request) ◄───────┘                            │                            │
//            │ handleRequest(request) ◄────────────────────────────────────┘                            │
//            │ handleRequest(request) ◄─────────────────────────────────────────────────────────────────┘
//            │                                │                            │                            │
//            │                                │                            │                            │
//            │                                │                            │                            │
//            │ Request processed successfully │                            │                            │
//            ◄────────────────────────────────┘                            │                            │
//            │                                │                            │                            │
//            └────────────────────────────────┘                            │                            │
//                                                                           │                            │
//                                                                           └────────────────────────────┘




//                             +----------------------------+
//                             |        Handler             |
//                             +----------------------------+
//                             | -nextHandler: Handler      |
//                             +----------------------------+
//                             | +setNext(handler: Handler): Handler |
//                             | +handleRequest(request: UserRequest): void |
//                             +----------------------------+
//                                       |
//                                       |
//                      +----------------+----------------+
//                      |                                 |
//    +-------------------------+            +-------------------------+
//    | AuthenticationHandler   |            | AuthorizationHandler    |
//    +-------------------------+            +-------------------------+
//    | +handleRequest(request: UserRequest): void | +handleRequest(request: UserRequest): void |
//    +-------------------------+            +-------------------------+
//                      |
//                      |
//    +-------------------------+
//    | ValidationHandler       |
//    +-------------------------+
//    | +handleRequest(request: UserRequest): void |
//    +-------------------------+
