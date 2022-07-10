package ru.netology.nmedia.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.Post

class PostDaoImpl(
    private val db: SQLiteDatabase
): PostDao {
    override fun getAll()=
        db.query(
            PostsTable.POSTS_NAME,
            PostsTable.ALL_COLUMNS_POSTS_NAMES,
            null,
            null,
            null,
            null,
            "${PostsTable.ColumnPosts.ID.columnName} DESC"
        ).use { cursor ->

            List(cursor.count) {
                cursor.moveToNext()
                cursor.toPost()
            }
        }


    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            put(PostsTable.ColumnPosts.AUTHOR.columnName, post.author)
            put(PostsTable.ColumnPosts.TEXT.columnName, post.text)
            put(PostsTable.ColumnPosts.DATE.columnName, post.date)
            put(PostsTable.ColumnPosts.VIDEO.columnName, post.video)
        }
        val id = if (post.id != 0L) {
            db.update(
                PostsTable.POSTS_NAME,
                values,
                "${PostsTable.ColumnPosts.ID.columnName} =?",
                arrayOf(post.id.toString())
            )
            post.id
        } else {
            db.insert(PostsTable.POSTS_NAME, null, values)
        }
        return db.query(
            PostsTable.POSTS_NAME,
            PostsTable.ALL_COLUMNS_POSTS_NAMES,
            "${PostsTable.ColumnPosts.ID.columnName} =?",
            arrayOf(id.toString()),
            null,
            null,
            null
        ).use { cursor ->
            cursor.moveToNext()
            cursor.toPost()
        }
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
                UPDATE ${PostsTable.POSTS_NAME} SET
                    ${PostsTable.ColumnPosts.LIKESCOUNT.columnName} = ${PostsTable.ColumnPosts.LIKESCOUNT.columnName} +
                    CASE WHEN ${PostsTable.ColumnPosts.LIKE_BY_ME.columnName} THEN -1 ELSE 1 END,
                    ${PostsTable.ColumnPosts.LIKE_BY_ME.columnName} = CASE WHEN ${PostsTable.ColumnPosts.LIKE_BY_ME.columnName}
                    THEN 0 ELSE 1 END
                WHERE ${PostsTable.ColumnPosts.ID.columnName} = ?;
            """.trimIndent(), arrayOf(id))
    }

    override fun removeById(id: Long) {
        db.delete(
            PostsTable.POSTS_NAME,
           "${PostsTable.ColumnPosts.ID.columnName}=?",
            arrayOf(id.toString())
        )
    }
}
