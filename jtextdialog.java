package chatapp;
import java.awt.*;
import java.awt.event.*;


class Jtextdialog extends Dialog implements ActionListener, KeyListener, WindowListener
{

    private Button dismiss;
    private TextArea thetext;
    private GridBagLayout mylayout;
    private GridBagConstraints myconstraints;


    public Jtextdialog(Frame myparent, String MyLabel, String contents,
			     int columns, int rows, int scrollbars,
			     boolean IsModal)
    {
	super(myparent, MyLabel, IsModal);

	mylayout = new GridBagLayout();
	this.setLayout(mylayout);

	myconstraints = new GridBagConstraints();
	myconstraints.anchor = myconstraints.CENTER;
	myconstraints.insets = new Insets(5,5,5,5);
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;

	thetext = new TextArea(contents, rows, columns, scrollbars);
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.weightx = 1; myconstraints.weighty = 1;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(thetext, myconstraints);
	thetext.setEditable(false);
	thetext.setFont(new Font("Dialog", Font.PLAIN, 12));
	this.add(thetext);

	dismiss = new Button("Dismiss");
	dismiss.addActionListener(this);
	dismiss.addKeyListener(this);
	myconstraints.gridx = 0; myconstraints.gridy = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.fill = myconstraints.NONE;
	mylayout.setConstraints(dismiss, myconstraints);
	this.add(dismiss);

	this.pack();

	// If this window is bigger than the parent window, place it at
	// the same coordinates as the parent.
	if ((myparent.getBounds().width <= this.getSize().width) ||
	    (myparent.getBounds().height <= this.getSize().height))
	    this.setLocation(myparent.getLocation().x,
			     myparent.getLocation().y);
	else
	    // Otherwise, place it centered within the parent window.
	    this.setLocation((((myparent.getBounds().width - 
				this.getSize().width) / 2)
			      + myparent.getLocation().x),
			     (((myparent.getBounds().height - 
				this.getSize().height) / 2)
			      + myparent.getLocation().y));

	addKeyListener(this);
	addWindowListener(this);
	this.setResizable(false);
	this.setVisible(true);
	dismiss.requestFocus();
    }

    public void actionPerformed(ActionEvent E)
    {
	if (E.getSource() == this.dismiss)
	    {
		this.dispose();
		return;
	    }
    }

    public void keyPressed(KeyEvent E)
    {
	if (E.getSource() == this.dismiss)
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
	if ((E.target == dismiss) && ((E.id == Event.ACTION_EVENT) || 
				 (E.key == (char) '\n')))
    */
}

