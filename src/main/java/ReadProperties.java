import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
    FileInputStream inputStream;
    Properties prop = null;

    public ReadProperties() throws IOException {
        try {
            prop = new Properties();
            String propFilePath = "src/main/resources/api.properties";

            inputStream = new FileInputStream(propFilePath);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFilePath + "' not found in the classpath");
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally { inputStream.close();
        }
    }
    public Properties getProperties(){
        return prop;
    }
}