package chatapp;
import java.awt.*;
import java.awt.event.*;


class Jmessagedialog
    extends Dialog
    implements ActionListener, KeyListener, WindowListener
{
    protected Jwindow myparent;

    protected Label message;
    protected Button ok;
    protected GridBagLayout mylayout;
    protected GridBagConstraints myconstraints;


    public Jmessagedialog(Jwindow parent, String TheTitle,
				boolean IsModal, String TheMessage)
    {
	super(parent, TheTitle, IsModal);

	myparent = parent;

	mylayout = new GridBagLayout();
	myconstraints = new GridBagConstraints();

	this.setLayout(mylayout);

	myconstraints.insets.top = 5; myconstraints.insets.bottom = 5;
	myconstraints.insets.left = 5; myconstraints.insets.right = 5;

	message = new Label(TheMessage);
	myconstraints.gridwidth = 1; myconstraints.gridheight = 1;
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.anchor = myconstraints.CENTER;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(message, myconstraints);
	this.add(message);

	ok = new Button("Ok");
	ok.addActionListener(this);
	ok.addKeyListener(this);
	myconstraints.gridwidth = 1; myconstraints.gridheight = 1;
	myconstraints.gridx = 0; myconstraints.gridy = 1;
	myconstraints.anchor = myconstraints.CENTER;
	myconstraints.fill = myconstraints.NONE;
	mylayout.setConstraints(ok, myconstraints);
	this.add(ok);

	this.setBackground(Color.lightGray);
	this.setSize(500,500);
	this.pack();
	this.setResizable(false);

	this.setLocation((((((myparent.getBounds()).width) 
			    - ((this.getSize()).width)) / 2)
			  + ((myparent.getLocation()).x)),
			 (((((myparent.getBounds()).height) 
			    - ((this.getSize()).height)) / 2)
			  + ((myparent.getLocation()).y)));

	addKeyListener(this);
	addWindowListener(this);
	this.setVisible(true);
	ok.requestFocus();
    }

    public void actionPerformed(ActionEvent E)
    {
	if (E.getSource() == this.ok)
	    {
		this.dispose();
		return;
	    }
    }

    public void keyPressed(KeyEvent E)
    {
	if (E.getSource() == this.ok)
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
	this.dispose();
	return;
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

    /*
	if ((E.target == this.ok) && ((E.id == Event.ACTION_EVENT) ||
				      (E.key == (char) '\n')))
    */
}
