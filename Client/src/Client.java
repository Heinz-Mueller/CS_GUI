/**
 * Created by Fuse on 26.05.2016.
 */

import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class Client
{
    private int zeigeMenue()
    {
        //Scanner liste eingabe des Benutzers ein
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        int eingabe = -1;
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


    private void erstelleAusgabe(String text)
    {
        String a = text;
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd 'at' HH:mm:ss ");
        Date jetzt = new Date();
        System.out.println(formatter.format(jetzt) + ": " + text);
    }


    public static void main(String ip, int port) throws IOException
    {

        //if(args.length == 2)
        if(port > 1000)
        {
            //Dient zum einlesen der nachricht-eingabe des Benutzers ein
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);

            int eingabe = 1;
            String nachricht = "";
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
                    //Socket server = new Socket(serverip, portnr);
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
                    //Socket server = new Socket(serverip, portnr);
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
}

//Test4