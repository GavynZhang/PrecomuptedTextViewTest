package com.gavynzhang.precomuptedtextviewtest.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gavynzhang.precomuptedtextviewtest.AutoScrollHandler;
import com.gavynzhang.precomuptedtextviewtest.R;
import com.gavynzhang.precomuptedtextviewtest.util.TestSpan;
import com.gavynzhang.precomuptedtextviewtest.util.Util;

import java.util.concurrent.CopyOnWriteArrayList;

public class NormalTextActivity extends AppCompatActivity {

    private ListView mListView;
    private Button mStartScrollBtn;
    private AutoScrollHandler mAutoScrollHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_text);

        mListView = findViewById(R.id.norman_recycler_view);
        mStartScrollBtn = findViewById(R.id.normal_start_scroll_down);

        mListView.setAdapter(new NormalAdapter(this));

        mAutoScrollHandler = new AutoScrollHandler(mListView, Util.TEST_LIST_ITEM_COUNT);

        mStartScrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAutoScrollHandler.startAutoScrollDown();
//                mListView.smoothScrollBy(0,Util.AUTO_SCROLL_STEP);
            }
        });
    }

    private static class NormalAdapter extends BaseAdapter {
        private Context mContext;

        public NormalAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return Util.TEST_LIST_ITEM_COUNT;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_normal, viewGroup, false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.test_text_view);

                convertView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.textView.setText(TestSpan.getSpanString(position));
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, Util.fromDPtoPix(mContext, Util.TEXT_SIZE_DP));

            return convertView;
        }

        private class ViewHolder {
            TextView textView;
        }
    }

}
