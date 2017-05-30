package tinkoff.androidcourse.govorun.ui.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import tinkoff.androidcourse.govorun.R;


/**
 * Created by goto1134
 * on 02.04.2017.
 */
public class MessageSender extends FrameLayout implements TextWatcher, View.OnClickListener {

    private EditText messageEditText;
    private ImageButton sendButton;
    private MessageSentListener messageSentListener;

    public MessageSender(Context context) {
        super(context);
    }

    public MessageSender(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MessageSender(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext())
                      .inflate(R.layout.widget_message_sender, this);
        messageEditText = (EditText) findViewById(R.id.ms_message_text);
        sendButton = (ImageButton) findViewById(R.id.ms_send_button);
        sendButton.setOnClickListener(this);
        messageEditText.addTextChangedListener(this);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        messageEditText.setEnabled(enabled);
        sendButton.setEnabled(enabled);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        sendButton.setVisibility(s.length() > 0 ? VISIBLE : GONE);
    }

    @Override
    public void onClick(View v) {
        if (messageSentListener != null) {
            String text = messageEditText.getText()
                                         .toString();
            messageSentListener.OnMessageSent(text);
            messageEditText.setText("");
        } else {
            throw new IllegalStateException("No message sent listener");
        }
    }

    public void setMessageSentListener(MessageSentListener messageSentListener) {
        this.messageSentListener = messageSentListener;
    }

    public interface MessageSentListener {
        void OnMessageSent(String aMessage);
    }
}
