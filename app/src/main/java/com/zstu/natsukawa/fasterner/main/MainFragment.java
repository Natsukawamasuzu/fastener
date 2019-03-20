package com.zstu.natsukawa.fasterner.main;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.zstu.natsukawa.fastener.R;
import com.zstu.natsukawa.fasterner.abilitycheck.AbilityCheckFragment;
import com.zstu.natsukawa.fasterner.personalinfo.PersonalInfoBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    @BindView(R.id.personal_avatar) ImageView imageView;
    @BindView(R.id.personal_name) TextView nameTextView;
    @BindView(R.id.personal_student_number) TextView studentNumberTextView;
    @BindView(R.id.personal_member_level) TextView memberLevelTextView;
    @BindView(R.id.personal_role_in_team) TextView roleInTeamTextView;
    @BindView(R.id.main_person_info) Button personInfoButton;
    @BindView(R.id.main_ask_for_leave_button) Button askForLeaveButton;
    @BindView(R.id.main_attendance_button) Button attendanceButton;
    @BindView(R.id.main_message) Button messageButton;
    @BindView(R.id.main_test) Button testButton;
    @BindView(R.id.main_tech_choice) Button techChoiceButton;

    private PersonalInfoBean personalInfoBean;
    private OnFragmentInteractionListener onFragmentInteractionListener;



    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(PersonalInfoBean personalInfoBean){
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("studentInfo",personalInfoBean);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            personalInfoBean = (PersonalInfoBean) getArguments().getSerializable("studentInfo");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if(context instanceof OnFragmentInteractionListener){
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
            super.onAttach(context);
        }else{
            throw new RuntimeException();
        }

    }

    @OnClick({R.id.main_person_info,R.id.main_attendance_button,R.id.main_ask_for_leave_button,R.id.main_message,R.id.main_test,R.id.main_tech_choice})
    public void personInfoButtonListener(View v){
        onFragmentInteractionListener.checkClickedButton(v.getId());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        ButterKnife.bind(this,view);
        initialiseView();
        return view;
    }

    private void initialiseView() {
        Glide.with(this).load(personalInfoBean.getAvatarUrl()).circleCrop().into(imageView);
        nameTextView.setText(personalInfoBean.getStudentName());
        studentNumberTextView.setText(personalInfoBean.getStudentNumber());
        roleInTeamTextView.setText(personalInfoBean.getStudentRoleInTeam());
        memberLevelTextView.setText(personalInfoBean.getStudentMemberLevel());
    }

    interface OnFragmentInteractionListener{
        void checkClickedButton(int buttonId);
    }

}
