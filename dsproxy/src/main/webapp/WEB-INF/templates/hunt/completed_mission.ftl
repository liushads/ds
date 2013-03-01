<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
${label_npc.name}：<br/>
<#if complete_mission_list?exists && complete_mission_list?size &gt; 0 >
	<#list complete_mission_list as playerMission> 
		击杀【${playerMission.condition.targetName}】完成
		<@a href="/p?h/${pid}/getrewards/${label_npc.id}/${playerMission.id}"/>
	 	领赏<br/>
	 	</a>
	</#list>
<#else>
列表为空	<br/>
</#if>
<br/><@a href="/p?p_T/${pid}/${label_npc.id}"/>返回</a>
<br/><@gofacility/>
</p>
</card>
</wml>
