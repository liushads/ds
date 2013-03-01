<#-- 显示可以已经镶嵌了的宝石列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if npc?exists>${npc.name}：</#if><#if dialog?exists && dialog.dialog?exists && dialog.dialog != "" >${dialog.dialog}
<#else>
${last_npc.name}:请选择需要洗掉哪件装备上面的宝石?
</#if><br/>
<#if page_objs?size=0>
	你身上没有镶嵌宝石的装备.<br/>
<#else>
	<#list page_objs as playerItem>
		<@a href="/p?i_EA/${pid}/${playerItem.id}/"/>${playerItem.item.name}(${playerItem.item.level}级)</a><br/>
	</#list>
	<#if (page>0)><@a href="/p?i_LE/${pid}/${npc_id}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?i_LE/${pid}/${npc_id}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
</#if>
<br/>
<@gofacility/>
<@gogame/>
</p>
</card>
</wml>