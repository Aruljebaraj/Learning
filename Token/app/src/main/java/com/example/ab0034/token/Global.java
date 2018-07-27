package com.example.ab0034.token;

public class Global {
    String Name = "NA";
    String Contact = "NA";

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }


    public String getContact() {
        return Contact;
    }

    public void setContact(String Contact) {
        this.Contact = Contact;
    }


    private static Global instance;

    static {
        instance = new Global();
    }

    private Global() {
    }

    public static Global getInstance() {
        return Global.instance;
    }

}
