package chatapp;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


class OTFtext
{

    public Color color;
    public Font font;
    public int x;
    public int y;
    public String text;

    public OTFtext(Color mycolor, int myx, int myy,
		       Font myfont, String mytext)
    {
	color = mycolor;
	font = myfont;
	text = mytext;
	x = myx;
	y = myy;
    }
}


class OTFline
{

    public Color color;
    public int startx;
    public int starty;
    public int endx;
    public int endy;

    public OTFline(Color mycolor, int mystartx, int mystarty,
		       int myendx, int myendy)
    {
	color = mycolor;
	startx = mystartx;
	starty = mystarty;
	endx = myendx;
	endy = myendy;
    }
}


class OTFrectangle
{

    public Color color;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean fill;

    public OTFrectangle(Color mycolor, int myx, int myy,
			    int mywidth, int myheight, boolean myfill)
    {
	color = mycolor;
	x = myx;
	y = myy;
	width = mywidth;
	height = myheight;
	fill = myfill;
    }
}


class OTFoval
{

    public Color color;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean fill;

    public OTFoval(Color mycolor, int myx, int myy,
		       int mywidth, int myheight, boolean myfill)
    {
	color = mycolor;
	x = myx;
	y = myy;
	width = mywidth;
	height = myheight;
	fill = myfill;
    }
}


