package daniel.broride;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

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

    public void fillUser(Context context){
        DbHelper myDb = DbHelper.getsInstance(context.getApplicationContext());
        Cursor res = myDb.getAllData();

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

    public User getUserById(int id){
        User user = null;
        for(int i=0; i<count; i++){
            if(users[i].getId()== id){
                return users[i];
            }
        }
       return user;
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

    public ArrayList<String> getAllData() {
        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i<count; i++){

            labels.add(users[i].getName());
        }

       return labels;
    }

    public int[] getAllId(){
        Log.d("Debug", "Entrei no metodo pra retornar todos ids");
        int[] array = new int[count];
        Log.d("count value",""+count);
        for (int i = 0; i < count; i++) {
            array[i] = users[i].getId();
            Log.d("Debug", ""+array[i]);
        }

        return  array;
    }
}
