

package helix.hackchat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.provider.SyncStateContract.Constants;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapterContacts extends BaseAdapter{
	/**Class variables*/
	private Activity context;
	ArrayList<GetSetFromAsync> veh_list = new ArrayList<GetSetFromAsync>();
	private LayoutInflater inflater=null;
	      
	public CustomAdapterContacts(Activity contxt, ArrayList<GetSetFromAsync> vehlist) {
			super();
			this.context=contxt;
			this.veh_list=vehlist;
			inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
	@Override
	public int getCount() {
		return veh_list.size();
	}
	
	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class ViewHolder {
		TextView tv,tv1;
	}
	
	/**Called this for data.length() times*/
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		ViewHolder holder1;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.custom_contacts, null);
	            
			/** Creates a ViewHolder and store references to the two children to bind data to.*/
			holder1=new ViewHolder();
			convertView.setTag(holder1);
			holder1.tv=(TextView) convertView.findViewById(R.id.name);
			holder1.tv1=(TextView) convertView.findViewById(R.id.mob);
	        }
		else {
			/** Get the ViewHolder back to get fast access to the TextView and the ImageView.*/
			holder1 = (ViewHolder) convertView.getTag();
		}
        if(position%2==0)
        	convertView.setBackgroundColor(Color.argb(255, 249, 222, 170));
        else
           	convertView.setBackgroundColor(Color.argb(255,254,253,251));
		String val = veh_list.get(position).getName();
		String val1 = veh_list.get(position).getMobile();
		holder1.tv.setText(veh_list.get(position).getName());
		holder1.tv1.setText(veh_list.get(position).getMobile());
	             
		convertView.setOnClickListener(new OnClickListener() {            
			@Override
			public void onClick(View v) {

	            ((ContactsActivity)context).startChat(veh_list.get(position).getUserID());
	            //Toast.makeText(context, "You Clicked "+veh_list.get(position).getUserID(), Toast.LENGTH_LONG).show();
			}
		});   
		return convertView;
	}
}