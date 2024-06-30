// The State design pattern is a behavioral pattern that allows you to 
// change the behavior of an object when its internal state changes. An 
// object can appear as if it is changing its class.

// the explanation:
// State pattern divides behavior logic into separate classes. Instead 
// of using conditional instructions to switch between different states, 
// we abstract each state into a separate class and switch the state via a state object.

// Example:
// Let's take the example of a cash machine (ATM) which has different statuses 
// such as "ready to use", "card inserted", "PIN entered", and "cash withdrawn".

// State interface
interface ATMState {
    void insertCard(ATMMachine atmMachine);
    void ejectCard(ATMMachine atmMachine);
    void insertPin(ATMMachine atmMachine, int pin);
    void requestCash(ATMMachine atmMachine, int amount);
}

// Concrete states
class NoCardState implements ATMState {
    public void insertCard(ATMMachine atmMachine) {
        System.out.println("Card inserted.");
        atmMachine.setATMState(new HasCardState());
    }

    public void ejectCard(ATMMachine atmMachine) {
        System.out.println("No card to eject.");
    }

    public void insertPin(ATMMachine atmMachine, int pin) {
        System.out.println("No card inserted.");
    }

    public void requestCash(ATMMachine atmMachine, int amount) {
        System.out.println("No card inserted.");
    }
}

class HasCardState implements ATMState {
    public void insertCard(ATMMachine atmMachine) {
        System.out.println("Card already inserted.");
    }

    public void ejectCard(ATMMachine atmMachine) {
        System.out.println("Card ejected.");
        atmMachine.setATMState(new NoCardState());
    }

    public void insertPin(ATMMachine atmMachine, int pin) {
        if (pin == 1234) {
            System.out.println("Correct PIN.");
            atmMachine.setATMState(new CorrectPinState());
        } else {
            System.out.println("Incorrect PIN.");
            atmMachine.setATMState(new NoCardState());
        }
    }

    public void requestCash(ATMMachine atmMachine, int amount) {
        System.out.println("Enter PIN first.");
    }
}

class CorrectPinState implements ATMState {
    public void insertCard(ATMMachine atmMachine) {
        System.out.println("Card already inserted.");
    }

    public void ejectCard(ATMMachine atmMachine) {
        System.out.println("Card ejected.");
        atmMachine.setATMState(new NoCardState());
    }

    public void insertPin(ATMMachine atmMachine, int pin) {
        System.out.println("PIN already entered.");
    }

    public void requestCash(ATMMachine atmMachine, int amount) {
        if (amount <= atmMachine.getCashInMachine()) {
            System.out.println("Cash dispensed: " + amount);
            atmMachine.setCashInMachine(atmMachine.getCashInMachine() - amount);
            if (atmMachine.getCashInMachine() <= 0) {
                atmMachine.setATMState(new OutOfCashState());
            }
        } else {
            System.out.println("Insufficient funds.");
        }
    }
}

class OutOfCashState implements ATMState {
    public void insertCard(ATMMachine atmMachine) {
        System.out.println("No cash available.");
    }

    public void ejectCard(ATMMachine atmMachine) {
        System.out.println("No cash available.");
    }

    public void insertPin(ATMMachine atmMachine, int pin) {
        System.out.println("No cash available.");
    }

    public void requestCash(ATMMachine atmMachine, int amount) {
        System.out.println("No cash available.");
    }
}

// Context class
class ATMMachine {
    private ATMState currentState;
    private int cashInMachine;

    public ATMMachine(int initialCash) {
        currentState = new NoCardState();
        cashInMachine = initialCash;
    }

    public void setATMState(ATMState newState) {
        currentState = newState;
    }

    public int getCashInMachine() {
        return cashInMachine;
    }

    public void setCashInMachine(int amount) {
        cashInMachine = amount;
    }

    public void insertCard() {
        currentState.insertCard(this);
    }

    public void ejectCard() {
        currentState.ejectCard(this);
    }

    public void insertPin(int pin) {
        currentState.insertPin(this, pin);
    }

    public void requestCash(int amount) {
        currentState.requestCash(this, amount);
    }
}

// Client
public class Main {
    public static void main(String[] args) {
        ATMMachine atmMachine = new ATMMachine(1000);

        atmMachine.insertCard();
        atmMachine.insertPin(1234);
        atmMachine.requestCash(500);
        atmMachine.ejectCard();

        atmMachine.insertCard();
        atmMachine.insertPin(1234);
        atmMachine.requestCash(600);
        atmMachine.ejectCard();
    }
}

// +---------------------+      +---------------------+
// |      Context        |<>----|       State         |
// |---------------------|      +---------------------+
// | - state: State      |      | + insertCard()      |
// | + setState(s: State)|      | + ejectCard()       |
// | + insertCard()      |      | + insertPin()       |
// | + ejectCard()       |      | + requestCash()     |
// | + insertPin()       |      +---------------------+
// | + requestCash()     |
// +---------------------+
//         ^
//         |
//         |     
// +---------------------+     +---------------------+     +---------------------+     +---------------------+
// |    NoCardState      |     |   HasCardState      |     | CorrectPinState     |     |   OutOfCashState    |
// +---------------------+     +---------------------+     +---------------------+     +---------------------+
// | + insertCard()      |     | + insertCard()      |     | + insertCard()      |     | + insertCard()      |
// | + ejectCard()       |     | + ejectCard()       |     | + ejectCard()       |     | + ejectCard()       |
// | + insertPin()       |     | + insertPin()       |     | + insertPin()       |     | + insertPin()       |
// | + requestCash()     |     | + requestCash()     |     | + requestCash()     |     | + requestCash()     |
// +---------------------+     +---------------------+     +---------------------+     +---------------------+




