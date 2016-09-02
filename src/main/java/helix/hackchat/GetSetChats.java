package helix.hackchat;

/**
 * Created by HelixTech-Admin on 4/9/2016.
 */
public class GetSetChats {
    String message_id,sender_id,receiver_id,message,sent_date;
    GetSetChats(){}
    GetSetChats(String msg_id,String sndr_id,String rcvr_id,String msg,String snt_dt){
        this.message_id = msg_id;
        this.sender_id = sndr_id;
        this.receiver_id = rcvr_id;
        this.message = msg;
        this.sent_date = snt_dt;
    }
    public String getMessageId(){
        return this.message_id;
    }
    public String getSenderId(){
        return this.sender_id;
    }
    public String getReceiverId(){
        return this.receiver_id;
    }
    public String getMessagee(){
        return this.message;
    }
    public String getSentDate(){
        return this.sent_date;
    }

    public void setMessageId(String m_id){
        this.message_id = m_id;
    }
    public void setSenderId(String s_id){
        this.sender_id = s_id;
    }
    public void setReceiverId(String r_id){
        this.receiver_id = r_id;
    }
    public void setMessagee(String msg){
        this.message = msg;
    }
    public void setSentDate(String dt){
        this.sent_date = dt;
    }
}
