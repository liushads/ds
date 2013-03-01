<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if desc?exists>${desc}</#if>
你准备使用${player_item.item.name},请选择要替换下的装备：<br/>
<#list items as arm>
	<@showNameUrl arm/> <@a href="/p?i_SwA/${pid}/${arm.id}/${player_item.id}"/>换</a><br/>
</#list>
<@goback />
</p></card>
</wml> 