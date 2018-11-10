package com.example.lenovoz51.lab2;

import android.view.Display;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo Z51 on 2018-11-06.
 */

public class RequestOperator extends Thread {

    private List<String> re = new ArrayList<>();
    public  interface RequestOperatorListener{
        void success(ModelPost publication);
        void failed(int responseCode);
    }

    private RequestOperatorListener listener;
    private int responseCode;
    private int i =0;


    public void setListener (RequestOperatorListener listener){
        this.listener = listener;
    }

    public void run(){
        super.run();
        try{
            ModelPost publication = request();

            if(publication != null){
                success(publication);
            }
            else{
                failed(responseCode);
            }
        }catch (IOException e){
            failed(-1);
        }catch (JSONException e){
            failed(-2);
        }
    }

    private ModelPost request() throws IOException, JSONException{
        URL obj = new URL("http://jsonplaceholder.typicode.com/posts");
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Context-Type","application/json");
        responseCode = con.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        InputStreamReader streamReader;

        if(responseCode == 200){
            streamReader = new InputStreamReader(con.getInputStream());
        }else{
            streamReader = new InputStreamReader(con.getErrorStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuffer response = new StringBuffer();

        while((inputLine = in.readLine()) != null){
            //System.out.println("line"+inputLine.toString());
            if(inputLine.toCharArray()[0] !='[' && inputLine.toCharArray()[0] !=']') {
                //System.out.println("line"+inputLine.toString());
                response = new StringBuffer();
                response.append(inputLine);
                for(int i = 0; i < 5; i++) {
                    response.append(in.readLine());
                }
                System.out.println(response.toString());
                re.add(response.toString());
            }
        }
        in.close();

        //System.out.println(response.toString());

        if(responseCode == 200){
            //return parsingJsonObject(response.toString());
            return parsingJsonObject(re);
        }else{
            return null;
        }
    }

    public ModelPost parsingJsonObject(List<String> res) throws  JSONException{
        //JSONArray array = new JSONArray(re);
        JSONObject object = new JSONObject(res.get(0));
        ModelPost post = new ModelPost();

        post.setId(object.optInt("id",0));
        post.setUserId(object.optInt("userId",0));

        post.setTitle(object.getString("title"));
        post.setBodyText(object.getString("body"));
        post.setSize(res.size());
        //System.out.println(post.getSize());

        return post;
    }

    private void failed(int code){
        if(listener!= null){
            listener.failed(code);
        }
    }

    private void success(ModelPost publication){
        if(listener!=null){
            listener.success(publication);
        }
    }

}
