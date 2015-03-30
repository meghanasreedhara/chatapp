package chatapp;
import java.io.*;
import java.net.*;


class Clientreader
    extends Thread
{

    protected Socket clientsocket;
    protected OTFclient theclient;
    protected BufferedReader instream;


    public Clientreader(Socket s, OTFclient c)
    {
	super("Client window socket");

	clientsocket = s;
	theclient = c;

	try {
	    instream = new BufferedReader(new InputStreamReader(
				this.clientsocket.getInputStream()));
	}
	catch (IOException a) {
	    return;
	}

	start();
    }

    public void run()
    {
	String line;

	while (true)
	    {
		line = null;

		try {
		    line = instream.readLine();

		    if (line == null)
			continue;
		    if (line.equals(""))
			continue;
		}
		catch (IOException a) {
		    this.theclient.shutdown(true);
		    return;
		}

		theclient.myparent.incoming(line);

		// We do this once every loop, so that we don't suck up
		// too many resources
		yield();
	    }
    }
}


class OTFclient
{
    protected String myhost;
    protected String myname;
    protected String mylocation;
    protected String myadditional;
    protected int myport;
    protected Jwindow myparent;
    protected Socket mysocket;
    protected Clientreader myreader;
    protected PrintWriter outstream;


    public OTFclient(String host, String name, int portnumber,
			 Jwindow mainwindow)
	throws IOException
    {
	myhost = host;
	myname = name;
	myport = portnumber;
	myparent = mainwindow;

	// set up the client socket
	try {
	    this.mysocket = new Socket(myhost, myport);
	}
	catch (UnknownHostException a) {
		    
	    new Jmessagedialog(mainwindow, "Couldn't connect",
			     true, "Couldn't find the server " + host);
	    throw(new IOException());
	}
	catch (IOException b) {
		    
	    new Jmessagedialog(mainwindow, "Couldn't connect", true,
	     "Couldn't connect to port " + portnumber + " on server " + host);
	    throw(new IOException());
	}

	// set up the reader
	myreader = new Clientreader(this.mysocket, this);

	// Get an output stream
	this.outstream =
	    new PrintWriter(this.mysocket.getOutputStream(), true);

	mylocation = myparent.location;
	myadditional = myparent.additional;

	myparent.message.setEnabled(true);
	myparent.readmess.setEnabled(true);
	myparent.page.setEnabled(true);
	myparent.menuleavemess.setEnabled(true);
	myparent.menuviewmess.setEnabled(true);
	myparent.menupage.setEnabled(true);
	myparent.menuviewmess.setEnabled(true);

	outgoing("/NAME " + myname);
	outgoing("/LOCATION " + mylocation);
	outgoing("/ADDITIONAL " + myadditional);
    }


    public void outgoing(String out)
    {
	outstream.println(out);
    }


    public synchronized void shutdown(boolean notify)
    {
	// Shut down the reader thread
	myreader.stop();
	myreader = null;

	// close up my socket
	try {
	    mysocket.close();
	} 
	catch (IOException e) {
	    new Jmessagedialog(myparent, "Communication error",
				     true, "Unable to close the connection");
	}
	
	if (notify)
	    // Make a message to the user
	    new Jmessagedialog(myparent, "Disconnected", true,
				     ("Disconnected from " + myparent.host));
	return;
    }
}
