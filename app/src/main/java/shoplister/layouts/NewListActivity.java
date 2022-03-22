package shoplister.layouts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import shoplister.R;
import shoplister.adapters.CategoryAdapter;
import shoplister.core.AppCustomActivity;
import shoplister.core.Instance;
import shoplister.core.Logger;
import shoplister.models.Category;
import shoplister.models.Item;
import shoplister.models.NewList;

public class NewListActivity extends AppCustomActivity implements View.OnClickListener
{
    public static NewList list;

    // Requirement 5: Recyclerview - Parent category
    private RecyclerView                                                  recyclerView;
    private FirebaseRecyclerAdapter<Category, CategoryAdapter.ViewHolder> adapter;

    TextView         newList_sharedTextView;
    EditText         newList_editTextNewList;
    Button           newListSaveButton;
    LinearLayout     newList_addContactWrapper;
    ConstraintLayout newList_Container;

    FragmentManager     FM;
    FragmentTransaction FT;

    // Requirement 2: Cookbook Listener #4 - Implementation in Activity
    // Requirement 6: Store shopping lists in Firebase database
    @Override
    public void onClick(View v)
    {
        list.setTitle(newList_editTextNewList.getText().toString());
        if (isValidList()) { // when its save we go to mainActivity ..
            list.insertToDB();
            Instance.context.startActivity(new Intent(Instance.context, MainActivity.class));
        }
        else Toast.makeText(Instance.context, "Fill in a list name and choose items", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newlist);

        Instance.context = this;

        recyclerView = findViewById(R.id.categoriesRecyclerView);

        adapter = new CategoryAdapter().getAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Instance.context));

        init();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Instance.context = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.list_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.newListSaveOption:
                onClick(item.getActionView());
                break;

            case R.id.newListAddContact:
                loadContactFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init()
    {
        newList_editTextNewList   = findViewById(R.id.newList_editTextNewList);
        newListSaveButton         = findViewById(R.id.newListSaveButton);
        newList_addContactWrapper = findViewById(R.id.newList_addContactWrapper);
        newList_Container         = findViewById(R.id.newList_Container);
        newList_sharedTextView    = findViewById(R.id.newList_sharedTextView);

        newList_editTextNewList.setText(getIntent().getStringExtra("listName"));
        newListSaveButton.setOnClickListener(this);

        newList_addContactWrapper.setOnClickListener(v -> loadContactFragment());
        newList_addContactWrapper.setOnLongClickListener(v -> {
            newList_sharedTextView.setText("Choose a contact to share the list with.");
            list.setRecipient(null);
            return true;
        });

        list = new NewList();

        FM = getSupportFragmentManager();
    }

    private void loadContactFragment()
    {
        FT = FM.beginTransaction();
        FT.add(R.id.newList_Container, new ContactsFragment(), "CONTACTS_FRAGMENT");
        FT.addToBackStack("B");
        FT.commit();
        FM.executePendingTransactions();
    }

    private boolean isValidList()
    {
        boolean valid = true;
        if (list.getTitle().trim().length() == 0) valid = false;
        if (list.getItems().size() == 0) valid = false;
        return valid;
    }
}