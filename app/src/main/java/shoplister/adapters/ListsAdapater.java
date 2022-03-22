package shoplister.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.HashMap;

import shoplister.R;
import shoplister.core.Instance;
import shoplister.core.Logger;
import shoplister.core.Notifications;
import shoplister.layouts.ShoplistActivity;
import shoplister.models.Contact;
import shoplister.models.Item;
import shoplister.models.NewList;

public class ListsAdapater extends BaseAdapter<NewList, ListsAdapater.ViewHolder>
{
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public View             itemView;
        public TextView         main_listTitleTextView;
        public ConstraintLayout main_listContainer;
        public ImageView        main_listImageView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.itemView               = itemView;
            this.main_listTitleTextView = itemView.findViewById(R.id.main_listTitleTextView);
            this.main_listContainer     = itemView.findViewById(R.id.main_listContainer);
            this.main_listImageView     = itemView.findViewById(R.id.main_listImageView);
        }
    }

    @Override
    void initAdapter()
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin    = 0;
        params.bottomMargin = 0;

        options = new FirebaseRecyclerOptions.Builder<NewList>().setQuery(Instance.database.lists, NewList.class).build();

        adapter = new FirebaseRecyclerAdapter<NewList, ViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull NewList model)
            {
                boolean relevantToMe = false;

                for (Contact contact : model.getContacts().values())
                    if (contact.getUid().equals(Instance.currentUser.UID)) relevantToMe = true;

                if (!relevantToMe) {
                    holder.main_listContainer.setVisibility(View.GONE);
                    holder.main_listContainer.setMaxHeight(0);
                    holder.main_listContainer.setLayoutParams(params);
                    return;
                }

                holder.main_listTitleTextView.setText(model.getTitle());
                holder.main_listImageView.setImageTintList(Instance.context.getColorStateList(Instance.theme));

                holder.main_listContainer.setOnClickListener(v -> {
                    Intent intent = new Intent(Instance.context, ShoplistActivity.class);
                    intent.putExtra("key",getRef(position).getKey());
                    Instance.context.startActivity(intent);
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(Instance.context).inflate(R.layout.list_recycleritem, parent, false);
                return new ViewHolder(view);
            }
        };
    }
}
