<#include "/include/header.ftl">
<card title="${game_title}"><p>
请输入道具密码：<input type="password" name="passwd" value=""></input><br/>
<anchor>确认
	<@go href="/p?i_UnB/${pid}/" method="post" />
		<postfield name="1" value="${obj}" />
		<postfield name="2" value="$passwd" />
	</go>		
</anchor>
<br/>
<@goback/>
</p>
</card>
</wml>