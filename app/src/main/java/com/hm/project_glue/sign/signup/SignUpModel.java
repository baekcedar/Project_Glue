package com.hm.project_glue.sign.signup;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jongkook on 2016. 11. 30..
 *
 * ========== 참고 사항 ==========
 * 타입설정(application/json) 형식으로 전송
 * (Request Body 전달시 application/json로 서버에 전달.)
 * conn.setRequestProperty("Content-Type", "application/json");
 *
 */

public class SignUpModel {
    private static final String TAG = "ResponseCode : ";
    private final String SERVER_URL = "http://dummy-dev.ap-northeast-2.elasticbeanstalk.com/group/";

    public void signUp(String id, String pw, String pwre,
                       String email, String name, String phone) {
        HashMap hashMap = new HashMap();
        hashMap.put("id", id);
        hashMap.put("pw", pw);
        hashMap.put("pwre", pwre);
        hashMap.put("phone", phone);
        hashMap.put("name", name);
        hashMap.put("email", email);

        // AsyncTask클래스는 항상 Subclassing 해서 사용 해야 함.
        // UI 처리 및 Background 작업 등 을 하나의 클래스에서 작업 할 수 있게 지원
        // 파라미터 타입은 작업 실행 시에 송신 : Map (doInBackground 파라미터 타입이, execute 메소드 인자값)
        // doInBackground 작업 시 진행 단위의 타입 : Void (onProgressUpdate 파라미터 타입)
        // doInBackground 리턴값 : String (onPostExecute 파라미터 타입)
        // 인자를 사용하지 않은 경우 Void Type 으로 지정
        new AsyncTask<Map, Void, String>(){
            @Override
            // doInBackground : Background 작업을 진행
            // doInBackground의 매개값 : Map
            protected String doInBackground(Map... params) {
                String result = "";
                try {
                    // SERVER_URL에서 보낸 값을 받음
                    result = postData(SERVER_URL, params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }.execute(hashMap);
    }

    public static String postData (String webURL, Map params) throws Exception{
        // 동기화 및 String보다 빠른 반응을 위해 StringBuilder 사용
        StringBuilder result = new StringBuilder();
        String dataLine;
        URL url = new URL(webURL);
        Log.i("URL Test","-------- " + url.toString());

        // HttpURLConnection 객체 생성
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // 요청 방식 선택 (GET, POST)
        conn.setRequestMethod("POST");
        // Request Header값 셋팅
        // Accept-Charset : 폼을 취급하기 위해 서버가 받아 드릴 수 있는 글자 글자 엔코딩 목록
        // Accept-Charset로 euc-kr을 세팅시 IE에서 동작하지 않을 수 있음
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        // content-type : request 메세지에 포함되어야 하는 정보가 있을 때, 데이터 타입 정의
        // application/x-www-form-urlencoded 방식을 선택하면, key-value 형태로 인코딩
        conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        // OutputStream 혹은 InputStream으로 POST 데이터를 넘겨주겠다는 옵션
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // Body에 Data를 담기위해 InputStream/OutputStream 객체를 생성
        OutputStream os = conn.getOutputStream();
        InputStream is = conn.getInputStream();

        // Map의 key, value를 담아냄
        ArrayList<String> keyset = new ArrayList<>(params.keySet());
        for(String key : keyset){
            String param = key + "=" + params.get(key) + "&";
            Log.i("POST value : ", param);
            // Request Body에 Data 셋팅
            os.write(param.getBytes());
        }
        // Request Body에 Data 입력
        os.flush();
        // OutputStream 종료
        os.close();

        // 실제 서버로 Request 요청 하는 부분 (응답 코드를 받는다. 200 성공, 나머지 에러)
        int responseCode = conn.getResponseCode();
        if(responseCode == HttpsURLConnection.HTTP_OK){
            // 스트림을 직접 읽으면 느리고 비효율적
            // 버퍼(BufferedReader)를 지원하는 보조 스트림 객체로 감싸서 사용
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // 입력받은 값이 null이 아니면 result에 담음
            while ((dataLine = br.readLine()) != null){
                result.append(dataLine);
            }
        } else {
            Log.i(TAG, "responseCode ------ " + responseCode);
        }

        return result.toString();
    }
}