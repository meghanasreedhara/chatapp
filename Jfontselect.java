package chatapp;



import java.awt.*;
import java.awt.event.*;


public class Jfontselect extends Dialog implements ActionListener, ItemListener, KeyListener, WindowListener
{
    private GridBagLayout mylayout;
    private GridBagConstraints myconstraints;
    private Jwindow myparent;
    private Font samplefont;
    private Graphics samplegraphic;

    private Panel p1;
    private Label typelabel;
    private Choice type;
    private Label sizelabel;
    private Choice size;
    private Label stylelabel;
    private Checkbox plain;
    private Checkbox bold;
    private Checkbox italics;
    private CheckboxGroup stylegroup;
    private Panel p2;
    private Label samplelabel;
    private OTFsamplecanvas sample;
    private Label textlabel;
    private TextField text;
    private Panel p3;
    private Button ok;
    private Button cancel;


    public Jfontselect(Frame parent)
    {
	super(parent, "Choose font", true);
	myparent = (Jwindow) parent;
	mylayout = new GridBagLayout();
	myconstraints = new GridBagConstraints();
	this.setLayout(mylayout);

	p1 = new Panel();
	p1.setLayout(mylayout);

	myconstraints.insets = new Insets(0,5,0,5);
	myconstraints.anchor = myconstraints.WEST;

	typelabel = new Label("Font type:");
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.fill = myconstraints.NONE;
	mylayout.setConstraints(typelabel, myconstraints);
	p1.add(typelabel);

	sizelabel = new Label("Font size:");
	myconstraints.gridx = 1; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.fill = myconstraints.NONE;
	mylayout.setConstraints(sizelabel, myconstraints);
	p1.add(sizelabel);

	stylelabel = new Label("Font style:");
	myconstraints.gridx = 2; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.fill = myconstraints.NONE;
	mylayout.setConstraints(stylelabel, myconstraints);
	p1.add(stylelabel);

	type = new Choice();
	type.addItemListener(this);
	type.addItem("Arial / Helvetica");
	type.addItem("Times New Roman / Adobe-Times");
	type.addItem("Courier New / Courier");
	type.addItem("MS Sans Serif / Lucida");
	type.addItem("MS Sans Serif II / Lucida Typewriter");
	if (myparent.drawfont.equals("Helvetica")) type.select(0);
	if (myparent.drawfont.equals("TimesRoman")) type.select(1);
	if (myparent.drawfont.equals("Courier")) type.select(2);
	if (myparent.drawfont.equals("Dialog")) type.select(3);
	if (myparent.drawfont.equals("DialogInput")) type.select(4);
	myconstraints.gridx = 0; myconstraints.gridy = 1;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(type, myconstraints);
	p1.add(type);

	size = new Choice();
	size.addItemListener(this);
	size.addItem("10");
	size.addItem("12");
	size.addItem("14");
	size.addItem("18");
	size.addItem("20");
	size.addItem("24");
	size.addItem("36");
	size.addItem("48");
	size.addItem("60");
	size.addItem("72");
	size.addItem("120");
	if (myparent.drawsize == 10) size.select(0);
	if (myparent.drawsize == 12) size.select(1);
	if (myparent.drawsize == 14) size.select(2);
	if (myparent.drawsize == 18) size.select(3);
	if (myparent.drawsize == 20) size.select(4);
	if (myparent.drawsize == 24) size.select(5);
	if (myparent.drawsize == 36) size.select(6);
	if (myparent.drawsize == 48) size.select(7);
	if (myparent.drawsize == 60) size.select(8);
	if (myparent.drawsize == 72) size.select(9);
	if (myparent.drawsize == 120) size.select(10);
	myconstraints.gridx = 1; myconstraints.gridy = 1;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(size, myconstraints);
	p1.add(size);

	stylegroup = new CheckboxGroup();

	plain = new Checkbox("Plain", stylegroup, true);
	plain.addItemListener(this);
	if (myparent.drawstyle == Font.PLAIN) plain.setState(true);
	else plain.setState(false);
	plain.setEnabled(true);
	myconstraints.gridx = 2; myconstraints.gridy = 1;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.fill = myconstraints.NONE;
	mylayout.setConstraints(plain, myconstraints);
	p1.add(plain);

	bold = new Checkbox("Bold", stylegroup, false);
	bold.addItemListener(this);
	if (myparent.drawstyle == Font.BOLD) bold.setState(true);
	else bold.setState(false);
	myconstraints.gridx = 2; myconstraints.gridy = 2;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.fill = myconstraints.NONE;
	mylayout.setConstraints(bold, myconstraints);
	p1.add(bold);

	italics = new Checkbox("Italics", stylegroup, false);
	italics.addItemListener(this);
	if (myparent.drawstyle == Font.ITALIC) italics.setState(true);
	else italics.setState(false);
	myconstraints.gridx = 2; myconstraints.gridy = 3;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.fill = myconstraints.NONE;
	mylayout.setConstraints(italics, myconstraints);
	p1.add(italics);

	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(p1, myconstraints);
	this.add(p1);

	p2 = new Panel();
	p2.setLayout(mylayout);

	samplelabel = new Label("Font sample:");
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(samplelabel, myconstraints);
	p2.add(samplelabel);

	sample = new OTFsamplecanvas(myparent);
	myconstraints.gridx = 0; myconstraints.gridy = 1;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 1;
	myconstraints.anchor = myconstraints.CENTER;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(sample, myconstraints);
	p2.add(sample);

	textlabel = new Label("Text to insert:");
	myconstraints.gridx = 0; myconstraints.gridy = 2;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(textlabel, myconstraints);
	p2.add(textlabel);

	text = new TextField(40);
	text.addKeyListener(this);
	myconstraints.gridx = 0; myconstraints.gridy = 3;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(text, myconstraints);
	p2.add(text);

	myconstraints.gridx = 0; myconstraints.gridy = 1;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(p2, myconstraints);
	this.add(p2);

	p3 = new Panel();
	p3.setLayout(mylayout);

	myconstraints.insets = new Insets(5,5,5,5);

	ok = new Button("Ok");
	ok.addActionListener(this);
	ok.addKeyListener(this);
	myconstraints.gridx = 0; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.anchor = myconstraints.EAST;
	myconstraints.fill = myconstraints.NONE;
	mylayout.setConstraints(ok, myconstraints);
	p3.add(ok);

	cancel = new Button("Cancel");
	cancel.addActionListener(this);
	cancel.addKeyListener(this);
	myconstraints.gridx = 1; myconstraints.gridy = 0;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.weightx = 0; myconstraints.weighty = 0;
	myconstraints.anchor = myconstraints.WEST;
	myconstraints.fill = myconstraints.NONE;
	mylayout.setConstraints(cancel, myconstraints);
	p3.add(cancel);

	myconstraints.gridx = 0; myconstraints.gridy = 2;
	myconstraints.gridheight = 1; myconstraints.gridwidth = 1;
	myconstraints.fill = myconstraints.BOTH;
	mylayout.setConstraints(p3, myconstraints);
	this.add(p3);


	this.setSize(600,600);
	this.pack();
	this.setLocation((((((myparent.getBounds()).width) 
			    - ((this.getSize()).width)) / 2)
			  + ((myparent.getLocation()).x)),
			 (((((myparent.getBounds()).height)
			    - ((this.getSize()).height)) / 2)
			  + ((myparent.getLocation()).y)));

	sample.repaint();
	addKeyListener(this);
	addWindowListener(this);
	this.setResizable(false);
	this.setVisible(true);
	text.requestFocus();
    }

    
    private void draw()
    {
	myparent.drawtext = text.getText();

	// send it out to the socket

	if (myparent.connected)
	    myparent.myclient.outgoing("/FROM " + myparent.name 
		       + " /TO " + myparent.whofor + " /FONT " 
		       + myparent.canvas.colorstring + " " 
		       + myparent.drawfont + " " + myparent.canvas.oldx 
		       + " " + myparent.canvas.oldy + " " + myparent.drawsize
		       + " " + myparent.drawstyle + " " + myparent.drawtext);

	myparent.canvas.drawtext(myparent.canvas.drawcolor, 
				 myparent.drawfont,
				 myparent.canvas.oldx,
				 myparent.canvas.oldy,
				 myparent.drawsize,
				 myparent.drawstyle,
				 myparent.drawtext);
    }

