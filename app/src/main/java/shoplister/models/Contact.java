package shoplister.models;

public class Contact
{
    private String name;
    private String uid;

    public Contact() { }

    public Contact(String name, String uid)
    {
        this.name = name;
        this.uid  = uid;
    }

    public String getName() { return name; }
    public String getUid()  { return uid; }
}
