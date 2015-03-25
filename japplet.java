package chatapp;


import java.applet.*;



public class Japplet
    extends Applet
{
   
 // This is just a simple applet wrapper class to allow OTF Chat
    // clients to be embedded in HTML documents.

 
   private Jwindow window;

    private String name = new String("");
  
  private String host = new String("");
   
 private String port = new String("");
    
private boolean autoconnect = false;


    
public String getAppletInfo()
    
{
	
return ("OTF Java Chat version 1.2 by Andy McLaughlin");
   
 }
    

   
 public String[][] getParameterInfo()
   
 {
	
String[][] args = {
	    { "username", "mohamed", "User login handle" },

	    { "servername", "bct-java2",

	      "Internet address of OTF Chat server" },
	  
  { "portnumber", "1-65535",
	   
   "TCP port number to use (12468 is default)" },

	    { "autoconnect", "yes/no",
	      "Tells client whether to connect automatically" }
	};
	

	return (args);
  
  }

    
   
 public void init()
    
{
	
// Get the user name, host name, and port from the launching
	// document, if specified.

	this.name = this.getParameter("username");

	this.host = this.getParameter("servername");

	this.port = this.getParameter("portnumber");
	
if (this.getParameter("autoconnect").equals("yes"))

	    this.autoconnect = true;

	
// If "username" is blank, that's OK -- it's probably a deliberate
	// attempt to get the user to enter a unique one later.  However,
	// if it other two values are blank, we'll supply some default ones
	// here

	if ((host == null) || host.equals(""))
	  
  this.host = "bct-java2";
	
if ((port == null) || port.equals(""))
	 
   this.port = "12468";

	// Done
	return;
  
  }


    public void start()
    
{
	
// Launch a window.  It will show up as an unsigned applet window.

	this.window = new Jwindow(this.name, this.host, this.port,
					this.autoconnect, this.getCodeBase());

// Done
	return;
 
   }


    
public void stop()
  
  {
	
return;
 
   }

   
 public void destroy()
    
{
	
// Disconnect from the server if connected
	
if (window.connected == true)
	   
 window.disconnect();
	
window.dispose();
	
return;
    
}
}


/*
  <applet code=OTFapplet width=300 height=300> </applet>
 */



