package com.example.taeil.mantomen;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class ClassDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        Intent intent = getIntent();
        String ClassName = intent.getStringExtra("ClassName"); // 클래스 네임을 넘겨받고


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //바텀바 연결

        switchFragment(0);  //홈버튼이 눌리면 0전송
    }


    final ClassIntromFragment classIntromFragment = new ClassIntromFragment();
//    final SearchFragment searchFragment = new SearchFragment();
//    final ChatFragment chatFragment = new ChatFragment();
//    final MypageFragment mypageFragment = new MypageFragment();
//    final Mypage2Fragment mypage2Fragment = new Mypage2Fragment();

    public void switchFragment(int id) {   //프래그먼트 교체 메인화면, 검색화면, 채팅화면, 마이페이지 총 네개의 프래그먼트 활용예정
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == 0)
            fragmentTransaction.replace(R.id.fragment2, classIntromFragment);
//        else if (id == 1)
//            fragmentTransaction.replace(R.id.fragment, searchFragment);
//        else if (id == 2)
//            fragmentTransaction.replace(R.id.fragment, chatFragment);
//        else if (id == 3)
//            //여기에 값을 받아오는 메소드를 추가
//            fragmentTransaction.replace(R.id.fragment, mypage2Fragment); //마이페이지2로연결
//        else if (id == 4)
//            fragmentTransaction.replace(R.id.fragment, mypageFragment); //마이페이지1로연결
        fragmentTransaction.commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation2_ClassIntro:
                    switchFragment(0);  //홈버튼이 눌리면 0전송
                    //navigationImageview.setVisibility(View.INVISIBLE);
                    return true;

//                case R.id.navigation2_ETC1:
//                    switchFragment(1); //서치버튼이 눌리면
//                    //181004 추후 메소드로 묶고 오류를 수정할 예정
//                    return true;
//
//
//                case R.id.navigation2_ClassReview:
//                    //회원정보를 가져오는 메소드 로그인할 때 이미 변수에 로그인 패스워드가 담겨있음 그걸가지고 이제 민영이한테서 정보를 받아올거임
//                    switchFragment(2); //마이페이지버튼이 눌리면
//                    return true;
            }
            return false;
        }
    };







}
