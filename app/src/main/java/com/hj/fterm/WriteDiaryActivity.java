package com.hj.fterm;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

/**
 * 날짜 선택 DatePicker
 * 기분 정도를 나타내는 RatingBar
 * 일기 작성하는 EditText
 * 일기를 저장하는 명령 수행하는 Button
 * 메인 화면으로 이동하는 Button
 */
public class WriteDiaryActivity extends AppCompatActivity {

    String tableName = "diary";

    Button mainBtn, saveBtn;
    TextView selDateTv, countTxtTv;
    EditText editText;
    RatingBar ratingBar;

    String dateMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_diary);

        // 메인 화면으로 이동하는 버튼
        mainBtn = (Button)findViewById(R.id.toMainBtn);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        // EditText 의 문자 개수 파악
        countTxtTv = (TextView)findViewById(R.id.cntText);
        editText = (EditText)findViewById(R.id.writeDiary);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String cntText = editText.getText().toString();
                countTxtTv.setText(cntText.length() + " / 200자");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 일기에 대한 정보를 db에 저장, 저장 후 메인 화면으로 이동
        saveBtn = (Button)findViewById(R.id.saveDiary);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateInfo = dateMessage; // 날짜 정보를 문자열로 가져옴
                System.out.println("dateInfo : " + dateInfo);
                double moodInfo = ratingBar.getRating(); // 기분의 정도를 수치로 가져옴
                System.out.println("moodInfo : " + moodInfo);
                String storyInfo = editText.getText().toString(); // 일기 내용을 가져옴
                System.out.println("storyInfo : " + storyInfo);

                // DB 에 정보 저장 - 단 이미 있는 날짜의 경우에는 구경을 유도하도록...
                saveData(dateInfo, moodInfo, storyInfo);
                finish();
                overridePendingTransition(0, 0); // 화면 전환 애니메이션 없애기 위함
            }
        });
    }

    private void saveData(String dateInfo, double moodInfo, String storyInfo) {
        String sql = "insert into " + tableName +
                "(date, mood, story) values ('" + dateInfo + "', " + moodInfo + ", '" + storyInfo + "');";
        UseDatabase database = UseDatabase.getInstance(this);
        database.open();

        if(database.execSQL(sql) == true){
            View toastView = (View) View.inflate(WriteDiaryActivity.this, R.layout.save_toast, null);
            Toast toast = new Toast(WriteDiaryActivity.this);
            toast.setView(toastView);
            toast.show();
        }
        else
            Toast.makeText(getApplicationContext(), "해당 날짜의 일기는 이미 있습니다. 구경해보세요.", Toast.LENGTH_SHORT).show();
        database.close();
    }

    // 날짜(달력) 관련 메소드
    public void tabDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public void processDatePickerResult(int year, int month, int day) {
        String year_string = Integer.toString(year);
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        dateMessage = (year_string + "년 " + month_string + "월 " + day_string + "일");

        selDateTv = (TextView)findViewById(R.id.selectDate);
        selDateTv.setVisibility(View.VISIBLE);
        selDateTv.setText("일기를 작성할 날짜 : " + dateMessage);
    }

}
