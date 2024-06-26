// Simple interface for a complex video conversion library

// In this example, the Facade simplifies communication with a 
// complex video conversion framework.

// The Facade provides a single class with a single method that 
// handles all the complexity of configuring the right classes of the 
// framework and retrieving the result in a correct format.

// Instead of making your code work with dozens of the framework classes 
// directly, you create a facade class which encapsulates that functionality 
// and hides it from the rest of the code. This structure also helps you to 
// minimize the effort of upgrading to future versions of the framework or 
// replacing it with another one. The only thing you’d need to change in your 
// app would be the implementation of the facade’s methods.

// While Facade decreases the overall complexity of the application, it also 
// helps to move unwanted dependencies to one place.

//some_complex_media_library/VideoFile.java
public class VideoFile {
    private String name;
    private String codecType;

    public VideoFile(String name, String codecType){
        this.name = name;
        this.codecType = codecType;
    }

    public String getCodecType() {
        return codecType;
    }

    public String getName() {
        return name;
    }
}

//some_complex_media_library/Codec.java
public interface Codec {
}

//some_complex_media_library/MPEG4CompressionCodec.java
public class MPEG4CompressionCodec implements Codec{
    public String type = "mp4";
}

//some_complex_media_library/OggCompressionCodec.java
public class OggCompressionCodec implements Codec {
    public String type = "ogg";
}

//some_complex_media_library/CodecFactory.java
public class CodecFactory {
    public static Codec extract(VideoFile file){
        String type = file.getCodecType();
        if (type.equals("mp4")) {
            System.out.println("CodecFactory: extracting mpeg audio...");
            return new MPEG4CompressionCodec();
        }
        else {
            System.out.println("CodecFactory: extracting ogg audio...");
            return new OggCompressionCodec();
        }
    }
}

//some_complex_media_library/BitrateReader.java
public class BitrateReader {
    public static VideoFile read(){
        System.out.println("BitrateReader: reading file...");
        return file;
    }

    public static VideoFile convert(VideoFile buffer, Codec codec){
        System.out.println("BitrateReader: writing file...");
        return buffer;
    }
}

//some_complex_media_library/AudioMixer.java
public class AudioMixer {
    public File fix(VideoFile result){
        System.out.println("AudioMixer: fixing audio...");
        return new File("tmp");
    }
}

//facade/VideoConversionFacade.java: Facade provides simple interface of video conversion
public class VideoConversionFacade{
    public File ConvertVideo(String fileName, String format){
            System.out.println("VideoConversionFacade: conversion started.");
            VideoFile file = new VideoFile(fileName);
            Codec sourceCodec = CodecFactory.extract(file);
            Codec destinationCodec;
            if (format.equals("mp4")) {
                destinationCodec = new MPEG4CompressionCodec();
            } else {
                destinationCodec = new OggCompressionCodec();
            }

            VideoFile buffer = BitrateReader.read(file, sourceCodec);
            VideoFile intermediateResult = BitrateReader.convert(buffer, destinationCodec);
            File result = (new AudioMixer()).fix(intermediateResult);
            System.out.println("VideoConversionFacade: conversion completed.");
            return result;
    }
}

//Client code
public class Demo {
    public static void main(String[] args) {
        VideoConversionFacade convertor = new VideoConversionFacade();
        File mp4Video = convertor.convertVideo("youtubevideo.ogg", "mp4");
    }
}

// +-------------------------------------+
// |            VideoConversionFacade    |
// |-------------------------------------|<-----asks---------- Application
// | + convertVideo(fileName, format)    |
// |-------------------------------------|
// |                                     |
// |    1. Create VideoFile              |
// |    2. Extract Codec using CodecFactory  |
// |    3. Read file using BitrateReader |
// |    4. Convert file using BitrateReader|
// |    5. Fix audio using AudioMixer    |
// +-------------------------------------+
//              |      |         |        |
//              v      v         v        v
// +------------+  +------------+  +------------+  +------------+
// | VideoFile  |  | CodecFactory |  | BitrateReader |  | AudioMixer   |
// +------------+  +------------+  +------------+  +------------+
// | + getName() |  | + extract() |  | + read()      |  | + fix()      |
// | + getFormat()|  |            |  | + convert()   |  |              |
// +------------+  +------------+  +------------+  +------------+
