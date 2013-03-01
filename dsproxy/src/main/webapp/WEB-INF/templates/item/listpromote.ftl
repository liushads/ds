<#-- 显示可以打造装备列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${last_npc.name}:<br/>
你需要升星石才可以升星，每件装备最多可以升3次星。<#if desc?exists>${desc}</#if><br/>
<#if page_objs?exists && (page_objs?size>0)>
	<#list 0..page_objs?size-1 as i>
		<@a href="/p?i_PRT/${pid}/${page_objs[i].id}"/>升星</a> <@showNameUrl page_objs[i]/><br/>
	</#list>
	<#if (page>0)><@a href="/p?i_Lp/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?i_Lp/${pid}/${page+1}"/>下页.</a></#if>
	(${page+1}/${total_page})<br/>
<#else>
很抱歉，你身上没有可以进行升星的装备。<br/>
</#if>
<@goback/>
<@gofacility/>
</p></card>
</wml>