import org.firmata4j.I2CDevice;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;

import java.io.IOException;

public class UnitTest {

    static IODevice Arduino;
    static Pin sensor;
    static Pin mos;
    static long value = 0;
    public static String BPM = "0";

    public static void main(String[] args) throws IOException {
        String portName = "COM3";
        Arduino  = new FirmataDevice(portName);


        try {
            Arduino.start();
            Arduino.ensureInitializationIsDone();
            sensor = Arduino.getPin(17);
            sensor.setMode(Pin.Mode.ANALOG);
            mos = Arduino.getPin(3);
            mos.setMode(Pin.Mode.OUTPUT);


            while (true) {
                long value = sensor.getValue();
                long percent = (100-value*100/1023);
                if (percent > -1 && percent < 100)
                {
                    System.out.println("percent: "+ percent);
                    System.out.println("value: " + value);
                }
                else if (percent > 100)
                {
                    System.out.println("Error! moisture value is > 100");
                }
                else
                {
                    System.out.println("Error! moisture value is < 0");
                }
                mos.setValue(1);
                Thread.sleep(3000);          //wait for 0.5 seconds
                mos.setValue(0);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        finally {
            Arduino.stop();
        }
    }
}