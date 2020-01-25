package com.example.user.myapplication4;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

        public ListView NameListView;
        public ProgressBar progressBarName;

        int value;

    //private ListAdapterClass adapter;
    private TextView choice1;
        List<names> namesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NameListView = (ListView)findViewById(R.id.listview1);

        /*ListAdapterClass adapter = new ListAdapterClass(this, R.layout.layout_items, namesList);


        NameListView.setAdapter(adapter);*/

        choice1 = (TextView)findViewById(R.id.choice_1);
        progressBarName = (ProgressBar)findViewById(R.id.progressBar);

        new GetHttpResponse(MainActivity.this).execute();

        NameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //value = Integer.parseInt(NameListView.getItemAtPosition(position).toString());
                view.setOnTouchListener(new ChoiceTouchListener());

                //display value here
            }
        });

        choice1.setOnDragListener(new ChoiceDragListener());

    }




    private final class ChoiceTouchListener implements View.OnTouchListener {

        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                //setup drag
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            }
            else
            {
                return false;
            }

        }

    }

    private class ChoiceDragListener implements View.OnDragListener
    {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            //handle drag events
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    //handle the dragged view being dropped over a drop view
                    //handle the dragged view being dropped over a target view
                    View view = (View) event.getLocalState();
                    //stop displaying the view where it was before it was dragged
                    view.setVisibility(View.INVISIBLE);
                    delete();
                    //namesList.remove(value);
                   // adapter.notifyDataSetChanged();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
        public void delete()
        {
            //variable in which listview item value is stored.



        }


    }




     //Fetching from database starts from here
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        String NameHolder;
        public String ServerURL = "http://192.168.0.14/MyApi/connection.php";
        List<names> namesList;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            HttpServicesClass httpServiceObject = new HttpServicesClass(ServerURL);
            try
            {
                httpServiceObject.ExecutePostRequest();

                if(httpServiceObject.getResponseCode() == 200)
                {
                    NameHolder = httpServiceObject.getResponse();

                    if(NameHolder != null)
                    {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(NameHolder);

                            JSONObject jsonObject;

                            names names;

                            namesList = new ArrayList<names>();

                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                names = new names();

                                jsonObject = jsonArray.getJSONObject(i);

                                names.Name = jsonObject.getString("name");

                                namesList.add(names);
                            }
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, httpServiceObject.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            //ListView NameListView = null;
            //ProgressBar progressBarName = null;


            progressBarName.setVisibility(View.GONE);

            NameListView.setVisibility(View.VISIBLE);

            if(namesList != null)
            {
                 ListAdapterClass adapter = new ListAdapterClass(namesList, context);

                 NameListView.setAdapter(adapter);
            }
        }
    }
}
