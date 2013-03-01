<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if item?exists>
	<#if item.icon?exists>
	<img alt="" src="${img_server}/${item.namePinyin}.gif" /><br/>
	</#if>
	名称:${item.name}<br/>	
	<#if item.attackMax != 0>
	攻击:${item.attackMin}-${item.attackMax}<br/>
	</#if>
	<#if item.defence != 0>
	防御:${item.defence}<br/>
	</#if>
	<#if item.agility != 0>
	敏捷:${item.agility}<br/>
	</#if>
	<#if item.hp &gt; 0>
	体力:${item.hp}<br/>
	</#if>
	说明:${item.description}<br/>
	负重:${item.room}<br/>
</#if>
<@goback/>
</p></card>
</wml>


 