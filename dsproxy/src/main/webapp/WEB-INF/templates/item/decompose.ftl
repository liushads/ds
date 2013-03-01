<#-- 装备分解 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${location}<br/>
${last_npc.name}:
<#if obj?exists>
分解成功,你获得：<br/>
	<#if (obj.items?size > 0)>
		<#list obj.items as item>
			<@a href="/p?i_II/${pid}/${item.id}"/>${item.name}x${obj.amounts[item_index]}</a><br/>
		</#list>
	</#if>
	<#if degree?exists>
	分解熟练度加${degree}<br/>
	</#if>
	<#if copper?exists>
	扣铜${copper}<br/>
	</#if>
	<@a href="/p?i_LD/${pid}/"/>继续分解</a><br/><#-- 跳转到分解列表 -->
<#else>
抱歉! 系统错误.
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml> 