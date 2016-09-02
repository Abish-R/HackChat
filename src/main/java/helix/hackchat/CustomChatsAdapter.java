package helix.hackchat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomChatsAdapter extends BaseAdapter{
    //GetSetChats hgs=new GetSetChats();
	ArrayList<GetSetChats> chatList = new ArrayList<GetSetChats>();
    Context context;
    private static LayoutInflater inflater=null;
	public CustomChatsAdapter(Activity chaty, ArrayList<GetSetChats> chatLst) {
		//super(hotelMaster,textViewResourceId, chatLst);
        chatList=chatLst;
		context=chaty;
		inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    public class ViewHolder{
       // TextView tv;
        TextView tv1,tv2,tv3;
        LinearLayout ll;
        Button old_msg;
        //LinearLayout.LayoutParams params;
        //TextView tv3;
    }
    String senderorreceiver,senderorreceiver1;
    
    /***  Place the values from json into the listview **/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        int temp = 1;
//        LinearLayout.LayoutParams params=null;
        if (convertView == null) {
            //layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			convertView = inflater.inflate(R.layout.custom_chat, null);
	            
			/** Creates a ViewHolder and store references to the two children to bind data to.*/
			holder=new ViewHolder();
			convertView.setTag(holder);
            //holder.ll = (LinearLayout)convertView.findViewById(R.id.layout);
			holder.tv1=(TextView) convertView.findViewById(R.id.sender);
            holder.tv2=(TextView) convertView.findViewById(R.id.msg);
            holder.tv3=(TextView) convertView.findViewById(R.id.time);
            holder.old_msg = (Button) convertView.findViewById(R.id.old_msg);
//            holder.params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	        } 
        else {
			/** Get the ViewHolder back to get fast access to the TextView and the ImageView.*/
			holder = (ViewHolder) convertView.getTag();		
        }

        if(position==0) {
//            holder.params.setMargins(20, 0, 0, 0);
//            holder.ll.setLayoutParams(holder.params);
            convertView.setBackgroundColor(Color.argb(255,254,253,251));
            senderorreceiver = chatList.get(position).getReceiverId();
        }
        else {
            senderorreceiver1 = chatList.get(position).getReceiverId();
            if (senderorreceiver.equals(senderorreceiver1)) {
//                holder.params.setMargins(20, 0, 0, 0);
//                holder.ll.setLayoutParams(holder.params);
                convertView.setBackgroundColor(Color.argb(255, 254, 253, 251));
            }
            else {
//                holder.params.setMargins(0, 0, 20, 0);
//                holder.ll.setLayoutParams(holder.params);
                convertView.setBackgroundColor(Color.argb(255, 249, 222, 170));
            }
        }
        //GetSetChats gsc=chatList.get(position);
        String sender = ((ChatActivity)context).getName(chatList.get(position).getSenderId(),position);
        holder.tv1.setText(sender);//chatList.get(position).getSenderId());
        holder.tv2.setText(chatList.get(position).getMessagee());
        holder.tv3.setText(chatList.get(position).getSentDate());
//        int pos=position+1;
//        if(chatList.size()>=30 && position==0) {//%30 == 0 && position
//            double value = (double)position%30;
//            holder.old_msg.setVisibility(View.VISIBLE);
//            holder.old_msg.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    holder.old_msg.setVisibility(View.INVISIBLE);
//                    ((ChatActivity)context).getNextPage();
//                }
//            });
//        }
//        public int getPosition(){
//            return position;
//        }
        convertView.setOnClickListener(new OnClickListener() {            
        	@Override
        	public void onClick(View v) {
                //Toast.makeText(context,position+"",Toast.LENGTH_SHORT).show();
        	}
        });   
        return convertView;
    }
}