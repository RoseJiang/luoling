<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@page language="java" import="com.luoling.weixin.util.WeixinUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎访问搜投网  www.souvc.com </title>
<%
        Map<String,Object>  ret = new HashMap<String,Object> ();
        ret=WeixinUtil.getWxConfig(request);
        request.setAttribute("appId", ret.get("appId"));
        request.setAttribute("timestamp", ret.get("timestamp"));
        request.setAttribute("nonceStr", ret.get("nonceStr"));
        request.setAttribute("signature", ret.get("signature"));
        %> 
</head>
<body>
        
        欢迎访问搜投网！
        
</body>


<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">

// 微信信息的以及调用的配置
wx.config({
    debug: false, 
    appId: '${appId}', 
    timestamp: '${timestamp}', 
    nonceStr: '${nonceStr}', 
    signature: '${signature}',
    jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo','onMenuShareQZone'] 
});

wx.ready(function(){
    // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
    wx.onMenuShareTimeline({
        title: '第八篇 ：微信公众平台开发实战Java版之如何网页授权获取用户基本信息', // 分享标题
        link:"http://www.cnblogs.com/liuhongfeng/p/5099149.html",
        imgUrl: "http://images.cnblogs.com/cnblogs_com/liuhongfeng/737147/o_1442809977405.jpg" // 分享图标
    });
    // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
    wx.onMenuShareAppMessage({
        title: '第七篇 ：微信公众平台开发实战Java版之如何获取微信用户基本信息', // 分享标题
        desc: "第七篇 ：微信公众平台开发实战Java版之如何获取微信用户基本信息", // 分享描述
        link:"http://www.cnblogs.com/liuhongfeng/p/5057167.html",
        imgUrl: "http://images.cnblogs.com/cnblogs_com/liuhongfeng/737147/o_QQ%E5%9B%BE%E7%89%8720151118180508.png", // 分享图标
        type: 'link', // 分享类型,music、video或link，不填默认为link
    });
    
    //获取“分享到QQ”按钮点击状态及自定义分享内容接口
    wx.onMenuShareQQ({
        title: '第六篇 ：微信公众平台开发实战Java版之如何自定义微信公众号菜单', // 分享标题
        desc: '第六篇 ：微信公众平台开发实战Java版之如何自定义微信公众号菜单', // 分享描述
        link: 'http://www.cnblogs.com/liuhongfeng/p/4857312.html', // 分享链接
        imgUrl: 'http://images.cnblogs.com/cnblogs_com/liuhongfeng/737147/o_qrcode_for_gh_228cd30523bc_258.jpg', // 分享图标
        success: function () { 
           // 用户确认分享后执行的回调函数
        },
        cancel: function () { 
           // 用户取消分享后执行的回调函数
        }
    });
    
    //获取“分享到腾讯微博”按钮点击状态及自定义分享内容接口
    wx.onMenuShareWeibo({
        title: '分享到腾讯微博标题', // 分享标题
        desc: '分享到腾讯微博描述', // 分享描述
        link: 'http://www.cnblogs.com/liuhongfeng/p/4857312.html', // 分享链接
        imgUrl: 'http://images.cnblogs.com/cnblogs_com/liuhongfeng/737147/o_qrcode_for_gh_228cd30523bc_258.jpg', // 分享图标
        success: function () { 
           // 用户确认分享后执行的回调函数
        },
        cancel: function () { 
            // 用户取消分享后执行的回调函数
        }
    });
    
    //获取“分享到QQ空间”按钮点击状态及自定义分享内容接口
    wx.onMenuShareQZone({
        title: '分享到QQ空间标题', // 分享标题
        desc: '分享到QQ空间描述', // 分享描述
        link: 'http://www.cnblogs.com/liuhongfeng/p/4857312.html', // 分享链接
        imgUrl: 'http://images.cnblogs.com/cnblogs_com/liuhongfeng/737147/o_qrcode_for_gh_228cd30523bc_258.jpg', // 分享图标
        success: function () { 
           // 用户确认分享后执行的回调函数
        },
        cancel: function () { 
            // 用户取消分享后执行的回调函数
        }
    });
    
});
</script>

</html>