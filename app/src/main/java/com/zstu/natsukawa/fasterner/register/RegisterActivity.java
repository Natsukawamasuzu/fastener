package com.zstu.natsukawa.fasterner.register;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.zstu.natsukawa.fastener.R;
import com.zstu.natsukawa.fasterner.base.BaseActivity;
import com.zstu.natsukawa.fasterner.base.Datas;

import java.util.Objects;

public class RegisterActivity extends BaseActivity implements RegisterContract.RegisterView{

    @BindView(R.id.rgst_student_number_textinputlayout) TextInputLayout studentNumberTextInputLayout;  //学号输入效果控制域
    @BindView(R.id.rgst_student_password_textinputlayout) TextInputLayout studentPasswordTextInputLayout;  //密码输入效果控制域
    @BindView(R.id.rgst_student_phone_textinputlayout) TextInputLayout studentPhoneTextInputLayout;  //手机号码输入效果控制域
    @BindView(R.id.rgst_student_password_confirm_textinputlayout) TextInputLayout passwordConfirmTextInputLayout;  //确认密码输入效果控制域
    @BindView(R.id.rgst_student_name_textinputlayou) TextInputLayout nameTextInputLayout;  //姓名输入控制域
    @BindView(R.id.rgst_student_number) TextInputEditText studentNumberEditText;  //学号输入域
    @BindView(R.id.rgst_student_password) TextInputEditText studentPasswordEditText;  //密码输入域
    @BindView(R.id.rgst_student_name) TextInputEditText studentNameEditText;  //姓名输入域
    @BindView(R.id.rgst_student_phone) TextInputEditText studentPhoneEditText;  //手机号码输入域
    @BindView(R.id.rgst_student_password_confirm) TextInputEditText passwordConfirmEditText; //确认密码输入域
    @BindView(R.id.rgst_register_button) Button registerButton;
    @BindView(R.id.register_background) ImageView imageView;
    private RegisterPresenter registerPresenter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.login_in).centerCrop().into(imageView);
        registerPresenter = new RegisterPresenter(this, Datas.BASE_URL2);

    }


    @Override
    public void showErrorStudentNumber(String errorInfo) {
        studentNumberTextInputLayout.setError(errorInfo);
    }

    @Override
    public void showErrorPhoneNumber(String errorInfo) {
        studentPhoneTextInputLayout.setError(errorInfo);
    }

    @Override
    public void showErrorPassword(String errorInfo) {
        studentPasswordTextInputLayout.setError(errorInfo);
    }

    @Override
    public void showErrorConfirmation(String errorInfo) {
        passwordConfirmTextInputLayout.setError(errorInfo);
    }

    @Override
    public void showErrorName(String errorInfo) {
        nameTextInputLayout.setError(errorInfo);
    }

    @Override
    public void showLoading() {
        progressDialog = new ProgressDialog(RegisterActivity.this,ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("注册中...");
        progressDialog.setMessage("请稍候...");
        progressDialog.show();
    }

    @Override
    public void showError() {
        new AlertDialog.Builder(RegisterActivity.this)
                .setTitle("出错啦！")
                .setMessage("看起来似乎出了一些问题...")
                .setNegativeButton("知道了", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void showSuccess(String onBackMessage) {
        progressDialog.dismiss();
        Toast.makeText(this, onBackMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void canRegister(boolean[] flags) {
        if(flags[0] && flags[1] && flags[2]  && flags[3] && flags[4])
            registerButton.setEnabled(true);
        else
            registerButton.setEnabled(false);
    }

    @OnClick(R.id.rgst_register_button)
    public void register(){
       String studentNumber = Objects.requireNonNull(studentNumberEditText.getText()).toString();
       String studentName = Objects.requireNonNull(studentNameEditText.getText()).toString();
       String studentPhoneNumber = Objects.requireNonNull(studentPhoneEditText.getText()).toString();
       String studentPassword = Objects.requireNonNull(studentPasswordEditText.getText()).toString();
       new AlertDialog.Builder(RegisterActivity.this)
               .setTitle("请注意")
               .setMessage("应用程序将不会验证你的身份真实性，请自行核对信息的正确性，学号一旦注册之后不可以更改！")
               .setPositiveButton("确定", (dialog, which) -> {
                   dialog.dismiss();
                   showLoading();
                   registerPresenter.register(studentNumber, studentName, studentPhoneNumber, studentPassword);
               })
               .setNegativeButton("取消",(dialog, which) -> dialog.dismiss())
               .show();
    }

    @OnTextChanged(value = R.id.rgst_student_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void studentNumberListener(Editable s){
        registerPresenter.checkValidStudentNumber(s.toString());
        registerPresenter.canRegister();
    }

    @OnTextChanged(value = R.id.rgst_student_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void studentPasswordListener(Editable s){
        registerPresenter.checkValidPassword(s.toString());
        registerPresenter.canRegister();
    }

    @OnTextChanged(value = R.id.rgst_student_phone, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void studentPhoneListener(Editable s){
        registerPresenter.checkValidPhoneNumber(s.toString());
        registerPresenter.canRegister();
    }

    @OnTextChanged(value = R.id.rgst_student_password_confirm, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void confirmListener(Editable s){
        String password = Objects.requireNonNull(studentPasswordEditText.getText()).toString();
        registerPresenter.checkConsistentPassword(password, s.toString());
        registerPresenter.canRegister();
    }

    @OnTextChanged(value = R.id.rgst_student_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void studentNameListener(Editable s){
        registerPresenter.checkNullName(s.toString());
        registerPresenter.canRegister();
    }
}
