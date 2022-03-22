package shoplister.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import shoplister.R;
import shoplister.core.Instance;
import shoplister.layouts.NewListActivity;
import shoplister.models.Item;

public class ItemAdapter extends BaseAdapter<Item, ItemAdapter.ViewHolder>
{
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public View             itemView;
        public TextView         itemTextView;
        public TextView         itemDescTextView;
        public ImageView        itemImageView;
        public CheckBox         itemCheckBox;
        public ConstraintLayout itemContainer;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.itemView         = itemView;
            this.itemTextView     = itemView.findViewById(R.id.itemTextView);
            this.itemDescTextView = itemView.findViewById(R.id.itemDescriptiontextView);
            this.itemImageView    = itemView.findViewById(R.id.itemImageView);
            this.itemCheckBox     = itemView.findViewById(R.id.item_checkBox);
            this.itemContainer    = itemView.findViewById(R.id.item_recyclerview_container);
        }
    }

    public ItemAdapter(String key) { super(key); }

    @Override
    void initAdapter()
    {
        options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(Instance.database.products.child(key).child("items"), Item.class).build();

        adapter = new FirebaseRecyclerAdapter<Item, ViewHolder>(options)
        {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Item model)
            {
                model.setCategory(key);
                model.checked = "0";

                holder.itemContainer.setBackgroundResource(model.borderColor(model.getCategory()));
                holder.itemCheckBox.setButtonTintList(Instance.context.getColorStateList(model.foregroundColor(model.getCategory())));
                holder.itemTextView.setText(model.getName());
                holder.itemDescTextView.setText(model.getDescription());

                if (holder.itemImageView.getDrawable() == null) holder.itemImageView.setImageResource(model.iconAsResource());

                // Requirement 2: Cookbook Listener #4 - Anonymous Inner Class
                holder.itemContainer.setOnClickListener(v -> {
                    holder.itemCheckBox.setChecked(!holder.itemCheckBox.isChecked());
                    if (holder.itemCheckBox.isChecked()) holder.itemContainer.setBackgroundResource(model.borderColorChecked(model.getCategory()));
                    else holder.itemContainer.setBackgroundResource(model.borderColor(model.getCategory()));

                    if (holder.itemCheckBox.isChecked()) NewListActivity.list.addItem(model);
                    else NewListActivity.list.removeItem(model.getName());
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(Instance.context).inflate(R.layout.item_recycleritem, parent, false);
                return new ViewHolder(view);
            }
        };
    }
}
