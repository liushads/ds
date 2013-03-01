<#-- 显示可以合成的宝石列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${location}<br/>
${last_npc.name}:
<#if material_requirement?exists>
抱歉! 你还缺少下面的一些材料.<br/>
	<#if (material_requirement.items?size > 0)>
		<#list material_requirement.items as item>
			${item.name}x${material_requirement.amounts[item_index]}<br/>
		</#list>
	</#if>
	<@gobuy/>
</#if>
<#if player_item?exists>
恭喜你合成成功,你获得
<@a href="/p?i_I/${pid}/${player_item.id}/0/n/"/><@showName player_item/><#if (amount>1)>x${amount}</#if></a><br/>
<#if gdegree?exists>
锻造熟练度加${gdegree}<br/>
</#if>
<@a href="/p?i_LCM/${pid}/${itemId}/${npc_id}"/>继续合成</a><br/>
<@goback/>
<#elseif err_msg?exists>
抱歉! 合成失败,${err_msg.text}<br/>
<#if err_msg.code = -25 ><@gopay/></#if>
</#if>
<@gogame/>
</p>
</card>
</wml>