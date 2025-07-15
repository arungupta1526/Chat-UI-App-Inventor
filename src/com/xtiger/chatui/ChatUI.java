// ChatUI.java Created by white_tiger.
// This file is part of the Chat-UI-App-Inventor project.
// It is an extension component for creating a chat UI in App Inventor.
// Created using Rush, and now migrate from Rush to "Fast-CLI" a tool for building App Inventor extensions.

// Migrated by: [Arun Gupta] 
// [https://community.appinventor.mit.edu/u/prem_gupta/summary] 
// [https://www.telegram.me/ArunGupta1526]
// Date: [15/07/2025]

package com.xtiger.chatui;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.common.*;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.FrameLayout;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.util.TypedValue;
import android.os.Handler;
import android.view.Gravity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.net.URL;
import java.io.InputStream;
import java.io.File;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.List;

@DesignerComponent(version = 2, versionName = "2.0", description = "Extension component for ChatUI. Created using Fast-cli."
        +
        "<br>Extension Post Link: <a href='https://community.appinventor.mit.edu/t/chatui-simple-chat-interface-extension/122943/'>Chat UI Extension by white_tiger</a>"
        +
        "<br>For more details, visit the <a href='https://github.com/himalayanxtiger/Chat-UI-App-Inventor'>developer's Github Repo</a>.", iconName = "icon.png", helpUrl = "https://community.appinventor.mit.edu/u/white_tiger/summary")

public class ChatUI extends AndroidNonvisibleComponent implements Component {

    private ComponentContainer container;
    private LinearLayout chatContainer;
    private ScrollView scrollView;
    private int sentMessageColor = Color.parseColor("#0084ff");
    private int receivedMessageColor = Color.parseColor("#f0f0f0");
    private int sentTextColor = Color.WHITE;
    private int receivedTextColor = Color.BLACK;
    private float messageCornerRadius = 20f;
    private int avatarSize = 40;
    private int messageMaxWidth = 250;
    private int messageHorizontalPadding = 16;
    private int messageVerticalPadding = 12;
    private boolean showTimestamp = true;
    private boolean showReadStatus = true;
    private String fontFamily = "sans-serif";
    private int fontSize = 14;
    private boolean showTypingIndicator = false;
    private Handler typingHandler = new Handler();
    private Runnable typingRunnable;
    private Typeface typeface;
    private List<View> selectedMessages;
    private TextView typingIndicatorView;

    public ChatUI(ComponentContainer container) {
        super(container.$form());
        this.container = container;
        selectedMessages = new ArrayList<>();
    }

    @SimpleFunction(description = "Initialize the chat UI in a VerticalArrangement")
    public void Initialize(VerticalArrangement arrangement) {
        chatContainer = new LinearLayout(container.$context());
        chatContainer.setOrientation(LinearLayout.VERTICAL);

        scrollView = new ScrollView(container.$context());
        scrollView.addView(chatContainer);

        FrameLayout frameLayout = (FrameLayout) arrangement.getView();
        frameLayout.addView(scrollView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
    }

    @SimpleFunction(description = "Send a message")
    public void Send(String message, String avatarUrl, String senderName, String timestamp) {
        addMessageToUI(message, avatarUrl, true, senderName, timestamp);
    }

    @SimpleFunction(description = "Receive a message")
    public void Receive(String message, String avatarUrl, String receiverName, String timestamp) {
        addMessageToUI(message, avatarUrl, false, receiverName, timestamp);
    }

    @SimpleFunction(description = "Add a submessage to indicate someone joined the chat")
    public void AddSubmessage(String message) {
        TextView submessageView = new TextView(container.$context());
        submessageView.setText(message);
        submessageView.setTextColor(Color.GRAY);
        submessageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize - 2);
        submessageView.setGravity(Gravity.CENTER);
        submessageView.setPadding(16, 8, 16, 8);

        chatContainer.addView(submessageView);
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }

    @SimpleFunction(description = "Show typing indicator")
    public void ShowTypingIndicator() {
        showTypingIndicator = true;
        addTypingIndicator();
    }

