<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
帮会大事记<br/>
<#if (page_objs?size>0) >
	<#list page_objs as log>
		${log.msg?if_exists}(${log.addTime?string("yyyy-MM-dd")})<br/>
  	</#list>
  	<#if (page>0)><@a href="/p?t_LS/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?t_LS/${pid}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
</#if>
<@a href="/p?t_M/${pid}/"/>返回帮会</a><br/>
<@gogame/>
</p>
</card>
</wml>
