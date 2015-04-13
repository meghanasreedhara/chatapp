package assgn1;
import java.awt.*;
import java.awt.event.*;
import java.net.*;


class Jserverwindow extends Frame implements ActionListener, WindowListener
{
    protected GridBagLayout mylayout;
    protected GridBagConstraints myconstraints;
    protected Label listening;
    protected List userlist;
    protected Button disconnect;
    protected Button disconnectall;
    protected Button shutdown;
    protected TextArea logwindow;
    protected Jserver myparent;


    public Jserverwindow(Jserver parent, String Name)
    {
	super(Name);
	myparent = parent;

	mylayout = new GridBagLayout();
	myconstraints = new GridBagConstraints();
	this.setLayout(mylayout);

	myconstraints.insets.top = 0; myconstraints.insets.bottom = 0;
	myconstraints.insets.right = 5; myconstraints.insets.left = 5;
	myconstraints.anchor = myconstraints.WEST;
	myconstraints.fill = myconstraints.BOTH;

	listening = new Label("Listening on port " + myparent.port);
	myconstraints.gridwidth = 2; myconstraints.gridheight = 1;
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	mylayout.setConstraints(listening, myconstraints);
	this.add(listening);

	userlist = new List(4, false);
	myconstraints.gridwidth = 1; myconstraints.gridheight = 3;
	myconstraints.gridx = 0; myconstraints.gridy = 1;
	myconstraints.weightx = 1; myconstraints.weighty = 0;
	mylayout.setConstraints(userlist, myconstraints);
	this.add(userlist);

	disconnect = new Button("Disconnect user");
	disconnect.addActionListener(this);
	myconstraints.gridwidth = 1; myconstraints.gridheight = 1;
	myconstraints.gridx = 1; myconstraints.gridy = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	mylayout.setConstraints(disconnect, myconstraints);
	disconnect.setEnabled(false);
	this.add(disconnect);

	disconnectall = new Button("Disconnect all");
	disconnectall.addActionListener(this);
	myconstraints.gridwidth = 1; myconstraints.gridheight = 1;
	myconstraints.gridx = 1; myconstraints.gridy = 2;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	mylayout.setConstraints(disconnectall, myconstraints);
	disconnectall.setEnabled(false);
	this.add(disconnectall);

	shutdown = new Button("Shut down");
	shutdown.addActionListener(this);
	myconstraints.gridwidth = 1; myconstraints.gridheight = 1;
	myconstraints.gridx = 1; myconstraints.gridy = 3;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	mylayout.setConstraints(shutdown, myconstraints);
	shutdown.setEnabled(true);
	this.add(shutdown);

	myconstraints.insets.top = 5; myconstraints.insets.bottom = 5;

	logwindow = new TextArea("server activity log:\n", 20, 40,
				 TextArea.SCROLLBARS_VERTICAL_ONLY);
	logwindow.setEditable(false);
	myconstraints.gridwidth = 2; myconstraints.gridheight = 1;
	myconstraints.gridx = 0; myconstraints.gridy = 4;
	myconstraints.weightx = 1; myconstraints.weighty = 1;
	mylayout.setConstraints(logwindow, myconstraints);
	this.add(logwindow);

	canvas = new OTFpicturecanvas(this);
	myconstraints.gridwidth = 2; myconstraints.gridheight = 1; 
	myconstraints.gridx = 0; myconstraints.gridy = 5;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	mylayout.setConstraints(canvas, myconstraints);
	this.add(canvas);

	try {
	    URL url = new URL ("file", "localhost", "OTFpic.jpg");
	    Image image = getToolkit().getImage(url);

	    this.canvas.setimage(image);
	} 
	catch (Exception e) { 
	    System.out.println(e);
	}

	addWindowListener(this);
    }

    public void actionPerformed(ActionEvent E)
    {
	if (E.getSource() == this.disconnect)
	    {
		String disconnectUser = userlist.getSelectedItem();

		if (disconnectUser != null)
		    {
			// Loop through all of the current connections to find
			// the object that corresponds to this name
			
			for (int count = 0;
			     count < myparent.connections.size();
			     count ++)
			    {
				Jclientsocket tempuser =
				    (Jclientsocket)
				    myparent.connections.elementAt(count);
				
				if (tempuser.username.equals(disconnectUser))
				    myparent.disconnect(tempuser, true);
			    }
		    }
		return;
	    }

	if (E.getSource() == this.disconnectall)
	    {
		myparent.disconnectall(true);
		return;
	    }

	if (E.getSource() == this.shutdown)
	    {
		if (myparent.connections.size() > 0)
		    new OTFservershutdowndialog(this, this.myparent);
		else
		    myparent.Shutdown();
		return;
	    }
    }

    public void windowActivated(WindowEvent E)
    {
    }

    public void windowClosed(WindowEvent E)
    {
    }

    public void windowClosing(WindowEvent E)
    {
	if (myparent.connections.size() > 0)
	    new OTFservershutdowndialog(this, this.myparent);
	else
	    myparent.Shutdown();
    }

