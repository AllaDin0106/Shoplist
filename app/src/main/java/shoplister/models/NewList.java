package shoplister.models;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.UUID;

import shoplister.core.Instance;
import shoplister.core.Logger;

public class NewList
{
    String                   key;
    String                   title;
    HashMap<String, Contact> contacts = new HashMap<>();
    HashMap<String, Item>    items    = new HashMap<>();

    Contact sender, recipient;

    public void addContact(Contact contact)       {}
    public void addItem(Item item)                { items.put(item.getName(), item); }
    public void removeContact(String contact)     {}
    public void removeItem(String item)           { if (items.get(item) != null) items.remove(item); }
    public void setTitle(String title)            { this.title = title; }
    public void setSender(Contact sender)         { this.sender = sender; }
    public void setRecipient(Contact recipient)   { this.recipient = recipient; }

    public String getTitle()                      { return title; }

    public HashMap<String, Item> getItems()       { return items; }
    public HashMap<String, Contact> getContacts() { return contacts; }

    // Requirement 6: Store shopping lists in Firebase database
    public void insertToDB()
    {
        key    = UUID.randomUUID().toString();
        sender = new Contact(Instance.currentUser.fullName, Instance.currentUser.UID);

        if (recipient != null) contacts.put(recipient.getUid(), recipient);
        contacts.put(sender.getUid(), sender);

        DatabaseReference list = Instance.database.lists.child(key);
        list.child("contacts").setValue(contacts);
        list.child("title").setValue(title);
        list.child("items").setValue(items);
    }
}
