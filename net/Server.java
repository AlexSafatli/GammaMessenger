/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

/**
 *
 * @author User
 */
import java.io.*;
import java.net.*;

public class Server {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Server myServ = new Server();
        myServ.run();
        // TODO code application logic here
    }
    public ServerSocket mySS;
    public void run() throws Exception{
         mySS = new ServerSocket(1999); // port address
        Socket SS_accept = mySS.accept();
        BufferedReader SS_BF = new BufferedReader(new InputStreamReader(SS_accept.getInputStream()));
        String temp = SS_BF.readLine();
        System.out.println(temp);
        PrintStream SSPS = new PrintStream (SS_accept.getOutputStream());
        SSPS.println("Server : Message to Client");
        
        
    }
}
