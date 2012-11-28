
package azr;
/**
 * SocketDemo.java
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


import net.rim.device.api.ui.*;

/**
 * This sample enables client/server communication using a simple implementation 
 * of TCP sockets. The client application allows the user to select direct TCP as
 * the connection method.  If direct TCP is not selected, a proxy TCP connection
 * is opened using the BlackBerry MDS Connection Service. The server application 
 * can be found in com/rim/samples/server/socketdemo. 
 */
public class Azr_remoteprompt extends UiApplication
{   
    AzrRP_Screen _screen;
    
    /**
     * Entry point for application.
     * @param args line arguments.
     */
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
    	Azr_remoteprompt app = new Azr_remoteprompt();
        app.enterEventDispatcher();
    }
    
    /**
     * Creates a new SocketDemo object
     */
    public Azr_remoteprompt()
    {
        // Create a new screen for the application.
        _screen = new AzrRP_Screen();        

        // Push the screen onto the UI stack for rendering.
        pushScreen(_screen);
    }    
    
    /**
     * Provides access to this application's UI screen
     * @return This application's UI screen.
     */
    AzrRP_Screen getScreen()
    {
        return _screen;
    }      
}

