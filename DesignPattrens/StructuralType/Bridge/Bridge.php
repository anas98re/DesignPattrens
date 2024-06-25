<?php

namespace RefactoringGuru\Bridge\RealWorld;

// Example in php:
// in this example, the Page hierarchy acts as the Abstraction, 
// and the Renderer hierarchy acts as the Implementation. Objects 
// of the Page class can assemble web pages of a particular kind 
// basic elements provided by a Renderer object attached to that page. 
// Since both of the class hierarchies are separate, you can add a new 
// Renderer class without changing any of the Page classes and vice versa.

/**
 * The Abstraction.
 */
abstract class page
{
    protected $renderer;

    /**
     * The Abstraction is usually initialized with one of the Implementation
     * objects.
     */
    public function __construct(Renderer $renderer)
    {
        $this->renderer = $renderer;
    }

    /**
     * The Bridge pattern allows replacing the attached Implementation object
     * dynamically.
     */
    public function changeRenderer(Renderer $renderer): void
    {
        $this->renderer = $renderer;
    }

    /**
     * The "view" behavior stays abstract since it can only be provided by
     * Concrete Abstraction classes.
     */
    abstract public function view(): string;
}

/**
 * This Concrete Abstraction represents a simple page.
 */
class SimplePage extends Page
{
    protected $title;
    protected $content;

    public function __construct(Renderer $renderer, string $title, string $content)
    {
        parent::__construct($renderer);
        $this->title = $title;
        $this->content = $content;
    }

    public function view(): string
    {
        return $this->renderer->renderParts([
            $this->renderer->renderHeader(),
            $this->renderer->renderTitle($this->title),
            $this->renderer->renderTextBlock($this->content),
            $this->renderer->renderFooter()
        ]);
    }
}

/**
 * This Concrete Abstraction represents a more complex page.
 */
class ProductPage extends Page
{
    protected $product;

    public function __construct(Renderer $renderer, Product $product)
    {
        parent::__construct($renderer);
        $this->product = $product;
    }

    public function view(): string
    {
        return $this->renderer->renderParts([
            $this->renderer->renderHeader(),
            $this->renderer->renderTitle($this->product->getTitle()),
            $this->renderer->renderTextBlock($this->product->getDescription()),
            $this->renderer->renderImage($this->product->getImage()),
            $this->renderer->renderLink("/cart/add/" . $this->product->getId(), "Add to cart"),
            $this->renderer->renderFooter()
        ]);
    }
}


/**
 * A helper class for the ProductPage class.
 */
class Product {
    private $id, $title, $description, $image, $price;

    public function __construct(
        string $id,
        string $title,
        string $description,
        string $image,
        float $price
    ) {
        $this->id = $id;
        $this->title = $title;
        $this->description = $description;
        $this->image = $image;
        $this->price = $price;
    }

    public function getId(): string { return $this->id; }

    public function getTitle(): string { return $this->title; }

    public function getDescription(): string { return $this->description; }

    public function getImage(): string { return $this->image; }

    public function getPrice(): float { return $this->price; }
}

/**
 * The Implementation declares a set of "real", "under-the-hood", "platform"
 * methods.
 *
 * In this case, the Implementation lists rendering methods that can be used to
 * compose any web page. Different Abstractions may use different methods of the
 * Implementation.
 */
interface Renderer{
    public function renderTitle(string $title): string;

    public function renderTextBlock(string $text): string;

    public function renderImage(string $url): string;

    public function renderLink(string $url, string $title): string;

    public function renderHeader(): string;

    public function renderFooter(): string;

    public function renderParts(array $parts): string;
}

/**
 * This Concrete Implementation renders a web page as HTML.
 */
class HTMLRenderer implements Renderer
{
    public function renderTitle(string $title): string
    {
        return "<h1>$title</h1>";
    }

    public function renderTextBlock(string $text): string
    {
        return "<div class='text'>$text</div>";
    }

    public function renderImage(string $url): string
    {
        return "<img src='$url'>";
    }

    public function renderLink(string $url, string $title): string
    {
        return "<a href='$url'>$title</a>";
    }

    public function renderHeader(): string
    {
        return "<html><body>";
    }

    public function renderFooter(): string
    {
        return "</body></html>";
    }

    public function renderParts(array $parts): string
    {
        return implode("\n", $parts);
    }
}

/**
 * This Concrete Implementation renders a web page as JSON strings.
 */
class JsonRenderer implements Renderer
{
    public function renderTitle(string $title): string
    {
        return '"title": "' . $title . '"';
    }

    public function renderTextBlock(string $text): string
    {
        return '"text": "' . $text . '"';
    }

    public function renderImage(string $url): string
    {
        return '"img": "' . $url . '"';
    }

    public function renderLink(string $url, string $title): string
    {
        return '"link": {"href": "' . $url . '", "title": "' . $title . '"}';
    }

