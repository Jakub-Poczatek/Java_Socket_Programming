import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
public class SimpleClient {
    public static void main(String args[]) {
        JFrame f = new JFrame();
        Container pane = new Container();
        JLabel message = new JLabel("");
        JButton buttonSend = new JButton("Send");
        JButton buttonExit = new JButton("Exit");
        JTextField textInput = new JTextField("Type here...");
        //Input From Keyboard
        String str;
        DataInputStream indata= new DataInputStream (System.in);

        JPanel p1 = new JPanel(new GridLayout(0, 3));
        p1.add(buttonSend);
        p1.add(message);
        p1.add(buttonExit);
        JPanel p2 = new JPanel(new GridLayout(0, 1));
        p2.add(textInput);

        pane.add(p1, BorderLayout.NORTH);
        pane.add(new JSeparator(), BorderLayout.CENTER);
        pane.add(p2, BorderLayout.SOUTH);
        f.add(p1);
        f.setVisible(true);
        f.pack();

        System.out.println("Type in Something & Press Enter to Send it To The >>S E R V E R<<: ");

        while(true){

            try {

                // Open your connection to a server, at port 5432

                // localhost used here

                Socket s1 = new Socket("127.0.0.1", 5432);

                // Get an input stream from the socket

                InputStream is = s1.getInputStream();

                // Decorate it with a �data� input stream

                DataInputStream dis = new DataInputStream(is);

                // Read the input and print it to the screen

                System.out.println("Incoming From Server>>>:" +dis.readUTF());
                //Display System Date
                DateFormat defaultDate = DateFormat.getDateInstance();
                System.out.println(defaultDate.format(new Date()));
                //Display System Time
                DateFormat shortTime = DateFormat.getTimeInstance(DateFormat.SHORT);
                System.out.println(shortTime.format(new Date()));

                // Get output stream associated with the socket
                OutputStream s1out = s1.getOutputStream();
                DataOutputStream dos = new DataOutputStream(s1out);
                System.out.println();
                System.out.println("Write Something: ");
                str = indata.readLine();
                dos.writeUTF(str);

                message.setText(str);

                // When done, just close the steam and connection
                dis.close();
                dos.close();
                s1.close();
            } catch (ConnectException connExc) {
                System.err.println("Could not connect to the server.");
            } catch (IOException e) {
                // ignore
            }
        }
    }
}