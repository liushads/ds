<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if last_npc?exists>${last_npc.name}:<#else>【提示】:</#if>
<#if player_item?exists>
	恭喜,你获得下列物品:<br/>
	<@showPlayerItem player_item/>
	<#if desc?exists && player_item.item.type = 1>
		附加属性:<br/>${desc}
	</#if>
<#else>
<#if material_requirement?exists>
抱歉! 你还缺少下面的一些材料.<br/>
	<#if (material_requirement.items?size > 0)>
		<#list material_requirement.items as item>
			${item.name}x${material_requirement.amounts[item_index]}<br/>
		</#list>
	</#if>
	<@gobuy/>
</#if>
<#if err_msg?exists && err_msg.code = -25>${err_msg.text}<br/><@gopay/></#if>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml>