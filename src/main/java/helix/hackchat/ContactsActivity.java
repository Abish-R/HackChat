package helix.hackchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HelixTech-Admin on 4/9/2016.
 */
public class ContactsActivity extends AppCompatActivity {
    DatabaseRecordHandler dbrh = new DatabaseRecordHandler(this);
    ListView contacts_list;
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp=getSharedPreferences("key", Context.MODE_PRIVATE);
        user_id = sp.getInt("hack_id", 0);
        //int user_id = dbrh.getMyDetails();
        new AsyncGetAppUsers(this).execute(user_id + "");

        setContentView(R.layout.application_users);
        contacts_list = (ListView)findViewById(R.id.contacts);

    }
    public void saveContacts(List<GetSetFromAsync> cont){
        for (GetSetFromAsync cn : cont) {
            GetSetContact gsc = new GetSetContact();
//            gsc.setUserID(cn.getUserID());
//            gsc.setName(cn.getName());
//            gsc.setMobile(cn.getMobile());
            if(dbrh.addContacts(new GetSetContact(cn.getUserID(),cn.getName(),cn.getMobile())) == 0)
                break;
        }
        getAllContact();
    }
    private void getAllContact(){
        ArrayList<GetSetFromAsync> contacts= new ArrayList<GetSetFromAsync>();
        List<GetSetContact> cont = dbrh.getAllContacts(user_id);
        for (GetSetContact cn : cont) {
                GetSetFromAsync tolist = new GetSetFromAsync();
                tolist.setUserID(cn.getUserID());
                tolist.setName(cn.getName());
                tolist.setMobile(cn.getMobile());
                contacts.add(tolist);

        }
        CustomAdapterContacts cac = new CustomAdapterContacts(ContactsActivity.this,contacts);
        contacts_list.setAdapter(cac);

    }
    public void startChat(int id){
        Intent intent=new Intent(this,ChatActivity.class);
        intent.putExtra("sender_id",user_id);
        intent.putExtra("receiver_id",id);
        intent.putExtra("Notification","");
        startActivity(intent);
    }
}
