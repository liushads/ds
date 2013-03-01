<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if page_objs?exists && (page_objs?size>0)>
玩家列表:<br/>
	<#list page_objs as player>
		<@a href="/p?p_VO/${pid}/${player.id}"/><#if player.teamId!=0>*</#if>${player.name}(${player.level}级<#if player.sex=0>女<#else>男</#if>@${player.location})
		</a><br/>
	</#list>
	<#if (page>0)><@a href="/p?p_LP/${pid}/${playerType}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?p_LP/${pid}/${playerType}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
<#else>
无。<br/>	
</#if>
<@goback />
<@gogame/>
</p>
</card>
</wml>

 