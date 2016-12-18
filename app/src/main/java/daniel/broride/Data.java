package daniel.broride;

import android.database.Cursor;
import android.util.Log;

public class Data {
    private static Data mInstance;

    User[] users = new User[20];
    int count;

    public static synchronized Data getmInstance(){
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
        while(res.moveToNext()){
            users[i] = new User();
            users[i].setId(res.getInt(0));
            users[i].setName(res.getString(1));
            users[i].setDriver(res.getInt(2));
            users[i].setAge(res.getInt(3));
            users[i].setDebit(res.getDouble(4));
            count++;
            i++;
            Log.d("Debug ", "Adicionei 1");
            Log.d("Debug ", users[i].getName());
        }
    }

    public User getUser(int i){
        return users[i];
    }

    public int getCount(){
        return count;
    }
}
