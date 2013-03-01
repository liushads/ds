<#-- 显示玩家所要装备类物品 -->
<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if label_buff?exists>
	<#list label_buff as buff>
		${buff.id}/${buff.buffValue}<br/>
	</#list>
</#if>
<@goback />
<@gogame/>
</p></card>
</wml> 