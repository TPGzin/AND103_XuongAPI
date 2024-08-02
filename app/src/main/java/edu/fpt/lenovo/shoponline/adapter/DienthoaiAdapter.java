package edu.fpt.lenovo.shoponline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.fpt.lenovo.shoponline.R;
import edu.fpt.lenovo.shoponline.model.Sanpham;

public class DienthoaiAdapter extends BaseAdapter {
    ArrayList<Sanpham> sanphamArrayList;
    Context context;

    public DienthoaiAdapter(ArrayList<Sanpham> sanphamArrayList, Context context) {
        this.sanphamArrayList = sanphamArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sanphamArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return sanphamArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderDT viewHolderDT=null;
        if(view==null){
            viewHolderDT=new ViewHolderDT();
            LayoutInflater inflater =(LayoutInflater)
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.item_dienthoai,null);
            viewHolderDT.txttendienthoai=view.findViewById(R.id.textviewdienthoai);
            viewHolderDT.txtgiadienthoai=view.findViewById(R.id.textviewgiadienthoai);
            viewHolderDT.txtmotadienthoai=view.findViewById(R.id.textviewmotadienthoai);
            viewHolderDT.imgdienthoai=view.findViewById(R.id.imageviewdienthoai);
            view.setTag(viewHolderDT);

        }
        else {
            viewHolderDT=(ViewHolderDT) view.getTag();
        }
        Sanpham sanpham=(Sanpham) getItem(i);
        viewHolderDT.txttendienthoai.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat= new DecimalFormat(("###,###,###"));
        viewHolderDT.txtgiadienthoai.setText("Gia:"+decimalFormat.format(sanpham.getGiasanpham()));
        viewHolderDT.txtmotadienthoai.setMaxLines(2);
        viewHolderDT.txtmotadienthoai.setText(sanpham.getMotasanpham());
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.home)
                .error(R.drawable.erro)
                .into(viewHolderDT.imgdienthoai);
        return null;
    }

    public class ViewHolderDT{
        public TextView txttendienthoai,txtgiadienthoai,txtmotadienthoai;
        public ImageView imgdienthoai;
    }
}
