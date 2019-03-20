package com.zstu.natsukawa.fasterner.login;

import android.content.Intent;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.zstu.natsukawa.fastener.R;
import com.zstu.natsukawa.fasterner.base.BaseActivity;
import com.zstu.natsukawa.fasterner.base.Datas;
import com.zstu.natsukawa.fasterner.main.MainActivity;
import com.zstu.natsukawa.fasterner.register.RegisterActivity;

import java.util.Objects;

import static android.view.View.GONE;

public class LoginActivity extends BaseActivity implements LoginContract.LoginView {

    @BindView(R.id.student_number_edittext) EditText studentNumber;
    @BindView(R.id.student_password_edittext) EditText studentPassword;
    @BindView(R.id.student_number_textinputlayout) TextInputLayout studentNumberTextInputLayout;
    @BindView(R.id.student_password_textinputlayout) TextInputLayout studentPasswordTextInputLayout;
    @BindView(R.id.login_button) Button button;
    @BindView(R.id.test) TextView textView;
    @BindView(R.id.login_background) ImageView imageView;

    private LoginPresenter loginPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initialiseViews();
    }

    private void initialiseViews(){
        Glide.with(this).load(R.drawable.login_in).centerCrop()
                .into(imageView);
        loginPresenter = new LoginPresenter(this,Datas.BASE_URL2);
        linkChildPresenter(loginPresenter);
    }

    @OnClick(R.id.login_button)
    public void login(){ 
        showLoading();
        moveToMainUI();
        //loginPresenter.login(Objects.requireNonNull(studentNumber.getText()).toString(), Objects.requireNonNull(studentPassword.getText()).toString());
        button.setEnabled(true);
    }

    @OnClick(R.id.test)
    public void register(){
        moveToRegister();
    }

    @OnTextChanged(value = R.id.student_number_edittext, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void studentNumberListener(Editable s) {
        loginPresenter.isValidStudentNumber(s.toString());
        loginPresenter.canLogin();
    }

    @OnTextChanged(value = R.id.student_password_edittext, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void passwordListener(Editable s) {
        loginPresenter.isValidStudentPassword(s.toString());
        loginPresenter.canLogin();
    }

    @Override
    public void showLoading() {
        button.setEnabled(false);
    }



    @Override
    public void showError(String errorMessage) {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle(errorMessage)
                .setPositiveButton("CONFIRM", (dialog, which) -> {
                    Toast.makeText(LoginActivity.this,"Clicked CONFIRM", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    button.setEnabled(true);
                })
                .setNegativeButton("CANCEL", (dialog, which) -> {
                    Toast.makeText(LoginActivity.this,"Clicked CANCEL", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    button.setEnabled(true);
                })
                .create().show();

    }

    @Override
    public void showSuccess(String result) {
        moveToMainUI();
        this.finish();
    }

    @Override
    public void moveToMainUI() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void moveToRegister() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @Override
    public void alertInvalidStudentNumber(String alertString) {
        studentNumberTextInputLayout.setError(alertString);
    }

    @Override
    public void alertInvalidStudentPassword(String alertString) {
        studentPasswordTextInputLayout.setError(alertString);
    }

    @Override
    public void canRegister(boolean[] flags) {
        button.setEnabled(flags[0] && flags[1]);
    }

    @Override
    public void cancelRequest() {
    }

    @Override
    public void loginFailed(String extraMessage) {
        Toast.makeText(this, extraMessage, Toast.LENGTH_SHORT).show();
        button.setEnabled(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
