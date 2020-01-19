package com.assafbt.countriestv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private Context context;
    private List<Country> countryList;

    public CountryAdapter(Context context, List<Country> list) {
        this.context = context;
        this.countryList = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Country country = countryList.get(position);

        holder.textName.setText(String.valueOf(country.getName()));
        holder.textNativeName.setText(String.valueOf(country.getNativeName()));
        holder.textArea.setText(String.valueOf(country.getArea()));

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName, textNativeName, textArea;

        public ViewHolder(View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.main_name);
            textNativeName = itemView.findViewById(R.id.main_nativeName);
            textArea = itemView.findViewById(R.id.main_area);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    // get position
                    int pos = getAdapterPosition();

                }
            });
        }//ViewHolder
    }//ViewHolder extends
}//CountryAdapter extends