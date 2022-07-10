package ru.netology.nmedia.db

import android.database.Cursor
import ru.netology.nmedia.Post


fun Cursor.toPost()= Post(
    id = getLong(getColumnIndexOrThrow(PostsTable.ColumnPosts.ID.columnName)),
    author = getString(getColumnIndexOrThrow(PostsTable.ColumnPosts.AUTHOR.columnName)),
    text = getString(getColumnIndexOrThrow(PostsTable.ColumnPosts.TEXT.columnName)),
    date = getLong(getColumnIndexOrThrow(PostsTable.ColumnPosts.DATE.columnName)),
    views = getInt(getColumnIndexOrThrow(PostsTable.ColumnPosts.VIEWS.columnName)),
    reposts = getInt(getColumnIndexOrThrow(PostsTable.ColumnPosts.REPOSTS.columnName)),
    likesCount = getInt(getColumnIndexOrThrow(PostsTable.ColumnPosts.LIKESCOUNT.columnName)),
    likeByMe = getInt(getColumnIndexOrThrow(PostsTable.ColumnPosts.LIKE_BY_ME.columnName)) != 0,
    video = getString(getColumnIndexOrThrow(PostsTable.ColumnPosts.VIDEO.columnName)),
)
