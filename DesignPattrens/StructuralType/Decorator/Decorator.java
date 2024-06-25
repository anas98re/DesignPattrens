// This example shows how you can adjust the behavior of an object without changing its code.

// Initially, the business logic class could only read and write data in plain text. Then we 
// created several small wrapper classes that add new behavior after executing standard 
// operations in a wrapped object.

// The first wrapper encrypts and decrypts data, and the second one compresses and extracts data.

// You can even combine these wrappers by wrapping one decorator with another.

public interface DataSource {
    void writeData(String data);

    String readData();
}

//FileDataSource class implements
public class FileDataSource implements DataSource {
    private String name;

    public FileDataSource(String name) {
        this.name = name;
    }

     @Override
    public void writeData(String data) {
        File file = new File(name);
        try (OutputStream fos = new FileOutputStream(file)) {
            fos.write(data.getBytes(), 0, data.length());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String readData() {
        char[] buffer = null;
        File file = new File(name);
        try (FileReader reader = new FileReader(file)) {
            buffer = new char[(int) file.length()];
            reader.read(buffer);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return new String(buffer);
    }
}

//DataSourceDecorator class implements DataSource 
public abstract class DataSourceDecorator implements DataSource{
    private DataSource wrapper;

    public DataSourceDecorator(DataSource source){
        this.wrapper = source;
    }

    @Override
    public void writeData(String data){
        wrapper.writeData(data);
    }

    @Override
    public void readData(){
        return wrappee.readData();
    }
}

//EncryptionDecorator class extends DataSource 
public class EncryptionDecorator extends DataSourceDecorator{
    public EncryptionDecorator(DataSource source){
        super(source);
    }

    @Override
    public void writeData(String data){
        super.writeData(encode(data));
    }

    @Override
    public void readData(){
        return decode(super.readData);
    }    

    private String encode(String data) {
        byte[] result = data.getBytes();
        for (int i = 0; i < result.length; i++) {
            result[i] += (byte) 1;
        }
        return Base64.getEncoder().encodeToString(result);
    }

    private String decode(String data) {
        byte[] result = Base64.getDecoder().decode(data);
        for (int i = 0; i < result.length; i++) {
            result[i] -= (byte) 1;
        }
        return new String(result);
    }
}

//CompressionDecorator class extends DataSourceDecorator 
public class CompressionDecorator extends DataSourceDecorator {
    private int compLevel = 6;

    public CompressionDecorator(DataSource source)
    {
        super(source);
    }

    public int getCompressionLevel() {
        return compLevel;
    }

    public void setCompressionLevel(int value) {
        compLevel = value;
    }

    @Override
    public void writeData(String data) {
        super.writeData(compress(data));
    }

    @Override
    public void readData(){
        return decompress(super.readData)
    }

    private String compress(String stringData) {
        byte[] data = stringData.getBytes();
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
            DeflaterOutputStream dos = new DeflaterOutputStream(bout, new Deflater(compLevel));
            dos.write(data);
            dos.close();
            bout.close();
            return Base64.getEncoder().encodeToString(bout.toByteArray());
        } catch (IOException ex) {
            return null;
        }
    }

    private String decompress(String stringData) {
        byte[] data = Base64.getDecoder().decode(stringData);
        try {
            InputStream in = new ByteArrayInputStream(data);
            InflaterInputStream iin = new InflaterInputStream(in);
            ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
            int b;
            while ((b = iin.read()) != -1) {
                bout.write(b);
            }
            in.close();
            iin.close();
            bout.close();
            return new String(bout.toByteArray());
        } catch (IOException ex) {
            return null;
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        String salaryRecords = "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000";
        DataSourceDecorator encoded = new CompressionDecorator(
                                        new EncryptionDecorator(
                                            new FileDataSource("out/OutputDemo.txt")));
        encoded.writeData(salaryRecords);
        DataSource plain = new FileDataSource("out/OutputDemo.txt");

        System.out.println("- Input ----------------");
        System.out.println(salaryRecords);
        System.out.println("- Encoded --------------");
        System.out.println(plain.readData());
        System.out.println("- Decoded --------------");
        System.out.println(encoded.readData());
    }
}

// +---------------------------+
// |       DataSource          |
// +---------------------------+
// | + writeData(String data)  |
// | + readData(): String      |
// +---------------------------+
//             ^
//             |
// +---------------------------+
// |     FileDataSource        |
// +---------------------------+
// | - name: String            |
// +---------------------------+
// | + FileDataSource(String)  |
// | + writeData(String data)  |
// | + readData(): String      |
// +---------------------------+
//             ^
//             |
// +---------------------------+
// |   DataSourceDecorator     | Implements DataSource
// +---------------------------+
// | - wrapper: DataSource     |
// +---------------------------+
// | + DataSourceDecorator(DataSource) |
// | + writeData(String data)  |
// | + readData(): String      |
// +---------------------------+
//             ^
//             |
// +---------------------------+
// |   EncryptionDecorator     | Extends DataSourceDecorator
// +---------------------------+
// | + EncryptionDecorator(DataSource) |
// | + writeData(String data)  |
// | + readData(): String      |
// | - encode(String data): String     |
// | - decode(String data): String     |
// +---------------------------+
//             ^
//             |
// +---------------------------+
// |   CompressionDecorator    | Extends DataSourceDecorator
// +---------------------------+
// | - compLevel: int          |
// +---------------------------+
// | + CompressionDecorator(DataSource) |
// | + writeData(String data)  |
// | + readData(): String      |
// | + getCompressionLevel(): int       |
// | + setCompressionLevel(int): void   |
// | - compress(String data): String    |
// | - decompress(String data): String  |
// +---------------------------+

