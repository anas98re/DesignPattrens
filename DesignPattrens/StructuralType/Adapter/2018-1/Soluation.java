//Adapter 
public class scalableRectangleAdapter implements ScalableRectangle {
    Rectangle rectangle = new Rectangle();

    @Override
    void scal(float value){
        float newWidth = rectangle.getWidth() * value;
        float newHeight = rectangle.getHeight() * value;
        rectangle.setWidth(newWidth);
        rectangle.setHeight(newHeight);
    }

    //Additionals
    @Override
    public void setWidth(float value) {
        rectangle.setWidth(value);
    }

    @Override
    public void setHeight(float value) {
        rectangle.setHeight(value);
    }

    @Override
    public float getWidth() {
        return rectangle.getWidth();
    }

    @Override
    public float getHeight() {
        return rectangle.getHeight();
    }
}