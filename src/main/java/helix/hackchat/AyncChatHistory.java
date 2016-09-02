package helix.hackchat;

/**
 * Created by HelixTech-Admin on 3/17/2016.
 */

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import helix.hackchat.general.Urls;

public class AyncChatHistory extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;
    private Context context;
    int pageno=0;

    public AyncChatHistory(Context conx){
        context=conx;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        // do stuff before posting data
//        progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
//        progressDialog.setMessage("Syncing data. Please Wait...");
//        progressDialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... arg0) {
        String result=null;
        try{
            // url where the data will be posted
            String postReceiverUrl = Urls.chatHistory;
            Log.v("Login Url:", "postURL: " + postReceiverUrl);
            HttpClient httpClient = new DefaultHttpClient();   // HttpClient
            HttpPost httpPost = new HttpPost(postReceiverUrl);	// post header
            pageno=Integer.parseInt(arg0[2]);

            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("sender_id", arg0[0]));
            nameValuePairs.add(new BasicNameValuePair("receiver_id", arg0[1]));
            nameValuePairs.add(new BasicNameValuePair("pageno", arg0[2]));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
            httpPost.setEntity(entity);

            // execute HTTP post request
            HttpResponse response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            //e.printStackTrace();
            Toast.makeText(context, "Server error while uploading data.!", Toast.LENGTH_LONG).show();
            //((AttendanceRegister)context).SingleButtonAlert("Attendance", "Server error while uploading data.", "Ok","");
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // do stuff after posting data
        //progressDialog.dismiss();
        int validation = 0,datacount=0;
        JSONObject root = null;
        String  message = null;

        try {
            Log.d("Inside Post", "message");
            root = new JSONObject(result);
            validation = root.getInt("response");
            message = root.getString("message");
            datacount = root.getInt("datacount");
            JSONArray data;

            Log.d(result, datacount+"");
            if (validation == 1 && datacount>=1 && message.equals("Success no more data"))
                ((ChatActivity) context).addHistory(null,pageno,"No more Chats");
            if (validation == 1 && datacount>=1) {
                data=root.getJSONArray("data");
                ArrayList<GetSetChats> chat = new ArrayList<GetSetChats>();
                for(int i=0;i<data.length();i++) {
                    GetSetChats gsc =  new GetSetChats();
                    gsc.setMessageId(data.getJSONObject(i).getString("message_id"));
                    gsc.setSenderId(data.getJSONObject(i).getString("sender_id"));
                    gsc.setReceiverId(data.getJSONObject(i).getString("receiver_id"));
                    gsc.setMessagee(data.getJSONObject(i).getString("message"));
                    gsc.setSentDate(data.getJSONObject(i).getString("sent_date"));
                    chat.add(gsc);
                    ((ChatActivity) context).addHistory(chat,pageno,"");
                }
            }else if(datacount<1)
                ((ChatActivity) context).addHistory(null,pageno,"No Chats");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
