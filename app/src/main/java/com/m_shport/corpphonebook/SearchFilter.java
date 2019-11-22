package com.m_shport.corpphonebook;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class SearchFilter extends Filter {

    RetrofitAdapter adapter;
    List<ContactList> filteredList;

    public SearchFilter (List<ContactList> filteredList, RetrofitAdapter adapter) {
        this.adapter = adapter;
        this.filteredList = filteredList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        FilterResults results = new FilterResults();

        if (charSequence != null && charSequence.length() > 0) {
            charSequence = charSequence.toString().toLowerCase();
            List<ContactList> filteredData = new ArrayList<>();

            for (int i = 0; i < filteredList.size(); i++) {
                if (filteredList.get(i).getSn().toLowerCase().contains(charSequence) || filteredList.get(i).getGivenname().toLowerCase().contains(charSequence) || filteredList.get(i).getDepartment().toLowerCase().contains(charSequence) || filteredList.get(i).getTitle().toLowerCase().contains(charSequence) || filteredList.get(i).getMobile().toLowerCase().contains(charSequence)) {
                    filteredData.add(filteredList.get(i));
                }
            }

            results.count = filteredData.size();
            results.values = filteredData;
        } else {
            results.count = filteredList.size();
            results.values = filteredList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.dataList = (List<ContactList>) filterResults.values;
        adapter.notifyDataSetChanged();
    }
}
