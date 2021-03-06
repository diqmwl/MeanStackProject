package com.project.taeil.mantomen;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class PlacticePostRequest extends AsyncTask<JSONObject, Void, String> {
    Activity activity;
    URL url;
    Variable variable;


    public PlacticePostRequest(Activity activity) {
        this.activity = activity;
    }


    @Override
    protected String doInBackground(JSONObject... postDataParams) {

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(10000 /* milliseconds */);
            conn.setRequestMethod("POST");

            String cookieString = variable.getCookies();
            conn.setRequestProperty("user", cookieString);
            Log.e("쿠키2",cookieString);

//            conn.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
//            conn.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
            conn.setDoInput(true);
            conn.setDoOutput(true);


            //서버로 보내기위해서 스트림 만듬
            OutputStream os = conn.getOutputStream();
            //버퍼를 생성하고 넣음
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

            String str = getPostDataString(postDataParams[0]);

            Log.e("params", "Post String = " + str);

            writer.write(str);

            writer.flush();
            writer.close(); //버퍼를 받아줌
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                //서버로 부터 데이터를 받음

//                final String COOKIES_HEADER = "Set_Cookie";
//                variable.setCookies(conn.getHeaderField(COOKIES_HEADER));   // 공통 쿠키 선언 로그인할 때 최초로 이제 보낼때 헤더에 쿠키만 보내면 됨 쉐어드 프레퍼런스를 활용
//                Log.e("쿠키",variable.getCookies());
//                List<String> cookies = conn.getHeaderFields().get("set-cookie"); // 쿠키 값 조회방법
//
//                if (cookies != null) {
//                    for (String cookie : cookies) {
//                        Log.d("@COOKIE", cookie.split(";\\s*")[0]);
//                    }
//                }


                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                Log.e("파람스", sb.toString());


                in.close();
                if (!sb.toString().trim().equals("0"))
                    SbExtraction(sb); // 스트링버퍼를 추출해서 세팅해줌
                return sb.toString(); //서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

            } else {
                return new String("Server Error : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
//          Toast.makeText(activity, result,
//                Toast.LENGTH_LONG).show();
        super.onPostExecute(result);

        variable = Variable.getInstance();
        String temp;
        String message1 = "아이디와 비밀번호를 확인해주세요";
        String message2 = "로그인성공";
//        temp = result.trim();
//        Log.d("오류", temp);
//
//        if (temp == null || temp.equals("0")) {
//            Toast.makeText(activity, message1,
//                    Toast.LENGTH_LONG).show();
//            return;
//        } else {
//            Toast.makeText(activity, temp,
//                    Toast.LENGTH_LONG).show();
//        }

    }

    private String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;
        variable = Variable.getInstance();

        Iterator<String> itr = params.keys();


        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (key.equals("userID"))
                variable.setUserID(value.toString());
            if (key.equals("userPassword"))
                variable.setUserPassword(value.toString());
            if (key.equals("userEmail")) {
                variable.setUserEmail(value.toString());   // 이메일 부분임 수정필요
                //variable.setUserEmail("수정필요한 이메일");   // 이메일 부분임 수정필요
            }
            if (key.equals("userGender"))
                variable.setUserGender(value.toString());
            if (key.equals("userName"))
                variable.setUserName(value.toString());
            if (key.equals("userAge"))
                variable.setUserAge(value.toString());
            if (key.equals("userCategory"))
                variable.setUserCategory(value.toString());
            if (key.equals("userIdentity"))
                variable.setUserIdentity(value.toString());
            if (key.equals("userParticipateClass"))
                variable.setUserParticipateClass(value.toString());
            if (key.equals("userOperateClass"))
                variable.setUserOperateClass(value.toString());
            if (key.equals("userPhoneNumber"))
                variable.setUserOperateClass(value.toString());


            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }


    private void SbExtraction(StringBuffer sb) {
        Variable variable = Variable.getInstance();

        String SB = sb.toString(); //일단 String버퍼를 스트링 형식으로 변형

        String userData[] = SB.split(",");
        String userValue[] = new String[userData.length]; //추출후에 담을거

        for (int i = 0; i < userData.length; i++) { //
            int idx = userData[i].indexOf(":");
            userValue[i] = userData[i].substring(idx + 2, userData[i].length() - 1);
            //userValue[i].replace("\"", ""); //처음이랑 마지막꺼는 버려야함 이상한 값임
        }

        variable.setUserID(userValue[1]);
        variable.setUserPassword(userValue[2]);
        variable.setUserEmail(userValue[3]);
        variable.setUserName(userValue[4]);
        variable.setUserAge(userValue[5]);
        variable.setUserGender(userValue[6]);
        variable.setUserCategory(userValue[7]);
        variable.setUserIdentity(userValue[8]);
        variable.setUserParticipateClass(userValue[9]);
        variable.setUserOperateClass(userValue[10]);
        variable.setUserPhoneNumber(userValue[11]);


        Log.e("추출", variable.getUserID());

    }


}
