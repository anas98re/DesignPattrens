// Proxy is a structural design pattern that provides an object that acts as a substitute
// for a real service object used by a client.
// A proxy receives client requests, does some work (access control, caching, etc.)
// and then passes the request to a service object.

// The proxy object has the same interface as a service, which makes it interchangeable
// with a real object when passed to a client.ol

//Example : 
// In this example, the Proxy pattern helps to implement the lazy initialization and 
// caching to an inefficient 3rd-party YouTube integration library.

// Proxy is invaluable when you have to add some additional behaviors to a class 
// which code you can’t change.


//Commen Interface
interface Image {
    void display();
}

//The real Object
class RealImage implements Image {
    public String filename;

    public RealImage(String filename) {
        this.filename = filename;
    } 

    private void loadFromDisk(){
        System.out.println("Loading " + filename);
    }

    @Override
    public void display() {
        System.out.println("Displaying " + filename);
    }
}

//The Proxy
class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;

    public ProxyImage(String filename) {
        this.filename = filename;
    }

    @Override
    public void display() {
        if (realImage == null){
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}

//client code
class proxyPattrenDemo {
    public static void main(String[] args) {
        Image image = new ProxyImage("test_image.jpg");
        
        // The image is loaded and displayed on the first call
        // First time: loading and displaying the image
        image.display();
        System.out.println("");

        // Second time: displaying the image without loading
        image.display();
    }

}

// +------------------+         +------------------+
// |      Client      |         |   RealSubject    |
// |------------------|        /|------------------|
// | - Image image    |-------/ | - filename: String|
// | - main()         |      /  |------------------|    
// |                  |     /   | + display()      |
// +------------------+    /    | + loadFromDisk() |
//                          /   +------------------+
//                         /
//                        /
//                       /
//            +---------/---------+
//            |       Proxy       |
//            |-------------------|
//            | - RealImage image |
//            | - filename: String|
//            |-------------------|
//            | + display()       |
//            +-------------------+
