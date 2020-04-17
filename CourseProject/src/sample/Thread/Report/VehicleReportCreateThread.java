package sample.Thread.Report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import sample.Methods.AdminSQLMethods;
import sample.Thread.ClientThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VehicleReportCreateThread extends Thread {
    String path =  System.getProperty("user.dir")+"\\reports\\vehicles\\";
    @Override
    public void run(){

        ClientThread thread = new ClientThread(()-> AdminSQLMethods.getVehicleStatistics());
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

            PdfWriter.getInstance(document, new FileOutputStream(path+"Vehicle Report "+dateFormat.format(date)+".pdf"));
            document.open();
            Paragraph p = new Paragraph("Vehicle report", mainFont);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            Font f = new Font();
            f.setSize(14);
            document.add(new Paragraph(" ", f));
            document.add(new Paragraph(String.format("Average time spent by all vehicles: %s.",result.get(0)[0], f)));
            document.add(new Paragraph(String.format("Average distance traveled by all vehicles: %s meters.", result.get(0)[1]), f));
            document.add(new Paragraph(String.format("Number of vehicles that have a driver: %s.",result.get(1)[0]), f));
            document.add(new Paragraph(String.format("Number of vehicles that do not have a driver: %s.",result.get(1)[1]), f));
            document.close();
        }
        catch (Exception e){
            try {
                PdfWriter.getInstance(document, new FileOutputStream("Users Report " + dateFormat.format(date) + ".pdf"));
                document.open();
                Paragraph p = new Paragraph("No data", mainFont);
                p.setAlignment(Element.ALIGN_CENTER);
                document.add(p);
                document.close();
            }
            catch (Exception e1){
                e.printStackTrace();
            }
        }

    }

}
