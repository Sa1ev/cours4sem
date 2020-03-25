package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VehicleReportCreateThread extends Thread {
    @Override
    public void run(){

        ClientThread thread = new ClientThread(1132, "Vehicle");
        thread.start();
        try {
            thread.join();
            ArrayList<String[]> result = (ArrayList) thread.result;
            Document document = new Document();
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date date = new Date();

            PdfWriter.getInstance(document, new FileOutputStream("Vehicle Report "+dateFormat.format(date)+".pdf"));
            document.open();
            Font mainFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 18, BaseColor.BLACK);
            Paragraph p = new Paragraph("Vehicle report", mainFont);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            Font f = new Font();
            f.setSize(14);

            document.add(new Paragraph(String.format("Average distance traveled by all users: %s meters.", result.get(0)[0]), f));
            document.add(new Paragraph(String.format("Average time spent by all users: %s seconds.",result.get(0)[1]), f));
            document.add(new Paragraph(String.format("Number of vehicles that have a driver: %s.",result.get(1)[0]), f));
            document.add(new Paragraph(String.format("Number of vehicles that do not have a driver: %s.",result.get(1)[1]), f));
            document.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
