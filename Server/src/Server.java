import javax.swing.*;
import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;


public class Server
{

    private boolean bearbeiteAnfrage(Socket client, Server newserver) throws IOException
    {
        ServerGUI a = new ServerGUI();

        String nachricht;
        Scanner in  = new Scanner(client.getInputStream() );

        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        boolean ende = true; //gibt an ob der server beendet werden soll


        //Der InputStream kann nicht 2 mal abgefangen werden
        //BufferedReader vonClient = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));

        String line;
        //Pfad abändern, ist nur eine Liste mit Namen
        FileReader fileReader = new FileReader("D:\\IntelliJ_java_projecte\\CS\\namen.txt");
        BufferedReader liste = new BufferedReader(fileReader);

        nachricht = in.nextLine();

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
            out.println("Name ist in Liste.\n");
        }
        else
        {
            out.println("Name ist nicht in der Liste.\n");
        }

        if(nachricht.equals("exit")) //wenn nicht weitegehen soll
        {
            newserver.erstelleAusgabe(nachricht);
            out.println("OK");
            ende = true;
        }
        else
        {
            newserver.erstelleAusgabe(nachricht);
            //a.serverAusgabe(nachricht);
            out.println("ok");
            ende = false;
        }

        liste.close();
        client.close();
        return ende;
    }


    private void erstelleAusgabe(String text)
    {

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd 'at' HH:mm:ss ");
        Date jetzt = new Date();
        System.out.println(formatter.format(jetzt) + ": " + text);
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
            ende = newserver.bearbeiteAnfrage(client, newserver);
        }
        server.close();
        newserver.erstelleAusgabe("Server wurde beendet!");
        System.exit(0);
    }
}