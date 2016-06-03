import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

/**
 * Created by Fuse on 28.05.2016.
 */
public class cGUI extends JFrame implements ActionListener
{
    private JTextField ipField;
    private JTextField portField;
    private JPanel clientPanel;
    private JTextArea clientAusgabe;
    private JButton clientButton;

    private PrintStream standardOut;



    // the Client object
    private Client client;

    public cGUI()
    {
        JFrame frame = new JFrame("cGUI");
        frame.setContentPane(clientPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientButton.addActionListener(this);
        frame.pack();
        frame.setVisible(true);
        clientAusgabe.append("Willkommen\n\n");

        //clientAusgabe.setEditable(false);
        //PrintStream printStream = new PrintStream(new myOutputStream(clientAusgabe));

        // keeps reference of standard output stream
        //standardOut = System.out;

        // re-assigns standard output stream and error output stream
        //System.setOut(printStream);
        //System.setErr(printStream);


        //System.out.println("ist gen");
        //standardOut.println("auf Terminal");




//        clientButton.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                try
//                {
//                    // disable login button
//                    //clientButton.setEnabled(false);
//
//                    //clientAusgabe.append("TEST");
//                    String ip;
//                    int port2;
//                    port2 = Integer.parseInt(portField.getText());
//                    ip = ipField.getText();
//                    Client.main(ip, port2);
//                }
//                catch (Exception e1){
//                    JOptionPane.showMessageDialog(null, "Falsche Eingabe!");
//                }
//            }
//        });
    }


    void append(String text)
    {
        System.out.println("append\n");
        clientAusgabe.append(text);
        clientAusgabe.append("append-Methode\n");
        clientAusgabe.setCaretPosition(clientAusgabe.getText().length() - 1);
    }

    // called by the GUI is the connection failed
    // we reset our buttons, label, textfield
    void connectionFailed()
    {
        clientButton.setEnabled(true);
        // reset port number and host name as a construction time
//        tfPort.setText("" + defaultPort);
//        tfServer.setText(defaultHost);
//        // let the user change them
//        tfServer.setEditable(false);
//        tfPort.setEditable(false);
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
        // ok it is coming from the JTextField
//        if()
//        {
//            // just have to send the message
//            client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, tf.getText()));
//            tf.setText("");
//            return;
//        }

        if(o == clientButton)
        {
            clientAusgabe.append("Verbinde wurde gedrückt\n");
            String ip;
            int port2;
            port2 = Integer.parseInt(portField.getText());
            ip = ipField.getText();

            // try creating a new Client with GUI
            client = new Client(ip, port2, this);
            // test if we can start the Client
            if(!client.start())
            {
                return;
            }

            //connected = true;

            // disable login button
            //clientButton.setEnabled(false);
            // disable the Server and Port JTextField
            //ipField.setEditable(false);
            //portField.setEditable(false);
            // Action listener for when the user enter a message
            //eingabeFeld.addActionListener(this);
        }
    }


    public static void main(String[] args)
    {
        new cGUI();
//        JFrame frame = new JFrame("cGUI");
//        frame.setContentPane(new cGUI().clientPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
    }

}
