package com.project.taeil.mantomen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.taeil.mantomen.R;

import java.io.InputStream;
import java.util.List;

public class AllClassListAdapter extends BaseAdapter{

    private LayoutInflater inflate;
    private RecyclerView.ViewHolder viewHolder;
    private Context context;
    private List<AllClass> AllClass;
    private int count;
    VariableOfClass variableOfClass;

    public AllClassListAdapter(Context context, List<AllClass> AllClass){
        // 메인에서 데이터 리스트를 넘겨받음
        this.context = context;
        this.count = AllClass.size();
        this.AllClass = AllClass;
        notifyDataSetChanged();

    }

    public void upDateItemList(List<AllClass> allClass){
        this.AllClass = allClass;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
         //return count;   // 프래그먼트 실시간 수정에서 생기는 오류
         return AllClass.size();  //요거도 수정해보자 실시간으로 바뀌게 나중에 지금은 무섭
    }

    @Override
    public Object getItem(int i) {
        return AllClass.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.fitlist,null);

        // 클래스 확립될 동안 리스트뷰를 잠시꺼둠

//        ImageView ClassPicture = (ImageView) v.findViewById(R.id.ClassImg);  //사진은 아직 안넣음
//
        TextView ClassName= (TextView) v.findViewById(R.id.Home_ClassName);
        TextView ClassTutorID = (TextView) v.findViewById(R.id.Home_ClassTutorID);
        // ClassTuteeID 튜티들
//        TextView ClassTotalPeople = (TextView) v.findViewById(R.id.Home_ClassTotalPeople);  //클래스 수강인원
//        TextView ClassCurrentPeople = (TextView) v.findViewById(R.id.Home_ClassCurrentPeople);
        // ClassCurrentPeople 현재원
        TextView ClassRPeriod = (TextView) v.findViewById(R.id.Home_ClassRPeriod);  //신청기간

        TextView ClassPrice = v.findViewById(R.id.Home_ClassPrice);  // 강의가격
        // ClassOPeriod 강의기간
        RatingBar ClassScore = (RatingBar) v.findViewById(R.id.ClassScore); //임의로 정해둬 지금은
//        TextView ClassIntro = (TextView) v.findViewById(R.id.Home_ClassIntro);


        //ImageView MentorPhoto= (ImageView) v.findViewById(R.id.Mentor_Photo);



        new DownloadImageTask((ImageView) v.findViewById(R.id.ClassImg))
                .execute(AllClass.get(i).getClassPicture());

        Log.e("사진오류",AllClass.get(i).getClassPicture());

        ClassName.setText(AllClass.get(i).getClassName());
        ClassTutorID.setText("멘토 : " + AllClass.get(i).getClassTutorID());
//        ClassCurrentPeople.setText(AllClass.get(i).getClassCurrentPeople() + "신청");
//        ClassTotalPeople.setText(AllClass.get(i).getClassTotalPeople() + "모집");


        int price = Integer.parseInt(AllClass.get(i).getClassPrice());  // 인트형으로 바꾼후에
        String str = String.format("%,d", price);
        ClassPrice.setText('\uFFE6'+ str); // 가격


        // 현재원필요
        ClassRPeriod.setText(AllClass.get(i).getClassFirstTime()+"까지");   // 모집기간인데 퍼스트 수업 날 전까지로하자 !!!!
        // 강의기간 필요
        // ClassScore.setRating(AllClass.get(i).getClassScore());
        ClassScore.setRating(Float.parseFloat(AllClass.get(i).getClassScore()));  // 점수
//        ClassIntro.setText(AllClass.get(i).getClassIntro());
//        ClassPhoto.setImageResource(AllClass.get(i).getClassPhoto());  //사진없으니까 뺌
//        MentorPhoto.setImageResource(AllClass.get(i).getMentorPhoto());

        v.setTag(AllClass.get(i).getClassName());
        return v;
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
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }




}
