
<?php

namespace RefactoringGuru\Facade\RealWorld;

// Think of the Facade as a simplicity adapter for some complex subsystem. The 
// Facade isolates complexity within a single class and allows other application 
// code to use the straightforward interface.

// In this example, the Facade hides the complexity of the YouTube API and FFmpeg 
// library from the client code. Instead of working with dozens of classes, the client 
// uses a simple method on the Facade.

/**
 * The Facade provides a single method for downloading videos from YouTube. This
 * method hides all the complexity of the PHP network layer, YouTube API and the
 * video conversion library (FFmpeg).
 */

class YouTubeDownloader
{
    protected $youtube;
    protected $ffmpeg;

    /**
     * It is handy when the Facade can manage the lifecycle of the subsystem it
     * uses.
     */
    public function __construct(YouTube $youtube, FFMpeg $ffmpeg)
    {
        $this->youtube = $youtube;
        $this->ffmpeg = $ffmpeg;
    }

    public function downloadVideo(string $url): void
    {
        echo "Fetching video metadata from YouTube...\n";
        $title = $this->youtube->fetchVideo($url);

        echo "Saving video file to a temporary file...\n";
        $this->youtube->saveAs($url, "video.mpg");

        echo "Processing source video...\n";
        $video = $this->ffmpeg->open('video.mpg');

        echo "Normalizing and resizing the video to smaller dimensions...\n";
        $video
            ->filters()
            ->resize(new Dimension(320, 240))
            ->synchronize();

        echo "Capturing preview image...\n";
        // $video
        //     ->frame(TimeCode::fromSeconds(10))
        //     ->save($title.'frame.jpg','1');

        echo "Saving video in target formats...\n";
        $video
            ->save(new X264(), $title . '.mp4')
            ->save(new WMV(), $title . '.wmv')
            ->save(new WebM(), $title . '.webm');

        echo "Done!\n";
    }
}

/**
 * The YouTube API subsystem.
 */
class YouTube
{
    public function fetchVideo(string $url): string
    {
        // Dummy implementation
        return "Sample Video Title";
    }

    public function saveAs(string $url, string $path): void
    {
        // Dummy implementation
        echo "Video saved as $path\n";
    }
}

/**
 * The FFmpeg subsystem (a complex video/audio conversion library).
 */
class FFMpeg
{
    public static function create(): FFMpeg
    {
        // Dummy implementation
        return new self();
    }

    public function open(string $video): FFMpegVideo
    {
        // Dummy implementation
        return new FFMpegVideo();
    }
}

class FFMpegVideo
{
    public function filters(): self
    {
        // Dummy implementation
        return $this;
    }

    public function resize(Dimension $dim): self
    {
        // Dummy implementation
        return $this;
    }

    public function synchronize(): self
    {
        // Dummy implementation
        return $this;
    }

    public function frame(TimeCode $timecode): self
    {
        // Dummy implementation
        return $this;
    }

    public function save(FormatInterface $format, string $path): self
    {
        // Dummy implementation
        echo "Video saved as $path\n";
        return $this;
    }
}

class Dimension
{
    private $width;
    private $height;

    public function __construct(int $width, int $height)
    {
        $this->width = $width;
        $this->height = $height;
    }
}

class TimeCode
{
    public static function fromSeconds(int $seconds): TimeCode
    {
        // Dummy implementation
        return new self();
    }
}

interface FormatInterface
{ /* Dummy implementation */
}

class X264 implements FormatInterface
{ /* Dummy implementation */
}
class WMV implements FormatInterface
{ /* Dummy implementation */
}
class WebM implements FormatInterface
{ /* Dummy implementation */
}

/**
 * The client code does not depend on any subsystem's classes. Any changes
 * inside the subsystem's code won't affect the client code. You will only need
 * to update the Facade.
 */
function clientCode(YouTubeDownloader $facade)
{
    // ...

    $facade->downloadVideo("https://www.youtube.com/watch?v=QH2-TGUlwu4");

    // ...
}

$youtube = new YouTube();
$ffmpeg = FFMpeg::create();
$facade = new YouTubeDownloader($youtube, $ffmpeg);
clientCode($facade);


// +--------------------+
// | YouTubeDownloader  |
// |--------------------|
// | - youtube: YouTube |
// | - ffmpeg: FFMpeg   |
// |--------------------|
// | + downloadVideo(url: String): void |
// +--------------------+
//             |
//             |
//             v
// +-----------+-----------+
// | YouTube                |              +------------------+
// |------------------------|              | FFMpeg           |
// | + fetchVideo(url: String): String |  |------------------|
// | + saveAs(url: String, path: String): void |  | + create(): FFMpeg  |
// +------------------------+              | + open(video: String): FFMpegVideo |
//                                         +------------------+
//                                                     |
//                                                     |
//                                                     v
//                             +----------------------+
//                             | FFMpegVideo          |
//                             |----------------------|
//                             | + filters(): self    |
//                             | + resize(dim: Dimension): self |
//                             | + synchronize(): self |
//                             | + frame(time: TimeCode): self |
//                             | + save(format: FormatInterface, path: String): self |
//                             +----------------------+
//                                     |
//                                     |
//                                     v
//                     +-------------------------+
//                     | Dimension               |
//                     |-------------------------|
//                     | - width: int            |
//                     | - height: int           |
//                     | + __construct(width: int, height: int) |
//                     +-------------------------+

//                     +-------------------------+
//                     | TimeCode                |
//                     |-------------------------|
//                     | + fromSeconds(seconds: int): TimeCode |
//                     +-------------------------+

//                     +-------------------------+
//                     | <<interface>>           |
//                     | FormatInterface         |
//                     +-------------------------+
//                             ^
//                             |
//     +-----------------------+-------------------------+
//     |                       |                         |
// +--------------------+ +--------------------+ +--------------------+
// | X264               | | WMV                | | WebM               |
// +--------------------+ +--------------------+ +--------------------+
