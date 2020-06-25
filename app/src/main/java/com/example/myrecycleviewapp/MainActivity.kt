package com.example.myrecycleviewapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.database.sqlite.SQLiteDatabase

class MainActivity : AppCompatActivity() {
    val dbhelper = SQLiteOpenHelper(this)
    val mDatabase : SQLiteDatabase = dbhelper.writableDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyRecyclerViewAdapter()
    }

    private fun addItem(name: String, age: Int, breed: String) {
        val cv = ContentValues()
        cv.put(COLUMN_NAME, name)
        cv.put(COLUMN_AGE, age)
        cv.put(COLUMN_BREED, breed)
        mDatabase.insert(TABLE_NAME, null, cv)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemDescription = itemView.findViewById<TextView>(R.id.itemName)
    }

    inner class MyRecyclerViewAdapter : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
            val myViewHolder = MyViewHolder(view)
            val position = myViewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                myViewHolder.itemDescription.setOnClickListener{
                    Toast.makeText(parent.context,
                        myViewHolder.adapterPosition,
                        Toast.LENGTH_SHORT).show()
                }
            }
            return myViewHolder
        }

        override fun getItemCount(): Int {
            return listItems.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val listItem = listItems[holder.adapterPosition]
            holder.itemDescription.text = listItem.desc
        }

    }
}

data class ListItem(val desc: String)

// TODO popuate list from db
private val listItems = listOf(
    ListItem("List Item #1"),
    ListItem("List Item #2"),
    ListItem("List Item #3"),
    ListItem("List Item #4"),
    ListItem("List Item #5"),
    ListItem("List Item #6"),
    ListItem("List Item #7"),
    ListItem("List Item #8"),
    ListItem("List Item #9"),
    ListItem("List Item #10")
)
