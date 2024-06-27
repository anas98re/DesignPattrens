// The appropriate design model is:  Chain of Responsibility

// To solve the problem of dynamically filtering emails according to 
// changing criteria, the “Chain of Responsibility” design pattern can 
// be used. This pattern allows the construction of a chain of shapes (handlers) 
// through which requests are passed, and the requests are processed by the appropriate 
// shape within the chain.

// Interface for email filter handlers
interface EmailFilter {
    void filterEmai(Email email);
}

// Concrete handler that filters based on keywords in email body
class keywordFilter implements EmailFilter {
    private List<String> keywords;

    public keywordFilter(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public void filterEmai(Email email) {
        // Check if email body contains any of the keywords
        boolean containsKeyword = keywords.stream()
            .anyMatch(keyword->email.getBody().contains(keyword));

        if(containsKeyword) {
            System.out.println("Email filtered by SubjectFilter: " + email.getSubject());
        }    
    }
}

// Concrete handler that filters based on keywords in email subject
class subjectFilter implements EmailFilter {
    private List<String> keywords;

    public subjectFilter(List<String keywords){
        this.keywords = keywords;
    }

    @Override
    public void filterEmai(Email email) {
        // Check if email subject contains any of the keywords
        boolean containsKeyword = keywords.stream()
            .anyMatch(keyword -> email.getSubject().contains(keyword));

        if (containsKeyword) {
            System.out.println("Email filtered by SubjectFilter: " + email.getSubject());
        }    
    }
}

// Concrete handler that filters based on known senders
class SenderFilter implements EmailFilter{
    private List<String> knownSenders;

    @Override
    public void filterEmai(Email email) {
        // Check if email sender is in the list of known senders
        if (knownSenders.contains(email.getSender())) {
            System.out.println("Email filtered by SenderFilter: " + email.getSender());
        }
    }
}

// Email class representing the email message
class Email {
    private String sender;
    private String subject;
    private String body;

    public Email(String sender, String subject, String body){
        this.sender = sender;
        this.subject = subject;
        this.body = body;
    }

    public String getSender() { return sender; }
    public String getSubject() { return subject; }
    public String getBody() { return body; }

}

// Client code that uses the chain of responsibility pattern to filter emails
public class EmailFilteringSystem {
    private EmailFilter filterChain;

    public EmailFilteringSystem() {
        // Configure the chain of filters dynamically
        List<String> keywords = List.of("urgent", "important");
        List<String> knownSenders = List.of("friend@example.com", "boss@example.com");

        // Create the chain of responsibility
        filterChain = new keywordFilter();
        filterChain = addFilter(filterChain, new subjectFilter(keywords));
        filterChain = addFilter(filterChain, new SenderFilter(knownSenders));
    }

    private EmailFilter addFilter(EmailFilter currentFilter, EmailFilter newFilter) {
            // Chaining the filters
            return email -> {
                currentFilter.filterEmail(email);
                newFilter.filterEmail(email);
            };
    }

    public void filterEmail(Email email) {
        filterChain.filterEmai(email);
    }
    
    public static void main(String[] args) {
            EmailFilteringSystem filteringSystem = new EmailFilteringSystem();

            // Example emails to filter
            Email email1 = new Email("friend@example.com", "Invitation", "Hey, let's meet up!");
            Email email2 = new Email("unknown@example.com", "Urgent", "Action required!");

            filteringSystem.filterEmail(email1);
            filteringSystem.filterEmail(email2);    
    }   
}


// +--------------------------------------------------+
// |                    Client                        |
// +--------------------------------------------------+
//         |
//         | إعداد سلسلة من المعالجين للتصفية
//         |
// +--------------------------------------------------+
// |                 EmailMessage                     |
// |--------------------------------------------------|
// | - body: String                                   |
// | - subject: String                                |
// | - sender: String                                 |
// +--------------------------------------------------+
//         |
//         | تمرير رسالة البريد الإلكتروني عبر سلسلة المعالجين
//         |
// +--------------------------------------------------+
// |              EmailFilterHandler                  |
// |--------------------------------------------------|
// | - nextHandler: EmailFilterHandler                 |
// +--------------------------------------------------+
//         |                                           |
//         v                                           v
// +--------------------------------------------------+
// |             KeywordFilterHandler                 |
// |--------------------------------------------------|
// | - keywords: List<String>                         |
// +--------------------------------------------------+
//         |                                           |
//         v                                           v
// +--------------------------------------------------+
// |             SenderFilterHandler                  |
// |--------------------------------------------------|
// | - senders: List<String>                          |
// +--------------------------------------------------+
//         |                                           |
//         v                                           v
// +--------------------------------------------------+
// |              DeliveryHandler                    |
// +--------------------------------------------------+



