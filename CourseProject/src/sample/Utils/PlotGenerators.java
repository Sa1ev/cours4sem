package sample.Utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;

import java.awt.*;

public class PlotGenerators {
    public static JFreeChart getBarGraph(double avgTime, double minTime, double maxTime, String title){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(minTime, "Minimum", "");
        dataset.addValue(avgTime, "Average", "");
        dataset.addValue(maxTime, "Maximum", "");
        JFreeChart chart = ChartFactory.createBarChart(
                 title    ,
                "Category",
                null      ,
                 dataset   ,
                 PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint (new Color(239, 239, 255));
        return chart;
    }
    public static JFreeChart getPieGraph(int withDriver, int withoutDriver, String title){
        DefaultPieDataset dataset = new DefaultPieDataset ();
        dataset.setValue( "With driver", withDriver);
        dataset.setValue("Without driver", withoutDriver);
        JFreeChart chart= ChartFactory.createPieChart(
                title,  // chart title
                dataset,             // data
                false,               // no legend
                true,                // tooltips
                false                // no URL generation
        );
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint (new Color(239, 239, 255));
        return chart;
    }
}
