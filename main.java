package chatapp;
import java.net.URL;
public class Main extends Object implements Runnable
{
    public static void main(String[] args)
    {
	Main firstinstance = new Main();
	firstinstance.run();
	return;
    }

    public Main()
    {
	try {
	    URL myURL = new URL("file", "localhost", "./");

	    // Pass some default settings.
	    new Jwindow("", "localhost", "12468", false, myURL);
	}
	catch (Exception E) {
	    System.out.println(E);
	}
    }

    public void run()
    {
	// Nothing to do here.
	return;
    }
}


