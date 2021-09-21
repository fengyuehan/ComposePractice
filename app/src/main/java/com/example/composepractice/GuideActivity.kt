package com.example.composepractice

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composepractice.ui.theme.ComposePracticeTheme
import com.example.composepractice.utils.SpUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

class GuideActivity : AppCompatActivity() {
    companion object {
        const val HAS_SHOW_GUIDE = "HAS_SHOW_GUIDE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (SpUtils.getBool(HAS_SHOW_GUIDE)) {
            go2Main()
        } else {
            setContent {
                ComposePracticeTheme {
                    GuidePage(listOf(R.mipmap.guide_1,R.mipmap.guide_2,R.mipmap.guide_3) ) {
                        go2Main()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun GuidePage(imageList: List<Int>, go2Main: () -> Unit) {
        Box(Modifier.fillMaxSize()) {
            val pageState = rememberPagerState(
                pageCount = imageList.size,
                initialOffscreenLimit = 2
            )

            HorizontalPager(
                state = pageState,
                modifier = Modifier.fillMaxSize(),
                ) { page ->
                Image(
                    painter = painterResource(id = imageList[page]),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }

            if (pageState.currentPage == imageList.size - 1) {
                Button(
                    onClick = {
                        go2Main()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(32.dp)
                ) {

                    /**
                     * @Composable
                        fun Text(
                        text: String, // 文本
                        // Modifier 一个 有序的、不可变的修饰元素集合，用于添加装饰或者行为到Compose UI元素。例如background、padding 、点击事件等。
                        modifier: Modifier = Modifier,
                        color: Color = Color.Unspecified, // 文字颜色
                        fontSize: TextUnit = TextUnit.Unspecified, // 文字大小
                        fontStyle: FontStyle? = null, // 绘制字母时使用的字体变体（例如，斜体）
                        fontWeight: FontWeight? = null, // 字体粗细
                        fontFamily: FontFamily? = null, // 呈现文本时要使用的字体系列
                        letterSpacing: TextUnit = TextUnit.Unspecified, // 字间距
                        textDecoration: TextDecoration? = null, // 文字装饰、比如下划线
                        textAlign: TextAlign? = null, // 对齐方式
                        lineHeight: TextUnit = TextUnit.Unspecified, // 行高
                        overflow: TextOverflow = TextOverflow.Clip, // 文字显示不完的处理方式，例如尾部显示…或者中间显示…
                        softWrap: Boolean = true, // 文本是否应在换行符处中断。如果为false，则文本的宽度会在水平方向上无限延伸，且textAlign属性失效，可能会出现异常情况。
                        maxLines: Int = Int.MAX_VALUE, // 最大行数
                        onTextLayout: (TextLayoutResult) -> Unit = {}, // 计算新的文本布局时执行的回调
                        style: TextStyle = LocalTextStyle.current // 文本的样式配置，例如颜色，字体，行高等。也就是说上面属性中的color,fontSize等一些属性也可以在这里进行声明。具体包含的属性可以参考TextStyle类。
                        ) {
                        ...
                        }
                     */
                    Text(
                        text = stringResource(id = R.string.start_experience),
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }

            HorizontalPagerIndicator(
                pagerState = pageState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
                activeColor = MaterialTheme.colors.primary,
                inactiveColor = Color.White
            )
        }

    }

    private fun go2Main() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}