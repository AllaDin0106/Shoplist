package shoplister.core;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import shoplister.models.NewList;

public class Database
{
    private FirebaseDatabase  db;
    public  DatabaseReference products, users, lists, currentUser;

    public Database()
    {
        db = FirebaseDatabase.getInstance();

        users    = db.getReference("users");
        products = db.getReference("products");
        lists    = db.getReference("lists");

        users.child(Instance.currentUser.UID).child("uid").setValue(Instance.currentUser.UID);
        users.child(Instance.currentUser.UID).child("name").setValue(Instance.currentUser.fullName);
        users.child(Instance.currentUser.UID).child("email").setValue(Instance.currentUser.email);
        users.child(Instance.currentUser.UID).child("photo").setValue(Instance.currentUser.photoURL.toString());

        currentUser = users.child(Instance.currentUser.UID);
    }
}
