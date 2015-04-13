package chatapp;
import java.awt.*;
import java.awt.event.*;


class Jsettings extends Dialog implements ActionListener, KeyListener, WindowListener
{
    private Jwindow myparent;

    private Label namelabel;
    private TextField name;
    private Label locationlabel;
    private TextField location;
    private Label additionallabel;
    private TextArea additional;
    private Label portlabel;
    private Label portlabel2;
    private Label hostlabel;
    private TextField port;
    private TextField host;
    private Panel p1;

    private Button ok;
    private Button cancel;
    private Panel p2;

    private GridBagLayout mylayout;
    private GridBagConstraints myconstraints;


    Jsettings(Frame parent)
    {
	super(parent, "Connection settings", true);

	myparent = (Jwindow) parent;

	mylayout = new GridBagLayout();
	myconstraints = new GridBagConstraints();
	myconstraints.insets = new Insets(0,5,0,5);
	myconstraints.anchor = myconstraints.WEST;
	this.setLayout(mylayout);

	p1 = new Panel();
	p1.setLayout(mylayout);

	// set up the 'name' field

	namelabel = new Label("Your user name (mandatory) : ");
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(namelabel, myconstraints);
	p1.add(namelabel);

	name = new TextField(20);
	name.setText(myparent.name);
	myconstraints.gridx = 1; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(name, myconstraints);
	p1.add(name);

	hostlabel = new Label("Server name (mandatory) :");
	myconstraints.gridx = 0; myconstraints.gridy = 1;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(hostlabel, myconstraints);
	p1.add(hostlabel);

	host = new TextField(20);
	host.setText(myparent.host);
	myconstraints.gridx = 1; myconstraints.gridy = 1;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(host, myconstraints);
	p1.add(host);
	if (myparent.connected) host.setEnabled(false);
	else host.setEnabled(true);

	portlabel = new Label("Network port (mandatory) :");
	myconstraints.gridx = 0; myconstraints.gridy = 2;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(portlabel, myconstraints);
	p1.add(portlabel);

	port = new TextField(20);
	port.setText(myparent.port);
	myconstraints.gridx = 1; myconstraints.gridy = 2;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(port, myconstraints);
	p1.add(port);
	if (myparent.connected) port.setEnabled(false);
	else port.setEnabled(true);

	portlabel2 = new Label("(if you don't know, don't change)");
	myconstraints.gridx = 1; myconstraints.gridy = 3;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(portlabel2, myconstraints);
	p1.add(portlabel2);

	// set up the 'location' field

	locationlabel = new Label("Your location (optional) : ");
	myconstraints.gridx = 0; myconstraints.gridy = 4;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(locationlabel, myconstraints);
	p1.add(locationlabel);

	location = new TextField(20);
	location.setText(myparent.location);
	myconstraints.gridx = 1; myconstraints.gridy = 4;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(location, myconstraints);
	p1.add(location);

	// set up the 'additional info' field

	additionallabel = new Label("Additional info (optional) : ");
	myconstraints.gridx = 0; myconstraints.gridy = 5;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(additionallabel, myconstraints);
	p1.add(additionallabel);

	additional = new TextArea(5,20);
	additional.addKeyListener(this);
	additional.setText(myparent.additional);
	myconstraints.gridx = 1; myconstraints.gridy = 5;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(additional, myconstraints);
	p1.add(additional);

	// set up the panel

	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(p1, myconstraints);
	this.add(p1);

	// set up a panel for the buttons

	p2 = new Panel();
	p2.setLayout(mylayout);
	myconstraints.insets = new Insets(5,5,5,5);

	ok = new Button("Ok");
	ok.addActionListener(this);
	ok.addKeyListener(this);
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(ok, myconstraints);
	p2.add(ok);

	cancel = new Button("Cancel");
	cancel.addActionListener(this);
	cancel.addKeyListener(this);
	myconstraints.gridx = 1; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(cancel, myconstraints);
	p2.add(cancel);

	// set up the panel

	myconstraints.gridx = 0; myconstraints.gridy = 1;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	mylayout.setConstraints(p2, myconstraints);
	this.add(p2);

	this.setSize(200,200);
	this.pack();
	this.setLocation((((((myparent.getBounds()).width)
			    - ((this.getSize()).width)) / 2)
			  + ((myparent.getLocation()).x)),
			 (((((myparent.getBounds()).height)
			    - ((this.getSize()).height)) / 2)
			  + ((myparent.getLocation()).y)));

	addKeyListener(this);
	addWindowListener(this);
	this.setVisible(true);
	name.requestFocus();
    }

    private void setValues()
    {
	myparent.name = name.getText();
	myparent.userid.setText(myparent.name);
	myparent.location = location.getText();
	myparent.port = port.getText();
	myparent.host = host.getText();
	myparent.additional = additional.getText();
    }

    public void actionPerformed(ActionEvent E)
    {
	if (E.getSource() == ok)
	    {
		setValues();
		this.dispose();
		return;
	    }

	else if (E.getSource() == cancel)
	    {
		this.dispose();
		return;
	    }
    }

    public void keyPressed(KeyEvent E)
    {
	if (E.getSource() == additional)
	    {
		if (E.getKeyCode() == E.VK_TAB)
		    additional.transferFocus();
	    }

	if (E.getSource() == ok)
	    {
		if (E.getKeyCode() == E.VK_ENTER)
		    {
			setValues();
			this.dispose();
			return;
		    }
	    }

	if (E.getSource() == cancel)
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

