package com.padcmyanmar.sfc.activities;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.padcmyanmar.sfc.R;
import com.padcmyanmar.sfc.adapters.NewsImagesPagerAdapter;
import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.data.persistence.AppDatabase;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.utils.RoomQuery;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aung on 11/11/17.
 */

public class NewsDetailsActivity extends BaseActivity {

    private static final String EXTRA_ID = "newsId";

    @BindView(R.id.vp_news_details_images)
    ViewPager vpNewsDetailsImages;

    @BindView(R.id.tv_news_details)
    TextView tvNewsDetails;

    public static Intent newIntent(Context context, String id) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this, this);

        String newsId = getIntent().getStringExtra(EXTRA_ID);
        if (newsId == null) {
            return;
        }

        Toast.makeText(this, "newsId : " + newsId, Toast.LENGTH_SHORT).show();

        getLifecycle().addObserver(NewsModel.getInstance()
                .newWithId(newsId)
                .observe(news -> {
                    if (news != null && !news.isEmpty())
                        tvNewsDetails.setText(news.get(0).getDetails());
                }));

        NewsImagesPagerAdapter newsImagesPagerAdapter = new NewsImagesPagerAdapter(getApplicationContext());
        vpNewsDetailsImages.setAdapter(newsImagesPagerAdapter);
    }
}
