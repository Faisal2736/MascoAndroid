package com.semicolons.masco.pk.uiActivities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.semicolons.masco.pk.R;
import com.semicolons.masco.pk.adapters.ExpandableListAdapter;

public class FAQsActivity extends AppCompatActivity {

    //expandable list var
    private ExpandableListAdapter expandableListAdapter;
    TextView textViewAbout;
    TextView textViewDel;
    private ExpandableListAdapter expandableListAdapter2;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private ExpandableListAdapter expandableListAdapter3;
    private ExpandableListView expListView, expListView1, expListView2;


    ExpandableListView.OnGroupExpandListener onGroupExpandListenser = new ExpandableListView.OnGroupExpandListener() {
        int previousGroup = -1;

        @Override
        public void onGroupExpand(int groupPosition) {
            if (groupPosition != previousGroup)
                expListView.collapseGroup(previousGroup);
            previousGroup = groupPosition;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_qs);
        setTitle("FAQs");

        textViewAbout = findViewById(R.id.about_us);
        textViewDel = findViewById(R.id.delivery);


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView1 = (ExpandableListView) findViewById(R.id.lvExp1);
        expListView2 = (ExpandableListView) findViewById(R.id.lvExp2);


        prepareListData();
        expandableListAdapter = new ExpandableListAdapter(this.getApplicationContext(), listDataHeader, listDataChild);
        expListView.setAdapter(expandableListAdapter);

        prepareListData2();
        expandableListAdapter2 = new ExpandableListAdapter(this.getApplicationContext(), listDataHeader, listDataChild);
        expListView1.setAdapter(expandableListAdapter2);

        prepareListData3();
        expandableListAdapter3 = new ExpandableListAdapter(this.getApplicationContext(), listDataHeader, listDataChild);
        expListView2.setAdapter(expandableListAdapter3);


        //expListView = getExpandableListView();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        //this code for adjusting the group indicator into right side of the view
        expListView.setIndicatorBounds(width - GetDipsFromPixel(50), width - GetDipsFromPixel(10));
        expListView1.setIndicatorBounds(width - GetDipsFromPixel(50), width - GetDipsFromPixel(10));
        expListView2.setIndicatorBounds(width - GetDipsFromPixel(50), width - GetDipsFromPixel(10));


    }

    public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("What is Masco.pk?");
        listDataHeader.add("How can I pay for my order?");
        listDataHeader.add("Can you ship orders nationwide?");


        // Adding child data
        List<String> child1 = new ArrayList<String>();
        child1.add("Masco.pk is an e-commerce initiative of Masco.pk Supermarket.\n" +
                "\n");

        // Adding child data
        List<String> child2 = new ArrayList<String>();
        child2.add("You can pay through Cash on Delivery");

        // Adding child data
        List<String> child3 = new ArrayList<String>();
        child3.add("Yes, currently we ship in over 800 cites in Pakistan.");


        listDataChild.put(listDataHeader.get(0), child1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), child2); // Header, Child data
        listDataChild.put(listDataHeader.get(2), child3); // Header, Child data


    }

    private void prepareListData2() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("What are the delivery charges?");
        listDataHeader.add("How can I track or check status of my order?");
        listDataHeader.add("Can I schedule my order?");
        listDataHeader.add("Can I track the status of my order?");


        // Adding child data
        List<String> child1 = new ArrayList<String>();
        child1.add("Please visit Delivery & Shipping page for more information.\n" +
                "\n");

        // Adding child data
        List<String> child2 = new ArrayList<String>();
        child2.add("Order status can be checked from 'My Orders' screen in your account. Status can also be checked through Order Tracking page.\n" +
                "\n");

        // Adding child data
        List<String> child3 = new ArrayList<String>();
        child3.add("Yes");

        // Adding child data
        List<String> child4 = new ArrayList<String>();
        child4.add("Yes call on our helpline number");


        listDataChild.put(listDataHeader.get(0), child1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), child2); // Header, Child data
        listDataChild.put(listDataHeader.get(2), child3); // Header, Child data
        listDataChild.put(listDataHeader.get(3), child4); // Header, Child data


    }

    private void prepareListData3() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Do I have to pay for shipping charges when returning a product?");


        // Adding child data
        List<String> child1 = new ArrayList<String>();
        child1.add("No, you don't have to pay for shipping charges when returning a product\n" +
                "\n");


        listDataChild.put(listDataHeader.get(0), child1); // Header, Child data


    }


}