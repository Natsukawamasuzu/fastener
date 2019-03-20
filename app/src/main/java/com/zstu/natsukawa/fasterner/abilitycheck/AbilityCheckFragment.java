package com.zstu.natsukawa.fasterner.abilitycheck;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.zstu.natsukawa.fastener.R;
import com.zstu.natsukawa.fasterner.base.Datas;
import com.zstu.natsukawa.fasterner.main.MainActivity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AbilityCheckFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AbilityCheckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AbilityCheckFragment extends Fragment implements AbilityCheckContract.AbilityCheckView {

    private static final String MENU_ITEM_ID = "menuItemId";
    private static final String STUDENT_NUMBER = "studentNumber";
    private int menuItemId;
    private AbilityCheckPresenter abilityCheckPresenter;
    private OnFragmentInteractionListener mListener;
    private String studentNumber;
    private ArrayList<QuestionBean> questionList = new ArrayList<>();
    private int questionNumber = 0;

    @BindView(R.id.question_textview) TextView questionView;
    @BindView(R.id.question_choices) RadioGroup radioGroup;
    @BindView(R.id.previous_question) Button previousButton;
    @BindView(R.id.choice_1) RadioButton radioButton1;
    @BindView(R.id.choice_2) RadioButton radioButton2;
    @BindView(R.id.choice_3) RadioButton radioButton3;
    @BindView(R.id.choice_4) RadioButton radioButton4;
    @BindView(R.id.next_question) Button nextButton;
    Toolbar toolbar;

    public AbilityCheckFragment() {    }

    public static AbilityCheckFragment newInstance(int menuItemId,String studentNumber) {
        AbilityCheckFragment fragment = new AbilityCheckFragment();
        Bundle args = new Bundle();
        args.putInt(MENU_ITEM_ID, menuItemId);
        args.putString(STUDENT_NUMBER,studentNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menuItemId = getArguments().getInt(MENU_ITEM_ID);
            studentNumber = getArguments().getString(STUDENT_NUMBER);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ability_check,container,false);
        ButterKnife.bind(this,view);
        initialiseViews();
        return view;
    }


    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void initialiseViews(){

        abilityCheckPresenter = new AbilityCheckPresenter(AbilityCheckFragment.this, Datas.BASE_URL);
        if(menuItemId == R.id.ability_test)
            abilityCheckPresenter.getAbilityQuestionList(studentNumber);
        if(menuItemId == R.id.character_test)
            abilityCheckPresenter.getCharacQuestionList(studentNumber);
        nextButton.setOnClickListener(v -> {
            if(questionNumber == 3){
                nextButton.setText("提交");
                questionNumber ++;
            }else if(questionNumber == 4){
                new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                    .setTitle("确认提交？")
                    .setMessage("一旦提交之后无法撤销操作")
                    .setNegativeButton("取消", ((dialog, which) -> dialog.dismiss()))
                    .setPositiveButton("确认",((dialog, which) -> {
                        Toast.makeText(getActivity(), "submitted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }))
                    .show();
            }else{
                questionNumber++;
            }
            showNextQuestion(questionNumber);
        });
        previousButton.setOnClickListener(v -> {
            if(questionNumber == 4)
                nextButton.setText("下一题");
            else if(questionNumber == 1)
                previousButton.setEnabled(false);
            questionNumber--;
            showPreviousQuestion(questionNumber);
        });
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

        });
        if(menuItemId == R.id.character_test){
            questionList.add(new QuestionBean("到理发店理发，你会如何与发型师沟通?","丢一堆杂志要他决定", "拿照片请他照着修剪","任由理发师帮你设计","口头说明大概要修剪的发型"));
            questionList.add(new QuestionBean("你是怎样吃薯条的?","不沾酱，直接吃薯条", "将蕃茄酱挤在干净的容器上，然后用薯条沾着品尝","将蕃茄酱沿线撕开，把薯条放入其中沾酱，然后品尝","将蕃茄酱包开一个小口，把酱一点点的挤到薯条上，然后品尝"));
            questionList.add(new QuestionBean("朋友邀请你去一个盛大的皇室派对，在宴会上你会选择穿什么样的衣服呢?","公主般可爱的泡泡裙", "代表优雅的银色晚礼服","尽显雍容华贵的貂皮大衣","展现女人俏皮可爱的迷你短裙"));
            questionList.add(new QuestionBean("深夜由车站步行20分钟才回到家，门已锁，家人已熟睡，怎么都无法吵醒他们，但二楼灯还亮着，你这时会怎么做?","回到车站打电话", "弄坏门或窗的锁","用铁丝想办法开门","到附近的店坐坐，再打电话，如果不行就坐到天亮"));
            questionList.add(new QuestionBean("到动物园游玩时，看到大象，而在象的有关部分中，给你最大印象的是什么？","象牙", "眼睛","鼻子","腿"));
        }else if(menuItemId == R.id.ability_test){
            questionList.add(new QuestionBean("当浏览器返回到新URL的包含applet 的页面时调用以下哪个函数","init()", "start()","stop()","destroy()"));
            questionList.add(new QuestionBean("已知表达式int m[] = {0, 1, 2, 3, 4, 5, 6 };\n" +
                    "下面哪个表达式的值与数组下标量总数相等？","m.length()", "m.length","m.length()+1","m.length+1"));
            questionList.add(new QuestionBean("Java中main()函数的返回值是什么 ","String", "int","char","void"));
            questionList.add(new QuestionBean(" 关于sleep()和wait()，以下描述错误的一项是？","sleep是线程类（Thread）的方法，wait是Object类的方法；", "sleep不释放对象锁，wait放弃对象锁；","sleep暂停线程、但监控状态仍然保持，结束后会自动恢复； ","wait后进入等待锁定池，只有针对此对象发出notify方法后获得对象锁进入运行状态"));
            questionList.add(new QuestionBean("分析选项中关于Java中this关键字的说法正确的是"," this关键字是在对象内部指代自身的引用 ", "this关键字可以在类中的任何位置使用 ","this关键字和类关联，而不是和特定的对象关联","同一个类的不同对象共用一个this"));
        }

        showNextQuestion(0);
    }

    private void showNextQuestion(int questionNumber){
        radioGroup.clearCheck();
        QuestionBean questionBean = questionList.get(questionNumber);
        toolbar.setSubtitle(questionNumber + 1 + "/" + questionList.size());
        if(questionNumber == 0){
            previousButton.setEnabled(false);
            questionView.setText(questionBean.getQuestion());
            radioButton1.setText(questionBean.getFirstChoice());
            radioButton2.setText(questionBean.getSecondChoice());
            radioButton3.setText(questionBean.getThirdChoice());
            radioButton4.setText(questionBean.getFourthChoice());
        }else{
            previousButton.setEnabled(true);
            questionView.setText(questionBean.getQuestion());
            radioButton1.setText(questionBean.getFirstChoice());
            radioButton2.setText(questionBean.getSecondChoice());
            radioButton3.setText(questionBean.getThirdChoice());
            radioButton4.setText(questionBean.getFourthChoice());
        }
    }

    private void showPreviousQuestion(int questionNumber){
        radioGroup.clearCheck();
        QuestionBean questionBean = questionList.get(questionNumber);
        toolbar.setSubtitle(questionNumber + 1 + "/" + questionList.size());
        questionView.setText(questionBean.getQuestion());
        radioButton1.setText(questionBean.getFirstChoice());
        radioButton2.setText(questionBean.getSecondChoice());
        radioButton3.setText(questionBean.getThirdChoice());
        radioButton4.setText(questionBean.getFourthChoice());
        questionView.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void showQuestions(ArrayList<QuestionBean> questionList) {
        this.questionList = questionList;
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
        void checkClickedButton(int buttonId);
    }
}
