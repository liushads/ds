<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if desc?exists>
${desc}<br/><br/>
</#if>
公告列表:<br/>
<#if (page_objs?size>0)>
	<#list page_objs as tn>	
		<@a href="/p?t_VN/${pid}/${tn.id}"/>${tn.title}</a>(${tn.addTime?string("yyyy-MM-dd")})
		<br/>
	</#list>
	<#if (page>0)><@a href="/p?t_MP/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?t_MP/${pid}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
</#if>
<br/>
<@goback/>
<@gogame/>
</p>
</card>
</wml>
