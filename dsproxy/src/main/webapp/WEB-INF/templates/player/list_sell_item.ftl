<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>

我的摊位：<@a href="/p?p_VP/${pid}/1"/>宠物</a> <@a href="/p?ex_VSA/${pid}/1"/>一键收摊</a>
<#if page_objs?exists && (page_objs?size>0)>

<br/>
	<#list page_objs as playerItem>
		<@showSellNameUrl playerItem/><br/>
	</#list>
	<#if (page>0)><@a href="/p?p_VSA/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?p_VSA/${pid}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
<#else>
<br/>
暂无摆摊的物品<br/>	
</#if>
<br/>
<@gogame/>
</p>
</card>
</wml>