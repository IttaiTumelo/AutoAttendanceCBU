package com.example.autoattendance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.autoattendance.databinding.FragmentApprovalBinding;
import com.example.autoattendance.databinding.FragmentLoginBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApprovalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApprovalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Course> courseList;
    FragmentApprovalBinding binding;

    public ApprovalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApprovalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApprovalFragment newInstance(String param1, String param2) {
        ApprovalFragment fragment = new ApprovalFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentApprovalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();
        binding.recyclerViewLecturerCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewLecturerCourses.setHasFixedSize(true);
        ApprovalAdapter approvalAdapter = new ApprovalAdapter(getContext(), courseList);
        binding.recyclerViewLecturerCourses.setAdapter(approvalAdapter);
        approvalAdapter.notifyDataSetChanged();

    }

    private void loadData() {
        BaseStatics baseStatics = new BaseStatics();
        courseList = (ArrayList<Course>) baseStatics.getCourses();
        Course course = new Course();
        course.name="Whar";
        courseList.add(course);
//        courseList = new ArrayList<>();
//        courseList.add(
//                new Course(
//                        "Introduction to Computer Science",
//                        "CSE 101",
//                        "Dr. John Doe",
//                        "MWF 10:00 AM - 10:50 AM",
//                        "https://www.cs.arizona.edu/sites/default/files/styles/inline_featured_image/public/inline-images/CS101%20-%20Fall%202020%20-%20Banner%20-%20v2.png?itok=Z5Q4Z3Zw",
//                        "Fall 2020",
//                        "Approved"
//                )
//        );
//        courseList.add(
//                new Course(
//                        "Introduction to Algorithms",
//                        "CSE 101",
//                        "Dr. Jane Mukupa",
//                        "MWF 08:00 AM - 08:50 AM",
//                        "https://www.cs.arizona.edu/sites/default/files/styles/inline_featured_image/public/inline-images/CS101%20-%20Fall%202020%20-%20Banner%20-%20v2.png?itok=Z5Q4Z3Zw",
//                        "Fall 2020",
//                        "Approved"
//                )
//        );
//        courseList.add(
//                new Course(
//                        "Introduction to Computer Science",
//                        "CSE 101",
//                        "Dr. John Doe",
//                        "MWF 10:00 AM - 10:50 AM",
//                        "https://www.cs.arizona.edu/sites/default/files/styles/inline_featured_image/public/inline-images/CS101%20-%20Fall%202020%20-%20Banner%20-%20v2.png?itok=Z5Q4Z3Zw",
//                        "Fall 2020",
//                        "Approved"
//                )
//        );
//        courseList.add(
//                new Course(
//                        "Introduction to Algorithms",
//                        "CSE 101",
//                        "Dr. Jane Mukupa",
//                        "MWF 08:00 AM - 08:50 AM",
//                        "https://www.cs.arizona.edu/sites/default/files/styles/inline_featured_image/public/inline-images/CS101%20-%20Fall%202020%20-%20Banner%20-%20v2.png?itok=Z5Q4Z3Zw",
//                        "Fall 2020",
//                        "Approved"
//                )
//        );
//        courseList.add(
//                new Course(
//                        "Introduction to Computer Science",
//                        "CSE 101",
//                        "Dr. John Doe",
//                        "MWF 10:00 AM - 10:50 AM",
//                        "https://www.cs.arizona.edu/sites/default/files/styles/inline_featured_image/public/inline-images/CS101%20-%20Fall%202020%20-%20Banner%20-%20v2.png?itok=Z5Q4Z3Zw",
//                        "Fall 2020",
//                        "Approved"
//                )
//        );
    }
}