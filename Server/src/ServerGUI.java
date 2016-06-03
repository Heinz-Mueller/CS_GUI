import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

import java.io.PrintStream;

/**
 * Created by Fuse on 28.05.2016.
 */



public class ServerGUI
{
    private JTextField textField1;
    private JButton starteServerButton;
    private JPanel serverPanel;
    private JTextArea serverAnzeige;

    private PrintStream standardOut;

    public ServerGUI()
    {
        serverAnzeige.setEditable(false);
        PrintStream printStream = new PrintStream(new myOutputStream(serverAnzeige));

        // keeps reference of standard output stream
      //  standardOut = System.out;

        // re-assigns standard output stream and error output stream
        // System.setOut(printStream);
       // System.setErr(printStream);

        //standardOut.println("auf Terminal");



        starteServerButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int port;
                    port = Integer.parseInt(textField1.getText());
                    Server.main(port);
                    serverAnzeige.append("hallop");
                    //JOptionPane.showMessageDialog(null, "Server wurde gestartet!");
                }
                catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "Falsche Eingabe!");
                }
            }
        });
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("ServerGUI");
        frame.setContentPane(new ServerGUI().serverPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
