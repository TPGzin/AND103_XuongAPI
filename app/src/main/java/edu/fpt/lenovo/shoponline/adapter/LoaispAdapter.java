package edu.fpt.lenovo.shoponline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.fpt.lenovo.shoponline.R;
import edu.fpt.lenovo.shoponline.model.Loaisp;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> loaispArrayList;
    Context context;
    public LoaispAdapter(ArrayList<Loaisp> arr, Context context)
    {
        this.loaispArrayList = arr;
        this.context = context;
    }
    @Override
    public int getCount() {
        return loaispArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaispArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        //tao khung
        if(view==null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_listview_loaisp,null);
            viewHolder.txttenloaisp=(TextView)view.findViewById(R.id.textviewloaisp);
            viewHolder.imgloaisp = (ImageView)view.findViewById(R.id.imageviewloaisp);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (LoaispAdapter.ViewHolder)view.getTag();
        }
        //lay du lieu
        Loaisp loaisp = (Loaisp)getItem(position);
        viewHolder.txttenloaisp.setText(loaisp.getTenloaisp());
        //lay anh qua mang => Picasso
        Picasso.get().load(loaisp.getHinhanhloaisp())
                .placeholder(R.drawable.home)
                .error(R.drawable.erro)
                .into(viewHolder.imgloaisp);
        return view;
    }
    public class ViewHolder{
        ImageView imgloaisp;
        TextView txttenloaisp;
    }
}
