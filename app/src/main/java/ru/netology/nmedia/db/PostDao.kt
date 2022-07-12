package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE posts SET text= :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)

    @Query(
        """
            UPDATE posts SET 
            likesCount = likesCount + CASE WHEN likeByMe THEN -1 ELSE 1 END,
            likeByMe = CASE WHEN likeByMe THEN 0 ELSE 1 END
            WHERE id=:id
        """
    )
    fun likeById(id: Long)

    @Query(
        """
                  UPDATE posts SET 
            reposts = reposts + 1
            WHERE id=:id      
        """
    )
    fun shareById(id: Long)

    @Query("DELETE FROM posts WHERE id=:id")
    fun removeById(id: Long)
}
