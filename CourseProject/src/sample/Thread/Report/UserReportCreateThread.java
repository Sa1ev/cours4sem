package sample.Thread.Report;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.JFreeChart;
import sample.Methods.AdminSQLMethods;
import sample.Utils.PlotGenerators;
import sample.Thread.ClientThread;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserReportCreateThread extends Thread {
    String path =  System.getProperty("user.dir")+"\\reports\\users\\";
    @Override
    public void run(){
        ClientThread thread = new ClientThread(()-> AdminSQLMethods.getUserStatistics());
        thread.start();
        File file = new File(path);
        file.mkdirs();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date();
        Document document = new Document();
        Font mainFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 18, BaseColor.BLACK);
        try {
            thread.join();
            ArrayList<String[]> result = (ArrayList) thread.result;

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path+"Users Report "+dateFormat.format(date)+".pdf"));
            document.open();

            Paragraph p = new Paragraph("User report", mainFont);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            Font f = new Font();
            f.setSize(14);
            document.add(new Paragraph(" ", f));
            document.add(new Paragraph(String.format("Average time spent by all users: %s.",result.get(0)[0]), f));
            document.add(new Paragraph(String.format("Average distance traveled by all users: %d meters.", Math.round(new Float(result.get(0)[1]))), f));
            document.add(new Paragraph(String.format("The longest ride: Id %s Name: %s Duration %ss. Distance %sm.",
                    result.get(1)[0], result.get(1)[1],result.get(2)[2],result.get(2)[3]), f));
            document.add(new Paragraph(String.format("The shortest ride: Id %s Name: %s Duration %ss. Distance %sm.",
                    result.get(2)[0], result.get(2)[1],result.get(1)[2],result.get(1)[3]), f));
            document.add(new Paragraph(String.format("The user who drove the most: Id %s Name: %s Duration %ss. Distance %sm.",
                    result.get(3)[0], result.get(3)[1],result.get(3)[2],result.get(3)[3]), f));
            document.add(new Paragraph(String.format("The user who drove the most in the last month: Id %s Name: %s Duration %ss. Distance %sm.",
                    result.get(4)[0], result.get(4)[1],result.get(4)[2],result.get(4)[3]), f));
            PdfContentByte pdfContentByte = writer.getDirectContent();
            PdfTemplate pdfTemplateChartHolder = pdfContentByte.createTemplate(300,300);
            Graphics2D graphicsChart = pdfTemplateChartHolder.createGraphics(300,300,new DefaultFontMapper());
            Rectangle2D chartRegion = new Rectangle2D.Double(0,0,300,300);
            JFreeChart chart = PlotGenerators.getBarGraph(new Double(result.get(0)[1]), new Double(result.get(1)[3]),new Double( result.get(2)[3]), "Distance");
            chart.draw(graphicsChart,chartRegion);
            graphicsChart.dispose();
            Image chartImage = Image.getInstance(pdfTemplateChartHolder);
            chartImage.setAlignment(Element.ALIGN_CENTER);
            document.add(chartImage);
            document.close();
        }
        catch (Exception e){
            e.printStackTrace();
            try {
                PdfWriter.getInstance(document, new FileOutputStream("Users Report "+dateFormat.format(date)+".pdf"));
                document.open();
                Paragraph p = new Paragraph("No data", mainFont);
                p.setAlignment(Element.ALIGN_CENTER);
                document.add(p);
                document.close();
            } catch (DocumentException ex) {
                ex.printStackTrace();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }

        }

    }

}
