//package com.example.dynamiccare_kisok.Activity;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.example.dynamiccare_kisok.Fragment.SelectWorkOut2;
//import com.example.dynamiccare_kisok.R;
//
//public class MainActivity extends AppCompatActivity {
//
//    FragmentManager fragmentManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        try {
//            fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            SelectWorkOut2 selectWorkOut = new SelectWorkOut2();
//
//            fragmentTransaction.replace(R.id.main_container, selectWorkOut);
//            fragmentTransaction.commit();
//
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//}
