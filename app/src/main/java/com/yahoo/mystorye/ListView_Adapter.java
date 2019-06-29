package com.yahoo.mystorye;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import database.table.tb_Story;

class ListView_Adapter extends BaseAdapter
{
    private Context _context;
    private List<tb_Story> lstData;
    private int _templateId;
    private LayoutInflater inflater=null;

    private OnClickListener _onClickListener;

    public interface OnClickListener{

        public void onClickViewInfo(int PKStory);
    }

    public ListView_Adapter(Context context, List<tb_Story> lst,
                            int templateId,
                            OnClickListener onClickListener)
    {
        _context=context;
        lstData=lst;
        _templateId=templateId;
        _onClickListener=onClickListener;
        inflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return lstData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View rowView;

        rowView=inflater.inflate(_templateId, null);

        try {

            TextView txtStoryName = rowView.findViewById(R.id.txtStoryName);
            TextView txtGenre = rowView.findViewById(R.id.txtGenre);
            TextView txtLike = rowView.findViewById(R.id.txtLike);
            TextView txtRate = rowView.findViewById(R.id.txtRate);
            ImageView imgLike = rowView.findViewById(R.id.like);
            ImageView imgRate = rowView.findViewById(R.id.rate);


            txtStoryName.setText(String.format("%s  by %s",
                    lstData.get(position).StoryName,
                    lstData.get(position).Author));

            txtGenre.setText(lstData.get(position).Genre);
            if (lstData.get(position).Like ==0){
                txtRate.setVisibility(View.GONE);
                txtLike.setVisibility(View.GONE);
                imgLike.setVisibility(View.GONE);
                imgRate.setVisibility(View.GONE);
            }
            else {
                txtRate.setText(String.valueOf(lstData.get(position).Rate));
                txtLike.setText(String.valueOf(lstData.get(position).Like));
            }

        }
        catch (Exception e){

        }


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_onClickListener!=null)
                    _onClickListener.onClickViewInfo( lstData.get(position).PKStory);

            }
        });

        return rowView;
    }

}
