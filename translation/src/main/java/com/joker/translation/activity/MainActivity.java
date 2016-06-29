package com.joker.translation.activity;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.joker.jokerlibrary.net.StringVolleyLis;
import com.joker.jokerlibrary.utils.EncryptionUtils;
import com.joker.jokerlibrary.utils.JsonUtils;
import com.joker.translation.R;
import com.joker.translation.entity.TranslationResult;
import com.joker.translation.net.VolleyUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends BaseAppActivity
{
    private MaterialEditText tvSrc;
    private TextView tvDst;
    private ButtonFloat btnListen;
    private ButtonRectangle btnTranslation;
    private MaterialSpinner spLanguage;
    private Handler uiHandler;

    private final static int RESULTS_RECOGNITION_SUCCESS = 1;
    private final static int TRANSLATION_SUCCESS = 2;

    private  Map<String,String> languageMap;
    private String to = "en";

    @Override
    protected void initVariables()
    {
        languageMap = new HashMap<>();
        languageMap.put("中文","zh");
        languageMap.put("英语","en");
        languageMap.put("日语","jp");
        languageMap.put("法语","fra");
        languageMap.put("泰语","th");
        languageMap.put("俄语","ru");
        languageMap.put("德语","de");

        uiHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case RESULTS_RECOGNITION_SUCCESS:
                        tvSrc.setText(msg.obj.toString());
                        break;
                    case TRANSLATION_SUCCESS:
                        tvDst.setText(msg.obj.toString());
                        break;
                }
            }
        };
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("翻译");
        setSupportActionBar(toolbar);

        tvSrc = (MaterialEditText) findViewById(R.id.tv_src);
        tvDst = (TextView) findViewById(R.id.tv_dst);
        btnListen = (ButtonFloat) findViewById(R.id.btn_listen);
        btnListen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listen();
            }
        });
        btnTranslation = (ButtonRectangle) findViewById(R.id.btn_translation);
        btnTranslation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                translation();
            }
        });
        spLanguage = (MaterialSpinner) findViewById(R.id.sp_language);
        spLanguage.setItems(new ArrayList<String>(languageMap.keySet()));
        spLanguage.setSelectedIndex((new ArrayList<String>(languageMap.keySet())).indexOf("英语"));
        spLanguage.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item)
            {
                chooseLanguage(item.toString());
            }
        });
    }

    @Override
    protected void loadData()
    {
    }

    private void listen()
    {
        Bundle params = new Bundle();
        params.putString(BaiduASRDigitalDialog.PARAM_API_KEY,"KMyZfUkTKDbU7LZXzLkDwxtf");
        params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY,"e9007554528d8bfa3b5f72d2b75990c3");

        BaiduASRDigitalDialog mDialog = new BaiduASRDigitalDialog(this,params);
        mDialog.setDialogRecognitionListener(new DialogRecognitionListener()
        {
            @Override
            public void onResults(Bundle bundle)
            {
                ArrayList<String> rs = bundle != null ? bundle.getStringArrayList(RESULTS_RECOGNITION) : null;
                if(rs != null && rs.size() > 0)
                {
                    Message message = uiHandler.obtainMessage();
                    message.what = RESULTS_RECOGNITION_SUCCESS;
                    message.obj = rs.get(0);
                    uiHandler.sendMessage(message);
                }
            }
        });
        mDialog.show();
    }

    private void translation()
    {
        String rawText = tvSrc.getText().toString();
        if(rawText == null || "".equals(rawText.trim()))
        {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";
            String q = rawText;
            String from = "auto";
            String to = this.to;
            String appid = "20160621000023724";
            String salt = new Random(System.currentTimeMillis()).nextInt(100000)+"";

            String signRaw = appid+q+salt+ "sBxl5nFZlWD8GRTJc3Ji";
            String sign = EncryptionUtils.md5Encrypt(signRaw).toLowerCase();

            Map<String,Object> map = new HashMap<>();
            map.put("q",q);
            map.put("from",from);
            map.put("to",to);
            map.put("appid",appid);
            map.put("salt",salt);
            map.put("sign",sign);

            VolleyUtils.RequestGet("translation", url, map, new StringVolleyLis(getBaseContext(), StringVolleyLis.mListener, StringVolleyLis.mErrorListener)
            {
                @Override
                public void onMySuccess(String result)
                {
                    TranslationResult translationResult = (TranslationResult) JsonUtils.jsonToBean(result,TranslationResult.class);
                    Log.d("MainActivity", "translationResult:" + translationResult);
                    Message message = uiHandler.obtainMessage();
                    message.what = TRANSLATION_SUCCESS;
                    message.obj = translationResult.trans_result.get(0).dst;
                    uiHandler.sendMessage(message);
                }

                @Override
                public void onMyError(VolleyError error, String description)
                {

                }
            });
        }
    }

    private void chooseLanguage(String item)
    {
        to = languageMap.get(item);
    }
}
