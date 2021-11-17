package com.example.composewidget.viewmoduel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.composewidget.model.FuncInfo
import com.example.composewidget.navigation.Screen

class CanvasViewModule :ViewModel() {
    val functions = arrayListOf<FuncInfo>().apply {
        add(FuncInfo("drawLine", Screen.CanvasList.drawLine, Color(0xFF365299)))
        add(FuncInfo("drawRect", Screen.CanvasList.drawRect, Color(0xFF365299)))
        add(FuncInfo("drawImage", Screen.CanvasList.drawImage, Color(0xFF365299)))
        add(FuncInfo("drawCircle", Screen.CanvasList.drawCircle, Color(0xFF365299)))
        add(FuncInfo("drawOval", Screen.CanvasList.drawOval, Color(0xFF365299)))
        add(FuncInfo("drawArc", Screen.CanvasList.drawArc, Color(0xFF365299)))
        add(FuncInfo("drawPath", Screen.CanvasList.drawPath, Color(0xFF365299)))
        add(FuncInfo("drawPoints", Screen.CanvasList.drawPoints, Color(0xFF365299)))
        add(FuncInfo("inset", Screen.CanvasList.inset, Color(0xFF365299)))
        add(FuncInfo("translate", Screen.CanvasList.translate, Color(0xFF365299)))
        add(FuncInfo("rotate", Screen.CanvasList.rotate, Color(0xFF365299)))
        add(FuncInfo("rotateRad", Screen.CanvasList.rotateRad, Color(0xFF365299)))
        add(FuncInfo("scale", Screen.CanvasList.scale, Color(0xFF365299)))
        add(FuncInfo("clipRect", Screen.CanvasList.clipRect, Color(0xFF365299)))
        add(FuncInfo("clipPath", Screen.CanvasList.clipPath, Color(0xFF365299)))
        add(FuncInfo("drawIntoCanvas", Screen.CanvasList.drawIntoCanvas, Color(0xFF365299)))
        add(FuncInfo("withTransform", Screen.CanvasList.withTransform, Color(0xFF365299)))
        add(FuncInfo("drawWithContent", Screen.CanvasList.drawWithContent, Color(0xFF365299)))
        add(FuncInfo("BlendMode", Screen.CanvasList.BlendMode, Color(0xFF365299)))
    }
}