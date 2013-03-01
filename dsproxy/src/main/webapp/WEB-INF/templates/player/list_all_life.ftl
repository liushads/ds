<#-- 显示可以进行镶嵌的装备列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
【<@a href="/p?p_VSP/${pid}/effect/"/>特性</a>|<@a href="/p?p_VSP/${pid}/res/"/>抗性</a>|时效】<br/>
<#if (page_objs?size>0)>
	<#list page_objs as item>		
		<@a href="/p?i_SLD/${pid}/${item.item.id}"/>${item.item.name}</a>:${item.leftStr}<br/>
	</#list>
	<#if (page>0)><@a href="/p?p_LL/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?p_LL/${pid}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
<#else>
列表为空 <br/>
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml> 