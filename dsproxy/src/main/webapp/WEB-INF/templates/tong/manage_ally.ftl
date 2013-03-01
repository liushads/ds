<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if desc?exists>
${desc}<br/>
</#if>
同盟管理<br/>
<#if type==1>
已有  <@a href="/p?t_MAl/${pid}/0"/>申请中</a><br/>
	<#if (page_objs?size>0)>
		<#list page_objs as ally>		
			<@a href="/p?t_MAlA/${pid}/remove/${type}/${ally.allyId}"/>解盟</a>  
			${ally.allyName?if_exists}<br/>
		</#list>
		<#if (page>0)><@a href="/p?t_MAl/${pid}/${type}/${page-1}"/>上页.</a></#if>
		<#if (page<total_page-1)><@a href="/p?t_MAl/${pid}/${type}/${page+1}"/>下页.</a></#if>
		<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
	</#if>
<#else>
<@a href="/p?t_MAl/${pid}/1"/>已有</a>  申请中<br/>
	<#if (page_objs?size>0)>
		<#list page_objs as ally>		
			<@a href="/p?t_MAlA/${pid}/allow/${type}/${ally.allyId}"/>同意</a>  
			<@a href="/p?t_MAlA/${pid}/refuse/${type}/${ally.allyId}"/>拒绝</a> ${ally.allyName?if_exists}<br/>
		</#list>
		<#if (page>0)><@a href="/p?t_MAl/${pid}/${type}/${page-1}"/>上页.</a></#if>
		<#if (page<total_page-1)><@a href="/p?t_MAl/${pid}/${type}/${page+1}"/>下页.</a></#if>
		<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
	</#if>
</#if>
<br/>
<@a href="/p?t_M/${pid}/"/>返回帮会</a><br/>
<@gogame/>
</p>
</card>
</wml>

