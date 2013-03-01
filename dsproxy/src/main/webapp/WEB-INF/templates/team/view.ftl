<#include "/include/header.ftl">
<#assign f=team.instanceTeam>
<card title="${game_title}"><p>
队伍属性:<br/>
<#if team?exists>
${team.members[0].name}之队<br/>
	【成员】<br/>
	<#list team.members as member>
		<#if team.members[member_index].playerId = pid >
			<#assign f=true>
		<#else>
			<@a href="/p?p_VO/${pid}/${member.playerId}"/>${member.name}</a>[${sect_name[member.sectId?string]}]<br/>
		</#if>
	</#list>
	【奖励】<br/>
	<#list team.reward as ps>
		${ps.sectSkill.alias}<br/>
	</#list>
	<#if !f>
	<@a href="/p?te_J/${pid}/${team.id}"/>加入队伍</a><br/>
	<#else>
	</#if>
</#if>
<@goback />
<@gogame/>
</p>
</card>
</wml>

 