
package azr;

import net.rim.device.api.ui.container.MainScreen;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
/*
public final class AzrRP_Screen extends MainScreen
{
 
    public AzrRP_Screen()
    {        
        // Set the displayed title of the screen       
        setTitle("AZR-RemotePrompt");
    }
}
*/

/*
 * SocketDemoScreen.java
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


//import net.rim.device.api.command.*;
import net.rim.device.api.system.*;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;


/**
 * A MainScreen class to allow for user interaction.
 */
public class AzrRP_Screen extends MainScreen
{     
    private EditField _hostField;   
    private static EditField _msg;  
    private CheckboxField _useDirectTcpField;
    private RichTextField _statusField;              
    private StringBuffer _message;
    private boolean _threadRunning = false;   
    
    ButtonField ExecuteButton=new ButtonField("Execute",ButtonField.CONSUME_CLICK);
    ButtonField LockButton=new ButtonField("Lock Windows",ButtonField.CONSUME_CLICK);
    ButtonField ShutdownButton=new ButtonField("Shutdown Windows",ButtonField.CONSUME_CLICK);
    ButtonField HibernateButton=new ButtonField("Hibernate Windows",ButtonField.CONSUME_CLICK);
    
    /**
     * Creates a new SocketDemoScreen object
     */
    public AzrRP_Screen()
    {          
        setTitle(new LabelField("AZR-RemotePrompt"));           
        
        add(new RichTextField("To shutdown server, send \"stop\" command.\nDownload the server for your system: \nhttps://sourceforge.net/projects/azrremoteprompt/files/\nLearn how to run the server: \nhttps://sourceforge.net/p/azrremoteprompt/wiki/Guide/" ,Field.NON_FOCUSABLE));
        add(new SeparatorField());
        
        
        FieldChangeListener listenerExecute=new FieldChangeListener(){
        	public void fieldChanged(Field field, int context){
        		  if (_hostField.getText().length() > 0)
                  {
                      new ConnectThread().start();
                      _threadRunning = true; 
                      
                      // Hide the virtual keyboard so the user can see status updates.
                      if(VirtualKeyboard.isSupported())
                      {
                          VirtualKeyboard keyboard = getVirtualKeyboard();
                          if(keyboard.getVisibility() != VirtualKeyboard.HIDE)
                          {
                              keyboard.setVisibility(VirtualKeyboard.HIDE);
                          }
                      }                                                         
                  }
                  else
                  {
                      Dialog.ask(Dialog.D_OK, "Please enter a valid host name" );
                  }
        	
        	}
	  
        };
        
        
        
        
        FieldChangeListener listenerLock=new FieldChangeListener(){
        	public void fieldChanged(Field field, int context){
        		_msg.setText("rundll32.exe user32.dll,LockWorkStation");
        		 if (_hostField.getText().length() > 0)
                 {
                     new ConnectThread().start();
                     _threadRunning = true; 
                     
                     // Hide the virtual keyboard so the user can see status updates.
                     if(VirtualKeyboard.isSupported())
                     {
                         VirtualKeyboard keyboard = getVirtualKeyboard();
                         if(keyboard.getVisibility() != VirtualKeyboard.HIDE)
                         {
                             keyboard.setVisibility(VirtualKeyboard.HIDE);
                         }
                     }                                                         
                 }
                 else
                 {
                     Dialog.ask(Dialog.D_OK, "Please enter a valid host name" );
                 }
              
        	}
	  
        };
        
        FieldChangeListener listenerShutdown=new FieldChangeListener(){
        	public void fieldChanged(Field field, int context){
        		_msg.setText("shutdown -s -t 00");
        		 if (_hostField.getText().length() > 0)
                 {
                     new ConnectThread().start();
                     _threadRunning = true; 
                     
                     // Hide the virtual keyboard so the user can see status updates.
                     if(VirtualKeyboard.isSupported())
                     {
                         VirtualKeyboard keyboard = getVirtualKeyboard();
                         if(keyboard.getVisibility() != VirtualKeyboard.HIDE)
                         {
                             keyboard.setVisibility(VirtualKeyboard.HIDE);
                         }
                     }                                                         
                 }
                 else
                 {
                     Dialog.ask(Dialog.D_OK, "Please enter a valid host name" );
                 }
              
        	}
	  
        };
        
        FieldChangeListener listenerHibernate=new FieldChangeListener(){
        	public void fieldChanged(Field field, int context){
        		_msg.setText("shutdown -h");
        		 if (_hostField.getText().length() > 0)
                 {
                     new ConnectThread().start();
                     _threadRunning = true; 
                     
                     // Hide the virtual keyboard so the user can see status updates.
                     if(VirtualKeyboard.isSupported())
                     {
                         VirtualKeyboard keyboard = getVirtualKeyboard();
                         if(keyboard.getVisibility() != VirtualKeyboard.HIDE)
                         {
                             keyboard.setVisibility(VirtualKeyboard.HIDE);
                         }
                     }                                                         
                 }
                 else
                 {
                     Dialog.ask(Dialog.D_OK, "Please enter a valid host name" );
                 }
              
        	}
	  
        };
        
        
        
        
        // Need to get the local host name from the user because access to
        // 'localhost' and 127.0.0.1 is restricted.
        _hostField = new EditField("Local Host: " , "");    
        add(_hostField);
        _hostField.setText("192.168.0.");
        _msg= new EditField("Command to execute: ","");
        add(_msg);
        
		ExecuteButton.setChangeListener(listenerExecute);
		LockButton.setChangeListener(listenerLock);
		ShutdownButton.setChangeListener(listenerShutdown);
		HibernateButton.setChangeListener(listenerHibernate);
        add(ExecuteButton);
        add(new SeparatorField());
        add(LockButton);
        add(ShutdownButton);
        add(HibernateButton);
        add(new SeparatorField());
        
        _useDirectTcpField = new CheckboxField("Use Direct TCP" , RadioInfo.getNetworkType() == RadioInfo.NETWORK_IDEN);
        add(_useDirectTcpField);
        _useDirectTcpField.setChecked(true);
        
        _statusField = new RichTextField(Field.NON_FOCUSABLE);    
        add(_statusField);
        
        _message = new StringBuffer();
        
        
    }
    
