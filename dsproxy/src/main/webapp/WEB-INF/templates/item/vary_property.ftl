<#-- 属性转换 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if last_npc?exists>${last_npc.name}:<br/></#if>
<#if player_item?exists>	
	<#if desc?exists>${desc} <#if err_msg?exists && err_msg.code = -15><@gobuy/><#else><br/></#if></#if>
	装备<@showHorizontal player_item/><br/>
	<#list player_item.itemProperties as property>
		<@a href="/p?i_VPT/${pid}/${player_item.id}/${property.id}/${td}"/>继续改变</a>&nbsp;
		<@a href="/p?i_RP/${pid}/${player_item.id}/${property.id}/"/>${property.name}</a><br/>
	</#list>
</#if>
<@a href="/p?i_LV/${pid}/"/>返回装备选择</a><br/>
<@gofacility/>
</p></card>
</wml> 