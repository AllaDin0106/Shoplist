package shoplister.adapters;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public abstract class BaseAdapter<T, S extends RecyclerView.ViewHolder>
{
    protected FirebaseRecyclerAdapter<T, S> adapter;
    protected FirebaseRecyclerOptions<T>    options;
    protected String key;

    public BaseAdapter()
    {
        initAdapter();
        adapter.startListening();
    }

    public BaseAdapter(String key)
    {
        this.key = key.replace(' ', '-').toLowerCase();
        initAdapter();
        adapter.startListening();
    }

    abstract void initAdapter();

    public FirebaseRecyclerAdapter<T, S> getAdapter() { return adapter; }
}
