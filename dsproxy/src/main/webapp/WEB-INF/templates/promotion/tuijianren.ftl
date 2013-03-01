<#include "/include/header.ftl">
<card title="${game_title}"><p>
推荐的好友:<br/>
<#if page_objs?exists && (page_objs?size>0)>
	<#list page_objs as tuijian>
		<@a href="/p?p_TJ/${pid}/jiang/${tuijian.id}"/>${tuijian.name}</a><br/>
	</#list>
	<#if (page>0)><@a href="/p?p_TJ/${pid}/index/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?p_TJ/${pid}/index/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
<#else>
你没有推荐人玩家
<br/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>