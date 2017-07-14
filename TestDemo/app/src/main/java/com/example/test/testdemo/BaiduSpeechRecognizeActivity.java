package com.example.test.testdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.speech.VoiceRecognitionService;

import java.util.ArrayList;
import java.util.Arrays;

public class BaiduSpeechRecognizeActivity extends AppCompatActivity implements RecognitionListener {

    public static final int STATUS_None = 0;
    public static final int STATUS_WaitingReady = 2;
    public static final int STATUS_Ready = 3;
    public static final int STATUS_Speaking = 4;
    public static final int STATUS_Recognition = 5;
    private long speechEndTime = -1;
    private static final int EVENT_ERROR = 11;
    private int status = STATUS_None;

    private Button btnRecognize;
    private static final int REQUEST_UI = 1;
    private SpeechRecognizer speechRecognizer;
    private TextView tvResult;
    private String TAG="tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_speech_recognize);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this, new ComponentName(this, VoiceRecognitionService.class));
        speechRecognizer.setRecognitionListener(this);

        btnRecognize = ((Button) findViewById(R.id.btnRecognize));
        btnRecognize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("decoder",0);
//                intent.setAction("com.baidu.action.RECOGNIZE_SPEECH");
//                startActivityForResult(intent, REQUEST_UI);
                start();
            }
        });
        tvResult = ((TextView) findViewById(R.id.tvContent));
        speechRecognizer.setRecognitionListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //Log.e("tag", "SpeechRecognizer Result: "+ data.getStringArrayListExtra(SpeechRecognizer.RESULTS_RECOGNITION));
            ArrayList<String> result = data.getStringArrayListExtra(SpeechRecognizer.RESULTS_RECOGNITION);
            tvResult.setText(result.toString());
        }
    }

    @Override
    protected void onDestroy() {
        speechRecognizer.destroy();
        super.onDestroy();
    }

    private void start() {
        print("点击了“开始”");
        btnRecognize.setText("正在初始化");
        Intent intent = new Intent();
        speechRecognizer.startListening(intent);
    }

    private void stop() {
        speechRecognizer.stopListening();
        btnRecognize.setText("已停止");
        print("点击了“说完了”");
    }

    private void cancel() {
        speechRecognizer.cancel();
        status = STATUS_None;
        btnRecognize.setText("取消");
        print("点击了“取消”");
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        status = STATUS_Ready;
        btnRecognize.setText("请说话");
        print("准备就绪，可以开始说话");
    }

    @Override
    public void onBeginningOfSpeech() {
        time = System.currentTimeMillis();
        status = STATUS_Speaking;
        btnRecognize.setText("正在聆听");
        print("检测到用户的已经开始说话");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        //print(String.valueOf(rmsdB));
    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
        speechEndTime = System.currentTimeMillis();
        status = STATUS_Recognition;
        print("检测到用户的已经停止说话");
        btnRecognize.setText("正在识别中");
    }

    @Override
    public void onError(int error) {
        time = 0;
        status = STATUS_None;
        StringBuilder sb = new StringBuilder();
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                sb.append("音频问题");
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                sb.append("没有语音输入");
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                sb.append("其它客户端错误");
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                sb.append("权限不足");
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                sb.append("网络问题");
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                sb.append("没有匹配的识别结果");
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                sb.append("引擎忙");
                break;
            case SpeechRecognizer.ERROR_SERVER:
                sb.append("服务端错误");
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                sb.append("连接超时");
                break;
        }
        sb.append(":" + error);
        print("识别失败：" + sb.toString());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cancel();
        start();
    }

    @Override
    public void onResults(Bundle results) {
        long end2finish = System.currentTimeMillis() - speechEndTime;
        status = STATUS_None;
        ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        print("识别成功：" + Arrays.toString(nbest.toArray(new String[nbest.size()])));
        String json_res = results.getString("origin_result");
        try {
            //print("origin_result=\n" + new JSONObject(json_res).toString(4));
        } catch (Exception e) {
            //print("origin_result=[warning: bad json]\n" + json_res);
        }
        String strEnd2Finish = "";
        if (end2finish < 60 * 1000) {
            strEnd2Finish = "(waited " + end2finish + "ms)";
        }
        tvResult.setText(nbest.get(0) + strEnd2Finish);
        time = 0;
        start();
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        ArrayList<String> nbest = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (nbest.size() > 0) {
            //print("~临时识别结果：" + Arrays.toString(nbest.toArray(new String[0])));
            //tvResult.setText(nbest.get(0));
        }
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        switch (eventType) {
            case EVENT_ERROR:
                String reason = params.get("reason") + "";
                print("EVENT_ERROR, " + reason);
                break;
            case VoiceRecognitionService.EVENT_ENGINE_SWITCH:
                int type = params.getInt("engine_type");
                print("*引擎切换至" + (type == 0 ? "在线" : "离线"));
                break;
        }
    }

    long time;
    private void print(String msg) {
//        long t = System.currentTimeMillis() - time;
//        if (t > 0 && t < 100000) {
//            tvResult.append(t + "ms, " + msg + "\n");
//        } else {
//            tvResult.append("" + msg + "\n");
//        }
//        ScrollView sv = (ScrollView) tvResult.getParent();
//        sv.smoothScrollTo(0, 1000000);
        Log.d(TAG, "----" + msg);
    }
}
