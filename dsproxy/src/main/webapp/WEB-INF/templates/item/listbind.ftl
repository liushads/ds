<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if last_npc?exists>${last_npc.name}:</#if>
	<#if desc?exists>${desc}。<br/></#if>
<#if page_objs?exists && (page_objs?size>0)>
你想解除哪件装备的绑定?(道具解除绑定需要花费X银)<br/>
	<#list page_objs as pi>
		<@a href="/p?i_I/${pid}/${pi.id}"/><@showName pi/></a>
		<@a href="/p?i_UB/${pid}/${pi.id}"/>解除绑定</a><br/>
	</#list>
	<#if (page>0)><@a href="/p?i_LR/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?i_LR/${pid}/${page+1}"/>">下页.</a></#if>
	(${page+1}/${total_page})<br/>
<#else>
你没有可以解除绑定的装备。<br/>
</#if>
<@gofacility/>
</p></card>
</wml> 