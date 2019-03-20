package com.zstu.natsukawa.fasterner.progress;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.zstu.natsukawa.fastener.R;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;


public class ProgressFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ArrayList<ProgressBean> progress = new ArrayList<>();

    @BindView(R.id.progress_recyclerview) RecyclerView progressRecyclerView;

    private OnFragmentInteractionListener mListener;

    public ProgressFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgressFragment newInstance(String param1, String param2) {
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress,container,false);
        ButterKnife.bind(this,view);
        initialiseViews();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initialiseViews(){
        progress.add(new ProgressBean("2018-01-12","构建工程项目，开始搭建项目框架","后台"));
        progress.add(new ProgressBean("2018-01-13","完成部分框架搭建，进行界面部分规划","移动端Android"));
        progress.add(new ProgressBean("2018-01-13","数据库表类型的设计，开会讨论所需字段，确定各个表之间的关系","后台"));
        progress.add(new ProgressBean("2018-01-16","框架部分搭建完成，进行单元测试","后台"));
        progress.add(new ProgressBean("2018-01-17","确定移动端界面样式，收集所需要的图片以及图标材料","移动端"));
        progress.add(new ProgressBean("2018-01-19","框架，技术等文档方面的思考与撰写","文档工作"));
        Collections.reverse(progress);
        ProgressItemAdapter progressItemAdapter = new ProgressItemAdapter(R.layout.project_timeline_item, progress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),RecyclerView.VERTICAL,false);
        progressRecyclerView.setLayoutManager(linearLayoutManager);
        progressRecyclerView.setAdapter(progressItemAdapter);
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
