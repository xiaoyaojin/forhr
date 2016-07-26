package com.example.contactdatabaseoporation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MydataBase extends SQLiteOpenHelper{
	private Context context;
	private String name; 
	private  CursorFactory factory; 
	private int version;
	
	public MydataBase(Context context, String name, CursorFactory factory, int version) 
	{   super(context, name, factory, version);
		this.context=context;
		this.name=name;
		this.factory=factory;
		this.version=version;

		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table contact_person(id  interger primary key ,name,tel,ralationship)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("alter table contact_person add column money");
	}

}
