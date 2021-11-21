package com.example.composeweather.common

import androidx.compose.runtime.Composable

@Composable
fun<T> PageStateControl(state:ContentState<T>,
                        onClick:() -> Unit,
                        content:@Composable (T) -> Unit) = when(state){
                            Loading -> {
                                LoadingPage()
                            }
                            is Error -> ErrorPage (onClick = onClick)
                            is Empty -> EmptyPage(state.reason)
                            is Success<T> -> content(state.data)
                        }