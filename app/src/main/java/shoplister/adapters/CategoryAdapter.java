package shoplister.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import shoplister.R;
import shoplister.core.Instance;
import shoplister.models.Category;
import shoplister.models.Item;

public class CategoryAdapter extends BaseAdapter<Category, CategoryAdapter.ViewHolder>
{
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public View         itemView;
        public RecyclerView itemsRecyclerView;
        public TextView     categoryName;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.itemView          = itemView;
            this.categoryName      = itemView.findViewById(R.id.categoryName);
            // Requirement 5: Recyclerview - Item children
            this.itemsRecyclerView = itemView.findViewById(R.id.itemsRecyclerView);
        }
    }

    @Override
    void initAdapter()
    {
        options = new FirebaseRecyclerOptions
                .Builder<Category>()
                .setQuery(Instance.database.products, Category.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Category, ViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Category model)
            {
                holder.categoryName.setText(model.getName());
                holder.categoryName.setTextColor(Instance.context.getColor(model.foregroundColor(model.asKey(model.getName()))));

                FirebaseRecyclerAdapter<Item, ItemAdapter.ViewHolder> adapter = new ItemAdapter(model.getName()).getAdapter();
                holder.itemsRecyclerView.setAdapter(adapter);
                holder.itemsRecyclerView.setLayoutManager(new GridLayoutManager(Instance.context, 2));
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(Instance.context).inflate(R.layout.category_recycleritem, parent, false);
                return new ViewHolder(view);
            }
        };
    }
}
