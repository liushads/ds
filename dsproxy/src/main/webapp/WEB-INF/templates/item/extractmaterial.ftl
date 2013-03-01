<#-- 显示装备的材料 -->
<#include "/include/header.ftl">
<#include "/include/item.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if player_item?exists>
	<@showBeautifulName player_item/>
<#elseif item?exists >
<img alt="" src="${img_server}/${item.namePinyin}.gif" /><br/>
名称:${item.name}<br/>
需求:【${item.level}级佩戴】<br/>
<#if item.hp != 0>
体力:${item.hp}<br/>
</#if>
<#if item.mp != 0>
内力:${item.mp}<br/>
</#if>
<#if item.attackMax != 0>
攻击:${item.attackMin}-${item.attackMax}<br/>
</#if>
<#if item.defence != 0>
防御:${item.defence}<br/>
</#if>
<#if item.agility != 0>
敏捷:${item.agility}<br/>
</#if>
<#if item.speed != 0>
移动:${item.speed}<br/>
</#if>
<#if (item.type = 1 && item.itemSubType > 0 && item.itemSubType <= 5) >
类型:【<@outArmAdept item />】<br/>
</#if>
负重:${item.room}<br/>
说明:${item.description}<br/>
镶嵌:<#if (item.maxAppend>0)>可镶嵌
	<#else>不可镶嵌</#if><br/>
</#if>
【${op["${return}"]}需要材料】<br/>
<#if obj?exists>
	<#if (obj.items?size > 0)>
		<#list obj.items as item>
			${item.name}x${obj.amounts[item_index]}<br/>
		</#list>
	</#if>
	<#if obj.copper != 0 >
	金钱:${obj.copper}银<br/>
	</#if>
	<#if player_item?exists>
		<@outOper player_item,"${return}"/><br/>
	</#if>
	<#if item?exists>
		<@operator item,"${return}"/>	
	</#if>
	
</#if>
<br/>
<@goback/>
<@gofacility/>
</p></card>
</wml>