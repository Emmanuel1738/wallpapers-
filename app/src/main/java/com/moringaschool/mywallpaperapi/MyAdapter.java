package com.moringaschool.mywallpaperapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<WallpaperModel> wallpaperModelArrayList;






    public MyAdapter(Context context, ArrayList<WallpaperModel> wallpaperModelArrayList) {
        this.context = context;
        this.wallpaperModelArrayList = wallpaperModelArrayList;
        this.wallpaperModelArrayList.addAll(wallpaperModelArrayList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTv;
        public TextView likesTv;
        public ImageView wallpaper;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.titleTv);
            likesTv = (TextView) itemView.findViewById(R.id.likesTv);
            wallpaper = (ImageView) itemView.findViewById(R.id.ImageView);

            PhotoViewAttacher mAttacher = new PhotoViewAttacher(wallpaper);
        }
    }
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.wallpaper_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        WallpaperModel wallpaperModel = wallpaperModelArrayList.get(position);

        String title = wallpaperModel.getTitle();
        String likes =  wallpaperModel.getLikes();
        String imgurl = wallpaperModel.getImageurl();


        Glide.with(context).load(imgurl).placeholder(R.drawable.ic_launcher_background).into(holder.wallpaper);

        holder.titleTv.setText(title);
        holder.likesTv.setText(likes);

    }



    @Override
    public int getItemCount() {
        return wallpaperModelArrayList.size();
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<WallpaperModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(wallpaperModelArrayList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (WallpaperModel wallpaperModel : wallpaperModelArrayList) {
                    if (wallpaperModel.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(wallpaperModel);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            wallpaperModelArrayList.clear();
            wallpaperModelArrayList.addAll((List) results.values);
            notifyDataSetChanged();

        }

        };

    }
