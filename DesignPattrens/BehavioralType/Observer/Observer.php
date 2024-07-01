<?php

namespace RefactoringGuru\Observer\RealWorld;

// In this example the Observer pattern allows various objects to observe 
// events that are happening inside a user repository of an app.

// The repository emits various types of events and allows observers to listen 
// to all of them, as well as only individual ones.

/**
 * The UserRepository represents a Subject. Various objects are interested in
 * tracking its internal state, whether it's adding a new user or removing one.
 */

class UserRepository implements \SplObserver
{
    /**
     * @var array The list of users.
     */
    private $users = [];

    // Here goes the actual Observer management infrastructure. Note that it's
    // not everything that our class is responsible for. Its primary business
    // logic is listed below these methods.

    /**
     * @var array
     */
    private $observers = [];

    public function __construct()
    {
        // A special event group for observers that want to listen to all
        // events.
        $this->observers["*"] = [];
    }

    private function initEventGroup(String $event = "*"): void
    {
        if (!isset($this->observers[$event])) {
            $this->observers[$event] = [];
        }
    }

    private function getEventObservers(string $event = "*"): array
    {
        $this->initEventGroup($event);
        $group = $this->observers[$event];
        $all = $this->observers["*"];

        return array_merge($group, $all);
    }

    public function attach(\SplObserver $observer, string $event = "*"): void
    {
        $this->initEventGroup($event);

        $this->observers[$event][] = $observer;
    }

    public function detach(\SplObserver $observer, string $event = "*"): void
    {
        foreach ($this->getEventObservers($event) as $key => $s) {
            if ($s === $observer) {
                unset($this->observers[$event][$key]);
            }
        }
    }

    public function notify(string $event = "*", $data = null): void
    {
        echo "UserRepository: Broadcasting the '$event' event.\n";
        foreach ($this->getEventObservers($event) as $observer) {
            $observer->update($this, $event, $data);
        }
    }

    // Here are the methods representing the business logic of the class.

    public function initialize($filename): void
    {
        echo "UserRepository: Loading user records from a file.\n";
        // ...
        $this->notify("users:init", $filename);
    }

    public function createUser(array $data): User
    {
        echo "UserRepository: Creating a user.\n";

        $user = new User();
        $user->update($data);

        $id = bin2hex(openssl_random_pseudo_bytes(16));
        $user->update(["id" => $id]);
        $this->users[$id] = $user;

        $this->notify("users:created", $user);

        return $user;
    }

    public function updateUser(User $user, array $data): User
    {
        echo "UserRepository: Updating a user.\n";

        $id = $user->attributes["id"];
        if (!isset($this->users[$id])) {
            return null;
        }

        $user = $this->users[$id];
        $user->update($data);

        $this->notify("users:updated", $user);

        return $user;
    }

    public function deleteUser(User $user): void
    {
        echo "UserRepository: Deleting a user.\n";

        $id = $user->attributes["id"];
        if (!isset($this->users[$id])) {
            return;
        }

        unset($this->users[$id]);

        $this->notify("users:deleted", $user);
    }
}


/**
 * Let's keep the User class trivial since it's not the focus of our example.
 */
class User
{
    public $attributes = [];

    public function update($data): void
    {
        $this->attributes = array_merge($this->attributes, $data);
    }
}

/**
 * This Concrete Component logs any events it's subscribed to.
 */
class Logger implements \SplObserver
{
    private $filename;

    public function __construct($filename)
    {
        $this->filename = $filename;
        if (file_exists($this->filename)) {
            unlink($this->filename);
        }
    }

    public function update(\SplSubject $repository, string $event = null, $data = null): void
    {
        $entry = date("Y-m-d H:i:s") . ": '$event' with data '" . json_encode($data) . "'\n";
        file_put_contents($this->filename, $entry, FILE_APPEND);

        echo "Logger: I've written '$event' entry to the log.\n";
    }
}

/**
 * This Concrete Component sends initial instructions to new users. The client
 * is responsible for attaching this component to a proper user creation event.
 */
class OnboardingNotification implements \SplObserver
{
    private $adminEmail;

    public function __construct($adminEmail)
    {
        $this->adminEmail = $adminEmail;
    }

    public function update(\SplSubject $repository, string $event = null, $data = null): void
    {
        // mail($this->adminEmail,
        //     "Onboarding required",
        //     "We have a new user. Here's his info: " .json_encode($data));

        echo "OnboardingNotification: The notification has been emailed!\n";
    }
}

/**
 * The client code.
 */

$repository = new UserRepository();
$repository->attach(new Logger(__DIR__ . "/log.txt"), "*");
$repository->attach(new OnboardingNotification("1@example.com"), "users:created");

$repository->initialize(__DIR__ . "/users.csv");

// ...

$user = $repository->createUser([
    "name" => "John Smith",
    "email" => "john99@example.com",
]);

// ...

$repository->deleteUser($user);


// In this example, the Observer design pattern was used to provide the ability to 
// monitor events that occur within the User Repository. This allows different things 
// to be able to track the state of the internal repository, whether it's adding a new 
// user or deleting one.

// Basic objects:
// UserRepository: Represents the repository that holds the list of users. It 
// broadcasts events when a change in internal state occurs.
// User: Represents the user and contains his data.
// Logger: Monitors events and writes logs of these events to a file.
// OnboardingNotification: Monitors events and sends initial startup instructions 
// to new users via email (simplified in example).
// The way of work:
// Different objects can register to subscribe to events broadcast by the user repository.
// When a certain event occurs (such as the creation of a new user), the repository notifies 
// all subscribers about this event.
// Subscribers perform their own behavior based on the event (such as writing a log or sending 
// an email).

// +-----------------------------------+
// |          \SplSubject              |
// +-----------------------------------+
// | + attach(observer: \SplObserver)  |
// | + detach(observer: \SplObserver)  |
// | + notify(event: string, data: any)|
// +-----------------------------------+
//            /\
//            |
// +-----------------------------+
// |         UserRepository      |
// +-----------------------------+
// | - users: array              |
// | - observers: array          |
// | + attach(observer, event)   |
// | + detach(observer, event)   |
// | + notify(event, data)       |
// | + initialize(filename)      |
// | + createUser(data): User    |
// | + updateUser(user, data): User|
// | + deleteUser(user)          |
// +-----------------------------+
//            /\
//            |
// +-------------------+   +--------------------------+   +-----------------------------+
// |   \SplObserver    |   |       Logger             |   |   OnboardingNotification    |
// +-------------------+   +--------------------------+   +-----------------------------+
// | + update(subject, |   | - filename: string       |   | - adminEmail: string        |
// |   event, data)    |   | + update(subject, event, |   | + update(subject, event,    |
// +-------------------+   |   data)                  |   |   data)                     |
//                         +--------------------------+   +-----------------------------+
//                                                /\
//                                                |
//                                              +------------------+
//                                              |      User        |
//                                              +------------------+
//                                              | - attributes: array|
//                                              | + update(data)    |
//                                              +------------------+
