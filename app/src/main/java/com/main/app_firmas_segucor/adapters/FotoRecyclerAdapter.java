package com.main.app_firmas_segucor.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.app_firmas_segucor.R;
import com.main.app_firmas_segucor.models.FotoConformidad;

import java.util.List;

public class FotoRecyclerAdapter extends RecyclerView.Adapter<FotoRecyclerAdapter.ViewHolder>{

    private Context context;
    //private List<String> listFotos;
    private List<FotoConformidad> listFotos;

    private OnClickListener onClickListener = null;

    public interface OnClickListener {
        void onBorrarFoto(View view, int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public FotoRecyclerAdapter(Context context, List<FotoConformidad> listFotos) {
        this.context = context;
        this.listFotos = listFotos;
    }

    @NonNull
    @Override
    public FotoRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foto_rv_card, parent, false);
        return new FotoRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FotoRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FotoConformidad item = listFotos.get(holder.getAdapterPosition());
        Bitmap bitmap;
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            bitmap = BitmapFactory.decodeFile(item.getPathFoto(), options);
        } else {
            bitmap = BitmapFactory.decodeFile(item.getPathFoto());
        }
        if(item.getIdFoto() > 0 && item.getIdFrontal() > 0){
            holder.viewBorrar.setVisibility(View.GONE);
            holder.imageFoto.setVisibility(View.GONE);
        }
        holder.imageFoto.setImageBitmap(bitmap);
        //
        holder.viewBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener == null) return;
                onClickListener.onBorrarFoto(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFotos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageFoto;
        private TextView viewBorrar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewBorrar = itemView.findViewById(R.id.view_borrar_fotoaudio);
            imageFoto = itemView.findViewById(R.id.img_foto);
        }
    }
}
