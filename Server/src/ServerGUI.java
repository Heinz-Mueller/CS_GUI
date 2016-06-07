import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

import java.io.PrintStream;

/**
 * Created by Fuse on 28.05.2016.
 */



public class ServerGUI extends JFrame implements ActionListener
{
    private JTextField portFeld;
    private JButton starteServerButton;
    private JPanel serverPanel;
    private JTextArea serverAnzeige;

    //private PrintStream standardOut;

    // my server
    private Server server;

    public ServerGUI()
    {
        JFrame frame = new JFrame("ServerGUI");
        frame.setContentPane(serverPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        starteServerButton.addActionListener(this);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600, 400);
        serverAnzeige.append("Willkommen\n\n");
    }

/*
        Für die myOutoutStream Klasse
        PrintStream printStream = new PrintStream(new myOutputStream(serverAnzeige));
        // keeps reference of standard output stream
        standardOut = System.out;

        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        System.setErr(printStream);

*/

    void append(String text)
    {
        serverAnzeige.append(text);
        serverAnzeige.setCaretPosition(serverAnzeige.getText().length() - 1);
    }

    // called by the GUI is the connection failed
    // we reset our buttons, label, textfield
    void connectionFailed()
    {
        starteServerButton.setEnabled(true);
    }

    public void ausgabe(String text)
    {
        System.out.println("AUSGABE auf terminal?!\n");
        serverAnzeige.append("Ausgabe! in GUI\n");
        serverAnzeige.append(text);
    }

    /*
    * Button clicked
    */
    public void actionPerformed(ActionEvent e)
    {

        Object o = e.getSource();

        if(o == starteServerButton)
        {
            serverAnzeige.append("Starte Server wurde gedückt\n");
            int port;

            try
            {
                port = Integer.parseInt(portFeld.getText().trim());
            } catch(Exception er)
            {
                serverAnzeige.append("Fehler bei der Port-Eingabe\n");
                return;
            }

            // neuen Server erzeugen
            server = new Server(port, this);
            // test if we can start the Client
            if(!server.start())
            {
                System.out.println("ohohoh");
                serverAnzeige.append("oh nein");
                return;
            }

            // disable login button
            starteServerButton.setEnabled(false);
            // disable Port JTextField
            portFeld.setEditable(false);
        }
    }

    //server GUI starten
    public static void main(String[] args)
    {
        new ServerGUI();
    }

}
