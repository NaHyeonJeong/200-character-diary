package com.hj.fterm;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 리본적인 레이아웃은 write_diary와 유사
 * 다른 부분은 RatingBar의 정도를 바꿀 수 없음
 * 이전 화면(리스트)으로 이동하는 버튼
 * 메인 화면으로 이동하는 버튼
 */
public class PastDiaryActivity extends AppCompatActivity {

    RatingBar rb;
    TextView tv1, tv2;
    Button mainBtn, prevBtn;

    String date = "";
    float moodRating = 0;
    String story = "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_diary);

        rb = (RatingBar) findViewById(R.id.pastRatingBar);
        tv1 = (TextView) findViewById(R.id.pastDate);
        tv2 = (TextView) findViewById(R.id.pastStory);
        mainBtn = (Button)findViewById(R.id.back_to_main_btn);
        prevBtn = (Button) findViewById(R.id.back_to_list_btn);

        // 이전 화면에서 보내온 값을 받음
        Intent intent = getIntent();
        date = intent.getStringExtra("Date");
        moodRating = intent.getFloatExtra("MoodRating", 0);
        story = intent.getStringExtra("Story");

        System.out.println("rating값 : " + moodRating + " 내용 : " + story);

        // 가져온 내용으로 화면 꾸미기
        tv1.setText(date + " 일기");
        rb.setRating(moodRating);
        tv2.setText(story);

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이전 액티비티가 아닌 맨 처음 나온 액티비티로 이동하기 위함
                Intent intent = new Intent(PastDiaryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

}

