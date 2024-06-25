<?php
// The Adapter pattern allows you to use 3rd-party or legacy classes even if they’re 
// incompatible with the bulk of your code. For example, instead of rewriting the 
// notification interface of your app to support each 3rd-party service such as Slack, 
// Facebook, SMS or (you-name-it), you can create a set of special wrappers that adapt 
// calls from your app to an interface and format required by each 3rd-party class. 

namespace RefactoringGuru\Adapter\RealWorld;

/**
 * The Target interface represents the interface that your application's classes
 * already follow.
 */

interface Notification
{
    public function send(String $title, String $message);
}

/**
 * Here's an example of the existing class that follows the Target interface.
 *
 * The truth is that many real apps may not have this interface clearly defined.
 * If you're in that boat, your best bet would be to extend the Adapter from one
 * of your application's existing classes. If that's awkward (for instance,
 * SlackNotification doesn't feel like a subclass of EmailNotification), then
 * extracting an interface should be your first step.
 */

class EmailNotification implements Notification
{
    private $adminEmail;

    public function __construct(String $adminEmail)
    {
        $this->adminEmail = $adminEmail;
    }

    //Override
    public function send(String $title, String $message)
    {
        mail($this->adminEmail, $title, $message);
        echo "Sent email with title '$title' to '{$this->adminEmail}' that says '$message'.";
    }
}

/**
 * The Adaptee is some useful class, incompatible with the Target interface. You
 * can't just go in and change the code of the class to follow the Target
 * interface, since the code might be provided by a 3rd-party library.
 */

class SlacKApi
{
    private $login;
    private $apiKey;

    public function __construct(String $login, String $apiKey)
    {
        $this->login = $login;
        $this->apiKey = $apiKey;
    }

    public function login(): void
    {
        // Send authentication request to Slack web service.
        echo "Logged in to a slack account '{$this->login}'.\n";
    }

    public function sendMeessage(string $chatId, string $message): void
    {
        // Send message post request to Slack web service.
        echo "Posted following message into the '$chatId' chat: '$message'.\n";
    }
}

/**
 * The Adapter is a class that links the Target interface and the Adaptee class.
 * In this case, it allows the application to send notifications using Slack
 * API.
 */
class slackNotificationAdapter implements Notification
{
    private $slack;
    private $chatId;

    public function __construct(SlackApi $slack, $chatId = null)
    {
        $this->slack = $slack;
        $this->chatId = $chatId;
    }

    /**
     * An Adapter is not only capable of adapting interfaces, but it can also
     * convert incoming data to the format required by the Adaptee.
     */

    //Override
    public function send(string $title, string $message)
    {
        $slackMessage = "#" . $title . "# " . strip_tags($message);
        $this->slack->login();
        $this->slack->sendMeessage($this->chatId, $slackMessage);
    }
}

/**
 * The client code can work with any class that follows the Target interface.
 */
function clienCode(Notification $notification){
    // ...

    echo $notification->send("Website is down!",
        "<strong style='color:red;font-size: 50px;'>Alert!</strong> " .
        "Our website is not responding. Call admins and bring it up!");

    // ...
}

echo "Client code is designed correctly and works with email notifications:\n";
$notification = new EmailNotification("developers@example.com");
clienCode($notification);
echo "\n\n";

echo "The same client code can work with other classes via adapter:\n";
$slackApi = new SlackApi("developers@example.com", "#######");
$notification = new slackNotificationAdapter($slackApi,"develop@example.com");






//     Adapter class  
// +----------------------------+
// |    SlackNotification       |
// |----------------------------|
// | - slack: SlackApi          |
// | - chatId: string           |
// |----------------------------|
// | + SlackNotification(SlackApi, string) |
// | + send(string, string): void |
// +----------------------------+
//             ^
//             |
//             | implements
//             v
// +----------------------+  
// |    Notification      |     
// |----------------------|                
// | + send(string, string): void |
// +----------------------+
//            ^
//            |
//            | implements
//            v
// +----------------------------+
// |    EmailNotification       |
// |----------------------------|
// | - adminEmail: string       |
// |----------------------------|
// | + EmailNotification(string)|
// | + send(string, string): void |
// +----------------------------+

// +----------------------+
// |       SlackApi       |
// |----------------------|
// | - login: string      |
// | - apiKey: string     |
// |----------------------|
// | + SlackApi(string, string) |
// | + logIn(): void           |
// | + sendMessage(string, string): void |
// +----------------------+