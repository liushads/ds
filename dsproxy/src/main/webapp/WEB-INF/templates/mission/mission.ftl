<#macro mission_desc label_mission>
	<#if label_mission.bonus?exists && label_mission.bonus?trim != "">
		完成后奖励：
		${label_mission.bonus}
	</#if>	
	<#if label_mission.extraBonus?exists && label_mission.extraBonus?trim != "">
		额外奖励：
		${label_mission.extraBonus}
	</#if>
</#macro>
