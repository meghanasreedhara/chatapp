package chatapp;
import java.awt.*;
import java.awt.event.*;


public class Jleavemessage extends Dialog implements ActionListener, KeyListener, WindowListener
{
    protected Jwindow myparent;
    protected Label whofor;
    protected TextField who;
    protected Label goahead;
    protected TextField message;
    protected Button ok;
    protected Button cancel;
    protected Panel p1;
    protected Panel p2;
    protected GridBagLayout mylayout;
    protected GridBagConstraints myconstraints;


    public Jleavemessage(Jwindow parent)
    {
	super(parent, "Leave a message", false);

	myparent = parent;
	mylayout = new GridBagLayout();
	myconstraints = new GridBagConstraints();

	this.setLayout(mylayout);

	myconstraints.insets = new Insets(0,5,0,5);

	p1 = new Panel();
	p1.setLayout(mylayout);

	whofor = new Label("Send to user:");
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.fill = myconstraints.BOTH;
	myconstraints.anchor = myconstraints.WEST;
	mylayout.setConstraints(whofor, myconstraints);
	p1.add(whofor);

	who = new TextField(20);
	who.addKeyListener(this);
	myconstraints.gridx = 0; myconstraints.gridy = 1;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.fill = myconstraints.NONE;
	myconstraints.anchor = myconstraints.WEST;
	mylayout.setConstraints(who, myconstraints);
	p1.add(who);

	goahead = new Label("Message to send:");
	myconstraints.gridx = 0; myconstraints.gridy = 2;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.fill = myconstraints.BOTH;
	myconstraints.anchor = myconstraints.WEST;
	mylayout.setConstraints(goahead, myconstraints);
	p1.add(goahead);

	message = new TextField(60);
	message.addKeyListener(this);
	myconstraints.gridx = 0; myconstraints.gridy = 3;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.fill = myconstraints.BOTH;
	myconstraints.anchor = myconstraints.WEST;
	mylayout.setConstraints(message, myconstraints);
	p1.add(message);

	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.fill = myconstraints.BOTH;
	myconstraints.anchor = myconstraints.CENTER;
	mylayout.setConstraints(p1, myconstraints);
	this.add(p1);

	p2 = new Panel();
	p2.setLayout(mylayout);

	myconstraints.insets = new Insets(5,5,5,5);

	ok = new Button("Ok");
	ok.addActionListener(this);
	ok.addKeyListener(this);
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.fill = myconstraints.NONE;
	myconstraints.anchor = myconstraints.CENTER;
	mylayout.setConstraints(ok, myconstraints);
	p2.add(ok);

	cancel = new Button("Cancel");
	cancel.addActionListener(this);
	cancel.addKeyListener(this);
	myconstraints.gridx = 1; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.fill = myconstraints.NONE;
	myconstraints.anchor = myconstraints.CENTER;
	mylayout.setConstraints(cancel, myconstraints);
	p2.add(cancel);

	myconstraints.gridx = 0; myconstraints.gridy = 1;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.fill = myconstraints.BOTH;
	myconstraints.anchor = myconstraints.CENTER;
	mylayout.setConstraints(p2, myconstraints);
	this.add(p2);

	this.setSize(600,400);
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
	who.requestFocus();
    }


    private void sendMessage()
    {
	if (this.who.getText().equals(""))
	    {
		new Jmessagedialog(myparent, "Need recipient", true,
			 "You must specify a recipient for the message!");
		return;
	    }

	if (myparent.connected == true)
	    {
		this.myparent.myclient.outgoing("/SETMESSAGE " + 
				this.who.getText() + " " + this.myparent.name
				+ " " + this.message.getText());
		return;
	    }
	else
	    {
		new Jmessagedialog(myparent, "Not connected", true,
					 "Must be connected first!");
		return;
	    }
    }


    public void actionPerformed(ActionEvent E)
    {
	if (E.getSource() == this.ok)
	    {
		this.sendMessage();
		this.dispose();
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
	if ((E.getSource() == this.ok) ||
	    (E.getSource() == this.who) ||
	    (E.getSource() == this.message))
	    {
		if (E.getKeyCode() == E.VK_ENTER) 
		    {
			this.sendMessage();
			this.dispose();
			return;
		    }
	    }

	else if (E.getSource() == this.cancel)
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
