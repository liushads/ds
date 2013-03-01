<#-- 显示可以进行属性转换的装备列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if last_npc?exists>${last_npc.name}:<br/></#if>
<#if player_item?exists>
	你需要将该装备哪个特性进行转换<br/>
	<#if item?exists>转换需要${item.name}<#else></#if><br/>
	装备<@showHorizontal player_item/><br/>
	<#list player_item.itemProperties as property>
		<@a href="/p?i_VP/${pid}/${player_item.id}/${property.id}/${td}"/>改变</a>&nbsp;
		<@a href="/p?i_RP/${pid}/${player_item.id}/${property.id}/"/>${property.name}</a><br/>
	</#list>
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml> 