import java.net.*;
import java.util.*;
import java.io.*;


class jclients
{
    protected Jserver myparent;


    public OTFtoclients(Jserver parent)
    {
	myparent = parent;
    }

    public void clientoutput(String s)
    {
	String outputstring = s;

	for (int count = 0; count < myparent.connections.size(); count ++)
	    {
		Jclientsocket c = 
		    ((Jclientsocket) 
		     myparent.connections.elementAt(count));
        
		if (c == null)
		    {   
			myparent.Serveroutput("Error managing clients!\n");
			System.exit(1);
		    }
                
		c.myoutput(outputstring);
	    }
    }
}


class vulture
    extends Thread
{
    protected Jserver myparent;


    public OTFvulture(Jserver parent)
    {
	super("OTF thread vulture");
	myparent = parent;
	this.start();
    }

    public void run()
    {
	int count;
	Jclientsocket temp;

	while(true)
	    {
		for (count = 0; count < myparent.connections.size();
		     count ++)
		    {
			temp = (Jclientsocket) 
			    myparent.connections.elementAt(count);

			if (temp.isAlive() == false)
			    {
				myparent.disconnect(temp, false);
			    }
		    }

		yield();
		try {
		    sleep(1000);
		}
		catch(InterruptedException E) { ; }
	    }
    }
}


