package com.example.composewidget.page

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composewidget.R
import com.example.composewidget.titleLiveData

@ExperimentalTextApi
@Composable
fun TextPageDemo() {
    titleLiveData.value = "Compose Text"
    /**
     * Compose中是没有滚动布局的，只有滚动修饰符verticalScroll和horizontalScroll
     */
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            TextDemo()
        }
    }
}

@ExperimentalTextApi
@Composable
fun TextDemo() {
    /**
     * fun Text(
    // 显示的文本字符串
    text: String,
    // 修改器，可以配置文本的大小及显示外观
    modifier: Modifier = Modifier,
    // 文本的颜色
    color: Color = Color.Unspecified,
    // 文本的字号大小
    fontSize: TextUnit = TextUnit.Unspecified,
    // 文字样式
    fontStyle: FontStyle? = null,
    // 文字粗细
    fontWeight: FontWeight? = null,
    // 文本字体
    fontFamily: FontFamily? = null,
    // 文字间的间距
    letterSpacing: TextUnit = TextUnit.Unspecified,
    // 文字添加装饰，可以添加上划线、下划线、中划线
    textDecoration: TextDecoration? = null,
    // 文本的对齐方式
    textAlign: TextAlign? = null,
    // 每一行的行高
    lineHeight: TextUnit = TextUnit.Unspecified,
    // 文本溢出的显示效果
    overflow: TextOverflow = TextOverflow.Clip,
    // 是否自动换行
    softWrap: Boolean = true,
    // 最多显示几行
    maxLines: Int = Int.MAX_VALUE,
    // 计算布局时的回调
    onTextLayout: (TextLayoutResult) -> Unit = {},
    // 样式
    style: TextStyle = LocalTextStyle.current
    )
     */
    Text(text = "Hello World")
    Text(text = stringResource(id = R.string.app_name))
    Text(text = "改变颜色", color = MaterialTheme.colors.primary)
    Text(text = "改变大小", fontSize = 30.sp)
    Text(text = "斜体", fontStyle = FontStyle.Italic)
    Text(text = "粗体", fontWeight = FontWeight.Bold)
    Text(text = "水平左对齐", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
    Text(text = "水平居中对齐", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    Text(text = "水平右对齐", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
    Spacer(modifier = Modifier.height(10.dp))
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colors.primary)
    ) {
        Text(text = "垂直居上对齐", color = MaterialTheme.colors.onPrimary)
    }
    Spacer(modifier = Modifier.height(10.dp))
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colors.primary)
    ) {
        Text(text = "垂直居中对齐", color = MaterialTheme.colors.onPrimary)
    }
    Spacer(modifier = Modifier.height(10.dp))
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colors.primary)
    ) {
        Text(text = "垂直居下对齐", color = MaterialTheme.colors.onPrimary)
    }
    Spacer(modifier = Modifier.height(10.dp))
    Text(text = "改个字体", fontFamily = FontFamily.Serif)
    Text(
        text = "加载Assets中的字体",
        fontFamily = FontFamily(Font(LocalContext.current.assets, "YouRan.ttf"))
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(text = "⬇️⬇️⬇️富文本⬇️⬇️⬇️")
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(MaterialTheme.colors.primary)) {
            append("试试富文本")
        }
        append("He")
        withStyle(style = SpanStyle(MaterialTheme.colors.secondary)) {
            append("ll")
        }
        append("o world")
        append("富文本")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("富文本加了个粗")
        }
    })
    Text(text = "⬆⬆⬆️富文本⬆️⬆⬆")
    Spacer(modifier = Modifier.height(10.dp))

    Text(text = "限制行数")
    Text(
        text = "限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行限制2行",
        maxLines = 2
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "限制2行，多出省略限制2行，多出省略限制2行，多出省略限制2行，多出省略限制2行，多出省略限制2行，多出省略限制2行，多出省略限制2行，多出省略限制2行，多出省略限制2行，多出省略限制2行，多出省略限制2行，多出省略",
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
    Spacer(modifier = Modifier.height(10.dp))
    SelectionContainer {
        Text(text = "可选中的文本")
    }
    Spacer(modifier = Modifier.height(10.dp))
    SelectionContainer {
        Column {
            Text(text = "可选中的文本")
            Text(text = "可选中的文本")
            DisableSelection {
                Text(text = "不可选中的文本")
            }
            Text(text = "可选中的文本")
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    val context = LocalContext.current
    ClickableText(
        text = buildAnnotatedString { append("可以点击的文本") },
        style = TextStyle(color = MaterialTheme.colors.onBackground)
    ) {
        Toast.makeText(context, "第${it}个字符被点击了", Toast.LENGTH_SHORT).show()
    }
    Spacer(modifier = Modifier.height(10.dp))

    /**
     * 在使用buildAnnotatedString时，可以使用pushStringAnnotation携带一个tag数据, 在点击对应tag时， onClick 中，可以使用getStringAnnotations获取携带的数据。
     */
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.onSurface
            )
        ) {
            append("试试链接吧，点击")
        }
        pushStringAnnotation(
            tag = "URL",
            annotation = "https://developer.android.com"
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("这里")
        }
        pop()
    }
    ClickableText(text = annotatedString, onClick = {
        offset ->
        annotatedString.getStringAnnotations(
            tag = "URL",
            start = offset,
            end = offset
        ).firstOrNull()?.let { annotation ->
            Toast.makeText(context, "点击链接${annotation.item}", Toast.LENGTH_SHORT).show()
        }
    })
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        buildAnnotatedString {

            withStyle(
                // 设置段落样式
                style = ParagraphStyle(
                    lineHeight = 60.sp,
                    textAlign = TextAlign.Center
                )
            ) {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Jetpack Compose\n")
                }

                withStyle(
                    style = SpanStyle(
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                ) {
                    append("by\n")
                }

                withStyle(
                    style = SpanStyle(
                        color = Color.Green,
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append("依然范特稀西\n")
                }

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color.Red
                    )
                ) {
                    append("@公众号：技术最TOP")
                }
            }
        },
        modifier = Modifier.width(400.dp)
    )

}
