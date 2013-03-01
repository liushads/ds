<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if action?exists>
<#if action=='g'>
找回密码<br/>
你的密码保护回答是?<br/>
<#elseif action=='s'>
<#if plus?exists>
未开启密码保护回答之前不能找回密码，请先开启密码保护 <br/><br/>
</#if>
开启密码保护<br/>
请尽量设置复杂的密码保护回答，为了维护大家的利益请注意保管好自己帐号安全，不要将自己的书签或者账号信息透露给别人<br/>
请设置你的密码保护回答：<br/>
</#if>
</#if>
<input name="answer" maxlength="8" emptyok="false" type="text" /><br/>
<anchor>确定
	<@go href="/p?p_Ps/${pid}/" method="post"/>
		<postfield name="1" value="${action}" />
		<postfield name="2" value="$answer" />
	</go>
</anchor>
<br/>
<br/>
【声明】：官方在游戏中不会以任何形式向你索取你的账号信息请不要向任何人透露自己的信息<br/>
</p>
</card>
</wml>