package com.ritesh.customfieldviews.adaptors;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * @author Ritesh Shakya
 */
public class NothingSelectedSpinnerAdapter implements SpinnerAdapter, ListAdapter {

    private static final int EXTRA = 1;
    private final String mHint;
    private final SpinnerAdapter adapter;
    private final Context context;
    private final int nothingSelectedLayout;
    private final int nothingSelectedDropdownLayout;
    private final LayoutInflater layoutInflater;

    public NothingSelectedSpinnerAdapter(SpinnerAdapter spinnerAdapter, @SuppressWarnings("SameParameterValue") int nothingSelectedLayout, Context context, String hint) {
        this(spinnerAdapter, nothingSelectedLayout, -1, context, hint);
    }

    private NothingSelectedSpinnerAdapter(SpinnerAdapter spinnerAdapter, int nothingSelectedLayout, int nothingSelectedDropdownLayout, Context context, String hint) {
        this.adapter = spinnerAdapter;
        this.context = context;
        this.nothingSelectedLayout = nothingSelectedLayout;
        this.nothingSelectedDropdownLayout = nothingSelectedDropdownLayout;
        this.mHint = hint;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            return getNothingSelectedView(parent, mHint);
        }
        return adapter.getView(position - EXTRA, null, parent);
    }

    private View getNothingSelectedView(ViewGroup parent, String hint) {
        View view = layoutInflater.inflate(nothingSelectedLayout, parent, false);
        ((TextView) view.findViewById(android.R.id.text1)).setText(hint);
        return view;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            return nothingSelectedDropdownLayout == -1 ? new View(context) : getNothingSelectedDropdownView(parent);
        }
        return adapter.getDropDownView(position - EXTRA, null, parent);
    }

    private View getNothingSelectedDropdownView(ViewGroup parent) {
        return layoutInflater.inflate(nothingSelectedDropdownLayout, parent, false);
    }

    @Override
    public int getCount() {
        int count = adapter.getCount();
        return count == 0 ? 0 : count + EXTRA;
    }

    @Override
    public Object getItem(int position) {
        return position == 0 ? null : adapter.getItem(position - EXTRA);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position >= EXTRA ? adapter.getItemId(position - EXTRA) : position - EXTRA;
    }

    @Override
    public boolean hasStableIds() {
        return adapter.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        return adapter.isEmpty();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        adapter.unregisterDataSetObserver(observer);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

}