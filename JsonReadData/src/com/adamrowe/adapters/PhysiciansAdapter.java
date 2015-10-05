package com.adamrowe.adapters;

import java.util.List;

import com.adamrowe.models.Physician;
import com.example.jsonreaddata.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//Custom Adapter to display two text views representing Name and Specialty of Physicians
public class PhysiciansAdapter extends BaseAdapter 
{		
	private LayoutInflater mInflater;
	private List<Physician> physicians;	

	public PhysiciansAdapter(Activity context, List<Physician> physicians) 
	{
	    this.mInflater = context.getLayoutInflater();
	    this.physicians = physicians;
	}

	@Override
	public int getCount() 
	{
		return physicians.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return physicians.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}
	
	//Static View Holder class for rendering during scrolling
	private static class ViewHolder 
	{
	    TextView txtName; //name text view
	    TextView txtSpecialty; //specialty text view
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder holder;
		if(convertView == null) 
		{
			//Inflate name_adapter layout
			convertView = mInflater.inflate(R.layout.name_adapter, null);
			
			holder = new ViewHolder();
			
			//Assign Name and Specialty Text Views from name_adapter layout to ViewHolder's TextViews
			holder.txtName = (TextView) convertView.findViewById(R.id.nameText);
			holder.txtSpecialty = (TextView) convertView.findViewById(R.id.specialtyText);
	
			convertView.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) convertView.getTag();
		}
		  
		//Assign Name and Specialty text from this physician to ViewHolder's TextViews
		holder.txtName.setText(physicians.get(position).getFormattedName());
		holder.txtSpecialty.setText(physicians.get(position).getSpecialty());

		return convertView;
	}
}
