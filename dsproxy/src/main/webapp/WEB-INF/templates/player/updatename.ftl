<#include "/include/header.ftl">

<card title="${game_title}">
<p>
申请改名<br/>
你旧的昵称是:${player.name}<br/>
请输入你新的昵称:<br/>
<input name="name" maxlength="12" emptyok="false" type="text" value=""/><br/>
<anchor>申请修改
	<@go href="/p?p_Unt/${pid}/" method="post"/>
			<postfield name="1" value="$name" />
	</go>
</anchor>	
<br/>
<@goback />
<@gofacility/>
</p></card>
</wml>