package todolist.yassine.com.todolist;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import todolist.yassine.com.todolist.data.DatabaseHandler;
import todolist.yassine.com.todolist.model.MyList;

public class ShowToDoActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<MyList> mlist = new ArrayList<>();
    private DatabaseHandler dbh;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_to_do);
        
        mListView = (ListView) findViewById(R.id.listId);
        
        refreshData();

    }

    private void refreshData() {

        mlist.clear();

        dbh = new DatabaseHandler(getApplicationContext());

        final ArrayList<MyList> listFromDB = dbh.getToDo();

        for (int i = 0; i < listFromDB.size(); i++){

            String title = listFromDB.get(i).getTitle();
            int id = listFromDB.get(i).getId();

            MyList list = new MyList();

            list.setTitle(title);
            list.setId(id);

            mlist.add(list);

        }

        dbh.close();

        adapter = new CustomAdapter(ShowToDoActivity.this, R.layout.list_row, mlist);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public class CustomAdapter extends ArrayAdapter<MyList>{

        Activity activity;
        int layoutRessource;
        MyList lists;
        private ArrayList<MyList> data = new ArrayList<>();

        public CustomAdapter(Activity act, int resource, ArrayList<MyList> mData) {
            super(act, resource, mData);

            activity = act;
            layoutRessource = resource;
            data = mData;

            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Nullable
        @Override
        public MyList getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getPosition(MyList item) {
            return super.getPosition(item);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            ViewHolder holder = null;

            if (row == null || (row.getTag() == null)){

                LayoutInflater inflater = LayoutInflater.from(activity);
                row = inflater.inflate(layoutRessource, null);

                holder = new ViewHolder();

                holder.mTitle = (TextView) row.findViewById(R.id.imageViewTitleId);
                holder.id = (ImageView) row.findViewById(R.id.imageViewDeleteId);

                row.setTag(holder);

            }else {

                holder = (ViewHolder) row.getTag();
            }

            holder.myLists = getItem(position);

            holder.mTitle.setText(holder.myLists.getTitle());
            holder.id.setId(holder.myLists.getId());
            Log.v("id", String.valueOf(holder.myLists.getId()));

            final ViewHolder finalHolder = holder;

            holder.id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dbh.deleteToDo(finalHolder.myLists.getId());

                    Toast.makeText(getApplicationContext(), "Tâche supprimé", Toast.LENGTH_LONG).show();

                    refreshData();
                }
            });

            return row;
        }

        class ViewHolder{

            MyList myLists;
            TextView mTitle;
            ImageView id;
        }
    }
}
