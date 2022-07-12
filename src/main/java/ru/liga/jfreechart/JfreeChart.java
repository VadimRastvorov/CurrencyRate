package ru.liga.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.CurrencyRate;
import ru.liga.Curs;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class JfreeChart extends ApplicationFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRate.class);
    private static final long serialVersionUID = 1L;
    private Map<String, List<Curs>> mapListCurs;

    static {
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow",
                true));
    }

    public JfreeChart(String title, Map<String, List<Curs>> mapListCurs) {
        super(title);
        this.mapListCurs = mapListCurs;
        ChartPanel chartPanel = (ChartPanel) createDemoPanel();
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }

    public JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        chart.setPadding(new RectangleInsets(4, 8, 3, 3));
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(600, 300));
        return panel;
    }

    private XYDataset createDataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (Map.Entry entry : mapListCurs.entrySet()) {
            TimeSeries timeSeries = new TimeSeries((String) entry.getKey());
            for (Curs curs : (List<Curs>) entry.getValue()) {
                timeSeries.add(new Day(curs.getLocalDate().getDayOfMonth(),
                        curs.getLocalDate().getMonthValue(),
                        curs.getLocalDate().getYear()), curs.getCurs().doubleValue());
            }
            dataset.addSeries(timeSeries);
        }
        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Курс валюты",  // title
                "",                            // x-axis label
                "Валюта",                      // y-axis label
                dataset,                       // data
                true,                          // create legend
                true,                          // generate tooltips
                false                          // generate URLs
        );

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setDefaultShapesVisible(true);
            renderer.setDefaultShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MM.YYYY"));

        return chart;
    }
}