    /**
    * Method to display a message to the user.
    * @param msg The message to display.
    */
    void updateDisplay(final String msg)
    {
        UiApplication.getUiApplication().invokeLater(new Runnable() 
        {
            public void run()
            {
                _message.append(msg);
                _message.append('\n');
                _statusField.setText(_message.toString());
            }
        });
    }
    
    /**
     * Returns the text entered by the user.
     * @return text entered by the user.
     */
    String getHostFieldText()
    {
        return _hostField.getText();
    }
    
    public static String  getMsg(){
    	return _msg.getText();
    }
    
    /**
     * Indicates whether the direct TCP checkbox is checked.
     * @return True if checkbox is checked, otherwise false.
     */
    boolean isDirectTCP()
    {
        return _useDirectTcpField.getChecked();
    }
    
    /**
     * Setter for boolean _threadRunning
     * @param running True if a ConnectThread is running, otherwise false.
     */
    void setThreadRunning(boolean running)
    {
        _threadRunning = running;
    }
    
   /**
    *@see net.rim.device.api.ui.container.MainScreen#makeMenu(Menu,int)
    */     
   
    

   /**
    * Prevent the save dialog from being displayed, nothing to save.
    * 
    * @see net.rim.device.api.ui.container.MainScreen#onSavePrompt()
    */
    public boolean onSavePrompt()
    {
        return true;
    }   
    
   /**
    * Handles the user pressing ENTER while the 
    * 'use direct tcp' CheckboxField has focus. 
    * 
    * @see net.rim.device.api.ui.Screen#keyChar(char,int,int)
    * 
    */
    protected boolean keyChar( char key, int status, int time )
    {
        if ( key == Characters.ENTER )
        {
            Field fieldWithFocus = getFieldWithFocus(); 
            
            if(fieldWithFocus == _useDirectTcpField)
            {
                    if(_useDirectTcpField.getChecked())
                    {
                            _useDirectTcpField.setChecked(false);
                    }
                    else
                    {
                            _useDirectTcpField.setChecked(true);
                    } 
                                        
                return true; // We've consumed the event.                
            }            
        }
        
        return super.keyChar( key, status, time ); // We'll let super handle the event.
    }
   
   
   /**
    * An anonymous MenuItem class.
    */


public static void setMsg(String newMsg) {
	// TODO Auto-generated method stub
	_msg.setText(newMsg);
}    
}        
      
