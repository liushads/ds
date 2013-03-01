<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${last_npc.name}:<br/>
<#if player_item?exists>
恭喜你,升星成功,你获得装备<br/>
<@showBeautifulName player_item/>
	<#if desc?exists>新增属性:${desc}<br/></#if><#-- 属性提示 -->
	<@a href="/p?i_PRT/${pid}/${player_item.id}"/>继续升星</a><br/>
<#else>
抱歉! 
<#if material_requirement?exists>
缺少材料<br/>
	<#if (material_requirement.items?size > 0)>
		<#list material_requirement.items as item>
			${item.name}x${material_requirement.amounts[item_index]}<br/>
		</#list>
	</#if><@gobuy/>
</#if>
<#if desc?exists>${desc}<br/><#if err_msg?exists && err_msg.code = -25><@gopay/></#if></#if><#-- 属性提示 -->
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml>