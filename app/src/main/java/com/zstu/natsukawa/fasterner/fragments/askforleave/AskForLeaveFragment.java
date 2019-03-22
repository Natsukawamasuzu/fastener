package com.zstu.natsukawa.fasterner.fragments.askforleave;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.zstu.natsukawa.fastener.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AskForLeaveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AskForLeaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AskForLeaveFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String STUDENT_NUMBER = "studentNumber";
    @BindView(R.id.ask_for_leave_list_recycler_view)
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String studentNumber;

    private OnFragmentInteractionListener mListener;

    public AskForLeaveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AskForLeaveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AskForLeaveFragment newInstance(String param1) {
        AskForLeaveFragment fragment = new AskForLeaveFragment();
        Bundle args = new Bundle();
        args.putString(STUDENT_NUMBER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            studentNumber = getArguments().getString(STUDENT_NUMBER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask_for_leave, container, false);
        ButterKnife.bind(this, view);
        initialiseView();
        return view;
    }

    private void initialiseView() {
        ArrayList<AskForLeaveBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AskForLeaveBean askForLeaveBean = new AskForLeaveBean();
            askForLeaveBean.setAskForLeaveStartTime("2019-03-0" + (i + 1));
            askForLeaveBean.setAskForLeaveReason("这点钱我很难帮你办事儿啊......");
            askForLeaveBean.setContactNumber("1325081112" + i);
            askForLeaveBean.setAuditingState(i % 3);
            list.add(askForLeaveBean);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        AskForLeaveItemAdapter askForLeaveItemAdapter = new AskForLeaveItemAdapter(this.getContext(), R.layout.ask_for_leave_auditing_list_item, list);
        recyclerView.setAdapter(askForLeaveItemAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

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
