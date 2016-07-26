package com.example.contactdatabaseoporation;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	Button findbtn;
	Button addbtn;
	EditText numedit;
	EditText nameedit;
	EditText shipedit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		findbtn=(Button)findViewById(R.id.findbtn);
		addbtn=(Button)findViewById(R.id.addbtn);
		numedit=(EditText)findViewById(R.id.numedit);
		nameedit=(EditText)findViewById(R.id.nameedit);
		shipedit=(EditText)findViewById(R.id.shipedit);
		addbtn.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Person person =new Person (nameedit.getText().toString(),numedit.getText().toString(),shipedit.getText().toString());
						databaseOperate.add(person);
					}
			
				});
		findbtn.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Person person =new Person (nameedit.getText().toString(),numedit.getText().toString(),shipedit.getText().toString());

						Bundle data=new Bundle();
						data.putSerializable("data", person);//person对象一定是可序列化的对象，必须实现Serializable接口
						Intent intent =new Intent(MainActivity.this,ResultActivity.class);
						intent.putExtras(data);
						startActivity(intent);
						
					}
					
				});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