public class Jcanvas
    extends Canvas
    implements MouseListener, MouseMotionListener
{
    public static final int FREEHAND = 0;
    public static final int LINE = 1;
    public static final int OVAL = 2;
    public static final int RECTANGLE = 3;
    public static final int TEXT = 4;

    public Color drawcolor;
    public String colorstring;

    public int drawtype = 0;
    public boolean fill = false;
    public Font font;
    public String textstring;

    public Vector graphics;
    private boolean dragging = false;
    public int oldx = 0;
    public int oldy = 0;

    private int ovalwidth;
    private int ovalheight;
    private int rectanglewidth;
    private int rectangleheight;
    private Jwindow myparent;

    protected Image image;


    public Jcanvas(Jwindow parent)
    {
	super();
	myparent = parent;
	graphics = new Vector(1);
	this.setBackground(Color.white);
	this.setForeground(Color.black);
	this.drawcolor = Color.black;
	this.colorstring = new String("black");
	this.setSize(400,125);
	this.setVisible(true);
	addMouseListener(this);
	addMouseMotionListener(this);
    }

    public void setimage(Image theimage)
    {
	image = theimage;
	this.repaint();
    }

    public void paint(Graphics g)
    {

	if (image != null)
	    {
		g.drawImage(image, 0, 0, getSize().width, getSize().height, 
			    this);
	    }


	for (int count = 0; count < graphics.size(); count ++)
	    {

		if (graphics.elementAt(count) instanceof OTFline)
		    {
			OTFline templine = 
			    (OTFline) graphics.elementAt(count);
			g.setColor(templine.color);
			g.drawLine(templine.startx, templine.starty,
				   templine.endx, templine.endy);
		    }

		if (graphics.elementAt(count) instanceof OTFoval)
		    {
			OTFoval tempoval = 
			    (OTFoval) graphics.elementAt(count);
			g.setColor(tempoval.color);

			if (tempoval.fill == false)
			    g.drawOval(tempoval.x, tempoval.y,
				       tempoval.width, tempoval.height);
			else
			    g.fillOval(tempoval.x, tempoval.y,
				       tempoval.width, tempoval.height);

		    }

		if (graphics.elementAt(count) instanceof OTFrectangle)
		    {
			OTFrectangle temprect = 
			    (OTFrectangle) graphics.elementAt(count);
			g.setColor(temprect.color);

			if (temprect.fill == false)
			    g.drawRect(temprect.x, temprect.y,
				       temprect.width, temprect.height);
			else
			    g.fillRect(temprect.x, temprect.y,
				       temprect.width, temprect.height);

		    }

		if (graphics.elementAt(count) instanceof OTFtext)
		    {
			OTFtext temptext =
			    (OTFtext) graphics.elementAt(count);
			g.setColor(temptext.color);
			g.setFont(temptext.font);
			g.drawString(temptext.text, temptext.x, temptext.y);

		    }

	    }

	g.dispose();
    }

    public String makeColorString(Color aColor)
    {
        // set the drawing color string
        if (aColor == Color.black)
	    return (new String("black"));
        else if (aColor == Color.blue)
	    return (new String("blue"));
        else if (aColor == Color.cyan)
	    return (new String("cyan"));
        else if (aColor == Color.darkGray)
	    return (new String("darkGray"));
        else if (aColor == Color.gray)
	    return (new String("gray"));
        else if (aColor == Color.green)
	    return (new String("green"));
        else if (aColor == Color.lightGray)
	    return (new String("lightGray"));
        else if (aColor == Color.magenta)
	    return (new String("magenta"));
        else if (aColor == Color.orange)
	    return (new String("orange"));
        else if (aColor == Color.pink)
	    return (new String("pink"));
        else if (aColor == Color.red)
	    return (new String("red"));
        else if (aColor == Color.white)
	    return (new String("white"));
        else if (aColor == Color.yellow)
	    return (new String("yellow"));

	else
	    return (new String("black"));
    }

    public Color parseColorString(String ColorString)
    {
        if (ColorString.equals("black"))
	    return (Color.black);
        else if (ColorString.equals("blue"))
	    return (Color.blue);
        else if (ColorString.equals("cyan"))
	    return (Color.cyan);
        else if (ColorString.equals("darkGray"))
	    return (Color.darkGray);
        else if (ColorString.equals("gray"))
	    return (Color.gray);
        else if (ColorString.equals("green"))
	    return (Color.green);
        else if (ColorString.equals("lightGray"))
	    return (Color.lightGray);
        else if (ColorString.equals("magenta"))
	    return (Color.magenta);
        else if (ColorString.equals("orange"))
	    return (Color.orange);
        else if (ColorString.equals("pink"))
	    return (Color.pink);
        else if (ColorString.equals("red"))
	    return (Color.red);
        else if (ColorString.equals("white"))
	    return (Color.white);
        else if (ColorString.equals("yellow"))
	    return (Color.yellow);

	else
	    return (Color.black);
    }

    public void drawline(Color color, int startx, int starty, int endx,
			 int endy)
    {
        graphics.addElement(new OTFline(color, startx, starty,
					    endx, endy));
        Graphics g = getGraphics();
        g.setColor(color);
        g.drawLine(startx, starty, endx, endy);
        g.dispose();
    }

    public void drawoval(Color color, int x, int y, int width, int height,
			 boolean filled)
    {
        graphics.addElement(new OTFoval(color, x, y,
					    width, height, filled));
        
        Graphics g = getGraphics();
        g.setColor(color);

        if (filled) g.fillOval(x, y, width, height);
        else g.drawOval(x, y, width, height);

        g.dispose();
    }

    public void drawrect(Color color, int x, int y, int width, int height,
			 boolean filled)
    {
        graphics.addElement(new OTFrectangle(color, x, y, width,
						 height, filled));
        
        Graphics g = getGraphics();
        g.setColor(color);

        if (filled) g.fillRect(x, y, width, height);
        else g.drawRect(x, y, width, height);

        g.dispose();

    }

    public void drawtext(Color color, String font, int x, int y, int size,
			 int style, String text)
    {
	Graphics g = getGraphics();
	Font thefont = new Font(font, style, size);

	graphics.addElement(new OTFtext(color, x, y, thefont, text));
    
	g.setColor(color);
	g.setFont(thefont);
	g.drawString(text, x, y);

	g.dispose();
    }                         

    public void mouseClicked(MouseEvent E)
    {
    }

    public void mouseEntered(MouseEvent E)
    {
	myparent.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }   

    public void mouseExited(MouseEvent E)
    {
	myparent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }   

    public void mousePressed(MouseEvent E)
    {
	oldx = E.getX();
	oldy = E.getY();
    }
	
    public void mouseReleased(MouseEvent E)
    {
	if (this.drawtype == this.LINE)
	    {
		this.drawline(this.drawcolor, oldx, oldy, E.getX(), E.getY());
		
		// send the draw command to the socket

		if (myparent.connected)
		    myparent.myclient.outgoing("/FROM " 
		       + myparent.name + " /TO " + myparent.whofor 
		       + " /LINE " + this.colorstring + " " + oldx 
		       + " " + oldy + " " + E.getX() + " " + E.getY() + " ");

		this.dragging = false;
	    }

	else if (this.drawtype == this.OVAL)
	    {
		if (oldx <= E.getX()) ovalwidth = E.getX() - oldx;
		else ovalwidth = oldx - E.getX();
		if (oldy <= E.getY()) ovalheight = E.getY() - oldy;
		else ovalheight = oldy - E.getY();

		if ((oldx <= E.getX()) && (E.getY() >= oldy))
		    { oldx = oldx; oldy = oldy; }
		else if ((oldx <= E.getX()) && (E.getY() <= oldy))
		    { oldx = oldx; oldy = E.getY(); }
		else if ((oldx >= E.getX()) && (E.getY() >= oldy))
		    { oldx = E.getX(); oldy = oldy; }
		else if ((oldx >= E.getX()) && (E.getY() <= oldy))
		    { oldx = E.getX(); oldy = E.getY(); }

		this.drawoval(this.drawcolor, oldx, oldy, ovalwidth,
			      ovalheight, this.fill);

		if (myparent.connected)
		    myparent.myclient.outgoing("/FROM "
			       + myparent.name + " /TO " + myparent.whofor
			       + " /OVAL " + this.colorstring + " " + oldx
			       + " " + oldy + " " + ovalwidth + " "
			       + ovalheight + " " + this.fill + " ");

		this.dragging = false;
	    }

	else if (this.drawtype == this.RECTANGLE)
	    {
		if (oldx <= E.getX())
		    rectanglewidth = E.getX() - oldx;
		else rectanglewidth = oldx - E.getX();

		if (oldy <= E.getY())
		    rectangleheight = E.getY() - oldy;
		else rectangleheight = oldy - E.getY();

		if ((oldx <= E.getX()) && (E.getY() >= oldy))
		    { oldx = oldx; oldy = oldy; }
		else if ((oldx <= E.getX()) && (E.getY() <= oldy))
		    { oldx = oldx; oldy = E.getY(); }
		else if ((oldx >= E.getX()) && (E.getY() >= oldy))
		    { oldx = E.getX(); oldy = oldy; }
		else if ((oldx >= E.getX()) && (E.getY() <= oldy))
		    { oldx = E.getX(); oldy = E.getY(); }

		this.drawrect(this.drawcolor, oldx, oldy,
			      rectanglewidth, rectangleheight,
			      this.fill);

		if (myparent.connected)
		    myparent.myclient.outgoing("/FROM "
			       + myparent.name + " /TO " + myparent.whofor
			       + " /RECT " + this.colorstring + " " + oldx
			       + " " + oldy + " " + rectanglewidth + " "
			       + rectangleheight + " " + this.fill + " ");

		this.dragging = false;
	    }

	else if (this.drawtype == this.TEXT)
	    {
		oldx = E.getX();
		oldy = E.getY();

		new Jfontselect(myparent);
	    }
    }   

    public void mouseDragged(MouseEvent E)
    {
	if (this.drawtype == this.FREEHAND)
	    {
		this.drawline(this.drawcolor, oldx, oldy, E.getX(), E.getY());
	
		// send the draw command to the socket
		if (myparent.connected)
		    myparent.myclient.outgoing("/FROM " 
		       + myparent.name + " /TO " + myparent.whofor
		       + " /LINE " + this.colorstring + " " + oldx 
		       + " " + oldy + " " + E.getX() + " " + E.getY() + " ");
		oldx = E.getX();
		oldy = E.getY();
	    }

	else
	    dragging = true;
    }

    public void mouseMoved(MouseEvent E)
    {
    }   
}

