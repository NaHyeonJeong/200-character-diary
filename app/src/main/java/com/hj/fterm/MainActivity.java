package com.hj.fterm;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 옵션 메뉴 - 앱 소개, 만든사람 소개, 음악 재생 옵션(3가지 음악), 음악 멈춤
 * 이미지 뷰 - 뷰 플립 사용
 * 일기 작성 페이지 이동 버튼
 * 예전에 작성했던 일기 리스트 보는 페이지 이동 버튼
 */
public class MainActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    Button writeDiaryBtn, pastDiaryBtn;
    ViewFlipper vf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vf = (ViewFlipper)findViewById(R.id.viewFlipper);
        writeDiaryBtn = (Button)findViewById(R.id.writeDiaryBtn);
        pastDiaryBtn = (Button)findViewById(R.id.pastDiaryBtn);

        /**
         * 뷰 플립 사용하여 일정 시간 마다 이미지 바꿈
         * 다크 모드로 변하면 검은색 테두리가 보이지 않는 것을 막아보고자...
         */
        vf.setFlipInterval(2000);
        vf.startFlipping();
        vf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vf.stopFlipping();
            }
        });

        // 일기 작성 페이지로 이동
        writeDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteDiaryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        // 지난 일기를 확인할 수 있은 페이지로 이동
        pastDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PastDiaryListActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    // 옵션 메뉴 만들기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    // 옵션 메뉴 클릭 시 동작할 메소드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.infoApp: // 앱에 대한 소개
                View infoAppDialogView = (View)View.inflate(MainActivity.this, R.layout.introduce_app, null); // dialog
                AlertDialog.Builder infoAppDlg = new AlertDialog.Builder(MainActivity.this);
                // dialog 설정
                infoAppDlg.setTitle("일기장 앱을 소개합니다.");
                infoAppDlg.setIcon(R.drawable.diary);
                infoAppDlg.setView(infoAppDialogView);
                infoAppDlg.setNegativeButton("닫기", null);
                infoAppDlg.show();
                return true;
            case R.id.devPerson: // 만든 사람에 대한 소개
                View devPerDialogView = (View)View.inflate(MainActivity.this, R.layout.introduce_dev_person, null); // dialog
                AlertDialog.Builder devPerDlg = new AlertDialog.Builder(MainActivity.this);
                // dialog 설정
                devPerDlg.setTitle("개발자를 소개합니다.");
                devPerDlg.setIcon(R.drawable.diary);
                devPerDlg.setView(devPerDialogView);
                devPerDlg.setNegativeButton("닫기", null);
                devPerDlg.show();
                return true;
            case R.id.favoriteMusic: // 좋아하는 음악 재생
                if(mPlayer == null){
                    mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.favorite_music);
                    mPlayer.start();
                    Toast.makeText(getApplicationContext(), "사계 - 태연", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.christmasMusic: // 크리스마스 음악 재생
                if(mPlayer == null){
                    mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.christmas_music);
                    mPlayer.start();
                    Toast.makeText(getApplicationContext(), "All I Want for Christmas Is You - Mariah Garey", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.lightMusic: // 밝은 음악 재생
                if(mPlayer == null){
                    mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.light_music);
                    mPlayer.start();
                    Toast.makeText(getApplicationContext(), "2002 - Anne Marie", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.stopMusic: // 음악 멈춤
                if(mPlayer != null) {
                    mPlayer.stop();
                    mPlayer = null;
                }
                Toast.makeText(getApplicationContext(), "음악 재생 중지", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}