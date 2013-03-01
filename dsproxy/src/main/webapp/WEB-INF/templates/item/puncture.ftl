<#-- 显示打孔装备 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${last_npc.name}:<br/>
<#if desc?exists>${desc} <br/>
<#if err_msg?exists><#if err_msg.code = -21><@gobuy/></#if><#if err_msg.code = -25><@gopay/></#if></#if>
</#if>
<#if player_item?exists>		
	打孔成功，你获得装备${player_item.item.name}<br/>
	<@showPlayerItem player_item />
	<@a href="/p?i_PT/${pid}/${player_item.id}"/>继续打孔</a><br/>
</#if>
<#if material_requirement?exists>
抱歉! 缺少材料<br/>
	<#if (material_requirement.items?size > 0)>
		<#list material_requirement.items as item>
			${item.name}x${material_requirement.amounts[item_index]}<br/>
		</#list>
	</#if><@gobuy/>
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml>


 