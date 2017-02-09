package app.com.example.android.mymobilesafer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import app.com.example.android.mymobilesafer.utils.MyUtils;
import app.com.example.android.mymobilesafer.utils.UpdateVersionUtils;

public class SplashActivity extends AppCompatActivity {

    private static final int MESSAGE_GET_CLOUD_VERSION = 101;
    //本地版本号
    private String mVersion;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            updateUtils.getCloudVersion();
        }
    };
    private UpdateVersionUtils updateUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mVersion= MyUtils.getVersion(getApplicationContext());
        updateUtils = new UpdateVersionUtils(mVersion, SplashActivity.this);

        //初始化控件
        initView();
        handler.sendEmptyMessageDelayed(MESSAGE_GET_CLOUD_VERSION,500);
    }

    private void initView() {
        TextView tvVersion= (TextView) findViewById(R.id.tv_version);
        tvVersion.setText("版本号:"+mVersion);
    }
}