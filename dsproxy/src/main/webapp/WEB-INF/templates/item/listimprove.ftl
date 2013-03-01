<#-- 显示可以打强化装备列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${last_npc.name}:<br/>您需要强化那件装备
<#if desc?exists>${desc}</#if><br/>
<#if page_objs?exists && (page_objs?size>0)>
	<#list 0..page_objs?size-1 as i>
		<@a href="/p?i_IMT/${pid}/${page_objs[i].id}"/>强化</a> <@showNameUrl page_objs[i]/><br/>
	</#list>
	<#if (page>0)><@a href="/p?i_LI/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?i_LI/${pid}/${page+1}"/>下页.</a></#if>
	(${page+1}/${total_page})<br/>
<#else>
没有可以进行强化的装备.<br/>
</#if>
<@goback/>
<@gogame/>
小提示:可以通过移花接木将已强化的装备的强化等级转移到另外一件装备上去<br/>
</p></card>
</wml>