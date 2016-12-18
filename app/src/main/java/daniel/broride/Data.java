package daniel.broride;

import android.database.Cursor;
import android.util.Log;

public class Data {
    private static Data mInstance;

    User[] users = new User[20];
    int count;

    public static synchronized Data getInstance(){
        if(mInstance == null){
            mInstance = new Data();
        }

        return mInstance;
    }

    private Data(){}

    public void fillUser(Cursor res){
        if(res.getCount()==0){
            //show message
            //trowh error
        }
        int i = 0;
        count = 0;

        while(res.moveToNext()){
            users[i] = new User();
            users[i].setId(res.getInt(0));
            users[i].setName(res.getString(1));
            users[i].setDriver(res.getInt(2));
            users[i].setAge(res.getInt(3));
            users[i].setDebit(res.getDouble(4));
            count++;
            i++;
        }
    }
    public void insertUser(User user){
        users[count] = new User();
        users[count] = user;
        count++;
    }

    public User getUser(int i){
        return users[i];
    }

    public int getCount(){
        return count;
    }
}
