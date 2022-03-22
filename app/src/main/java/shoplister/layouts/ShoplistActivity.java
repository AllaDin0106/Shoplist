package shoplister.layouts;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import shoplister.R;
import shoplister.adapters.ShoplistAdapter;
import shoplister.core.AppCustomActivity;
import shoplister.core.Instance;
import shoplister.core.Logger;
import shoplister.dialogs.DeleteShoplist;
import shoplister.models.Item;
import shoplister.models.NewList;

public class ShoplistActivity extends AppCustomActivity
{
    RecyclerView                                              recyclerView;
    FirebaseRecyclerAdapter<Item, ShoplistAdapter.ViewHolder> adapter;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist);

        Instance.context = this;

        key = getIntent().getStringExtra("key");

        recyclerView = findViewById(R.id.shoplist_recyclerView);

        adapter = new ShoplistAdapter(key).getAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Instance.context));
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
        getMenuInflater().inflate(R.menu.shoplist_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        new DeleteShoplist(key).show();
        return super.onOptionsItemSelected(item);
    }
}