    @SimpleFunction(description = "Hide typing indicator")
    public void HideTypingIndicator() {
        showTypingIndicator = false;
        removeTypingIndicator();
    }

    @SimpleFunction(description = "Delete a message by index (starting from 1)")
    public void DeleteMessage(int index) {
        if (index > 0 && index <= chatContainer.getChildCount()) {
            chatContainer.removeViewAt(index - 1);
        }
    }

    @SimpleProperty(description = "Set the color for sent messages")
    public void SentMessageColor(int color) {
        sentMessageColor = color;
    }

    @SimpleProperty(description = "Set the color for received messages")
    public void ReceivedMessageColor(int color) {
        receivedMessageColor = color;
    }

    @SimpleProperty(description = "Set the text color for sent messages")
    public void SentTextColor(int color) {
        sentTextColor = color;
    }

    @SimpleProperty(description = "Set the text color for received messages")
    public void ReceivedTextColor(int color) {
        receivedTextColor = color;
    }

    @SimpleProperty(description = "Set the corner radius for message bubbles")
    public void MessageCornerRadius(float radius) {
        messageCornerRadius = radius;
    }

    @SimpleProperty(description = "Set the size of avatar images")
    public void AvatarSize(int size) {
        avatarSize = size;
    }

    @SimpleProperty(description = "Set the maximum width of message bubbles")
    public void MessageMaxWidth(int width) {
        messageMaxWidth = width;
    }

    @SimpleProperty(description = "Set the horizontal padding of message bubbles")
    public void MessageHorizontalPadding(int padding) {
        messageHorizontalPadding = padding;
    }

    @SimpleProperty(description = "Set the vertical padding of message bubbles")
    public void MessageVerticalPadding(int padding) {
        messageVerticalPadding = padding;
    }

    @SimpleProperty(description = "Show or hide message timestamps")
    public void ShowTimestamp(boolean show) {
        showTimestamp = show;
    }

    @SimpleProperty(description = "Show or hide read status for sent messages")
    public void ShowReadStatus(boolean show) {
        showReadStatus = show;
    }

    @SimpleProperty(description = "Set the font family for messages")
    public void FontFamily(String typefacePath) {
        loadTypeface(typefacePath);
    }

    @SimpleProperty(description = "Set the font size for messages")
    public void FontSize(int size) {
        fontSize = size;
    }

    @SimpleEvent(description = "Event raised when a profile picture is clicked")
    public void ProfilePictureClicked(String name) {
        EventDispatcher.dispatchEvent(this, "ProfilePictureClicked", name);
    }

    @SimpleEvent(description = "Event raised when a message is selected")
    public void MessageSelected(String message, int index) {
        EventDispatcher.dispatchEvent(this, "MessageSelected", message, index);
    }

    private void loadTypeface(String typefacePath) {
        try {
            if (isCompanion()) {
                final String packageName = form.getPackageName();
                final String platform = packageName.contains("makeroid")
                        ? "Makeroid"
                        : packageName.contains("Niotron")
                                ? "Niotron"
                                : packageName.contains("Appzard")
                                        ? "Appzard"
                                        : "AppInventor";
                typefacePath = android.os.Build.VERSION.SDK_INT > 28
                        ? "/storage/emulated/0/Android/data/" + packageName + "/files/assets/" + typefacePath
                        : "/storage/emulated/0/" + platform + "/assets/" + typefacePath;
                typeface = Typeface.createFromFile(new File(typefacePath));
            } else {
                typeface = Typeface.createFromAsset(form.$context().getAssets(), typefacePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            typeface = Typeface.DEFAULT;
        }
    }

    private void addMessageToUI(String message, String avatarUrl, boolean isSent, String name, String timestamp) {
        LinearLayout messageLayout = new LinearLayout(container.$context());
        messageLayout.setOrientation(LinearLayout.VERTICAL);
        messageLayout.setPadding(0, 8, 0, 8);

        TextView nameView = createNameView(name, isSent);
        messageLayout.addView(nameView);

        LinearLayout contentLayout = new LinearLayout(container.$context());
        contentLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView avatarView = createAvatarView(name);
        loadAvatarImage(avatarView, avatarUrl);
        TextView messageView = createMessageView(message, isSent);
        TextView timeView = createTimeView(timestamp);
        TextView statusView = createStatusView(isSent);

        LinearLayout textLayout = new LinearLayout(container.$context());
        textLayout.setOrientation(LinearLayout.VERTICAL);

        if (isSent) {
            textLayout.addView(messageView);
            if (showTimestamp)
                textLayout.addView(timeView);
            if (showReadStatus)
                textLayout.addView(statusView);
            contentLayout.addView(textLayout);
            contentLayout.addView(avatarView);
            contentLayout.setGravity(Gravity.RIGHT);
        } else {
            contentLayout.addView(avatarView);
            textLayout.addView(messageView);
            if (showTimestamp)
                textLayout.addView(timeView);
            contentLayout.addView(textLayout);
            contentLayout.setGravity(Gravity.LEFT);
        }

        messageLayout.addView(contentLayout);
        chatContainer.addView(messageLayout);
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));

        setupLongClickListener(messageLayout, message);
    }

