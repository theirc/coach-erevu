package com.ryanwarsaw.hekima.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.ryanwarsaw.hekima.R;
import com.ryanwarsaw.hekima.model.Week;
import java.util.List;

public class MenuAdapter extends ArrayAdapter<Week> {

  private Context context;
  private List<Week> list;

  public MenuAdapter(@NonNull Context context, List<Week> list) {
    super(context, 0, list);
    this.context = context;
    this.list = list;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    View menuItem = convertView != null ?
        convertView : LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
    ((Button) menuItem.findViewById(R.id.menu_button)).setText(list.get(position).title);
    return menuItem;
  }
}
