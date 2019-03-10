package com.jigar.android.gps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jigar on 3/9/2019.
 */

public class Adapter_images extends RecyclerView.Adapter<Adapter_images.ViewHolder>  {
    private ArrayList<String> list = new ArrayList<>();
    private Context mContext;

    public Adapter_images(ArrayList<String> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        //private PositionClickListener mListener;
        public ImageView img_view;

        public ViewHolder(View itemView) {

            super(itemView);
            img_view = (ImageView) itemView.findViewById(R.id.img_view);
            mContext= itemView.getContext();
        }

    }
    @NonNull
    @Override
    public Adapter_images.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_data, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_images.ViewHolder holder, final int position) {

        String image_model = list.get(position);
        ImageView img_view = holder.img_view;

        String get_path = list.get(position);
        File imgFile = new  File(get_path);
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            img_view.setImageBitmap(myBitmap);

        }
//        holder.img_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "Position - "+ position, Toast.LENGTH_SHORT).show();
//            }
//
//        });
//        holder.img_view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                Toast.makeText(mContext, "LognClick-"+position, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}