    private TextView createNameView(String name, boolean isSent) {
        TextView nameView = new TextView(container.$context());
        nameView.setText(name);
        nameView.setTextColor(isSent ? sentTextColor : receivedTextColor);
        nameView.setTypeface(typeface != null ? typeface : Typeface.DEFAULT_BOLD);
        nameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize - 2);
        nameView.setPadding(isSent ? 0 : avatarSize + 16, 4, isSent ? avatarSize + 16 : 0, 4);
        nameView.setGravity(isSent ? Gravity.RIGHT : Gravity.LEFT);
        return nameView;
    }

    private ImageView createAvatarView(final String name) {
        ImageView avatarView = new ImageView(container.$context());
        int avatarSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, avatarSize,
                container.$context().getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(avatarSizePx, avatarSizePx);
        params.setMargins(8, 0, 8, 0);
        avatarView.setLayoutParams(params);
        avatarView.setOnClickListener(v -> ProfilePictureClicked(name));
        return avatarView;
    }

    private void loadAvatarImage(ImageView avatarView, String avatarUrl) {
        if (avatarUrl.startsWith("http://") || avatarUrl.startsWith("https://")) {
            new DownloadImageTask(avatarView).execute(avatarUrl);
        } else {
            Bitmap avatarBitmap = BitmapFactory.decodeFile(avatarUrl);
            avatarView.setImageBitmap(avatarBitmap);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                bmImage.setImageBitmap(result);
            }
        }
    }

    private TextView createMessageView(String message, boolean isSent) {
        TextView messageView = new TextView(container.$context());
        messageView.setText(message);
        messageView.setTextColor(isSent ? sentTextColor : receivedTextColor);
        messageView.setPadding(messageHorizontalPadding, messageVerticalPadding, messageHorizontalPadding,
                messageVerticalPadding);
        messageView.setTypeface(typeface != null ? typeface : Typeface.DEFAULT);
        messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        GradientDrawable shape = new GradientDrawable();
        shape.setColor(isSent ? sentMessageColor : receivedMessageColor);
        shape.setCornerRadius(messageCornerRadius);
        messageView.setBackground(shape);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(4, 2, 4, 2);
        messageView.setLayoutParams(params);

        int maxWidthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, messageMaxWidth,
                container.$context().getResources().getDisplayMetrics());
        messageView.setMaxWidth(maxWidthPx);
        return messageView;
    }

    private TextView createTimeView(String timestamp) {
        TextView timeView = new TextView(container.$context());
        timeView.setText(timestamp != null && !timestamp.isEmpty() ? timestamp : getCurrentTime());
        timeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        timeView.setTextColor(Color.GRAY);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 2, 8, 2);
        timeView.setLayoutParams(params);

        return timeView;
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private TextView createStatusView(boolean isSent) {
        TextView statusView = new TextView(container.$context());
        if (isSent) {
            statusView.setText(Html.fromHtml("&#x2713;&#x2713;")); // Double check mark
            statusView.setTextColor(Color.BLUE);
        }
        statusView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 2, 8, 2);
        statusView.setLayoutParams(params);

        return statusView;
    }

    private void addTypingIndicator() {
        if (typingIndicatorView == null) {
            typingIndicatorView = new TextView(container.$context());
            typingIndicatorView.setTextColor(Color.GRAY);
            typingIndicatorView.setPadding(16, 8, 16, 8);
            chatContainer.addView(typingIndicatorView);
        }

        if (typingRunnable != null) {
            typingHandler.removeCallbacks(typingRunnable);
        }

        typingRunnable = new Runnable() {
            private int dotCount = 0;

            @Override
            public void run() {
                if (showTypingIndicator) {
                    StringBuilder dots = new StringBuilder();
                    for (int i = 0; i < dotCount; i++) {
                        dots.append(".");
                    }
                    typingIndicatorView.setText("Typing" + dots.toString());
                    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));

                    dotCount = (dotCount + 1) % 4;
                    typingHandler.postDelayed(this, 500);
                } else {
                    removeTypingIndicator();
                }
            }
        };

        typingHandler.post(typingRunnable);
    }

    private void removeTypingIndicator() {
        if (typingRunnable != null) {
            typingHandler.removeCallbacks(typingRunnable);
        }
        if (typingIndicatorView != null) {
            chatContainer.removeView(typingIndicatorView);
            typingIndicatorView = null;
        }
    }

    @SimpleFunction(description = "Enable or disable link detection in messages")
    public void EnableLinkDetection(boolean enable) {
        for (int i = 0; i < chatContainer.getChildCount(); i++) {
            View view = chatContainer.getChildAt(i);
            if (view instanceof LinearLayout) {
                LinearLayout messageLayout = (LinearLayout) view;
                for (int j = 0; j < messageLayout.getChildCount(); j++) {
                    View childView = messageLayout.getChildAt(j);
                    if (childView instanceof TextView) {
                        TextView messageView = (TextView) childView;
                        if (enable) {
                            Linkify.addLinks(messageView, Linkify.WEB_URLS);
                            messageView.setMovementMethod(LinkMovementMethod.getInstance());
                        } else {
                            messageView.setAutoLinkMask(0);
                            messageView.setMovementMethod(null);
                        }
                    }
                }
            }
        }
    }

    private void setupLongClickListener(View messageLayout, final String message) {
        messageLayout.setOnLongClickListener(v -> {
            toggleMessageSelection(messageLayout, message);
            return true;
        });
    }

    private void toggleMessageSelection(View messageLayout, String message) {
        int index = chatContainer.indexOfChild(messageLayout) + 1; // Adding 1 to start index from 1
        if (selectedMessages.contains(messageLayout)) {
            selectedMessages.remove(messageLayout);
            messageLayout.setBackgroundColor(Color.TRANSPARENT);
        } else {
            selectedMessages.add(messageLayout);
            messageLayout.setBackgroundColor(Color.LTGRAY);
        }
        MessageSelected(message, index);
    }

    @SimpleFunction(description = "Clear all selected messages")
    public void ClearSelection() {
        for (View view : selectedMessages) {
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        selectedMessages.clear();
    }

    @SimpleFunction(description = "Get the number of selected messages")
    public int GetSelectedCount() {
        return selectedMessages.size();
    }

    @SimpleFunction(description = "Delete all selected messages")
    public void DeleteSelectedMessages() {
        for (View view : selectedMessages) {
            chatContainer.removeView(view);
        }
        selectedMessages.clear();
    }

    @SimpleFunction(description = "Get the total number of messages")
    public int GetMessageCount() {
        return chatContainer.getChildCount();
    }

    @SimpleFunction(description = "Clear all messages")
    public void ClearAllMessages() {
        chatContainer.removeAllViews();
        selectedMessages.clear();
    }

    private boolean isCompanion() {
        return container.$context().getPackageName().contains("makeroid") ||
                container.$context().getPackageName().contains("Niotron") ||
                container.$context().getPackageName().contains("Appzard") ||
                container.$context().getPackageName().contains("aicompanion3");
    }
}