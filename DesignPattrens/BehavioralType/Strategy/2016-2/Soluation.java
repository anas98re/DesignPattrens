//The Fit design pattren is : Factory Method
public interface Player {
    public void play(String file);
}

public class videoPlayer implements Player {
    @Override
    public void play(String file){
        System.out.println("Playing video file: " + file);
    }
}

public class AudioPlayer implements Player {
    @Override
    public void play(String file) {
        System.out.println("Playing audio file: " + file);
    }
}

//Factories
abstract class MediaPlayerFactory {
    protected abstract Player createsPlayer();

    public void play(String file){
        Player player = createPlayer();
        if(!player){
            player.play(file);
        } else {
            System.out.println("Unsupported file format: " + file);
        }
    }
}

public class AudioPlayerFactory implements PlayerFactory{
    @Override
    protected player createPlayer(){
        return new AudioPlayer();
    } 
}

public class VideoPlayerFactory implements PlayerFactory{
    @Override
    protected player createPlayer(){
        return new VideoPlayer();
    }
}

public class Main {
    public static void main(String[] args) {
        MediaPlayerFactory videoPlayer = new MediaPlayerFactory();
        videoPlayer.play("example.mp4");

        MediaPlayerFactory audioPlayer = new MediaPlayerFactory();
        audioPlayer.play("example.mp3");

        MediaPlayerFactory newFormatPlayer = new MediaPlayerFactory() {
            @Override
            protected Player createPlayer() {
                return new Player() {
                    @Override
                    public void play(String file) {
                        System.out.println("Playing new format file: " + file);
                    }
                };
            }
        };
        newFormatPlayer.play("example.newformat");
    }
}




//Another Soluation
//the second fit design pattren is : Strategy
interface MediaStrategy {
    void play(String file);
}

class VideoStrategy implements MediaStrategy {
    @Override
    public void play(String file){
        System.out.println("Playing video file: " + file);
    }
}

class AudioStrategy implements MediaStrategy {
    @Override
    public void play(String file){
        System.out.println("Playing audio file: " + file);
    }
}

//Context
public class MediaPlayerContext {
    private mediaStrategy strategy;

    pubilc void setStrategy(mediaStrategy strategy){
        this.strategy = strategy;
    }

    public void play(String file){
        if(!strategy){
            strategy.play(file);
        } else {
            System.out.println("No strategy set. Cannot play file: " + file);
        }
    }
}


public class Main{
    public static void main(string[] arg){
        MediaPlayerContext player = new MediaPlayerContext();

        player.setStrategy(new VideoStrategy());
        player.play("ex1.mp4");

        player.setStrategy(new AudioStrategy());
        player.play("ex2.mp3");


    }
}

// +----------------------+           +----------------------+
// |      MediaPlayerContext|          |     MediaStrategy    |
// |----------------------|           |----------------------|
// | - strategy: MediaStrategy       | + play(file: String)  |
// |----------------------|           +----------------------+
// | + setStrategy(strategy: MediaStrategy) |
// | + play(file: String) |
// +----------------------+           +----------------------+
//         |                                  ^
//         |                                  |
//         |                                  |
//         |                                  |
// +--------------------------+     +---------------------------+
// |     VideoStrategy        |     |      AudioStrategy        |
// |--------------------------|     |---------------------------|
// | + play(file: String)     |     | + play(file: String)      |
// +--------------------------+     +---------------------------+