<#include "/include/header.ftl">
<card title="${game_title}"><p>

<#if team?exists>
	${team.members[0].name}之队<br/>
	【成员】<br/>
	<#list team.members as member>
	
		<#if team.members[member_index].playerId = pid >
			${member.name}[${sect_name[member.sectId?string]}]
		<#else>
			<@a href="/p?p_VO/${pid}/${member.playerId}"/>${member.name}</a>[${sect_name[member.sectId?string]}]
		</#if>
		<#if member.online == 0>[离线]</#if>
		
		<#--队长查看，有踢出选项-->
		<#if member_index!=0 && team.members[0].playerId = pid && !team.instanceTeam >
			<@a href="/p?te_K/${pid}/${member.playerId}"/>踢出</a>
		</#if>
		<br/>
	</#list>
	【奖励】<br/>
	<#list team.reward as ps>
		${ps.sectSkill.alias}<br/>
	</#list>
	<#if !team.instanceTeam>
	<@a href="/p?te_Q/${pid}/"/>脱离队伍</a><br/>
	</#if>
<#else>
	你现在没有加入任何队伍！你可以创建组队或选择有*的玩家加入队伍；队伍成员享有不同门派的独有心法的加成，比如有成员是毒龙教的，则队伍其他成员享有毒龙教毒龙心经的心法加成。<br/>
	<@a href="/p?te_C/${pid}/"/>创建队伍</a><br/>
	<#if (teams?exists && teams?size>0)>队伍列表：<br/>
	<#list teams as t>
		<@a href="/p?te_V/${pid}/${t.id}"/>${t.members[0].name}之队</a>(${t.onlineMembers}/${t.members?size}人) <@a href="/p?te_J/${pid}/${t.id}"/>加入</a><br/> 
	</#list>
	</#if>
</#if>
<@goback />
<@gogame/>
</p>
</card>
</wml>

 