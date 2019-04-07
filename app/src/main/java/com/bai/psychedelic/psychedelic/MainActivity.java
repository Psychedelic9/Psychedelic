package com.bai.psychedelic.psychedelic;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bai.psychedelic.psychedelic.activity.WeiboMainActivity;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import java.text.SimpleDateFormat;
import com.sina.weibo.sdk.web.WeiboPageUtils;

public class MainActivity extends AppCompatActivity {
    private SsoHandler mSsoHandler;
    private Context mContext;
    private Oauth2AccessToken mAccessToken;
    private TextView mTokenText;
    private AuthInfo authInfo;
    private boolean useWeb = true;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authInfo = new AuthInfo(this, Constants.APP_KEY,
                Constants.REDIRECT_URL, Constants.SCOPE);
        WbSdk.install(this,authInfo);
        mTokenText = (TextView) findViewById(R.id.token_text_view);
        mSsoHandler = new SsoHandler(MainActivity.this);
        mContext = MainActivity.this;
        mAccessToken = new Oauth2AccessToken();
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if (mAccessToken.isSessionValid()) {//授權未過期情況下不需要重新登錄
            updateTokenView(true);
            Intent mWeiboMainActivityIntent = new Intent(mContext,WeiboMainActivity.class);
            startActivity(mWeiboMainActivityIntent);
//            getWeiboText();HTML5版本微博页面
        }else{

            init();//登錄
        }

    }

    private void init() {
        mSsoHandler.authorize(new SelfWbAuthListener());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

    }

    public void getWeiboText() {
        WeiboPageUtils.getInstance(MainActivity.this,authInfo).gotoMyProfile(false);

    }

    private class SelfWbAuthListener implements com.sina.weibo.sdk.auth.WbAuthListener{
        @Override
        public void onSuccess(final Oauth2AccessToken token) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAccessToken = token;
                    if (mAccessToken.isSessionValid()) {
                        //显示Token
                        updateTokenView(false);
                        // 保存 Token 到 SharedPreferences
                        AccessTokenKeeper.writeAccessToken(MainActivity.this, mAccessToken);
                        Toast.makeText(mContext,"授权成功", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void cancel() {
            Toast.makeText(mContext,"授权取消", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(WbConnectErrorMessage errorMessage) {
            Toast.makeText(mContext, errorMessage.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
        String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
        mTokenText.setText(String.format(format, mAccessToken.getToken(), date));

        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
        }
        mTokenText.setText(message);
    }
}
