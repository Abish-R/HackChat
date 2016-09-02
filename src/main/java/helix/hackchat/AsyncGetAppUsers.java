/** Ridio v1.0.1
 * 	Purpose	   : Sign Up Data saving class into the Helix DB 
 *  Created by : Abish 
 *  Created Dt : old file
 *  Modified on: 
 *  Verified by: Srinivas
 *  Verified Dt: 18-01-2016
 * **/

package helix.hackchat;

import java.util.ArrayList;
import java.util.List;

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

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import helix.hackchat.general.Urls;

public class AsyncGetAppUsers extends AsyncTask<String, Void, String> {
	/**Class Variables*/
	Context context;
	String TAG="SignUpClass";
	int validation=0;
	JSONObject root;
	ProgressDialog progressDialog;
	public AsyncGetAppUsers(Context users) {
		//this.activity = activity;
		context = users;
	}
	/**Before Sending Data*/
	protected void onPreExecute() {
		super.onPreExecute();
		// do stuff before posting data
//		progressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
//		progressDialog.setMessage("Loading. Please Wait...");
//		progressDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		progressDialog.show();
	}
	/**While sending datas to DB*/
	@Override
	protected String doInBackground(String... arg0) {
		String result=null;
		try{
			// url where the data will be posted                	
			String postReceiverUrl = Urls.chatContact;
			Log.v(TAG, "postURL: " + postReceiverUrl);                   
			HttpClient httpClient = new DefaultHttpClient();   // HttpClient                    
			HttpPost httpPost = new HttpPost(postReceiverUrl);	// post header
                    
			/** our data for sending*/
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(0);
			//Log.d(uname.getText().toString(), pass.getText().toString());
			nameValuePairs.add(new BasicNameValuePair("user_id", arg0[0]));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8);
			httpPost.setEntity(entity);
			
			// execute HTTP post request
			HttpResponse response = httpClient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**After Data passed, returned datas are below*/
	@Override
	protected void onPostExecute(String result) {
//		progressDialog.dismiss();
		String message=null,user_id=null;
		JSONArray data;
		ArrayList<GetSetFromAsync> contact = new ArrayList<GetSetFromAsync>();
		try {
			Log.d("Inside Post", "message");
			root = new JSONObject(result);
			validation = root.getInt("response");
			message = root.getString("message");
			Log.d(result, message);
			if(validation==1) {
				data=root.getJSONArray("data");
				for(int i=0;i<data.length();i++){
					GetSetFromAsync cont = new GetSetFromAsync();
					int userid = Integer.parseInt(data.getJSONObject(i).getString("user_id"));
					cont.setUserID(userid);//Integer.parseInt(user_id)
					cont.setName(data.getJSONObject(i).getString("name"));
					cont.setMobile(data.getJSONObject(i).getString("mobile_number"));
					contact.add(cont);
				}
				((ContactsActivity) context).saveContacts(contact);

			} else{
				alertWrongSignUp(message+".!");
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/**Alert Messages*/  
	private void alertWrongSignUp(String val){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle("Contacts");
		alertDialogBuilder.setMessage(val);
		// set positive button: Yes message
		alertDialogBuilder.setPositiveButton("Retry",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
			}						
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}
