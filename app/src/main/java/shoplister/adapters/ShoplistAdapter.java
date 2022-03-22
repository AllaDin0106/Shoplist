package shoplister.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import shoplister.R;
import shoplister.core.Instance;
import shoplister.core.Logger;
import shoplister.models.Item;
import shoplister.models.NewList;

public class ShoplistAdapter extends BaseAdapter<Item, ShoplistAdapter.ViewHolder>
{
    boolean titleSet = false;

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        View             itemView;
        ImageView        shoplist_itemIcon;
        TextView         shoplist_itemName;
        CheckBox         shoplist_checkBox;
        ConstraintLayout shoplist_Container;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.itemView           = itemView;
            this.shoplist_itemIcon  = itemView.findViewById(R.id.shoplist_itemIcon);
            this.shoplist_itemName  = itemView.findViewById(R.id.shoplist_itemName);
            this.shoplist_checkBox  = itemView.findViewById(R.id.shoplist_checkBox);
            this.shoplist_Container = itemView.findViewById(R.id.shoplist_Container);
        }
    }

    public ShoplistAdapter(String key) { super(key); }

    @Override
    void initAdapter()
    {
        options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(Instance.database.lists.child(key).child("items"), Item.class).build();

        adapter = new FirebaseRecyclerAdapter<Item, ViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Item model)
            {
                if (!titleSet) setTitle();

                holder.shoplist_itemIcon.setImageResource(model.iconAsResource());
                holder.shoplist_itemName.setText(model.getName());
                holder.shoplist_checkBox.setButtonTintList(Instance.context.getColorStateList(model.foregroundColor(model.getCategory())));
                holder.shoplist_checkBox.setChecked(model.getChecked().equals("1") ? true : false);
                holder.shoplist_Container.setBackgroundResource(model.borderColor(model.getCategory()));

                holder.shoplist_Container.setOnClickListener(v -> {
                    holder.shoplist_checkBox.setChecked(!holder.shoplist_checkBox.isChecked());
                    String isChecked = holder.shoplist_checkBox.isChecked() ? "1" : "0";
                    Instance.database.lists.child(key).child("items").child(model.getName()).child("checked").setValue(isChecked);
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(Instance.context).inflate(R.layout.shoplist_recycleritem, parent, false);
                return new ViewHolder(view);
            }
        };
    }

    private void setTitle()
    {
        Instance.database.lists.child(key).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                ((TextView) Instance.context.findViewById(R.id.shoplist_TitleTextView)).setText(snapshot.getValue(NewList.class).getTitle());
                titleSet = true;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
