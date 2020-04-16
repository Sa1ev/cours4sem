package sample.Thread;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBListener extends Thread {

    private Connection conn;
    private org.postgresql.PGConnection pgconn;

    public DBListener(Connection conn) throws SQLException {
        this.conn = conn;
        this.pgconn = (org.postgresql.PGConnection)conn;
        Statement stmt = conn.createStatement();
        stmt.execute("LISTEN \"myNotify\"");
        stmt.close();
    }

    public void run() {
        while (true) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT 1");
                rs.next();
                rs.close();
                stmt.close();

                org.postgresql.PGNotification notifications[] = pgconn.getNotifications();
                if (notifications != null) {
                    for (int i=0; i<notifications.length; i++) {
                        new UILoadThread(null).start();
                    }
                }
                Thread.sleep(100);

                // wait a while before checking again for new
                // notifications

            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

}
