package app.com.example.android.mymobilesafer.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import app.com.example.android.mymobilesafer.adapter.HomeAdapter;
import app.com.example.android.mymobilesafer.R;
import app.com.example.android.mymobilesafer.dialog.InterPasswordDialog;
import app.com.example.android.mymobilesafer.dialog.SetUpPasswordDialog;
import app.com.example.android.mymobilesafer.utils.MD5Utils;
import app.com.example.android.mymobilesafer.utils.ToastUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private GridView gridView;
    private long mExitTime;

    //存储设置文件
    private SharedPreferences msharedPreferences;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        msharedPreferences = getActivity().getSharedPreferences("config",MODE_PRIVATE);

        gridView= (GridView) view.findViewById(R.id.gv_home);

        //填充GridView
        gridView.setAdapter(new HomeAdapter(getActivity()));
        //单击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://手机防盗
                        if(isSetUpPassword()){
                            showInterPasswordDialog();
                        }else{
                            showSetUpPasswordDialog();
                        }
                        break;
                    case 1://通讯卫士
                        break;
                    case 2://软件管家
                        break;
                    case 3://病毒查杀
                        break;
                    case 4://缓存清理
                        break;
                    case 5://进程管理
                        break;
                    case 6://流量统计
                        break;
                    case 7://高级工具
                        break;
                    case 8://设置中心
                        break;
                }
            }
        });
    }

    /**
     *
     * 设置密码对话框
     */

    public void showSetUpPasswordDialog(){
        final SetUpPasswordDialog msetPwdDialog = new SetUpPasswordDialog(getActivity());
        msetPwdDialog.setCallBack(new SetUpPasswordDialog.MyCallBack(){
            @Override
            public void ok() {
                String firstPwd = msetPwdDialog.mFirstPWDET.getText().toString().trim();
                String affirmPwd = msetPwdDialog.mAffirmET.getText().toString().trim();

                if (!TextUtils.isEmpty(firstPwd) && !TextUtils.isEmpty(affirmPwd)) {
                    //两次密码一致，则保存
                    if (firstPwd.equals(affirmPwd)) {
                        savePwd(affirmPwd);
                        //显示输入密码对话框
                        showInterPasswordDialog();
                    } else {
                        ToastUtils.showToast(getActivity(),"两次密码不一致");
                    }
                } else {
                    ToastUtils.showToast(getActivity(),"两次密码不一致");
                }
            }

            @Override
            public void cancel() {
                msetPwdDialog.dismiss();
            }
        });
        msetPwdDialog.setCancelable(true);
        msetPwdDialog.show();
    }


    /**
     * 输入密码对话框
     * @return
     */
    public void showInterPasswordDialog(){
        final String password = getPassword();
        final InterPasswordDialog mInterPasswordDialog =  new InterPasswordDialog(getContext());

        mInterPasswordDialog.setCallBack(new InterPasswordDialog.MyCallBack() {
            @Override
            public void confirm() {
                if(TextUtils.isEmpty(mInterPasswordDialog.getPassword())){
                    ToastUtils.showToast(getActivity(),"密码不能为空");
                }else if(password.equals(MD5Utils.encode(mInterPasswordDialog.getPassword()))){
                    //进入防盗界面
                    ToastUtils.showToast(getActivity(),"成功进入防盗界面");
                }else{
                    mInterPasswordDialog.dismiss();
                    ToastUtils.showToast(getActivity(),"密码错误，请重新输入");
                }
            }

            @Override
            public void dismiss() {
                mInterPasswordDialog.dismiss();
            }
        });

        mInterPasswordDialog.setCancelable(true);
        mInterPasswordDialog.show();
    }

    /**
     * 保存密码
     * @return
     */

    public void savePwd(String affirmPwd){
        SharedPreferences.Editor editor = msharedPreferences.edit();
        editor.putString("PhoneAntiTheftPWD", MD5Utils.encode(affirmPwd));
        editor.commit();
    }

    /**
     * 获取密码
     * @return
     */
    public String getPassword(){
        String password  = msharedPreferences.getString("PhoneAntiTheftPWD",null);
        if(TextUtils.isEmpty(password))
            return "";
        return password;
    }
    //判断是否设置过密码
    public boolean isSetUpPassword(){
        String password = msharedPreferences.getString("PhoneAntiTheftPWD", null);
        if(TextUtils.isEmpty(password)){
            return false;
        }
        return true;
    }

    //按两次返回键退出程序


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode== KeyEvent.KEYCODE_BACK){
//            if((System.currentTimeMillis()-mExitTime)<2000){
//                System.exit(0);
//            }else{
//                Toast.makeText(this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
//                mExitTime= System.currentTimeMillis();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