public class Jserver
    extends Thread
{
    
    protected int port;
    protected Jserverwindow mywindow;
    protected boolean graphics;
    protected static int DEFAULTPORT = 13456;

    protected ServerSocket serversocket;
    protected Socket socket;
    protected ThreadGroup threadgroup;
    protected vulture thevulture;

    public Vector connections;
    public Vector messages;
    public OTFtoclients thewriter;

    protected File Logfile;
    protected long LogfileSize = 0;
    FileOutputStream Log;


    public Jserver(int askport, boolean isgraphics)
    {
	super("babylon server");

	port = askport;
	this.graphics = isgraphics;

	// if user wants graphics, set up simple window

	if (this.graphics)
	    {
		mywindow = new Jserverwindow(this, 
						   "OTF server window");
		mywindow.setSize(400,400);
		mywindow.setVisible(true);
	    }

	else
	    {
		System.out.println("\n server status");
		System.out.println("Listening on port " + port);
		System.out.println("Connections:");
	    }

	// open up the log file
	try {
	    Logfile = new File("Server.log");
	    LogfileSize = Logfile.length();        
	    Log = new FileOutputStream(Logfile);

	} 
	catch (IOException F) { 
	    this.Serveroutput("Unable to open log file\n"); 
	}

	try {
	    serversocket = new ServerSocket(port);
	} 
	catch (IOException e) {
	    Serveroutput("Couldn't create server socket\n");
	    System.exit(1);
	}

	connections = new Vector();
	messages = new Vector();
	thewriter = new jclients(this);
	threadgroup = new ThreadGroup("Clients");
	thevulture = new vulture(this);

	this.Serveroutput("Reading message file\n");
	this.ReadMessages();

	this.Serveroutput("Waiting for connections\n");
	start();
    }


    public synchronized void disconnect(Jclientsocket who,
					boolean notify)
    {
	int count;

	for (count = 0; count < this.connections.size(); count ++)
	    {
		Jclientsocket temp =
		    ((Jclientsocket) this.connections.elementAt(count));

		if (temp == who)
		    {
			if (notify)
			    {
				// Try to let the user know they're being
				// disconnected 
				temp.myoutput(" You are being disconnected.");
			    }
			
			// Stop the thread for this client
			temp.stop();

			try {
			    temp.mysocket.close();
			} 
			catch (IOException F) {
			    Serveroutput("Couldn't close socket\n");
			}

			this.connections.removeElement(temp);
			this.connections.trimToSize();

			this.Serveroutput("User " + temp.username 
					      + " disconnected\n"); 
			this.Serveroutput("There are " +
					(this.connections.size()) +
					" users connected\n");

			// Tell all the other clients to ditch this user
			thewriter.clientoutput("/REMOVEUSER " + who.username);

			// Get rid of the the client reader
			temp = null;

			try { 
			    this.sleep(250); 
			} 
			catch (InterruptedException I) {;}

			break;
		    }
	    }

	if (this.graphics)
	    {
		this.mywindow.userlist.remove(who.username);
		
		if (this.connections.size() <= 1)
		    this.mywindow.disconnectall.setEnabled(false);
		if (this.connections.size() <= 0)
		    this.mywindow.disconnect.setEnabled(false);
	    }

	return;
    }


    public synchronized void disconnectall(boolean notify)
    {
	int count;

	// Loop backwards through all of the current connections
	for(count = (connections.size() - 1); count >= 0; count --)
	    {
		Jclientsocket temp =
		    (Jclientsocket) this.connections.elementAt(count);

		if (temp == null)
		    continue;

		this.disconnect(temp, notify);
	    }
	return;
    }


    public void run()
    {
	while (true) 
	    {
		try {
		    socket = serversocket.accept();
		} 
		catch (IOException e) { 
		    Serveroutput("Connection error\n");
		    try {
			serversocket.close();
		    } 
		    catch (IOException f) {
			Serveroutput("Couldn't close socket\n");
		    }
		    System.exit(1);
		}

		Serveroutput("Accepting new connection\n");

		if (socket == null)
		    {
			Serveroutput("Server tried to start up NULL socket\n");
			try {
			    serversocket.close();
			} 
			catch (IOException g) {
			    Serveroutput("Couldn't close socket\n");
			}
			System.exit(1);
		    }

		Jclientsocket cs = 
		    new Jclientsocket(this, socket, threadgroup);
		cs.start();

		synchronized (connections) 
		    {
			connections.addElement(cs);
		    }

		if (connections.size() == 0)
		    {
			Serveroutput("Failed to add new client to list\n");
			try {
			    serversocket.close();
			} 
			catch (IOException h) {
			    Serveroutput("Couldn't close socket\n");
			}
			System.exit(1);
		    }
	    }
    }

    protected void Serveroutput(String message) 
    {
	if (this.graphics)
	    this.mywindow.logwindow.append(message);
	else System.out.print(message);

	// now write it to the log

	try {
	    byte[] messagebytes = message.getBytes();
	    this.Log.write(messagebytes);
	    LogfileSize += message.length();
	} 
	catch (IOException F) {
	    if (this.graphics)
		this.mywindow.logwindow.append(
				       "Unable to write to log file\n");
	    else
		System.out.print("Unable to write to log file\n");
	}

	return;
    }

    protected void ReadMessages()
    {
	String amessage;
	int count;
	File messagefile;
	long FileSize;
	byte[] buffer;
	String BufferString;
	FileInputStream themessages;
	String TempFor;
	String TempFrom;
	String TempMessage;

	try {
	    messagefile = new File("Messages.saved");
	    FileSize = messagefile.length();
	    themessages = new FileInputStream(messagefile);
	    
	    // allocate a byte array for the file
	    buffer = new byte[(int) FileSize];

	    // read in the file
	    themessages.read(buffer);

	    // convert to a string
	    BufferString = new String(buffer);
	    
	    // now go through and get the messages
	    for (count = 0; count < BufferString.length(); count ++) 
		{
		    while (BufferString.charAt(count) != '/') 
			count ++;

		    BufferString = BufferString.substring(count);
		    count = 0;
		    if (BufferString.startsWith("/FOR") == false)
			{
			    if (count >= BufferString.length()) continue;
			    this.Serveroutput("Message file is corrupt\n");
			    return;
			}

		    BufferString = BufferString.substring(5);
		    count = 0;
		    TempFor = new String("");
		    while (BufferString.charAt(count) != '/') 
			{
			    TempFor += BufferString.charAt(count);
			    count ++;
			}

		    BufferString = BufferString.substring(count);
		    count = 0;
		    if (BufferString.startsWith("/FROM") == false)
			{
			    if (count >= BufferString.length()) continue;
			    this.Serveroutput("Message file is corrupt\n");
			    return;
			}

		    BufferString = BufferString.substring(6);
		    count = 0;
		    TempFrom = new String("");
		    while (BufferString.charAt(count) != '/') 
			{
			    TempFrom += BufferString.charAt(count);
			    count ++;
			}

		    BufferString = BufferString.substring(count);
		    count = 0;
		    if (BufferString.startsWith("/MESSAGE") == false) 
			{
			    if (count >= BufferString.length()) continue;
			    this.Serveroutput("Message file is corrupt\n");
			    return;
			}
		    BufferString = BufferString.substring(9);
		    count = 0;
		    TempMessage = new String("");
		    while (BufferString.charAt(count) != '/')
			{
			    if (count >= BufferString.length()) continue;
			    TempMessage += BufferString.charAt(count);
			    count ++;
			}
		    BufferString = BufferString.substring(count + 3);
		    count = 0;
		    
		    // System.out.println("For \"" + TempFor + "\"");
		    // System.out.println("From \"" + TempFrom + "\"");
		    // System.out.println("Message \"" + TempMessage 
		    // + "\"");

		    this.messages.addElement(new Jmessage(TempFor,
						TempFrom, TempMessage));
		}
	} 
	catch (IOException E) {
	    return;
	}

	return;
    }

    protected void SaveMessages()
    {
	String amessage;
	int count;
	File messagefile;
	FileOutputStream themessages;

	try {
	    messagefile = new File("Messages.saved");
	    themessages = new FileOutputStream(messagefile);

	    for (count = 0; count < this.messages.size(); count ++)
		{
		    amessage = 
			new String(this.messages.elementAt(count).toString());
		    byte[] miscbyte = amessage.getBytes();
		    themessages.write(miscbyte);
		}

	    themessages.close();
	} 
	catch (IOException E) {
	    this.Serveroutput("Unable to create message file\n"); 
	    return;
	}

	return;
    }

    protected void Shutdown()
    {
	this.Serveroutput(shutting down...\n");

	this.thewriter.clientoutput(
		    " Server is shutting down.");

	if (this.connections.size() > 0)
	    {
		this.Serveroutput("Disconnecting users\n");
		this.disconnectall(true);
	    }
	else this.Serveroutput("No users connected\n");

	// Shut down the vulture
	this.thevulture.stop();

	this.Serveroutput("Saving user messages\n");
	this.SaveMessages();

	this.Serveroutput("Closing log file\n");
	try {
	    this.Log.close();
	} 
	catch (IOException F) { 
	    this.Serveroutput("Unable to close server log file\n"); 
	}

	if (this.graphics)
	    this.mywindow.dispose();

	this.stop();
	System.out.println("");
	System.out.println("OTF server shutdown complete");
	System.exit(0);
    }

    public static void main  (String[] args)
    {
	if (args.length == 0)
	    {
		new Jserver(DEFAULTPORT, true);
		return;
	    }

	else if ((args.length == 1) && 
		 (args[0].equals("-nographics") == false))
	    {
		new Jserver(Integer.parseInt(args[0]), true);
		return;
	    }

	else if ((args.length == 1) && (args[0].equals("-nographics")))
	    {
		new Jserver(DEFAULTPORT, false);    
		return;
	    }


	else if ((args.length == 2) && (args[0].equals("-nographics")))
	    {
		new Jserver(Integer.parseInt(args[1]), false);    
		return;
	    }

	else System.out.println(
			"Usage:  OTFserver [-nographics] [port number]");
	return;
    }
}
