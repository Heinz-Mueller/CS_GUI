/**
 * Created by Fuse on 26.05.2016.
 */

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class Client
{
    // for I/O
    private ObjectInputStream sInput;		// to read from the socket
    private ObjectOutputStream sOutput;		// to write on the socket
    private Socket socket;

    // if I use a GUI or not
    private cGUI cg;

    // the server, the port and the username
    private String ip;
    private int port;

    Client()
    {
    }

    /*
    *  Constructor called by console mode
    *  server: the server address
    *  port: the port number
    */
    Client(String ip, int port)
    {
        // which calls the common constructor with the GUI set to null
        this(ip, port, null);
    }

    /*
    * Constructor call when used from a GUI
    * in console mode the ClienGUI parameter is null
    */
    Client(String ip, int port, cGUI cg)
    {
        this.ip = ip;
        this.port = port;
        // save if we are in GUI mode or not
        this.cg = cg;
    }


    private int zeigeMenue() //wird nicht benutzt
    {
        //Scanner liste eingabe des Benutzers ein
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        int eingabe = -1;
        if(cg == null)
        {
            while(eingabe < 0 || eingabe > 2)
            {
                //Auswahlmenue zeigen bis eingabe richtig
                try
                {
                    System.out.println("");
                    System.out.println("---------------------");
                    System.out.println("Menue:");
                    System.out.println("0: Client beenden");
                    System.out.println("1: Namen eingeben");
                    System.out.println("2: Server beenden");
                    System.out.println("---------------------");
                    System.out.print("Was moechten Sie tun?: ");
                    eingabe = Integer.parseInt(br.readLine());
                }
                catch(Exception e)
                {
                    System.out.println("Fehlerhafte Eingabe!");
                }
            }
            return eingabe;
        }
        else
        {
            try
            {
                cg.append("\n");
                cg.append("---------------------\n");
                cg.append("Menue:\n");
                cg.append("0: Client beenden\n");
                cg.append("1: Namen eingeben\n");
                cg.append("2: Server beenden\n");
                cg.append("---------------------\n");
                cg.append("Was moechten Sie tun?: \n");

                eingabe = Integer.parseInt(br.readLine());
                }
                catch(Exception e)
                {
                    System.out.println("Fehlerhafte Eingabe!");
                }
            return eingabe;
        }
    }

    private void erstelleAusgabe(String text)
    {
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd 'at' HH:mm:ss ");
        Date jetzt = new Date();
        //wenn keine GUI dann Terminal Ausgabe
        if(cg == null)
        {
            System.out.println(formatter.format(jetzt) + ": " + text);
        }
        else
        {
            cg.append(formatter.format(jetzt) + ": " + text +"\n");
        }
    }


    private void test(String ip, int port) throws IOException
    {
        //Dient zum einlesen der nachricht-eingabe des Benutzers
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        int eingabe = 1;
        String nachricht;
        Client newclient = new Client(); //Schnittstelle
        Socket server = new Socket(ip, port);

        Scanner in  = new Scanner( server.getInputStream() );
        PrintWriter out = new PrintWriter( server.getOutputStream(), true);

        System.out.println("Namen eingeben: ");
        nachricht = br.readLine();
        out.println(nachricht);

        newclient.erstelleAusgabe(in.nextLine());
        out.close();
        server.close();
    }


    public static void main(String ip, int port) throws IOException //wird in der gui nicht aufgerufen
    {
        //if(args.length == 2)
        if(port > 1000)
        {
            //Dient zum einlesen der nachricht-eingabe des Benutzers
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);

            int eingabe = 1;
            String nachricht;
            Client newclient = new Client(); //Schnittstelle

            //Für IP, zB localhost
            //String serverip = args[0];
            //Für Port
            //int portnr = Integer.parseInt(args[1]);

            //Client mit Server socket verbinden
            //Dem Server eine Nachricht senden
            while( eingabe == 1 )
            {
                eingabe = newclient.zeigeMenue();
                if(eingabe == 1)
                {
                    Socket server = new Socket(ip, port);

                    Scanner in  = new Scanner( server.getInputStream() );
                    PrintWriter out = new PrintWriter( server.getOutputStream(), true);

                    System.out.println("Namen eingeben: ");
                    nachricht = br.readLine();
                    out.println(nachricht);

                    newclient.erstelleAusgabe(in.nextLine());
                    out.close();
                    server.close();
                }
                else if(eingabe == 2)
                {
                    Socket server = new Socket(ip, port);
                    //Schreibkanal zum Server aufbauen
                    PrintWriter out = new PrintWriter(server.getOutputStream(), true);
                    //server soll beendet werden
                    out.println("exit");
                    newclient.erstelleAusgabe("Server wurde beendet!");
                }
            }
            newclient.erstelleAusgabe("Client wurde beendet!");
        }
        else
        {
            System.out.println("Zu viele oder zu wenige Parameter!");
        }
        System.exit(0);
    }

    private void display(String msg)
    {
        if(cg == null)
        {
            System.out.println(msg);      // println in console mode
        }
        else
        {
            cg.append(msg + "\n");		// append to the ClientGUI JTextArea (or whatever)
        }
    }

    public boolean start()
    {
        cg.aktiv = true;
        Thread a = new ListenFromServer();
        a.start();
        // success we inform the caller that it worked
        return true;
    }


    /*
    * a class that waits for the message from the server and append them to the JTextArea
    * if we have a GUI or simply System.out.println() it in console mode
    */
    class ListenFromServer extends Thread
    {
        public void knopf()
        {
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        public void run()
        {
            if(cg.aktiv)
            {
                try
                {
                    cg.append("---------------------\n");
                    cg.append("Suchenamen einegeben\n");
                    cg.append("---------------------\n");

                    Socket server = new Socket(ip, port);

                    Scanner in  = new Scanner( server.getInputStream() );
                    PrintWriter out = new PrintWriter( server.getOutputStream(), true);

                    String msg1 = cg.eingabeFeld.getText();
                    out.println(msg1);

                    erstelleAusgabe(in.nextLine());
                    out.close();
                    server.close();

                    cg.eingabeFeld.setText("");

                    cg.aktiv = false;

                }
                catch(IOException e) {
                    display("Server has close the connection: " + e);
                    if(cg != null)
                        cg.connectionFailed();
                    //break;
                }
                // can't happen with a String object but need the catch anyhow
//                catch(ClassNotFoundException e2) {
//                }
            }
        }
    }

}