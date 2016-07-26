package com.example.contactdatabaseoporation;

import android.support.v7.app.ActionBarActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ResultActivity extends ActionBarActivity {

	SimpleAdapter adapter;
	ListView resultList;
	ArrayList<Person> list;
	ArrayList<Map<String,String>> mlist;
	Person person;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		resultList=(ListView)findViewById(R.id.result_list);
		Person person=(Person) getIntent().getSerializableExtra("data");
		list=databaseOperate.select(person);
		mlist=new ArrayList<Map<String,String>>();
		for(int i=0;i<list.size();i++)
		{
			Map<String,String> map=new HashMap<String, String>();
			map.put("name", list.get(i).getName());
			map.put("tel", list.get(i).getTel());
			map.put("relationship", list.get(i).getShip());
			mlist.add(map);	
		}
		adapter=new SimpleAdapter(ResultActivity.this,mlist,R.layout.result_list_item, new String[]{"name","tel","relationship"}, new int[]{R.id.item_name,R.id.item_tel ,R.id.item_ship});
		resultList.setAdapter(adapter);
		resultList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				AlertDialog.Builder builder=new AlertDialog.Builder(this).setTitle("Ñ¡Ôñ²Ù×÷").set
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
