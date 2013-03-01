<#assign img_server="http://58.61.39.100:80/images"/>
<#assign bbs_server="http://58.61.39.100:9001"/>
<#assign game_title="斗神OL"/>
<#if zoneId?exists>
	<#assign game_title="斗神OL("+zoneId+"区)"/>
</#if>

<#macro app>
<#if from?exists>
  <#if from = 'zone'>
     <a href="http://mg.3g.qq.com/CookieJumpServlet.do?cpid=917&amp;gameid=127&amp;uid=${ouid}&amp;jumpType=app">应用</a>>斗神OL<br/>
     <br/>
  </#if>
</#if>
</#macro>

<#macro qqbar>
<#if ouid?exists>
<a href="http://mg.3g.qq.com/CookieJumpServlet.do?cpid=917&amp;gameid=127&amp;uid=${ouid}&amp;jumpType=mgHome">手机腾讯网</a>-<a href="http://mg.3g.qq.com/CookieJumpServlet.do?cpid=917&amp;gameid=127&amp;uid=${ouid}&amp;jumpType=mgameHome">QQ网游</a><br/>
我的<a href="http://mg.3g.qq.com/CookieJumpServlet.do?cpid=917&amp;gameid=127&amp;uid=${ouid}&amp;jumpType=zone">空间</a>.<a href="http://mg.3g.qq.com/CookieJumpServlet.do?cpid=917&amp;gameid=127&amp;uid=${ouid}&amp;jumpType=house">家园</a><br/>
</#if>
小Q报时(${qqtime})
</#macro>

<#macro company>
</#macro>