<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
你需要金刚钻才可以打孔，每件装备最多可以开4个孔。<br/>
<#if page_objs?exists && (page_objs?size>0)>
	<#list 0..page_objs?size-1 as i>
		<@a href="/p?i_PT/${pid}/${page_objs[i].id}"/>打孔</a> <@showNameUrl page_objs[i]/><br/>
	</#list>
	<#if (page>0)><@a href="/p?i_Lp/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?i_Lp/${pid}/${page+1}"/>下页.</a>
	(${page+1}/${total_page})<br/>
	</#if>
<#else>
很抱歉，你身上没有可以进行打孔的装备。<br/>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml> 