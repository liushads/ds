欢迎来到斗神世界，进入游戏之前你需要先创建一个角色才能进行游戏。请选择你要创建的角色职业。提示：每个帐号可创建三个角色<br/>
<#if sects?exists && uid?exists>
	<#list sects as sect>
		<@a href="/p?u_SE/${uid}/${sect.id}/"/>${sect.name}</a><br/>
	</#list>
</#if>