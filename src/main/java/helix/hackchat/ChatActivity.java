package helix.hackchat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseRecordHandler dbrh = new DatabaseRecordHandler(this);
    TextView dummy;
    ListView message_list;
    EditText message;
    Button send,old_msg;
    int sender,receiver,page=0,dont_show_button;
    ArrayList<GetSetChats> chat = new ArrayList<GetSetChats>();
    CustomChatsAdapter cac;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        //Bundle extras = getIntent().getExtras();
        //sender = extras.getInt("sender_id");
        //receiver = extras.getInt("receiver_id");
        //Toast.makeText(this,sender+" "+receiver,Toast.LENGTH_SHORT).show();
        cac= new CustomChatsAdapter(ChatActivity.this,chat);
        setContentView(R.layout.activity_chat);
        initializeViews();
        message_list.setAdapter(cac);
        context = getApplicationContext();
//        message_list.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
//        message_list.setStackFromBottom(true);
    }

    @Override
    public void onNewIntent(Intent intent){
        Bundle extras = intent.getExtras();
        if(extras != null){
            if(extras.getString("Notification").length()<5) {
                sender = extras.getInt("sender_id");
                receiver = extras.getInt("receiver_id");
                new AyncChatHistory(this).execute( sender+ "", receiver + "", page+"");
            }else{
                if(receiver == extras.getInt("sender_id")){
                    ArrayList<GetSetChats> chat = new ArrayList<GetSetChats>();
                    sender = extras.getInt("receiver_id");//values interchanged
                    receiver = extras.getInt("sender_id");//values interchanged
                    String msg_id = extras.getString("msg_id");
                    String msg = extras.getString("message");
                    String snt_dt = extras.getString("sent_date");
                    GetSetChats gsc = new GetSetChats();
                    gsc.setMessageId(msg_id);
                    gsc.setSenderId(receiver+"");//values interchanged
                    gsc.setReceiverId(sender + "");//values interchanged
                    gsc.setMessagee(msg);
                    gsc.setSentDate(snt_dt);
                    chat.add(gsc);
                    addHistory(chat,0,"");
                }else {
                    sender = extras.getInt("receiver_id");//values interchanged
                    receiver = extras.getInt("sender_id");//values interchanged
                    chat.clear();
                    new AyncChatHistory(this).execute(sender + "", receiver + "", page+"");
                }
                //cac= new CustomChatsAdapter(ChatActivity.this,chat);
                //message_list.setAdapter(cac);
            }
            //String notification = extras.getInt("Notification");

            //new AyncChatHistory(this).execute( sender+ "", receiver + "", "0");
//            else
//                new AyncChatHistory(this).execute( receiver+ "", sender + "", "0");
                // extract the extra-data in the Notification
//                String msg = extras.getString("NotificationMessage");
//                txtView = (TextView) findViewById(R.id.txtMessage);
//                txtView.setText(msg);
        }
    }

    private void initializeViews() {
        message_list = (ListView) findViewById(R.id.message_list);
        dummy = (TextView) findViewById(R.id.dummy);
        message = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);
        old_msg = (Button) findViewById(R.id.old_msg);
        send.setOnClickListener(this);
        old_msg.setOnClickListener(this);

//        message.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    hideKeyboard();
//                }
//            }
//            private void hideKeyboard() {
//                if (message != null) {
//                    InputMethodManager imanager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imanager.hideSoftInputFromWindow(message.getWindowToken(), 0);
//
//                }
//
//            }
//        });
    }


    public void onClick(View v){
        if(v.getId()==R.id.send){
            if(message.getText().toString().length()<1)
                Toast.makeText(this, "No text to send", Toast.LENGTH_SHORT).show();
            else {
                new AsyncSiendMessage(this).execute(sender + "", receiver + "", message.getText().toString());
                message.setText("");
            }

        }
        else if(v.getId()==R.id.old_msg){
            old_msg.setVisibility(View.GONE);
            dummy.setVisibility(View.VISIBLE);
            dummy.setText("Loading...");
            page++;
            new AyncChatHistory(this).execute(sender + "", receiver + "", page + "");
            //getNextPage();
        }
    }

   // CustomChatsAdapter cac = new CustomChatsAdapter(ChatActivity.this,null);
    public void addHistory(List<GetSetChats> chats,int page,String msg){
        if(msg.equals("No more Chats")) {
            dont_show_button=1;
            old_msg.setVisibility(View.GONE);
            dummy.setText("No More History");
        }
        else if(msg.equals("No Chats"))
            dummy.setText("Start Chat");
        else{
            GetSetChats gsc = new GetSetChats();
            //String sender = dbrh.getNameofUser(Integer.parseInt(chats.get(chats.size() - 1).getSenderId()));
            //String receiver = dbrh.getNameofUser(Integer.parseInt(chats.get(chats.size() - 1).getReceiverId()));
            gsc.setMessageId(chats.get(chats.size() - 1).getMessageId());
            gsc.setSenderId(chats.get(chats.size() - 1).getSenderId());
            gsc.setReceiverId(chats.get(chats.size() - 1).getReceiverId());
            gsc.setMessagee(chats.get(chats.size() - 1).getMessagee());
            gsc.setSentDate(chats.get(chats.size() - 1).getSentDate());
            if (page < 1) {
                chat.add(gsc);
                cac.notifyDataSetChanged();
                message_list.smoothScrollToPosition(cac.getCount() - 1);
            } else {
                chat.add(0, gsc);
                cac.notifyDataSetChanged();
                message_list.smoothScrollToPosition(place+1);
            }

        }
        //message_list.smoothScrollToPosition(0);
        //message_list.setSelection(chats.size()-1);
    }

//    public void createNotification(String tit, String message) {
//        int icon = R.drawable.icon;
//        long when = System.currentTimeMillis();
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notification = new Notification(icon, message, when);
//
//        String title = "DashBoard Builder";//context.getString(R.string.app_name);
//
//        Intent notificationIntent = new Intent(this, ChatActivity.class);
//        // set intent so it does not start a new activity
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent intent =
//                PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        notification.setLatestEventInfo(this, title, message, intent);
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        // Play default notification sound
//        notification.defaults |= Notification.DEFAULT_SOUND;
//
//        // Vibrate if vibrate is enabled
//        notification.defaults |= Notification.DEFAULT_VIBRATE;
//        notificationManager.notify(0, notification);
//
//    }
int count=0,place=0;String name;

    public String getName(String u_id,int position){
        int id = Integer.parseInt(u_id);
        if((position == 0 || position == 1) && dont_show_button==0 && cac.getCount()>=29) {
            old_msg.setVisibility(View.VISIBLE);
            dummy.setVisibility(View.GONE);
            place = position;
        }
        else if(dont_show_button==1) {
            old_msg.setVisibility(View.GONE);
            dummy.setVisibility(View.VISIBLE);
            dummy.setText("No More data to load");
        }
        else{
            old_msg.setVisibility(View.GONE);
            dummy.setVisibility(View.GONE);
        }
        if(sender != id && count==0) {
            name= dbrh.getNameofUser(id);
            count++;
            return name;
        }else if(sender == id)
            return "You";
        else
            return name;
    }

//    public void getNextPage(){
//        page++;
//        new AyncChatHistory(this).execute(sender + "", receiver + "", page+"");
////        if(position/30 == 0) {
////            old_msg.setVisibility(View.VISIBLE);
////            dummy.setVisibility(View.INVISIBLE);
////        }
////        else {
////            dummy.setVisibility(View.VISIBLE);
////            old_msg.setVisibility(View.INVISIBLE);
////        }
//    }
}
