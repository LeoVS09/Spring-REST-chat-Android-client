package org.hello.rest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import java.util.ArrayList;
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
import org.springframework.web.client.RestTemplate;

public class MainActivity extends ActionBarActivity implements View.OnKeyListener, View.OnClickListener {
    final String serverUrl = "http://188.114.217.43:9090";
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


//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
            postMessage();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        postMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_refresh) {
//            new HttpRequestTask().execute("lol");
//            return true;
//        }
        if (id == R.id.action_settings) {
            return true;
        }
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
            final String url = serverUrl + "/message";
            Map<String, String> vars = new HashMap<String, String>();
            vars.put("userLogin", username);
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                Message message = restTemplate.postForObject(url + "/{userLogin}",params[0], Message.class, vars);
                return message;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Message message) {
            messageAdapter.add(message);
            messagesView.setSelection(messageAdapter.getCount() - 1);
        }

    }
}
