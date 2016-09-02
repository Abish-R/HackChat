package helix.hackchat;

/**
 * Created by HelixTech-Admin on 4/9/2016.
 */
public class GetSetFromAsync {
    int userid;
    String name,mobile,deviceid;

    public GetSetFromAsync(){}

    public GetSetFromAsync(int id, String nam, String mob, String dev_id){
        this.userid = id;
        this.name = nam;
        this.mobile = mob;
        this.deviceid = dev_id;
    }
    public GetSetFromAsync(int id, String nam, String mob){
        this.userid = id;
        this.name = nam;
        this.mobile = mob;
    }
    public int getUserID(){
        return this.userid;
    }
    public String getName(){
        return this.name;
    }
    public String getMobile(){
        return this.mobile;
    }
    public String getDeviceId(){
        return this.deviceid;
    }

    public void setUserID(int id){
        this.userid = id;
    }
    public void setName(String nam){
        this.name = nam;
    }
    public void setMobile(String mob){
        this.mobile = mob;
    }
    public void setDeviceId(String dev_id){
        this.deviceid = dev_id;
    }
}
