<#include "/include/header.ftl">
<card title="${game_title}"><p>
任务列表：<br/>
<#if playerMissions?exists && playerMissions?size &gt; 0 >
	<#list playerMissions as mission> 
	<#if mission.complete>		
	 	击杀【${mission.condition.targetName}】完成，未领赏<br/><#else>
	 	<@a href="/p?h_LM/${pid}/info/${mission.id}/${mission.condition.targetId}"/>
	 	击杀【${mission.condition.targetName}】可得${mission.condition.bonusGold}个${rewardItem.name}<br/></a>
	 	</#if>
	 	
	</#list>
<#else>
没有任务	<br/>
</#if>
<@goback/>
<@gofacility/>
</p>
</card>
</wml>
