package com.example.composepractice

import androidx.navigation.NavDirections
import com.example.composepractice.data.Recommend
import com.example.composepractice.data.Song
import com.example.composepractice.ui.fragment.PlaySongFragmentDirections

abstract class MusicScreen(val directions: NavDirections){
    class PlayList(recommend: Recommend):MusicScreen(NavGraphDirections.toMusicPlayListFragment(recommend))

    //歌曲播放
    class PlaySong : MusicScreen(NavGraphDirections.toPlaySongFragment())

    //歌曲评论
    class SongComment(song: Song) :
        MusicScreen(PlaySongFragmentDirections.actionPlaySongFragmentToCommentsFragment(song))
}