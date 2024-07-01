// In this example, the Iterator pattern is used to go over social profiles of a remote 
// social network collection without exposing any of the communication details to the client code.

// ProfileIterator: An interface that defines the hasNext 
// and next operations for navigating between profiles.
interface ProfileIterator {
    boolean hasNext();
    Profile next();
}

// Social Network: An interface that defines the processes for 
// creating Profile Iterator objects.
interface SocialNetwork {
    ProfileIterator createFriendsIterator(String profileEmail);
    ProfileIterator createCoworkersIterator(String profileEmail);
}

// FacebookIterator: An implementation of the ProfileIterator interface for handling data of 
// friends or colleagues on Facebook.
class FacebookIterator implements ProfileIterator {
    private Facebook facebook;
    private String profileEmail;
    private String type;
    private int currentPosition = 0;
    private List<String> emails = new ArrayList<>();
    private List<Profile> profiles = new ArrayList<>();

    public FacebookIterator(Facebook facebook, String profileEmail, String type) {
        this.facebook = facebook;
        this.profileEmail = profileEmail;
        this.type = type;
    }

    // Function purpose: This function is responsible for lazy loading of data. 
    // If the emails list is empty, it loads data from an external source 
    // (in this case, from Facebook).
    // Code details:
    // if (emails.isEmpty()): Checks if the list of emails is empty. If it is empty, 
    // the download operation is performed.
    // List<String> profiles = facebook.requestProfileFriendsFromFacebook(profileEmail, type): 
    // Requests a list of profiles (friends or colleagues) from Facebook using the profile 
    // email and type (friends or colleagues).
    // for (String profile : profiles): Scrolls through the list of retrieved profiles.
    // this.emails.add(profile): Adds the profile email to the list of emails.
    // this.profiles.add(null): Adds null to the list of profiles in the same order. This 
    // is used to cache full profiles that will be fetched later if needed.
    public void lazyLoad(){
        if(emails.isEmpty()){
            List<String> profiles = facebook.requestProfileFriendsFromFacebook(profileEmail, type);
            for(String profile : profiles){
                this.emails.add(profile);
                this.profiles.add(null);
            }
        }
    }

    // Function purpose: This function determines whether there is a next element that 
    // can be retrieved by Iterator.
    // Code details:
    // lazyLoad(): Calls the lazyLoad function to check if data is loaded if it is not yet loaded.
    // return currentPosition < emails.size(): compares currentPosition to the size of the 
    // list of emails. If the current position is less than the list size, it returns true 
    // which means there is a next item to retrieve. If the current position is equal to or 
    // greater than the list size, it returns false which means there are no more items to retrieve.
    @Override
    public boolean hasNext(){
        lazyLoad();
        return currentPosition < email.size();
    }

    // In the context of this example, we use the Iterator design pattern to navigate through a 
    // set of profiles in a social network (such as Facebook) without revealing contact or storage 
    // details. lazyLoad ensures that data is loaded only when needed to avoid unnecessary data 
    // loading. hasNext uses lazyLoad to ensure that data is available before checking if there 
    // is a next element.

    public profile next() {
        if(!hasNext()){
            return null;
        }

        string friendEmail = email.get(currentPosition);
        Profile friendProfile = profiles.get(currentPosition);

        if(friendProfile == null){
            friendProfile = facebookProfile.requestProfileFriendsFromFacebook(friendEmail);
            profiles.set(currentPosition, friendProfile);
        }
        currentPosition++:
        return friendProfile;
    }
}

// Facebook: An implementation of the SocialNetwork interface that handles network 
// requests and creates ProfileIterator objects.
class Facebook implements SocialNetwork {
    @Override
    public ProfileIterator createFriendsIterator(String profileEmail){
        return new FacebookIterator(this, profileEmail, "friends");
    }

    @Override
    public ProfileIterator createCoworkersIterator(String profileEmail){
        return mew FacebookIterator(this, profileEmail, "coworkers");
    }

    // API requests simulation
    public List<String> requestProfileFriendsFromFacebook(String profileEmail, String type) {
        // Simulate a network request
        return Arrays.asList("friend1@example.com", "friend2@example.com");
    }

    public Profile requestProfileFromFacebook(String profileEmail) {
        // Simulate a network request
        return new Profile(profileEmail);
    }
}

// Profile: An object containing profile details.
class Profile {
    private String Email;
    // Other profile details...

    public Profile(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

//Client Code
public class SocialSpammer {
    public static void main(String[] args) {
        Facebook facebook = new Facebook();
        ProfileIterator friendsIterator = facebook.createFriendsIterator("user@example.com");

        while (friendsIterator.hasNext()) {
            Profile profile = friendsIterator.next();
            System.out.println("Sending spam email to " + profile.getEmail());
        }
    }
}

// +----------------------+          +--------------------------+
// |      Iterator        |          |        Aggregate         |
// +----------------------+          +--------------------------+
// | + hasNext(): boolean |          | + createIterator():      |
// | + next(): Object     |          |      Iterator            |
// +----------------------+          +--------------------------+
//             ^                                ^
//             |                                |
// +------------------------+       +---------------------------+
// |   ConcreteIterator     |       |     ConcreteAggregate     |
// +------------------------+       +---------------------------+
// | - index: int           |       | - items: List<Object>     |
// | + hasNext(): boolean   |       | + createIterator():       |
// | + next(): Object       |       |      Iterator             |
// +------------------------+       +---------------------------+
//             |                                |
//             |                                |
// +-----------+----------+          +----------+-----------+
// |   FacebookIterator   |          |          Facebook      |
// +----------------------+          +--------------------------+
// | - facebook: Facebook |          | - friends: List<Profile> |
// | - profileEmail: String|          | - coworkers: List<Profile>|
// | - type: String       |          |                          |
// | - currentPosition: int|          |                          |
// | - emails: List<String>|          |                          |
// | - profiles: List<Profile>|      |                          |
// +----------------------+          +--------------------------+
//             |
//             v
// +----------------------+ 
// |       Profile        |
// +----------------------+
// | - email: String      |
// | - name: String       |
// +----------------------+