    public function renderHeader(): string
    {
        return '';
    }

    public function renderFooter(): string
    {
        return '';
    }

    public function renderParts(array $parts): string
    {
        return "{\n" . implode(",\n", array_filter($parts)) . "\n}";
    }
}

/**
 * The client code usually deals only with the Abstraction objects.
 */
function clientCode(Page $page)
{
    // ...

    echo $page->view();

    // ...
}


/**
 * The client code can be executed with any pre-configured combination of the
 * Abstraction+Implementation.
 */
$HTMLRenderer = new HTMLRenderer();
$JSONRenderer = new JsonRenderer();

$page = new SimplePage($HTMLRenderer, "Home", "Welcome to our website!");
echo "HTML view of a simple content page:\n";
clientCode($page);
echo "\n\n";

/**
 * The Abstraction can change the linked Implementation at runtime if needed.
 */
$page->changeRenderer($JSONRenderer);
echo "JSON view of a simple content page, rendered with the same client code:\n";
clientCode($page);
echo "\n\n";


$product = new Product("123", "Star Wars, episode1",
    "A long time ago in a galaxy far, far away...",
    "/images/star-wars.jpeg", 39.95);

$page = new ProductPage($HTMLRenderer, $product);
echo "HTML view of a product page, same client code:\n";
clientCode($page);
echo "\n\n";

$page->changeRenderer($JSONRenderer);
echo "JSON view of a simple content page, with the same client code:\n";
clientCode($page);

// +------------------------------+
// |            Renderer          |
// |------------------------------|
// | + renderTitle(string): string|
// | + renderTextBlock(string):   |
// |   string                     |
// | + renderImage(string): string|
// | + renderLink(string, string):|
// |   string                     |
// | + renderHeader(): string     |
// | + renderFooter(): string     |
// | + renderParts(array): string |
// +--------------^---------------+
//                |
// +-------------------+-------------------+
// |                                       |
// +-------------------+               +-------------------+
// |   HTMLRenderer    |               |   JsonRenderer    |
// |-------------------|               |-------------------|
// | + renderTitle(string): string     | + renderTitle(string): string  |
// | + renderTextBlock(string): string | + renderTextBlock(string): string|
// | + renderImage(string): string     | + renderImage(string): string  |
// | + renderLink(string, string):     | + renderLink(string, string): string|
// |   string                         | + renderHeader(): string       |
// | + renderHeader(): string         | + renderFooter(): string       |
// | + renderFooter(): string         | + renderParts(array): string   |
// | + renderParts(array): string     |                                |
// +-------------------+               +-------------------+
//                ^
//                |
// +--------------+-------------------+
// |              Page                |
// |------------------------------    |
// | - renderer: Renderer             |
// |------------------------------    |
// | + __construct(Renderer)          |
// | + changeRenderer(Renderer): void |
// | + view(): string                 |
// +--------------^-------------------+
//                |
// +--------------+-------------------+
// |                                      |
// +----------------------+              +----------------------+
// |     SimplePage       |              |     ProductPage      |
// |----------------------|              |----------------------|
// | - title: string      |              | - product: Product   |
// | - content: string    |              |----------------------|
// |----------------------|              | + __construct(Renderer,|
// | + __construct(Renderer,             |   Product)            |
// |   string, string)                   | + view(): string      |
// | + view(): string                    +----------------------+
// +----------------------+
//                ^
//                |
//                |
//                |
//           +------------------------------+
//           |            Product            |
//           |------------------------------|
//           | - id: string                 |
//           | - title: string              |
//           | - description: string        |
//           | - image: string              |
//           | - price: float               |
//           |------------------------------|
//           | + __construct(string,        |
//           |   string, string, string,    |
//           |   float)                     |
//           | + getId(): string            |
//           | + getTitle(): string         |
//           | + getDescription(): string   |
//           | + getImage(): string         |
//           | + getPrice(): float          |
//           +------------------------------+

// the explanation:
// Renderer: An interface that defines a set of methods for rendering content.
// HTMLRenderer and JsonRenderer: These are specific implementations of Renderer. 
// They implement content presentation methods in different ways (HTML and JSON).
// Page: An abstract class that contains a reference to the Renderer, and is used 
// to display content. It also contains a changeRenderer method to change the Renderer 
// at runtime.
// SimplePage and ProductPage: These are two extensions of Page, representing different 
// types of pages. Each of them implements the view method to display content using the 
// specified Renderer.
// Product: A helper class containing product details, used by ProductPage.

// Using Bridge: <<<<<<<<<<<<<< IMPORTANT >>>>>>>>>>>>>>>>>
// Bridge mode separates the abstraction (the page) from the implementation (the renderer), 
// allowing both aspects to be developed independently. This pattern can be used when you 
// have multiple abstractions and multiple implementations, such as with different pages 
// (such as SimplePage and ProductPage) and different views (such as HTMLRenderer and 
// JsonRenderer).