    public void actionPerformed(ActionEvent E)
    {
	if (E.getSource() == this.ok)
	    {
		draw();
		this.dispose();
		return;
	    }
	
	else if (E.getSource() == this.cancel)
	    {
		this.dispose();
		return;
	    }
    }

    public void itemStateChanged(ItemEvent E)
    {
	if (E.getSource() == this.plain)
	    {
		myparent.drawstyle = Font.PLAIN;
		sample.repaint();
		return;
	    }

	else if (E.getSource() == this.bold)
	    {
		myparent.drawstyle = Font.BOLD;
		sample.repaint();
		return;
	    }
	
	else if (E.getSource() == this.italics)
	    {
		myparent.drawstyle = Font.ITALIC;
		sample.repaint();
		return;
	    }

	else if (E.getSource() == this.type)
	    {
		if (type.getSelectedIndex() == 0)
		    myparent.drawfont = "Helvetica";
		if (type.getSelectedIndex() == 1)
		    myparent.drawfont = "TimesRoman";
		if (type.getSelectedIndex() == 2)
		    myparent.drawfont = "Courier";
		if (type.getSelectedIndex() == 3)
		    myparent.drawfont = "Dialog";
		if (type.getSelectedIndex() == 4)
		    myparent.drawfont = "DialogInput";
		sample.repaint();
		return;
	    }

	else if (E.getSource() == this.size)
	    {
		if (size.getSelectedIndex() == 0) myparent.drawsize = 10;
		if (size.getSelectedIndex() == 1) myparent.drawsize = 12;
		if (size.getSelectedIndex() == 2) myparent.drawsize = 14;
		if (size.getSelectedIndex() == 3) myparent.drawsize = 18;
		if (size.getSelectedIndex() == 4) myparent.drawsize = 20;
		if (size.getSelectedIndex() == 5) myparent.drawsize = 24;
		if (size.getSelectedIndex() == 6) myparent.drawsize = 36;
		if (size.getSelectedIndex() == 7) myparent.drawsize = 48;
		if (size.getSelectedIndex() == 8) myparent.drawsize = 60;
		if (size.getSelectedIndex() == 9) myparent.drawsize = 72;
		if (size.getSelectedIndex() == 10) myparent.drawsize = 120;
		sample.repaint();
		return;
	    }
    }

