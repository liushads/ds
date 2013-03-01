<#-- 显示道道具特殊属性 -->
<#include "/include/header.ftl">
<#include "/include/item.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if player_item?exists>
	<#if player_item.item.icon?exists>
	<img alt="" src="${img_server}/${player_item.item.namePinyin}.gif" /><br/>
	</#if>	
	<#if player_item?exists>
	<@showBeautifulName player_item/>
		特性列表:<br/>
		<#list player_item.itemProperties as property>
			<#if property.description?exists>
				${property.description}<#-- 基础属性计算为数值显示 -->
				<@printPropertyValue property,player_level/>
			<#else>
				${property.name}
			</#if><br/>
		</#list>
	</#if>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>