package chatapp;
class Jmessage
{
    public String isfor;
    public String from;
    public String message;


    public Jmessage(String whofor, String whofrom, String info)
    {
	this.isfor = whofor;
	this.from = whofrom;
	this.message = info;
    }

    public String toString()
    {
	return("/FOR " + this.isfor + "/FROM " + this.from + "/MESSAGE " 
	       + this.message + "/END");
    }

}
