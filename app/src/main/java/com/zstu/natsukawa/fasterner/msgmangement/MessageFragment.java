package com.zstu.natsukawa.fasterner.msgmangement;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.zstu.natsukawa.fastener.R;
import com.zstu.natsukawa.fasterner.base.Datas;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment implements MessageContract.MessageView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.message_recycler_view) RecyclerView recyclerView;
    private MessageItemAdapter messageItemAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String studentNumber;

    private OnFragmentInteractionListener mListener;
    private List<MessageEntity> messageData = new ArrayList<>();
    private MessagePresenter messagePresenter;

    public MessageFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
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
        SharedPreferences sp = context.getSharedPreferences(Datas.SP_NAME,Context.MODE_PRIVATE);
        studentNumber = sp.getString("studentNumber", null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showEmptyMessageList() {
        Toast.makeText(this.getActivity(), "暂时没有什么消息呢~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess(ArrayList<MessageEntity> messages) {
        messageData.addAll(messages);
        messageItemAdapter.notifyDataSetChanged();
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

    private void initialiseViews(){
        messageData.add(new MessageEntity("2018-01-12","牛顿的烈焰极光剑队伍预定于今天下午1点在618开会"));
        messageData.add(new MessageEntity("2018-01-13","又双叒叕队伍今天队伍进度内容尚未提交，注意及时提交"));
        messageData.add(new MessageEntity("2018-01-14","昨天鸽子队有两个人没有签到并且鸽了，及时联系我说明原因"));
        messageData.add(new MessageEntity("2018-01-15","复读机队的初期文档还没有上交，你要赶工了！"));
        Collections.reverse(messageData);
        messagePresenter = new MessagePresenter(MessageFragment.this,Datas.BASE_URL);
        messageItemAdapter = new MessageItemAdapter(R.layout.message_item_layout,messageData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(messageItemAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        //messagePresenter.getMessages(studentNumber);
    }
}
