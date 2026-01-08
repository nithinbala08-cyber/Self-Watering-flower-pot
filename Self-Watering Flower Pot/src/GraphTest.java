import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.text.SimpleDateFormat;



public class GraphTest extends JPanel {

    private DynamicTimeSeriesCollection dataset;
    private JFreeChart chart;
    private XYSeries series;

    static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    public GraphTest(String title) {
        series = new XYSeries("Data");

        // Create a dataset and add the series to it
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        chart = ChartFactory.createTimeSeriesChart(title, "Time", "Moisture", dataset, true, true, false);
        final XYPlot plot = chart.getXYPlot();
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        DateAxis axis = (DateAxis)plot.getDomainAxis();
        axis.setFixedAutoRange(10000);
        axis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss.SS"));
        range.setRange(0, 100);
        final ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);
    }
    public void update(float value) {
        series.add((double)System.currentTimeMillis(), (double)value);
    }
}