package ru.netology.nmedia.db

import ru.netology.nmedia.Post


internal fun PostEntity.toModel()= Post(
    id = id,
    author = author,
    text = text,
    date = date,
    views = views,
    reposts = reposts,
    likesCount = likesCount,
    likeByMe = likeByMe,
    video = video,
)

internal fun Post.toEntity()= PostEntity(
    id = id,
    author = author,
    text = text,
    date = date,
    views = views,
    reposts = reposts,
    likesCount = likesCount,
    likeByMe = likeByMe,
    video = video,
)
