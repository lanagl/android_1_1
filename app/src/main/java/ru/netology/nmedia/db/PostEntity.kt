package ru.netology.nmedia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
class PostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id:Long,
    val author:String,
    val text:String,
    val date:Long,
    val views:Int = 0,
    val reposts:Int = 0,
    val likesCount:Int = 0,
    val likeByMe:Boolean,
    val video: String?,
)
