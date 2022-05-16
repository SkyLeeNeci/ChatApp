package karpenko.diploma.chatapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<AwesomeMessage> {

    List<AwesomeMessage> messages;
    private Activity activity;

    public MessageAdapter(@NonNull Activity context, int resource, @NonNull List<AwesomeMessage> objects) {
        super(context, resource, objects);
        this.messages = objects;
        this.activity = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        AwesomeMessage awesomeMessage = getItem(position);
        int layoutResource = 0;
        int viewType = getItemViewType(position);

        if (viewType == 0){
            layoutResource = R.layout.your_message_item;
        }else layoutResource = R.layout.my_message_item;

        if (convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        }else {
            convertView = layoutInflater.inflate(layoutResource,
                    parent,false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        boolean isText = awesomeMessage.getImageUri() == null;

        if (isText){
            viewHolder.textTextView.setVisibility(View.VISIBLE);
            viewHolder.photoImageView.setVisibility(View.GONE);
            viewHolder.textTextView.setText(awesomeMessage.getText());
        }else {
            viewHolder.textTextView.setVisibility(View.GONE);
            viewHolder.photoImageView.setVisibility(View.VISIBLE);
            Glide.with(viewHolder.photoImageView.getContext())
                    .load(awesomeMessage.getImageUri())
                    .into(viewHolder.photoImageView);
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        int flag;
        AwesomeMessage awesomeMessage = messages.get(position);
        if (awesomeMessage.isMine()){
            flag = 0;
        }else flag = 1;
        return flag;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private class ViewHolder{
        private TextView textTextView;
        private ImageView photoImageView;

        public ViewHolder(View view){
            textTextView = view.findViewById(R.id.textTextView);
            photoImageView = view.findViewById(R.id.photoImageView);
        }

    }

}
