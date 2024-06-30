// In this example, the Strategy pattern is used to implement the various payment methods 
// in an e-commerce application. After selecting a product to purchase, a customer picks 
// a payment method: either Paypal or credit card.

// Concrete strategies not only perform the actual payment but also alter the behavior 
// of the checkout form, providing appropriate fields to record payment details.

// PaymentStrategy Interface
public interface PaymentStrategy {
    void pay(double amount);
    void renderPaymentForm();
}

// CreditCardPayment Concrete Strategy
public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cvv;

    public CreditCardPayment(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card.");
    }

    @Override
    public void renderPaymentForm() {
        System.out.println("Rendering Credit Card Payment Form with fields: Card Number, CVV.");
    }
}

// PayPalPayment Concrete Strategy
public class PayPalPayment implements PaymentStrategy {
    private String email;
    private String password;

    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using PayPal.");
    }

    @Override
    public void renderPaymentForm() {
        System.out.println("Rendering PayPal Payment Form with fields: Email, Password.");
    }
}


// Context
public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void executePayment(double amount) {
        paymentStrategy.pay(amount);
    }

    public void renderForm() {
        paymentStrategy.renderPaymentForm();
    }
}


// Demo
public class ECommercePaymentDemo {
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext();

        // Render and pay using Credit Card
        context.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456", "123"));
        context.renderForm();
        context.executePayment(100.0);

        // Render and pay using PayPal
        context.setPaymentStrategy(new PayPalPayment("user@example.com", "password"));
        context.renderForm();
        context.executePayment(200.0);
    }
}

// +------------------------+
// |      PaymentStrategy   |
// |------------------------|
// | + pay(amount: double): void |
// | + renderPaymentForm(): void |
// +------------------------+
//           ^
//           |
// +---------+---------+          +---------+---------+
// | CreditCardPayment |          |   PayPalPayment   |
// +-------------------+          +-------------------+
// | - cardNumber: String |       | - email: String   |
// | - cvv: String       |        | - password: String|
// |-------------------|          |-------------------|
// | + pay(amount: double): void | | + pay(amount: double): void |
// | + renderPaymentForm(): void | | + renderPaymentForm(): void |
// +-------------------+          +-------------------+
//           ^
//           |
// +--------------------------+
// |         Context          |
// |--------------------------|
// | - paymentStrategy: PaymentStrategy |
// |--------------------------|
// | + setPaymentStrategy(s: PaymentStrategy): void |
// | + executePayment(amount: double): void |
// | + renderForm(): void |
// +--------------------------+
