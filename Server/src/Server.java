import javax.swing.*;
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;


public class Server
{
    // for I/O
    private ObjectInputStream sInput;		// to read from the socket
    private ObjectOutputStream sOutput;		// to write on the socket
    private ServerSocket socket;

    // if I use a GUI or not
    private ServerGUI sg;
    // the port number to listen for connection
    private int port;


    Server()
    {
    }

    /*
    *  Constructor called by console mode
    *  port: the port number
    */
    Server(int port)
    {
        // which calls the common constructor with the GUI set to null
        this(port, null);
    }

    /*
    * Constructor call when used from a GUI
    * in console mode the ClienGUI parameter is null
    */
    Server(int port, ServerGUI sg)
    {
        this.port = port;
        // save if we are in GUI mode or not
        this.sg = sg;
    }


    private boolean bearbeiteAnfrage(Socket client)throws IOException
    {
        String nachricht;
        Scanner in  = new Scanner(client.getInputStream() );

        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        boolean ende = false; //gibt an ob der server beendet werden soll

        //Der InputStream kann nicht 2 mal abgefangen werden
        //BufferedReader vonClient = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));

        String line;
        //Pfad abändern, ist nur eine Liste mit Namen
        FileReader fileReader = new FileReader("D:\\IntelliJ_java_projecte\\CS_GUI\\namen.txt");
        BufferedReader liste = new BufferedReader(fileReader);

        nachricht = in.nextLine();
        String suchName = nachricht;

        boolean istInListe = false;
        while((line = liste.readLine()) != null)
        {
            if( line.matches(nachricht) )
            {
                istInListe = true;
            }
        }

        if(istInListe)
        {
            //schicke Nachricht an Client
            out.println(suchName+ " ist in Liste.\n");
        }
        else
        {
            out.println(suchName+ " ist nicht in der Liste.\n");
        }

        if(nachricht.equals("exit")) //wenn nicht weitegehen soll
        {
            erstelleAusgabe(nachricht);
            out.println("OK");
            ende = true;
        }
        else
        {
            erstelleAusgabe(nachricht);
        }
        liste.close();
        client.close();
        return ende;
    }



    private void erstelleAusgabe(String text)
    {
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd 'at' HH:mm:ss ");
        Date jetzt = new Date();
        //wenn keine GUI dann Terminal Ausgabe
        if(sg == null)
        {
            System.out.println(formatter.format(jetzt) + ": " + text);
        }
        else
        {
            sg.append(formatter.format(jetzt) + ": " + text +"\n");
        }
    }


    public static void main(int port) throws IOException
    {
//        if (args.length != 1)
//        {
//            System.err.println("Usage: java Server <port number>");
//            System.exit(1);
//        }

        //int portnr = Integer.parseInt(args[0]);
        //ServerSocket server = new ServerSocket(portnr);

        ServerSocket server = new ServerSocket(port);

        boolean ende = false;
        Server newserver = new Server(); //Schnittstelle zur "erstelleAusgabe"
        newserver.erstelleAusgabe("Server wurde gestartet!");

        while(!ende)
        {
            //Socket nimmt verbindungen an
            Socket client = server.accept();
            ende = newserver.bearbeiteAnfrage(client);
        }
        server.close();
        newserver.erstelleAusgabe("Server wurde beendet!");
        System.exit(0);
    }


    private void display(String msg)
    {
        if(sg == null)
        {
            System.out.println(msg);      // println in console mode
        }
        else
        {
            sg.append(msg + "\n");		// append to the ClientGUI JTextArea (or whatever)
        }
    }

    public boolean start() //throws IOException
    {
        // creates the Thread
        new ListenFromServer().start();
        // success we inform the caller that it worked
        return true;
    }

    class ListenFromServer extends Thread
    {
        public void run()
        {
            while(true)
            {
                try
                {
                    //ServerSocket server = new ServerSocket(port);
                    socket = new ServerSocket(port);

                    boolean ende = false;
                    erstelleAusgabe("Server wurde gestartet!");

                    while(!ende)
                    {
                        //Socket nimmt verbindungen an
                        Socket client = socket.accept();
                        ende = bearbeiteAnfrage(client);
                    }
                    socket.close();
                    erstelleAusgabe("Server wurde beendet!");
                    System.exit(0);
                }
                catch(IOException e) {
                    display("Server has close the connection: " + e);
                    if(sg != null)
                        sg.connectionFailed();
                    break;
                }
            }
        }
    }



}