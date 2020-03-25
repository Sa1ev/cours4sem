package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserReportCreateThread extends Thread {
    @Override
    public void run(){
        ClientThread thread = new ClientThread(1132, "User");
        thread.start();
        try {
            thread.join();
            ArrayList<String[]> result = (ArrayList) thread.result;
            Document document = new Document();
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            Date date = new Date();

            PdfWriter.getInstance(document, new FileOutputStream("Users Report "+dateFormat.format(date)+".pdf"));
            document.open();
            Font mainFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 18, BaseColor.BLACK);
            Paragraph p = new Paragraph("User report", mainFont);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            Font f = new Font();
            f.setSize(14);

            document.add(new Paragraph(String.format("Average distance traveled by all users: %s meters.", result.get(0)[0]), f));
            document.add(new Paragraph(String.format("Average time spent by all users: %s seconds.",result.get(0)[1]), f));
            document.add(new Paragraph(String.format("The longest ride: Id%s Name: %s Duration %ss. Distance %sm.",
                    result.get(1)[0], result.get(1)[1],result.get(1)[2],result.get(1)[3]), f));
            document.add(new Paragraph(String.format("The shortest ride: Id%s Name: %s Duration %ss. Distance %sm.",
                    result.get(2)[0], result.get(2)[1],result.get(2)[2],result.get(2)[3]), f));
            document.add(new Paragraph(String.format("The user who drove the most: Id%s Name: %s Duration %ss. Distance %sm.",
                    result.get(3)[0], result.get(3)[1],result.get(3)[2],result.get(3)[3]), f));
            document.add(new Paragraph(String.format("The user who drove the most in the last month: Id%s Name: %s Duration %ss. Distance %sm.",
                    result.get(4)[0], result.get(4)[1],result.get(4)[2],result.get(4)[3]), f));
            document.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

}
