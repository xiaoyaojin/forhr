package com.example.contactdatabaseoporation;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseOperate  {

	private static Context context;
	public databaseOperate(Context context) {
		super();
		this.context = context;
	}
	private static  MydataBase database;

	public static void add(Person person) {
		// TODO Auto-generated method stub
		database=new MydataBase(context,"yjdatabase", null, 0);
		SQLiteDatabase sqlopenhelper=database.getReadableDatabase();
		String name=person.getName();
		String tel=person.getTel();
		String relation=person.getShip();
		sqlopenhelper.execSQL("insert into table contact_person(name,tel,ralationship) values(?,?,?)",new String []{name,tel,relation});
	}
	public static void delete(Person person)
	{
		database=new MydataBase(context,"yjdatabase", null, 0);
		SQLiteDatabase sqlopenhelper=database.getReadableDatabase();
		sqlopenhelper.execSQL("delete from table contact_person where name=?,tel=?,relationship=?",new String []{person.getName(),person.getTel(),person.getShip()});
		
	}
	public static void update(Person person,Person newperson)
	{
		database=new MydataBase(context,"yjdatabase", null, 0);
		SQLiteDatabase sqlopenhelper=database.getReadableDatabase();
		sqlopenhelper.execSQL("update table contact_person set  name=?, tel=?, relationship=? where name=?, tel=?, relationship=?",new String []{newperson.getName(),newperson.getTel(),newperson.getShip(),person.getName(),person.getTel(),person.getShip()});
	}
	public static ArrayList<Person> select(Person person)
	{
		ArrayList<Person> select_result=new ArrayList<Person>();
		database=new MydataBase(context,"yjdatabase", null, 0);
		SQLiteDatabase sqlopenhelper=database.getReadableDatabase();
		Cursor result =sqlopenhelper.rawQuery("select from table contact_person where name like ? or tel like ? or relationship like ? ",new String []{"%"+person.getName()+"%","%"+person.getTel()+"%","%"+person.getShip()+"%"} );
		while(result.moveToNext())
		{
			select_result.add(new Person (result.getColumnName(1),result.getColumnName(2),result.getColumnName(3)));
		}
		return select_result;
	}
	
}
