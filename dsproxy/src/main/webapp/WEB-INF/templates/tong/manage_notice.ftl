<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if desc?exists>
${desc}<br/><br/>
</#if>
公告列表:<@a href="/p?t_CN/${pid}"/>创建公告</a><br/>
<#if (page_objs?size>0)>
	<#list page_objs as tn>	
		<@a href="/p?t_DN/${pid}/${tn.id}"/>删除</a> <@a href="/p?t_VN/${pid}/${tn.id}"/>${tn.title}</a>
		<br/>
	</#list>
	<#if (page>0)><@a href="/p?t_MN/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?t_MN/${pid}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
</#if>
<br/>
<@a href="/p?t_M/${pid}/"/>返回帮会</a><br/>
<@gogame/>
</p>
</card>
</wml>
