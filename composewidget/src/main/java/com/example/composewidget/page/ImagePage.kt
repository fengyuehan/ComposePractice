package com.example.composewidget.page

import android.graphics.Bitmap
import android.graphics.Paint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.createBitmap
import com.example.composewidget.R
import com.example.composewidget.titleLiveData
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun ImagePageDemo(){
    titleLiveData.value = "Compose Image"

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ImageDemo()
    }
}

@Composable
fun ImageDemo() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        LoadDrawableDemo()
        LoadDrawableDemo1()
        LoadDrawableDemo2()
        LoadBitmapDemo()
        LoadBitmapDemo1()
        RoundCornerDemo()
        CircleDemo()
        ObserveStateDemo()
        LoadUrlDemo()
        ColorFilterDemo()
        TintDemo()
        ContentScaleDemo()
    }
}

@Composable
fun ContentScaleDemo() {
    Text(text = "ContentScale.Crop")
    UrlImage(contentScale = ContentScale.Crop)

    Text(text = "ContentScale.Fit")
    UrlImage(contentScale = ContentScale.Fit)

    Text(text = "ContentScale.FillBounds")
    UrlImage(contentScale = ContentScale.FillBounds)

    Text(text = "ContentScale.FillHeight")
    UrlImage(contentScale = ContentScale.FillHeight)

    Text(text = "ContentScale.FillWidth")
    UrlImage(contentScale = ContentScale.FillWidth)

    Text(text = "ContentScale.Inside")
    UrlImage(contentScale = ContentScale.Inside)

    Text(text = "ContentScale.None")
    UrlImage(contentScale = ContentScale.None)
}

@Composable
fun UrlImage(contentScale: ContentScale?){
    Image(
        painter = rememberCoilPainter(
            "https://pic3.zhimg.com/v2-77bbb941f260b90f0193ef73d3f2b9e4_1440w.jpg?source=172ae18b",
            fadeIn = true
        ), contentDescription = null,
        modifier = Modifier
            .size(200.dp, 300.dp)
            .background(MaterialTheme.colors.onBackground),
        contentScale = contentScale ?: ContentScale.Fit,
        alignment = Alignment.Center
    )
    Spacer(modifier = Modifier.height(10.dp))

}

@Composable
fun TintDemo() {
    Text(text = "tint")
    Image(
        painter = painterResource(id = R.drawable.logo), contentDescription = null,
        colorFilter = ColorFilter.tint(Color.Red)
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun ColorFilterDemo() {
    Text(text = "colorFilter")
    Image(
        painter = rememberCoilPainter(
            "https://macjpeg.macsc.com/macdown/pic/202009/03113634_e7f0bb805b.jpeg",
            fadeIn = true
        ),
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        colorFilter = ColorFilter.colorMatrix(
            ColorMatrix(
                floatArrayOf(
                    -1f, 0f, 0f, 0f, 255f,
                    0f, -1f, 0f, 0f, 255f,
                    0f, 0f, -1f, 0f, 255f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        ),
        contentDescription = null,
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun LoadUrlDemo() {
    Text(text = "加载url")
    Image(
        painter = rememberCoilPainter(
            "https://macjpeg.macsc.com/macdown/pic/202009/03113634_e7f0bb805b.jpeg",
            fadeIn = true
        ),
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentDescription = null,
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun ObserveStateDemo() {
    Text(text = "监听加载状态")
    val painter = rememberCoilPainter(request = "https://macjpeg.macsc.com/macdown/pic/202009/03113634_e7f0bb805b.jpeg")
    Box{
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.FillWidth
        )
        when(painter.loadState){
            is ImageLoadState.Loading -> {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center),
                    color = Color.Red
                )
            }
            is ImageLoadState.Error -> {

            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun CircleDemo() {
    Text(text = "圆")
    Surface (
        shape = RoundedCornerShape(percent = 50),
        modifier = Modifier.padding(10.dp)
    ) {
        Image(
            painter = rememberCoilPainter(
                "https://pic3.zhimg.com/v2-77bbb941f260b90f0193ef73d3f2b9e4_1440w.jpg?source=172ae18b",
                fadeIn = true,
            ),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .background(colors.onBackground),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun RoundCornerDemo() {
    Text(text = "加个圆角")
    Surface (
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(10.dp)
    ){
        Image(painter = rememberCoilPainter(
            request = "https://pic3.zhimg.com/v2-77bbb941f260b90f0193ef73d3f2b9e4_1440w.jpg?source=172ae18b",
            previewPlaceholder = R.drawable.logo
        ), 
            contentDescription = null,
            modifier = Modifier.background(colors.onBackground)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun LoadBitmapDemo() {
    Text(text = "加载Bitmap")
    val bitmap = createBitmap(100,100,config = Bitmap.Config.ARGB_8888)
    bitmap.applyCanvas {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = android.graphics.Color.RED
        drawCircle(50f,50f,50f,paint)
    }
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun LoadBitmapDemo1() {
    Text(text = "加载bitmap")
    val bitmap = createBitmap(100, 100)
    bitmap.applyCanvas {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = colors.onBackground.toArgb()
        drawCircle(50f, 50f, 50f, paint)
    }
    Image(bitmap = bitmap.asImageBitmap(), contentDescription = null)
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun LoadDrawableDemo2() {
    Text(text = "加载资源drawable")
    /**
     * contentDescription :无障碍服务用于描述此图像表示的文本。
     */
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null,
        modifier = Modifier
            .height(50.dp)
            .width(50.dp)
            .clip(shape = RoundedCornerShape(50))
            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(50)),
        contentScale = ContentScale.FillBounds
    )
    Spacer(modifier = Modifier.height(10.dp))
}



@Composable
fun LoadDrawableDemo() {
    Text(text = "加载资源drawable")
    /**
     * contentDescription :无障碍服务用于描述此图像表示的文本。
     */
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null,
        modifier = Modifier
            .height(100.dp)
            .width(100.dp),
        contentScale = ContentScale.Crop
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun LoadDrawableDemo1() {
    Text(text = "加载资源drawable")
    /**
     * contentDescription :无障碍服务用于描述此图像表示的文本。
     */
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = null,
        modifier = Modifier
            .height(100.dp)
            .width(100.dp),
        contentScale = ContentScale.FillBounds
    )
    Spacer(modifier = Modifier.height(10.dp))
}
