/*
 * ConnectThread.java
 *
 * Copyright © 1998-2011 Research In Motion Ltd.
 * 
 * Note: For the sake of simplicity, this sample application may not leverage
 * resource bundles and resource strings.  However, it is STRONGLY recommended
 * that application developers make use of the localization features available
 * within the BlackBerry development platform to ensure a seamless application
 * experience across a variety of languages and geographies.  For more information
 * on localizing your application, please refer to the BlackBerry Java Development
 * Environment Development Guide associated with this release.
 */

package azr;

import java.io.*;
import javax.microedition.io.*;
import net.rim.device.api.ui.*;

/**
 * A thread class to handle communication with the server component.
 */
public class ConnectThread extends Thread
{   
    private InputStream _in;
    private OutputStreamWriter _out;        
    private AzrRP_Screen _screen;   
    
    /**
     * Creates a new ConnectThread object
     */
    public ConnectThread()
    {
        _screen = ((Azr_remoteprompt)UiApplication.getUiApplication()).getScreen(); 
    }
    
   /**
    * Pass some data to the server and wait for a response.
    * @param data The data to send.
    */
    private void send(String data) throws IOException
    {
        // Cache the length locally for better efficiency.
        int length = data.length();
        
        // Create an input array just big enough to hold the data
        // (we're expecting the same string back that we send).
       //char[] input = new char[length];
        _out.write(data, 0, length);
/*		
        // Read character by character into the input array.
        for (int i = 0; i < length; ++i) 
        {
            input[i] = (char)_in.read();
        }        

        // Hand the data to the parent class for updating the GUI. By explicitly 
        // creating the stringbuffer we can save a few object creations.
        StringBuffer s = new StringBuffer();
        s.append("Received: ") ;
        s.append(input, 0, length);
        _screen.updateDisplay(s.toString());*/
        
    }

    
    
    
   /**
    * Implementation of Thread.
    */
    public void run()
    {        
        StreamConnection connection = null;       
        
        try
        {
            _screen.updateDisplay("Opening Connection...");
            String url = "socket://" + _screen.getHostFieldText() + ":44444;interface=wifi" + (_screen.isDirectTCP() ? ";deviceside=true" : "");                                    
            connection = (StreamConnection)Connector.open(url);
            _screen.updateDisplay("Connection with "+ _screen.getHostFieldText()+ " established.");
            
            _in = connection.openInputStream();        
            _out = new OutputStreamWriter(connection.openOutputStream());            
            
            // Send the HELLO string.
            //send("Hello from BlackBerry 9860.");
            //send("Hello from BlackBerry 9860.");
            send(AzrRP_Screen.getMsg());
            
            

            // Execute further data exchange here...

            //send("Bye");
            
            
            
            
            _screen.updateDisplay("Done!");
        }
        catch(IOException e)
        {
            System.err.println(e.toString());
        }
        finally
        {              
            _screen.setThreadRunning(false);
            
            try
            {               
                _in.close();             
            }
            catch(IOException ioe)
            {                
            }
            try
            {       
                _out.close();  
                AzrRP_Screen.setMsg(null);
                
            }
            catch(IOException ioe)
            {               
            }
            try
            {               
                connection.close();
                
            }
            catch(IOException ioe)
            {                
            }
        }
    }
}
