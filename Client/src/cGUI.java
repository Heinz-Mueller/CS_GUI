import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.*;


/**
 * Created by Fuse on 28.05.2016.
 */
public class cGUI extends JFrame implements ActionListener, KeyListener, WindowListener
{
    private JTextField ipField;
    private JTextField portField;
    private JPanel clientPanel;
    private JTextArea clientAusgabe;
    private JButton clientButton;
    private JScrollPane scrollFeld;
    public JTextField eingabeFeld;
    public JButton test;
    //private JTextField eingabeFeld;

    // the Client object
    private Client client;

    //
    public boolean aktiv;

    public cGUI()
    {
        JFrame frame = new JFrame("cGUI");
        frame.setContentPane(clientPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        eingabeFeld.addActionListener(this);
        eingabeFeld.addKeyListener(this);

        test.addActionListener(this);

        clientButton.addActionListener(this);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600, 400);
        clientAusgabe.append("Willkommen\n\n");
        //eingabeFeld.requestFocus();

    }


    void append(String text)
    {
        clientAusgabe.append(text);
        clientAusgabe.setCaretPosition(clientAusgabe.getText().length() - 1);
    }

    // called by the GUI is the connection failed
    // we reset our buttons, label, textfield
    void connectionFailed()
    {
        clientButton.setEnabled(true);
//        connected = false;
    }

    public void ausgabe(String text)
    {
        System.out.println("AUSGABE auf terminal?!\n");
        clientAusgabe.append("Ausgabe! in GUI\n");
        clientAusgabe.append(text);
    }

    /*
    * Button or JTextField clicked
    */
    public void actionPerformed(ActionEvent e)
    {
        Object o = e.getSource();
        if(o == clientButton)
        {
            clientAusgabe.append("Verbinde wurde gedrückt\n");
            String ip;
            int port;

            try
            {
                port = Integer.parseInt(portField.getText().trim());
            } catch(Exception er)
            {
                clientAusgabe.append("Fehler bei der Port-Eingabe\n");
                return;
            }

            ip = ipField.getText().trim();

            // try creating a new Client with GUI
            client = new Client(ip, port, this);
            // test if we can start the Client
            if(!client.start())
            {
                return;
            }

            //connected = true;
            //aktiv = true;

            // disable login button
            clientButton.setEnabled(false);
            // disable the Server and Port JTextField
            ipField.setEditable(false);
            portField.setEditable(false);

            // Action listener for when the user enter a message
            //eingabeFeld.addActionListener(this);
            //test.addActionListener(this);
        }
        if(o == test)
        {
            String msg2 = eingabeFeld.getText();
            clientAusgabe.append(msg2 +"\n");
            //aktiv = true;
            client.start();
            //notifyAll();

//            try
//            {
//                System.in.read();
//            } catch (IOException e1)
//            {
//                e1.printStackTrace();
//            }

            //out.println(msg1);
        }
    }


    // KeyListener-Methoden
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            client.start();
            //clientAusgabe.append("ohmann");
            //System.exit(0);
        }
    }

    public void keyReleased(KeyEvent e) {
    }
    public void keyTyped(KeyEvent e) {
    }



    // Window-Listener-Methoden
    public void windowClosing(WindowEvent e)
    {
        System.exit(0);
    }

    public void windowActivated(WindowEvent e) {
    }
    public void windowClosed(WindowEvent e) {
    }
    public void windowDeactivated(WindowEvent e) {
    }
    public void windowDeiconified(WindowEvent e) {
    }
    public void windowIconified(WindowEvent e) {
    }
    public void windowOpened(WindowEvent e) {
    }





    public static void main(String[] args)
    {
        new cGUI();
    }




}
