package com.androidtuts4u.arun.registartionapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidtuts4u.arun.registartionapp.R;
import com.androidtuts4u.arun.registartionapp.UpdateActivity;
import com.androidtuts4u.arun.registartionapp.database.Entity;
import com.androidtuts4u.arun.registartionapp.database.MyAppDatabase;
import com.androidtuts4u.arun.registartionapp.ui.home.HomeFragment;

import java.util.HashMap;
import java.util.List;

import static com.androidtuts4u.arun.registartionapp.ui.home.HomeFragment.parent_id;

/**
 * Created by arun on 23-08-2017.
 */

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.UserViewHolder> {

    List<Entity> nodeList;
    Context context;
    int expanded_id = -1;
    public HashMap<Integer, Long> prev_tap;
    public static boolean isDayView = false;

    public UserDetailsAdapter(List<Entity> nodeList) {
        this.nodeList = nodeList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        isDayView = false;

        this.prev_tap = new HashMap<>();
        for(int i = 0; i < nodeList.size(); i++) {
            prev_tap.put(nodeList.get(i).getId(), Long.valueOf("-1"));
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        View iteView = inflater.inflate(R.layout.list_item, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(iteView);
        return viewHolder;
    }

    public void setDayview() {
        isDayView = true;
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {

        final Entity entity = nodeList.get(position);
        final boolean expanded = (entity.getId() == expanded_id);

        if(!expanded){
           holder.tvDescription.setVisibility(View.GONE);
           holder.ivMenu.setVisibility(View.GONE);
        } else {
            holder.tvDescription.setVisibility(View.VISIBLE);
            holder.ivMenu.setVisibility(View.VISIBLE);
        }

        holder.tvName.setText(entity.getName());
        holder.tvDescription.setText(entity.getDescription());
        if(isDayView) {
            holder.tvDescription.setText(MyAppDatabase.hier(entity.getId()));
        }
        holder.tvDate.setText(entity.getDate());
        holder.ivMenu.setVisibility(View.VISIBLE);
        holder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Entity entity = nodeList.get(position);
                final int userId = entity.getId();
                PopupMenu menu = new PopupMenu(context, holder.ivMenu);

                menu.inflate(R.menu.popup_menu);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                MyAppDatabase.getAppDatabase(context).dao().delete(entity);
                                notifyItemRangeChanged(position,nodeList.size());
                                nodeList.remove(position);
                                notifyItemRemoved(position);
                                break;
                            case R.id.update:
                                Intent intent = new Intent(context, UpdateActivity.class);
                                intent.putExtra("USERID", userId);
                                intent.putExtra("PARENT_ID", parent_id);
                                context.startActivity(intent);
                                break;


                        }


                        return false;
                    }
                });
                menu.show();

            }
        });

        holder.cl_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long prev_time = prev_tap.get(entity.getId());
                if (prev_time != null && prev_time != -1) {
                    Long entity_time = System.currentTimeMillis();
                    prev_tap.put(entity.getId(), System.currentTimeMillis());
                    if (entity_time - prev_time < 350) {
                        expanded_id = -1;
                        HomeFragment.parent_id = entity.getId();
                        nodeList = MyAppDatabase.getAppDatabase(context).dao().findbyParentId(entity.getId());
                        notifyDataSetChanged();
                        return;
                    }
                } else {
                    prev_tap.put(entity.getId(), System.currentTimeMillis());
                }


                if(expanded_id == -1) {
                    expanded_id = entity.getId();
                    notifyDataSetChanged();
                } else {
                    if(expanded_id == entity.getId()) {
                        expanded_id = -1;
                        notifyDataSetChanged();
                    } else {
                        expanded_id = entity.getId();
                        notifyDataSetChanged();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return nodeList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout cl_all;
        TextView tvName, tvDescription, tvDate;
        ImageView ivMenu;

        public UserViewHolder(View itemView) {
            super(itemView);

            cl_all = (ConstraintLayout) itemView.findViewById(R.id.cl_all);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            ivMenu = (ImageView) itemView.findViewById(R.id.iv_menu);
        }


    }
}
