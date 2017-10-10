package it.fadeout.risckit.business;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class ConfigReader {
	static HashMap<String,String> m_aoProperties;

    private static void loadPropValues() throws IOException {

        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            //inputStream = new ConfigReader().getClass().getClassLoader().getResourceAsStream(propFileName);
            inputStream = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
            //inputStream = new FileInputStream(oCurrentFile.getPath() + "/config.properties");

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            Enumeration<String> aoProperties =  (Enumeration<String>) prop.propertyNames();
            //Clear all
            m_aoProperties.clear();

            String sKey = aoProperties.nextElement();

            while (sKey != null) {
                m_aoProperties.put(sKey, prop.getProperty(sKey));
                if (aoProperties.hasMoreElements()) {
                    sKey = aoProperties.nextElement();
                }
                else  {
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
    }

    public static String getPropValue(String sValue) throws IOException
    {
        if (m_aoProperties == null) {
            m_aoProperties = new HashMap<String,String>();
            loadPropValues();
        }

        return m_aoProperties.get(sValue);
    }
}
