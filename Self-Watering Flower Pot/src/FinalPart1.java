import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.I2CDevice;
import org.firmata4j.Pin;
import org.firmata4j.IODevice;
import org.firmata4j.ssd1306.SSD1306;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class FinalPart1
{
    static IODevice Arduino;
    static Pin moisture;
    static Pin mosfet;
    static SSD1306 OLED;
    static long value = 0;
    static long wet = 50;
    static long percent;
    static String pump = "OFF";

    static String graphTitle = "Soil Moisture Percentage vs. Time";

    public static void main(String[] args) throws IOException {
        String port = "COM3";
        Arduino = new FirmataDevice(port);

        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final GraphTest chart = new GraphTest(graphTitle);
        frame.add(chart);
        frame.pack();
        frame.setVisible(true);

        try {
            Arduino.start();
            Arduino.ensureInitializationIsDone();
            I2CDevice i2cObject = Arduino.getI2CDevice((byte) 0x3C);
            OLED = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);
            OLED.init();

            moisture = Arduino.getPin(17);
            moisture.setMode(Pin.Mode.ANALOG);
            mosfet = Arduino.getPin(3);
            mosfet.setMode(Pin.Mode.OUTPUT);
            value = moisture.getValue();
            percent = (100 - value*100/1023);

            ArrayList<Long> values = new ArrayList();

            for (int i = 0; i > -1; i++) {
                mosfet.setValue(0);
                value = moisture.getValue();
                percent = (100 - value*100/1023);
                if (i == 0 || i % 50 == 0) {
                    if (percent < wet) {
                        mosfet.setValue(1);
                        pump = "ON";
                        System.out.println("value: " + value);
                    } else {
                        pump = "OFF";
                    }
                }
                OLED.clear();
                OLED.getCanvas().drawString(0, 0, "Percentage: " + percent);
                OLED.getCanvas().drawString(0, 10, "Pump is " + pump);
                OLED.display();
                values.add(percent);
                System.out.println(values);
                chart.update(percent);
                Thread.sleep(500);
            }
        } catch (Exception e) {
            System.out.println("error!");
        } finally {
            Arduino.stop();
        }
    }
}