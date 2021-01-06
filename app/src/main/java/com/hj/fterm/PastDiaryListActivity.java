package com.hj.fterm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * 지난 일기를 리스트로 보여주는 페이지
 * 특정 날짜의 일기를 클릭하면 새로운 페이지로 해당 날짜의 일기를 보여줌
 */
public class PastDiaryListActivity extends AppCompatActivity {

    String tableName = "diary";

    String savedDate = "";
    float moodRating = 0;
    String story = "";
    ArrayList<String> diaryList;
    ArrayAdapter<String> diaryListAdapter;
    ArrayList<Float> moodList;
    ArrayList<String> storyList;

    ListView diaryListView;
    Button mainBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_diary_list);

        diaryListView = (ListView)findViewById(R.id.pastDiaryList);
        String sql = "select * from " + tableName + ";";
        int count;

        UseDatabase database = UseDatabase.getInstance(this);
        database.open();

        diaryList = new ArrayList<String>();
        moodList = new ArrayList<Float>();
        storyList = new ArrayList<String>();

        if(database != null){
            Cursor outCursor = database.rawQuery(sql);
            count = outCursor.getCount();
            System.out.println("record count = " + count);

            for(int i = 0; i < count; i++) {
                outCursor.moveToNext();
                savedDate = outCursor.getString(0);
                moodRating = outCursor.getFloat(1);
                story = outCursor.getString(2);

                diaryList.add(savedDate + " 일기");
                moodList.add(moodRating);
                storyList.add(story);
            }
            /* 리스트뷰 만들기 */
            diaryListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, diaryList);
            diaryListView.setAdapter(diaryListAdapter);
        }

        diaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 또 다른 페이지에서 과거 일기 보여주기...
                Toast.makeText(getApplicationContext(), diaryList.get(i), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PastDiaryActivity.class);
                // 해당 날짜의 값을 새로운 페이지로 보내줌
                intent.putExtra("Date", diaryList.get(i));
                intent.putExtra("MoodRating", moodList.get(i));
                intent.putExtra("Story", storyList.get(i));
                startActivity(intent);
                overridePendingTransition(0, 0); // 화면 전환 애니메이션 없앰
            }
        });

        // 메인 화면으로 이동하는 버튼
        mainBtn = (Button)findViewById(R.id.toMainBtn);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0); // 화면 전환 애니메이션 없애기 위함
            }
        });
    }
}
