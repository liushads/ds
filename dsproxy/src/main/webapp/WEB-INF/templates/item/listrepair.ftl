<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${last_npc.name}:<#if desc?exists>${desc}。</#if>
<#if page_objs?exists && (page_objs?size>0)>
您要修理哪件装备?<br/>
	<#if (copper?exists)>
		全部 <@a href="/p?i_R/${pid}/0/0"/>[${copper}铜]</a><@a href="/p?i_R/${pid}/0/1"/>[0.5金]</a>修理<br/>
	</#if>
	<#list 0..page_objs?size-1 as i>
		<@a href="/p?i_R/${pid}/${page_objs[i].id}/0"/>修理</a> <@showNameUrl page_objs[i]/><br/>
	</#list>
	<#if (page>0)><@a href="/p?i_LR/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?i_LR/${pid}/${page+1}"/>下页.</a></#if>
	(${page+1}/${total_page})<br/>
<#else>
你没有需要修理的装备。<br/>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml> 