    public void keyPressed(KeyEvent E)
    {
	if (E.getSource() == this.ok)
	    {
		if (E.getKeyCode() == E.VK_ENTER)
		    {
			this.draw();
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

	else if (E.getSource() == this.text)
	    {
		if (E.getKeyCode() == E.VK_ENTER)
		    {
			this.draw();
			this.dispose();
			return;
		    }
		else if (E.getKeyCode() == E.VK_TAB)
		    {
			this.text.transferFocus();
			return;
		    }
		else
		    {
			sample.text = new String(this.text.getText());
			sample.repaint();
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


class OTFsamplecanvas
    extends Canvas
{
    private static final int X = 300;
    private static final int Y = 75;
    
    public String text;
    public Jwindow main;
    
    public OTFsamplecanvas(Jwindow mainwindow)
    {
	super();
	main = mainwindow;
	this.setBackground(Color.lightGray);
	this.setSize(this.X, this.Y);
	this.repaint();
	this.setVisible(true);
	this.text = new String("OTF and on");
    }

    public void paint(Graphics g)
    {
	g.setColor(Color.black);
	g.setFont(new Font(main.drawfont, main.drawstyle, main.drawsize));
	if (main.drawsize > this.Y)
	    g.drawString(this.text, 0, (this.Y - (this.Y / 10)));
	else
	    g.drawString(this.text, 0, main.drawsize);
    }
}

