package org.hello.rest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.view.KeyEvent;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends ActionBarActivity implements View.OnKeyListener, View.OnClickListener {

    MessageAdapter messageAdapter;
    ListView messagesView;
    EditText messageInput;
    Button sendButton;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  Костыль
        //     |
        //    \|/    - не исправлять
        //     V
        username = "Trol";

        messageInput = (EditText) findViewById(R.id.message_input);
        messageInput.setOnKeyListener(this);

        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);

        messageAdapter = new MessageAdapter(this, new ArrayList<Message>());
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)
            postMessage();
        return true;
    }

    @Override
    public void onClick(View v) {
        postMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    private void postMessage()  {
        String text = messageInput.getText().toString();

        if (text.equals("")) {
            return;
        }
        Message message = new Message();
        message.setText(text);
        message.setName(username);
        message.setTime(new Date().getTime());
        new HttpRequestTask().execute(message);

    }

    private class HttpRequestTask extends AsyncTask<Message, Void, Message> {


        @Override
        protected Message doInBackground(Message... params) {

            ArrayList<String> vars = new ArrayList<>();
            try {

                RestRequest rest = new RestRequest();
                //rest.in  определят что отсылка произойдет по адресу http://{host}/message/{username} и возврашает rest
                //rest.send отсылает обьект params[0] и возврашает обьект типа Message.class
                //params[0].setText(rest.in("registration", username).headStatus(params[0].getText()));
                //Log.i("lol",rest.in("registration", username).headStatus(params[0].getText()));
                User us = new User(params[0].getText(),"lol");
                params[0].setText(rest.in("user/login", username).headEntity(us, String.class).getBody().toString());
                //params[0].setText(us.getPassword());
                return params[0];
            } catch (Exception e) {
                params[0].setText(e.getMessage());
            }

            return params[0];
        }

        @Override
        protected void onPostExecute(Message message) {
            messageAdapter.add(message);
            messagesView.setSelection(messageAdapter.getCount() - 1);
        }

    }
}
