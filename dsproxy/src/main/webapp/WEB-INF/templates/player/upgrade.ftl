<#include "/include/header.ftl">
<card title="${game_title}"><p>
	<@a href="/p?mv_W/${pid}/${curr_facility.id}/"/>继续游戏</a><br/>
	<img alt="" src="${img_server}/tan.gif" />恭喜你，升到${player.level}级了!<br/>
	<#if curLevel?exists && preLevel?exists>
	体力+${curLevel.hp-preLevel.hp}，攻击+${curLevel.attackMin-preLevel.attackMin}，防御+${curLevel.defence-preLevel.defence}<br/>
	</#if>
	<#if player_upgrade?exists>
	<#if player_upgrade.content?exists>
	【小提示】<br/>	
	${player_upgrade.content}<br/>
	</#if>
	</#if>
	<@a href="/p?mv_W/${pid}/${curr_facility.id}/"/>继续游戏</a>
</p></card>
</wml>