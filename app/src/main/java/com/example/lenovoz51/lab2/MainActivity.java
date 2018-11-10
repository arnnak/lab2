package com.example.lenovoz51.lab2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements RequestOperator.RequestOperatorListener{

    Button sendRequestButton;
    TextView title;
    TextView bodyText;
    TextView size;
    private ModelPost publication;
    private IndicationgView indicator;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendRequestButton = (Button)findViewById(R.id.send_request);
        sendRequestButton.setOnClickListener(requestButtonClicked);

        title = (TextView)findViewById(R.id.title);
        bodyText = (TextView)findViewById(R.id.body_text);
        indicator = (IndicationgView)findViewById(R.id.generated_graphic);
        size = (TextView)findViewById(R.id.number);

        pb = (ProgressBar)findViewById(R.id.bar);
        pb.setVisibility(ProgressBar.INVISIBLE);

    }

    View.OnClickListener requestButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new DelayTask().execute();
            //sendRequest();
            //pb.setVisibility(ProgressBar.INVISIBLE);
        }
    };

    private void sendRequest(){
        RequestOperator ro = new RequestOperator();
        ro.setListener(this);
        setIndicatorStatus(IndicationgView.executing);
        updatePublication();
        ro.start();
    }

    public void updatePublication(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(publication != null){
                    title.setText(publication.getTitle());
                    bodyText.setText(publication.getBodyText());
                    size.setText(publication.getSize()+"");
                }else{
                    title.setText("");
                    bodyText.setText("");
                    size.setText(0+"");
                }
            }
        });
    }

    @Override
    public void success(ModelPost publication) {
        this.publication = publication;
        setIndicatorStatus(IndicationgView.success);
        updatePublication();
    }

    @Override
    public void failed(int responseCode) {
        this.publication = null;
        setIndicatorStatus(IndicationgView.failed);
        updatePublication();
    }

    public void setIndicatorStatus(final int status){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                indicator.setState(status);
                indicator.invalidate();
            }
        });
    }

    public class DelayTask extends AsyncTask<Void, Integer, String> {
        int count = 0;

        @Override
        protected void onPreExecute() {
            pb.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            sendRequest();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Complete";
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pb.setVisibility(ProgressBar.INVISIBLE);
        }

       /* @Override
        protected void onProgressUpdate(Integer... values) {
            pb.setProgress(values[0]);
        }*/

    }
}
