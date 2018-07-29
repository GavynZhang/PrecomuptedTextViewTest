package com.gavynzhang.precomuptedtextviewtest.ui;

import android.content.Context;
import android.support.v4.text.PrecomputedTextCompat;
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
import com.gavynzhang.precomuptedtextviewtest.util.ExecutorProvider;
import com.gavynzhang.precomuptedtextviewtest.util.TestSpan;
import com.gavynzhang.precomuptedtextviewtest.util.Util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PrecomputedTextActivity extends AppCompatActivity {

    private ListView mListView;
    private Button mButton;

    private AutoScrollHandler mAutoScrollHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precomputed_text);

        mListView = findViewById(R.id.pre_list_view);
        mButton = findViewById(R.id.pre_start_scroll_btn);
        mListView.setAdapter(new PreAdapter(this, ExecutorProvider.getExecutor()));

        mAutoScrollHandler = new AutoScrollHandler(mListView, Util.TEST_LIST_ITEM_COUNT);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAutoScrollHandler.startAutoScrollDown();
            }
        });
    }

    private static class PreAdapter extends BaseAdapter {
        private Context mContext;
        private Executor mExecutor;

        public PreAdapter(Context context, Executor executor) {
            mContext = context;
            mExecutor = executor;
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

                PreViewHolder viewHolder = new PreViewHolder();
                viewHolder.textView = (AppCompatTextView) convertView.findViewById(R.id.test_text_view);

                convertView.setTag(viewHolder);
            }

            PreViewHolder holder = (PreViewHolder) convertView.getTag();

            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, Util.fromDPtoPix(mContext, Util.TEXT_SIZE_DP));

            holder.textView.setTextFuture(
                    PrecomputedTextCompat.getTextFuture(
                            TestSpan.getSpanString(position),
                            holder.textView.getTextMetricsParamsCompat(),
                            mExecutor)
            );
            return convertView;
        }

        private class PreViewHolder {
            AppCompatTextView textView;
        }
    }
}
