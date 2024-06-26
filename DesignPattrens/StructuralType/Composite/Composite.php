<?php

// The Composite pattern can streamline the work with any tree-like 
// recursive structures. The HTML DOM tree is an example of such a 
// structure. For instance, while the various input elements can act as 
// leaves, the complex elements like forms and fieldsets play the role of composites.

// Bearing that in mind, you can use the Composite pattern to apply various 
// behaviors to the whole DOM tree in the same way as to its inner elements 
// without coupling your code to concrete classes of the DOM tree. Examples 
// of such behaviors might be rendering the DOM elements, exporting it into 
// various formats, validating its parts, etc.

// With the Composite pattern, you don’t need to check whether it’s the simple 
// or complex type of element before executing the behavior. Depending on the 
// element’s type, it either gets executed right away or passed all the way down 
// to all element’s children.


namespace RefactoringGuru\Composite\RealWorld;

/**
 * The base Component class declares an interface for all concrete components,
 * both simple and complex.
 *
 * In our example, we'll be focusing on the rendering behavior of DOM elements.
 */
abstract class FormElement
{
    /**
     * We can anticipate that all DOM elements require these 3 fields.
     */
    protected $name;
    protected $title;
    protected $data;

    public function __construct(string $name, string $title)
    {
        $this->name = $name;
        $this->title = $title;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function setData($data): void
    {
        $this->data = $data;
    }

    public function getData(): array
    {
        return $this->data;
    }

    /**
     * Each concrete DOM element must provide its rendering implementation, but
     * we can safely assume that all of them are returning strings.
     */
    abstract public function render(): string;
}

/**
 * This is a Leaf component. Like all the Leaves, it can't have any children.
 */
class Input extends FormElement
{
    private $type;

    public function __construct(string $name, string $title, string $type)
    {
        parent::__construct($name, $title);
        $this->type = $type;
    }

    /**
     * Since Leaf components don't have any children that may handle the bulk of
     * the work for them, usually it is the Leaves who do the most of the heavy-
     * lifting within the Composite pattern.
     */
    public function render(): string
    {
        return "<label for=\"{$this->name}\">{$this->title}</label>\n" .
            "<input name=\"{$this->name}\" type=\"{$this->type}\" value=\"{$this->data}\">\n";
    }
}

/**
 * The base Composite class implements the infrastructure for managing child
 * objects, reused by all Concrete Composites.
 */
abstract class FieldComposite extends FormElement
{
    /**
     * @var FormElement[]
     */
    protected $fields = [];

    /**
     * The methods for adding/removing sub-objects.
     */
    public function add(FormElement $field): void
    {
        $name = $field->getName();
        $this->fields[$name] = $field;
    }

    public function remove(FormElement $component): void
    {
        $this->fields = array_filter($this->fields, function ($child) use ($component) {
            return $child != $component;
        });
    }

    /**
     * Whereas a Leaf's method just does the job, the Composite's method almost
     * always has to take its sub-objects into account.
     *
     * In this case, the composite can accept structured data.
     *
     * @param array $data
     */
    public function setData($data): void
    {
        foreach ($this->fields as $name => $field) {
            if (isset($data[$name])) {
                $field->setData($data[$name]);
            }
        }
    }

    /**
     * The same logic applies to the getter. It returns the structured data of
     * the composite itself (if any) and all the children data.
     */
    public function getData(): array
    {
        $data = [];
        
        foreach ($this->fields as $name => $field) {
            $data[$name] = $field->getData();
        }
        
        return $data;
    }

    /**
     * The base implementation of the Composite's rendering simply combines
     * results of all children. Concrete Composites will be able to reuse this
     * implementation in their real rendering implementations.
     */
    public function render(): string
    {
        $output = "";
        
        foreach ($this->fields as $name => $field) {
            $output .= $field->render();
        }
        
        return $output;
    }
}

/**
 * The fieldset element is a Concrete Composite.
 */
class Fieldset extends FieldComposite
{
    public function render(): string
    {
        // Note how the combined rendering result of children is incorporated
        // into the fieldset tag.
        $output = parent::render();
        
        return "<fieldset><legend>{$this->title}</legend>\n$output</fieldset>\n";
    }
}

/**
 * And so is the form element.
 */
class Form extends FieldComposite
{
    protected $url;

    public function __construct(string $name, string $title, string $url)
    {
        parent::__construct($name, $title);
        $this->url = $url;
    }

    public function render(): string
    {
        $output = parent::render();
        return "<form action=\"{$this->url}\">\n<h3>{$this->title}</h3>\n$output</form>\n";
    }
}

/**
 * The client code gets a convenient interface for building complex tree
 * structures.
 */
function getProductForm(): FormElement
{
    $form = new Form('product', "Add product", "/product/add");
    $form->add(new Input('name', "Name", 'text'));
    $form->add(new Input('description', "Description", 'text'));

    $picture = new Fieldset('photo', "Product photo");
    $picture->add(new Input('caption', "Caption", 'text'));
    $picture->add(new Input('image', "Image", 'file'));
    $form->add($picture);

    return $form;
}

/**
 * The form structure can be filled with data from various sources. The Client
 * doesn't have to traverse through all form fields to assign data to various
 * fields since the form itself can handle that.
 */
function loadProductData(FormElement $form)
{
    $data = [
        'name' => 'Apple MacBook',
        'description' => 'A decent laptop.',
        'photo' => [
            'caption' => 'Front photo.',
            'image' => 'photo1.png',
        ],
    ];

    $form->setData($data);
}

/**
 * The client code can work with form elements using the abstract interface.
 * This way, it doesn't matter whether the client works with a simple component
 * or a complex composite tree.
 */
function renderProduct(FormElement $form)
{
    // ..

    echo $form->render();

    // ..
}

$form = getProductForm();
loadProductData($form);
renderProduct($form);

// +----------------------------+
// |        FormElement         / Abstract class
// |----------------------------|
// | - name: String             |
// | - title: String            |
// | - data: Array              |
// |----------------------------|
// | + getName(): String        |
// | + setData($data): void     |
// | + getData(): Array         |
// | + render(): String         |
// +-------------^--------------+
//               |
//               |
//   +-----------+------------+
//   |                        |
//   |                        |
// +------------+    +----------------+
// |   Input    |    | FieldComposite |
// |------------|    |----------------|
// | - type: String  | - fields: Array|
// |------------|    |----------------|
// | + render() |    | + add($field)  |
// +------------+    | + remove($comp)|
//                   | + setData($data)|
//                   | + getData(): Arr|
//                   | + render(): Str |
//                   +-------^---------+
//                           |
//           +---------------+--------------------+
//           |                                    |
//   +----------------+                 +--------------------+
//   |    Fieldset    |                 |       Form         |
//   +----------------+                 +--------------------+
//   | + render(): Str|                 | - url: String      |
//   +----------------+                 |--------------------|
//                                      | + render(): String |
//                                      +--------------------+

// Explanation of the drawing:
// FormElement: The abstract base class that contains fields and operations common 
// to all elements.
// Input: A simple input element (leaf) that inherits from FormElement and must 
// implement a render function.
// FieldComposite: A composite class that can contain other components. Provides 
// functionality to add, remove and assign data to subcomponents.
// Fieldset: A composite class that represents a collection of form elements. 
// Inherits from FieldComposite.
// Form: A composite class that represents an HTML form. It inherits from 
// FieldComposite and contains an additional URL field.
// Composite Pattern enables us to create a hierarchical structure of objects 
// and standardize dealing with them. This allows us to build flexible and easily scalable systems.