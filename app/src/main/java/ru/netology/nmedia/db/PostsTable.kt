package ru.netology.nmedia.db

object PostsTable {
    const val POSTS_NAME = "posts"

    val DDL_POSTS ="""
        CREATE TABLE $POSTS_NAME (
            ${ColumnPosts.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${ColumnPosts.AUTHOR.columnName} TEXT NOT NULL,
            ${ColumnPosts.TEXT.columnName} TEXT NOT NULL,
            ${ColumnPosts.DATE.columnName} INTEGER NOT NULL, 
            ${ColumnPosts.VIEWS.columnName} INTEGER NOT NULL DEFAULT 0, 
            ${ColumnPosts.REPOSTS.columnName} INTEGER NOT NULL DEFAULT 0, 
            ${ColumnPosts.LIKESCOUNT.columnName} INTEGER NOT NULL DEFAULT 0, 
            ${ColumnPosts.LIKE_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT 0, 
            ${ColumnPosts.VIDEO.columnName} TEXT
        );
    """.trimIndent()

    enum class ColumnPosts(val columnName: String){
        ID("id"),
        AUTHOR("author"),
        TEXT("text"),
        DATE("date"),
        VIEWS("views"),
        REPOSTS("reposts"),
        LIKESCOUNT("likesCount"),
        LIKE_BY_ME("likeByMe"),
        VIDEO("video"),
    }

    val ALL_COLUMNS_POSTS_NAMES = ColumnPosts.values().map {
        it.columnName
    }.toTypedArray()

}
