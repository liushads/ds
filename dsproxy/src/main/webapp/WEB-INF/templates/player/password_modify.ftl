<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if modify?exists>
你已经开启了密码保护，你可以修改新的密码保护<br/>
</#if>
修改账号保护<br/>
你的旧密码保护回答是？<br/>
<input name="oldanswer" maxlength="8" emptyok="false" type="text" /><br/>
你的新密码保护回答是？<br/>
<input name="newanswer" maxlength="8" emptyok="false" type="text" /><br/>
<anchor>确定
	<@go href="/p?p_Ps/${pid}/" method="post"/>
		<postfield name="1" value="m" />
		<postfield name="2" value="$oldanswer" />
		<postfield name="3" value="$newanswer" />
	</go>
</anchor>
<br/>
<br/>
【声明】：官方在游戏中不会以任何形式向你索取你的账号信息请不要向任何人透露自己的信息<br/>

</p>
</card>
</wml>