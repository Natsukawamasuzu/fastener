package com.zstu.natsukawa.fasterner.personalinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.zstu.natsukawa.fastener.R;
import com.zstu.natsukawa.fasterner.base.Datas;
import com.zstu.natsukawa.fasterner.optionview.AbilityLevelView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonalInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonalInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalInfoFragment extends Fragment implements PersonalInfoContract.PersonalInfoView {
    @BindView(R.id.personal_avatar) AppCompatImageView studentAvatar;
    @BindView(R.id.personal_student_number) TextView studentNumberTextView;
    @BindView(R.id.personal_name) TextView studentNameTextView;
    @BindView(R.id.personal_member_level) TextView studentMemberLevelTextView;
    @BindView(R.id.personal_role_in_team) TextView studentRoleInTeamTextView;
    @BindView(R.id.status) AbilityLevelView abilityLevelView;
    private static final String ARG_PARAM1 = "studentNumber";
    private PersonalInfoPresenter personalInfoPresenter;
    private String studentNumber;
    private ArrayList<Float> scoreList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public PersonalInfoFragment() {
        // Required empty public constructor
    }

    public static PersonalInfoFragment newInstance(String studentNumber) {
        PersonalInfoFragment personalInfoFragment = new PersonalInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, studentNumber);
        personalInfoFragment.setArguments(args);
        return personalInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            studentNumber = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_info,container,false);
        ButterKnife.bind(this,view);
        initialiseViews();
        return view;
    }

    private void initialiseViews() {
        personalInfoPresenter = new PersonalInfoPresenter(PersonalInfoFragment.this,Datas.BASE_URL);
        ArrayList<String> attributeList = new ArrayList<String>(5){
            {add("技术测试"); add("性格测试"); add("专业知识"); add("学习能力"); add("其他");}
        };
        scoreList = new ArrayList<Float>(5){
            {add(0f); add(0f); add(0f); add(0f); add(0f);}
        };
        abilityLevelView.setDimensionCount(attributeList.size());
        abilityLevelView.setDimensionStrings(attributeList);
        abilityLevelView.setDimensionMaxValue(100);
        abilityLevelView.setDimensionValues(scoreList);
        personalInfoPresenter.refreshStudentStatus(studentNumber);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showSuccess(String result) {
        PersonalInfoBean personalInfoBean = personalInfoPresenter.getStudentStatusFromLocal();
        Glide
                .with(this)
                .load(result)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(studentAvatar);
        studentNumberTextView.setText(personalInfoBean.getStudentNumber());
        studentNameTextView.setText(personalInfoBean.getStudentName());
        studentMemberLevelTextView.setText(personalInfoBean.getStudentMemberLevel());
        studentRoleInTeamTextView.setText(personalInfoBean.getStudentRoleInTeam());
        scoreList.clear();
        scoreList.add(personalInfoBean.getTechnologyLevel());
        scoreList.add(personalInfoBean.getCharacteristicLevel());
        scoreList.add(personalInfoBean.getProfessionalKnowledgeLevel());
        scoreList.add(personalInfoBean.getLearningAbilityLevel());
        scoreList.add(personalInfoBean.getOtherAbilityLevel());
        abilityLevelView.setDimensionValues(scoreList);
    }

    @Override
    public void showError() {

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
