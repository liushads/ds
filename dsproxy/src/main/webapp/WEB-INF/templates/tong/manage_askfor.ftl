<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if desc?exists>
${desc}<br/>
</#if>
仓库管理<br/>
索要列表：（按帮会排名排）<br/>
<#if (page_objs?size>0)>
	<#list page_objs as depot>		
		<@a href="/p?t_MAsA/${pid}/allow/${depot.id}/${depot.itemId}"/>同意</a>  
		<@a href="/p?t_MAsA/${pid}/refuse/${depot.id}/${depot.itemId}"/>拒绝</a> ${depot.askPlayerName?if_exists}${depot.askPlayerName}   索要<@a href="/p?t_II/${pid}/${depot.id}/ma"/><#if depot.item?exists>${depot.item.name?if_exists}(${depot.item.level?if_exists})</#if></a><#if (depot.frozenAmount>1)>x${depot.frozenAmount}</#if><br/>
	</#list>
	<#if (page>0)><@a href="/p?t_MAs/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?t_MAs/${pid}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
</#if>

<@a href="/p?t_M/${pid}/"/>返回帮会</a><br/>
<@gogame/>
</p>
</card>
</wml>

