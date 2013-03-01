<#-- 显示可以合成的宝石列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
合成<@showItemNoBuy item/>需要材料:<br/>
<#if material_requirement?exists>
	<#if (material_requirement.items?size > 0)>
		<#list material_requirement.items as item>
			${item.name}x${material_requirement.amounts[item_index]}<br/>
		</#list>
	</#if>
	<#if material_requirement.copper?exists>
	铜币:${material_requirement.copper}<br/>
	</#if>
	<#if material_requirement.gold?exists>
	金锭:${material_requirement.gold}<br/>
	</#if>
	<#if material_requirement.degree?exists>
	技能等级:
	<#if material_requirement.degree?number==0>制药初级</#if>
	<#if material_requirement.degree?number==300>制药中级</#if>
	<#if material_requirement.degree?number==600>制药高级</#if>
	<#if material_requirement.degree?number==1000>制药专家</#if><br/>
	</#if>
</#if>
<#if items?exists>
你身上现有的材料列表:<br/>
<#list items as playerItem>
<@showNoOpUrl playerItem/><br/>
</#list>
<@a href="/p?i_A/${pid}/${item.id}/0/${npc_id}/"/>合成</a><br/>
<#else>
你包裹里面没有相应的材料,暂时不能合成.<br/>
</#if>
<@goback/>
<@gogame/>
</p>
</card>
</wml>