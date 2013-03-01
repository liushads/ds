<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
<#if label_desc?exists>
${label_desc}<br/>
<#else>
<a href="/notice.wml">短信充值未到账解决办法</a><br/>
<#assign d={"1":"手机充值卡","2":"Q币","3":"短信"}>
<#macro showType type target>
<#if type=target>
${d[type]}
<#else>
<@a href="/p?pa_I/${pid}/${type}/"/>${d[type]}</a>
</#if>
</#macro>
<#if desc?exists>
您的银贝或者金锭/金票不足，您可以选择以下方式充值金锭/金票，或者在商城用金锭/金票<@a href="/p?ma_BGN/${pid}/46"/>购买银矿石</a>来获取银；<br/>
</#if>
金票和金锭充值<br/>
你现有:<br/>
<#if player?exists>
<@showGold player/>
</#if>
<@a href="/ga?pa_A/${pid}/${player.userId}/"/>游戏账户</a><br/>
<#--Q币-->
<anchor>金票充值
	<@go href="/qpa" method="post"/>
		<postfield name="uid" value="${player.userId}" />
		<postfield name="pid" value="${pid}" />
		<postfield name="alias" value="B" />
		<postfield name="retUrl" value="<@encodeUrl "/p?pa_R/${pid}/", userKey, Md5Key/>" />
		<postfield name="5" value="0" />
    </go>
</anchor><br/>
<anchor>金锭充值
	<@go href="/qpa" method="post"/>
		<postfield name="uid" value="${player.userId}" />
		<postfield name="pid" value="${pid}" />
		<postfield name="retUrl" value="<@encodeUrl "/p?pa_R/${pid}/", userKey, Md5Key/>" />
    </go>
</anchor><br/>
</#if>
<@goback/><br/>
<@gogame/>
</p></card>
</wml>