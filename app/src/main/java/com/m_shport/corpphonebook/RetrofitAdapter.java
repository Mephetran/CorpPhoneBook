package com.m_shport.corpphonebook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.ViewHolder> implements Filterable {

    List<ContactList> dataList, filteredList;
    SearchFilter filter;

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView sn, givenname, title;

        public ViewHolder (View view) {
            super(view);

            sn = view.findViewById(R.id.sn);
            givenname = view.findViewById(R.id.givenname);
            title = view.findViewById(R.id.title);
        }
    }

    public RetrofitAdapter (List<ContactList> dataList) {
        this.dataList = dataList;
        this.filteredList = dataList;
    }

    @Override
    public RetrofitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview, parent, false);
        return new RetrofitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RetrofitAdapter.ViewHolder holder, final int position) {
        holder.sn.setText(dataList.get(position).getSn());
        holder.givenname.setText(dataList.get(position).getGivenname());
        holder.title.setText(dataList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("surname", dataList.get(position).getSn());
                intent.putExtra("name", dataList.get(position).getGivenname());
                intent.putExtra("department", dataList.get(position).getDepartment());
                intent.putExtra("jobtitle", dataList.get(position).getTitle());
                intent.putExtra("mobile", dataList.get(position).getMobile());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new SearchFilter(filteredList, this);
        }
        return filter;
    }
}
