package com.main.app_firmas_segucor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.app_firmas_segucor.R;
import com.main.app_firmas_segucor.models.SolCotizacionOT;

import java.util.ArrayList;
import java.util.List;

public class OtesSCAdapter extends RecyclerView.Adapter<OtesSCAdapter.ViewHolder> {
    Context context;
    private List<SolCotizacionOT> solCotizacionList=new ArrayList<>();
    private OnClickListener onClickListener = null;

    public interface OnClickListener {
        void onConformidad(View view, int position);
        void onCotizacion(View view, int position) throws Exception;
        void onSubir(View view, int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public OtesSCAdapter(Context context, List<SolCotizacionOT> solCotizacionList) {
        this.context=context;
        this.solCotizacionList=solCotizacionList;
    }

    @NonNull
    @Override
    public OtesSCAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.sc_otes_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtesSCAdapter.ViewHolder holder, int position) {
        holder.bind(solCotizacionList.get(position));
        holder.btnConformidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener == null) return;
                onClickListener.onConformidad(view, holder.getAdapterPosition());
            }
        });
        holder.btnCotizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener == null) return;
                try {
                    onClickListener.onCotizacion(view, holder.getAdapterPosition());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        holder.btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener == null) return;
                onClickListener.onSubir(view, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return solCotizacionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView viewNumOte, btnCotizacion, btnConformidad, btnSubir,view_fecha,view_sc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNumOte=itemView.findViewById(R.id.view_numero_ot);
            view_fecha=itemView.findViewById(R.id.view_fecha);
            view_sc=itemView.findViewById(R.id.view_sc);
            btnCotizacion = itemView.findViewById(R.id.view_btn_solicitacot);
            btnConformidad = itemView.findViewById(R.id.view_btn_conformidad);
            btnSubir = itemView.findViewById(R.id.view_btn_subir);
        }
        public void bind(SolCotizacionOT solCotizacionList){
            viewNumOte.setText(solCotizacionList.getOt() +"");
            view_fecha.setText(solCotizacionList.getFecha_op());
            view_sc.setText(solCotizacionList.getSolcotizacion());
        }
    }
}