    public void windowDeactivated(WindowEvent E)
    {
    }

    public void windowDeiconified(WindowEvent E)
    {
    }

    public void windowIconified(WindowEvent E)
    {
    }

    public void windowOpened(WindowEvent E)
    {
    }
}


class OTFservershutdowndialog extends Dialog implements ActionListener, KeyListener, WindowListener
{
    protected Jserverwindow myparent;
    protected Jserver theserver;

    protected Label message1;
    protected Label message2;
    protected Panel p1;
    protected Button yes;
    protected Button cancel;
    protected Panel p2;
    protected GridBagLayout mylayout;
    protected GridBagConstraints myconstraints;

    public OTFservershutdowndialog(Jserverwindow serverwindow,
				       Jserver server)
    {
	super(serverwindow, "Server shutdown", true);
	myparent = serverwindow;
	theserver = server;

	mylayout = new GridBagLayout();
	myconstraints = new GridBagConstraints();

	this.setLayout(mylayout);

	myconstraints.insets.top = 0; myconstraints.insets.bottom = 0;
	myconstraints.insets.left = 5; myconstraints.insets.right = 5;


	p1 = new Panel();
	p1.setLayout(mylayout);

	message1 = new Label("Are you sure you want to disconnect");
	myconstraints.gridwidth = 1; myconstraints.gridheight = 1;
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.anchor = myconstraints.CENTER;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(message1, myconstraints);
	p1.add(message1);

	message2 = new Label("all users and shut down the server?");
	myconstraints.gridwidth = 1; myconstraints.gridheight = 1;
	myconstraints.gridx = 0; myconstraints.gridy = 1;
	myconstraints.anchor = myconstraints.CENTER;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(message2, myconstraints);
	p1.add(message2);

	myconstraints.gridwidth = 1; myconstraints.gridheight = 1;
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.anchor = myconstraints.CENTER;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(p1, myconstraints);
	this.add(p1);

	p2 = new Panel();
	p2.setLayout(mylayout);

	yes = new Button("Yes");
	yes.addKeyListener(this);
	yes.addActionListener(this);
	myconstraints.gridwidth = 1; myconstraints.gridheight = 1;
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.anchor = myconstraints.CENTER;
	myconstraints.fill = myconstraints.NONE;
	mylayout.setConstraints(yes, myconstraints);
	p2.add(yes);

	cancel = new Button("Cancel");
	cancel.addKeyListener(this);
	cancel.addActionListener(this);
	myconstraints.gridwidth = 1; myconstraints.gridheight = 1;
	myconstraints.gridx = 1; myconstraints.gridy = 0;
	myconstraints.anchor = myconstraints.CENTER;
	myconstraints.fill = myconstraints.NONE;
	mylayout.setConstraints(cancel, myconstraints);
	p2.add(cancel);

	myconstraints.gridwidth = 1; myconstraints.gridheight = 1;
	myconstraints.gridx = 0; myconstraints.gridy = 1;
	myconstraints.anchor = myconstraints.CENTER;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(p2, myconstraints);
	this.add(p2);


	this.setBackground(Color.lightGray);
	this.setSize(500,500);
	this.pack();
	this.setResizable(false);

	this.setLocation((((((myparent.getBounds()).width) - 
			    ((this.getSize()).width)) / 2)
			  + ((myparent.getLocation()).x)),
			 (((((myparent.getBounds()).height) - 
			    ((this.getSize()).height)) / 2)
			  + ((myparent.getLocation()).y)));

	addKeyListener(this);
	addWindowListener(this);
	this.setVisible(true);
	yes.requestFocus();
    }

    public void actionPerformed(ActionEvent E)
    {
	if (E.getSource() == this.yes)
	    {
		this.dispose();
		this.theserver.Shutdown();
		return;
	    }

	else if (E.getSource() == this.cancel)
	    {
		this.dispose();
		return;
	    }
    }

    public void keyPressed(KeyEvent E)
    {
	if (E.getSource() == yes)
	    {
		if (E.getKeyCode() == E.VK_ENTER)
		    {
			this.dispose();
			this.theserver.Shutdown();
			return;
		    }
	    }

	else if (E.getSource() == cancel)
	    {
		if (E.getKeyCode() == E.VK_ENTER)
		    {
			this.dispose();
			return;
		    }
	    }
    }

    public void keyReleased(KeyEvent E)
    {
    }

    public void keyTyped(KeyEvent E)
    {
    }   

    public void windowActivated(WindowEvent E)
    {
    }

    public void windowClosed(WindowEvent E)
    {
    }

    public void windowClosing(WindowEvent E)
    {
	this.dispose();
	return;
    }

    public void windowDeactivated(WindowEvent E)
    {
    }

    public void windowDeiconified(WindowEvent E)
    {
    }

    public void windowIconified(WindowEvent E)
    {
    }

    public void windowOpened(WindowEvent E)
    {
    }
}
}
