import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.DateFormat;
public class SimpleClient {
    public static void main(String args[]) {
        //Create the GUI
        JFrame frame = new JFrame();
        frame.setTitle("Client/Server Messenger");
        frame.setSize(600, 300);
        frame.setLocation(100, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton buttonSend = new JButton("Send");
        JLabel labelMessage = new JLabel("");
        JButton buttonExit = new JButton("Exit");
        JTextField textFieldInput = new JTextField("");

        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout(8, 6));
        container.setBackground(Color.lightGray);
        buttonSend.addActionListener(e -> {
            try {
                Socket s1 = new Socket("127.0.0.1", 5432);
                OutputStream s1out = s1.getOutputStream();
                DataOutputStream dos = new DataOutputStream(s1out);
                System.out.println(textFieldInput.getText());
                dos.writeUTF(textFieldInput.getText());
                dos.close();
                s1.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
        frame.add(buttonSend, BorderLayout.WEST);
        frame.add(buttonExit, BorderLayout.EAST);
        frame.add(labelMessage, BorderLayout.CENTER);
        frame.add(textFieldInput, BorderLayout.SOUTH);

        //frame.add(container, BorderLayout.CENTER);
        frame.setVisible(true);

        //Input From Keyboard
        String str;
        DataInputStream indata= new DataInputStream (System.in);

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

                // When done, just close the steam and connection
                dis.close();
                dos.close();
                s1.close();
            } catch (ConnectException connExc) {
                System.err.println("Could not connect to the server.\n" + connExc);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}