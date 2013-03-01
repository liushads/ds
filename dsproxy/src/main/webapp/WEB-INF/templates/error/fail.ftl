<#include "/include/header.ftl">
<card title="${game_title}">
<p>
<#if desc?exists>
${desc}<br/>
<@goback/>
</#if>
<#if err_msg?exists>
<#if err_msg.code == -1><@a href="/p?p_E/${pid}/"/>继续游戏</a><br/><br/>
<#else>
	<@goback/>
	<#if pid?exists && err_msg.code!=-104 && err_msg.code!=-22 && err_msg.code!=-20>
		<@gofacility/>
	</#if>
</#if>
${err_msg.text?if_exists}(错误码:${err_msg.code})<br/>
<#if err_msg.code == -437 || err_msg.code == -909 || err_msg.code == -908>
<@a href="/p?ma_I/${pid}/1/"/>去商城购买</a><br/>
<#elseif err_msg.code == -22>
<@a href="/p?pa_I/${pid}/2/"/>短信充值</a><br/>
<#elseif err_msg.code == -104>
<@a href="/p?p_Ps/${pid}/p/g/"/>找回密码</a><br/>
</#if>
<#--<@a href="/p?p_E/${pid}/"/>继续游戏</a>-->
<#if err_msg.code == -1><@a href="/p?p_E/${pid}/"/>继续游戏</a><br/><br/>
<#else>
	<@goback/>
	<#if pid?exists && err_msg.code!=-104 && err_msg.code!=-22 && err_msg.code!=-20>
		<@gofacility/>
	</#if>
</#if>
</#if>
</p>
</card>
</wml>