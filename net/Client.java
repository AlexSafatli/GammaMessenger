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
public class Client {

    public static void main(String[] args)throws IOException {
       Client myCli = new Client();
       myCli.run();
       
    
    }
    public void run()throws IOException{
        Socket mySkt = new Socket("127.0.0.1",1999);// port address
        PrintStream myPS = new PrintStream(mySkt.getOutputStream());
        myPS.println(" Client : Message to Server");
        BufferedReader myBR = new BufferedReader(new InputStreamReader(mySkt.getInputStream()));
        String temp = myBR.readLine();
        System.out.println(temp);
        
        
    }
    
}
